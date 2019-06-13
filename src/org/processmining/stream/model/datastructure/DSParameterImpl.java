package org.processmining.stream.model.datastructure;

public class DSParameterImpl<T> implements DSParameter<T> {

	private final Class<T> clazz;

	private T value;

	@SuppressWarnings("unchecked")
	public DSParameterImpl(T value) {
		clazz = (Class<T>) value.getClass();
		setValue(value);

	}

	public Class<T> getParameterType() {
		return clazz;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
