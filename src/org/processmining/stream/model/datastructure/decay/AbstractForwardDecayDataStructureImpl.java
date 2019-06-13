package org.processmining.stream.model.datastructure.decay;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.processmining.stream.model.datastructure.AbstractDataStructure;
import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.DSParameterDefinition;
import org.processmining.stream.model.datastructure.DataStructure;

public abstract class AbstractForwardDecayDataStructureImpl<T, P extends DSParameterDefinition>
		extends AbstractDataStructure<T, P> implements DecayDataStructure<T> {

	private final Map<T, Queue<WeakReference<ItemDecayValuePair<T>>>> counts = new HashMap<>();
	private final DecayFunction f;
	private final List<ItemDecayValuePair<T>> items = new ArrayList<>();
	private long landMark = 0;
	private final int renewalRate;
	private final double threshold;
	private long time = 0;

	public AbstractForwardDecayDataStructureImpl(Map<P, DSParameter<?>> params, final DecayFunction f, final int renewalRate,
			final double threshold) {
		super(params);
		this.f = f;
		this.renewalRate = renewalRate;
		this.threshold = threshold;
	}

	public Collection<T> add(T t) {
		ItemDecayValuePair<T> idvp = new ItemDecayValuePair<>(t, time, f.evaluate(time - landMark));
		items.add(idvp);
		if (idvp.getDecayValue() == Double.POSITIVE_INFINITY || idvp.getDecayValue() == Double.NaN) {
			resetLandMark();
		}
		if (!(counts.containsKey(t))) {
			counts.put(t, new ArrayDeque<WeakReference<ItemDecayValuePair<T>>>());
		}
		counts.get(t).offer(new WeakReference<ItemDecayValuePair<T>>(idvp));
		time++;
		if (time % renewalRate == 0) {
			return clean();
		}
		return new HashSet<>();
	}

	protected Collection<T> clean() {
		Collection<T> removed = new HashSet<>();
		double valueOfLatest = f.evaluate(time - landMark - 1);
		int removeIndex = findMaxIndexOfItemsToRemove(0, items.size() - 1, valueOfLatest);
		if (removeIndex > -1) {
			for (int i = 0; i <= removeIndex; i++) {
				counts.get(items.get(i).getItem()).poll();
				if (counts.get(items.get(i).getItem()).isEmpty()) {
					counts.remove(items.get(i).getItem());
				}
			}
			int i = 0;
			while (i <= removeIndex) {
				removed.add(items.get(0).getItem());
				items.remove(0);
				i++;
			}
		}
		return removed;
	}

	public void clear() {
		items.clear();
		counts.clear();
	}

	public boolean contains(Object o) {
		return counts.containsKey(o);
	}

	// binary search through the list of items
	protected int findMaxIndexOfItemsToRemove(final int from, final int to, final double divisor) {
		int range = (to - from) + 1;
		if (to - from > 1) {
			int split = from + ((range / 2) - 1);
			boolean recurseLeft = (items.get(split).getDecayValue() / divisor) >= threshold;
			if (recurseLeft) {
				return findMaxIndexOfItemsToRemove(from, split - 1, divisor);
			} else {

				return Math.max(split, findMaxIndexOfItemsToRemove(split + 1, to, divisor));
			}
		} else if (to - from == 1) {
			if ((items.get(to).getDecayValue() / divisor) < threshold) {
				return to;
			} else if ((items.get(from).getDecayValue() / divisor) < threshold) {
				return from;
			} else {
				return -1;
			}
		} else if (to - from == 0) {
			return items.get(to).getDecayValue() < threshold ? to : -1;
		} else {
			return -1;
		}
	}

	public int getCapacity() {
		return Integer.MAX_VALUE;
	}

	public Map<T, Queue<WeakReference<ItemDecayValuePair<T>>>> getCounts() {
		return counts;
	}

	public DecayFunction getDecayFunction() {
		return f;
	}

	public long getFrequencyOf(T e) {
		if (!contains(e)) {
			return 0;
		} else {
			return counts.get(e).size();
		}
	}

	public List<ItemDecayValuePair<T>> getItems() {
		return items;
	}

	public long getLandMark() {
		return landMark;
	}

	public int getRenewalRate() {
		return renewalRate;
	}

	public long getSize() {
		return items.size();
	}

	public double getThreshold() {
		return threshold;
	}

	public long getTime() {
		return time;
	}

	public org.processmining.stream.model.datastructure.DataStructure.Type getType() {
		return DataStructure.Type.FORWARD_EXPONENTIAL_DECAY;
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public Iterator<T> iterator() {
		return counts.keySet().iterator();
	}

	private void resetLandMark() {
		landMark = items.get(0).getTimeStamp();
		for (ItemDecayValuePair<T> idvp : items) {
			idvp.setDecayValue(f.evaluate(idvp.getTimeStamp() - landMark));
		}
	}

	public void setLandMark(long landMark) {
		this.landMark = landMark;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
