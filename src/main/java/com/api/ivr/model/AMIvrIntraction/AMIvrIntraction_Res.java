/**
 * 
 */
package com.api.ivr.model.AMIvrIntraction;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.api.ivr.db.entity.AMRIvrIntraction;

/**
 * @author TA
 *
 */
public class AMIvrIntraction_Res extends com.api.ivr.model.CommonOutput {

	private AMRIvrIntraction response;

	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}



	public AMRIvrIntraction getResponse() {
		return response;
	}



	public void setResponse(AMRIvrIntraction response) {
		this.response = response;
	}

}
