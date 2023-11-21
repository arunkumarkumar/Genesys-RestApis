package com.api.ivr.model.dynamicmenu;

import java.io.Serializable;

/**
 * @author 1613981
 *
 * 
 */
public class Entry implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	 Value value;


	 // Getter Methods 

	 public String getKey() {
	  return key;
	 }

	 public Value getValue() {
	  return value;
	 }

	 // Setter Methods 

	 public void setKey(String key) {
	  this.key = key;
	 }

	 public void setValue(Value value) {
	  this.value = value;
	 }

	@Override
	public String toString() {
		return "Entry [key=" + key + ", value=" + value + "]";
	}
	 
}
