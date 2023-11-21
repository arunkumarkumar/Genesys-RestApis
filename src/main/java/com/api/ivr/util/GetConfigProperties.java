/**
 * 
 */
package com.api.ivr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
//
//import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.api.ivr.controller.LoadFilesScheduler_Controller;
import com.api.ivr.global.constants.GlobalConstants;

import org.apache.logging.log4j.Logger;

/**
 * @author TA
 *
 */
@Component
public class GetConfigProperties {
	@Autowired
	LoadFilesScheduler_Controller configController;
	
	@Autowired
	Utilities utilities;
	
	@Value("${propertyfilepath}")
	private String propertyfilepath;
	
	//// Get and load all properties from file ( config, operations, parametric,
	//// payload )
	public Properties getServiceConfig(Properties properties) throws Exception {

		Properties serviceProperties = new Properties();
		serviceProperties.putAll(properties);

		//String sessionId = properties.getProperty("sessionId");
		String serviceName = properties.getProperty("serviceName");
		String tpSystem = properties.getProperty("tpSystem");

		

		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Config));
		
		
		//// LOAD DUMMY FLAG PARAMETERS
		try {
			if (properties != null && properties.getProperty(serviceName) != null) {

				String configPropertyStr = properties.getProperty(serviceName);
				String serviceConfigValues[] = configPropertyStr.split(",");

				if (serviceConfigValues.length >= 4) {
					serviceProperties.setProperty("dummyFlag", serviceConfigValues[0]);
					serviceProperties.setProperty("operationName", serviceConfigValues[1]);
					serviceProperties.setProperty("serviceId", serviceConfigValues[2]);
					serviceProperties.setProperty("serviceType", serviceConfigValues[3]);
				} else {
					serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
					System.out.println(" Config property value is not match with the expected format. Property is: "
							+ configPropertyStr);
				}
			} else {
				System.out.println(utilities.getCurrentClassAndMethodName() + " - " + properties.getProperty(serviceName)
						+ " is not found in config.properties.");
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			}
		} catch (Exception e) {
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			System.out.println(utilities.getCurrentClassAndMethodName()
					+ " Config.properties -> Exception occured while processing the config propery:" + serviceName
					+ ". " + e.getMessage());
		}

		properties = new Properties();
		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Operations));
		
		
		//// LOAD ENDPOINT PARAMETER
		try {
			if (properties != null && properties.getProperty("endPoint." + serviceName) != null) {
				String endPointString = properties.getProperty("endPoint." + serviceName);

				String endPoints[] = endPointString.split(";");
				if (endPoints.length >= 2 && !utilities.isNullOrEmpty(endPoints[0])) {
					serviceProperties.setProperty("endPoint", endPoints[0].trim());
					serviceProperties.setProperty("timeOut", endPoints[1] != null ? endPoints[1].trim() : "20000");
				} else {
					serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
					System.out.println(utilities.getCurrentClassAndMethodName()
							+ " Operations.properties -> Incorrect endPoint params found for the service : "
							+ serviceName);
					
				}
			} else {
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
				System.out.println(utilities.getCurrentClassAndMethodName()
						+ " Operations.properties -> No endPoint URL found for the service: " + serviceName);
			}

			if (properties != null && properties.getProperty("endPoint." + tpSystem + ".tokenUrl") != null) {
				String endPointString = properties.getProperty("endPoint." + tpSystem + ".tokenUrl");

				String endPoints[] = endPointString.split(";");
				if (endPoints.length >= 2 && !utilities.isNullOrEmpty(endPoints[0])) {
					serviceProperties.setProperty("tokenURL", endPoints[0].trim());
					serviceProperties.setProperty("tokenTimeOut", endPoints[1] != null ? endPoints[1].trim() : "20000");
				}
			}
		} catch (Exception e) {
			System.out.println(utilities.getCurrentClassAndMethodName()
					+ " Exception occured while fetching endPoint in operations.properties. Service: " + serviceName
					+ " - " + e.getMessage());
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		properties = new Properties();
		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Parametric));

		
		//// LOAD Parametric properties
		try {
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();

				if (key.contains(tpSystem + ".")) {
					serviceProperties.put(key, value);
				}

				if (!key.contains(".")) {
					serviceProperties.put(key, value);
				}

			}

		} catch (Exception e) {
			System.out.println(utilities.getCurrentClassAndMethodName()
					+ " Exception occured while loading Parametric properties." + e.getMessage());
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		//// LOAD PAYLOAD PARAMETER
		try {

			File file = new File(propertyfilepath + tpSystem + "Payload.properties");
			System.out.println(file.toString() +"-------FileLocation");
			System.out.println(tpSystem +"---------Tpsystem");
			Properties propPayload = new Properties();
			InputStream input = new FileInputStream(file);
			propPayload.load(input);

			if (propPayload != null && propPayload.getProperty(serviceName) != null) {
				serviceProperties.setProperty("requestPayload", propPayload.getProperty(serviceName));
			} else {
				System.out.println(utilities.getCurrentClassAndMethodName() + " No request payload found in " + tpSystem
						+ "Payload.properties");
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			}

		} catch (Exception e) {
			System.out.println(utilities.getCurrentClassAndMethodName() + " Exception occured while loading payload properties."
					+ e.getMessage());
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		return serviceProperties;
	}
	
	


}
