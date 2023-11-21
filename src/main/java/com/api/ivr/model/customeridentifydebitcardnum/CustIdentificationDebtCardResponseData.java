package com.api.ivr.model.customeridentifydebitcardnum;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationDebtCardResponseData {
	@JsonProperty("header")
	private Header header;
	@JsonProperty("response-code")
	private String responseCode;
	@JsonProperty("response-text")
	private String responseText;
	@JsonProperty("card-number")
	private String cardNumber;
	@JsonProperty("card-sequence-number")
	private String cardSequenceNumber;
	@JsonProperty("card-token")
	private String cardToken;
	@JsonProperty("encrypted-data")
	private String encryptedData;
	@JsonProperty("account-block")
	private List<AccountBlock> accountBlock = null;
	@JsonProperty("card-status")
	private String cardStatus;
	@JsonProperty("reason-code")
	private String reasonCode;
	@JsonProperty("reason-code-description")
	private String reasonCodeDescription;
	@JsonProperty("pin-retry-exceed-flag")
	private String pinRetryExceedFlag;
	@JsonProperty("effective-date")
	private String effectiveDate;
	@JsonProperty("effective-time")
	private String effectiveTime;
	@JsonProperty("last-effective-date")
	private String lastEffectiveDate;
	@JsonProperty("last-effective-time")
	private String lastEffectiveTime;
	@JsonProperty("card-expiry")
	private String cardExpiry;
	@JsonProperty("card-type")
	private String cardType;
	@JsonProperty("card-sub-type")
	private String cardSubType;
	@JsonProperty("card-issue-type")
	private String cardIssueType;
	@JsonProperty("card-perso-type")
	private String cardPersoType;
	@JsonProperty("card-issue-date")
	private String cardIssueDate;
	@JsonProperty("card-activation-channel")
	private String cardActivationChannel;
	@JsonProperty("card-activation-date")
	private String cardActivationDate;
	@JsonProperty("card-deactivation-channel")
	private String cardDeactivationChannel;
	@JsonProperty("card-deactivation-date")
	private String cardDeactivationDate;
	@JsonProperty("pin-create-date")
	private String pinCreateDate;
	@JsonProperty("pin-create-channel")
	private String pinCreateChannel;
	@JsonProperty("token-indicator")
	private String tokenIndicator;
	@JsonProperty("replace-card-indicator")
	private String replaceCardIndicator;
	@JsonProperty("emboss-name")
	private String embossName;
	@JsonProperty("emboss-line2")
	private String embossLine2;
	@JsonProperty("relationship-id")
	private String relationshipId;
	@JsonProperty("date-of-birth")
	private String dateOfBirth;
	@JsonProperty("mobile-number")
	private String mobileNumber;
	@JsonProperty("wallet-registration-flag")
	private String walletRegistrationFlag;
	@JsonProperty("corporate-relationship-id")
	private String corporateRelationshipId;
	@JsonProperty("corporate-card-indicator")
	private String corporateCardIndicator;
	@JsonProperty("card-category")
	private String cardCategory;
	@JsonProperty("wallet-details")
	private List<WalletDetail> walletDetails = null;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardSequenceNumber() {
		return cardSequenceNumber;
	}

	public void setCardSequenceNumber(String cardSequenceNumber) {
		this.cardSequenceNumber = cardSequenceNumber;
	}

	public String getCardToken() {
		return cardToken;
	}

	public void setCardToken(String cardToken) {
		this.cardToken = cardToken;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public List<AccountBlock> getAccountBlock() {
		return accountBlock;
	}

	public void setAccountBlock(List<AccountBlock> accountBlock) {
		this.accountBlock = accountBlock;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonCodeDescription() {
		return reasonCodeDescription;
	}

	public void setReasonCodeDescription(String reasonCodeDescription) {
		this.reasonCodeDescription = reasonCodeDescription;
	}

	public String getPinRetryExceedFlag() {
		return pinRetryExceedFlag;
	}

	public void setPinRetryExceedFlag(String pinRetryExceedFlag) {
		this.pinRetryExceedFlag = pinRetryExceedFlag;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getLastEffectiveDate() {
		return lastEffectiveDate;
	}

	public void setLastEffectiveDate(String lastEffectiveDate) {
		this.lastEffectiveDate = lastEffectiveDate;
	}

	public String getLastEffectiveTime() {
		return lastEffectiveTime;
	}

	public void setLastEffectiveTime(String lastEffectiveTime) {
		this.lastEffectiveTime = lastEffectiveTime;
	}

	public String getCardExpiry() {
		return cardExpiry;
	}

	public void setCardExpiry(String cardExpiry) {
		this.cardExpiry = cardExpiry;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardSubType() {
		return cardSubType;
	}

	public void setCardSubType(String cardSubType) {
		this.cardSubType = cardSubType;
	}

	public String getCardIssueType() {
		return cardIssueType;
	}

	public void setCardIssueType(String cardIssueType) {
		this.cardIssueType = cardIssueType;
	}

	public String getCardPersoType() {
		return cardPersoType;
	}

	public void setCardPersoType(String cardPersoType) {
		this.cardPersoType = cardPersoType;
	}

	public String getCardIssueDate() {
		return cardIssueDate;
	}

	public void setCardIssueDate(String cardIssueDate) {
		this.cardIssueDate = cardIssueDate;
	}

	public String getCardActivationChannel() {
		return cardActivationChannel;
	}

	public void setCardActivationChannel(String cardActivationChannel) {
		this.cardActivationChannel = cardActivationChannel;
	}

	public String getCardActivationDate() {
		return cardActivationDate;
	}

	public void setCardActivationDate(String cardActivationDate) {
		this.cardActivationDate = cardActivationDate;
	}

	public String getCardDeactivationChannel() {
		return cardDeactivationChannel;
	}

	public void setCardDeactivationChannel(String cardDeactivationChannel) {
		this.cardDeactivationChannel = cardDeactivationChannel;
	}

	public String getCardDeactivationDate() {
		return cardDeactivationDate;
	}

	public void setCardDeactivationDate(String cardDeactivationDate) {
		this.cardDeactivationDate = cardDeactivationDate;
	}

	public String getPinCreateDate() {
		return pinCreateDate;
	}

	public void setPinCreateDate(String pinCreateDate) {
		this.pinCreateDate = pinCreateDate;
	}

	public String getPinCreateChannel() {
		return pinCreateChannel;
	}

	public void setPinCreateChannel(String pinCreateChannel) {
		this.pinCreateChannel = pinCreateChannel;
	}

	public String getTokenIndicator() {
		return tokenIndicator;
	}

	public void setTokenIndicator(String tokenIndicator) {
		this.tokenIndicator = tokenIndicator;
	}

	public String getReplaceCardIndicator() {
		return replaceCardIndicator;
	}

	public void setReplaceCardIndicator(String replaceCardIndicator) {
		this.replaceCardIndicator = replaceCardIndicator;
	}

	public String getEmbossName() {
		return embossName;
	}

	public void setEmbossName(String embossName) {
		this.embossName = embossName;
	}

	public String getEmbossLine2() {
		return embossLine2;
	}

	public void setEmbossLine2(String embossLine2) {
		this.embossLine2 = embossLine2;
	}

	public String getRelationshipId() {
		return relationshipId;
	}

	public void setRelationshipId(String relationshipId) {
		this.relationshipId = relationshipId;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getWalletRegistrationFlag() {
		return walletRegistrationFlag;
	}

	public void setWalletRegistrationFlag(String walletRegistrationFlag) {
		this.walletRegistrationFlag = walletRegistrationFlag;
	}

	public String getCorporateRelationshipId() {
		return corporateRelationshipId;
	}

	public void setCorporateRelationshipId(String corporateRelationshipId) {
		this.corporateRelationshipId = corporateRelationshipId;
	}

	public String getCorporateCardIndicator() {
		return corporateCardIndicator;
	}

	public void setCorporateCardIndicator(String corporateCardIndicator) {
		this.corporateCardIndicator = corporateCardIndicator;
	}

	public String getCardCategory() {
		return cardCategory;
	}

	@Override
	public String toString() {
		return "CustIdentificationDebtCardResponseData [header=" + header + ", responseCode=" + responseCode
				+ ", responseText=" + responseText + ", cardNumber=" + cardNumber + ", cardSequenceNumber="
				+ cardSequenceNumber + ", cardToken=" + cardToken + ", encryptedData=" + encryptedData
				+ ", accountBlock=" + accountBlock + ", cardStatus=" + cardStatus + ", reasonCode=" + reasonCode
				+ ", reasonCodeDescription=" + reasonCodeDescription + ", pinRetryExceedFlag=" + pinRetryExceedFlag
				+ ", effectiveDate=" + effectiveDate + ", effectiveTime=" + effectiveTime + ", lastEffectiveDate="
				+ lastEffectiveDate + ", lastEffectiveTime=" + lastEffectiveTime + ", cardExpiry=" + cardExpiry
				+ ", cardType=" + cardType + ", cardSubType=" + cardSubType + ", cardIssueType=" + cardIssueType
				+ ", cardPersoType=" + cardPersoType + ", cardIssueDate=" + cardIssueDate + ", cardActivationChannel="
				+ cardActivationChannel + ", cardActivationDate=" + cardActivationDate + ", cardDeactivationChannel="
				+ cardDeactivationChannel + ", cardDeactivationDate=" + cardDeactivationDate + ", pinCreateDate="
				+ pinCreateDate + ", pinCreateChannel=" + pinCreateChannel + ", tokenIndicator=" + tokenIndicator
				+ ", replaceCardIndicator=" + replaceCardIndicator + ", embossName=" + embossName + ", embossLine2="
				+ embossLine2 + ", relationshipId=" + relationshipId + ", dateOfBirth=" + dateOfBirth
				+ ", mobileNumber=" + mobileNumber + ", walletRegistrationFlag=" + walletRegistrationFlag
				+ ", corporateRelationshipId=" + corporateRelationshipId + ", corporateCardIndicator="
				+ corporateCardIndicator + ", cardCategory=" + cardCategory + ", walletDetails=" + walletDetails + "]";
	}

	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}

	public List<WalletDetail> getWalletDetails() {
		return walletDetails;
	}

	public void setWalletDetails(List<WalletDetail> walletDetails) {
		this.walletDetails = walletDetails;
	}

}
