package org.processmining.stream.core.interfaces;

/**
 * A strongly typed instance is aware of the type of data packets(== topic)
 * send.
 *
 */
public interface XSStronglyTyped<T extends XSDataPacket<?, ?>> {

	public Class<T> getTopic();

}
