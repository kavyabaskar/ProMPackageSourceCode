package org.processmining.stream.core.interfaces;

import java.util.Set;

/**
 * A signature represents the minimum set of keys that a data packet must
 * provide in order to be conforming with the stream.
 *
 * @author Sebastiaan J. van Zelst
 * @author Andrea Burattin
 */
public interface XSSignature extends Set<Object> {

	/**
	 * This method evaluates the given packet
	 *
	 * @param dataPacket
	 *            the data packet to evaluate
	 * @return <tt>true</tt> if the data packet conforms the signature,
	 *         <tt>false</tt> otherwise
	 */
	public boolean evaluate(XSDataPacket<?, ?> dataPacket);

}
