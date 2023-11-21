package com.api.ivr.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateOtpResponseData {
	String encryptedBlock;
	String code = "";
	String exponent = "";
	String keyIndex = "";
	String otpSn = "";

	public String getEncryptedBlock() {
		return encryptedBlock;
	}

	public void setEncryptedBlock(String encryptedBlock) {
		this.encryptedBlock = encryptedBlock;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExponent() {
		return exponent;
	}

	public void setExponent(String exponent) {
		this.exponent = exponent;
	}

	public String getKeyIndex() {
		return keyIndex;
	}

	public void setKeyIndex(String keyIndex) {
		this.keyIndex = keyIndex;
	}

	public String getOtpSn() {
		return otpSn;
	}

	public void setOtpSn(String otpSn) {
		this.otpSn = otpSn;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
