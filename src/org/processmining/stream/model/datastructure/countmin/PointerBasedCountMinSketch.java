package org.processmining.stream.model.datastructure.countmin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.ItemPointerPair;
import org.processmining.stream.model.datastructure.PointerBasedDataStructure;

public class PointerBasedCountMinSketch<T, P> extends CountMinSketchImpl<T> implements PointerBasedDataStructure<T, P> {

	private final Object[][] pointers;

	public PointerBasedCountMinSketch(Map<CountMinSketchParameterDefinition, DSParameter<?>> params) {
		super(params);
		pointers = new Object[getDepth()][getWidth()];
	}

	public Collection<ItemPointerPair<T, P>> add(T t, P p) {
		int[] buckets = CountMinUtils.getHashBuckets(t.toString(), getDepth(), getWidth());
		for (int i = 0; i < getDepth(); ++i) {
			int cell = buckets[i];
			getTable()[i][cell] += 1;
			pointers[i][cell] = p;
		}
		setSize(getSize() + 1);
		// nothing will be removed in the countmin sketch.
		return new HashSet<>();
	}

	@SuppressWarnings("unchecked")
	public P getPointedElement(T t) {
		long res = Long.MAX_VALUE;
		int rowMin = -1;
		int colMin = -1;
		int[] buckets = CountMinUtils.getHashBuckets(t.toString(), getDepth(), getWidth());
		for (int i = 0; i < getDepth(); ++i) {
			long val = getTable()[i][buckets[i]];
			if (res > val) {
				res = val;
				rowMin = i;
				colMin = buckets[i];
			}
		}
		return (P) pointers[rowMin][colMin];
	}

	@Override
	public Type getType() {
		return Type.COUNT_MIN_SKETCH_POINTER;
	}

}
