package org.processmining.stream.model.datastructure.decay;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.DSParameterDefinition;
import org.processmining.stream.model.datastructure.ItemPointerPair;
import org.processmining.stream.model.datastructure.PointerBasedDataStructure;

public abstract class AbstractPointerBasedBackwardDecayDataStructure<T, PO, PA extends DSParameterDefinition>
		extends AbstractBackwardDecayDataStructureImpl<T, PA> implements PointerBasedDataStructure<T, PO> {

	private final Map<T, PO> pointers = new ConcurrentHashMap<>();

	public AbstractPointerBasedBackwardDecayDataStructure(Map<PA, DSParameter<?>> params, DecayFunction df,
			int renewalRate, double threshold) {
		super(params, df, renewalRate, threshold);
	}

	public Collection<ItemPointerPair<T, PO>> add(T t, PO p) {
		Collection<T> removedObjects = add(t);
		Collection<ItemPointerPair<T, PO>> removedObjectsAndPointers = new HashSet<>();
		for (T obj : removedObjects) {
			removedObjectsAndPointers.add(new ItemPointerPair<T, PO>(obj, pointers.get(obj)));
			if (!contains(obj)) {
				pointers.remove(obj);
			}
		}
		if (contains(t)) {
			pointers.put(t, p);
		}
		return removedObjectsAndPointers;
	}

	public PO getPointedElement(T t) {
		return pointers.get(t);
	}

}
