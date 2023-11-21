package com.api.ivr.model.customeridentifycardnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationCardNumAttributes {

	@JsonProperty("block-code")
	public String blockCode;

	@JsonProperty("block-status")
	public String blockStatus;

	@JsonProperty("customer-id")
	public String customerId;

	@JsonProperty("card-status")
	public String cardStatus;

	@JsonProperty("card-num")
	public String cardNum;

	

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

	public String getBlockStatus() {
		return blockStatus;
	}

	public void setBlockStatus(String blockStatus) {
		this.blockStatus = blockStatus;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	@Override
	public String toString() {
		return "CustIdentificationCardNumAttributes [blockCode=" + blockCode + ", blockStatus=" + blockStatus
				+ ", customerId=" + customerId + ", cardStatus=" + cardStatus + ", cardNum=" + cardNum + "]";
	}



}
