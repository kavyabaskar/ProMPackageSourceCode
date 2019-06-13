package org.processmining.stream.model.datastructure.countmin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import org.processmining.stream.model.datastructure.AbstractDataStructure;
import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.SketchBasedDataStructure;

/**
 * Implementation of Count Min Sketch: Paper: An Improved Data Stream Summary:
 * The Count-Min Sketch and its Applications; Cormode et al.; 2005;
 * 10.1016/j.jalgor.2003.12.001
 *
 * Code adapted from:
 * https://github.com/addthis/stream-lib/blob/master/src/main/java/com/
 * clearspring/analytics/stream/frequency/CountMinSketch.java commit:
 * 9f1bf8bd8d81b4fcaf632a18be90091dd68afd4f
 * 
 * 
 * License of the original code: * Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 *
 */

public class CountMinSketchImpl<T> extends AbstractDataStructure<T, CountMinSketchParameterDefinition>
		implements SketchBasedDataStructure<T> {

	public static final long PRIME_MODULUS = (1L << 31) - 1;

	private double confidence;
	private int depth;
	private double eps;
	private long[] hashA;
	private long size;
	private long[][] table;

	private int width;

	/*
	 * "Setting d (== number of hashes) to log(1/error_prob) and w (== hash
	 * range) to O(1/error_margin) ensures that the estimator of an element has
	 * error at most error_margin * N (== stream size) with probability at least
	 * 1 - error prob.
	 */
	public CountMinSketchImpl(Map<CountMinSketchParameterDefinition, DSParameter<?>> params) {
		super(params);
		construct((Double) params.get(CountMinSketchParameterDefinition.ERROR_MARGIN).getValue(),
				(Double) params.get(CountMinSketchParameterDefinition.CONFIDENCE).getValue(),
				(Integer) params.get(CountMinSketchParameterDefinition.SEED).getValue());
	}

	@Override
	public Collection<T> add(T item) {
		int[] buckets = CountMinUtils.getHashBuckets(item.toString(), depth, width);
		for (int i = 0; i < depth; ++i) {
			table[i][buckets[i]] += 1;
		}
		size += 1;
		// nothing will be removed in the countmin sketch.
		return new HashSet<>();
	}

	public void clear() {
		for (int i = 0; i < depth; i++) {
			for (int j = 0; j < width; j++) {
				table[i][j] = 0;
			}
		}
		size = 0;
	}

	private void construct(double epsOfTotalCount, double confidence, int seed) {
		// 2/w = eps ; w = 2/eps 
		// 1/2^depth <= 1-confidence ; depth >= -log2 (1-confidence) 
		this.eps = epsOfTotalCount;
		this.confidence = confidence;
		this.width = (int) Math.ceil(2 / epsOfTotalCount);
		this.depth = (int) Math.ceil(-Math.log(1 - confidence) / Math.log(2));
		initTablesWith(depth, width, seed);
	}

	@SuppressWarnings("unchecked")
	public boolean contains(Object o) {
		return getFrequencyOf((T) o) > 0;
	}

	/**
	 * The estimate is correct within 'epsilon' * (total item count), with
	 * probability 'confidence'.
	 */
	public long estimateCount(T item) {
		long res = Long.MAX_VALUE;
		int[] buckets = CountMinUtils.getHashBuckets(item.toString(), depth, width);
		for (int i = 0; i < depth; ++i) {
			res = Math.min(res, table[i][buckets[i]]);
		}
		return res;
	}

	public int getCapacity() {
		return Integer.MAX_VALUE;
	}

	public double getConfidence() {
		return confidence;
	}

	protected int getDepth() {
		return depth;
	}

	public long getFrequencyOf(T e) {
		return estimateCount(e);
	}

	public double getRelativeError() {
		return eps;
	}

	public long getSize() {
		return size;
	}

	protected long[][] getTable() {
		return table;
	}

	public Type getType() {
		return Type.COUNT_MIN_SKETCH;
	}

	protected int getWidth() {
		return width;
	}

	private void initTablesWith(int depth, int width, int seed) {
		this.table = new long[depth][width];
		this.hashA = new long[depth];
		Random r = new Random(seed);
		// We're using a linear hash functions 
		// of the form (a*x+b) mod p. 
		// a,b are chosen independently for each hash function. 
		// However we can set b = 0 as all it does is shift the results 
		// without compromising their uniformity or independence with 
		// the other hashes. 
		for (int i = 0; i < depth; ++i) {
			hashA[i] = r.nextInt(Integer.MAX_VALUE);
		}
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long size() {
		return size;
	}

	//	public CountMinSketchImpl(int depth, int width, int seed) {
	//		this.depth = depth;
	//		this.width = width;
	//		this.eps = 2.0 / width;
	//		this.confidence = 1 - 1 / Math.pow(2, depth);
	//		initTablesWith(depth, width, seed);
	//	}
	//
	//	public CountMinSketchImpl(double epsOfTotalCount, double confidence, int seed) {
	//		construct(epsOfTotalCount, confidence, seed);
	//	}
	//	
	//	CountMinSketchImpl(int depth, int width, int size, long[] hashA, long[][] table) {
	//		this.depth = depth;
	//		this.width = width;
	//		this.eps = 2.0 / width;
	//		this.confidence = 1 - 1 / Math.pow(2, depth);
	//		this.hashA = hashA;
	//		this.table = table;
	//		this.size = size;
	//	}

	//	int hash(long item, int i) {
	//		long hash = hashA[i] * item;
	//		// A super fast way of computing x mod 2^p-1 
	//		// See http://www.cs.princeton.edu/courses/archive/fall09/cos521/Handouts/universalclasses.pdf 
	//		// page 149, right after Proposition 7. 
	//		hash += hash >> 32;
	//		hash &= PRIME_MODULUS;
	//		// Doing "%" after (int) conversion is ~2x faster than %'ing longs. 
	//		return ((int) hash) % width;
	//	}

	//	public void add(long item) {
	//		for (int i = 0; i < depth; ++i) {
	//			table[i][hash(item, i)] += 1;
	//		}
	//		size += 1;
	//	}

	//	public long estimateCount(long item) {
	//		long res = Long.MAX_VALUE;
	//		for (int i = 0; i < depth; ++i) {
	//			res = Math.min(res, table[i][hash(item, i)]);
	//		}
	//		return res;
	//	}

	/**
	 * Merges count min sketches to produce a count min sketch for their
	 * combined streams
	 * 
	 * @param estimators
	 * @return merged estimator or null if no estimators were provided
	 * @throws CMSMergeException
	 *             if estimators are not mergeable (same depth, width and seed)
	 */
	//	
	//	public static byte[] serialize(CountMinSketchImpl<?> sketch) {
	//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	//		DataOutputStream s = new DataOutputStream(bos);
	//		try {
	//			s.writeLong(sketch.size);
	//			s.writeInt(sketch.depth);
	//			s.writeInt(sketch.width);
	//			for (int i = 0; i < sketch.depth; ++i) {
	//				s.writeLong(sketch.hashA[i]);
	//				for (int j = 0; j < sketch.width; ++j) {
	//					s.writeLong(sketch.table[i][j]);
	//				}
	//			}
	//			return bos.toByteArray();
	//		} catch (IOException e) {
	//			// Shouldn't happen 
	//			throw new RuntimeException(e);
	//		}
	//	}
	//
	//	public static CountMinSketchImpl<?> deserialize(byte[] data) {
	//		ByteArrayInputStream bis = new ByteArrayInputStream(data);
	//		DataInputStream s = new DataInputStream(bis);
	//		try {
	//			CountMinSketchImpl<?> sketch = new CountMinSketchImpl();
	//			sketch.size = s.readLong();
	//			sketch.depth = s.readInt();
	//			sketch.width = s.readInt();
	//			sketch.eps = 2.0 / sketch.width;
	//			sketch.confidence = 1 - 1 / Math.pow(2, sketch.depth);
	//			sketch.hashA = new long[sketch.depth];
	//			sketch.table = new long[sketch.depth][sketch.width];
	//			for (int i = 0; i < sketch.depth; ++i) {
	//				sketch.hashA[i] = s.readLong();
	//				for (int j = 0; j < sketch.width; ++j) {
	//					sketch.table[i][j] = s.readLong();
	//				}
	//			}
	//			return sketch;
	//		} catch (IOException e) {
	//			// Shouldn't happen 
	//			throw new RuntimeException(e);
	//		}
	//	}

}
