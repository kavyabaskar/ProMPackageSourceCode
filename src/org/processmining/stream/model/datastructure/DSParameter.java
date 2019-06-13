package org.processmining.stream.model.datastructure;

public interface DSParameter<T> {

	Class<T> getParameterType();

	T getValue();

	void setValue(T value);

}
