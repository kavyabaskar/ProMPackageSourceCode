package org.processmining.stream.model.datastructure;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.processmining.stream.model.datastructure.countmin.CountMinSketchParameterDefinition;
import org.processmining.stream.model.datastructure.decay.BackwardExponentialDecayParameterDefinition;
import org.processmining.stream.model.datastructure.decay.ForwardExponentialDecayParameterDefinition;
import org.processmining.stream.model.datastructure.frequent.FrequentAlgorithmParameterDefinition;
import org.processmining.stream.model.datastructure.lossy.LossyCountingParameterDefinition;
import org.processmining.stream.model.datastructure.reservoir.ReservoirParameterDefinition;
import org.processmining.stream.model.datastructure.slidingwindow.SlidingWindowParameterDefinition;
import org.processmining.stream.model.datastructure.spacesaving.SpaceSavingParameterDefinition;

/**
 * A stream based data structure is an encapsulating entity for all "data stores"
 * that we connect to a stream. It acts as a parent for more advanced structures
 * such as a lossy counting based data store, a reservoir sampling based data
 * store etc. Note that it in a sense is a "stripped" version of the Set
 * <E> interface, though it only admits basic "fast" operations/queries.
 * 
 *
 * @param <T>
 *            generic type parameter
 */
public interface DataStructure<T> {

	public enum Type {
		COUNT_MIN_SKETCH("Count Min Sketch", EnumSet.allOf(CountMinSketchParameterDefinition.class), true, false, false), 
		COUNT_MIN_SKETCH_POINTER("Count Min Sketch (Pointer)", EnumSet.allOf(CountMinSketchParameterDefinition.class), true, false, true), 
		BACKWARD_EXPONENTIAL_DECAY("Backward Exponential Decay", EnumSet.allOf(BackwardExponentialDecayParameterDefinition.class), true, true, false), 
		BACKWARD_EXPONENTIAL_DECAY_POINTER("Backward Exponential Decay (Pointer)", EnumSet.allOf(BackwardExponentialDecayParameterDefinition.class), true, true, true),
		FORWARD_EXPONENTIAL_DECAY("Forward Exponential Decay",EnumSet.allOf(ForwardExponentialDecayParameterDefinition.class), true, true,false),
		FORWARD_EXPONENTIAL_DECAY_POINTER("Forward Exponential Decay (Pointer)", EnumSet.allOf(ForwardExponentialDecayParameterDefinition.class), true, true, true),
		FREQUENT_ALGORITHM("Frequent",EnumSet.allOf(FrequentAlgorithmParameterDefinition.class), true, true,false), 
		FREQUENT_POINTER("Frequent (Pointer)",EnumSet.allOf(FrequentAlgorithmParameterDefinition.class), true,true, true),
		INFINITE_HASH("Infinite (HashSet)",Collections.singleton(new ParameterFreeParameterDefinition()),false, true, false), 
		INFINITE_HASH_POINTER("Infinite (Pointer, HashSet)",Collections.singleton(new ParameterFreeParameterDefinition()),false, true,true), 
		LOSSY_COUNTING("Lossy Counting",EnumSet.allOf(LossyCountingParameterDefinition.class),true, true,false), 
		LOSSY_COUNTING_BUDGET("Lossy Counting with Budget",EnumSet.allOf(LossyCountingParameterDefinition.class),true, true,false), 
		LOSSY_COUNTING_POINTER("Lossy Counting (Pointer)",EnumSet.allOf(LossyCountingParameterDefinition.class),true, true,true), 
		LOSSY_COUNTING_BUDGET_POINTER("Lossy Counting with Budget (Pointer)",EnumSet.allOf(LossyCountingParameterDefinition.class),true, true,true),
		RESERVOIR("Reservoir Sampling",EnumSet.allOf(ReservoirParameterDefinition.class),true, true,false),
		SLIDING_WINDOW("Sliding Window",EnumSet.allOf(SlidingWindowParameterDefinition.class),true,true,false), 
		SLIDING_WINDOW_POINTER("Sliding Window (Pointer)",EnumSet.allOf(SlidingWindowParameterDefinition.class),true,true,true),
		SPACE_SAVING("Space Saving",EnumSet.allOf(SpaceSavingParameterDefinition.class),true,true,false), 
		SPACE_SAVING_POINTER("Space Saving (Pointer)",EnumSet.allOf(SpaceSavingParameterDefinition.class),true,true,true);

		private final Collection<? extends DSParameterDefinition> dSParameterDefinition;
		private final boolean iterable;
		private final String name;
		private final boolean supportsPointer;
		private final boolean finite;

		private Type(String name, Collection<? extends DSParameterDefinition> paramDef, boolean finite,
				boolean iterable, boolean supportsPointer) {
			this.name = name;
			this.dSParameterDefinition = paramDef;
			this.finite = finite;
			this.iterable = iterable;
			this.supportsPointer = supportsPointer;
		}

		@Deprecated // -> use DSParameterFactory
		public Map<DSParameterDefinition, DSParameter<?>> getDefaultParameterMap() {
			Map<DSParameterDefinition, DSParameter<?>> def = new HashMap<>();
			for (DSParameterDefinition d : getParameterDefinition()) {
				def.put(d, DSParameterFactory
						.createParameter(d.getParameterType().cast(d.parse(d, d.getDefaultValue().toString()))));
			}
			return def;
		}

		public String getName() {
			return name;
		}

		public Collection<? extends DSParameterDefinition> getParameterDefinition() {
			return dSParameterDefinition;
		}

		public boolean isIterable() {
			return iterable;
		}

		public boolean supportsPointer() {
			return supportsPointer;
		}

		public boolean isFinite() {
			return finite;
		}

		@Override
		public String toString() {
			return getName();
		}
	}

	public static Collection<Type> INFIITE_DATA_STRUCTURES = EnumSet.of(Type.INFINITE_HASH, Type.INFINITE_HASH_POINTER);

	public static Collection<Type> ITERABLE_DATA_STRUCTURES = EnumSet.of(Type.BACKWARD_EXPONENTIAL_DECAY,
			Type.FREQUENT_ALGORITHM, Type.FREQUENT_POINTER, Type.INFINITE_HASH, Type.LOSSY_COUNTING,
			Type.LOSSY_COUNTING_BUDGET, Type.LOSSY_COUNTING_POINTER, Type.RESERVOIR, Type.SLIDING_WINDOW,
			Type.SPACE_SAVING, Type.SPACE_SAVING_POINTER, Type.FORWARD_EXPONENTIAL_DECAY, Type.FORWARD_EXPONENTIAL_DECAY_POINTER);

	public static Collection<Type> FINITE_ITERABLE_DATA_STRUCTURES = EnumSet.of(Type.BACKWARD_EXPONENTIAL_DECAY,
			Type.FREQUENT_ALGORITHM, Type.FREQUENT_POINTER, Type.LOSSY_COUNTING, Type.LOSSY_COUNTING_BUDGET,
			Type.LOSSY_COUNTING_POINTER, Type.RESERVOIR, Type.SLIDING_WINDOW, Type.SPACE_SAVING,
			Type.SPACE_SAVING_POINTER, Type.FORWARD_EXPONENTIAL_DECAY, Type.FORWARD_EXPONENTIAL_DECAY_POINTER);

	public static Collection<Type> POINTER_DATA_STRUCTURES = EnumSet.of(Type.COUNT_MIN_SKETCH_POINTER,
			Type.FREQUENT_POINTER, Type.INFINITE_HASH_POINTER, Type.LOSSY_COUNTING_POINTER, Type.SPACE_SAVING_POINTER, Type.FORWARD_EXPONENTIAL_DECAY_POINTER);

	public static Collection<Type> FINITE_POINTER_DATA_STRUCTURES = EnumSet.of(Type.COUNT_MIN_SKETCH_POINTER,
			Type.FREQUENT_POINTER, Type.LOSSY_COUNTING_POINTER, Type.SPACE_SAVING_POINTER, Type.FORWARD_EXPONENTIAL_DECAY_POINTER);

	public static Collection<Type> FINITE_ITERABLE_POINTER_DATA_STRUCTURES = EnumSet.of(Type.FREQUENT_POINTER,
			Type.LOSSY_COUNTING_POINTER, Type.SPACE_SAVING_POINTER, Type.FORWARD_EXPONENTIAL_DECAY_POINTER);

	/**
	 * add elements to a stream based data structure may lead to removal of
	 * other elements in the data structure If possible, the implementing
	 * algorithm will return a collection of elements.
	 * 
	 * @param t
	 * @return
	 */
	Collection<T> add(T t);

	void clear();

	boolean contains(Object o);

	int getCapacity();

	long getFrequencyOf(T e);

	<P extends DSParameterDefinition> Map<P, DSParameter<?>> getParameters();

	long getSize();

	Type getType();

	boolean isEmpty();
	
	long getUsedMemoryInBytes();

}
