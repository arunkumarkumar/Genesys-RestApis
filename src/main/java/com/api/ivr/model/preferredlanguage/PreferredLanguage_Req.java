package com.api.ivr.model.preferredlanguage;

public class PreferredLanguage_Req {
	private String cli;
	
	public String getCli() {
		return cli;
	}

	public void setCli(String cli) {
		this.cli = cli;
	}

	public String getSessionId() {
		return sessionId;
	}

	@Override
	public String toString() {
		return "PreferredLanguage_Req [cli=" + cli + ", sessionId=" + sessionId + "]";
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	private String sessionId;

}
