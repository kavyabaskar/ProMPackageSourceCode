package org.processmining.stream.model.datastructure.slidingwindow;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.processmining.stream.model.datastructure.AbstractDataStructure;
import org.processmining.stream.model.datastructure.DSParameter;

public class SlidingWindowImpl<T> extends AbstractDataStructure<T, SlidingWindowParameterDefinition>
		implements SlidingWindow<T> {

	protected final Deque<T> window;
	protected final int size;

	public SlidingWindowImpl(Map<SlidingWindowParameterDefinition, DSParameter<?>> parameters) {
		super(parameters);
		this.size = (Integer) getParameters().get(SlidingWindowParameterDefinition.SIZE).getValue();
		window = new ArrayDeque<T>(size);
	}

	public Iterator<T> iterator() {
		return window.iterator();
	}

	public boolean isEmpty() {
		return window.isEmpty();
	}

	public boolean contains(Object o) {
		return window.contains(o);
	}

	public Collection<T> add(T t) {
		Collection<T> removed = new HashSet<>();
		if (getSize() == size) {
			removed = Collections.singleton(window.removeFirst());
		}
		window.add(t);
		return removed;
	}

	public void clear() {
		window.clear();
	}

	@Override
	public String toString() {
		String repr = "{ ";
		int i = 0;
		for (T t : window) {
			if (i == 0) {
				repr += t.toString();
			} else {
				repr += ", " + t.toString();
			}
			i++;
		}
		repr += " }";
		return repr;
	}

	public Type getType() {
		return Type.SLIDING_WINDOW;
	}

	public long getSize() {
		return window.size();
	}

	public int getCapacity() {
		return size;
	}

	public long getFrequencyOf(T e) {
		return Collections.frequency(window, e);
	}
}