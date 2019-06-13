package org.processmining.stream.visualization;

import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.processmining.framework.util.Pair;
import org.processmining.stream.core.abstracts.AbstractXSRunnable;
import org.processmining.stream.core.interfaces.XSVisualization;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class XSNumberOfEventsSentVisualizationImpl extends AbstractXSRunnable implements XSVisualization<String> {

	private long counter = 0;
	private Object monitor = new Object();
	private JLabel counterLabel = SlickerFactory.instance().createLabel("0");
	private JComponent visualization = new JPanel();
	private long timeout = 500;

	public XSNumberOfEventsSentVisualizationImpl(String name) {
		super(name);
		visualization.add(counterLabel);
	}

	public JComponent asComponent() {
		return visualization;
	}
	
	public JComponent asComponent2() {
		return new JPanel();
	}


	public void update(Pair<Date, String> message) {
		updateVisualization(message);

	}

	public void update(String object) {
		updateVisualization(object);

	}

	public void updateVisualization(Pair<Date, String> newArtifact) {
		updateVisualization(newArtifact.getSecond());
	}

	public void updateVisualization(String newArtifact) {
		synchronized (monitor) {
			counter++;
		}
	}

	protected void workPackage() {
		synchronized (monitor) {
			counterLabel.setText(Long.toString(counter));
		}
		visualization.revalidate();
		visualization.repaint();
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
//			System.out.print("Interrupted after: "+ counter + " events");
		}
	}

}
