package org.processmining.stream.model.datastructure;

import java.util.Collection;

/**
 * Specifies a data structure in which the elements additionally point to some
 * object. This is useful for, e.g., CxA buckets where we let the case point to
 * some activity.
 * 
 * @author svzelst
 *
 * @param <T>
 *            element of the data structure
 * @param <P>
 *            type of the pointed element;
 */
public interface PointerBasedDataStructure<T, P> extends DataStructure<T> {

	/**
	 * add element t to the data structure, update it's pointer to p.
	 * 
	 * @param t
	 *            element in data structure
	 * @param p
	 *            pointed element
	 */
	Collection<ItemPointerPair<T, P>> add(T t, P p);

	P getPointedElement(T t);

}
