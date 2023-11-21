package com.api.ivr.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CusIdentificationRMNAttributes {

	@JsonProperty("response")
	private CustIdentificationRMNResponse response;

	public CustIdentificationRMNResponse getResponse() {
		return response;
	}

	public void setResponse(CustIdentificationRMNResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "CusIdentificationRMNAttributes [response=" + response + "]";
	}
}
