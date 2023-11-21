package com.api.ivr.model.customeridentifydebitcardnum;

import com.api.ivr.model.CommonOutput;

public class CustomerIdentifyDebitcardnum_Res extends CommonOutput {
	private CustIdentificationDebtCardResponseData response;

	public CustIdentificationDebtCardResponseData getResponse() {
		return response;
	}

	public void setResponse(CustIdentificationDebtCardResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "CustomerIdentifyDebitcardnum_Res [response=" + response + "]";
	}

}
