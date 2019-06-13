package org.processmining.stream.model.datastructure;

import java.util.Map;

public abstract class AbstractDataStructure<T, P extends DSParameterDefinition> implements DataStructure<T> {

	private final Map<P, DSParameter<?>> params;

	//	private final MemoryMeter memoryMeter = new MemoryMeter();

	public AbstractDataStructure(Map<P, DSParameter<?>> params) {
		this.params = params;
	}

	@SuppressWarnings("unchecked")
	public Map<P, DSParameter<?>> getParameters() {
		return params;
	}

	public long getUsedMemoryInBytes() {
		long res = -1;
		//		try {
		//			res = memoryMeter.measureDeep(this);
		//		} catch (IllegalStateException e) {
		//			// please try: -javaagent:<path to>/jamm.jar in JVM arguments
		//			res = -1;
		//		}
		return res;
	}

}
