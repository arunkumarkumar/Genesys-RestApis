package com.api.ivr.model;


public class IdentifyCustomer_Req extends CommonInput {
	
	private String mobile;
	private String ucid;
	private String sessionid;
	private String hotline;
	
	
	@Override
	public String toString() {
		return "IdentifyCustomer_Req [mobile=" + mobile + ", ucid=" + ucid + ", sessionid=" + sessionid + ", hotline="
				+ hotline + "]";
	}

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



	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



}
