package org.processmining.stream.model.datastructure.decay;

import java.util.Map;

import org.processmining.stream.model.datastructure.DSParameter;

public class BackwardExponentialDecayDataStructureImpl<T>
		extends AbstractBackwardDecayDataStructureImpl<T, BackwardExponentialDecayParameterDefinition> {

	public BackwardExponentialDecayDataStructureImpl(
			Map<BackwardExponentialDecayParameterDefinition, DSParameter<?>> parameters) {
		super(parameters,
				new BackwardExponentialDecayFunctionImpl(
						(Double) parameters.get(BackwardExponentialDecayParameterDefinition.BASE).getValue(),
						(Double) parameters.get(BackwardExponentialDecayParameterDefinition.DECAY_RATE).getValue()),
				(Integer) parameters.get(BackwardExponentialDecayParameterDefinition.RENEWAL_RATE).getValue(),
				(Double) parameters.get(BackwardExponentialDecayParameterDefinition.THRESHOLD).getValue());
	}

}
