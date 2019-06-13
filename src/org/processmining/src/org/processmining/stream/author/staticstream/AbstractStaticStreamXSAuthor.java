package org.processmining.stream.author.staticstream;

import org.processmining.stream.core.abstracts.AbstractXSAuthor;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSStaticStream;
import org.processmining.stream.core.interfaces.XSVisualization;

public abstract class AbstractStaticStreamXSAuthor<D extends XSDataPacket<?, ?>, V> extends AbstractXSAuthor<D, V> {

	private int pointer = 0;
	private final XSStaticStream<D> staticStream;

	public AbstractStaticStreamXSAuthor(String name, XSVisualization<V> visualization, XSStaticStream<D> staticStream) {
		super(name, visualization);
		this.staticStream = staticStream;
	}

	public XSStaticStream<D> getStaticStream() {
		return staticStream;
	}

	protected abstract void updateVisualization(D data);

	protected final void workPackage() {
		if (pointer < staticStream.size()) {
			D data = staticStream.get(pointer);
			write(data);
			updateVisualization(data);
			pointer++;
		} else {
			stopXSRunnable();
		}
	}

}
