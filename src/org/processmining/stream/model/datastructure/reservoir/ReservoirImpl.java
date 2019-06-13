package org.processmining.stream.model.datastructure.reservoir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.processmining.stream.model.datastructure.AbstractDataStructure;
import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.DSParameterFactory;

public class ReservoirImpl<T> extends AbstractDataStructure<T, ReservoirParameterDefinition> implements Reservoir<T> {

	protected final ArrayList<T> internalStore;
	protected final int maxSize;
	protected int members = 0;
	protected int totalElements = 0;

	protected final Random rGen;

	public ReservoirImpl(Map<ReservoirParameterDefinition, DSParameter<?>> params) {
		this(params, (Integer) params.get(ReservoirParameterDefinition.MAX_SIZE).getValue(),
				(Long) params.get(ReservoirParameterDefinition.SEED).getValue());
	}

	private ReservoirImpl(Map<ReservoirParameterDefinition, DSParameter<?>> params, int maxSize, long seed) {
		super(params);
		for (ReservoirParameterDefinition p : EnumSet.allOf(ReservoirParameterDefinition.class)) {
			getParameters().put(p, DSParameterFactory.createParameter(p.getParameterType().cast(p.getDefaultValue())));
		}
		getParameters().put(ReservoirParameterDefinition.MAX_SIZE, DSParameterFactory.createParameter(maxSize));
		getParameters().put(ReservoirParameterDefinition.SEED, DSParameterFactory.createParameter(seed));
		this.internalStore = new ArrayList<T>(maxSize);
		this.maxSize = maxSize;
		this.rGen = new Random(seed);
	}

	//	public ReservoirImpl(Map<String, Double> parameters, long seed) {
	//		this(parameters.get(Parameters.SIZE).intValue(), seed);
	//	}

	/**
	 * if we did not yet filled all spots, the element is always included. if
	 * the reservoir is full and the (t+1)th data point comes in, it is included
	 * with probability n / t + 1.
	 * 
	 * @return
	 * 
	 */
	public Collection<T> add(T e) {
		if (totalElements < maxSize) {
			internalStore.add(e);
			members++;
			totalElements++;
			return new HashSet<>();
		} else {
			totalElements++;
			boolean include = rGen.nextInt(totalElements) < maxSize;
			if (include) {
				int newIndex = rGen.nextInt(maxSize);
				Collection<T> removed = Collections.singleton(internalStore.get(newIndex));
				internalStore.set(newIndex, e);
				return removed;
			}
			return new HashSet<>();
		}
	}

	public void clear() {
		internalStore.clear();
		members = 0;
	}

	public boolean contains(Object o) {
		return internalStore.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		boolean result = true;
		for (Object o : c) {
			if (!contains(o)) {
				result = false;
				break;
			}
		}
		return result;
	}

	public int indexOf(T t) {
		return internalStore.indexOf(t);
	}

	public boolean isEmpty() {
		return internalStore.isEmpty();
	}

	public Iterator<T> iterator() {
		return internalStore.iterator();
	}

	public void set(int i, T t) {
		if (i < members) {
			internalStore.set(i, t);
		}
	}

	@Override
	public String toString() {
		String repr = "{ ";
		for (int i = 0; i < members; i++) {
			if (i == 0) {
				repr += internalStore.get(i);
			} else {
				repr += ", " + internalStore.get(i);
			}
		}
		repr += " }";
		return repr;
	}

	public Type getType() {
		return Type.RESERVOIR;

	}

	public long getSize() {
		return members;
	}

	public int getCapacity() {
		return maxSize;
	}

	public long getFrequencyOf(T e) {
		return Collections.frequency(internalStore, e);
	}
}
