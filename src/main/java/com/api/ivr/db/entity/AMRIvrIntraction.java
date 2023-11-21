package com.api.ivr.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "CUST_TVAL")
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "INSERT_CUST_TVAL_SP", procedureName = "INSERT_CUST_TVAL_SP", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_RELID", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_HASHEDVAL", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_STATUS", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_BLOCKEDDATE", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_CREATECHANGEDATE", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_TRIES", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_UNBLOCKEDDATE", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "O_METHOD", type = String.class),}),
		

		@NamedStoredProcedureQuery(name = "GET_CUST_TVAL_SP", procedureName = "GET_CUST_TVAL_SP", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_RELID", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.OUT, name = "O_CHECK", type = String.class),})})
@Component
public class AMRIvrIntraction  {

	@Id
	@Column(name = "REL_ID")
	private String relID;

	@Column(name = "HASHED_VAL")
	private String hashedVal;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "BLOCKED_DATE")
	private String blockedDate;

	@Column(name = "CREATE_CHANGE_DATE")
	private String createchangeDate;

	@Column(name = "TRIES")
	private String tries;

	@Column(name = "UNBLOCKED_DATE")
	private String unblockedDate;

	@Column(name = "O_CHECK")
	private String check;

	public String getRelID() {
		return relID;
	}

	public void setRelID(String relID) {
		this.relID = relID;
	}

	public String getHashedVal() {
		return hashedVal;
	}

	public void setHashedVal(String hashedVal) {
		this.hashedVal = hashedVal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBlockedDate() {
		return blockedDate;
	}

	public void setBlockedDate(String blockedDate) {
		this.blockedDate = blockedDate;
	}

	public String getCreatechangeDate() {
		return createchangeDate;
	}

	public void setCreatechangeDate(String createchangeDate) {
		this.createchangeDate = createchangeDate;
	}

	public String getTries() {
		return tries;
	}

	public void setTries(String tries) {
		this.tries = tries;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getUnblockedDate() {
		return unblockedDate;
	}

	public void setUnblockedDate(String unblockedDate) {
		this.unblockedDate = unblockedDate;
	}

	@Override
	public String toString() {
		return "AMIvrIntraction [relID=" + relID + ", hashedVal=" + hashedVal + ", status=" + status + ", blockedDate="
				+ blockedDate + ", createchangeDate=" + createchangeDate + ", tries=" + tries + ", unblockedDate="
				+ unblockedDate + ", check=" + check + "]";
	}


}
