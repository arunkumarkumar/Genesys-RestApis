package com.api.ivr.model.customeridentifycardnum;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerIdentificationCardNum_MultipleLinks {
	@JsonProperty("first")
	private IdenifiyCardNum_firstlink first;
	@JsonProperty("last")
	private IdentifyCardNum_lastlink  last;
	
	@JsonProperty("self")
	private IdentifyCardNum_selflink self;

	@Override
	public String toString() {
		return "CustomerIdentificationCardNum_MultipleLinks [first=" + first + ", last=" + last + ", self=" + self
				+ "]";
	}

	public IdenifiyCardNum_firstlink getFirst() {
		return first;
	}

	public void setFirst(IdenifiyCardNum_firstlink first) {
		this.first = first;
	}

	public IdentifyCardNum_lastlink getLast() {
		return last;
	}

	public void setLast(IdentifyCardNum_lastlink last) {
		this.last = last;
	}

	public IdentifyCardNum_selflink getSelf() {
		return self;
	}

	public void setSelf(IdentifyCardNum_selflink self) {
		this.self = self;
	}
}
