package com.api.ivr.model;




import com.fasterxml.jackson.annotation.JsonProperty;

public class CustIdentificationRMNDocument {
	@JsonProperty("document-type")
	private String documentType;
	@JsonProperty("document-number")
	private String documentNumber;

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}



}
