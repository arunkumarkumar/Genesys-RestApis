package com.api.ivr.model.customercontactdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerContactDetailss_data {

	private CustomerContactDetail_Attributes attributes;

	public CustomerContactDetail_Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(CustomerContactDetail_Attributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "CustomerContactDetail_Data [attributes=" + attributes + "]";
	}
	


}
