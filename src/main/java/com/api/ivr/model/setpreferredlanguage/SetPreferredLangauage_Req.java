package com.api.ivr.model.setpreferredlanguage;

public class SetPreferredLangauage_Req {
	private String cli;
	private String relid;
	private String sessionId;
	private String langcode;
	public String getCli() {
		return cli;
	}
	@Override
	public String toString() {
		return "SetPreferredLangauage_Req [cli=" + cli + ", relid=" + relid + ", sessionId=" + sessionId + ", langcode="
				+ langcode + "]";
	}
	public void setCli(String cli) {
		this.cli = cli;
	}
	public String getRelid() {
		return relid;
	}
	public void setRelid(String relid) {
		this.relid = relid;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getLangcode() {
		return langcode;
	}
	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

}
