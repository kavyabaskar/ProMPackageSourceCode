package org.processmining.stream.model.datastructure.decay;

public class ItemDecayValuePair<T> {

	private double decayValue;
	
	private final T item;

	private final long timeStamp;

	public ItemDecayValuePair(T item, long timeStamp, double age) {
		this.item = item;
		this.decayValue = age;
		this.timeStamp = timeStamp;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object o) {
		if (o instanceof ItemDecayValuePair) {
			ItemDecayValuePair<?> c = ((ItemDecayValuePair) o);
			boolean r = c.getItem().equals(getItem());
			return r && c.getDecayValue() == getDecayValue();
		}
		return false;
	}

	public double getDecayValue() {
		return decayValue;
	}

	public T getItem() {
		return item;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	@Override
	public int hashCode() {
		return 31 * getItem().hashCode() + new Double(getDecayValue()).intValue();
	}

	public void setDecayValue(double decayValue) {
		this.decayValue = decayValue;
	}

	@Override
	public String toString() {
		return "(" + getItem().toString() + "," + getDecayValue() + ")";
	}

}
