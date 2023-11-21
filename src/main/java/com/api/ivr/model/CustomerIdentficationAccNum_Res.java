package com.api.ivr.model;

public class CustomerIdentficationAccNum_Res extends CommonOutput {
	private CustomerIdentificationResponseData response;

	public CustomerIdentificationResponseData getResponse() {
		return response;
	}

	public void setResponse(CustomerIdentificationResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "CustomerIdentificationResponseData [response=" + response + "]";
	}
	

}
