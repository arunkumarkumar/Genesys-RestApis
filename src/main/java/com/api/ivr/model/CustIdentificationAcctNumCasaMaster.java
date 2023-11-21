package com.api.ivr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustIdentificationAcctNumCasaMaster {
	@JsonProperty("operating-instructions")
	private String operatingInstructions;
	@JsonProperty("arm-code")
	private String armCode;
	@JsonProperty("account-type")
	private String accountType;
	@JsonProperty("customer-segment-code")
	private String customerSegmentCode;
	@JsonProperty("institutional-class-code")
	private String institutionalClassCode;
	@JsonProperty("arm-name")
	private String armName;
	@JsonProperty("is-staff")
	private String isStaff;
	@JsonProperty("isic-code")
	private String isicCode;
	@JsonProperty("customer-type-code")
	private String customerTypeCode;

	public String getOperatingInstructions() {
		return operatingInstructions;
	}

	public void setOperatingInstructions(String operatingInstructions) {
		this.operatingInstructions = operatingInstructions;
	}

	public String getArmCode() {
		return armCode;
	}

	public void setArmCode(String armCode) {
		this.armCode = armCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCustomerSegmentCode() {
		return customerSegmentCode;
	}

	public void setCustomerSegmentCode(String customerSegmentCode) {
		this.customerSegmentCode = customerSegmentCode;
	}

	public String getInstitutionalClassCode() {
		return institutionalClassCode;
	}

	public void setInstitutionalClassCode(String institutionalClassCode) {
		this.institutionalClassCode = institutionalClassCode;
	}

	public String getArmName() {
		return armName;
	}

	public void setArmName(String armName) {
		this.armName = armName;
	}

	public String getIsStaff() {
		return isStaff;
	}

	public void setIsStaff(String isStaff) {
		this.isStaff = isStaff;
	}

	public String getIsicCode() {
		return isicCode;
	}

	public void setIsicCode(String isicCode) {
		this.isicCode = isicCode;
	}

	public String getCustomerTypeCode() {
		return customerTypeCode;
	}

	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}


}
