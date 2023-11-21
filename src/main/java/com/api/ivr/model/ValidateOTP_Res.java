package com.api.ivr.model;

public class ValidateOTP_Res extends CommonOutput {
private ValidateOTPResponse_Data response;

public ValidateOTPResponse_Data getResponse() {
	return response;
}

public void setResponse(ValidateOTPResponse_Data response) {
	this.response = response;
}

@Override
public String toString() {
	return "ValidateOTP_Res [response=" + response + "]";
}
}
