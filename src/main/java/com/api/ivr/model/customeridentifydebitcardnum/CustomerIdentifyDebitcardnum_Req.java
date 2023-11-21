package com.api.ivr.model.customeridentifydebitcardnum;

import com.api.ivr.model.CommonInput;

public class CustomerIdentifyDebitcardnum_Req extends CommonInput {
	private String cardnumber;
	private String sessionid;
	private String ucid;
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	@Override
	public String toString() {
		return "CustomerIdentifyDebitcardnum_Req [cardnumber=" + cardnumber + ", sessionid=" + sessionid + ", ucid="
				+ ucid + ", hotline=" + hotline + "]";
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
	private String hotline;

}
