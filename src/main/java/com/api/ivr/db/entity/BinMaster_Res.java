package com.api.ivr.db.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class BinMaster_Res extends com.api.ivr.model.CommonOutput {

	private int id;
	private Long bin;
	private String cardType;
	private String cardName;
	private String host;
	private String card_name_desc;
	private String card_group;
	private String card_subgroup;
	private String company_code;
	private String currency_code;
	private String credit_limit_flag;
	private String avail_limit_flag;
	private String ubpayment_flag;
	private String cardProductType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getBin() {
		return bin;
	}

	public void setBin(Long bin) {
		this.bin = bin;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCard_name_desc() {
		return card_name_desc;
	}

	public void setCard_name_desc(String card_name_desc) {
		this.card_name_desc = card_name_desc;
	}

	public String getCard_group() {
		return card_group;
	}

	public void setCard_group(String card_group) {
		this.card_group = card_group;
	}

	public String getCard_subgroup() {
		return card_subgroup;
	}

	public void setCard_subgroup(String card_subgroup) {
		this.card_subgroup = card_subgroup;
	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public String getCredit_limit_flag() {
		return credit_limit_flag;
	}

	public void setCredit_limit_flag(String credit_limit_flag) {
		this.credit_limit_flag = credit_limit_flag;
	}

	public String getAvail_limit_flag() {
		return avail_limit_flag;
	}

	public void setAvail_limit_flag(String avail_limit_flag) {
		this.avail_limit_flag = avail_limit_flag;
	}

	public String getUbpayment_flag() {
		return ubpayment_flag;
	}

	public void setUbpayment_flag(String ubpayment_flag) {
		this.ubpayment_flag = ubpayment_flag;
	}

	public String getCardProductType() {
		return cardProductType;
	}

	public void setCardProductType(String cardProductType) {
		this.cardProductType = cardProductType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
