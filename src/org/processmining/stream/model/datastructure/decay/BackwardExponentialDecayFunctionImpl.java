package org.processmining.stream.model.datastructure.decay;

/**
 * Exponential decay model of the form: A = A0 * e^{k * t} where A0 is the basis
 * value, k is the rate of decay and t is the current time.
 */
public class BackwardExponentialDecayFunctionImpl implements DecayFunction {

	protected final double base;

	protected final double decayRate;

	public BackwardExponentialDecayFunctionImpl(final double base, final double decayRate) {
		this.base = base;
		this.decayRate = decayRate;
	}

	public double evaluate(long age) {
		return base * Math.exp(-1 * decayRate * age);
	}

	public Type getType() {
		return Type.BACKWARD_EXPONENTIAL;
	}
}
