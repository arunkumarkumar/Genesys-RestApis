package com.api.ivr.model.customercontactdetails;

import java.util.Arrays;

public class CustomerContactDetails_ResponseData {
	private CustomerContactDetailss_data[] data;

	
	private String message;

	public CustomerContactDetailss_data[] getData() {
		return data;
	}

	public void setData(CustomerContactDetailss_data[] data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CustomerContactDetails_ResponseData [data=" + Arrays.toString(data) + ", message=" + message + "]";
	}

	


}
