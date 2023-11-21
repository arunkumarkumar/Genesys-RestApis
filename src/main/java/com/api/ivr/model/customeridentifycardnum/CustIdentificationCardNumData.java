package com.api.ivr.model.customeridentifycardnum;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustIdentificationCardNumData {
	
	@Override
	public String toString() {
		return "CustIdentificationCardNumData [id=" + id + ", type=" + type + ", links=" + links + ", attributes="
				+ attributes + "]";
	}
	@JsonProperty("id")
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CustomerIdentifyCardNum_Links getLinks() {
		return links;
	}

	public void setLinks(CustomerIdentifyCardNum_Links links) {
		this.links = links;
	}
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("links")
	private CustomerIdentifyCardNum_Links links;
	
    @JsonProperty("attributes")
	public CustIdentificationCardNumAttributes attributes;

	public CustIdentificationCardNumAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(CustIdentificationCardNumAttributes attributes) {
		this.attributes = attributes;
	}


}
