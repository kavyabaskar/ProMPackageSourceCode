package org.processmining.stream.core.interfaces;

import javax.swing.JComponent;

public interface XSReaderResultVisualizer<T> {

	public JComponent visualize(T streamBasedObject);

}
