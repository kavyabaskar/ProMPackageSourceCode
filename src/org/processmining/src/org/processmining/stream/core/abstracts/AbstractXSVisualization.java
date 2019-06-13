package org.processmining.stream.core.abstracts;

import org.processmining.stream.core.interfaces.XSVisualization;

public abstract class AbstractXSVisualization<T> extends AbstractXSRunnable implements XSVisualization<T> {

	public AbstractXSVisualization(String name) {
		super(name);
	}

}
