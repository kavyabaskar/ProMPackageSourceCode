package org.processmining.stream.core.interfaces;

import java.util.List;

/**
 * A static representation of a stream. Mainly for the purpose of debugging /
 * scientific experiments where we want to reuse a stream multiple times.
 * 
 * @author svzelst
 *
 * @param <T>
 */
public interface XSStaticStream<T extends XSDataPacket<?, ?>> extends List<T> {

	//	public int getNumberOfDistinctDataPacketClasses();

}
