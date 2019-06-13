package org.processmining.stream.model.datastructure.reservoir;

import org.processmining.stream.model.datastructure.DSParameterDefinition;

public enum ReservoirParameterDefinition implements DSParameterDefinition {
	MAX_SIZE("Maximal Size", Integer.class, 1000), SEED("Seed", Long.class, 1337l);

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

	private <T> ReservoirParameterDefinition(String name, Class<T> clazz, T defaultVal) {
		this.name = name;
		this.clazz = clazz;
		this.defaultVal = defaultVal;
	}

	public <P extends DSParameterDefinition> Object parse(P p, String value) {
		assert (p instanceof ReservoirParameterDefinition);
		switch ((ReservoirParameterDefinition) p) {
			case MAX_SIZE :
				return Integer.valueOf(value);
			case SEED :
				return Long.valueOf(value);
		}
		return value;
	}

}
