package com.api.ivr.endpoints;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ivr.db.entity.AMRIvrIntraction;
import com.api.ivr.db.service.impl.DBServiceImpl;
import com.api.ivr.exception.CustomException;
import com.api.ivr.global.constants.GlobalConstants;
import com.api.ivr.hosthelper.HostHelper;
import com.api.ivr.model.CommonInput;
import com.api.ivr.model.CustomerIdentficationAccNum_Req;
import com.api.ivr.model.CustomerIdentficationAccNum_Res;
import com.api.ivr.model.CustomerIdentificationResponseData;
import com.api.ivr.model.GenerateOTP_Req;
import com.api.ivr.model.GenerateOTP_Res;
import com.api.ivr.model.IdentifyCustomerRMNRespose;
import com.api.ivr.model.IdentifyCustomer_Req;
import com.api.ivr.model.IdentifyCustomer_Res;
import com.api.ivr.model.RandomChallengeVO;
import com.api.ivr.model.ValidateOTPResponse_Data;
import com.api.ivr.model.ValidateOTP_Req;
import com.api.ivr.model.ValidateOTP_Res;
import com.api.ivr.model.customercontactdetails.CustomerContactDetails_Req;
import com.api.ivr.model.customercontactdetails.CustomerContactDetails_Res;
import com.api.ivr.model.customeridentifycardnum.CustomerIdenificationCardNum_Req;
import com.api.ivr.model.customeridentifycardnum.CustomerIdentificationCardNum_Res;
import com.api.ivr.model.customeridentifydebitcardnum.CustomerIdentifyDebitcardnum_Req;
import com.api.ivr.model.customeridentifydebitcardnum.CustomerIdentifyDebitcardnum_Res;
import com.api.ivr.model.validateTpin.ValidateTPIN_Req;
import com.api.ivr.util.GetConfigProperties;
import com.api.ivr.util.Utilities;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EndPoint_ApiServices {
	@Autowired
	Utilities utilities;

	@Autowired
	HostHelper hostHelper;
	
	@Autowired
	private DBServiceImpl db;
	@Autowired
	GetConfigProperties getConfigProperties;

	public IdentifyCustomer_Res IdentifyCustomerRMN(Map<String, Object> inputparams, IdentifyCustomer_Req reqObj) {
		IdentifyCustomer_Res resObj = new IdentifyCustomer_Res();
		Properties serviceProps = (Properties) inputparams.get("serviceProperties");
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		try {
			Response responseMessage = null;
			String xmlString = serviceProps.getProperty("requestPayload");

			Map<String, Object> inputElemets = new HashMap<>();
			inputElemets.put("mobile", reqObj.getMobile());
			inputElemets.put("id", serviceProps.getProperty("trackingID").replace("IVR-", ""));
			String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);

			System.out.println(utilities.getCurrentClassAndMethodName() + ". Request is: " + requestJson);

			System.out.println(
					"Session ID : " + " " + utilities.getCurrentClassAndMethodName() + ". Request is: " + requestJson);

			serviceProps.setProperty("requestBody", requestJson);
			reqObj.setRequestBody(requestJson);
			String token = utilities.callOAuth2Token(serviceProps);

			/// Token Validation
			if (token == null || "".equals(token.trim())) {

				System.out.println(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

				throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
						GlobalConstants.FAILURE + ". The token is null/empty");
			}
			serviceProps.setProperty("token", token);
			reqObj.setToken(token);

			serviceProps.setProperty("httpMethod", HttpMethod.POST);
			inputparams.put("serviceProperties", serviceProps);

			Map<String, String> httpHeaderParams = new HashMap<>();
			httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json");
			httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
			httpHeaderParams.put("request-country", serviceProps.getProperty("requestCountry").toUpperCase());
			httpHeaderParams.put("message-sender", serviceProps.getProperty("message-sender"));
			httpHeaderParams.put("source-system", serviceProps.getProperty("source-system"));
			httpHeaderParams.put("countrycode", serviceProps.getProperty("countrycode"));

			inputparams.put("httpHeaderParams", httpHeaderParams);

			/// Call EndPoint API
			responseMessage = hostHelper.invokeHttpsWebservice(inputparams);

			if (responseMessage != null && responseMessage.getEntity() != null
					&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

				System.out.println(utilities.getCurrentClassAndMethodName() + ". Response : "
						+ responseMessage.getEntity().toString());

				System.out.println(
						" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
								+ ". Response : " + responseMessage.getEntity().toString());

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the
				 * success code and return to IVR. otherwise error message received from
				 * endPoint. mapping that error message data and return to IVR.
				 * 
				 ****/

				if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
					IdentifyCustomerRMNRespose dataObjects = objectMapper
							.readValue(responseMessage.getEntity().toString(), IdentifyCustomerRMNRespose.class);
					if (dataObjects != null) {
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
						resObj.setErrormessage(GlobalConstants.SUCCESS);
						resObj.setResponse(dataObjects);
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}
				} else {
					IdentifyCustomerRMNRespose dataObjects = objectMapper
							.readValue(responseMessage.getEntity().toString(), IdentifyCustomerRMNRespose.class);
					if (dataObjects != null) {
						resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects.getMessage());
					}  else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}

				}
			} else {
				System.out.println(utilities.getCurrentClassAndMethodName() + ". Empty Response");

				System.out.println(" SESSION ID : " + reqObj.getSessionId() + " "
						+ utilities.getCurrentClassAndMethodName() + ". Empty Response");

				throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
						"Response is Null, Setting Failure code");
			}

			String endTime = dateTimeFormat.format(new Date());
			resObj.setEndTime(endTime);
		} catch (SocketException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETEXCEPTION_700007);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket Closed");
			System.out.println("SESSION ID : " + " " + utilities.getCurrentClassAndMethodName()
					+ " Socket Exception occured." + e.getMessage());
		} catch (SocketTimeoutException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket timeout Occured");
		} catch (JsonParseException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_JSONPARSEEXCEPTION_700009);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : JSON Parse occured");
		} catch (ConnectTimeoutException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_CONNECTTIMEOUTEXCEPTION_700010);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Connect TimeOut Exception occured");
		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : IO Exception occured");
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
		} catch (ParseException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_PARSEEXCEPTION_700005);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Parse Exception occured");
		} catch (IllegalArgumentException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Illegal Argument Exception Occured");

		} catch (URISyntaxException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : URI syntax Exception Occured");

		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception ");
			System.out.println(e.getMessage());

		}
return resObj;
	}
	
	
	public GenerateOTP_Res generateOTP(Map<String, Object> inputparams, GenerateOTP_Req reqObj) {
		GenerateOTP_Res resObj = new GenerateOTP_Res();
		try {
			Response responseMessage = null;
			 Response responseMessageRandom = null;
			 responseMessageRandom =getRandomChallengeResponse(inputparams, reqObj.getRelId());
			 
				if (responseMessageRandom != null && responseMessageRandom.getEntity() != null
						&& !responseMessageRandom.getEntity().toString().equalsIgnoreCase("")) {
                     System.out.println(utilities.getCurrentClassAndMethodName()
 							+ ". Response (Random Challenge API) : " + responseMessageRandom.getEntity().toString());

                     System.out.println(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
						+ ". Response : " + responseMessageRandom.getEntity().toString());
					
					ObjectMapper objectMapperRandom = new ObjectMapper();
					objectMapperRandom.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
					RandomChallengeVO randomChallengeVo = objectMapperRandom.readValue(responseMessageRandom.getEntity().toString(),
							RandomChallengeVO.class);

					/***
					 * check status code from endPoint API response.
					 * 
					 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
					 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
					 * 
					 * ****/
					if ((responseMessageRandom.getStatus() == 200 || responseMessageRandom.getStatus() == 201)
							&& randomChallengeVo.getErrorMessage() != null
							&& "".equalsIgnoreCase(randomChallengeVo.getErrorMessage())) {

						////CALL GENERATE OTP SERVICE,IF DUMMY FLG IS N
						if (!"Y".equalsIgnoreCase(reqObj.getDummyFlag())) {
							responseMessage = getGenerateOTPResponse(inputparams);
						}		
						
						if (responseMessage != null && responseMessage.getEntity() != null
								&& !responseMessage.getEntity().toString().trim().equalsIgnoreCase("")) {
							
							System.out.println(utilities.getCurrentClassAndMethodName() + ". Response : "
									+ responseMessage.getEntity().toString());
							System.out.println(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
							+ ". Response : " + responseMessage.getEntity().toString());
							
							ObjectMapper objectMapper = new ObjectMapper();
							objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
							GenerateOTP_Res otpvo = objectMapper.readValue(responseMessage.getEntity().toString(),
									GenerateOTP_Res.class);
							
							/***
							 * check status code from endPoint API response.
							 * 
							 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
							 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
							 * 
							 * ****/
							
							if ((responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201)
									&& otpvo.getErrormessage() != null
									&& "".equalsIgnoreCase(otpvo.getErrormessage())) {

								resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
								resObj.setErrormessage(GlobalConstants.SUCCESS);

								com.api.ivr.model.GenerateOtpResponseData responseData = new com.api.ivr.model.GenerateOtpResponseData();

								RandomChallengeVO randomChallengeVO = objectMapper
										.readValue(responseMessageRandom.getEntity().toString(), RandomChallengeVO.class);

								// base64Challenge
								responseData.setEncryptedBlock(randomChallengeVO.getBase64Challenge());
								// modulus
								responseData.setCode(randomChallengeVO.getModulus());
								// exponent
								responseData.setExponent(randomChallengeVO.getExponent());
								// key index
								responseData.setKeyIndex(randomChallengeVO.getKeyIndex());
								// sn
								responseData.setOtpSn(otpvo.getResponse().getOtpSn());
								resObj.setResponse(responseData);

								
							} else {
								if (otpvo.getResponse().getCode() != null) {
									resObj.setErrorcode(otpvo.getResponse().getCode());
									resObj.setErrormessage(otpvo.getErrormessage());
								} else {
									resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
									resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
								}
							}
							
						} else {
							
							System.out.println(utilities.getCurrentClassAndMethodName() + ". Empty Response");
							
							System.out.println(" SESSION ID : " + reqObj.getSessionId() + " "
									+ utilities.getCurrentClassAndMethodName() + ". Empty Response");


							throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
									"Response is Null, Setting Failure code");
						}
						
						
					} else {
						if (randomChallengeVo.getStatusCode() != null) {
							resObj.setErrorcode(randomChallengeVo.getStatusCode());
							resObj.setErrormessage(randomChallengeVo.getErrorMessage());
						} else {
							resObj.setErrorcode(String.valueOf(responseMessageRandom.getStatus()));
							resObj.setErrormessage(GlobalConstants.FAILURE);
						}
					}

				} else {
					
					System.out.println(utilities.getCurrentClassAndMethodName() + ". Empty Response in Random Challenge API");
					
					System.out.println(" SESSION ID : " + reqObj.getSessionId() + " "
							+ utilities.getCurrentClassAndMethodName()
							+ ". Empty Response in Random Challenge API");

					throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
							"Response is Null, Setting Failure code");
				}

			} catch (SocketException e) {
				resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETEXCEPTION_700007);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket Closed");
			} catch (SocketTimeoutException e) {
				resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket timeout Occured");
			} catch (JsonParseException e) {
				resObj.setErrorcode(GlobalConstants.ERRORCODE_JSONPARSEEXCEPTION_700009);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : JSON Parse occured");
			} catch (ConnectTimeoutException e) {
				resObj.setErrorcode(GlobalConstants.ERRORCODE_CONNECTTIMEOUTEXCEPTION_700010);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Connect TimeOut Exception occured");
			} catch (IOException e) {
				resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : IO Exception occured");
			} catch (NullPointerException e) {
				resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
			} catch (ParseException e) {
				resObj.setErrorcode(GlobalConstants.ERRORCODE_PARSEEXCEPTION_700005);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Parse Exception occured");
			} catch (IllegalArgumentException e) {
				resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Illegal Argument Exception Occured");

			} catch (URISyntaxException e) {
				resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : URI syntax Exception Occured");

			} catch (CustomException e) {
				resObj.setErrorcode(e.getErrorCode());
				resObj.setErrormessage(e.getErrorMsg());
			} catch (Exception e) {
				resObj.setErrorcode(GlobalConstants.FAILURECODE);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception ");
		
			}
		return resObj;
		
	}

private Response getGenerateOTPResponse(Map<String, Object> inputParams) throws Exception {
	
	Response responseMessage = null;
	
	GenerateOTP_Req reqObj = (GenerateOTP_Req) inputParams.get("reqObj");
	Properties serviceProps = (Properties) inputParams.get("serviceProperties");
	
	String sessionId = reqObj.getSessionId();
	
	
	//// Generate OTP Call
	String xmlString = serviceProps.getProperty("requestPayload");

	/// Form REquest
	Map<String, Object> inputElemets = new HashMap<>();
	inputElemets.put("appId", serviceProps.getProperty("appId"));
	inputElemets.put("groupId", serviceProps.getProperty("groupId"));
	inputElemets.put("userId", reqObj.getRelId());
	String clientRequestId = (serviceProps.getProperty("trackingID")).substring(0,40);
	inputElemets.put("clientRequestId", clientRequestId);
	inputElemets.put("apiKey", serviceProps.getProperty("apiKey"));

	inputElemets.put("mobile", reqObj.getMobileNumber());
	//SimpleDateFormat messageTemplateDate = utilities.getDateFormat(GlobalConstants.DateTimeFormat);
	//String msgTemp = messageTemplateDate.format(new Date()) + " " + serviceProps.getProperty("msgTemplate");
	String msgTemp =serviceProps.getProperty("msgTemplate");

	////Expiry OTP TimeSetup
	msgTemp = utilities.getOTPExpiryMsgTemplate(msgTemp);
	byte[] encodedmessageTemplate = Base64.encodeBase64(msgTemp.getBytes("UTF-8"));
	
	//inputElemets.put("msgTemplate", serviceProps.getProperty("msgTemplate"));
	if("Y".equalsIgnoreCase(serviceProps.getProperty("isEncoded"))){
		inputElemets.put("msgTemplate", new String(encodedmessageTemplate));
	}else {
		inputElemets.put("msgTemplate", msgTemp);
	}
	
	inputElemets.put("isEncoded", serviceProps.getProperty("isEncoded"));
	//inputElemets.put("resendFlag", serviceProps.getProperty("resendFlag"));

	inputElemets.put("clientIP", Inet4Address.getLocalHost().getHostAddress());
	inputElemets.put("remoteIP", Inet4Address.getLocalHost().getHostAddress());

	/***
	 * Load parameter to payload request
	 * ***/
	
	String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);
	
	System.out.println(utilities.getCurrentClassAndMethodName() + ". Request is: " + requestJson);

	System.out.println("Session ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ ". Request is: " + requestJson);

	serviceProps.setProperty("requestBody", requestJson);
	reqObj.setRequestBody(requestJson);

	//// Token Generation
	String userName = serviceProps.getProperty("uaasUserName");
	String encPassword = serviceProps.getProperty("uaasPassword");
	String password = new String(java.util.Base64.getDecoder().decode(encPassword.getBytes()));
	
	String token = utilities.callBasicAuthToken(userName, password);

	/// Token Validation
	if (token == null || "".equals(token.trim())) {
System.out.println(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");
		throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
				GlobalConstants.FAILURE + ". The required token is null/empty");
	}
	serviceProps.setProperty("token", token);
	reqObj.setToken(token);

	serviceProps.setProperty("httpMethod", HttpMethod.POST);
	inputParams.put("serviceProperties", serviceProps);

	/// Header Parameter
	/***
	 * 
	 * form Http Headers Parameter and pass this parameter to helper class. 
	 * then the helper class call endpoint api with header parameter and return response
	 * 
	 * (for helper class reuse purpose)
	 * ***/
	
	Map<String, String> httpHeaderParams = new HashMap<>();
	httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
	//httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
	inputParams.put("httpHeaderParams", httpHeaderParams);

	///Call EndPoint API
	responseMessage = hostHelper.invokeHttpsWebservice(inputParams);
	
	return responseMessage;
}

	
	private Response getRandomChallengeResponse(Map<String, Object> inputParams, String userId) throws Exception {
		RandomChallengeVO randomChallengeVO = new RandomChallengeVO();
		Response responseMessage = null;

		CommonInput reqObj = (CommonInput) inputParams.get("reqObj");
		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		String sessionId = reqObj.getSessionId();


		try {

			String serviceNameN = "getRandomChallenge";
			String trackingIDN = utilities.generateTrackingId();

			/// LOAD PROPERTIES
			Properties properties = new Properties();

			properties.put("sessionId", sessionId);
			properties.put("serviceName", serviceNameN);
			properties.put("trackingID", trackingIDN);
			properties.put("tpSystem", serviceProps.getProperty("tpSystem"));
			properties.put("hostLoggerName", serviceProps.getProperty("hostLoggerName"));

			/// LOAD SERVICE PROPERTIES
			serviceProps = new Properties();
			serviceProps = getConfigProperties.getServiceConfig(properties);

			properties.clear();

			if (serviceProps.getProperty("ERROR_CODE") != null
					&& GlobalConstants.FAILURECODE.equals(serviceProps.getProperty("ERROR_CODE"))) {
				System.out.println(utilities.getCurrentClassAndMethodName()
						+ ". Error found in service properties (Random Challenge API)");

				randomChallengeVO.setStatusCode(GlobalConstants.FAILURECODE);
				randomChallengeVO.setErrorMessage(
						GlobalConstants.FAILURE + ". Error found in service properties (Random Challenge API)");
				return Response.status(400).entity(randomChallengeVO).build();
			}

			inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProps);
			inputParams.put("reqObj", reqObj);

			//// Generate Random Challenge Call
			String xmlString = serviceProps.getProperty("requestPayload");

			/// Form Request
			Map<String, Object> inputElemets = new HashMap<>();
			inputElemets.put("appId", serviceProps.getProperty("appId"));
			inputElemets.put("userId", userId);
			inputElemets.put("apiKey", serviceProps.getProperty("apiKey"));
			inputElemets.put("groupId", serviceProps.getProperty("groupId"));
			String clientRequestId = (serviceProps.getProperty("trackingID")).substring(0,40);
			inputElemets.put("clientRequestId", clientRequestId);

			inputElemets.put("clientIP", Inet4Address.getLocalHost().getHostAddress());
			inputElemets.put("remoteIP", Inet4Address.getLocalHost().getHostAddress());

			/***
			 * Load parameter to payload request
			 * ***/
			
			String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);			
			System.out.println(utilities.getCurrentClassAndMethodName() + ". Request is (Random Challenge API) : " + requestJson);

			serviceProps.setProperty("requestBody", requestJson);
			reqObj.setRequestBody(requestJson);

			//// Token Generation
			String userName = serviceProps.getProperty("uaasUserName");
			String encPassword = serviceProps.getProperty("uaasPassword");
			String password = new String(java.util.Base64.getDecoder().decode(encPassword.getBytes()));
			String token = utilities.callBasicAuthToken(userName, password);

			/// Token Validation
			if (token == null || "".equals(token.trim())) {
				System.out.println(utilities.getCurrentClassAndMethodName()
						+ ". The required token is null/empty (Random Challenge API)");

				randomChallengeVO.setStatusCode(GlobalConstants.ERRORCODE_TOKEN_GEN_700014);
				randomChallengeVO.setErrorMessage(
						GlobalConstants.FAILURE + ". The required token is null/empty (Random Challenge API)");
				return Response.status(400).entity(randomChallengeVO).build();
				
			}
			serviceProps.setProperty("token", token);
			reqObj.setToken(token);
			serviceProps.setProperty("httpMethod", HttpMethod.POST);
			inputParams.put("serviceProperties", serviceProps);
			Map<String, String> httpHeaderParams = new HashMap<>();
			httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
			//httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
			inputParams.put("httpHeaderParams", httpHeaderParams);

			///Call EndPoint API
			responseMessage = hostHelper.invokeHttpsWebservice(inputParams);

		} catch (Exception e) {
			System.out.println("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Exception Occured when Random challenge API calling " + e.getMessage());
		}
		return responseMessage;
	}

	public ValidateOTP_Res validateOTP(Map<String, Object> inputparams, ValidateOTP_Req reqObj) {
		ValidateOTP_Res resObj = new ValidateOTP_Res();
		Properties serviceProps = (Properties) inputparams.get("serviceProperties");
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		
		try {
			Response responseMessage = null;
			String xmlString = serviceProps.getProperty("requestPayload");

			Map<String, Object> inputElemets = new HashMap<>();
			inputElemets.put("appId", serviceProps.getProperty("appId"));
			inputElemets.put("groupId", serviceProps.getProperty("groupId"));
			inputElemets.put("userId", reqObj.getRelID());
			String clientRequestId = (serviceProps.getProperty("trackingID")).substring(0,40);
			inputElemets.put("clientRequestId", clientRequestId);
			inputElemets.put("apiKey", serviceProps.getProperty("apiKey"));

			inputElemets.put("otpSn", reqObj.getOtpSn());
			/// RSA Data encryption
			inputElemets.put("encOtp", utilities.encryptDataRSA(reqObj.getExponent(), reqObj.getModulus(),
					reqObj.getEncryptedBlock(), reqObj.getOtp(), reqObj.getSessionId()));

			inputElemets.put("purpose", serviceProps.getProperty("purpose"));
			inputElemets.put("keyIndex", reqObj.getKeyIndex());

			inputElemets.put("clientIP", Inet4Address.getLocalHost().getHostAddress());
			inputElemets.put("remoteIP", Inet4Address.getLocalHost().getHostAddress());

			/***
			 * Load parameter to payload request
			 * ***/
			
			String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);
			
			System.out.println(utilities.getCurrentClassAndMethodName() + ". Request is: " + requestJson);
			
			System.out.println("Session ID : " + " " + utilities.getCurrentClassAndMethodName()
					+ ". Request is: " + requestJson);
			
			System.out.println("Session ID : " +  " " + utilities.getCurrentClassAndMethodName()
					+ ". Request is: " + requestJson);

			serviceProps.setProperty("requestBody", requestJson);
			reqObj.setRequestBody(requestJson);

			//// Token Generation
			String userName = serviceProps.getProperty("uaasUserName");
			String encPassword = serviceProps.getProperty("uaasPassword");
			String password = new String(java.util.Base64.getDecoder().decode(encPassword.getBytes()));
			String token = utilities.callBasicAuthToken(userName, password);

			/// Token Validation
			if (token == null || "".equals(token.trim())) {
				
				System.out.println(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

				throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
						GlobalConstants.FAILURE + ". The required token is null/empty");
			}
			serviceProps.setProperty("token", token);
			reqObj.setToken(token);

			serviceProps.setProperty("httpMethod", HttpMethod.POST);
			inputparams.put("serviceProperties", serviceProps);

			/// Header Parameter
			/***
			 * 
			 * form Http Headers Parameter and pass this parameter to helper class. 
			 * then the helper class call endpoint api with header parameter and return response
			 * 
			 * (for helper class reuse purpose)
			 * ***/
			
			Map<String, String> httpHeaderParams = new HashMap<>();
			//httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json");
			httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
			//httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");

			inputparams.put("httpHeaderParams", httpHeaderParams);

			///Call EndPoint API
			responseMessage = hostHelper.invokeHttpsWebservice(inputparams);

		if (responseMessage != null && responseMessage.getEntity() != null
				&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {
			
			System.out.println(utilities.getCurrentClassAndMethodName() + ". Response : "
					+ responseMessage.getEntity().toString());
			
			System.out.println(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
							+ ". Response : " + responseMessage.getEntity().toString());

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

			ValidateOTPResponse_Data dataObjects = objectMapper.readValue(responseMessage.getEntity().toString(),
					ValidateOTPResponse_Data.class);
			
			/***
			 * check status code from endPoint API response.
			 * 
			 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
			 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
			 * 
			 * ****/
			if ((responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201)
					&& dataObjects != null
					&& dataObjects.getErrorMessage() != null
					&& "".equalsIgnoreCase(dataObjects.getErrorMessage())) {
				
//				ValidateOTPResponseData dataObjects = objectMapper.readValue(responseMessage.getEntity().toString(),
//						ValidateOTPResponseData.class);
						
				if (dataObjects != null) {
					resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
					resObj.setErrormessage(GlobalConstants.SUCCESS);
					resObj.setResponse(dataObjects);
				} else {
					resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
				}
			} else {
//				ValidateOTPResponseData dataObjects = objectMapper.readValue(responseMessage.getEntity().toString(),
//						ValidateOTPResponseData.class);
				
				if (dataObjects != null && dataObjects.getStatusCode() != null) {
					resObj.setErrorcode(dataObjects.getStatusCode());
					resObj.setErrormessage(dataObjects.getErrorMessage());
				} else {
					resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
				}
				
			}
		} else {
			
			System.out.println(utilities.getCurrentClassAndMethodName() + ". Empty Response");
			
			System.out.println(" SESSION ID : " + reqObj.getSessionId() + " "
					+ utilities.getCurrentClassAndMethodName() + ". Empty Response");


			throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
					"Response is Null, Setting Failure code");
		}

		String endTime = dateTimeFormat.format(new Date());
		resObj.setEndTime(endTime);
	
	}
	 catch (SocketException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETEXCEPTION_700007);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket Closed");
	} catch (SocketTimeoutException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket timeout Occured");
	} catch (JsonParseException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_JSONPARSEEXCEPTION_700009);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : JSON Parse occured");
	} catch (ConnectTimeoutException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_CONNECTTIMEOUTEXCEPTION_700010);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Connect TimeOut Exception occured");
	} catch (IOException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : IO Exception occured");
	} catch (NullPointerException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
	} catch (ParseException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_PARSEEXCEPTION_700005);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Parse Exception occured");
	} catch (IllegalArgumentException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Illegal Argument Exception Occured");

	} catch (URISyntaxException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : URI syntax Exception Occured");
	} catch (CustomException e) {
		resObj.setErrorcode(e.getErrorCode());
		resObj.setErrormessage(e.getErrorMsg());
	} catch (Exception e) {
		resObj.setErrorcode(GlobalConstants.FAILURECODE);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception ");
	}
	return resObj;		
	}
	
	public CustomerIdentficationAccNum_Res customerIdentificationAccNum(Map<String, Object> inputparams, CustomerIdentficationAccNum_Req reqObj) {
		CustomerIdentficationAccNum_Res resObj = new CustomerIdentficationAccNum_Res();
		Properties serviceProps = (Properties) inputparams.get("serviceProperties");
		try {
			Response responseMessage=null;
			String xmlString = serviceProps.getProperty("requestPayload");

			Map<String, Object> inputElemets = new HashMap<>();
			inputElemets.put("casa-number", reqObj.getAcctNum());
			inputElemets.put("casa-currency-code", reqObj.getCurrency_code());

			String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);
			
			System.out.println(utilities.getCurrentClassAndMethodName() + ". Request is: " + requestJson);
			
			System.out.println("Session ID : " + " " + utilities.getCurrentClassAndMethodName()
					+ ". Request is: " + requestJson);



			serviceProps.setProperty("requestBody", requestJson);
			reqObj.setRequestBody(requestJson);

			//// Token Generation
			String token = utilities.callOAuth2Token(serviceProps);

			/// Token Validation
			if (token == null || "".equals(token.trim())) {
				System.out.println(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

				throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
						GlobalConstants.FAILURE + ". The token is null/empty");
			}
			serviceProps.setProperty("token", token);
			reqObj.setToken(token);

			serviceProps.setProperty("httpMethod", HttpMethod.GET);
			inputparams.put("serviceProperties", serviceProps);

			/// Header Parameter
			/***
			 * 
			 * form Http Headers Parameter and pass this parameter to helper class. 
			 * then the helper class call endpoint api with header parameter and return response
			 * 
			 * (for helper class reuse purpose)
			 * ***/
			
			Map<String, String> httpHeaderParams = new HashMap<>();
			httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json");
			httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
			httpHeaderParams.put("request-country", serviceProps.getProperty("requestCountry").toUpperCase());
			httpHeaderParams.put("message-sender", serviceProps.getProperty("message-sender"));
			httpHeaderParams.put("source-system", serviceProps.getProperty("source-system"));
			httpHeaderParams.put("countrycode", serviceProps.getProperty("countrycode"));

			inputparams.put("httpHeaderParams", httpHeaderParams);

			///Call EndPoint API
			responseMessage = hostHelper.invokeHttpsWebservice(inputparams);
		

		if (responseMessage != null && responseMessage.getEntity() != null
				&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

			System.out.println(utilities.getCurrentClassAndMethodName() + ". Response : "
					+ responseMessage.getEntity().toString());

			System.out.println(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
							+ ". Response : " + responseMessage.getEntity().toString());

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

			/***
			 * check status code from endPoint API response.
			 * 
			 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
			 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
			 * 
			 * ****/
			
			if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
				CustomerIdentificationResponseData dataObjects = objectMapper.readValue(
						responseMessage.getEntity().toString(), CustomerIdentificationResponseData.class);
				if (dataObjects != null) {
					resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
					resObj.setErrormessage(GlobalConstants.SUCCESS);
					resObj.setResponse(dataObjects);
				} else {
					resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
				}
			} else {
				CustomerIdentificationResponseData dataObjects = objectMapper.readValue(
						responseMessage.getEntity().toString(), CustomerIdentificationResponseData.class);
				if (dataObjects != null) {
					resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects);
				}	
				 else {
					resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
				} 
				
			}
		} else {
			
			System.out.println(utilities.getCurrentClassAndMethodName() + ". Empty Response");
			System.out.println(" SESSION ID : " + reqObj.getSessionId() + " "
					+ utilities.getCurrentClassAndMethodName() + ". Empty Response");

			throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
					"Response is Null, Setting Failure code");
		}

	} catch (SocketException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETEXCEPTION_700007);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket Closed");
	} catch (SocketTimeoutException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket timeout Occured");
	} catch (JsonParseException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_JSONPARSEEXCEPTION_700009);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : JSON Parse occured");
	} catch (ConnectTimeoutException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_CONNECTTIMEOUTEXCEPTION_700010);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Connect TimeOut Exception occured");
	} catch (IOException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : IO Exception occured");
	} catch (NullPointerException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
	} catch (ParseException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_PARSEEXCEPTION_700005);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Parse Exception occured");
	} catch (IllegalArgumentException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Illegal Argument Exception Occured");

	} catch (URISyntaxException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : URI syntax Exception Occured");
	} catch (CustomException e) {
		resObj.setErrorcode(e.getErrorCode());
		resObj.setErrormessage(e.getErrorMsg());
	} catch (Exception e) {
		resObj.setErrorcode(GlobalConstants.FAILURECODE);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception ");

	}
return resObj;
	}
	
	public CustomerIdentificationCardNum_Res getidentifyCardNum(Map<String, Object> inputparams, CustomerIdenificationCardNum_Req reqObj) {
		CustomerIdentificationCardNum_Res resObj = new CustomerIdentificationCardNum_Res();
		Properties serviceProps = (Properties) inputparams.get("serviceProperties");
		
		try {
			Response responseMessage=null;
		String xmlString = serviceProps.getProperty("requestPayload");

		Map<String, Object> inputElemets = new HashMap<>();
		inputElemets.put("cardNum", reqObj.getCardNumber());

		/***
		 * Load parameter to payload request
		 * ***/
		String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);

		System.out.println(utilities.getCurrentClassAndMethodName() + ". request JSON is: " + requestJson);

		
		System.out.println(" SESSION ID : " + reqObj.getSessionId() + " "
				+ utilities.getCurrentClassAndMethodName() + ". request JSON is: " + requestJson);

		serviceProps.setProperty("requestBody", requestJson);
		reqObj.setRequestBody(requestJson);

		//// Token Generation
		String token = utilities.callOAuth2Token(serviceProps);

		/// Token Validation
		if (token == null || "".equals(token.trim())) {
			
			System.out.println(utilities.getCurrentClassAndMethodName() + ". The token is null/empty");

			throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
					GlobalConstants.FAILURE + ". The token is null/empty");
		}
		serviceProps.setProperty("token", token);
		reqObj.setToken(token);

		///Endpoint Http Method 
		serviceProps.setProperty("httpMethod", HttpMethod.POST);
		inputparams.put("serviceProperties", serviceProps);

		/// Header Parameter
		/***
		 * 
		 * form Http Headers Parameter and pass this parameter to helper class. 
		 * then the helper class call endpoint api with header parameter and return response
		 * 
		 * (for helper class reuse purpose)
		 * ***/
		Map<String, String> httpHeaderParams = new HashMap<>();
		httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json");
		httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
		httpHeaderParams.put("X-Market", serviceProps.getProperty("requestCountry"));
		httpHeaderParams.put("Transaction-ID", serviceProps.getProperty("trackingID"));
		httpHeaderParams.put("channel", serviceProps.getProperty("channel"));

		inputparams.put("httpHeaderParams", httpHeaderParams);

		///Call EndPoint API
		responseMessage = hostHelper.invokeHttpsWebservice(inputparams);
	

	if (responseMessage != null && responseMessage.getEntity() != null
			&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

		System.out.println(utilities.getCurrentClassAndMethodName() + ". Response : "
				+ responseMessage.getEntity().toString());

		System.out.println(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
						+ ". Response : " + responseMessage.getEntity().toString());
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		/***
		 * check status code from endPoint API response.
		 * 
		 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
		 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
		 * 
		 * ****/
		
		if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
			CustomerIdentificationCardNum_Res dataObjects = objectMapper.readValue(
					responseMessage.getEntity().toString(), CustomerIdentificationCardNum_Res.class);
			if (dataObjects != null) {
				resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
				resObj.setErrormessage(GlobalConstants.SUCCESS);
				resObj.setResponse(dataObjects.getResponse());
			} else {
				resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
			}
		} else {
			CustomerIdentificationResponseData dataObjects = objectMapper.readValue(
					responseMessage.getEntity().toString(), CustomerIdentificationResponseData.class);

			if (dataObjects != null) {
				resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
				resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects);
			}  else {
				resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
			}

		}
	} else {
		
		System.out.println(utilities.getCurrentClassAndMethodName() + ". Empty Response");
		System.out.println(" SESSION ID : " + reqObj.getSessionId() + " "
				+ utilities.getCurrentClassAndMethodName() + ". Empty Response");

		throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
				"Response is Null, Setting Failure code");
	}
} catch (SocketException e) {
	resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETEXCEPTION_700007);
	resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket Closed");
} catch (SocketTimeoutException e) {
	resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008);
	resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket timeout Occured");
} catch (JsonParseException e) {
	resObj.setErrorcode(GlobalConstants.ERRORCODE_JSONPARSEEXCEPTION_700009);
	resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : JSON Parse occured");
} catch (ConnectTimeoutException e) {
	resObj.setErrorcode(GlobalConstants.ERRORCODE_CONNECTTIMEOUTEXCEPTION_700010);
	resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Connect TimeOut Exception occured");
} catch (IOException e) {
	resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
	resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : IO Exception occured");
} catch (NullPointerException e) {
	resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
	resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
} catch (ParseException e) {
	resObj.setErrorcode(GlobalConstants.ERRORCODE_PARSEEXCEPTION_700005);
	resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Parse Exception occured");
} catch (IllegalArgumentException e) {
	resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
	resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Illegal Argument Exception Occured");

} catch (URISyntaxException e) {
	resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
	resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : URI syntax Exception Occured");
} catch (CustomException e) {
	resObj.setErrorcode(e.getErrorCode());
	resObj.setErrormessage(e.getErrorMsg());
} catch (Exception e) {
	resObj.setErrorcode(GlobalConstants.FAILURECODE);
	resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception ");

} 
		return resObj;
	}
	
	public CustomerIdentifyDebitcardnum_Res getIdentifyDebitCard(Map<String, Object> inputparams, CustomerIdentifyDebitcardnum_Req reqObj) {
		CustomerIdentifyDebitcardnum_Res resObj = new CustomerIdentifyDebitcardnum_Res();
		Properties serviceProps = (Properties) inputparams.get("serviceProperties");
		try {
			Response responseMessage=null;
			String xmlString = serviceProps.getProperty("requestPayload");

			SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.dateFormat);
			SimpleDateFormat timeFormat = new SimpleDateFormat(GlobalConstants.timeFormat);
			String sourceDate = dateFormat.format(new Date());
			String sourceTime = timeFormat.format(new Date());

			Map<String, Object> inputElemets = new HashMap<>();
			inputElemets.put("card-number", reqObj.getCardnumber());
			inputElemets.put("user-id",serviceProps.getProperty("Euronet.header.user-id"));
			inputElemets.put("transaction-sequence-number", utilities.generateSequenceNumber());
			inputElemets.put("source-date", sourceDate);
			inputElemets.put("source-time", sourceTime);
			inputElemets.put("transmission-date", sourceDate);
			inputElemets.put("transmission-time", sourceTime);
			inputElemets.put("x-market", serviceProps.getProperty("requestCountry").toUpperCase());

			/// Form input Request
			/***
			 * Load parameter to payload request
			 * ***/
			
			String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);
			System.out.println(utilities.getCurrentClassAndMethodName() + ". request JSON is: " + requestJson);

			
			System.out.println(" SESSION ID : " + reqObj.getSessionId() + " "
					+ utilities.getCurrentClassAndMethodName() + ". request JSON is: " + requestJson);
			
			serviceProps.setProperty("requestBody", requestJson);
			reqObj.setRequestBody(requestJson);

			//// Token Generation
			String token = utilities.callOAuth2Token(serviceProps);

			/// Token Validation
			if (token == null || "".equals(token.trim())) {

				throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
						GlobalConstants.FAILURE + ". The token is null/empty");
			}
			
			
			serviceProps.setProperty("token", token);
			reqObj.setToken(token);

			serviceProps.setProperty("httpMethod", HttpMethod.POST);
			inputparams.put("serviceProperties", serviceProps);

			
			/// Header Parameter
			/***
			 * 
			 * form Http Headers Parameter and pass this parameter to helper class. 
			 * then the helper class call endPoint api with header parameter and return response
			 * 
			 * (for helper class reuse purpose)
			 * ***/
			
			Map<String, String> httpHeaderParams = new HashMap<>();
			httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
			httpHeaderParams.put(HttpHeaders.ACCEPT, "application/json");
			httpHeaderParams.put("x-market", serviceProps.getProperty("requestCountry").toUpperCase());

			inputparams.put("httpHeaderParams", httpHeaderParams);

			///Call EndPoint API - Helper
			responseMessage = hostHelper.invokeHttpsWebservice(inputparams);
		

		if (responseMessage != null && responseMessage.getEntity() != null
				&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

			System.out.println(utilities.getCurrentClassAndMethodName() + ". Response : "
					+ responseMessage.getEntity().toString());

			System.out.println(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
							+ ". Response : " + responseMessage.getEntity().toString());

			ObjectMapper objectMapper = new ObjectMapper();

			/***
			 * check status code from endPoint API response.
			 * 
			 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
			 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
			 * 
			 * ****/
			
			if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
				CustomerIdentifyDebitcardnum_Res dataObjects = objectMapper.readValue(
						responseMessage.getEntity().toString(), CustomerIdentifyDebitcardnum_Res.class);
				
				if (dataObjects != null) {
					if ("00".equals(dataObjects.getResponse().getReasonCode())) {
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
//						resObj.setErrormessage(dataObjects != null && dataObjects.getResponseText() != null
//								? dataObjects.getResponseText()
//								: GlobalConstants.SUCCESS);
						
						resObj.setErrormessage(dataObjects != null && dataObjects.getResponse().getResponseText() != null
								? GlobalConstants.SUCCESS
								: GlobalConstants.SUCCESS);

						resObj.setResponse(dataObjects.getResponse());
					} else {
						resObj.setErrorcode(dataObjects != null
								&& dataObjects.getResponse().getReasonCode() != null
										? dataObjects.getResponse().getResponseCode()
										: GlobalConstants.FAILURECODE);
						resObj.setErrormessage(dataObjects != null
								&& dataObjects.getResponse().getResponseText() != null
										? dataObjects.getResponse().getResponseText()
										: GlobalConstants.FAILURE);
					}
				} else {
					resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
				}
			} else {
				CustomerIdentifyDebitcardnum_Res dataObjects = objectMapper.readValue(
						responseMessage.getEntity().toString(), CustomerIdentifyDebitcardnum_Res.class);

				if (dataObjects != null) {
					resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects);
				}  else {
					resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
				}

			}
		} else {
			
			System.out.println(utilities.getCurrentClassAndMethodName() + ". Empty Response");
			System.out.println(" SESSION ID : " + reqObj.getSessionId() + " "
					+ utilities.getCurrentClassAndMethodName() + ". Empty Response");

			throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
					"Response is Null, Setting Failure code");
		}
	} catch (SocketException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETEXCEPTION_700007);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket Closed");
	} catch (SocketTimeoutException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket timeout Occured");
	} catch (JsonParseException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_JSONPARSEEXCEPTION_700009);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : JSON Parse occured");
	} catch (ConnectTimeoutException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_CONNECTTIMEOUTEXCEPTION_700010);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Connect TimeOut Exception occured");
	} catch (IOException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : IO Exception occured");
	} catch (NullPointerException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
	} catch (ParseException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_PARSEEXCEPTION_700005);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Parse Exception occured");
	} catch (IllegalArgumentException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Illegal Argument Exception Occured");

	} catch (URISyntaxException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : URI syntax Exception Occured");

	} catch (CustomException e) {
		resObj.setErrorcode(e.getErrorCode());
		resObj.setErrormessage(e.getErrorMsg());
	} catch (Exception e) {
		resObj.setErrorcode(GlobalConstants.FAILURECODE);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception ");

	}
		return resObj;
	}
	
	public CustomerContactDetails_Res getContactDetails(Map<String, Object> inputparams, CustomerContactDetails_Req reqObj) {
		CustomerContactDetails_Res resObj = new CustomerContactDetails_Res();
		Properties serviceProps = (Properties) inputparams.get("serviceProperties");
		try {
			Response responseMessage=null;
			String xmlString = serviceProps.getProperty("requestPayload");

			Map<String, Object> inputElemets = new HashMap<>();
			inputElemets.put("profile-id", reqObj.getRelid());
			String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);
           System.out.println(utilities.getCurrentClassAndMethodName() + ". request JSON is: " + requestJson);

			
			System.out.println(" SESSION ID : " + reqObj.getSessionId() + " "
					+ utilities.getCurrentClassAndMethodName() + ". request JSON is: " + requestJson);

			serviceProps.setProperty("requestBody", requestJson);
			reqObj.setRequestBody(requestJson);

			//// Token Generation
			String token = utilities.callOAuth2Token(serviceProps);

			/// Token Validation
			if (token == null || "".equals(token.trim())) {

               System.out.println(". The token is null/empty");

				throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
						GlobalConstants.FAILURE + ". The token is null/empty");
			}
			serviceProps.setProperty("token", token);
			reqObj.setToken(token);

			serviceProps.setProperty("httpMethod", HttpMethod.GET);
			inputparams.put("serviceProperties", serviceProps);

			/// Header Parameter
			/***
			 * 
			 * form Http Headers Parameter and pass this parameter to helper class. 
			 * then the helper class call endpoint api with header parameter and return response
			 * 
			 * (for helper class reuse purpose)
			 * ***/
			
			Map<String, String> httpHeaderParams = new HashMap<>();
			httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json");
			httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
			httpHeaderParams.put("request-country", serviceProps.getProperty("requestCountry").toUpperCase());
			httpHeaderParams.put("message-sender", serviceProps.getProperty("message-sender"));
			httpHeaderParams.put("source-system", serviceProps.getProperty("source-system"));
			httpHeaderParams.put("countrycode", serviceProps.getProperty("countrycode"));

			inputparams.put("httpHeaderParams", httpHeaderParams);

			///Call EndPoint API
			responseMessage = hostHelper.invokeHttpsWebservice(inputparams);
		

		if (responseMessage != null && responseMessage.getEntity() != null
				&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

			System.out.println(utilities.getCurrentClassAndMethodName() + ". Response : "
					+ responseMessage.getEntity().toString());

			System.out.println(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
							+ ". Response : " + responseMessage.getEntity().toString());

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

			/***
			 * check status code from endPoint API response.
			 * 
			 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
			 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
			 * 
			 * ****/
			
			if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
				CustomerContactDetails_Res dataObjects = objectMapper
						.readValue(responseMessage.getEntity().toString(), CustomerContactDetails_Res.class);
				if (dataObjects != null) {
					resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
					resObj.setErrormessage(GlobalConstants.SUCCESS);
					resObj.setResponse(dataObjects.getResponse());
				} else {
					resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
				}
			} else {
				CustomerContactDetails_Res dataObjects = objectMapper
						.readValue(responseMessage.getEntity().toString(), CustomerContactDetails_Res.class);
				if (dataObjects!= null) {
					resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects);
				}
				 else {
					resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
				}
			}
		} else {
			System.out.println(utilities.getCurrentClassAndMethodName() + ". Empty Response");
			System.out.println(" SESSION ID : " + reqObj.getSessionId() + " "
					+ utilities.getCurrentClassAndMethodName() + ". Empty Response");



			throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
					"Response is Null, Setting Failure code");
		}

	
	} catch (SocketException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETEXCEPTION_700007);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : " + e.getMessage());
	} catch (SocketTimeoutException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : " + e.getMessage());
	} catch (JsonParseException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : " + e.getMessage());
	} catch (IOException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : " + e.getMessage());
	} catch (NullPointerException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : " + e.getMessage());
	} catch (ParseException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : " + e.getMessage());
	} catch (URISyntaxException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : " + e.getMessage());

	} catch (CustomException e) {
		resObj.setErrorcode(e.getErrorCode());
		resObj.setErrormessage(e.getErrorMsg());
	} catch (Exception e) {
		resObj.setErrorcode(GlobalConstants.FAILURECODE);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : " + e.getMessage());

	}
	return resObj;	
	}
	public com.api.ivr.model.validateTpin.ValidateTPIN_Res validateTPIN(Map<String, Object> inputparams, ValidateTPIN_Req reqObj) {
		com.api.ivr.model.validateTpin.ValidateTPIN_Res resObj = new com.api.ivr.model.validateTpin.ValidateTPIN_Res();
		Properties serviceProps = (Properties) inputparams.get("serviceProperties");
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String requestInitiatedTimestamp = dateTimeFormat.format(new Date());
		try {
			Response responseMessage=null;
			AMRIvrIntraction amivr = new AMRIvrIntraction();
			String xmlString = serviceProps.getProperty("requestPayload");

			/***
			 * check whether the AMIVR relId already in DB
			 * Get AMIVR Details. It helps to send encPassword to endpoint. and maintain the 
			 * maximum tries in DB based on the error
			 * 
			 * ***/
			String blockCodeMax_Tries = serviceProps.getProperty("blockCodeMax_Tries");
			amivr.setRelID(reqObj.getUserid());
			amivr = db.getAMIvrDetails(amivr);
			if (amivr == null || amivr.getCheck() == null || amivr.getCheck().equalsIgnoreCase("")
					|| !amivr.getCheck().equalsIgnoreCase("DATAFOUND")) {
				resObj.setStartTime(requestInitiatedTimestamp);
				resObj.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				resObj.setErrormessage("Customer Data not found in DB");

				System.out.println(utilities.getCurrentClassAndMethodName() + ". Customer Data not found in DB");

				return resObj;
			}
			
			if(Integer.parseInt(amivr.getTries()) >= Integer.parseInt(blockCodeMax_Tries)) {
				resObj.setStartTime(requestInitiatedTimestamp);
				resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_TPIN_BLOCKED_700041);
				resObj.setErrormessage("TPIN is blocked - Reached Max attempts");
				

				

				return resObj;
			}
			
			String encPassword = amivr.getHashedVal();

			Map<String, Object> inputElemets = new HashMap<>();
			inputElemets.put("userId", reqObj.getUserid());
			inputElemets.put("sha256Password", reqObj.getPassword());
			inputElemets.put("encPassword",
					encPassword != null && !encPassword.equalsIgnoreCase("") ? encPassword : "");
			inputElemets.put("key_alias",
					serviceProps.getProperty("key_alias") != null
							&& !serviceProps.getProperty("key_alias").equalsIgnoreCase("")
									? serviceProps.getProperty("key_alias")
									: "");
			inputElemets.put("sessionID", reqObj.getSessionId());
			inputElemets.put("usecaseID", reqObj.getTrackingId());

			/***
			 * Load parameter to payload request
			 * ***/
			String requestXML = utilities.injectDataIntoRequestString(xmlString, inputElemets);

			

			serviceProps.setProperty("requestBody", requestXML);
			reqObj.setRequestBody(requestXML);

			//// Token Generation
			String token = utilities.callBasicAuthToken("", "");

			/// Token Validation
			if (token == null || "".equals(token.trim())) {
				

				throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
						GlobalConstants.FAILURE + ". The token is null/empty");
			}
			serviceProps.setProperty("token", token);
			reqObj.setToken(token);

			serviceProps.setProperty("httpMethod", HttpMethod.POST);
			
			inputparams.put("serviceProperties", serviceProps);

			/// Header Parameter
			/***
			 * 
			 * form Http Headers Parameter and pass this parameter to helper class. 
			 * then the helper class call endpoint api with header parameter and return response
			 * 
			 * (for helper class reuse purpose)
			 * ***/
			
			Map<String, String> httpHeaderParams = new HashMap<>();
//			httpHeaderParams.put(HttpHeaders.USER_AGENT, "Mozilla/5.0");
//			httpHeaderParams.put(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5");
//			httpHeaderParams.put(HttpHeaders.CONTENT_LENGTH, requestXML != null ? Integer.toString(requestXML.length()):"0");
			httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "text/xml; charset=utf-8");
			httpHeaderParams.put("SOAPAction", "verify");

			inputparams.put("httpHeaderParams", httpHeaderParams);

			///Call EndPoint API
			responseMessage = hostHelper.invokeHttpsWebservice(inputparams);
		

		if (responseMessage != null && responseMessage.getEntity() != null
				&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

		
			if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
				
				String responseXML = responseMessage.getEntity().toString();
				
				String verifyResponse = StringUtils.substringBetween(responseXML, "<verifyReturn xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">", "</verifyReturn>");
				if(verifyResponse==null || StringUtils.isEmpty(verifyResponse)){
					String exception= StringUtils.substringBetween(responseXML, "<faultcode>", "</faultcode>");
					exception = exception.replaceAll("soapenv:", "");
					
					resObj.setErrorcode(GlobalConstants.FAILURECODE);
					resObj.setErrormessage(GlobalConstants.FAILURE);
				}else{
					AMRIvrIntraction amivr1 = new AMRIvrIntraction();
					amivr1.setRelID(reqObj.getUserid());

					if(verifyResponse.equalsIgnoreCase("100")){
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
						resObj.setErrormessage(GlobalConstants.SUCCESS);
						if(Integer.parseInt(amivr.getTries()) < Integer.parseInt(blockCodeMax_Tries)){
							amivr1.setStatus("ACTIVE");
							amivr1.setTries("0");
							
						}
						else{
							amivr1.setTries(blockCodeMax_Tries);
							amivr1.setStatus("BLOCKED");
							
						}
					
					
					}else if(verifyResponse.equalsIgnoreCase("102")){
						String errorcode = String.format("%06d", Integer.parseInt(verifyResponse));
						resObj.setErrorcode(errorcode);
						resObj.setErrormessage("DECRYPTION FAILED");
						
					}

					resObj.setCode(verifyResponse);
				}
			} else {
				resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
				resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
			}
		} else {
			
			throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
					"Response is Null, Setting Failure code");
		}

		String endTime = dateTimeFormat.format(new Date());
		resObj.setEndTime(endTime);

		
	} catch (SocketException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETEXCEPTION_700007);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket Closed");
		
	} catch (SocketTimeoutException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket timeout Occured");
		
	} catch (JsonParseException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_JSONPARSEEXCEPTION_700009);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : JSON Parse occured");
		
	} catch (ConnectTimeoutException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_CONNECTTIMEOUTEXCEPTION_700010);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Connect TimeOut Exception occured");
		
	} catch (IOException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : IO Exception occured");
		
	} catch (NullPointerException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
		
	} catch (ParseException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_PARSEEXCEPTION_700005);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Parse Exception occured");
		
	} catch (IllegalArgumentException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Illegal Argument Exception Occured");
		
	} catch (URISyntaxException e) {
		resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : URI syntax Exception Occured");
		

	} catch (CustomException e) {
		resObj.setErrorcode(e.getErrorCode());
		resObj.setErrormessage(e.getErrorMsg());
	} catch (Exception e) {
		resObj.setErrorcode(GlobalConstants.FAILURECODE);
		resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception ");
	
	}
	return resObj;	
		
	}
}
