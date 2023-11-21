package com.api.ivr.model.customercontactdetails;

import com.api.ivr.model.CommonInput;

public class CustomerContactDetails_Req extends CommonInput {
	private String relid;
	private String sessionid;
	private String hotline;
	private String ucid;
	public String getRelid() {
		return relid;
	}
	public void setRelid(String relid) {
		this.relid = relid;
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
	@Override
	public String toString() {
		return "CustomerContactDetails_Req [relid=" + relid + ", sessionid=" + sessionid + ", hotline=" + hotline
				+ ", ucid=" + ucid + "]";
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
