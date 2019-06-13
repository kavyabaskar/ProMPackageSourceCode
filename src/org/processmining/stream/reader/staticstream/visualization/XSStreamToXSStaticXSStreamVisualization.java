package org.processmining.stream.reader.staticstream.visualization;

import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.processmining.framework.util.Pair;
import org.processmining.stream.core.abstracts.AbstractXSVisualization;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class XSStreamToXSStaticXSStreamVisualization extends AbstractXSVisualization<Object> {

	private JComponent comp = new JPanel();
	private int lastVisualized = -1;
	private int length;
	private final Object monitor = new Object();
	private int received;

	public XSStreamToXSStaticXSStreamVisualization(int totalPackets) {
		super("XSEStream to XSStaticXSStream Visualization");
		this.length = totalPackets;
		comp.add(SlickerFactory.instance().createLabel("Packets received: 0/" + length));
	}

	public JComponent asComponent() {
		return comp;
	}
	
	public JComponent asComponent2() {
		return new JPanel();
	}


	public void update(Pair<Date, String> message) {
		updateVisualization(message.getSecond());
	}

	public void update(String object) {
		updateVisualization(object);
	}

	public void updateVisualization(Object newArtifact) {
		synchronized (monitor) {
			received++;
			monitor.notify();
		}
	}

	public void updateVisualization(Pair<Date, Object> newArtifact) {
		updateVisualization(newArtifact.getSecond());
	}

	protected void workPackage() {
		synchronized (monitor) {
			if (lastVisualized == received) {
				try {
					monitor.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			comp.removeAll();
			comp.add(SlickerFactory.instance().createLabel("Packets received: " + received + "/" + length));
			if (received >= length) {
				comp.add(SlickerFactory.instance().createLabel("... Static Stream Ready"));
			}
			comp.revalidate();
			comp.repaint();
			lastVisualized = received;
		}

	}

}
