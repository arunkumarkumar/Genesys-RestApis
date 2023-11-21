package com.api.ivr.model;




import com.fasterxml.jackson.annotation.JsonProperty;

public class CustIdentificationRMNContact {
	@JsonProperty("contact")
	private String contact;
	@JsonProperty("sequence-number")
	private String sequenceNumber;
	@JsonProperty("contact-type-code")
	private String contactTypeCode;
	@JsonProperty("primary-contact")
	private String primaryContact;

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getContactTypeCode() {
		return contactTypeCode;
	}

	public void setContactTypeCode(String contactTypeCode) {
		this.contactTypeCode = contactTypeCode;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}



}
