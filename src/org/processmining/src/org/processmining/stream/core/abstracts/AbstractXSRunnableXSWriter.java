package org.processmining.stream.core.abstracts;

import java.util.List;

import org.processmining.stream.core.factories.DelegateFactory;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSWritable;
import org.processmining.stream.core.interfaces.XSWriter;

public abstract class AbstractXSRunnableXSWriter<T extends XSDataPacket<?, ?>> extends AbstractXSRunnable
		implements XSWriter<T> {

	private final XSWriter<T> delegate;

	public AbstractXSRunnableXSWriter(String name) {
		super(name);
		delegate = DelegateFactory.createWriterDelegate();
	}

	public void connect(XSWritable<T> writable) {
		delegate.connect(writable);
	}

	public void disconnect(XSWritable<T> writable) {
		delegate.disconnect(writable);
	}

	public void write(T dataPacket) {
		delegate.write(dataPacket);
	}

	public long getNumberOfPacketsSend() {
		return delegate.getNumberOfPacketsSend();
	}

	public List<XSWritable<T>> getWritables() {
		return delegate.getWritables();
	}

	@Override
	public final void run() {
		super.run();
		write(null);
	}
}
