package org.processmining.stream.model.datastructure.infinite;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.processmining.stream.model.datastructure.AbstractDataStructure;
import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.IterableDataStructure;
import org.processmining.stream.model.datastructure.ParameterFreeParameterDefinition;

public class HashSetInfiniteDataStructureImpl<T> extends AbstractDataStructure<T, ParameterFreeParameterDefinition> implements IterableDataStructure<T> {

	public HashSetInfiniteDataStructureImpl() {
		super(new HashMap<ParameterFreeParameterDefinition, DSParameter<?>>());
	}

	private final HashSet<T> delegate = new HashSet<T>();

	public Collection<T> add(T t) {
		delegate.add(t);
		return new HashSet<>();
	}

	public void clear() {
		delegate.clear();
	}

	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	public int getCapacity() {
		return Integer.MAX_VALUE;
	}

	public long getFrequencyOf(T e) {
		return contains(e) ? 1 : 0;
	}

	public long getSize() {
		return delegate.size();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public Type getType() {
		return Type.INFINITE_HASH;
	}

	public Iterator<T> iterator() {
		return delegate.iterator();
	}

}
