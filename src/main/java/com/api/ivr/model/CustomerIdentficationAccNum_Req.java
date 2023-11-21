package com.api.ivr.model;

public class CustomerIdentficationAccNum_Req extends CommonInput {
	private String acctNum;

	private String currency_code;
	
	private String sessionid;
	
	private String ucid;
	
	private String hotline;


	public String getAcctNum() {
		return acctNum;
	}

	@Override
	public String toString() {
		return "CustomerIdentficationAccNum [acctNum=" + acctNum + ", currency_code=" + currency_code + ", sessionid="
				+ sessionid + "]";
	}

	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
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
