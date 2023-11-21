package com.api.ivr.model;

public class ValidateOTPResponse_Data {
	private String statusCode;
	private String errorMessage;
	@Override
	public String toString() {
		return "ValidateOTPResponse_Data [statusCode=" + statusCode + ", errorMessage=" + errorMessage
				+ ", sessionTicket=" + sessionTicket + "]";
	}

	private String sessionTicket;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSessionTicket() {
		return sessionTicket;
	}

	public void setSessionTicket(String sessionTicket) {
		this.sessionTicket = sessionTicket;
	}

}
