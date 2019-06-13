package org.processmining.stream.model.datastructure.spacesaving;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.ItemPointerPair;
import org.processmining.stream.model.datastructure.PointerBasedDataStructure;

public class PointerBasedSpaceSavingImpl<T, P> extends SpaceSavingImpl<T> implements PointerBasedDataStructure<T, P> {

	private final Map<T, P> pointers = new ConcurrentHashMap<>();

	public PointerBasedSpaceSavingImpl(Map<SpaceSavingParameterDefinition, DSParameter<?>> params) {
		super(params);
	}

	public Collection<ItemPointerPair<T, P>> add(T t, P p) {
		Collection<ItemPointerPair<T, P>> removedPointers = new HashSet<>();
		Collection<T> removedItems = super.add(t);
		pointers.put(t, p);
		for (T item : removedItems) {
			removedPointers.add(new ItemPointerPair<T, P>(item, pointers.get(item)));
			pointers.remove(item);
		}
		return removedPointers;
	}

	public P getPointedElement(T t) {
		return pointers.get(t);
	}

	@Override
	public void clear() {
		super.clear();
		pointers.clear();
	}
	
	public Type getType() {
		return Type.SPACE_SAVING_POINTER;
	}

}
