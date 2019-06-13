package org.processmining.stream.dialog;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import org.processmining.framework.util.ui.widgets.ProMPropertiesPanel;
import org.processmining.framework.util.ui.widgets.ProMTextField;
import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.DSParameterDefinition;
import org.processmining.stream.model.datastructure.DSParameterFactory;
import org.processmining.stream.model.datastructure.DataStructure.Type;

public class StreamBasedDataStructureParametersPanel extends ProMPropertiesPanel {

	private static final long serialVersionUID = 5422540973044323839L;
	private final Map<DSParameterDefinition, ProMTextField> parametersUserInput = new HashMap<>();

	public StreamBasedDataStructureParametersPanel(Type type) {
		super("");
		setMinimumSize(new Dimension(100, 150));
		setupParameterFields(type);
	}

	public void setupParameterFields(Type type) {
		for (DSParameterDefinition p : type.getParameterDefinition()) {
			ProMTextField tf = addTextField(p.getName(), p.getDefaultValue().toString());
			tf.setMinimumSize(new Dimension(100, 30));
			tf.setPreferredSize(new Dimension(100, 30));
			parametersUserInput.put(p, tf);
		}
	}

	public Map<DSParameterDefinition, ProMTextField> getUserInputPerParameterKey() {
		return parametersUserInput;
	}

	public Map<DSParameterDefinition, DSParameter<?>> convertUserInput() {
		Map<DSParameterDefinition, DSParameter<?>> result = new HashMap<>();
		for (Map.Entry<DSParameterDefinition, ProMTextField> me : getUserInputPerParameterKey().entrySet()) {
			DSParameterDefinition parameter = me.getKey();
			String userValue = me.getValue().getText();
			DSParameter<?> paramInstance = DSParameterFactory
					.createParameter(parameter.getParameterType().cast(parameter.parse(parameter, userValue)));
			result.put(parameter, paramInstance);
		}
		return result;
	}

}
