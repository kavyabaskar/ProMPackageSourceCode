package org.processmining.stream.model.datastructure.decay;

import org.processmining.stream.model.datastructure.IterableDataStructure;

public interface DecayDataStructure<T> extends IterableDataStructure<T> {

	DecayFunction getDecayFunction();

}
