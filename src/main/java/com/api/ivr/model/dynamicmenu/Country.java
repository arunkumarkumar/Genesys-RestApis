package com.api.ivr.model.dynamicmenu;

import java.io.Serializable;
import java.util.List;

/**
 * @author 1613981
 *
 * 
 */
public class Country implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private List<Entry> entry;

	 // Getter Methods 

	 public String getName() {
	  return name;
	 }

	 public List<Entry> getEntry(){
	   return this.entry;
	 }
	 
	 // Setter Methods 

	 public void setName(String name) {
	  this.name = name;
	 }

	 public void setEntry(List<Entry> Entry){
	        this.entry = Entry;
	 }

	@Override
	public String toString() {
		return "Country [name=" + name + ", entry=" + entry + "]";
	}
}
