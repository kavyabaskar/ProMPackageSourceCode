package org.processmining.stream.model.datastructure.decay;

import org.processmining.stream.model.datastructure.DSParameterDefinition;

public enum BackwardExponentialDecayParameterDefinition implements DSParameterDefinition {

	RENEWAL_RATE("Renewal rate", Integer.class, 25), THRESHOLD("Threshold", Double.class, 0.15), BASE("Base",
			Double.class, 1d), DECAY_RATE("Decay Rate", Double.class, 0.25d);

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

	private <T> BackwardExponentialDecayParameterDefinition(String name, Class<T> clazz, T defaultVal) {
		this.name = name;
		this.clazz = clazz;
		this.defaultVal = defaultVal;
	}

	public <P extends DSParameterDefinition> Object parse(P p, String value) {
		assert (p instanceof BackwardExponentialDecayParameterDefinition);
		switch ((BackwardExponentialDecayParameterDefinition) p) {
			case BASE :
			case DECAY_RATE :
			case THRESHOLD :
				return Double.parseDouble(value);
			case RENEWAL_RATE :
				return Integer.parseInt(value);
		}
		return value;
	}

}
