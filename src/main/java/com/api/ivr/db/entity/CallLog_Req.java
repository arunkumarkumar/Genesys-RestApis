package com.api.ivr.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "CALL_LOG_DTLS")
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "INSERT_CALL_LOG_DTLS_SP", procedureName = "INSERT_CALL_LOG_DTLS_SP", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_CLI", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_DNIS", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_STARTTIME", type = Date.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_ENDTIME", type = Date.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_UCID", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_RMN", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_IS_RMN", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_BANK_CARD_LOAN", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_CUSTOMER_SEGMENT", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_TRANSFER_OR_DISC", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_ACC_CARD_ID", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_SEG_CODE", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_REL_ID", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_BLOCK_CODE", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_LANGUAGE", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_CONTEXT_ID", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_LASTMENU", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_TRANSFER_ATTRIBUTES", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_COUNTRY", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_ONE_FA", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_TWO_FA", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_VERIFIED_BY", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_IDENTIFIED_BY", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_MENU_TRAVERSE", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_CHANNEL", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_INVOLUNTARY_REASON", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_AGENT_ID", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_SESSION_ID", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_OTP_DEST", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "DISCONNECT_REASON", type = String.class), }) })
@Component
public class CallLog_Req {

	@Column(name = "CLI")
	private String cli;

	@Column(name = "DNIS")
	private String dnis;

	@Column(name = "STARTTIME")
	private Date starttime;

	@Column(name = "ENDTIME")
	private Date endtime;

	@Id
	@Column(name = "UCID")
	private String ucid;

	@Column(name = "RMN")
	private String rmn;

	@Column(name = "IS_RMN")
	private String is_rmn;

	@Column(name = "BANK_CARD_LOAN")
	private String bank_card_loan;

	@Column(name = "CUSTOMER_SEGMENT")
	private String customer_segment;

	@Column(name = "TRANSFER_OR_DISC")
	private String transfer_or_disc;

	@Column(name = "ACC_CARD_ID")
	private String acc_card_id;

	@Column(name = "SEG_CODE")
	private String seg_code;

	@Column(name = "REL_ID")
	private String rel_id;

	@Column(name = "BLOCK_CODE")
	private String block_code;

	@Column(name = "LANGUAGE")
	private String language;

	@Column(name = "CONTEXT_ID")
	private String context_id;

	@Column(name = "LASTMENU")
	private String lastmenu;

	@Column(name = "TRANSFER_ATTRIBUTES")
	private String transfer_attributes;

	@Column(name = "COUNTRY")
	private String conutry;

	@Column(name = "ONE_FA")
	private String one_fa;

	@Column(name = "TWO_FA")
	private String two_fa;

	@Column(name = "VERIFIED_BY")
	private String verified_by;

	@Column(name = "IDENTIFIED_BY")
	private String identified_by;

	@Column(name = "MENU_TRAVERSE")
	private String menu_traverse;

	@Column(name = "CHANNEL")
	private String channel;

	@Column(name = "INVOLUNTARY_REASON")
	private String involuntry_Reason;

	@Column(name = "AGENT_ID")
	private String agent_id;

	@Column(name = "SESSION_ID")
	private String session_id;

	@Column(name = "OTP_DEST")
	private String otp_dest;

	@Column(name = "DISCONNECT_REASON")
	private String disconnect_reason;

	public String getCli() {
		return cli;
	}

	public void setCli(String cli) {
		this.cli = cli;
	}

	public String getDnis() {
		return dnis;
	}

	public void setDnis(String dnis) {
		this.dnis = dnis;
	}

	public java.util.Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public java.util.Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getUcid() {
		return ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}

	public String get_rmn() {
		return rmn;
	}

	public void setI_rmn(String rmn) {
		this.rmn = rmn;
	}

	public String getis_rmn() {
		return is_rmn;
	}

	public void set_is_rmn(String is_rmn) {
		this.is_rmn = is_rmn;
	}

	public String get_bank_card_loan() {
		return bank_card_loan;
	}

	public void set_bank_card_loan(String bank_card_loan) {
		this.bank_card_loan = bank_card_loan;
	}

	public String getCustomer_segment() {
		return customer_segment;
	}

	public void setCustomer_segment(String customer_segment) {
		this.customer_segment = customer_segment;
	}

	public String getTransfer_or_disc() {
		return transfer_or_disc;
	}

	public void setTransfer_or_disc(String transfer_or_disc) {
		this.transfer_or_disc = transfer_or_disc;
	}

	public String getAcc_card_id() {
		return acc_card_id;
	}

	public void setAcc_card_id(String acc_card_id) {
		this.acc_card_id = acc_card_id;
	}

	public String getSeg_code() {
		return seg_code;
	}

	public void setSeg_code(String seg_code) {
		this.seg_code = seg_code;
	}

	public String getRel_id() {
		return rel_id;
	}

	public void setRel_id(String rel_id) {
		this.rel_id = rel_id;
	}

	public String getBlock_code() {
		return block_code;
	}

	public void setBlock_code(String block_code) {
		this.block_code = block_code;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getContext_id() {
		return context_id;
	}

	public void setContext_id(String context_id) {
		this.context_id = context_id;
	}

	public String getLastmenu() {
		return lastmenu;
	}

	public void setLastmenu(String lastmenu) {
		this.lastmenu = lastmenu;
	}

	public String getTransfer_attributes() {
		return transfer_attributes;
	}

	public void setTransfer_attributes(String transfer_attributes) {
		this.transfer_attributes = transfer_attributes;
	}

	public String getConutry() {
		return conutry;
	}

	public void setConutry(String conutry) {
		this.conutry = conutry;
	}

	public String getOne_fa() {
		return one_fa;
	}

	public void setOne_fa(String one_fa) {
		this.one_fa = one_fa;
	}

	public String getTwo_fa() {
		return two_fa;
	}

	public void setTwo_fa(String two_fa) {
		this.two_fa = two_fa;
	}

	public String getVerified_by() {
		return verified_by;
	}

	public void setVerified_by(String verified_by) {
		this.verified_by = verified_by;
	}

	public String getIdentified_by() {
		return identified_by;
	}

	public void setIdentified_by(String identified_by) {
		this.identified_by = identified_by;
	}

	public String getMenu_traverse() {
		return menu_traverse;
	}

	public void setMenu_traverse(String menu_traverse) {
		this.menu_traverse = menu_traverse;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getInvoluntry_Reason() {
		return involuntry_Reason;
	}

	public void setInvoluntry_Reason(String involuntry_Reason) {
		this.involuntry_Reason = involuntry_Reason;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getOtp_dest() {
		return otp_dest;
	}

	public void setOtp_dest(String otp_dest) {
		this.otp_dest = otp_dest;
	}

	public String getRmn() {
		return rmn;
	}

	public void setRmn(String rmn) {
		this.rmn = rmn;
	}
	public String getBank_card_loan() {
		return bank_card_loan;
	}

	public void setBank_card_loan(String bank_card_loan) {
		this.bank_card_loan = bank_card_loan;
	}

	public String getDisconnect_reason() {
		return disconnect_reason;
	}

	public void setDisconnect_reason(String disconnect_reason) {
		this.disconnect_reason = disconnect_reason;
	}

	@Override
	public String toString() {
		return "CallLog_Req [cli=" + cli + ", dnis=" + dnis + ", starttime=" + starttime + ", endtime=" + endtime
				+ ", ucid=" + ucid + ", rmn=" + rmn + ", is_rmn=" + is_rmn + ", bank_card_loan=" + bank_card_loan
				+ ", customer_segment=" + customer_segment + ", transfer_or_disc=" + transfer_or_disc + ", acc_card_id="
				+ acc_card_id + ", seg_code=" + seg_code + ", rel_id=" + rel_id + ", block_code=" + block_code
				+ ", language=" + language + ", context_id=" + context_id + ", lastmenu=" + lastmenu
				+ ", transfer_attributes=" + transfer_attributes + ", conutry=" + conutry + ", one_fa=" + one_fa
				+ ", two_fa=" + two_fa + ", verified_by=" + verified_by + ", identified_by=" + identified_by
				+ ", menu_traverse=" + menu_traverse + ", channel=" + channel + ", involuntry_Reason="
				+ involuntry_Reason + ", agent_id=" + agent_id + ", session_id=" + session_id + ", otp_dest=" + otp_dest
				+ ", disconnect_reason=" + disconnect_reason + "]";
	}
}
