package org.processmining.stream.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;

import org.processmining.framework.util.ui.widgets.ProMPropertiesPanel;
import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.DSParameterDefinition;
import org.processmining.stream.model.datastructure.DataStructure.Type;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class StreamBasedDataStructureSelector<T> extends ProMPropertiesPanel {

	private static final long serialVersionUID = -4874033865457474796L;

	private final JComboBox<Type> selector;
	private final Map<Type, StreamBasedDataStructureParametersPanel> parametersPanels = new HashMap<>();
	private final String title;

	@SuppressWarnings("unchecked")
	public StreamBasedDataStructureSelector(String title, Collection<Type> types) {
		super(title);
		this.title = title;
		selector = addComboBox("Select Memory Structure:", types);
		selector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paint();
			}
		});
		paint();
	}

	private void paint() {
		removeAll();
		add(SlickerFactory.instance().createLabel(title));
		add(selector);
		Type type = (Type) selector.getSelectedItem();
		if (!parametersPanels.containsKey(type)) {
			parametersPanels.put(type, new StreamBasedDataStructureParametersPanel(type));
		}
		//		add(SlickerFactory.instance().createLabel("Parameters for " + type.getName() + ":"));
		add(parametersPanels.get(type));
		revalidate();
		repaint();
	}

	public Type getDataStructureType1() {
		return (Type) selector.getSelectedItem();
	}

	public Map<DSParameterDefinition, DSParameter<?>> getDataStructureParameterMapping1() {
		return parametersPanels.get(getDataStructureType1()).convertUserInput();
	}
	
	//for 2nd miner
	public Type getDataStructureType2() {
		return (Type) selector.getSelectedItem();
	}

	public Map<DSParameterDefinition, DSParameter<?>> getDataStructureParameterMapping2() {
		return parametersPanels.get(getDataStructureType2()).convertUserInput();
	}
	//
}
