package com.api.ivr.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class IdentifyCustomerRMNRespose {
	
	@JsonProperty("data")
	private CustIdentificationRMNDatum data;
	
	private String message;

	@Override
	public String toString() {
		return "IdentifyCustomerRMNRespose [data=" + data + ", message=" + message + "]";
	}

	public CustIdentificationRMNDatum getData() {
		return data;
	}

	public void setData(CustIdentificationRMNDatum data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
