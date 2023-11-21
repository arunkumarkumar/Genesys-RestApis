package com.api.ivr.model.customeridentifycardnum;

import com.api.ivr.model.CommonInput;

public class CustomerIdenificationCardNum_Req extends CommonInput {
	private String cardNumber;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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

	@Override
	public String toString() {
		return "CustomerIdenificationCardNum_Req [cardNumber=" + cardNumber + ", sessionid=" + sessionid + ", ucid="
				+ ucid + ", hotline=" + hotline + "]";
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

	private String sessionid;
	private String ucid;
	private String hotline;
}
