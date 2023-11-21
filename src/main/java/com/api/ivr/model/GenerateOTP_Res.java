package com.api.ivr.model;

public class GenerateOTP_Res extends CommonOutput {

	private GenerateOtpResponseData response;

	public GenerateOtpResponseData getResponse() {
		return response;
	}

	public void setResponse(GenerateOtpResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "GenerateOTP_Res [response=" + response + "]";
	}
	
}
