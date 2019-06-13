package org.processmining.stream.model.datastructure;

public interface DSParameterDefinition {

	String getName();

	public Class<?> getParameterType();

	public Object getDefaultValue();

	public <P extends DSParameterDefinition> Object parse(P p, String value);

}
