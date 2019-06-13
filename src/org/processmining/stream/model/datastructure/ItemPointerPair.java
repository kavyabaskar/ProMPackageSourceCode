package org.processmining.stream.model.datastructure;

public class ItemPointerPair<T, P> {

	private final T item;

	private final P pointer;

	public ItemPointerPair(T t, P p) {
		item = t;
		pointer = p;
	}

	public T getItem() {
		return item;
	}

	public P getPointer() {
		return pointer;
	}

	@Override
	public String toString() {
		return "(obj: " + item.toString() + ", po: " + pointer.toString();
	}

}
