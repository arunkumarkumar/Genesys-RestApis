package com.api.ivr.model;

public class ValidateOTP_Req extends CommonInput {
	private String otp;
	private String encryptedBlock;
	private String modulus;
	private String exponent;
	private String keyIndex;
	private String otpSn;
	private String relID;
	private String hotline;
	private String ucid;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "ValidateOTP_Req [otp=" + otp + ", encryptedBlock=" + encryptedBlock + ", modulus=" + modulus
				+ ", exponent=" + exponent + ", keyIndex=" + keyIndex + ", otpSn=" + otpSn + ", relID=" + relID
				+ ", hotline=" + hotline + ", ucid=" + ucid + "]";
	}

	public String getEncryptedBlock() {
		return encryptedBlock;
	}

	public void setEncryptedBlock(String encryptedBlock) {
		this.encryptedBlock = encryptedBlock;
	}

	public String getModulus() {
		return modulus;
	}

	public void setModulus(String modulus) {
		this.modulus = modulus;
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

	public String getRelID() {
		return relID;
	}

	public void setRelID(String relID) {
		this.relID = relID;
	}

	public String getHotline() {
		return hotline;
	}

	public void setHotline(String hotline) {
		this.hotline = hotline;
	}

	public String getUcid() {
		return ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}

}
