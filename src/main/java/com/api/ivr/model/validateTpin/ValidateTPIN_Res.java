package com.api.ivr.model.validateTpin;

import com.api.ivr.model.CommonOutput;

public class ValidateTPIN_Res extends CommonOutput {
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ValidateOTP_Res [code=" + code + "]";
	}

}
