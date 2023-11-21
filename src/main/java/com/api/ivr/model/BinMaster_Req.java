package com.api.ivr.model;

public class BinMaster_Req  {
	
	private String binNumber;
	public String getBinNumber() {
		return binNumber;
	}
	public void setBinNumber(String binNumber) {
		this.binNumber = binNumber;
	}
	private String sessionId;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@Override
	public String toString() {
		return "BinMaster_Req [cli=" + binNumber + ", sessionId=" + sessionId + "]";
	}
	

}
