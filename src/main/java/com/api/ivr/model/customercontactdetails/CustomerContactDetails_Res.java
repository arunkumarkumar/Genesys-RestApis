package com.api.ivr.model.customercontactdetails;

import com.api.ivr.model.CommonOutput;

public class CustomerContactDetails_Res extends CommonOutput {
	
	private CustomerContactDetails_ResponseData response;

	public CustomerContactDetails_ResponseData getResponse() {
		return response;
	}

	public void setResponse(CustomerContactDetails_ResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "CustomerContactDetails_Res [response=" + response + "]";
	}
	

}
