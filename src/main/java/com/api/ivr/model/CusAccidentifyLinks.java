package com.api.ivr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CusAccidentifyLinks {
 @JsonProperty("self")
	private String self;

public String getSelf() {
	return self;
}

public void setSelf(String self) {
	this.self = self;
}

@Override
public String toString() {
	return "CusAccidentifyLinks [self=" + self + "]";
}
}
