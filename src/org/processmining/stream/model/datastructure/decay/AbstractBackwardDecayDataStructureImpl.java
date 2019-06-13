package org.processmining.stream.model.datastructure.decay;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.processmining.stream.model.datastructure.AbstractDataStructure;
import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.DSParameterDefinition;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.set.hash.THashSet;

public abstract class AbstractBackwardDecayDataStructureImpl<T, P extends DSParameterDefinition>
		extends AbstractDataStructure<T, P> implements DecayDataStructure<T> {

	protected final DecayFunction decayFunction;
	protected final TObjectIntMap<T> internalStore = new TObjectIntHashMap<T>();
	protected final int renewalRate;
	protected final double threshold;
	protected int time = 0;

	public AbstractBackwardDecayDataStructureImpl(Map<P, DSParameter<?>> params, final DecayFunction df,
			final int renewalRate, final double threshold) {
		super(params);
		this.decayFunction = df;
		this.renewalRate = renewalRate;
		this.threshold = threshold;
	}

	public boolean isEmpty() {
		return internalStore.isEmpty();
	}

	public boolean contains(Object o) {
		return internalStore.containsKey(o);
	}

	public Collection<T> add(T t) {
		time++;
		internalStore.put(t, time);
		if (time % renewalRate == 0) {
			return clean();
		}
		return new HashSet<>();
	}

	protected Collection<T> clean() {
		Set<T> remove = new THashSet<T>();
		for (T t : internalStore.keySet()) {
			int age = time - internalStore.get(t);
			if (decayFunction.evaluate(age) < threshold) {
				remove.add(t);
			}
		}
		for (T t : remove) {
			internalStore.remove(t);
		}
		return remove;
	}

	public void clear() {
		internalStore.clear();
		time = 0;
	}

	@Override
	public String toString() {
		String str = "{";
		int i = 0;
		for (T t : internalStore.keySet()) {
			str += (i == 0 ? " " : ", ");
			str += "(" + t.toString() + ", " + internalStore.get(t) + ")";
			i++;
		}
		str += " }";
		return str;
	}

	public Iterator<T> iterator() {
		return internalStore.keySet().iterator();
	}

	public Type getType() {
		return Type.BACKWARD_EXPONENTIAL_DECAY;
	}

	public long getSize() {
		return internalStore.size();
	}

	public int getCapacity() {
		return Integer.MAX_VALUE;
	}

	public long getFrequencyOf(T e) {
		if (internalStore.containsKey(e)) {
			return 1;
		} else {
			return 0;
		}
	}

	public DecayFunction getDecayFunction() {
		return decayFunction;
	}
}
