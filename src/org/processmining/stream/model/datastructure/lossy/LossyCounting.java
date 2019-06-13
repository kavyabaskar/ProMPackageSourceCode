package org.processmining.stream.model.datastructure.lossy;

import org.processmining.stream.model.datastructure.CounterBasedDataStructure;

/**
 * A lossy bucket is a collection that implements lossy counting.
 * 
 *
 * @param <T>
 *            generic type parameter
 */
public interface LossyCounting<T> extends CounterBasedDataStructure<T> {

	public int getBucketOf(T e);

	public int getBucketSize();

	public int getCurrentBucket();

	public double getMaximumApproximationError();

}
