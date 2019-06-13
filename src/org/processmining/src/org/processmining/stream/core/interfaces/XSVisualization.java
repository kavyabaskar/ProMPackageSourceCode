package org.processmining.stream.core.interfaces;

import java.util.Date;

import javax.swing.JComponent;

import org.processmining.framework.util.Pair;

/**
 * 
 * @author svzelst
 *
 * @param <T>
 *            defines the artifact to be used to construct the eventual
 *            visualization
 */
public interface XSVisualization<T> extends XSRunnable {

	public JComponent asComponent();

	@Deprecated
	public void update(Pair<Date, String> message);

	@Deprecated
	public void update(String object);

	public void updateVisualization(Pair<Date, T> newArtifact);

	public void updateVisualization(T newArtifact);

	//public JComponent asComponent1();
	
	public JComponent asComponent2();
}
