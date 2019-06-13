package org.processmining.stream.core.abstracts;

import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.processmining.framework.util.Pair;
import org.processmining.stream.core.interfaces.XSAuthor;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSVisualization;

public abstract class AbstractXSAuthor<D extends XSDataPacket<?, ?>, V> extends AbstractXSRunnableXSWriter<D>
		implements XSAuthor<D> {

	private final XSVisualization<V> visualization;

	public AbstractXSAuthor(String name) {
		this(name, null);
	}

	public AbstractXSAuthor(String name, XSVisualization<V> visualization) {
		super(name);
		this.visualization = visualization != null ? visualization : new AbstractXSVisualization<V>("") {
			public JComponent asComponent() {
				return new JPanel();
			}
			
			
			public JComponent asComponent2() {
				return new JPanel();
			}


			public void update(Pair<Date, String> message) {
			}

			public void update(String object) {
			}

			public void updateVisualization(Pair<Date, V> newArtifact) {
			}

			public void updateVisualization(V newArtifact) {
			}

			protected void workPackage() {
				interrupt();
			}
		};
	}

	public XSVisualization<V> getVisualization() {
		return visualization;
	}

	@Override
	public void interrupt() {
		super.interrupt();
		visualization.interrupt();
	}

	@Override
	public void pause() {
		super.pause();
		visualization.pause();
	}

	@Override
	public void pauseXSRunnable() {
		pause();
	}

	@Override
	public void start() {
		super.start();
		visualization.start();
	}

	@Override
	public void startXSRunnable() {
		start();
	}

	@Override
	public void stopXSRunnable() {
		interrupt();
	}

}
