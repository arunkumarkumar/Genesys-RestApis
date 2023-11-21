package com.api.ivr.model;
public class GenerateOTP_Req extends CommonInput {
	private String mobileNumber;
	private String relId;
	private String language;
	private String ucid;
	private String sessionid;
	private String hotline;

	public String getUcid() {
		return ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getHotline() {
		return hotline;
	}

	public void setHotline(String hotline) {
		this.hotline = hotline;
	}

	public void setMobileNumber(String mobile) {
		this.mobileNumber = mobile;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "GenerateOTP_Req [mobileNumber=" + mobileNumber + ", relId=" + relId + ", language=" + language
				+ ", ucid=" + ucid + ", sessionid=" + sessionid + ", hotline=" + hotline + "]";
	}



}
