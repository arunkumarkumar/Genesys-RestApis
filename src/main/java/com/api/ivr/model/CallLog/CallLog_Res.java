package com.api.ivr.model.CallLog;

import com.api.ivr.model.CommonOutput;

public class CallLog_Res extends CommonOutput {
	private String status;

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "CallLog_Res [status=" + status + "]";
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
