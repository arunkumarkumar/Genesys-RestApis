/**
 * 
 */
package com.api.ivr.db.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author TA
 *
 */
public class BusinessHrsCheck_Res extends com.api.ivr.model.CommonOutput {

	private String segment;
	private String language;
	private String product;
	private boolean isHoliday;
	private boolean isServiceHrsCheckAvailable;
	private String serviceHrsStartTime;
	private String serviceHrsEndTime;
	private boolean isServiceHrs;
	private boolean isVDNAvailable;
	private String vdnRoutingData;
	private String genPrompt;
	private String splPrompt;

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public boolean isHoliday() {
		return isHoliday;
	}

	public void setHoliday(boolean isHoliday) {
		this.isHoliday = isHoliday;
	}

	public boolean isServiceHrsCheckAvailable() {
		return isServiceHrsCheckAvailable;
	}

	public void setServiceHrsCheckAvailable(boolean isServiceHrsCheckAvailable) {
		this.isServiceHrsCheckAvailable = isServiceHrsCheckAvailable;
	}

	public String getServiceHrsStartTime() {
		return serviceHrsStartTime;
	}

	public void setServiceHrsStartTime(String serviceHrsStartTime) {
		this.serviceHrsStartTime = serviceHrsStartTime;
	}

	public String getServiceHrsEndTime() {
		return serviceHrsEndTime;
	}

	public void setServiceHrsEndTime(String serviceHrsEndTime) {
		this.serviceHrsEndTime = serviceHrsEndTime;
	}

	public boolean isServiceHrs() {
		return isServiceHrs;
	}

	public void setServiceHrs(boolean isServiceHrs) {
		this.isServiceHrs = isServiceHrs;
	}

	public boolean isVDNAvailable() {
		return isVDNAvailable;
	}

	public void setVDNAvailable(boolean isVDNAvailable) {
		this.isVDNAvailable = isVDNAvailable;
	}

	public String getVdnRoutingData() {
		return vdnRoutingData;
	}

	public void setVdnRoutingData(String vdnRoutingData) {
		this.vdnRoutingData = vdnRoutingData;
	}

	public String getGenPrompt() {
		return genPrompt;
	}

	public void setGenPrompt(String genPrompt) {
		this.genPrompt = genPrompt;
	}

	public String getSplPrompt() {
		return splPrompt;
	}

	public void setSplPrompt(String splPrompt) {
		this.splPrompt = splPrompt;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
