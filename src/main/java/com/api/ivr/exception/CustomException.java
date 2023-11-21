package com.api.ivr.exception;

/**
 * @author TA
 *
 */

public class CustomException extends RuntimeException {

	private String errorCode;
	private String errorMsg;

	public CustomException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}
}