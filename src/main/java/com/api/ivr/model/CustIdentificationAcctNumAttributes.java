package com.api.ivr.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CustIdentificationAcctNumAttributes {
	
	@JsonProperty("casa-customerlinks")
	private Object casaCustomerlinks;
	@JsonProperty("casa-profile")
	private CustIdentificationAcctNumCasaProfile casaProfile;
	@JsonProperty("casa-customers")
	private List<CustIdentificationAcctNumCasaCustomer> casaCustomers = null;
	@JsonProperty("casa-link-customers")
	private Object casaLinkCustomers;
	@JsonProperty("casa-master")
	private CustIdentificationAcctNumCasaMaster casaMaster;

	public Object getCasaCustomerlinks() {
		return casaCustomerlinks;
	}

	public void setCasaCustomerlinks(Object casaCustomerlinks) {
		this.casaCustomerlinks = casaCustomerlinks;
	}

	public CustIdentificationAcctNumCasaProfile getCasaProfile() {
		return casaProfile;
	}

	public void setCasaProfile(CustIdentificationAcctNumCasaProfile casaProfile) {
		this.casaProfile = casaProfile;
	}

	public List<CustIdentificationAcctNumCasaCustomer> getCasaCustomers() {
		return casaCustomers;
	}

	public void setCasaCustomers(List<CustIdentificationAcctNumCasaCustomer> casaCustomers) {
		this.casaCustomers = casaCustomers;
	}

	public Object getCasaLinkCustomers() {
		return casaLinkCustomers;
	}

	public void setCasaLinkCustomers(Object casaLinkCustomers) {
		this.casaLinkCustomers = casaLinkCustomers;
	}

	public CustIdentificationAcctNumCasaMaster getCasaMaster() {
		return casaMaster;
	}

	public void setCasaMaster(CustIdentificationAcctNumCasaMaster casaMaster) {
		this.casaMaster = casaMaster;
	}


}
