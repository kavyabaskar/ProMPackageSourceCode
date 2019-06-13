package org.processmining.stream.model.datastructure.decay;

import java.util.Map;

import org.processmining.stream.model.datastructure.DSParameter;

public class PointerBasedBackwardExponentialDecayDataStructureImpl<T, PO>
		extends AbstractPointerBasedBackwardDecayDataStructure<T, PO, BackwardExponentialDecayParameterDefinition> {

	public PointerBasedBackwardExponentialDecayDataStructureImpl(
			Map<BackwardExponentialDecayParameterDefinition, DSParameter<?>> parameters) {
		super(parameters,
				new BackwardExponentialDecayFunctionImpl(
						(Double) parameters.get(BackwardExponentialDecayParameterDefinition.BASE).getValue(),
						(Double) parameters.get(BackwardExponentialDecayParameterDefinition.DECAY_RATE).getValue()),
				(Integer) parameters.get(BackwardExponentialDecayParameterDefinition.RENEWAL_RATE).getValue(),
				(Double) parameters.get(BackwardExponentialDecayParameterDefinition.THRESHOLD).getValue());
	}

}
