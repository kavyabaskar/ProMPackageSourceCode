package org.processmining.stream.model.datastructure.decay;

/**
 * forward exponential decay function. Based on: Forward Decay: A Practical Time
 * Decay Model for Streaming Systems; G. Cormode et al.; doi:
 * http://dx.doi.org/10.1109/ICDE.2009.65
 * 
 * form: exp(alpha * age);
 * 
 * @author svzelst
 *
 */
public class ForwardExponentialDecayFunction implements DecayFunction {

	private final double alpha;

	public ForwardExponentialDecayFunction(double alpha) {
		this.alpha = alpha;
	}

	public double getAlpha() {
		return alpha;
	}

	public double evaluate(long age) {
		return Math.pow(Math.E, age * alpha);
	}

	public Type getType() {
		return Type.FORWARD_EXPONENTIAL;
	}

}
