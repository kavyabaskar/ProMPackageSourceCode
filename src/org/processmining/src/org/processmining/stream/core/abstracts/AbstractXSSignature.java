package org.processmining.stream.core.abstracts;

import java.util.HashSet;

import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSSignature;

public abstract class AbstractXSSignature extends HashSet<Object> implements XSSignature {

	private static final long serialVersionUID = -8210683528561236394L;

	public boolean evaluate(XSDataPacket<?, ?> dataPacket) {
		boolean result = true;
		for (Object key : this) {
			if (!dataPacket.containsKey(key)) {
				result = false;
				break;
			}
		}
		return result;
	}

}
