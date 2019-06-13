package org.processmining.stream.model.datastructure.slidingwindow;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.ItemPointerPair;
import org.processmining.stream.model.datastructure.PointerBasedDataStructure;

public class PointerBasedSlidingWindowImpl<T, P> extends SlidingWindowImpl<T>
		implements PointerBasedDataStructure<T, P> {

	public PointerBasedSlidingWindowImpl(Map<SlidingWindowParameterDefinition, DSParameter<?>> params) {
		super(params);
	}

	private final Map<T, P> pointers = new ConcurrentHashMap<>();

	public Collection<ItemPointerPair<T, P>> add(T t, P p) {
		Collection<ItemPointerPair<T, P>> removedPointers = new HashSet<>();
		Collection<T> removedItems = add(t);
		pointers.put(t, p);
		for (T obj : removedItems) {
			removedPointers.add(new ItemPointerPair<T, P>(obj, pointers.get(obj)));
			pointers.remove(obj);
		}
		return removedPointers;
	}

	@Override
	public void clear() {
		super.clear();
		pointers.clear();
	}

	public P getPointedElement(T t) {
		return pointers.get(t);
	}

	@Override
	/*protected void remove(T t) {
		super.remove(t);
		pointers.remove(t);
	}*/

	public Type getType() {
		return Type.SLIDING_WINDOW_POINTER;
	}

	/*@Override
	public String toString() {
		String str = "{";
		for (T elem : frequencies.keySet()) {
			str += "(" + elem.toString() + "->" + getPointedElement(elem) + ", " + frequencies.get(elem) + ", "
					+ buckets.get(elem) + "), ";
		}
		if (str.length() > 2) {
			str = str.substring(0, str.length() - 2);
		}
		str += "}";
		return str;
	}*/
	
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

}
