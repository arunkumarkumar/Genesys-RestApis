package com.api.ivr.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ivr.db.entity.AMRIvrIntraction;
import com.api.ivr.db.entity.BinMaster_Res;
import com.api.ivr.db.entity.BusinessHrsCheck_Req;
import com.api.ivr.db.entity.BusinessHrsCheck_Res;
import com.api.ivr.db.service.impl.DBServiceImpl;
import com.api.ivr.endpoints.EndPoint_ApiServices;
import com.api.ivr.exception.CustomException;
import com.api.ivr.global.constants.GlobalConstants;
import com.api.ivr.model.BinMaster_Req;
import com.api.ivr.model.CustomerIdentficationAccNum_Req;
import com.api.ivr.model.CustomerIdentficationAccNum_Res;
import com.api.ivr.model.CustomerIdentificationResponseData;
import com.api.ivr.model.GenerateOTP_Req;
import com.api.ivr.model.GenerateOTP_Res;
import com.api.ivr.model.GenerateOtpResponseData;
import com.api.ivr.model.IdentifyCustomerRMNRespose;
import com.api.ivr.model.IdentifyCustomer_Req;
import com.api.ivr.model.IdentifyCustomer_Res;
import com.api.ivr.model.ValidateOTPResponse_Data;
import com.api.ivr.model.ValidateOTP_Req;
import com.api.ivr.model.ValidateOTP_Res;
import com.api.ivr.model.AMIvrIntraction.AMIvrIntraction_Res;
import com.api.ivr.model.CallLog.CallLog_Res;
import com.api.ivr.model.customercontactdetails.CustomerContactDetails_Req;
import com.api.ivr.model.customercontactdetails.CustomerContactDetails_Res;
import com.api.ivr.model.customercontactdetails.CustomerContactDetails_ResponseData;
import com.api.ivr.model.customeridentifycardnum.CustomerIdenificationCardNum_Req;
import com.api.ivr.model.customeridentifycardnum.CustomerIdentificationCardNum_Res;
import com.api.ivr.model.customeridentifycardnum.CustomerIdentificationCardNum_ResponseData;
import com.api.ivr.model.customeridentifydebitcardnum.CustIdentificationDebtCardResponseData;
import com.api.ivr.model.customeridentifydebitcardnum.CustomerIdentifyDebitcardnum_Req;
import com.api.ivr.model.customeridentifydebitcardnum.CustomerIdentifyDebitcardnum_Res;
import com.api.ivr.model.preferredlanguage.PreferredLanguage_Req;
import com.api.ivr.model.preferredlanguage.PreferredLanguage_Res;
import com.api.ivr.model.setpreferredlanguage.SetPreferredLangauage_Req;
import com.api.ivr.model.setpreferredlanguage.SetPreferredLanguage_Res;
import com.api.ivr.model.validateTpin.ValidateTPIN_Req;
import com.api.ivr.model.validateTpin.ValidateTPIN_Res;
import com.api.ivr.service.GetPropertiesValues;
import com.api.ivr.util.ConfigureFiles;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class RestApis_Modules {
	@Autowired
	private Environment env;

	@Autowired
	private DBServiceImpl db;

	@Autowired
	private GetPropertiesValues property;

	@Autowired
	private EndPoint_ApiServices api;

	Logger LOGGER = LogManager.getLogger(RestApis_Modules.class);

	@PostMapping(value = "/customerrmn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public IdentifyCustomer_Res getCustomerRMN(@RequestBody IdentifyCustomer_Req req) {
		LOGGER.info("API FOR CUSTOMER IS RMN OR NRMN IS STARTED");
		IdentifyCustomer_Res response = new IdentifyCustomer_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			LOGGER.info("CUSTOMERRMN API REQUEST : " + req.toString());
			IdentifyCustomerRMNRespose dummyResponse = null;
			if (req != null & req.getMobile() != null && req.getSessionid() != null & !"".equals(req.getMobile())) {
				Map<String, Object> inputparams = property.getCustomerRMN(req);
				IdentifyCustomer_Req reqObj = (IdentifyCustomer_Req) inputparams.get("reqObj");
				System.out.println(inputparams + "Property Values");
				if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {
					String filePath = env.getProperty("IdentifyCustomerRMN") + "/" + req.getMobile() + ".json";
					String responseString = "";
					try {
						responseString = new String(Files.readAllBytes(Paths.get(filePath)));
					} catch (Exception e) {
						System.out.println(e.getMessage() + "-------------------------------------------");
						response.setErrorcode(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013);
						response.setErrormessage("API RESPONSE FILE NOT FOUND IN THE LOCATION");
						LOGGER.error("API RESPONSE FILE NOT FOUND IN THE LOCATION" + e.getStackTrace());
						return response;
					}
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

					dummyResponse = objectMapper.readValue(responseString, IdentifyCustomerRMNRespose.class);
				} else {
					System.out.println("Calling Original Api Location");
					IdentifyCustomer_Res responses = api.IdentifyCustomerRMN(inputparams, reqObj);
					return responses;
				}
				if (dummyResponse != null & dummyResponse.getData() != null) {
					response.setErrorcode(GlobalConstants.SUCCESSCODE);
					response.setErrormessage(GlobalConstants.SUCCESS);
					response.setResponse(dummyResponse);
					if (dummyResponse.getData().getAttributes().getResponse().getProfile().size() == 1) {
						response.setRMNorNRMN("Mobile Number Identified as RMN Customer");
						LOGGER.info("Mobile Number Identified as RMN Customer");
					} else {
						response.setRMNorNRMN("Mobile Number Identified as NRMN Customer");
						LOGGER.info("Mobile Number Identified as NRMN Customer");
					}
					response.setEndTime(starttime);
					LOGGER.info("CUSTOMERRMN API RESPONSE : " + response);
					return response;
				} else {
					response.setErrorcode(GlobalConstants.FAILURECODE);
					response.setErrormessage(GlobalConstants.FAILURE + "Response was Null");
					response.setResponse(null);
					response.setEndTime(starttime);
					LOGGER.info("CUSTOMERRMN API FAILURE RESPONSE : " + response);
					return response;
				}

			} else if ("".equals(req.getMobile())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_MOBILENUMBEREMPTY);
				response.setErrormessage("Mobile Number is Empty");
				LOGGER.info("CUSTOMERRMN API REQUEST MOBILE NUMBER IS EMPTY : " + req);
				response.setEndTime(starttime);
				return response;
			} else if (req.getMobile() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_MOBILENUMBER_NULL);
				response.setErrormessage("Mobile Number is Null");
				response.setResponse(null);
				response.setEndTime(starttime);
				LOGGER.info("CUSTOMERRMN API REQUEST MOBILE NUMBER IS NULL : " + req);
				return response;
			} else if (req.getSessionid() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_SESSIONID_NULL);
				response.setErrormessage("Sessionid is Null");
				response.setResponse(null);
				response.setEndTime(starttime);
				LOGGER.info("CUSTOMERRMN API REQUEST SESSIONID IS NULL : " + req);
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(starttime);
				LOGGER.info("CUSTOMERRMN API FAILURE : " + req);
				return response;
			}
		} catch (IOException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error("IO EXCEPTION ON CUSTOMERRMN: " + e.getStackTrace());
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error("NULLPOINTER EXCEPTION ON CUSTOMERRMN: " + e.getStackTrace());
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error("EXCEPTION : " + e.getStackTrace());
		}
		return response;

	}

	@PostMapping(value = "/generateotp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenerateOTP_Res generateOTP(@RequestBody GenerateOTP_Req req) {
		LOGGER.info("API FOR GENERATE OTP IS STARTED");
		GenerateOTP_Res response = new GenerateOTP_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			LOGGER.info("GENERATEOTP API REQUEST : " + req.toString());
			GenerateOtpResponseData dummyResponse = null;
			if (req != null & req.getMobileNumber() != null
					&& req.getRelId() != null & !"".equals(req.getMobileNumber()) & !"".equals(req.getRelId())) {
				Map<String, Object> inputparams = property.generateOTP(req);
				GenerateOTP_Req reqObj = (GenerateOTP_Req) inputparams.get("reqObj");
				if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {
					String filePath = env.getProperty("GenerateOTP") + "/" + req.getRelId() + ".json";

					String responseString = "";
					try {
						responseString = new String(Files.readAllBytes(Paths.get(filePath)));
					} catch (Exception e) {
						System.out.println(e.getMessage() + "-------------------------------------------");
						response.setErrorcode(GlobalConstants.ERRORCODE_FILE_NOTFOUND);
						response.setErrormessage("API RESPONSE FILE NOT FOUND IN THE LOCATION");
						LOGGER.error("API RESPONSE FILE NOT FOUND IN THE LOCATION OF GENERATEOTP" + e.getStackTrace());
						return response;

					}
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

					dummyResponse = objectMapper.readValue(responseString, GenerateOtpResponseData.class);
				} else {
                    GenerateOTP_Res res=api.generateOTP(inputparams,reqObj);
                    return res;
				}
				if (dummyResponse != null & dummyResponse.getCode() != null) {
					if ("100".equals(dummyResponse.getCode())) {
						response.setErrorcode(GlobalConstants.SUCCESSCODE);
						response.setErrormessage(GlobalConstants.SUCCESS);
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						LOGGER.info("GENERATE API RESPONSE : " + dummyResponse.toString());
						return response;
					} else {
						response.setErrorcode(GlobalConstants.ERRORCODE_OTPBLOCKED);
						response.setErrormessage(GlobalConstants.FAILURE + "OTP Blocked");
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						LOGGER.info("GENERATE API FAILUERE RESPONSE : " + dummyResponse.toString());
						return response;
					}
				} else {
					response.setErrorcode(GlobalConstants.FAILURECODE);
					response.setErrormessage(GlobalConstants.FAILURE + "Response was Null");
					response.setResponse(dummyResponse);
					response.setEndTime(starttime);
					LOGGER.info("GENERATE API FAILUERE RESPONSE WAS NULL : " + dummyResponse.toString());
					return response;
				}
			} else if ("".equals(req.getMobileNumber())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_MOBILENUMBEREMPTY);
				response.setErrormessage("Mobile Number is Empty");
				response.setEndTime(starttime);
				LOGGER.info("MOBILE NUMBER IS EMPTY IN THE REQUEST");
				return response;
			} else if (req.getMobileNumber() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_MOBILENUMBER_NULL);
				response.setErrormessage("Mobile Number is Null");
				response.setEndTime(starttime);
				LOGGER.info("MOBILE NUMBER IS  NULL IN THE REQUEST");
				return response;
			} else if (req.getRelId() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_RELID_NULL);
				response.setErrormessage("Relid is Null");
				response.setEndTime(starttime);
				LOGGER.info("RELID IS  NULL IN THE REQUEST");
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(starttime);
				LOGGER.info("GENERATE OTP API GET FAILURE :" + "REQUEST :" + req.toString() + "RESPONSE :"
						+ response.toString());
				return response;
			}
		} catch (IOException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error("IO EXCEPTION ON GENERATE OTP: " + e.getStackTrace());
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error("NULLPOINTER EXCEPTION ON GENERATE OTP: " + e.getStackTrace());
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error("EXCEPTION : " + e.getStackTrace());
		}
		return response;

	}

	@PostMapping(value = "/validateotp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ValidateOTP_Res validateOTP(@RequestBody ValidateOTP_Req req) {
		LOGGER.info("API FOR VALIDATE OTP IS STARTED");
		ValidateOTP_Res response = new ValidateOTP_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			LOGGER.info("VALIDATE OTP API REQUEST : " + req.toString());
			ValidateOTPResponse_Data dummyResponse =null;
			if (req != null & req.getOtp() != null
					&& req.getOtpSn() != null & !"".equals(req.getOtp()) & !"".equals(req.getRelID())) {
				Map<String, Object> inputparams = property.validateOTP(req);
				ValidateOTP_Req reqObj = (ValidateOTP_Req) inputparams.get("reqObj");
				if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {
				String filePath = env.getProperty("ValidateOTP") + "/" + req.getRelID() + ".json";

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					System.out.println(e.getMessage() + "-------------------------------------------");
					response.setErrorcode(GlobalConstants.ERRORCODE_FILE_NOTFOUND);
					response.setErrormessage("API RESPONSE FILE NOT FOUND IN THE LOCATION");
					LOGGER.error("API RESPONSE FILE NOT FOUND IN THE LOCATION OF GENERATEOTP" + e.getStackTrace());
					return response;

				}
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

				 dummyResponse = objectMapper.readValue(responseString,
						ValidateOTPResponse_Data.class);
				}else {
				ValidateOTP_Res res=api.validateOTP(inputparams,reqObj);
				return res;
					
				}
				if (dummyResponse != null) {
					if ("100".equals(dummyResponse.getStatusCode())) {
						response.setErrorcode(GlobalConstants.SUCCESSCODE);
						response.setErrormessage(GlobalConstants.SUCCESS);
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						LOGGER.info("VALIDATE API SUCCESS RESPONSE : " + dummyResponse.toString());
						return response;
					} else if ("308".equalsIgnoreCase(dummyResponse.getStatusCode())) {
						response.setErrorcode("001308");
						response.setErrormessage("INVALID OTP VALIDATION");
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						LOGGER.info("VALIDATE API INVALID OTP VALIDATION : " + dummyResponse.toString());
						return response;
					} else if ("368".equalsIgnoreCase(dummyResponse.getStatusCode())) {
						response.setErrorcode("001368");
						response.setErrormessage("OTP BLOCKED");
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						LOGGER.info("VALIDATE API OTP BLOCKED RESPONSE : " + dummyResponse.toString());
						return response;
					} else if ("307".equalsIgnoreCase(dummyResponse.getStatusCode())) {
						response.setErrorcode("001307");
						response.setErrormessage("OTP Expired");
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						LOGGER.info("VALIDATE API OTP Expired RESPONSE : " + dummyResponse.toString());
						return response;
					} else if ("330".equalsIgnoreCase(dummyResponse.getStatusCode())) {
						response.setErrorcode("001330");
						response.setErrormessage("Invalid OTP Token");
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						LOGGER.info("VALIDATE API Invalid OTP Token RESPONSE : " + dummyResponse.toString());
						return response;
					} else {
						response.setErrorcode(GlobalConstants.FAILURECODE);
						response.setErrormessage(GlobalConstants.FAILURE);
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						LOGGER.info("VALIDATE API FAILURE RESPONSE : " + dummyResponse.toString());
						return response;
					}
				} else {
					response.setErrorcode(GlobalConstants.FAILURECODE);
					response.setErrormessage(GlobalConstants.FAILURE + "Response was Null");
					response.setResponse(dummyResponse);
					response.setEndTime(starttime);
					return response;
				}
			} else if ("".equals(req.getOtp())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_OTPINPUT_EMPTY);
				response.setErrormessage("User entered Otp is Empty");
				response.setEndTime(starttime);
				LOGGER.info("User entered Otp is Empty :" + req.toString());
				return response;
			} else if (req.getOtp() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_OTPINPUT_NULL);
				response.setErrormessage("Otp input is null");
				response.setResponse(null);
				response.setEndTime(starttime);
				LOGGER.info("User entered Otp input is null :" + req.toString());
				return response;
			} else if (req.getRelID() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_RELID_NULL);
				response.setErrormessage("Relid is Null");
				response.setResponse(null);
				response.setEndTime(starttime);
				LOGGER.info("User entered Relid is Null :" + req.toString());
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(starttime);
				return response;
			}
		} catch (IOException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error("IO EXCEPTION ON VALIDATE OTP: " + e.getStackTrace());
			return response;
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error("NULLPOINTER EXCEPTION ON VALIDATE OTP: " + e.getStackTrace());
			return response;
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error("EXCEPTION ON VALIDATE OTP: " + e.getStackTrace());
			return response;
		}

	}

	@PostMapping(value = "/accountnumidentify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerIdentficationAccNum_Res accountnumidentification(@RequestBody CustomerIdentficationAccNum_Req req) {
		LOGGER.info("API FOR ACCOUNT NUMBER IDENTIFY IS STARTED");
		CustomerIdentficationAccNum_Res response = new CustomerIdentficationAccNum_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			LOGGER.info("ACCOUNT NUMBER IDENTIFY API REQUEST : " + req.toString());
			CustomerIdentificationResponseData dummyResponse=null;
			if (req != null & req.getAcctNum() != null
					&& req.getSessionid() != null & !"".equals(req.getAcctNum()) & req.getCurrency_code() != null) {
				Map<String, Object> inputparams = property.customerIdentificationAccNum(req);
				CustomerIdentficationAccNum_Req reqObj = (CustomerIdentficationAccNum_Req) inputparams.get("reqObj");
				if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {
				String filePath = env.getProperty("AccNumIdentification") + "/" + req.getAcctNum() + ".json";

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					System.out.println(e.getMessage() + "-------------------------------------------");
					response.setErrorcode(GlobalConstants.ERRORCODE_FILE_NOTFOUND);
					response.setErrormessage("API RESPONSE FILE NOT FOUND IN THE LOCATION");
					LOGGER.error("API RESPONSE FILE NOT FOUND IN THE LOCATION OF GENERATEOTP" + e.getStackTrace());
					return response;
				}
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

			       dummyResponse = objectMapper.readValue(responseString,
						CustomerIdentificationResponseData.class);
				}else {
			CustomerIdentficationAccNum_Res res	=api.customerIdentificationAccNum(inputparams,reqObj);
			return res;		
				}
				if (dummyResponse != null & dummyResponse.getData() != null) {
					if (dummyResponse.getData().get(0).getAttributes().getCasaProfile() != null
							& !"".equals(dummyResponse.getData().get(0).getAttributes().getCasaProfile().toString())) {
						response.setErrorcode(GlobalConstants.SUCCESSCODE);
						response.setErrormessage(GlobalConstants.SUCCESS);
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						LOGGER.info("ACCOUNT NUMBER IDENTIFY API SUCCESS RESPONSE : " + dummyResponse.toString());
						return response;
					} else if (dummyResponse.getData().get(0).getAttributes().getCasaProfile().toString().isEmpty()) {
						response.setErrorcode(GlobalConstants.ERRORCODE_PROFILE_NOTFOUND);
						response.setErrormessage("CUSTOMER PROFILE NOT FOUND");
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						LOGGER.info("ACCOUNT NUMBER IDENTIFY API CUSTOMER PROFILE NOT FOUND RESPONSE : "
								+ dummyResponse.toString());
						return response;
					} else {
						response.setErrorcode(GlobalConstants.FAILURECODE);
						response.setErrormessage(GlobalConstants.FAILURE);
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						LOGGER.info("ACCOUNT NUMBER IDENTIFY API FAILURE RESPONSE : " + dummyResponse.toString());
						return response;
					}
				} else {
					response.setErrorcode(GlobalConstants.FAILURECODE);
					response.setErrormessage(GlobalConstants.FAILURE + "Response was Null");
					response.setResponse(dummyResponse);
					response.setEndTime(starttime);
					LOGGER.info("ACCOUNT NUMBER IDENTIFY API RESPONSE NULL : " + dummyResponse.toString());
					return response;
				}

			} else if ("".equals(req.getAcctNum())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_ACCNUM_EMPTY);
				response.setErrormessage("Account Number is Empty");
				response.setEndTime(starttime);
				return response;
			} else if ("".equals(req.getCurrency_code()) || req.getCurrency_code() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_ACCNUM_EMPTY);
				response.setErrormessage("Currency code Empty / Null");
				response.setEndTime(starttime);
				LOGGER.info("Currency code Empty / Null :" + req.toString());
				return response;
			} else if (req.getAcctNum() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_ACCNUM_NULL);
				response.setErrormessage("Account Number is Null");
				response.setResponse(null);
				response.setEndTime(starttime);
				LOGGER.info("Account Number is Null:" + req.toString());
				return response;
			} else if (req.getSessionid() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_SESSIONID_NULL);
				response.setErrormessage("Session Id is Null");
				response.setResponse(null);
				response.setEndTime(starttime);
				LOGGER.info("Session Id is Null:" + req.toString());
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(starttime);
				return response;
			}
		} catch (IOException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error("IO EXCEPTION ON ACCOUNT NUMBER IDENTIFY API: " + e.getLocalizedMessage());
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error("NULLPOINTER EXCEPTION ON ACCOUNT NUMBER IDENTIFY API: " + e.getStackTrace());
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
			LOGGER.error(" EXCEPTION ON ACCOUNT NUMBER IDENTIFY API: " + e.getStackTrace());
		}
		return response;

	}

	@PostMapping(value = "/customeridentifycardnum", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerIdentificationCardNum_Res customerIdentificationCardNum(
			@RequestBody CustomerIdenificationCardNum_Req req) {
		CustomerIdentificationCardNum_Res response = new CustomerIdentificationCardNum_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			CustomerIdentificationCardNum_ResponseData dummyResponse=null;
			if (req != null & req.getCardNumber() != null
					&& req.getSessionid() != null & !"".equals(req.getCardNumber())) {
				
				Map<String, Object> inputparams = property.getIdentifyCardNum(req);
				CustomerIdenificationCardNum_Req reqObj = (CustomerIdenificationCardNum_Req) inputparams.get("reqObj");
				if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {
				String filePath = env.getProperty("CustomerCardNumIdentification") + "/" + req.getCardNumber()
						+ ".json";

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					System.out.println(e.getMessage() + "-------------------------------------------");
					response.setErrorcode(GlobalConstants.ERRORCODE_FILE_NOTFOUND);
					response.setErrormessage("API RESPONSE FILE NOT FOUND IN THE LOCATION");
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());

				}
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

				 dummyResponse = objectMapper.readValue(responseString,
						CustomerIdentificationCardNum_ResponseData.class);
				}else {
					CustomerIdentificationCardNum_Res	res=api.getidentifyCardNum(inputparams,reqObj);
					return res;
				}
				if (dummyResponse != null) {
					if (dummyResponse.getData().get(0).getAttributes().cardNum != null
							& !"".equals(dummyResponse.getData().get(0).getAttributes().cardNum)) {
						response.setErrorcode(GlobalConstants.SUCCESSCODE);
						response.setErrormessage(GlobalConstants.SUCCESS);
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						return response;
					} else if (dummyResponse.getData().get(0).getAttributes().cardNum.isEmpty()
							|| dummyResponse.getData().get(0).getAttributes().cardNum == null) {
						response.setErrorcode(GlobalConstants.ERRORCODE_CARDNUMBER_NOTFOUND);
						response.setErrormessage("CARD NUMBER NOT FOUND");
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						return response;
					} else {
						response.setErrorcode(GlobalConstants.FAILURECODE);
						response.setErrormessage(GlobalConstants.FAILURE);
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						return response;
					}
				} else {
					response.setErrorcode(GlobalConstants.FAILURECODE);
					response.setErrormessage(GlobalConstants.FAILURE + "Response was Null");
					response.setResponse(null);
					response.setEndTime(starttime);
					return response;
				}

			} else if ("".equals(req.getCardNumber())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_CARDNUMBER_EMPTY);
				response.setErrormessage("CardNumber is Empty");
				response.setEndTime(starttime);
				return response;
			} else if (req.getCardNumber() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_CARDNUMBER_NULL);
				response.setErrormessage("Card Number is Null");
				response.setResponse(null);
				response.setEndTime(starttime);
				return response;
			} else if (req.getSessionid() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_SESSIONID_NULL);
				response.setErrormessage("Session Id is Null");
				response.setResponse(null);
				response.setEndTime(starttime);
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(starttime);
				return response;
			}
		} catch (IOException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
		}
		return response;

	}

	@PostMapping(value = "/customeridentifydebitcardnum", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerIdentifyDebitcardnum_Res customerIdentificationDebtCardNum(
			@RequestBody CustomerIdentifyDebitcardnum_Req req) {
		CustomerIdentifyDebitcardnum_Res response = new CustomerIdentifyDebitcardnum_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			CustIdentificationDebtCardResponseData dummyResponse=null;
			if (req != null & req.getCardnumber() != null
					&& req.getSessionid() != null & !"".equals(req.getCardnumber())) {
				Map<String, Object> inputparams = property.getIdentifyDebitNum(req);
				CustomerIdentifyDebitcardnum_Req reqObj = (CustomerIdentifyDebitcardnum_Req) inputparams.get("reqObj");
				if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {
				String filePath = env.getProperty("CustomerIdentificationDebitCardNum") + "/" + req.getCardnumber()
						+ ".json";

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					System.out.println(e.getMessage() + "-------------------------------------------");
					response.setErrorcode(GlobalConstants.ERRORCODE_FILE_NOTFOUND);
					response.setErrormessage("API RESPONSE FILE NOT FOUND IN THE LOCATION");
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());

				}
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

				dummyResponse = objectMapper.readValue(responseString,
						CustIdentificationDebtCardResponseData.class);
				}else {
					CustomerIdentifyDebitcardnum_Res res=api.getIdentifyDebitCard(inputparams,reqObj);
					return res;
				}
				if (dummyResponse != null) {
					if (req.getCardnumber().equals(dummyResponse.getCardNumber())) {
						response.setErrorcode(GlobalConstants.SUCCESSCODE);
						response.setErrormessage(GlobalConstants.SUCCESS);
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						return response;
					} else if (!req.getCardnumber().equals(dummyResponse.getCardNumber())) {
						response.setErrorcode(GlobalConstants.ERRORCODE_INVALID_DEBITCARD);
						response.setErrormessage("DEBIT CARD NUMBER IS INVALID");
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						return response;
					} else {
						response.setErrorcode(GlobalConstants.FAILURECODE);
						response.setErrormessage(GlobalConstants.FAILURE);
						response.setResponse(dummyResponse);
						response.setEndTime(starttime);
						return response;
					}
				} else {
					response.setErrorcode(GlobalConstants.FAILURECODE);
					response.setErrormessage(GlobalConstants.FAILURE + "Response was Null");
					response.setResponse(null);
					response.setEndTime(starttime);
					return response;
				}

			} else if ("".equals(req.getCardnumber())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_CARDNUMBER_EMPTY);
				response.setErrormessage("Debit CardNumber is Empty");
				response.setEndTime(starttime);
				return response;
			} else if (req.getCardnumber() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_CARDNUMBER_NULL);
				response.setErrormessage("Debit Card Number is Null");
				response.setResponse(null);
				response.setEndTime(starttime);
				return response;
			} else if (req.getSessionid() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_SESSIONID_NULL);
				response.setErrormessage("Session Id is Null");
				response.setResponse(null);
				response.setEndTime(starttime);
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(starttime);
				return response;
			}
		} catch (IOException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
		}
		return response;

	}

	@PostMapping(value = "/customercontactdetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerContactDetails_Res customerContactDetails(@RequestBody CustomerContactDetails_Req req) {
		CustomerContactDetails_Res response = new CustomerContactDetails_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			CustomerContactDetails_ResponseData dummyResponse=null;
			if (req != null & req.getRelid() != null && req.getSessionid() != null & !"".equals(req.getRelid())) {
				
				Map<String, Object> inputparams = property.getContactDetails(req);
				CustomerContactDetails_Req reqObj = (CustomerContactDetails_Req) inputparams.get("reqObj");
				if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {
				String filePath = env.getProperty("CustomerContactDetails") + "/" + req.getRelid() + ".json";

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					System.out.println(e.getMessage() + "-------------------------------------------");
					response.setErrorcode(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013);
					response.setErrormessage("API RESPONSE FILE NOT FOUND IN THE LOCATION");
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				}
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

				dummyResponse = objectMapper.readValue(responseString,
						CustomerContactDetails_ResponseData.class);
				}else {
					CustomerContactDetails_Res res=api.getContactDetails(inputparams,reqObj);
					return res;
				}
				if (dummyResponse != null & dummyResponse.getData() != null) {
					response.setErrorcode(GlobalConstants.SUCCESSCODE);
					response.setErrormessage(GlobalConstants.SUCCESS);
					response.setResponse(dummyResponse);
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				} else {
					response.setErrorcode(GlobalConstants.FAILURECODE);
					response.setErrormessage(GlobalConstants.FAILURE + "Response was Null");
					response.setResponse(null);
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				}

			} else if ("".equals(req.getRelid())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_RELID_EMPTY);
				response.setErrormessage("Profile Id is Empty");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getRelid() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_RELID_NULL);
				response.setErrormessage("Relid is Null");
				response.setResponse(null);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getSessionid() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_SESSIONID_NULL);
				response.setErrormessage("Session Id is Null");
				response.setResponse(null);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			}
		} catch (IOException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
		}
		return response;

	}
	@PostMapping(value = "/validatetpin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ValidateTPIN_Res validateTpin(@RequestBody ValidateTPIN_Req req) {
		ValidateTPIN_Res response = new ValidateTPIN_Res();
		try {

			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			String encryptResponse=null;
			Map<String, Object> inputparams = property.validateTpin(req);
			ValidateTPIN_Req reqObj = (ValidateTPIN_Req) inputparams.get("reqObj");
			if (req != null & req.getUserid() != null && req.getSessionid() != null & !"".equals(req.getUserid())) {
				if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {
				String filePath = env.getProperty("ValidateTPIN") + "/" + req.getUserid() + ".xml";

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
					System.out.println(responseString);
				} catch (IOException e) {
					System.out.println(e.getMessage() + "-------------------------------------------");
					response.setErrorcode(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013);
					response.setErrormessage("API RESPONSE FILE NOT FOUND IN THE LOCATION");
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				}
				encryptResponse = StringUtils.substringBetween(responseString,
						"<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><ns1:verifyResponse soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:ns1=\"http://services.ca.imbank.scb\"><verifyReturn xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">",
						"</verifyReturn></ns1:verifyResponse></soapenv:Body></soapenv:Envelope>");
				System.out.println(encryptResponse);
				}else {
					ValidateTPIN_Res res=api.validateTPIN(inputparams,reqObj);
					return res;
				}
				if (encryptResponse != null) {
					response.setErrorcode(GlobalConstants.SUCCESSCODE);
					response.setErrormessage(GlobalConstants.SUCCESS);
					response.setCode(encryptResponse);
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				} else {
					response.setErrorcode(GlobalConstants.FAILURECODE);
					response.setErrormessage(GlobalConstants.FAILURE + "Xml Response was Null");
					response.setCode(encryptResponse);
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				}

			} else if ("".equals(req.getUserid())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_RELID_EMPTY);
				response.setErrormessage("Profile Id is Empty");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getUserid() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_RELID_NULL);
				response.setErrormessage("Relid is Null");
				response.setCode(null);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getSessionid() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_SESSIONID_NULL);
				response.setErrormessage("Session Id is Null");
				response.setCode(null);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			}
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
		}
		return response;

	}

	@PostMapping(value = "/preferredlanguage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PreferredLanguage_Res getPreferredLanguage(@RequestBody PreferredLanguage_Req req) {
		PreferredLanguage_Res response = new PreferredLanguage_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);

			if (req != null & req.getCli() != null && req.getSessionId() != null & !"".equals(req.getCli())) {
				PreferredLanguage_Res res = db.getPreferredLanguageBasedOnCLI(req);

				if (res != null & res.getLangCode() != null & !"".equals(res.getLangCode())) {
					response.setErrorcode(GlobalConstants.SUCCESSCODE);
					response.setErrormessage(GlobalConstants.SUCCESS);
					response.setLangCode(res.getLangCode());
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				} else if (res.getLangCode() == null) {
					response.setErrorcode(res.getErrorcode());
					response.setErrormessage(res.getErrormessage());
					response.setLangCode(res.getLangCode());
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				} else if ("".equals(res.getLangCode())) {
					response.setErrorcode(res.getErrorcode());
					response.setErrormessage(res.getErrormessage() + "- Langcode is Empty");
					response.setLangCode(res.getLangCode());
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				} else {
					response.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					response.setErrormessage(GlobalConstants.FAILURE);
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				}
			} else if ("".equals(req.getCli())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_CLIID_EMPTY);
				response.setErrormessage("Client Id  is Empty");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getCli() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_CLIID_NULL);
				response.setErrormessage("Client Id is Null");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getSessionId() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_SESSIONID_NULL);
				response.setErrormessage("Session Id is Null");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			}
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
		}
		return response;
	}

	@PostMapping(value = "/setpreferredlanguage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SetPreferredLanguage_Res setPreferredLanguage(@RequestBody SetPreferredLangauage_Req req) {
		SetPreferredLanguage_Res response = new SetPreferredLanguage_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			if (req != null & req.getCli() != null & req.getSessionId() != null & !"".equals(req.getCli())
					& req.getLangcode() != null & !"".equals(req.getLangcode()) & req.getRelid() != null) {
				String status = db.setPreferredLanguage(req);
				response.setErrorcode(GlobalConstants.SUCCESSCODE);
				response.setErrormessage(GlobalConstants.SUCCESS);
				response.setStatus(status);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getCli() == null || "".equals(req.getCli())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
				response.setErrormessage("Cli id is Null or Empty");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;

			} else if (req.getLangcode() == null || "".equals(req.getLangcode())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
				response.setErrormessage("Lang Code is Null or Empty");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getRelid() == null || "".equals(req.getRelid())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
				response.setErrormessage("Relid is Null or Empty");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getSessionId() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_SESSIONID_NULL);
				response.setErrormessage("Session Id is Null");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			}
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
		}
		return response;

	}

	@PostMapping(value = "/AmIvrInteraction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AMIvrIntraction_Res getAMIVRHost(@RequestBody AMRIvrIntraction req) {
		AMIvrIntraction_Res response = new AMIvrIntraction_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			if (req != null & !"".equals(req.getRelID())) {
				com.api.ivr.db.entity.AMRIvrIntraction res = db.getAMIvrDetails(req);
				if (res != null & !"".equals(res.getRelID()) & res.getStatus() != null & !"".equals(res.getStatus())) {
					response.setErrorcode(GlobalConstants.SUCCESSCODE);
					response.setErrormessage(GlobalConstants.SUCCESS);
					response.setResponse(res);
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				} else {
					response.setErrorcode(GlobalConstants.FAILURECODE);
					response.setErrormessage(GlobalConstants.FAILURE);
					response.setResponse(res);
					response.setEndTime(dateTimeFormat.format(new Date()));
				}
			} else if (req.getRelID() == null || "".equals(req.getRelID())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
				response.setErrormessage("Relid is Null or Empty");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			}
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);

		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
		}
		return response;

	}

	@PostMapping(value = "/binMaster", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BinMaster_Res getgetCardDetailsBasedOnBin(@RequestBody BinMaster_Req req) {
		BinMaster_Res response = new BinMaster_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());

			if (req != null & req.getSessionId() != null & !"".equals(req.getBinNumber())
					& req.getBinNumber().trim().length() >= 6) {

				response = db.getCardDetailsBasedOnBin(req);
				response.setStartTime(starttime);
				if (response != null & response.getHost() != null & !"".equals(response.getHost())
						& response.getCardType() != null & response.getCardName() != null
						& !"".equals(response.getCardName()) & !"".equals(response.getCardType())) {
					response.setErrorcode(GlobalConstants.SUCCESSCODE);
					response.setErrormessage(GlobalConstants.SUCCESS);
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				} else {
					response.setErrorcode(response.getErrorcode());
					response.setErrormessage(response.getErrormessage());
					response.setEndTime(dateTimeFormat.format(new Date()));
					return response;
				}
			} else if (req.getBinNumber().trim().length() <= 6) {
				response.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
				response.setErrormessage("BinNumber  is length more than six :" + req.getBinNumber().trim().length());
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if ("".equals(req.getBinNumber())) {
				response.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
				response.setErrormessage("BinNumber  is Empty");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getBinNumber() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
				response.setErrormessage("BinNumber is Null");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getSessionId() == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
				response.setErrormessage("Session Id is Null");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			}
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
		}
		return response;
	}

	@PostMapping(value = "/insertcalllog", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CallLog_Res insertCallLog(@RequestBody com.api.ivr.db.entity.CallLog_Req req) {
		CallLog_Res response = new CallLog_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			if (req != null & req.getSession_id() != null) {
				String status = db.insertCallLog(req);
				response.setErrorcode(GlobalConstants.SUCCESSCODE);
				response.setErrormessage(GlobalConstants.SUCCESS);
				response.setStatus(status);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getSession_id() == null || req == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
				response.setErrormessage("Session Id is Null");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			}
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
		}
		return response;

	}

	@PostMapping(value = "/businesshourcheck", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BusinessHrsCheck_Res businessHourCheck(@RequestBody BusinessHrsCheck_Req req) {
		BusinessHrsCheck_Res response = new BusinessHrsCheck_Res();
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateTimeFormat.format(new Date());
			response.setStartTime(starttime);
			if (req != null & req.getSessionId() != null) {
				response = db.checkBusinessHours(req);
				response.setErrorcode(GlobalConstants.SUCCESSCODE);
				response.setErrormessage(GlobalConstants.SUCCESS);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else if (req.getSessionId() == null || req == null) {
				response.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
				response.setErrormessage("Session Id is Null");
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			} else {
				response.setErrorcode(GlobalConstants.FAILURECODE);
				response.setErrormessage(GlobalConstants.FAILURECODE);
				response.setEndTime(dateTimeFormat.format(new Date()));
				return response;
			}
		} catch (NullPointerException e) {
			response.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			response.setErrormessage(GlobalConstants.FAILURE);
		} catch (Exception e) {
			response.setErrorcode(GlobalConstants.FAILURECODE);
			response.setErrormessage(GlobalConstants.FAILURE);
		}
		return response;

	}

	@GetMapping(value = "/dynamicmenu")
	public Map<String, Map<String, String>> dynamicMenu() {
		Map<String, Map<String, String>> menufiles = null;
		try {
			String menu = "menu.json";
			ConfigureFiles menus = new ConfigureFiles();
			menufiles = menus.loadDynamicMenu(menu, env.getProperty("propertyfilepath"));
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return menufiles;

	}

}
