package com.api.ivr.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CustomerIdentificationResponseData {
	
	@JsonProperty("data")
	private List<CustomerIdentificationAccNum_Data> data = null;

	public List<CustomerIdentificationAccNum_Data> getData() {
		return data;
	}

	public void setData(List<CustomerIdentificationAccNum_Data> data) {
		this.data = data;
	}

}
