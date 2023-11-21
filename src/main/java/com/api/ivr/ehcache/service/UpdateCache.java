package com.api.ivr.ehcache.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import com.api.ivr.model.dynamicmenu.Entry;
import com.api.ivr.model.dynamicmenu.Menu;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author TA
 *
 * 
 */

@Component
public class UpdateCache {

	@Value("${propertyfilepath}")
	private String propertyfilepath;
	

	@CachePut(cacheNames = "configfile", key = "#keyname")
	public Object updateCacheValues(String keyname) {
		InputStream input = null;

		try {
			if (keyname.equalsIgnoreCase("Config.properties")) {
				File file = new File(propertyfilepath + keyname);
				Properties prop = new Properties();
				input = new FileInputStream(file);
				prop.load(input);

				return prop;
			} else if (keyname.equalsIgnoreCase("Operations.properties")) {
				File file = new File(propertyfilepath + keyname);
				Properties prop = new Properties();
				input = new FileInputStream(file);
				prop.load(input);

				return prop;

			} else if (keyname.equalsIgnoreCase("Parametric.properties")) {
				File file = new File(propertyfilepath + keyname);
				Properties prop = new Properties();
				input = new FileInputStream(file);
				prop.load(input);

				return prop;
			} else if (keyname.equalsIgnoreCase("menu.json")) {
				return loadDynamicMenu(keyname);
			} 
			else {
				File file = new File(propertyfilepath + keyname);
				if (file.exists()) {
					if (keyname.contains(".json")) {
						return new JSONObject(Files.lines(Paths.get(propertyfilepath + keyname))
								.collect(Collectors.joining(System.lineSeparator())));
					} else if (keyname.contains(".xml")) {
						return file;
					} else if (keyname.contains(".properties")) {
						Properties prop = new Properties();
						input = new FileInputStream(file);
						prop.load(input);
						return prop;
					} else {
						return null;
					}
				} else {
					return null;
				}

			}

		} catch (Exception e) {
		} finally {
			try {
				input.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Load menu json file and store the values in map
	 * 
	 * @return
	 */
	public Map<String, Map<String, String>> loadDynamicMenu(String keyName) {

		Map<String, Map<String, String>> dynamicMenuMap = new HashMap<>();
		try {
			
			File file 					= new File(propertyfilepath+ keyName);
			ObjectMapper objectMapper 	= new ObjectMapper();
			Menu menu 					= objectMapper.readValue(file, Menu.class);
			
			List<Entry> list = menu.getDynamicMenu().getCountry().getEntry();

			for (Entry entry : list) {
				String key = entry.getKey();
				Value value = (Value) entry.getValue();
				
				Map<String, String> valueMap = new HashMap<>();
				
				valueMap.put("PROMPTS", ((com.api.ivr.model.dynamicmenu.Value) value).getPrompts().trim());
				valueMap.put("BARGEIN", ((com.api.ivr.model.dynamicmenu.Value) value).getBargein().trim());
				valueMap.put("GRAMMARS", ((com.api.ivr.model.dynamicmenu.Value) value).getGrammars().trim());
				valueMap.put("MENU_DESC", ((com.api.ivr.model.dynamicmenu.Value) value).getMenusDescription().trim());
				valueMap.put("NEXT_NODE", ((com.api.ivr.model.dynamicmenu.Value) value).getNextNode().trim());
				valueMap.put("NI_PROMPT", ((com.api.ivr.model.dynamicmenu.Value) value).getNoInput().trim());
				valueMap.put("NM_PROMPT", ((com.api.ivr.model.dynamicmenu.Value) value).getNoMatch().trim());
				valueMap.put("RETRY", ((com.api.ivr.model.dynamicmenu.Value) value).getRetry().trim());
				valueMap.put("STATE_ID", ((com.api.ivr.model.dynamicmenu.Value) value).getStateID().trim());
				valueMap.put("MENU_TIMEOUT", ((com.api.ivr.model.dynamicmenu.Value) value).getTimeOut().trim());
				valueMap.put("MAXTRIES", ((com.api.ivr.model.dynamicmenu.Value) value).getMaxtries().trim());
				valueMap.put("MAXTRIES_NEXTNODE", ((com.api.ivr.model.dynamicmenu.Value) value).getMaxTriesNextNode().trim());
				valueMap.put("INTENT", ((com.api.ivr.model.dynamicmenu.Value) value).getIntent().trim());

				dynamicMenuMap.put(key, valueMap);

			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return dynamicMenuMap;
	}

}
