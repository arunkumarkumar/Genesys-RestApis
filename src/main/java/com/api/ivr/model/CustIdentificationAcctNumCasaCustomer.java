package com.api.ivr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustIdentificationAcctNumCasaCustomer {
	@JsonProperty("profile-id")
	private String profileId;
	@JsonProperty("primary-flag")
	private String primaryFlag;
	@JsonProperty("full-name")
	private String fullName;

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "CustIdentificationAcctNumCasaCustomer [profileId=" + profileId + ", primaryFlag=" + primaryFlag
				+ ", fullName=" + fullName + "]";
	}

}
