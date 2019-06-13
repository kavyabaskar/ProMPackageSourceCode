package org.processmining.stream.model.datastructure.reservoir;

import org.processmining.stream.model.datastructure.IterableDataStructure;

public interface Reservoir<T> extends IterableDataStructure<T> {

	public int indexOf(T t);

	public void set(int i, T t);

}
