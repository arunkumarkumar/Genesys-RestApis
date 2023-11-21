package com.api.ivr.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.api.ivr.ehcache.service.UpdateCache;
import com.api.ivr.model.dynamicmenu.Entry;
import com.api.ivr.model.dynamicmenu.Menu;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class LoadFilesScheduler_Controller {
	@Value("${propertyfilepath}")
	private String propertyfilepath;

	@Value("${propertyfilenames}")
	private String propertyfilenames;

	private Map<String, Long> file_modified_time  = new HashMap<>();

	@Autowired
	public UpdateCache updateCache;
	/**
	 * Scheduler method to check file modification time
	 * 
	 * 
	 * 	@Value("${propertyfilepath}")
	private String propertyfilepath;

	@Value("${propertyfilenames}")
	private String propertyfilenames;

	private Map<String, Long> file_modified_time  = new HashMap<>();

	@Autowired
	public UpdateCache updateCache;
	
	@Autowired
	public DBController dbControllerData;
	
	
//	@Autowired
//	private GetConfigDetailsRepository getConfigDetailsRepository;
	
	
	/**
	 * This method caches the configuration file values in ehcache based on key
	 * value pair
	 * 
	 * @param keyname
	 * @return Object based on keyname
	 */
	@Cacheable(value = "configfile", key = "#keyname", unless = "#result == null")
	public Object getConfigFileValues(String keyname) {
//		Logger tpSystemLogger = LogManager.getContext().getLogger(GlobalConstants.HostLog);
		System.out.println("Entering getConfigFileValues Keyname =: "+ propertyfilepath + keyname);
		InputStream input = null;
		try {
			System.out.println("********* Loading ehcache Key********** " + keyname);

			if (keyname.equalsIgnoreCase("Config.properties")) {
				//tpSystemLogger.debug("Path: " + propertyfilepath + keyname );
				File file = new File(propertyfilepath + keyname);
				Properties prop = new Properties();
				input = new FileInputStream(file);
				prop.load(input);
				//tpSystemLogger.debug("Leaving getConfigFileValues from 'Config' values"); 
				return prop;
			}else if (keyname.equalsIgnoreCase("Operations.properties")) {
				//tpSystemLogger.debug("Path: " + propertyfilepath + keyname);
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
			}else {
				System.out.println("Trying to fetch other property file");
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
						System.out.println("Trying to fetch other property file. The file extention is not found"); 
						return null;
					}
				} else {
					System.out.println("Given Config/Properties file does not exit in Path: " + propertyfilepath + keyname);
					return null;
				}

			}

		} catch (Exception e) {
			System.out.println("Exception in getConfigFileValues: "+e.getMessage());
		}
		System.out.println("Leaving getConfigFileValues returning NULL");
		return null;
	}
	

	@Scheduled(fixedDelayString = "${fixed.delay}")
	public void schedulerToCheckFileModified() {
		 
		try {		
			System.out.println("LoadFilesScheduler_Controller.schedulerToCheckFileModified()");
			//Checks and updates the recent modified timestamp for the property files
			for(String fileName : propertyfilenames.split(",")) {
				fileName = fileName.trim();
				if(fileName.isEmpty()) {
					continue;
				}
				
				File file = new File(propertyfilepath + fileName);

				long current_modified = file.lastModified();

				if (file_modified_time.get(fileName) == null) {
					file_modified_time.put(fileName, current_modified);
					System.out.println("Currently Modified -");
				}

				if (file_modified_time.get(fileName) == current_modified) {
					System.out.println("Currently Modified --");
	
				} else {
					System.out.println("Currently Modified");
					updateCache.updateCacheValues(fileName);
					file_modified_time.replace(fileName, current_modified);
				}
			}

		} catch (Exception e) {
		
		}

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
