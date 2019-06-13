package org.processmining.stream.model.datastructure.spacesaving;

import org.processmining.stream.model.datastructure.DSParameterDefinition;

public enum SpaceSavingParameterDefinition implements DSParameterDefinition {
	SIZE("Size", Integer.class, 1000);

	private final String name;
	private final Class<?> clazz;

	public String getName() {
		return name;
	}

	public Class<?> getParameterType() {
		return clazz;
	}

	public Object getDefaultValue() {
		return defaultVal;
	}

	private final Object defaultVal;

	private <T> SpaceSavingParameterDefinition(String name, Class<T> clazz, T defaultVal) {
		this.name = name;
		this.clazz = clazz;
		this.defaultVal = defaultVal;
	}

	public <P extends DSParameterDefinition> Object parse(P p, String value) {
		assert (p instanceof SpaceSavingParameterDefinition);
		switch ((SpaceSavingParameterDefinition) p) {
			case SIZE :
				return Integer.parseInt(value);
		}
		return value;
	}
}
