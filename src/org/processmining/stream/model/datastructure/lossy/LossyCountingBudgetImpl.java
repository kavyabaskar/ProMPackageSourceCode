package org.processmining.stream.model.datastructure.lossy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.processmining.stream.model.datastructure.DSParameter;

public class LossyCountingBudgetImpl<T> extends LossyCountingImpl<T> {

	public LossyCountingBudgetImpl(Map<LossyCountingParameterDefinition, DSParameter<?>> params) {
		super(params);
	}

	@Override
	public Collection<T> add(T e) {
		return add(e, 1);
	}

	public Collection<T> add(T t, int f) {
		Collection<T> res = new HashSet<>();
		if (frequencies.size() == bucketSize && !(this.contains(t))) {
			res = clean();
		}
		if (frequencies.containsKey(t)) {
			frequencies.put(t, frequencies.get(t) + f);
		} else {
			frequencies.put(t, f);
		}
		if (!buckets.containsKey(t)) {
			buckets.put(t, currentBucket - 1);
		}
		items++;
		return res;
	}

	@Override
	public Collection<T> clean() {
		Collection<T> result = new HashSet<>();
		while (frequencies.size() == bucketSize) {
			result = super.clean();
			currentBucket++;
		}
		return result;
	}

	public Type getType() {
		return Type.LOSSY_COUNTING_BUDGET;
	}
}
