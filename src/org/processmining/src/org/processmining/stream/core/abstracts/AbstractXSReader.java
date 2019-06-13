package org.processmining.stream.core.abstracts;

import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.processmining.framework.util.Pair;
import org.processmining.stream.core.enums.CommunicationType;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSReader;
import org.processmining.stream.core.interfaces.XSVisualization;

public abstract class AbstractXSReader<T extends XSDataPacket<?, ?>, S, V> extends AbstractXSRunnableXSWritable<T>
		implements XSReader<T, S> {

	private final XSVisualization<V> visualization;

	public AbstractXSReader(String name, final boolean trackPerformance, XSVisualization<V> visualization) {
		super(name, CommunicationType.SYNC, trackPerformance);
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

	public AbstractXSReader(String name, XSVisualization<V> visualization) {
		this(name, false, visualization);
	}

	protected abstract S computeCurrentResult1();
	public S getCurrentResult1() {
		S result1;
		synchronized (getDeliveryLock()) {
			result1 = computeCurrentResult1();
		}
		return result1;
	}

	public XSVisualization<V> getVisualization() {
		return visualization;
	}
	
	//for 2nd miner
	protected abstract S computeCurrentResult2();
	public S getCurrentResult2() {
		S result2;
		synchronized (getDeliveryLock()) {
			result2 = computeCurrentResult2();
		}
		return result2;
	}
	//

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
	@Deprecated
	public void pauseXSRunnable() {
		pause();
	}

	@Override
	public void start() {
		super.start();
		visualization.start();
	}

	@Override
	@Deprecated
	public void startXSRunnable() {
		start();
	}

	@Override
	@Deprecated
	public void stopXSRunnable() {
		interrupt();
	}

}
