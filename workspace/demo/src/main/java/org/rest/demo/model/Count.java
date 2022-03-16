package org.rest.demo.model;

public class Count {
	
	private long totalUniqueUserIds;
	
	public Count() {
		
	}

	public long getTotalUniqueUserIds() {
		return totalUniqueUserIds;
	}

	public void setTotalUniqueUserIds(long totalUniqueUserIds) {
		this.totalUniqueUserIds = totalUniqueUserIds;
	}

	@Override
	public String toString() {
		return "Count [totalUniqueUserIds=" + totalUniqueUserIds + "]";
	}

}
