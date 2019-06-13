package org.processmining.stream.model.datastructure.countmin;

import org.processmining.stream.model.datastructure.DSParameterDefinition;

/**
 * Parameters of the count min sketch. From: Methods for finding frequent items
 * in data streams; Cormode et al; 2010; 10.1007/s00778-009-0172-z, page 9
 * (freely translated to javadoc readable language...):
 * 
 * "So setting d (== number of hashes) to log(1/error_prob) and w (== hash
 * range) to O(1/error_margin) ensures that the estimator of an element has
 * error at most error_margin * N (== stream size) with probability at least 1 -
 * error prob.
 * 
 * @author svzelst
 *
 */
public enum CountMinSketchParameterDefinition implements DSParameterDefinition {
	CONFIDENCE("Confidence", Double.class, 0.99), ERROR_MARGIN("Error Margin", Double.class,
			0.01), SEED("Seed", Integer.class, Integer.MAX_VALUE);

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

	private <T> CountMinSketchParameterDefinition(String name, Class<T> clazz, T defaultVal) {
		this.name = name;
		this.clazz = clazz;
		this.defaultVal = defaultVal;
	}

	public <P extends DSParameterDefinition> Object parse(P p, String value) {
		assert (p instanceof CountMinSketchParameterDefinition);
		switch ((CountMinSketchParameterDefinition) p) {
			case CONFIDENCE :
				return Double.parseDouble(value);
			case ERROR_MARGIN :
				return Double.parseDouble(value);
			case SEED :
				return Integer.parseInt(value);
		}
		return value;
	}

}
