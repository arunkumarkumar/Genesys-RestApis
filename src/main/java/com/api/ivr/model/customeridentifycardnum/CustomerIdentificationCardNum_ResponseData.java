package com.api.ivr.model.customeridentifycardnum;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerIdentificationCardNum_ResponseData {
	@JsonProperty("data")
	private List<CustIdentificationCardNumData> data;
	
	@JsonProperty("links")
	private CustomerIdentificationCardNum_MultipleLinks links;
	
	@JsonProperty("meta")
	private CustomerIdentification_meta meta;

	public CustomerIdentification_meta getMeta() {
		return meta;
	}

	public void setMeta(CustomerIdentification_meta meta) {
		this.meta = meta;
	}

	@Override
	public String toString() {
		return "CustomerIdentificationCardNum_ResponseData [data=" + data + ", links=" + links + ", meta=" + meta + "]";
	}

	public CustomerIdentificationCardNum_MultipleLinks getLinks() {
		return links;
	}

	public void setLinks(CustomerIdentificationCardNum_MultipleLinks links) {
		this.links = links;
	}

	public List<CustIdentificationCardNumData> getData() {
		return data;
	}

	public void setData(List<CustIdentificationCardNumData> data) {
		this.data = data;
	}

}
