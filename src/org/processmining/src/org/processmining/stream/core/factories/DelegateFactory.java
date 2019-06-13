package org.processmining.stream.core.factories;

import org.processmining.stream.core.delegates.XSWriterDelegate;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSWriter;

public class DelegateFactory {

	public static <T extends XSDataPacket<?, ?>> XSWriter<T> createWriterDelegate() {
		return new XSWriterDelegate<T>();
	}

}
