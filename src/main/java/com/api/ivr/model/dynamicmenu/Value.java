package com.api.ivr.model.dynamicmenu;

import java.io.Serializable;

/**
 * @author 1613981
 *
 * 
 */
public class Value implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private String bargein;
	private String prompts;
	private String grammars;
	private String nextNode;
	private String stateID;
	private String menusDescription;
	private String noInput;
	private String noMatch;
	private String retry;
	private String timeOut;
	private String maxTriesNextNode;
	private String maxtries;
	private String intent;

	// Getter Methods

	public String getBargein() {
		return bargein;
	}

	public String getPrompts() {
		return prompts;
	}

	public String getGrammars() {
		return grammars;
	}

	public String getNextNode() {
		return nextNode;
	}

	public String getStateID() {
		return stateID;
	}

	public String getMenusDescription() {
		return menusDescription;
	}

	public String getNoInput() {
		return noInput;
	}

	public String getNoMatch() {
		return noMatch;
	}

	public String getRetry() {
		return retry;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public String getMaxtries() {
		return maxtries;
	}

	public String getMaxTriesNextNode() {
		return maxTriesNextNode;
	}

	// Setter Methods

	public void setBargein(String bargein) {
		this.bargein = bargein;
	}

	public void setPrompts(String prompts) {
		this.prompts = prompts;
	}

	public void setGrammars(String grammars) {
		this.grammars = grammars;
	}

	public void setNextNode(String nextNode) {
		this.nextNode = nextNode;
	}

	public void setStateID(String stateID) {
		this.stateID = stateID;
	}

	public void setMenusDescription(String menusDescription) {
		this.menusDescription = menusDescription;
	}

	public void setNoInput(String noInput) {
		this.noInput = noInput;
	}

	public void setNoMatch(String noMatch) {
		this.noMatch = noMatch;
	}

	public void setRetry(String retry) {
		this.retry = retry;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public void setMaxtries(String maxtries) {
		this.maxtries = maxtries;
	}

	public void setMaxTriesNextNode(String maxTriesNextNode) {
		this.maxTriesNextNode = maxTriesNextNode;
	}

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	@Override
	public String toString() {
		return "Value [bargein=" + bargein + ", prompts=" + prompts + ", grammars=" + grammars + ", nextNode="
				+ nextNode + ", stateID=" + stateID + ", menusDescription=" + menusDescription + ", noInput=" + noInput
				+ ", noMatch=" + noMatch + ", retry=" + retry + ", timeOut=" + timeOut + ", maxTriesNextNode="
				+ maxTriesNextNode + ", maxtries=" + maxtries + ", intent=" + intent + "]";
	}

}
