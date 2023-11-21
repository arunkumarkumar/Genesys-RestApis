package com.api.ivr.model.validateTpin;

import com.api.ivr.model.CommonInput;

public class ValidateTPIN_Req extends CommonInput {
	private String userid;
	private String sessionid;
	private String password;
	private String ucid;
	private String hotline;
	@Override
	public String toString() {
		return "ValidateTPIN_Req [userid=" + userid + ", sessionid=" + sessionid + ", password=" + password + ", ucid="
				+ ucid + ", hotline=" + hotline + "]";
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUcid() {
		return ucid;
	}
	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
	public String getHotline() {
		return hotline;
	}
	public void setHotline(String hotline) {
		this.hotline = hotline;
	}

}
