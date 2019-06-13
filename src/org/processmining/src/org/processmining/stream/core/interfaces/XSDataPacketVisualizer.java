package org.processmining.stream.core.interfaces;

public interface XSDataPacketVisualizer<T1 extends XSDataPacket<?, ?>, T2> {

	public T2 decode(T1 packet);

}
