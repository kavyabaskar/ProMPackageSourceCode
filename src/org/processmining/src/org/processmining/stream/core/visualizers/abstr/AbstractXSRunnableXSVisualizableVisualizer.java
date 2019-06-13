package org.processmining.stream.core.visualizers.abstr;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.processmining.stream.core.interfaces.XSRunnable;
import org.processmining.stream.core.interfaces.XSVisualizable;
import org.processmining.stream.core.visualizers.views.StartPauseStopXSRunnableInteractionJPanel;

public abstract class AbstractXSRunnableXSVisualizableVisualizer<T extends XSRunnable & XSVisualizable> {

	private final JPanel container = new JPanel();
	private final JPanel interaction;
	private final JComponent visualization3 = new JPanel();
	private final JComponent qualityMeasuresContainer;
	/**private T visualized2;
	
	public T getVisualized2() {
		return visualized2;
	}

	public void setVisualized2(T visualized2) {
		this.visualized2 = visualized2;
	}
**/
	public AbstractXSRunnableXSVisualizableVisualizer(T visualized) {
		interaction = new StartPauseStopXSRunnableInteractionJPanel(visualized);
		container.setLayout(new BorderLayout());
		
		qualityMeasuresContainer = constructQualityMeasuresContainer(visualized);
		visualization3.setLayout(new BorderLayout());
		visualization3.add(interaction, BorderLayout.CENTER);
		visualization3.add(qualityMeasuresContainer, BorderLayout.CENTER);
		JComponent visualization1 = visualized.getVisualization().asComponent();
		JComponent visualization2 = visualized.getVisualization().asComponent2();
	
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				visualization1, visualization2);
		split.setResizeWeight(0.50d); 
		split.setEnabled(true);
		container.add(split, BorderLayout.CENTER); //position of pause stop button

		
		JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		interaction, split);
		split2.setResizeWeight(0.50d); 
		split2.setEnabled(true);
		container.add(split2, BorderLayout.CENTER);//position of model display, slider, update result button

	
	}

	@SuppressWarnings({})
	private JComponent constructQualityMeasuresContainer(T visualized) {
		JComponent qualitycontainer = new JPanel();
		qualitycontainer.setLayout(new BorderLayout());
		//implement the fitness/precision calculation
		return qualitycontainer;
	}
	
	
	
	
	
	public JComponent show() {
		return container;
	}


}
