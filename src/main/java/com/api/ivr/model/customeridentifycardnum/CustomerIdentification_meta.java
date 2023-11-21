package com.api.ivr.model.customeridentifycardnum;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerIdentification_meta {
	@JsonProperty("totalResourceCount")
	private int totalResourceCount;

	public int getTotalResourceCount() {
		return totalResourceCount;
	}

	public void setTotalResourceCount(int totalResourceCount) {
		this.totalResourceCount = totalResourceCount;
	}

	@Override
	public String toString() {
		return "CustomerIdentification_meta [totalResourceCount=" + totalResourceCount + "]";
	}

}
