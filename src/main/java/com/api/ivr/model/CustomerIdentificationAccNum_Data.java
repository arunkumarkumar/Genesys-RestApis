package com.api.ivr.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CustomerIdentificationAccNum_Data {

	@Override
	public String toString() {
		return "CustomerIdentificationAccNum_Data [id=" + id + ", type=" + type + ", attributes=" + attributes
				+ ", links=" + links + "]";
	}
	
	@JsonProperty("id")
	private String id;
	@JsonProperty("type")
	private String type;
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

	@JsonProperty("attributes")
	private CustIdentificationAcctNumAttributes attributes;
	
	@JsonProperty("links")
	private CusAccidentifyLinks links;

	public CusAccidentifyLinks getLinks() {
		return links;
	}

	public void setLinks(CusAccidentifyLinks links) {
		this.links = links;
	}

	public CustIdentificationAcctNumAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(CustIdentificationAcctNumAttributes attributes) {
		this.attributes = attributes;
	}


}
