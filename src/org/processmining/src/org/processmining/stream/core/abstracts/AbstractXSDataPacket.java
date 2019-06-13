package org.processmining.stream.core.abstracts;

import java.io.Serializable;

import org.processmining.stream.core.interfaces.XSDataPacket;

public abstract class AbstractXSDataPacket<K extends Serializable, V extends Serializable>
		implements XSDataPacket<K, V> {

	private long size = 1;

	@Override
	public long diskSize() {
		//TODO: fix this method to return the actual byte size of the object
		return this.size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public XSDataPacket<K, V> clone() {
		try {
			return (XSDataPacket<K, V>) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
