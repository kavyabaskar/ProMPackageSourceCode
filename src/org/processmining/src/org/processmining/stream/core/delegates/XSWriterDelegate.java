package org.processmining.stream.core.delegates;

import java.util.ArrayList;
import java.util.List;

import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSRunnable;
import org.processmining.stream.core.interfaces.XSWritable;
import org.processmining.stream.core.interfaces.XSWriter;

public final class XSWriterDelegate<T extends XSDataPacket<?, ?>> implements XSWriter<T> {

	private final List<XSWritable<T>> writables = new ArrayList<XSWritable<T>>();
	private final Object writablesLock = new Object();
	private final long packets = 0;

	private boolean canWrite(XSWritable<T> writable) {
		boolean result = true;
		if (writable instanceof XSRunnable) {
			XSRunnable wr = (XSRunnable) writable;
			result &= wr.isRunning();
		}
		return result;
	}

	public void connect(XSWritable<T> writable) {
		synchronized (writablesLock) {
			writables.add(writable);
		}
	}

	public void disconnect(XSWritable<T> writable) {
		synchronized (writablesLock) {
			writables.remove(writable);
		}
	}

	public long getNumberOfPacketsSend() {
		return packets;
	}

	public List<XSWritable<T>> getWritables() {
		return writables;
	}

	public void write(T dataPacket) {
		synchronized (writablesLock) {
			for (XSWritable<T> writable : writables) {
				if (canWrite(writable)) {
					writable.deliver(dataPacket);
				}
			}
		}
	}
}
