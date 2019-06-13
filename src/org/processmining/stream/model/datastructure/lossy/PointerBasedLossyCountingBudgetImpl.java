package org.processmining.stream.model.datastructure.lossy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.ItemPointerPair;
import org.processmining.stream.model.datastructure.PointerBasedDataStructure;

public class PointerBasedLossyCountingBudgetImpl<T, P> extends LossyCountingImpl<T>
		implements PointerBasedDataStructure<T, P> {

	public PointerBasedLossyCountingBudgetImpl(Map<LossyCountingParameterDefinition, DSParameter<?>> params) {
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
	protected void remove(T t) {
		super.remove(t);
		pointers.remove(t);
	}

	public Type getType() {
		return Type.LOSSY_COUNTING_BUDGET_POINTER;
	}

	@Override
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
	}

}
