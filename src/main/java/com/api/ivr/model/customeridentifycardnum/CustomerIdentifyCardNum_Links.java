package com.api.ivr.model.customeridentifycardnum;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerIdentifyCardNum_Links {
	@JsonProperty("self")
	private String self;

	@Override
	public String toString() {
		return "CustomerIdentifyCardNum_Links [self=" + self + "]";
	}

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

}
