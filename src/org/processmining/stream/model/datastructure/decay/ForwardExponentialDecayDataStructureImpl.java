package org.processmining.stream.model.datastructure.decay;

import java.util.Map;

import org.processmining.stream.model.datastructure.DSParameter;

public class ForwardExponentialDecayDataStructureImpl<T>
		extends AbstractForwardDecayDataStructureImpl<T, ForwardExponentialDecayParameterDefinition> {

	public ForwardExponentialDecayDataStructureImpl(
			Map<ForwardExponentialDecayParameterDefinition, DSParameter<?>> params) {
		super(params,
				new ForwardExponentialDecayFunction(
						(Double) params.get(ForwardExponentialDecayParameterDefinition.DECAY_RATE).getValue()),
				(Integer) params.get(ForwardExponentialDecayParameterDefinition.RENEWAL_RATE).getValue(),
				(Double) params.get(ForwardExponentialDecayParameterDefinition.THRESHOLD).getValue());
	}

}
