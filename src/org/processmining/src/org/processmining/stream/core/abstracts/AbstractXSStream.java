package org.processmining.stream.core.abstracts;

import org.processmining.stream.core.enums.CommunicationType;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSReader;
import org.processmining.stream.core.interfaces.XSSignature;
import org.processmining.stream.core.interfaces.XSStream;
import org.processmining.stream.core.interfaces.XSWritable;

public abstract class AbstractXSStream<T extends XSDataPacket<?, ?>> extends AbstractXSHub<T, T>
		implements XSStream<T> {

	private final XSSignature signature;

	public AbstractXSStream(String name, XSSignature signature, CommunicationType communicationType) {
		super(name, communicationType);
		this.signature = signature;
	}

	@Override
	public void connect(XSWritable<T> writable) {
		assert (writable instanceof XSReader);
		@SuppressWarnings("unchecked")
		XSReader<T, ?> reader = (XSReader<T, ?>) writable;
		assert (getTopic().equals(reader.getTopic()));
		super.connect(reader);
	}

	@Override
	public XSSignature getSignature() {
		return signature;
	}

	@Override
	protected T transform(T packet) {
		return packet;
	}

}
