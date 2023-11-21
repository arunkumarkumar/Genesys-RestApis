package com.api.ivr.model;

import java.sql.Timestamp;

public class CommonInput {
	private String apiName;
	private String messageID;
	private String host;
	private String ucid;
	private Timestamp timeStamp;
	private String hotline;
	private String sessionId;
	
	private String requestBody;
	private String token;
	private String endPoint;
	private String timeout;
	
	private String trackingId;
	private String requestTime;
	private String responseTime;
	private String dummyFlag;
	private String serviceName;
	private String serviceId;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	/**
	 * @return the messageID
	 */
	public String getMessageID() {
		return messageID;
	}

	/**
	 * @param messageID
	 *            the messageID to set
	 */
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the ucid
	 */
	public String getUcid() {
		return ucid;
	}

	/**
	 * @param ucid
	 *            the ucid to set
	 */
	public void setUcid(String ucid) {
		this.ucid = ucid;
	}

	/**
	 * @return the timeStamp
	 */
	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp
	 *            the timeStamp to set
	 */
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @param apiName
	 *            the apiName to set
	 */
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	/**
	 * @return the apiName
	 */
	public String getApiName() {
		return apiName;
	}

	public String getSessionId() {
		return sessionId;
	}

	@Override
	public String toString() {
		return "CommonInput [apiName=" + apiName + ", messageID=" + messageID + ", host=" + host + ", ucid=" + ucid
				+ ", timeStamp=" + timeStamp + ", requestBody=" + requestBody + ", token=" + token + ", trackingId="
				+ trackingId + ", hotline=" + hotline + ", sessionId=" + sessionId + ", requestTime=" + requestTime
				+ ", responseTime=" + responseTime + ", dummyFlag=" + dummyFlag + ", serviceName=" + serviceName
				+ ", serviceId=" + serviceId + "]";
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getHotline() {
		return hotline;
	}

	public void setHotline(String hotline) {
		this.hotline = hotline;
	}

	public void setSessionId(String sessionId) {

		if (sessionId == null || sessionId.isEmpty()) {
			throw new IllegalArgumentException("SESSIONID IS SHOULD NOT BE NULL OR EMPTY");
		} else {
			this.sessionId = sessionId;
		}
	}

	public String getDummyFlag() {
		return dummyFlag;
	}

	public void setDummyFlag(String dummyFlag) {
		this.dummyFlag = dummyFlag;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	
	

}
