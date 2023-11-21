package com.api.ivr.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(name = "CUSTOMER_DTLS")
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "GET_PREFFERED_LANG_CODE_SP", procedureName = "GET_PREFFERED_LANG_CODE_SP", resultClasses = {
				CustomerInfo.class }, parameters = {
						@StoredProcedureParameter(name = "p_cli_no", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "p_lang_code", type = String.class, mode = ParameterMode.OUT),
						@StoredProcedureParameter(name = "p_check", type = String.class, mode = ParameterMode.OUT), }),

		@NamedStoredProcedureQuery(name = "INSERT_CUSTOMER_DTLS_SP", procedureName = "INSERT_CUSTOMER_DTLS_SP", resultClasses = {
				CustomerInfo.class }, parameters = {
						@StoredProcedureParameter(name = "p_cli_no", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "p_lang_code", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "p_rel_id", type = String.class, mode = ParameterMode.IN), }) })

@Component
public class CustomerInfo implements Serializable {

	private static final long serialVersionUID = 7872244242998111278L;

	@Id
	@Column(name = "CLI_NO")
	private String cli;

	@Column(name = "LANG_CODE")
	private String langCode;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "REL_ID")
	private String relId;
}
