package com.api.ivr.model.customeridentifydebitcardnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletDetail {

	@JsonProperty("token-requestor-id")
	private String tokenRequestorId;
	@JsonProperty("token-number")
	private String tokenNumber;
	@JsonProperty("token-activation-date")
	private String tokenActivationDate;
	@JsonProperty("token-activation-time")
	private String tokenActivationTime;
	@JsonProperty("token-status")
	private String tokenStatus;
	@JsonProperty("token-expiry-date")
	private String tokenExpiryDate;
	@JsonProperty("token-reference-id")
	private String tokenReferenceId;
	@JsonProperty("token-type")
	private String tokenType;
	@JsonProperty("tokenized-status")
	private String tokenizedStatus;
	@JsonProperty("tokenized-status-date")
	private String tokenizedStatusDate;
	@JsonProperty("tokenized-status-time")
	private String tokenizedStatusTime;
	@JsonProperty("number-of-active-tokens")
	private String numberOfActiveTokens;

	public String getTokenRequestorId() {
		return tokenRequestorId;
	}

	public void setTokenRequestorId(String tokenRequestorId) {
		this.tokenRequestorId = tokenRequestorId;
	}

	public String getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public String getTokenActivationDate() {
		return tokenActivationDate;
	}

	public void setTokenActivationDate(String tokenActivationDate) {
		this.tokenActivationDate = tokenActivationDate;
	}

	public String getTokenActivationTime() {
		return tokenActivationTime;
	}

	public void setTokenActivationTime(String tokenActivationTime) {
		this.tokenActivationTime = tokenActivationTime;
	}

	public String getTokenStatus() {
		return tokenStatus;
	}

	public void setTokenStatus(String tokenStatus) {
		this.tokenStatus = tokenStatus;
	}

	public String getTokenExpiryDate() {
		return tokenExpiryDate;
	}

	public void setTokenExpiryDate(String tokenExpiryDate) {
		this.tokenExpiryDate = tokenExpiryDate;
	}

	public String getTokenReferenceId() {
		return tokenReferenceId;
	}

	public void setTokenReferenceId(String tokenReferenceId) {
		this.tokenReferenceId = tokenReferenceId;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getTokenizedStatus() {
		return tokenizedStatus;
	}

	public void setTokenizedStatus(String tokenizedStatus) {
		this.tokenizedStatus = tokenizedStatus;
	}

	public String getTokenizedStatusDate() {
		return tokenizedStatusDate;
	}

	public void setTokenizedStatusDate(String tokenizedStatusDate) {
		this.tokenizedStatusDate = tokenizedStatusDate;
	}

	public String getTokenizedStatusTime() {
		return tokenizedStatusTime;
	}

	public void setTokenizedStatusTime(String tokenizedStatusTime) {
		this.tokenizedStatusTime = tokenizedStatusTime;
	}

	public String getNumberOfActiveTokens() {
		return numberOfActiveTokens;
	}

	public void setNumberOfActiveTokens(String numberOfActiveTokens) {
		this.numberOfActiveTokens = numberOfActiveTokens;
	}

	@Override
	public String toString() {
		return "WalletDetail [tokenRequestorId=" + tokenRequestorId + ", tokenNumber=" + tokenNumber
				+ ", tokenActivationDate=" + tokenActivationDate + ", tokenActivationTime=" + tokenActivationTime
				+ ", tokenStatus=" + tokenStatus + ", tokenExpiryDate=" + tokenExpiryDate + ", tokenReferenceId="
				+ tokenReferenceId + ", tokenType=" + tokenType + ", tokenizedStatus=" + tokenizedStatus
				+ ", tokenizedStatusDate=" + tokenizedStatusDate + ", tokenizedStatusTime=" + tokenizedStatusTime
				+ ", numberOfActiveTokens=" + numberOfActiveTokens + "]";
	}



}
