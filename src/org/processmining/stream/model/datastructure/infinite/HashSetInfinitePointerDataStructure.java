package org.processmining.stream.model.datastructure.infinite;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.processmining.stream.model.datastructure.ItemPointerPair;
import org.processmining.stream.model.datastructure.PointerBasedDataStructure;

public class HashSetInfinitePointerDataStructure<T, P> extends HashSetInfiniteDataStructureImpl<T>
		implements PointerBasedDataStructure<T, P> {

	private final Map<T, P> pointers = new ConcurrentHashMap<>();

	public Collection<ItemPointerPair<T, P>> add(T t, P p) {
		super.add(t);
		pointers.put(t, p);
		return new HashSet<>();
	}

	public P getPointedElement(T t) {
		return pointers.get(t);
	}

}
