package org.processmining.stream.core.interfaces;

public interface XSHub<T1 extends XSDataPacket<?, ?>, T2 extends XSDataPacket<?, ?>>
		extends XSRunnable, XSWritable<T1>, XSWriter<T2> {

}
