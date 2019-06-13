package org.processmining.stream.model.datastructure.decay;

public interface DecayFunction {

	enum Type {
		BACKWARD_EXPONENTIAL, FORWARD_EXPONENTIAL;
	}

	double evaluate(long age);
	
	Type getType();

}
