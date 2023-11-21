package com.api.ivr.model.dynamicmenu;

import java.io.Serializable;

/**
 * @author 1613981
 *
 * 
 */
public class Menu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DynamicMenu DynamicMenu;

	 // Getter Methods 

	 public DynamicMenu getDynamicMenu() {
	  return DynamicMenu;
	 }

	 // Setter Methods 

	 public void setDynamicMenu(DynamicMenu DynamicMenu) {
	  this.DynamicMenu = DynamicMenu;
	 }

	@Override
	public String toString() {
		return "Menu [DynamicMenu=" + DynamicMenu + "]";
	}
	 
}
	
	