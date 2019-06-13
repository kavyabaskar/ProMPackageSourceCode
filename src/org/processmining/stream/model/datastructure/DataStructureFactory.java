package org.processmining.stream.model.datastructure;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import javax.naming.OperationNotSupportedException;

import org.processmining.stream.model.datastructure.DataStructure.Type;
import org.processmining.stream.model.datastructure.countmin.CountMinSketchImpl;
import org.processmining.stream.model.datastructure.countmin.CountMinSketchParameterDefinition;
import org.processmining.stream.model.datastructure.countmin.PointerBasedCountMinSketch;
import org.processmining.stream.model.datastructure.decay.BackwardExponentialDecayDataStructureImpl;
import org.processmining.stream.model.datastructure.decay.BackwardExponentialDecayParameterDefinition;
import org.processmining.stream.model.datastructure.decay.ForwardExponentialDecayDataStructureImpl;
import org.processmining.stream.model.datastructure.decay.ForwardExponentialDecayParameterDefinition;
import org.processmining.stream.model.datastructure.decay.PointerBasedBackwardExponentialDecayDataStructureImpl;
import org.processmining.stream.model.datastructure.decay.PointerBasedForwardExponentialDecayDataStructureImpl;
import org.processmining.stream.model.datastructure.frequent.FrequentAlgorithmImpl;
import org.processmining.stream.model.datastructure.frequent.FrequentAlgorithmParameterDefinition;
import org.processmining.stream.model.datastructure.frequent.PointerBasedFrequentAlgorithmImpl;
import org.processmining.stream.model.datastructure.infinite.HashSetInfiniteDataStructureImpl;
import org.processmining.stream.model.datastructure.infinite.HashSetInfinitePointerDataStructure;
import org.processmining.stream.model.datastructure.lossy.LossyCountingBudgetImpl;
import org.processmining.stream.model.datastructure.lossy.LossyCountingImpl;
import org.processmining.stream.model.datastructure.lossy.LossyCountingParameterDefinition;
import org.processmining.stream.model.datastructure.lossy.PointerBasedLossyCountingBudgetImpl;
import org.processmining.stream.model.datastructure.lossy.PointerBasedLossyCountingImpl;
import org.processmining.stream.model.datastructure.reservoir.ReservoirImpl;
import org.processmining.stream.model.datastructure.reservoir.ReservoirParameterDefinition;
import org.processmining.stream.model.datastructure.slidingwindow.PointerBasedSlidingWindowImpl;
import org.processmining.stream.model.datastructure.slidingwindow.SlidingWindowImpl;
import org.processmining.stream.model.datastructure.slidingwindow.SlidingWindowParameterDefinition;
import org.processmining.stream.model.datastructure.spacesaving.PointerBasedSpaceSavingImpl;
import org.processmining.stream.model.datastructure.spacesaving.SpaceSavingImpl;
import org.processmining.stream.model.datastructure.spacesaving.SpaceSavingParameterDefinition;

public class DataStructureFactory {

	@SuppressWarnings({ "unchecked" })
	public static <T, PA extends DSParameterDefinition, PO> IterableDataStructure<T> createIterableDataStructure(
			Type type, Map<PA, DSParameter<?>> parameters)
			throws DSParameterMissingException, DSWrongParameterException, OperationNotSupportedException {
		switch (type) {
			case BACKWARD_EXPONENTIAL_DECAY :
				assert (type.isIterable());
				checkBackwardExponentialDecayParameters(parameters);
				return new BackwardExponentialDecayDataStructureImpl<T>(
						(Map<BackwardExponentialDecayParameterDefinition, DSParameter<?>>) parameters);
			case RESERVOIR :
				assert (type.isIterable());
				checkReservoirParameters(parameters);
				return new ReservoirImpl<T>((Map<ReservoirParameterDefinition, DSParameter<?>>) parameters);
			case SLIDING_WINDOW :
				assert (type.isIterable());
				checkSlidingWindowParameters(parameters);
				return new SlidingWindowImpl<T>((Map<SlidingWindowParameterDefinition, DSParameter<?>>) parameters);
			case SLIDING_WINDOW_POINTER :
				assert (type.isIterable());
				checkSlidingWindowParameters(parameters);
				return new PointerBasedSlidingWindowImpl<T, PO>(
						(Map<SlidingWindowParameterDefinition, DSParameter<?>>) parameters);
			case FREQUENT_ALGORITHM :
				assert (type.isIterable());
				checkFrequentAlgorithmParameters(parameters);
				return new FrequentAlgorithmImpl<T>(
						(Map<FrequentAlgorithmParameterDefinition, DSParameter<?>>) parameters);
			case SPACE_SAVING :
				assert (type.isIterable());
				checkSpaceSavingParameters(parameters);
				return new SpaceSavingImpl<T>((Map<SpaceSavingParameterDefinition, DSParameter<?>>) parameters);
			case FREQUENT_POINTER :
				assert (type.isIterable());
				checkFrequentAlgorithmParameters(parameters);
				return new PointerBasedFrequentAlgorithmImpl<T, PO>(
						(Map<FrequentAlgorithmParameterDefinition, DSParameter<?>>) parameters);
			case SPACE_SAVING_POINTER :
				assert (type.isIterable());
				checkSpaceSavingParameters(parameters);
				return new PointerBasedSpaceSavingImpl<T, PO>(
						(Map<SpaceSavingParameterDefinition, DSParameter<?>>) parameters);
			case LOSSY_COUNTING :
				assert (type.isIterable());
				checkLossyCountingParameters(parameters);
				return new LossyCountingImpl<T>((Map<LossyCountingParameterDefinition, DSParameter<?>>) parameters);
			case LOSSY_COUNTING_BUDGET :
				assert (type.isIterable());
				checkLossyCountingParameters(parameters);
				return new LossyCountingBudgetImpl<T>(
						(Map<LossyCountingParameterDefinition, DSParameter<?>>) parameters);
			case LOSSY_COUNTING_POINTER :
				assert (type.isIterable());
				checkLossyCountingParameters(parameters);
				return new PointerBasedLossyCountingImpl<T, PO>(
						(Map<LossyCountingParameterDefinition, DSParameter<?>>) parameters);
			case LOSSY_COUNTING_BUDGET_POINTER :
				assert (type.isIterable());
				checkLossyCountingParameters(parameters);
				return new PointerBasedLossyCountingBudgetImpl<T, PO>(
						(Map<LossyCountingParameterDefinition, DSParameter<?>>) parameters);
			case COUNT_MIN_SKETCH :
			case COUNT_MIN_SKETCH_POINTER :
				throw new OperationNotSupportedException();
			case INFINITE_HASH :
				assert (type.isIterable());
				return new HashSetInfiniteDataStructureImpl<T>();
			case INFINITE_HASH_POINTER :
				assert (type.isIterable());
				return new HashSetInfinitePointerDataStructure<T, PO>();
			case FORWARD_EXPONENTIAL_DECAY :
				assert (type.isIterable());
				checkForwardExponentialDecayParameters(parameters);
				return new ForwardExponentialDecayDataStructureImpl<T>(
						(Map<ForwardExponentialDecayParameterDefinition, DSParameter<?>>) parameters);
			case FORWARD_EXPONENTIAL_DECAY_POINTER :
				assert (type.isIterable());
				checkForwardExponentialDecayParameters(parameters);
				return new PointerBasedForwardExponentialDecayDataStructureImpl<T, PO>(
						(Map<ForwardExponentialDecayParameterDefinition, DSParameter<?>>) parameters);
			case BACKWARD_EXPONENTIAL_DECAY_POINTER :
				assert (type.isIterable());
				checkBackwardExponentialDecayParameters(parameters);
				return new PointerBasedBackwardExponentialDecayDataStructureImpl<T, PO>(
						(Map<BackwardExponentialDecayParameterDefinition, DSParameter<?>>) parameters);
			default :
				break;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T, PA extends DSParameterDefinition, PO> PointerBasedDataStructure<T, PO> createPointerDataStructure(
			Type type, Map<PA, DSParameter<?>> parameters)
			throws DSParameterMissingException, DSWrongParameterException, OperationNotSupportedException {
		switch (type) {
			case BACKWARD_EXPONENTIAL_DECAY :
			case FREQUENT_ALGORITHM :
			case RESERVOIR :
			case SLIDING_WINDOW :
			case SPACE_SAVING :
			case COUNT_MIN_SKETCH :
			case LOSSY_COUNTING :
			case INFINITE_HASH :
			case LOSSY_COUNTING_BUDGET :
			case FORWARD_EXPONENTIAL_DECAY :
				throw new OperationNotSupportedException(
						"This data structure does not support a pointer based data structure");
			case FREQUENT_POINTER :
				assert (type.supportsPointer());
				checkFrequentAlgorithmParameters(parameters);
				return new PointerBasedFrequentAlgorithmImpl<T, PO>(
						(Map<FrequentAlgorithmParameterDefinition, DSParameter<?>>) parameters);
			case SPACE_SAVING_POINTER :
				assert (type.supportsPointer());
				checkSpaceSavingParameters(parameters);
				return new PointerBasedSpaceSavingImpl<T, PO>(
						(Map<SpaceSavingParameterDefinition, DSParameter<?>>) parameters);
			case LOSSY_COUNTING_POINTER :
				assert (type.supportsPointer());
				checkLossyCountingParameters(parameters);
				return new PointerBasedLossyCountingImpl<T, PO>(
						(Map<LossyCountingParameterDefinition, DSParameter<?>>) parameters);
			case LOSSY_COUNTING_BUDGET_POINTER :
				assert (type.supportsPointer());
				checkLossyCountingParameters(parameters);
				return new PointerBasedLossyCountingImpl<T, PO>(
						(Map<LossyCountingParameterDefinition, DSParameter<?>>) parameters);
			case SLIDING_WINDOW_POINTER :
				assert (type.isIterable());
				checkSlidingWindowParameters(parameters);
				return new PointerBasedSlidingWindowImpl<T, PO>(
						(Map<SlidingWindowParameterDefinition, DSParameter<?>>) parameters);
			case COUNT_MIN_SKETCH_POINTER :
				assert (type.supportsPointer());
				checkCountMinSketchParameters(parameters);
				return new PointerBasedCountMinSketch<T, PO>(
						(Map<CountMinSketchParameterDefinition, DSParameter<?>>) parameters);
			case INFINITE_HASH_POINTER :
				assert (type.supportsPointer());
				return new HashSetInfinitePointerDataStructure<T, PO>();
			case FORWARD_EXPONENTIAL_DECAY_POINTER :
				assert (type.supportsPointer());
				checkForwardExponentialDecayParameters(parameters);
				return new PointerBasedForwardExponentialDecayDataStructureImpl<T, PO>(
						(Map<ForwardExponentialDecayParameterDefinition, DSParameter<?>>) parameters);
			case BACKWARD_EXPONENTIAL_DECAY_POINTER :
				assert (type.supportsPointer());
				checkBackwardExponentialDecayParameters(parameters);
				return new PointerBasedBackwardExponentialDecayDataStructureImpl<T, PO>(
						(Map<BackwardExponentialDecayParameterDefinition, DSParameter<?>>) parameters);
			default :
				break;
		}
		return null;
	}

	@SuppressWarnings({ "unchecked" })
	public static <T, PA extends DSParameterDefinition, PO> DataStructure<T> createDataStructure(Type type,
			Map<PA, DSParameter<?>> parameters) throws DSParameterMissingException, DSWrongParameterException {
		switch (type) {
			case BACKWARD_EXPONENTIAL_DECAY :
				checkBackwardExponentialDecayParameters(parameters);
				return new BackwardExponentialDecayDataStructureImpl<T>(
						(Map<BackwardExponentialDecayParameterDefinition, DSParameter<?>>) parameters);
			case RESERVOIR :
				checkReservoirParameters(parameters);
				return new ReservoirImpl<T>((Map<ReservoirParameterDefinition, DSParameter<?>>) parameters);
			case SLIDING_WINDOW :
				checkSlidingWindowParameters(parameters);
				return new SlidingWindowImpl<T>((Map<SlidingWindowParameterDefinition, DSParameter<?>>) parameters);
			case FREQUENT_ALGORITHM :
				checkFrequentAlgorithmParameters(parameters);
				return new FrequentAlgorithmImpl<T>(
						(Map<FrequentAlgorithmParameterDefinition, DSParameter<?>>) parameters);
			case SPACE_SAVING :
				checkSpaceSavingParameters(parameters);
				return new SpaceSavingImpl<T>((Map<SpaceSavingParameterDefinition, DSParameter<?>>) parameters);
			case FREQUENT_POINTER :
				checkFrequentAlgorithmParameters(parameters);
				return new PointerBasedFrequentAlgorithmImpl<T, PO>(
						(Map<FrequentAlgorithmParameterDefinition, DSParameter<?>>) parameters);
			case SPACE_SAVING_POINTER :
				checkSpaceSavingParameters(parameters);
				return new PointerBasedSpaceSavingImpl<T, PO>(
						(Map<SpaceSavingParameterDefinition, DSParameter<?>>) parameters);
			case LOSSY_COUNTING :
				checkLossyCountingParameters(parameters);
				return new LossyCountingImpl<T>((Map<LossyCountingParameterDefinition, DSParameter<?>>) parameters);
			case LOSSY_COUNTING_BUDGET :
				checkLossyCountingParameters(parameters);
				return new LossyCountingBudgetImpl<T>(
						(Map<LossyCountingParameterDefinition, DSParameter<?>>) parameters);
			case LOSSY_COUNTING_POINTER :
				checkLossyCountingParameters(parameters);
				return new PointerBasedLossyCountingImpl<T, PO>(
						(Map<LossyCountingParameterDefinition, DSParameter<?>>) parameters);
			case LOSSY_COUNTING_BUDGET_POINTER :
				checkLossyCountingParameters(parameters);
				return new PointerBasedLossyCountingImpl<T, PO>(
						(Map<LossyCountingParameterDefinition, DSParameter<?>>) parameters);
			case SLIDING_WINDOW_POINTER :
				assert (type.isIterable());
				checkSlidingWindowParameters(parameters);
				return new PointerBasedSlidingWindowImpl<T, PO>(
						(Map<SlidingWindowParameterDefinition, DSParameter<?>>) parameters);
			case COUNT_MIN_SKETCH :
				checkCountMinSketchParameters(parameters);
				return new CountMinSketchImpl<T>((Map<CountMinSketchParameterDefinition, DSParameter<?>>) parameters);
			case COUNT_MIN_SKETCH_POINTER :
				checkCountMinSketchParameters(parameters);
				return new PointerBasedCountMinSketch<T, PO>(
						(Map<CountMinSketchParameterDefinition, DSParameter<?>>) parameters);
			case INFINITE_HASH :
				return new HashSetInfiniteDataStructureImpl<T>();
			case INFINITE_HASH_POINTER :
				return new HashSetInfinitePointerDataStructure<T, PO>();
			case FORWARD_EXPONENTIAL_DECAY :
				checkForwardExponentialDecayParameters(parameters);
				return new ForwardExponentialDecayDataStructureImpl<T>(
						(Map<ForwardExponentialDecayParameterDefinition, DSParameter<?>>) parameters);
			case FORWARD_EXPONENTIAL_DECAY_POINTER :
				checkForwardExponentialDecayParameters(parameters);
				return new PointerBasedForwardExponentialDecayDataStructureImpl<T, PO>(
						(Map<ForwardExponentialDecayParameterDefinition, DSParameter<?>>) parameters);
			case BACKWARD_EXPONENTIAL_DECAY_POINTER :
				checkBackwardExponentialDecayParameters(parameters);
				return new PointerBasedBackwardExponentialDecayDataStructureImpl<T, PO>(
						(Map<BackwardExponentialDecayParameterDefinition, DSParameter<?>>) parameters);
			default :
				break;

		}
		return null;
	}

	private static <PA> boolean checkLossyCountingParameters(Map<PA, DSParameter<?>> parameters)
			throws DSParameterMissingException, DSWrongParameterException {
		for (PA p : parameters.keySet()) {
			if (!(p instanceof LossyCountingParameterDefinition))
				throw new DSWrongParameterException(
						p.getClass() + " is not of class " + LossyCountingParameterDefinition.class);
		}
		Set<LossyCountingParameterDefinition> allParameters = EnumSet.allOf(LossyCountingParameterDefinition.class);
		for (LossyCountingParameterDefinition p : allParameters) {
			if (!parameters.containsKey(p)) {
				throw new DSParameterMissingException(
						"Parameter " + p.toString() + " was not specified for the Lossy Bucket");
			}
		}
		return true;
	}

	private static <PA> boolean checkForwardExponentialDecayParameters(Map<PA, DSParameter<?>> parameters)
			throws DSParameterMissingException, DSWrongParameterException {
		for (PA p : parameters.keySet()) {
			if (!(p instanceof ForwardExponentialDecayParameterDefinition))
				throw new DSWrongParameterException(
						p.getClass() + " is not of class " + ForwardExponentialDecayParameterDefinition.class);
		}
		Set<ForwardExponentialDecayParameterDefinition> allParameters = EnumSet
				.allOf(ForwardExponentialDecayParameterDefinition.class);
		for (ForwardExponentialDecayParameterDefinition p : allParameters) {
			if (!parameters.containsKey(p)) {
				throw new DSParameterMissingException("Parameter " + p.toString()
						+ " was not specified for the Exponential Decay Based Data Structure");
			}
		}
		return true;
	}

	private static <PA> boolean checkBackwardExponentialDecayParameters(Map<PA, DSParameter<?>> parameters)
			throws DSParameterMissingException, DSWrongParameterException {
		for (PA p : parameters.keySet()) {
			if (!(p instanceof BackwardExponentialDecayParameterDefinition))
				throw new DSWrongParameterException(
						p.getClass() + " is not of class " + BackwardExponentialDecayParameterDefinition.class);
		}
		Set<BackwardExponentialDecayParameterDefinition> allParameters = EnumSet
				.allOf(BackwardExponentialDecayParameterDefinition.class);
		for (BackwardExponentialDecayParameterDefinition p : allParameters) {
			if (!parameters.containsKey(p)) {
				throw new DSParameterMissingException("Parameter " + p.toString()
						+ " was not specified for the Exponential Decay Based Data Structure");
			}
		}
		return true;
	}

	private static <PA> boolean checkReservoirParameters(Map<PA, DSParameter<?>> parameters)
			throws DSParameterMissingException, DSWrongParameterException {
		for (PA p : parameters.keySet()) {
			if (!(p instanceof ReservoirParameterDefinition))
				throw new DSWrongParameterException(
						p.getClass() + " is not of class " + ReservoirParameterDefinition.class);
		}
		Set<ReservoirParameterDefinition> allParameters = EnumSet.allOf(ReservoirParameterDefinition.class);
		for (ReservoirParameterDefinition p : allParameters) {
			if (!parameters.containsKey(p)) {
				throw new DSParameterMissingException(
						"Parameter " + p.toString() + " was not specified for the Reservoir");
			}
		}
		return true;
	}

	private static <PA> boolean checkSlidingWindowParameters(Map<PA, DSParameter<?>> parameters)
			throws DSParameterMissingException, DSWrongParameterException {
		for (PA p : parameters.keySet()) {
			if (!(p instanceof SlidingWindowParameterDefinition))
				throw new DSWrongParameterException(
						p.getClass() + " is not of class " + SlidingWindowParameterDefinition.class);
		}
		Set<SlidingWindowParameterDefinition> allParameters = EnumSet.allOf(SlidingWindowParameterDefinition.class);
		for (SlidingWindowParameterDefinition p : allParameters) {
			if (!parameters.containsKey(p)) {
				throw new DSParameterMissingException(
						"Parameter " + p.toString() + " was not specified for the Sliding Window");
			}
		}
		return true;
	}

	private static <PA> boolean checkFrequentAlgorithmParameters(Map<PA, DSParameter<?>> parameters)
			throws DSParameterMissingException, DSWrongParameterException {
		for (PA p : parameters.keySet()) {
			if (!(p instanceof FrequentAlgorithmParameterDefinition))
				throw new DSWrongParameterException(
						p.getClass() + " is not of class " + FrequentAlgorithmParameterDefinition.class);
		}
		Set<FrequentAlgorithmParameterDefinition> allParameters = EnumSet
				.allOf(FrequentAlgorithmParameterDefinition.class);
		for (FrequentAlgorithmParameterDefinition p : allParameters) {
			if (!parameters.containsKey(p)) {
				throw new DSParameterMissingException(
						"Parameter " + p.toString() + " was not specified for the Frequent Counting Algorithm");
			}
		}
		return true;
	}

	private static <PA> boolean checkSpaceSavingParameters(Map<PA, DSParameter<?>> parameters)
			throws DSParameterMissingException, DSWrongParameterException {
		for (PA p : parameters.keySet()) {
			if (!(p instanceof SpaceSavingParameterDefinition))
				throw new DSWrongParameterException(
						p.getClass() + " is not of class " + SpaceSavingParameterDefinition.class);
		}
		Set<SpaceSavingParameterDefinition> allParameters = EnumSet.allOf(SpaceSavingParameterDefinition.class);
		for (SpaceSavingParameterDefinition p : allParameters) {
			if (!parameters.containsKey(p)) {
				throw new DSParameterMissingException(
						"Parameter " + p.toString() + " was not specified for the Space Saving Algorithm");
			}
		}
		return true;
	}

	private static <PA> boolean checkCountMinSketchParameters(Map<PA, DSParameter<?>> parameters)
			throws DSParameterMissingException, DSWrongParameterException {
		for (PA p : parameters.keySet()) {
			if (!(p instanceof CountMinSketchParameterDefinition))
				throw new DSWrongParameterException(
						p.getClass() + " is not of class " + CountMinSketchParameterDefinition.class);
		}
		Set<CountMinSketchParameterDefinition> allParameters = EnumSet.allOf(CountMinSketchParameterDefinition.class);
		for (CountMinSketchParameterDefinition p : allParameters) {
			if (!parameters.containsKey(p)) {
				throw new DSParameterMissingException(
						"Parameter " + p.toString() + " was not specified for the Space Saving Algorithm");
			}
		}
		return true;
	}
}
