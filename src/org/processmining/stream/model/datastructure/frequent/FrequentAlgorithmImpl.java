package org.processmining.stream.model.datastructure.frequent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.processmining.stream.model.datastructure.AbstractDataStructure;
import org.processmining.stream.model.datastructure.CounterBasedDataStructure;
import org.processmining.stream.model.datastructure.DSParameter;

/**
 * Stream based implementation of the "Frequent Algorithm" Implementation based
 * on: Methods for finding frequent items in data streams; Cormode et al; 2010
 * 10.1007/s00778-009-0172-z
 * 
 * Original algorithm described in: A Simple Algorithm for Finding Frequent
 * Elements in Streams and Bags; Karp et al.; 2003 10.1145/762471.762473
 * 
 * @author svzelst
 *
 */
public class FrequentAlgorithmImpl<T> extends AbstractDataStructure<T, FrequentAlgorithmParameterDefinition>
		implements CounterBasedDataStructure<T> {

	private final int maxSize;
	private final Map<T, Long> map;

	protected Map<T, Long> getMap() {
		return map;
	}

	public FrequentAlgorithmImpl(Map<FrequentAlgorithmParameterDefinition, DSParameter<?>> params) {
		super(params);
		this.maxSize = (Integer) params.get(FrequentAlgorithmParameterDefinition.SIZE).getValue();
		map = new ConcurrentHashMap<>(maxSize);
	}

	public Iterator<T> iterator() {
		return map.keySet().iterator();
	}

	public Collection<T> add(T t) {
		if (map.containsKey(t)) {
			map.put(t, map.get(t) + 1);
		} else if (getSize() < getCapacity() - 1) {
			map.put(t, 1l);
		} else {
			return clean();
		}
		return new HashSet<>();
	}

	protected Collection<T> clean() {
		Collection<T> removed = new HashSet<>();
		for (T key : map.keySet()) {
			long val = map.get(key) - 1;
			if (val == 0) {
				map.remove(key);
				removed.add(key);
			} else {
				map.put(key, val);
			}
		}
		return removed;
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

	public long getSize() {
		return map.size();
	}

	public Type getType() {
		return Type.FREQUENT_ALGORITHM;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public String toString() {
		String str = "{";
		for (T elem : map.keySet()) {
			str += "(" + elem.toString() + ", " + getFrequencyOf(elem) + "), ";
		}
		if (str.length() > 2) {
			str = str.substring(0, str.length() - 2);
		}
		str += "}";
		return str;
	}

}
