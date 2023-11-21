package com.api.ivr.model.preferredlanguage;

import com.api.ivr.model.CommonOutput;

public class PreferredLanguage_Res extends CommonOutput {
	private String langCode;

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	@Override
	public String toString() {
		return "PreferredLanguage_Res [langCode=" + langCode + "]";
	}
	

}
