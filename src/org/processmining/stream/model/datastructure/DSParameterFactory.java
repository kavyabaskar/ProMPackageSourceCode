package org.processmining.stream.model.datastructure;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.processmining.stream.model.datastructure.DataStructure.Type;
import org.processmining.stream.model.datastructure.countmin.CountMinSketchParameterDefinition;
import org.processmining.stream.model.datastructure.decay.BackwardExponentialDecayParameterDefinition;
import org.processmining.stream.model.datastructure.decay.ForwardExponentialDecayParameterDefinition;
import org.processmining.stream.model.datastructure.frequent.FrequentAlgorithmParameterDefinition;
import org.processmining.stream.model.datastructure.lossy.LossyCountingParameterDefinition;
import org.processmining.stream.model.datastructure.reservoir.ReservoirParameterDefinition;
import org.processmining.stream.model.datastructure.slidingwindow.SlidingWindowParameterDefinition;
import org.processmining.stream.model.datastructure.spacesaving.SpaceSavingParameterDefinition;

public class DSParameterFactory {

	@SuppressWarnings("unchecked")
	public static <T extends DSParameterDefinition> Map<T, DSParameter<?>> createDefaultParameters(Type type) {
		switch (type) {
			case BACKWARD_EXPONENTIAL_DECAY :
			case BACKWARD_EXPONENTIAL_DECAY_POINTER :
				return (Map<T, DSParameter<?>>) constructParameterMap(
						EnumSet.allOf(BackwardExponentialDecayParameterDefinition.class));
			case RESERVOIR :
				return (Map<T, DSParameter<?>>) constructParameterMap(
						EnumSet.allOf(ReservoirParameterDefinition.class));
			case SLIDING_WINDOW :
				return (Map<T, DSParameter<?>>) constructParameterMap(
						EnumSet.allOf(SlidingWindowParameterDefinition.class));
			case SLIDING_WINDOW_POINTER :
				return (Map<T, DSParameter<?>>) constructParameterMap(
						EnumSet.allOf(SlidingWindowParameterDefinition.class));
			case FREQUENT_ALGORITHM :
			case FREQUENT_POINTER :
				return (Map<T, DSParameter<?>>) constructParameterMap(
						EnumSet.allOf(FrequentAlgorithmParameterDefinition.class));
			case SPACE_SAVING :
			case SPACE_SAVING_POINTER :
				return (Map<T, DSParameter<?>>) constructParameterMap(
						EnumSet.allOf(SpaceSavingParameterDefinition.class));
			case COUNT_MIN_SKETCH :
			case COUNT_MIN_SKETCH_POINTER :
				return (Map<T, DSParameter<?>>) constructParameterMap(
						EnumSet.allOf(CountMinSketchParameterDefinition.class));
			case LOSSY_COUNTING :
			case LOSSY_COUNTING_BUDGET :
			case LOSSY_COUNTING_POINTER :
				return (Map<T, DSParameter<?>>) constructParameterMap(
						EnumSet.allOf(LossyCountingParameterDefinition.class));
			case LOSSY_COUNTING_BUDGET_POINTER :
				return (Map<T, DSParameter<?>>) constructParameterMap(
						EnumSet.allOf(LossyCountingParameterDefinition.class));
			case FORWARD_EXPONENTIAL_DECAY :
			case FORWARD_EXPONENTIAL_DECAY_POINTER :
				return (Map<T, DSParameter<?>>) constructParameterMap(
						EnumSet.allOf(ForwardExponentialDecayParameterDefinition.class));
			case INFINITE_HASH :
			case INFINITE_HASH_POINTER :
				return new HashMap<T, DSParameter<?>>();
			default :
				break;
		}
		return null;
	}

	private static <T extends DSParameterDefinition> Map<T, DSParameter<?>> constructParameterMap(
			final Iterable<T> definitions) {
		Map<T, DSParameter<?>> params = new HashMap<>();
		for (T def : definitions) {
			params.put(def, DSParameterFactory.createParameter(def.getDefaultValue()));
		}
		return params;
	}

	public static <T> DSParameter<T> createParameter(T value) {
		return new DSParameterImpl<>(value);
	}

}
