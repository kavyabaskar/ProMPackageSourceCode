package org.processmining.stream.core.interfaces;

import java.util.List;

/**
 * An XSWriter is an entity that is able to write data packets onto a/some
 * XSWritable(s).
 *
 * @param <T>
 */
public abstract interface XSWriter<T extends XSDataPacket<?, ?>> {

	/**
	 * Connect to the XSWritable object
	 * 
	 * @param writable
	 *            to connect onto
	 */
	public void connect(XSWritable<T> writable);

	/**
	 * Disconnect from a XSWritable object
	 * 
	 * @param writable
	 *            to disconnect from
	 */
	public void disconnect(XSWritable<T> writable);

	/**
	 * Write onto all writable currently connected
	 * 
	 * @param dataPacket
	 *            to write
	 */
	public void write(T dataPacket);

	/**
	 * Get all writables to which this writer is currently connected
	 * 
	 * @return the writables
	 */
	public List<XSWritable<T>> getWritables();

	/**
	 * Get the total number of packets send so far.
	 * 
	 * @return
	 */
	public long getNumberOfPacketsSend();

}
