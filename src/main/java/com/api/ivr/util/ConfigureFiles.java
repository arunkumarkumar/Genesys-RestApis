package com.api.ivr.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.api.ivr.model.dynamicmenu.Entry;
import com.api.ivr.model.dynamicmenu.Menu;
import com.api.ivr.model.dynamicmenu.Value;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigureFiles {
public Map<String, Map<String, String>> loadDynamicMenu(String keyName,String filepath) {
		
//		Logger MQLog = LogManager.getContext().getLogger(GlobalConstants.HostLog)
		Map<String, Map<String, String>> dynamicMenuMap = new HashMap<>();
		try {
			
			File file 					= new File(filepath+ keyName);
			ObjectMapper objectMapper 	= new ObjectMapper();
			Menu menu 					= objectMapper.readValue(file, Menu.class);
			
			List<Entry> list = menu.getDynamicMenu().getCountry().getEntry();

			for (Entry entry : list) {
				String key = entry.getKey();
				Value value = entry.getValue();
				
				Map<String, String> valueMap = new HashMap<>();
				
				valueMap.put("PROMPTS", value.getPrompts().trim());
				valueMap.put("BARGEIN", value.getBargein().trim());
				valueMap.put("GRAMMARS", value.getGrammars().trim());
				valueMap.put("MENU_DESC", value.getMenusDescription().trim());
				valueMap.put("NEXT_NODE", value.getNextNode().trim());
				valueMap.put("NI_PROMPT", value.getNoInput().trim());
				valueMap.put("NM_PROMPT", value.getNoMatch().trim());
				valueMap.put("RETRY", value.getRetry().trim());
				valueMap.put("STATE_ID", value.getStateID().trim());
				valueMap.put("MENU_TIMEOUT", value.getTimeOut().trim());
				valueMap.put("MAXTRIES", value.getMaxtries().trim());
				valueMap.put("MAXTRIES_NEXTNODE", value.getMaxTriesNextNode().trim());
				valueMap.put("INTENT", value.getIntent().trim());

				dynamicMenuMap.put(key, valueMap);

			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return dynamicMenuMap;
	}

}
