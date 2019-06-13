package org.processmining.stream.core.abstracts;

import java.util.List;

import org.processmining.stream.core.enums.CommunicationType;
import org.processmining.stream.core.factories.DelegateFactory;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSHub;
import org.processmining.stream.core.interfaces.XSWritable;
import org.processmining.stream.core.interfaces.XSWriter;

public abstract class AbstractXSHub<T1 extends XSDataPacket<?, ?>, T2 extends XSDataPacket<?, ?>>
		extends AbstractXSRunnableXSWritable<T1> implements XSHub<T1, T2> {

	private final XSWriter<T2> delegate;

	public XSWriter<T2> getWriterDelegate() {
		return delegate;
	}

	public AbstractXSHub(String name, CommunicationType communicationType) {
		super(name, communicationType);
		delegate = DelegateFactory.createWriterDelegate();
	}

	public void connect(XSWritable<T2> writable) {
		delegate.connect(writable);
	}

	public void disconnect(XSWritable<T2> writable) {
		delegate.disconnect(writable);
	}

	public void write(T2 dataPacket) {
		delegate.write(dataPacket);
	}

	public long getNumberOfPacketsSend() {
		return delegate.getNumberOfPacketsSend();
	}

	public List<XSWritable<T2>> getWritables() {
		return delegate.getWritables();
	}

	protected void handleNextPacket(T1 packet) {
		write(transform(packet));
	}

	protected abstract T2 transform(T1 packet);

	@Override
	public final void run() {
		super.run();
		write(null);
	}
}
