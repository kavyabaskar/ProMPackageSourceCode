package org.processmining.stream.model.datastructure;

public class ParameterFreeParameterDefinition implements DSParameterDefinition {

	public String getName() {
		return "param_free";
	}

	public Class<?> getParameterType() {
		return this.getClass();
	}

	public Object getDefaultValue() {
		return new Object();
	}

	public <P extends DSParameterDefinition> Object parse(P p, String value) {
		return new Object();
	}

}
