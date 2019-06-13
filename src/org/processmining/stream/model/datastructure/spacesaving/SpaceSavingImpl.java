package org.processmining.stream.model.datastructure.spacesaving;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.processmining.stream.model.datastructure.AbstractDataStructure;
import org.processmining.stream.model.datastructure.CounterBasedDataStructure;
import org.processmining.stream.model.datastructure.DSParameter;

/**
 * Implementation of the SpaceSaving algorithm, based on: Methods for finding
 * frequent items in data streams; Cormode et al; 2010 10.1007/s00778-009-0172-z
 *
 * Original algorithm: Efficient Computation of Frequent and Top-k Elements in
 * Data Streams; Metwally et. al.; 2005; 10.1007/978-3-540-30570-5_27
 * 
 * @author svzelst
 *
 */
public class SpaceSavingImpl<T> extends AbstractDataStructure<T, SpaceSavingParameterDefinition>
		implements CounterBasedDataStructure<T> {

	private final Map<T, Integer> map;

	private final int maxSize;

	public SpaceSavingImpl(Map<SpaceSavingParameterDefinition, DSParameter<?>> params) {
		super(params);
		this.maxSize = (Integer) params.get(SpaceSavingParameterDefinition.SIZE).getValue();
		map = new ConcurrentHashMap<>();
	}

	public Collection<T> add(T t) {
		if (map.containsKey(t)) {
			map.put(t, map.get(t) + 1);
		} else if (getSize() < getCapacity()) {
			map.put(t, 1);
		} else {
			return clean(t);
		}
		return new HashSet<>();
	}

	protected Collection<T> clean(T t) {
		int valMin = Integer.MAX_VALUE;
		T argMin = null;
		for (T key : map.keySet()) {
			if (map.get(key) < valMin) {
				argMin = key;
				valMin = map.get(key);
			}
		}
		map.remove(argMin);
		map.put(t, valMin + 1);
		return Collections.singleton(argMin);
	}

	public void clear() {
		map.clear();
	}

	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	public int getCapacity() {
		return maxSize;
	}

	public long getFrequencyOf(T e) {
		return map.get(e);
	}

	public Map<T, Integer> getMap() {
		return map;
	}

	public long getSize() {
		return map.size();
	}

	public Type getType() {
		return Type.SPACE_SAVING;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Iterator<T> iterator() {
		return map.keySet().iterator();
	}

}
