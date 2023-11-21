package com.api.ivr.model.dynamicmenu;

import java.io.Serializable;

/**
 * @author 1613981
 *
 * 
 */
public class DynamicMenu implements Serializable{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		Country country;


		 // Getter Methods 

		 public Country getCountry() {
		  return country;
		 }

		 // Setter Methods 

		 public void setCountry(Country country) {
		  this.country = country;
		 }

		@Override
		public String toString() {
			return "DynamicMenu [country=" + country + "]";
		}
}
