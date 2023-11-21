package com.api.ivr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationAcctNumCasaProfile {
	

	@JsonProperty("casa-currency-code")
	private String casaCurrencyCode;
	@JsonProperty("casa-number")
	private String casaNumber;
	@JsonProperty("product-code")
	private String productCode;
	@JsonProperty("casa-short-name")
	private String casaShortName;
	@JsonProperty("casa-title")
	private String casaTitle;
	@JsonProperty("segment-code")
	private String segmentCode;
	@JsonProperty("account-branch")
	private String accountBranch;
	@JsonProperty("branch-name")
	private String branchName;
	@JsonProperty("account-open-date")
	private String accountOpenDate;
	@JsonProperty("account-class-code")
	private String accountClassCode;
	@JsonProperty("consolidated-statement-flag")
	private String consolidatedStatementFlag;
	@JsonProperty("account-closure-reason")
	private String accountClosureReason;
	@JsonProperty("account-close-date")
	private String accountCloseDate;
	@JsonProperty("account-current-status")
	private String accountCurrentStatus;
	@JsonProperty("last-debit-transaction-date")
	private String lastDebitTransactionDate;
	@JsonProperty("last-credit-transaction-date")
	private String lastCreditTransactionDate;
	@JsonProperty("master-number")
	private String masterNumber;
	@JsonProperty("account-category")
	private String accountCategory;
	@JsonProperty("gl-department-id")
	private String glDepartmentId;
	@JsonProperty("auto-close")
	private String autoClose;
	@JsonProperty("waiver-excess-interest-flag")
	private String waiverExcessInterestFlag;
	@JsonProperty("credit-interest-indicator")
	private String creditInterestIndicator;
	@JsonProperty("debit-interest-indicator")
	private String debitInterestIndicator;
	@JsonProperty("include-interest-pool")
	private String includeInterestPool;
	@JsonProperty("debit-interest-product-code")
	private String debitInterestProductCode;
	@JsonProperty("interest-in-suspense")
	private String interestInSuspense;
	@JsonProperty("interest-code")
	private String interestCode;
	@JsonProperty("bulk-charges")
	private String bulkCharges;
	@JsonProperty("advise-interest-indicator")
	private String adviseInterestIndicator;
	@JsonProperty("suppress-statement")
	private String suppressStatement;
	@JsonProperty("credit-interest-product-code")
	private String creditInterestProductCode;
	@JsonProperty("interest-statement-indicator")
	private String interestStatementIndicator;
	@JsonProperty("suppress-advice")
	private String suppressAdvice;
	@JsonProperty("ignore-charge-advice")
	private String ignoreChargeAdvice;
	@JsonProperty("product-description")
	private String productDescription;

	public String getCasaCurrencyCode() {
		return casaCurrencyCode;
	}

	public void setCasaCurrencyCode(String casaCurrencyCode) {
		this.casaCurrencyCode = casaCurrencyCode;
	}

	public String getCasaNumber() {
		return casaNumber;
	}

	public void setCasaNumber(String casaNumber) {
		this.casaNumber = casaNumber;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCasaShortName() {
		return casaShortName;
	}

	public void setCasaShortName(String casaShortName) {
		this.casaShortName = casaShortName;
	}

	public String getCasaTitle() {
		return casaTitle;
	}

	public void setCasaTitle(String casaTitle) {
		this.casaTitle = casaTitle;
	}

	public String getSegmentCode() {
		return segmentCode;
	}

	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}

	public String getAccountBranch() {
		return accountBranch;
	}

	public void setAccountBranch(String accountBranch) {
		this.accountBranch = accountBranch;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAccountOpenDate() {
		return accountOpenDate;
	}

	public void setAccountOpenDate(String accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}

	public String getAccountClassCode() {
		return accountClassCode;
	}

	public void setAccountClassCode(String accountClassCode) {
		this.accountClassCode = accountClassCode;
	}

	public String getConsolidatedStatementFlag() {
		return consolidatedStatementFlag;
	}

	public void setConsolidatedStatementFlag(String consolidatedStatementFlag) {
		this.consolidatedStatementFlag = consolidatedStatementFlag;
	}

	public String getAccountClosureReason() {
		return accountClosureReason;
	}

	public void setAccountClosureReason(String accountClosureReason) {
		this.accountClosureReason = accountClosureReason;
	}

	public String getAccountCloseDate() {
		return accountCloseDate;
	}

	public void setAccountCloseDate(String accountCloseDate) {
		this.accountCloseDate = accountCloseDate;
	}

	public String getAccountCurrentStatus() {
		return accountCurrentStatus;
	}

	public void setAccountCurrentStatus(String accountCurrentStatus) {
		this.accountCurrentStatus = accountCurrentStatus;
	}

	public String getLastDebitTransactionDate() {
		return lastDebitTransactionDate;
	}

	public void setLastDebitTransactionDate(String lastDebitTransactionDate) {
		this.lastDebitTransactionDate = lastDebitTransactionDate;
	}

	public String getLastCreditTransactionDate() {
		return lastCreditTransactionDate;
	}

	public void setLastCreditTransactionDate(String lastCreditTransactionDate) {
		this.lastCreditTransactionDate = lastCreditTransactionDate;
	}

	public String getMasterNumber() {
		return masterNumber;
	}

	public void setMasterNumber(String masterNumber) {
		this.masterNumber = masterNumber;
	}

	public String getAccountCategory() {
		return accountCategory;
	}

	public void setAccountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
	}

	public String getGlDepartmentId() {
		return glDepartmentId;
	}

	public void setGlDepartmentId(String glDepartmentId) {
		this.glDepartmentId = glDepartmentId;
	}

	public String getAutoClose() {
		return autoClose;
	}

	public void setAutoClose(String autoClose) {
		this.autoClose = autoClose;
	}

	public String getWaiverExcessInterestFlag() {
		return waiverExcessInterestFlag;
	}

	public void setWaiverExcessInterestFlag(String waiverExcessInterestFlag) {
		this.waiverExcessInterestFlag = waiverExcessInterestFlag;
	}

	public String getCreditInterestIndicator() {
		return creditInterestIndicator;
	}

	public void setCreditInterestIndicator(String creditInterestIndicator) {
		this.creditInterestIndicator = creditInterestIndicator;
	}

	public String getDebitInterestIndicator() {
		return debitInterestIndicator;
	}

	public void setDebitInterestIndicator(String debitInterestIndicator) {
		this.debitInterestIndicator = debitInterestIndicator;
	}

	public String getIncludeInterestPool() {
		return includeInterestPool;
	}

	public void setIncludeInterestPool(String includeInterestPool) {
		this.includeInterestPool = includeInterestPool;
	}

	public String getDebitInterestProductCode() {
		return debitInterestProductCode;
	}

	public void setDebitInterestProductCode(String debitInterestProductCode) {
		this.debitInterestProductCode = debitInterestProductCode;
	}

	public String getInterestInSuspense() {
		return interestInSuspense;
	}

	public void setInterestInSuspense(String interestInSuspense) {
		this.interestInSuspense = interestInSuspense;
	}

	public String getInterestCode() {
		return interestCode;
	}

	public void setInterestCode(String interestCode) {
		this.interestCode = interestCode;
	}

	public String getBulkCharges() {
		return bulkCharges;
	}

	public void setBulkCharges(String bulkCharges) {
		this.bulkCharges = bulkCharges;
	}

	public String getAdviseInterestIndicator() {
		return adviseInterestIndicator;
	}

	public void setAdviseInterestIndicator(String adviseInterestIndicator) {
		this.adviseInterestIndicator = adviseInterestIndicator;
	}

	public String getSuppressStatement() {
		return suppressStatement;
	}

	public void setSuppressStatement(String suppressStatement) {
		this.suppressStatement = suppressStatement;
	}

	public String getCreditInterestProductCode() {
		return creditInterestProductCode;
	}

	public void setCreditInterestProductCode(String creditInterestProductCode) {
		this.creditInterestProductCode = creditInterestProductCode;
	}

	public String getInterestStatementIndicator() {
		return interestStatementIndicator;
	}

	public void setInterestStatementIndicator(String interestStatementIndicator) {
		this.interestStatementIndicator = interestStatementIndicator;
	}

	public String getSuppressAdvice() {
		return suppressAdvice;
	}

	public void setSuppressAdvice(String suppressAdvice) {
		this.suppressAdvice = suppressAdvice;
	}

	public String getIgnoreChargeAdvice() {
		return ignoreChargeAdvice;
	}

	public void setIgnoreChargeAdvice(String ignoreChargeAdvice) {
		this.ignoreChargeAdvice = ignoreChargeAdvice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	@Override
	public String toString() {
		return "CustIdentificationAcctNumCasaProfile [casaCurrencyCode=" + casaCurrencyCode + ", casaNumber="
				+ casaNumber + ", productCode=" + productCode + ", casaShortName=" + casaShortName + ", casaTitle="
				+ casaTitle + ", segmentCode=" + segmentCode + ", accountBranch=" + accountBranch + ", branchName="
				+ branchName + ", accountOpenDate=" + accountOpenDate + ", accountClassCode=" + accountClassCode
				+ ", consolidatedStatementFlag=" + consolidatedStatementFlag + ", accountClosureReason="
				+ accountClosureReason + ", accountCloseDate=" + accountCloseDate + ", accountCurrentStatus="
				+ accountCurrentStatus + ", lastDebitTransactionDate=" + lastDebitTransactionDate
				+ ", lastCreditTransactionDate=" + lastCreditTransactionDate + ", masterNumber=" + masterNumber
				+ ", accountCategory=" + accountCategory + ", glDepartmentId=" + glDepartmentId + ", autoClose="
				+ autoClose + ", waiverExcessInterestFlag=" + waiverExcessInterestFlag + ", creditInterestIndicator="
				+ creditInterestIndicator + ", debitInterestIndicator=" + debitInterestIndicator
				+ ", includeInterestPool=" + includeInterestPool + ", debitInterestProductCode="
				+ debitInterestProductCode + ", interestInSuspense=" + interestInSuspense + ", interestCode="
				+ interestCode + ", bulkCharges=" + bulkCharges + ", adviseInterestIndicator=" + adviseInterestIndicator
				+ ", suppressStatement=" + suppressStatement + ", creditInterestProductCode="
				+ creditInterestProductCode + ", interestStatementIndicator=" + interestStatementIndicator
				+ ", suppressAdvice=" + suppressAdvice + ", ignoreChargeAdvice=" + ignoreChargeAdvice
				+ ", productDescription=" + productDescription + "]";
	}


}
