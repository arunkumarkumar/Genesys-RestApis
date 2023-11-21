package com.api.ivr.model.setpreferredlanguage;

import com.api.ivr.model.CommonOutput;

public class SetPreferredLanguage_Res extends CommonOutput {
	@Override
	public String toString() {
		return "SetPreferredLanguage_Res [status=" + status + "]";
	}

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
