package org.processmining.stream.model.datastructure.frequent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.ItemPointerPair;
import org.processmining.stream.model.datastructure.PointerBasedDataStructure;

public class PointerBasedFrequentAlgorithmImpl<T, P> extends FrequentAlgorithmImpl<T>
		implements PointerBasedDataStructure<T, P> {

	private final Map<T, P> pointers = new ConcurrentHashMap<>();

	public PointerBasedFrequentAlgorithmImpl(Map<FrequentAlgorithmParameterDefinition, DSParameter<?>> params) {
		super(params);
	}

	public Collection<ItemPointerPair<T, P>> add(T t, P p) {
		Collection<T> removedItems = super.add(t);
		Collection<ItemPointerPair<T, P>> removedPairs = new HashSet<ItemPointerPair<T, P>>();
		for (T obj : removedItems) {
			removedPairs.add(new ItemPointerPair<T, P>(obj, pointers.get(obj)));
			pointers.remove(obj);
		}
		if (super.contains(t)) {
			pointers.put(t, p);
		}
		return removedPairs;
	}

	public P getPointedElement(T t) {
		return pointers.get(t);
	}

	@Override
	public String toString() {
		String str = "{";
		for (T elem : getMap().keySet()) {
			str += "(" + elem.toString() + "->" + pointers.get(elem) + ", " + getFrequencyOf(elem) + "), ";
		}
		if (str.length() > 2) {
			str = str.substring(0, str.length() - 2);
		}
		str += "}";
		return str;
	}

	@Override
	public Type getType() {
		return Type.FREQUENT_POINTER;
	}

}
