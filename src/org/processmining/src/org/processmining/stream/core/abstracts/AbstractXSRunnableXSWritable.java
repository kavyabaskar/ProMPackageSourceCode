package org.processmining.stream.core.abstracts;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.processmining.stream.core.XSDeliveryLock;
import org.processmining.stream.core.enums.CommunicationType;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSRunnable;
import org.processmining.stream.core.interfaces.XSWritable;

public abstract class AbstractXSRunnableXSWritable<T_DPCK extends XSDataPacket<?, ?>> extends AbstractXSRunnable
		implements XSWritable<T_DPCK> {

	private AtomicBoolean busy = new AtomicBoolean(false);
	private final CommunicationType communicationType;
	private T_DPCK currentPacket;
	private final XSDeliveryLock deliveryLock = new XSDeliveryLock();
	private long[] handlingTimes;
	private XSRunnable latestWritingThread = null;
	//	private final MemoryMeter memoryMeter = new MemoryMeter();

	private long[] memoryUsage;

	private int nextHandlingTimeIndex = 0;

	private int nextMemoryUsageIndex = 0;

	private AtomicLong packets = new AtomicLong(0);

	private int performanceTrackerStepSize = 1;

	private final Object syncLock = new Object();

	private AtomicBoolean syncWaiting = new AtomicBoolean(false);

	private boolean trackPerformance = false;

	public AbstractXSRunnableXSWritable(final String name, final CommunicationType communicationType) {
		this(name, communicationType, false);
	}

	public AbstractXSRunnableXSWritable(final String name, final CommunicationType communicationType,
			boolean trackPerformance) {
		super(name);
		this.communicationType = communicationType;
		this.trackPerformance = trackPerformance;
		handlingTimes = trackPerformance ? new long[Byte.MAX_VALUE] : null;
		memoryUsage = trackPerformance ? new long[Byte.MAX_VALUE] : null;
	}

	/**
	 * delivers a packet at this writable. This method is typically performed by
	 * some writable thread.
	 */
	public final void deliver(T_DPCK packet) {
		switch (communicationType) {
			case ASYNC :
				if (busy.get()) {
					return;
				}
				break;
			default :
				break;
		}
		synchronized (getDeliveryLock()) {
			busy.set(true);
			latestWritingThread = (XSRunnable) Thread.currentThread();
			getDeliveryLock().put(latestWritingThread, !latestWritingThread.isStopped());
			storeNewPacket(packet);
			if (packet != null) {
				packets.addAndGet(1);
			}
			getDeliveryLock().notify();
		}
		if (communicationType.equals(CommunicationType.SYNC)) {
			synchronized (syncLock) {
				syncWaiting.set(true);
				try {
					syncLock.wait();
				} catch (InterruptedException e) {
					// interuption is OKAY, => writer thread will stop
				}
			}
		}

	}

	private void deregisterWritingEntity() {
		getDeliveryLock().remove(latestWritingThread);
		if (getDeliveryLock().keySet().isEmpty()) {
			interrupt();
		}
		if (getCommunicationType().equals(CommunicationType.SYNC)) {
			synchronizeWithWriter();
		}
	}

	public CommunicationType getCommunicationType() {
		return communicationType;
	}

	public XSDeliveryLock getDeliveryLock() {
		return deliveryLock;
	}

	protected long[] getHandlingTimes() {
		return handlingTimes;
	}

	public XSRunnable getLatestWritingThread() {
		return latestWritingThread;
	}

	//	protected MemoryMeter getMemoryMeter() {
	//		return memoryMeter;
	//	}

	protected long[] getMemoryUsage() {
		return memoryUsage;
	}

	protected int getNextHandlingTimeIndex() {
		return nextHandlingTimeIndex;
	}

	protected int getNextMemoryUsageIndex() {
		return nextMemoryUsageIndex;
	}

	protected T_DPCK getNextPacket() {
		T_DPCK res = currentPacket;
		currentPacket = null;
		return res;
	}

	public long getNumberOfPacketsReceived() {
		return packets.get();
	}

	public long[] getPacketHandlingTimes() {
		return handlingTimes;
	}

	public int getPerformanceTrackerStepSize() {
		return performanceTrackerStepSize;
	}

	public long[] getUsedMemory() {
		return memoryUsage;
	}

	protected abstract void handleNextPacket(T_DPCK packet);

	public boolean isTrackingPerformance() {
		return trackPerformance;
	}

	protected boolean isTrackPerformance() {
		return trackPerformance;
	}

	protected void setHandlingTimes(long[] handlingTimes) {
		this.handlingTimes = handlingTimes;
	}

	public void setLatestWritingThread(XSRunnable latestWritingThread) {
		this.latestWritingThread = latestWritingThread;
	}

	protected void setMemoryUsage(long[] memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	protected void setNextHandlingTimeIndex(int nextHandlingTimeIndex) {
		this.nextHandlingTimeIndex = nextHandlingTimeIndex;
	}

	protected void setNextMemoryUsageIndex(int nextMemoryUsageIndex) {
		this.nextMemoryUsageIndex = nextMemoryUsageIndex;
	}

	public void setPerformanceTrackerStepSize(int performanceTrackerStepSize) {
		this.performanceTrackerStepSize = performanceTrackerStepSize;
	}

	public void setTrackPerformance(boolean track) {
		if (!track) {
			trackPerformance = track;
		} else if (packets.get() == 0) {
			trackPerformance = track;
			handlingTimes = new long[Byte.MAX_VALUE];
			memoryUsage = new long[Byte.MAX_VALUE];
		}
	}

	private boolean shouldReceiveAPacket() {
		return (latestWritingThread == null
				|| (latestWritingThread != null && getDeliveryLock().get(latestWritingThread)));
	}

	protected void storeNewPacket(T_DPCK packet) {
		currentPacket = packet;
	}

	private void storePacketHandlingTime(long start, long end) {
		if (nextHandlingTimeIndex > handlingTimes.length - 1) {
			handlingTimes = Arrays.copyOf(handlingTimes, handlingTimes.length + Byte.MAX_VALUE);
		}
		if (getNumberOfPacketsReceived() % performanceTrackerStepSize == 0) {
			handlingTimes[nextHandlingTimeIndex] = end - start;
		} else {
			handlingTimes[nextHandlingTimeIndex] = -1;
		}
		nextHandlingTimeIndex++;
	}

	private void storeUsedMemory() {
		if (nextMemoryUsageIndex > memoryUsage.length - 1) {
			memoryUsage = Arrays.copyOf(memoryUsage, memoryUsage.length + Byte.MAX_VALUE);
		}
		if (getNumberOfPacketsReceived() % performanceTrackerStepSize == 0) {
			memoryUsage[nextMemoryUsageIndex] = measureUsedMemory();
		} else {
			memoryUsage[nextMemoryUsageIndex] = -1;
		}
		nextMemoryUsageIndex++;
	}

	/**
	 * measures actual memory currently used by the algorithm. may be changed by
	 * child classes to define a more specific measurement. For example, by only
	 * measuring the size of certain data structures within the child class.
	 * 
	 * @return
	 */
	protected long measureUsedMemory() {
		long res = -1;
		//		try {
		//			res = memoryMeter.measureDeep(this);
		//		} catch (IllegalStateException e) {
		//			// please try: -javaagent:<path to>/jamm.jar in JVM arguments
		//			res = -1;
		//		}
		return res;
	}

	private void synchronizeWithWriter() {
		while (!syncWaiting.get()) {
			Thread.yield();
		}
		synchronized (syncLock) {
			syncWaiting.set(false);
			syncLock.notify();
		}
	}

	/**
	 * Conceptually the (thread executing) the work package does the following:
	 * 1. Acquire read/write lock and check if a new message arrived 2. if no
	 * message arrived sleep 3. if interrupted && stopped, then exit 4. if woken
	 * up then verify that we are still running and that there is a packet. If
	 * so, handle the packet.
	 */
	protected final void workPackage() {
		synchronized (getDeliveryLock()) {
			T_DPCK nextPacket = getNextPacket();
			if (nextPacket == null) {
				if (shouldReceiveAPacket()) {
					try {
						getDeliveryLock().wait();
						nextPacket = getNextPacket();
						if (nextPacket == null) {
							if (getDeliveryLock().get(latestWritingThread)) {
								throw new NullPointerException(Thread.currentThread().getName().toString()
										+ ": Newly delivered data packet appears to be null.");
							} else if (!getDeliveryLock().get(latestWritingThread)) {
								deregisterWritingEntity();
							}
						}
					} catch (InterruptedException e) {
						// if a packet arrived just yet, signal that we handled it.
						if (busy.get()) {
							synchronizeWithWriter();
						}
					}
				} else if (latestWritingThread != null && !getDeliveryLock().get(latestWritingThread)) {
					deregisterWritingEntity();
				}
			}
			if (!isStopped()) {
				if (nextPacket != null) {
					long start = trackPerformance ? System.nanoTime() : -1;
					handleNextPacket(nextPacket);
					long end = trackPerformance ? System.nanoTime() : -1;
					if (trackPerformance) {
						storePacketHandlingTime(start, end);
						storeUsedMemory();
					}
					busy.set(false);
					if (getCommunicationType().equals(CommunicationType.SYNC)) {
						synchronizeWithWriter();
					}
				} else {
					throw new NullPointerException(Thread.currentThread().getName().toString()
							+ ": Newly delivered data packet appears to be null.");
				}
			}
		}
	}

	public void triggerPacketHandle(T_DPCK dataPacket) {
		handleNextPacket(dataPacket);
	}

}
