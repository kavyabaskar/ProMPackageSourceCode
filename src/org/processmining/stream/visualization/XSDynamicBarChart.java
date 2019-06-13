package org.processmining.stream.visualization;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.processmining.framework.util.Pair;
import org.processmining.stream.core.abstracts.AbstractXSRunnable;
import org.processmining.stream.core.interfaces.XSVisualization;
import org.processmining.stream.util.views.DynamicTimeSeriesBarChart;

public class XSDynamicBarChart extends AbstractXSRunnable implements XSVisualization<String> {

	private DynamicTimeSeriesBarChart chart;
	private ChartPanel chartPanel;
	//	private final Object monitor = new Object();
	private final Lock lock = new ReentrantLock(true);
	private final Condition notEmpty = lock.newCondition();
	private final Queue<Pair<Date, String>> queue = new ConcurrentLinkedQueue<>();
	private JComponent visualization = new JPanel();

	public XSDynamicBarChart(String name, int maxNumOfTimeSeries) {
		super(name);
		visualization.setLayout(new GridBagLayout());
		GridBagConstraints layout = new GridBagConstraints();

		layout.fill = GridBagConstraints.BOTH;
		layout.weightx = 1.0;
		layout.weighty = 1.0;
		layout.gridwidth = GridBagConstraints.REMAINDER;

		chart = new DynamicTimeSeriesBarChart(10, maxNumOfTimeSeries, "", "Time", "# Emissions");
		chartPanel = new ChartPanel(this.chart.getChartInstance());
		visualization.add(this.chartPanel, layout);
	}

	public JComponent asComponent() {
		return visualization;
	}
	
	public JComponent asComponent2() {
		return new JPanel();
	}


	@Override
	public void stopXSRunnable() {
		super.interrupt();
		lock.lock();
		try {
			while (!queue.isEmpty()) {
				Pair<Date, String> packet = queue.poll();
				chart.addCategoryDataPoint(packet.getFirst(), packet.getSecond());
			}
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	@Deprecated
	public void update(Pair<Date, String> message) {
		updateVisualization(message);
	}

	@Deprecated
	public void update(String message) {
		updateVisualization(message);
	}

	public void updateVisualization(Pair<Date, String> newArtifact) {
		lock.lock();
		try {
			queue.add(newArtifact);
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public void updateVisualization(String newArtifact) {
		updateVisualization(new Pair<Date, String>(new Date(System.currentTimeMillis()), newArtifact));
	}

	protected void workPackage() {
		lock.lock();
		try {
			if (!isStopped()) {
				if (queue.isEmpty()) {
					notEmpty.await();
				}
				// if queue is empty after notification, we "stop"
				if (!queue.isEmpty()) {
					Pair<Date, String> packet = queue.poll();
					chart.addCategoryDataPoint(packet.getFirst(), packet.getSecond());
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

}
