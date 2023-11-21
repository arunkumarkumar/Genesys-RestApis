package com.api.ivr.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.api.ivr.exception.CustomException;
import com.api.ivr.model.CustomerIdentficationAccNum_Req;
import com.api.ivr.model.GenerateOTP_Req;
import com.api.ivr.model.IdentifyCustomer_Req;
import com.api.ivr.model.ValidateOTP_Req;
import com.api.ivr.model.customercontactdetails.CustomerContactDetails_Req;
import com.api.ivr.model.customeridentifycardnum.CustomerIdenificationCardNum_Req;
import com.api.ivr.model.customeridentifydebitcardnum.CustomerIdentifyDebitcardnum_Req;
import com.api.ivr.model.validateTpin.ValidateTPIN_Req;
import com.api.ivr.util.GetConfigProperties;



@Component
@Service
public class GetPropertiesValues {
	@Autowired
	com.api.ivr.util.Utilities utilities;
	
	@Autowired
	GetConfigProperties getConfigProperties;
	
	public Map<String, Object> getCustomerRMN(IdentifyCustomer_Req req) {
		
		Map<String, Object> inputParams = new HashMap<String, Object>();
		try {
		
		SimpleDateFormat formatter = new SimpleDateFormat(com.api.ivr.global.constants.GlobalConstants.DateTimeFormat);
		String startDate = formatter.format(new Date());
		
		String hostLoggerName = com.api.ivr.global.constants.GlobalConstants.HostLog_Ebbs;
		String serviceName = "getCustomerIdentificationRMN";
		String trackingID = utilities.generateTrackingId();
		Properties properties = new Properties();

		properties.put("serviceName", serviceName);
		properties.put("trackingID", trackingID);
		properties.put("tpSystem", com.api.ivr.global.constants.GlobalConstants.TPSystem_Ebbs);
		properties.put("hostLoggerName", hostLoggerName);

		/// LOAD SERVICE PROPERTIES
		/****
		 * 
		 * Load all mandatory properties.
		 * Load the properties from file based on service name.
		 * @return config.properties, operation.properties, EBBSpayload.properties and parametric.properties
		 * 
		 ****/
		Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

		if (serviceProperties.getProperty("ERROR_CODE") != null
				&& com.api.ivr.global.constants.GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
			System.out.println(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

			throw new CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
					"Exception occurs while fetching environment properties");
		}

		/// CLEAR PROPERTIES
		properties.clear();

		/// ASSIGN PARAMETER
		req.setTrackingId(trackingID);
		req.setServiceName(serviceName);
		req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
		req.setServiceId(serviceProperties.getProperty("serviceId"));
		req.setEndPoint(serviceProperties.getProperty("endPoint"));
		req.setTimeout(serviceProperties.getProperty("timeOut"));
		req.setRequestTime(startDate);
		req.setApiName(serviceName);
		req.setHost(com.api.ivr.global.constants.GlobalConstants.TPSystem_Ebbs);

		/// ASSIGN ALL PARAMS TO HASHMAP
				inputParams.put("serviceProperties", serviceProperties);
		inputParams.put("reqObj", req);
		
return inputParams;
		
	}catch (Exception e) {
		System.out.println("Exception :"+ e.getMessage());
	}
		return inputParams;
	}

	public Map<String, Object> generateOTP(GenerateOTP_Req req) {
		Map<String, Object> inputParams = new HashMap<String, Object>();
		try {
		
		SimpleDateFormat formatter = new SimpleDateFormat(com.api.ivr.global.constants.GlobalConstants.DateTimeFormat);
		String startDate = formatter.format(new Date());
		
		String hostLoggerName = com.api.ivr.global.constants.GlobalConstants.HostLog_UAAS;
		String serviceName = "generateOTP";
		String trackingID = utilities.generateTrackingId();
		Properties properties = new Properties();

		properties.put("serviceName", serviceName);
		properties.put("trackingID", trackingID);
		properties.put("tpSystem", com.api.ivr.global.constants.GlobalConstants.TPSystem_UAAS);
		properties.put("hostLoggerName", hostLoggerName);

		/// LOAD SERVICE PROPERTIES
		/****
		 * 
		 * Load all mandatory properties.
		 * Load the properties from file based on service name.
		 * @return config.properties, operation.properties, EBBSpayload.properties and parametric.properties
		 * 
		 ****/
		Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

		if (serviceProperties.getProperty("ERROR_CODE") != null
				&& com.api.ivr.global.constants.GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
			System.out.println(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

			throw new CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
					"Exception occurs while fetching environment properties");
		}

		/// CLEAR PROPERTIES
		properties.clear();

		/// ASSIGN PARAMETER
		req.setTrackingId(trackingID);
		req.setServiceName(serviceName);
		req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
		req.setServiceId(serviceProperties.getProperty("serviceId"));
		req.setEndPoint(serviceProperties.getProperty("endPoint"));
		req.setTimeout(serviceProperties.getProperty("timeOut"));
		req.setRequestTime(startDate);
		req.setApiName(serviceName);
		req.setHost(com.api.ivr.global.constants.GlobalConstants.TPSystem_UAAS);

		/// ASSIGN ALL PARAMS TO HASHMAP
				inputParams.put("serviceProperties", serviceProperties);
		inputParams.put("reqObj", req);
		
return inputParams;
		
	}catch (Exception e) {
		System.out.println("Exception :"+ e.getMessage());
	}
		return inputParams;
		
	}
	
	public Map<String, Object> validateOTP(ValidateOTP_Req req) {
		Map<String, Object> inputParams = new HashMap<String, Object>();
		try {
			
			String hostLoggerName = com.api.ivr.global.constants.GlobalConstants.HostLog_UAAS;
			String serviceName = "validateOTP";
			String trackingID = utilities.generateTrackingId();
			
			SimpleDateFormat formatter = new SimpleDateFormat(com.api.ivr.global.constants.GlobalConstants.DateTimeFormat);
			String startDate = formatter.format(new Date());

			
			Properties properties = new Properties();

			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", com.api.ivr.global.constants.GlobalConstants.TPSystem_UAAS);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& com.api.ivr.global.constants.GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				
				System.out.println(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(com.api.ivr.global.constants.GlobalConstants.TPSystem_UAAS);

			/// ASSIGN ALL PARAMS TO HASHMA
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			return inputParams;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return inputParams ;
	}
	
	public Map<String, Object> customerIdentificationAccNum(CustomerIdentficationAccNum_Req req) {
		Map<String, Object> inputParams = new HashMap<String, Object>();
		try {
			
			String hostLoggerName = com.api.ivr.global.constants.GlobalConstants.HostLog_Ebbs;
			String serviceName = "getCustomerIdentificationAcctNum";
			String trackingID = utilities.generateTrackingId();
			
			SimpleDateFormat formatter = new SimpleDateFormat(com.api.ivr.global.constants.GlobalConstants.DateTimeFormat);
			String startDate = formatter.format(new Date());

			
			Properties properties = new Properties();


			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", com.api.ivr.global.constants.GlobalConstants.TPSystem_Ebbs);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& com.api.ivr.global.constants.GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				
				System.out.println(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(com.api.ivr.global.constants.GlobalConstants.TPSystem_Ebbs);
			/// ASSIGN ALL PARAMS TO HASHMA
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			return inputParams;
		}catch (Exception e) {
		System.out.println(e.getMessage());
		}
		return inputParams ;
		
	}
	
	public Map<String, Object> getIdentifyCardNum(CustomerIdenificationCardNum_Req req) {
		Map<String, Object> inputParams = new HashMap<String, Object>();
		try {
			
			String hostLoggerName = com.api.ivr.global.constants.GlobalConstants.HostLog_C400;
			String serviceName = "getCustomerIdentificationCardNum";
			String trackingID = utilities.generateTrackingId();

			SimpleDateFormat formatter = new SimpleDateFormat(com.api.ivr.global.constants.GlobalConstants.DateTimeFormat);
			String startDate = formatter.format(new Date());

			
			Properties properties = new Properties();


			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", com.api.ivr.global.constants.GlobalConstants.TPSystem_C400);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& com.api.ivr.global.constants.GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				
				System.out.println(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(com.api.ivr.global.constants.GlobalConstants.TPSystem_C400);
			/// ASSIGN ALL PARAMS TO HASHMA
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			return inputParams;
		}catch (Exception e) {
		System.out.println(e.getMessage());
		}
		return inputParams ;
		

}
	public Map<String, Object> getIdentifyDebitNum(CustomerIdentifyDebitcardnum_Req req) {
		Map<String, Object> inputParams = new HashMap<String, Object>();
		try {
			
			String hostLoggerName = com.api.ivr.global.constants.GlobalConstants.HostLog_Euronet;
			String serviceName = "getCustomerIdentificationDebtCardNum";
			String trackingID = utilities.generateTrackingId();
			String tpSystem = com.api.ivr.global.constants.GlobalConstants.TPSystem_Euronet;

			SimpleDateFormat formatter = new SimpleDateFormat(com.api.ivr.global.constants.GlobalConstants.DateTimeFormat);
			String startDate = formatter.format(new Date());

			
			Properties properties = new Properties();


			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", tpSystem);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& com.api.ivr.global.constants.GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				
				System.out.println(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(tpSystem);

			
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			return inputParams;
		}catch (Exception e) {
		System.out.println(e.getMessage());
		}
		return inputParams ;
		
		
	}
	
	public Map<String, Object> getContactDetails(CustomerContactDetails_Req req) {
		Map<String, Object> inputParams = new HashMap<String, Object>();
		try {
			String hostLoggerName = com.api.ivr.global.constants.GlobalConstants.HostLog_Ebbs;
			String serviceName = "getCustomerContactDetails";
			String trackingID = utilities.generateTrackingId();
			

			SimpleDateFormat formatter = new SimpleDateFormat(com.api.ivr.global.constants.GlobalConstants.DateTimeFormat);
			String startDate = formatter.format(new Date());

			
			Properties properties = new Properties();


			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", com.api.ivr.global.constants.GlobalConstants.TPSystem_Ebbs);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& com.api.ivr.global.constants.GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				
				System.out.println(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(com.api.ivr.global.constants.GlobalConstants.TPSystem_Ebbs);

			
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			return inputParams;
		}catch (Exception e) {
		System.out.println(e.getMessage());
		}
		return inputParams ;
		
	}
	
	public Map<String, Object> validateTpin(ValidateTPIN_Req req) {
		Map<String, Object> inputParams = new HashMap<String, Object>();
		try {
			String hostLoggerName = com.api.ivr.global.constants.GlobalConstants.HostLog_CASAS;
			String serviceName = "validateTPIN";
			String trackingID = utilities.generateTrackingId();
			

			SimpleDateFormat formatter = new SimpleDateFormat(com.api.ivr.global.constants.GlobalConstants.DateTimeFormat);
			String startDate = formatter.format(new Date());

			
			Properties properties = new Properties();


			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", com.api.ivr.global.constants.GlobalConstants.TPSystem_CASAS);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& com.api.ivr.global.constants.GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				
				System.out.println(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(com.api.ivr.global.constants.GlobalConstants.TPSystem_CASAS);

			
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			return inputParams;
		}catch (Exception e) {
		System.out.println(e.getMessage());
		}
		return inputParams ;
		
	}
}
