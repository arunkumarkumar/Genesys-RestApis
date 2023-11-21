package com.api.ivr.model;

public class IdentifyCustomer_Res extends CommonOutput {
	private IdentifyCustomerRMNRespose response;
    private String RMNorNRMN;
	public String getRMNorNRMN() {
		return RMNorNRMN;
	}

	public void setRMNorNRMN(String rMNorNRMN) {
		RMNorNRMN = rMNorNRMN;
	}

	public IdentifyCustomerRMNRespose getResponse() {
		return response;
	}

	public void setResponse(IdentifyCustomerRMNRespose response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "IdentifyCustomer_Res [response=" + response + ", RMNorNRMN=" + RMNorNRMN + "]";
	}
	

}
