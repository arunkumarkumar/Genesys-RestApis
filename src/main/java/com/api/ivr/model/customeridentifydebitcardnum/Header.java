package com.api.ivr.model.customeridentifydebitcardnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Header {

	@JsonProperty("service-id")
	private String serviceId;
	@JsonProperty("channel-id")
	private String channelId;
	@JsonProperty("x-market")
	private String xMarket;
	@JsonProperty("transaction-sequence-number")
	private String transactionSequenceNumber;
	@JsonProperty("source-date")
	private String sourceDate;
	@JsonProperty("source-time")
	private String sourceTime;
	@JsonProperty("transmission-date")
	private String transmissionDate;
	@JsonProperty("transmission-time")
	private String transmissionTime;
	@JsonProperty("user-id")
	private String userId;
	@JsonProperty("message-version")
	private String messageVersion;
	@JsonProperty("signature")
	private String signature;
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getxMarket() {
		return xMarket;
	}
	public void setxMarket(String xMarket) {
		this.xMarket = xMarket;
	}
	public String getTransactionSequenceNumber() {
		return transactionSequenceNumber;
	}
	public void setTransactionSequenceNumber(String transactionSequenceNumber) {
		this.transactionSequenceNumber = transactionSequenceNumber;
	}
	public String getSourceDate() {
		return sourceDate;
	}
	public void setSourceDate(String sourceDate) {
		this.sourceDate = sourceDate;
	}
	public String getSourceTime() {
		return sourceTime;
	}
	public void setSourceTime(String sourceTime) {
		this.sourceTime = sourceTime;
	}
	public String getTransmissionDate() {
		return transmissionDate;
	}
	public void setTransmissionDate(String transmissionDate) {
		this.transmissionDate = transmissionDate;
	}
	public String getTransmissionTime() {
		return transmissionTime;
	}
	public void setTransmissionTime(String transmissionTime) {
		this.transmissionTime = transmissionTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Override
	public String toString() {
		return "Header [serviceId=" + serviceId + ", channelId=" + channelId + ", xMarket=" + xMarket
				+ ", transactionSequenceNumber=" + transactionSequenceNumber + ", sourceDate=" + sourceDate
				+ ", sourceTime=" + sourceTime + ", transmissionDate=" + transmissionDate + ", transmissionTime="
				+ transmissionTime + ", userId=" + userId + ", messageVersion=" + messageVersion + ", signature="
				+ signature + "]";
	}
	
	
}
