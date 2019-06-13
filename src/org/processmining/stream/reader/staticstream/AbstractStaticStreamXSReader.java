package org.processmining.stream.reader.staticstream;

import java.util.List;

import org.processmining.framework.plugin.PluginContext;
import org.processmining.stream.core.abstracts.AbstractXSReader;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSStaticStream;
import org.processmining.stream.core.interfaces.XSVisualization;
import org.processmining.stream.reader.staticstream.visualization.XSStreamToXSStaticXSStreamVisualization;

public abstract class AbstractStaticStreamXSReader<T extends XSDataPacket<?, ?>, S extends XSStaticStream<T>>
		extends AbstractXSReader<T, S, Object> {

	private final int totalNumberOfEvents;
	private final XSStaticStream<T> container;
	private final PluginContext context;

	public AbstractStaticStreamXSReader(final String name, XSVisualization<Object> visualization,
			final int totalNumberOfEvents, final XSStaticStream<T> container) {
		this(name, visualization, totalNumberOfEvents, container, null);
	}

	public AbstractStaticStreamXSReader(final String name, XSVisualization<Object> visualization,
			final int totalNumberOfEvents, final XSStaticStream<T> container, final PluginContext context) {
		super(name, visualization);
		visualization = initializeVisualizer(totalNumberOfEvents);
		visualization.start();
		this.totalNumberOfEvents = totalNumberOfEvents;
		this.container = container;
		this.context = context;
	}

	protected XSVisualization<Object> initializeVisualizer(int totalNumberOfEvents) {
		return new XSStreamToXSStaticXSStreamVisualization(totalNumberOfEvents);
	}

	public XSStaticStream<T> getContainer() {
		return container;
	}

	protected void handleNextPacket(T packet) {
		if (container.size() < totalNumberOfEvents) {
			container.add(packet);
			//			getVisualization().update(packet.toString());
			getVisualization().updateVisualization(packet);
		} else {
			if (context != null) {
				writeContainerToContext(container, getContext());
			}
			this.interrupt();
		}
	}

	protected abstract void writeContainerToContext(List<T> container, PluginContext context);

	public PluginContext getContext() {
		return context;
	}
}
