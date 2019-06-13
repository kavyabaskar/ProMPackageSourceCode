package org.processmining.stream.core.interfaces;

import java.util.Map;

/**
 * A data packet represents the minimum entity that can be sent over a stream. A
 * data packet is basically a tuple and is modeled as a map (set of key-value
 * pairs).
 * <p/>
 * <p>
 * In order to be sent over a stream, a data packet is required to fulfill the
 * {@link XSSignature} of the stream (the stream will call the
 * {@link XSSignature#evaluate(XSDataPacket)} method).
 *
 * @param <K>
 *            the type of the keys
 * @param <V>
 *            the type of the values
 * @see java.util.Map
 * @see java.lang.Cloneable
 */
public interface XSDataPacket<K, V> extends Map<K, V>, Cloneable {

	/**
	 * This method returns the size of the current data packet
	 *
	 * @return the data packet size
	 */
	public long diskSize();

	/**
	 * Creates and returns a copy of this object.
	 *
	 * @return A clone of this instance
	 */
	public XSDataPacket<K, V> clone();
}
