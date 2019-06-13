package org.processmining.stream.core.interfaces;

/**
 * An XSReader reads something above XSDataPackets and produces some output of
 * type T2 at some point in time.
 */
public interface XSReader<T1 extends XSDataPacket<?, ?>, T2>
		extends XSRunnable, XSWritable<T1>, XSVisualizable, XSStronglyTyped<T1> {

	public T2 getCurrentResult1();
	public T2 getCurrentResult2();

}
