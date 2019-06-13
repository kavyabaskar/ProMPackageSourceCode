package org.processmining.stream.model.datastructure.decay;

import java.util.Map;

import org.processmining.stream.model.datastructure.DSParameter;

public class PointerBasedForwardExponentialDecayDataStructureImpl<T, PO>
		extends AbstractPointerBasedForwardDecayDataStructure<T, PO, ForwardExponentialDecayParameterDefinition> {

	public PointerBasedForwardExponentialDecayDataStructureImpl(
			Map<ForwardExponentialDecayParameterDefinition, DSParameter<?>> params) {
		super(params,
				new ForwardExponentialDecayFunction(
						(Double) params.get(ForwardExponentialDecayParameterDefinition.DECAY_RATE).getValue()),
				(Integer) params.get(ForwardExponentialDecayParameterDefinition.RENEWAL_RATE).getValue(),
				(Double) params.get(ForwardExponentialDecayParameterDefinition.THRESHOLD).getValue());
	}

}
