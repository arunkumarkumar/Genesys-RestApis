package com.api.ivr.model.customeridentifycardnum;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentifyCardNum_lastlink {
	@JsonProperty("url")
	private String url;
	@JsonProperty("method")
	private String method;
	@JsonProperty("body")
	private String body;
	@Override
	public String toString() {
		return "IdenifiyCardNum_firstlink [url=" + url + ", method=" + method + ", body=" + body + "]";
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

}
