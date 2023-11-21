package com.api.ivr.model.customeridentifydebitcardnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBlock {

	@JsonProperty("account-no")
	private String accountNo;
	@JsonProperty("encrypted-account-number")
	private String encryptedAccountNumber;
	@JsonProperty("account-currency")
	private String accountCurrency;
	@JsonProperty("primary-flag")
	private String primaryFlag;
	@JsonProperty("account-type")
	private String accountType;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getEncryptedAccountNumber() {
		return encryptedAccountNumber;
	}

	public void setEncryptedAccountNumber(String encryptedAccountNumber) {
		this.encryptedAccountNumber = encryptedAccountNumber;
	}

	public String getAccountCurrency() {
		return accountCurrency;
	}

	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "AccountBlock [accountNo=" + accountNo + ", encryptedAccountNumber=" + encryptedAccountNumber
				+ ", accountCurrency=" + accountCurrency + ", primaryFlag=" + primaryFlag + ", accountType="
				+ accountType + "]";
	}

}
