package org.processmining.stream.core.enums;

public enum CommunicationType {
	SYNC("Synchronous"), ASYNC("Asynchronous");

	private final String description;

	private CommunicationType(final String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return description;
	}
}
