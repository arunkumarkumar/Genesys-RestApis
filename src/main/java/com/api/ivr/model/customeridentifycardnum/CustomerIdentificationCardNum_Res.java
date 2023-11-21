package com.api.ivr.model.customeridentifycardnum;

import com.api.ivr.model.CommonOutput;

public class CustomerIdentificationCardNum_Res extends CommonOutput {
	private CustomerIdentificationCardNum_ResponseData response;

	public CustomerIdentificationCardNum_ResponseData getResponse() {
		return response;
	}

	public void setResponse(CustomerIdentificationCardNum_ResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "CustomerIdentificationCardNum_Res [response=" + response + "]";
	}
	

}
