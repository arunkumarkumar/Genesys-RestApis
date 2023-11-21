package com.api.ivr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustIdentificationRMNDatum {
	
	@JsonProperty("attributes")
	private CusIdentificationRMNAttributes attributes;
	
	@JsonProperty("id")
    private String id;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("links")
	private CusIdentificationRMNLinks links;

	public CusIdentificationRMNLinks getLinks() {
		return links;
	}

	public void setLinks(CusIdentificationRMNLinks links) {
		this.links = links;
	}

	public CusIdentificationRMNAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(CusIdentificationRMNAttributes attributes) {
		this.attributes = attributes;
	}

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

	@Override
	public String toString() {
		return "CustIdentificationRMNDatum [attributes=" + attributes + ", id=" + id + ", type=" + type + "]";
	}
	
	

}
