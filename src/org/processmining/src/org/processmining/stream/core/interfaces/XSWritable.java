package org.processmining.stream.core.interfaces;

import org.processmining.stream.core.enums.CommunicationType;

public abstract interface XSWritable<T extends XSDataPacket<?, ?>> {

	void deliver(T dataPacket);

	CommunicationType getCommunicationType();

	long getNumberOfPacketsReceived();

	Object getDeliveryLock();

	boolean isTrackingPerformance();

	long[] getPacketHandlingTimes();

	long[] getUsedMemory();

	void setTrackPerformance(boolean track);

	void setPerformanceTrackerStepSize(int i);

	/**
	 * access method of the deliver procedure, i.e. without interaction with the
	 * stream framework, use in case the writable should be used as a delegate /
	 * internally.
	 * 
	 * @param dataPacket
	 */
	void triggerPacketHandle(T dataPacket);

}
