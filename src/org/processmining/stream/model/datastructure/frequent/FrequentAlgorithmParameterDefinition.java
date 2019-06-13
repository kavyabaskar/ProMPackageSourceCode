package org.processmining.stream.model.datastructure.frequent;

import org.processmining.stream.model.datastructure.DSParameterDefinition;

public enum FrequentAlgorithmParameterDefinition implements DSParameterDefinition {
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

	private <T> FrequentAlgorithmParameterDefinition(String name, Class<T> clazz, T defaultVal) {
		this.name = name;
		this.clazz = clazz;
		this.defaultVal = defaultVal;
	}

	public <P extends DSParameterDefinition> Object parse(P p, String value) {
		assert (p instanceof FrequentAlgorithmParameterDefinition);
		switch ((FrequentAlgorithmParameterDefinition) p) {
			case SIZE :
				return Integer.parseInt(value);
		}
		return value;
	}
}
