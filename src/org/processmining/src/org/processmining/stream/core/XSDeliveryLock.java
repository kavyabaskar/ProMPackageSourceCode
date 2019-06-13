package org.processmining.stream.core;

import java.util.concurrent.ConcurrentHashMap;

import org.processmining.stream.core.interfaces.XSRunnable;

public class XSDeliveryLock extends ConcurrentHashMap<XSRunnable, Boolean> {

	private static final long serialVersionUID = 5880021443140515342L;
}
