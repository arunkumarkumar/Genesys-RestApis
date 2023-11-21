package com.api.ivr.model.customercontactdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerContactDetail_Attributes {

	@JsonProperty("attention-party")
	private String attentionparty;

	@JsonProperty("alert-required-for-contact-amendment")
	private String alertrequiredforcontactamendment;

	@JsonProperty("contact-type-code")
	private String contacttypecode;

	@JsonProperty("primary-contact")
	private String primarycontact;

	@JsonProperty("contact")
	private String contact;

	@JsonProperty("profile-id")
	private String profileid;

	@JsonProperty("sequence-number")
	private String sequencenumber;

	@JsonProperty("contact-type-classification-code")
	private String contacttypeclassificationcode;

	@JsonProperty("alert-suppress-reason-code")
	private String alertsuppressreasoncode;

	public String getAttentionparty() {
		return attentionparty;
	}

	public void setAttentionparty(String attentionparty) {
		this.attentionparty = attentionparty;
	}

	public String getAlertrequiredforcontactamendment() {
		return alertrequiredforcontactamendment;
	}

	public void setAlertrequiredforcontactamendment(String alertrequiredforcontactamendment) {
		this.alertrequiredforcontactamendment = alertrequiredforcontactamendment;
	}

	public String getContacttypecode() {
		return contacttypecode;
	}

	public void setContacttypecode(String contacttypecode) {
		this.contacttypecode = contacttypecode;
	}

	public String getPrimarycontact() {
		return primarycontact;
	}

	public void setPrimarycontact(String primarycontact) {
		this.primarycontact = primarycontact;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getProfileid() {
		return profileid;
	}

	public void setProfileid(String profileid) {
		this.profileid = profileid;
	}

	public String getSequencenumber() {
		return sequencenumber;
	}

	public void setSequencenumber(String sequencenumber) {
		this.sequencenumber = sequencenumber;
	}

	public String getContacttypeclassificationcode() {
		return contacttypeclassificationcode;
	}

	public void setContacttypeclassificationcode(String contacttypeclassificationcode) {
		this.contacttypeclassificationcode = contacttypeclassificationcode;
	}

	public String getAlertsuppressreasoncode() {
		return alertsuppressreasoncode;
	}

	public void setAlertsuppressreasoncode(String alertsuppressreasoncode) {
		this.alertsuppressreasoncode = alertsuppressreasoncode;
	}

	@Override
	public String toString() {
		return "CustomerContactDetail_Attributes [attentionparty=" + attentionparty
				+ ", alertrequiredforcontactamendment=" + alertrequiredforcontactamendment + ", contacttypecode="
				+ contacttypecode + ", primarycontact=" + primarycontact + ", contact=" + contact + ", profileid="
				+ profileid + ", sequencenumber=" + sequencenumber + ", contacttypeclassificationcode="
				+ contacttypeclassificationcode + ", alertsuppressreasoncode=" + alertsuppressreasoncode + "]";
	}


}
