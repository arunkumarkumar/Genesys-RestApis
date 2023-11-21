package com.api.ivr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.RSAPublicKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component

public class Utilities {

	@Value("${propertyfilepath}")
	private String propertyfilepath;

//	@Autowired
//	ConfigController configController;

	//// Generate Tracking ID
	public String generateTrackingId() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmssMs");
		String trackingId = "IVR-" + dateFormat.format(new Date()) + "-" + UUID.randomUUID().toString(); // "IVR-19062020013055600-01a8671b-3918-4cc3-8ead-eb30c9728721";
		return trackingId;
	}

	//// Get Current Class Name and Method Name
	public String getCurrentClassAndMethodName() {
		final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		final String s = e.getClassName();
		return s.substring(s.lastIndexOf('.') + 1, s.length()) + "." + e.getMethodName();
	}

	//// Null or empty field check
	public boolean isNullOrEmpty(String inputValue) {
		if (inputValue == null || "".equals(inputValue.trim())) {
			return true;
		} else {
			return false;
		}
	}

	//// Get and load all properties from file ( config, operations, parametric,
	//// payload )
	/*public Properties getServiceConfig(Properties properties) throws Exception {

		Properties serviceProperties = new Properties();
		serviceProperties.putAll(properties);

		String sessionId = properties.getProperty("sessionId");
		String serviceName = properties.getProperty("serviceName");
		String tpSystem = properties.getProperty("tpSystem");

		Logger sessionLogger = CustomLogger.getLogger(sessionId);

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
					sessionLogger.debug(getCurrentClassAndMethodName()
							+ " Config property value is not match with the expected format. Property is: "
							+ configPropertyStr);
				}
			} else {
				sessionLogger.debug(getCurrentClassAndMethodName() + " - " + properties.getProperty(serviceName)
						+ " is not found in config.properties.");
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			}
		} catch (Exception e) {
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			sessionLogger.debug(getCurrentClassAndMethodName()
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
				if (endPoints.length >= 2 && !isNullOrEmpty(endPoints[0])) {
					serviceProperties.setProperty("endPoint", endPoints[0].trim());
					serviceProperties.setProperty("timeOut", endPoints[1] != null ? endPoints[1].trim() : "20000");
				} else {
					serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
					sessionLogger.debug(getCurrentClassAndMethodName()
							+ " Operations.properties -> Incorrect endPoint params found for the service : "
							+ serviceName);
				}
			} else {
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
				sessionLogger.debug(getCurrentClassAndMethodName()
						+ " Operations.properties -> No endPoint URL found for the service: " + serviceName);
			}

			if (properties != null && properties.getProperty("endPoint." + tpSystem + ".tokenUrl") != null) {
				String endPointString = properties.getProperty("endPoint." + tpSystem + ".tokenUrl");

				String endPoints[] = endPointString.split(";");
				if (endPoints.length >= 2 && !isNullOrEmpty(endPoints[0])) {
					serviceProperties.setProperty("tokenURL", endPoints[0].trim());
					serviceProperties.setProperty("tokenTimeOut", endPoints[1] != null ? endPoints[1].trim() : "20000");
				}
			}
		} catch (Exception e) {
			sessionLogger.debug(getCurrentClassAndMethodName()
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
			sessionLogger.debug(getCurrentClassAndMethodName()
					+ " Exception occured while loading Parametric properties." + e.getMessage());
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		//// LOAD PAYLOAD PARAMETER
		try {

			File file = new File(propertyfilepath + tpSystem + "Payload.properties");
			Properties propPayload = new Properties();
			InputStream input = new FileInputStream(file);
			propPayload.load(input);

			if (propPayload != null && propPayload.getProperty(serviceName) != null) {
				serviceProperties.setProperty("requestPayload", propPayload.getProperty(serviceName));
			} else {
				sessionLogger.debug(getCurrentClassAndMethodName() + " No request payload found in " + tpSystem
						+ "Payload.properties");
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			}

		} catch (Exception e) {
			sessionLogger.debug(getCurrentClassAndMethodName() + " Exception occured while loading payload properties."
					+ e.getMessage());
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		return serviceProperties;
	}*/

	/// Get token related endpoint Parameter and credentials from file
	/*public Properties getTokenParams(String tpSystem, String serviceName) {
		Properties serviceProperties = new Properties();
		serviceProperties.setProperty("serviceName", serviceName);
		serviceProperties.setProperty("tpSystem", tpSystem);

		Properties properties = new Properties();

		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Operations));
		//// LOAD ENDPOINT TOKEN PARAMETER
		try {
			if (properties != null && properties.getProperty("endPoint." + tpSystem + ".tokenUrl") != null) {
				String endPointString = properties.getProperty("endPoint." + tpSystem + ".tokenUrl");

				String endPoints[] = endPointString.split(";");
				if (endPoints.length >= 2 && !isNullOrEmpty(endPoints[0].trim())) {
					String tokenUrl = endPoints[0].trim();
					String tokenTimeOut = endPoints[1].trim();
					serviceProperties.setProperty("tokenURL", tokenUrl);
					serviceProperties.setProperty("tokenTimeOut", endPoints[1] != null ? tokenTimeOut : "20000");
				}
			}
		} catch (Exception e) {
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

			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		return serviceProperties;
	}*/

	//// Parse XML Data
	public String removeXmlNamespaceAndPreamble(String xmlString) {
		return xmlString.replaceAll("(<\\?[^<]*\\?>)?", ""). /* remove preamble */
				replaceAll("xmlns.*?(\"|\').*?(\"|\')", "") /* remove xmlns declaration */
				.replaceAll("(<)(\\w+:)(.*?>)", "$1$3") /* remove opening tag prefix */
				.replaceAll("(</)(\\w+:)(.*?>)", "$1$3"); /* remove closing tags prefix */
	}

	public String getAttribute(String xmlString, String tagName) {
		String output = xmlString;
		if (xmlString.indexOf("<" + tagName + ">") >= 0 && xmlString
				.indexOf("</" + tagName + ">") > xmlString.indexOf("<" + tagName + ">") + tagName.length()) {
			output = xmlString.substring(xmlString.indexOf("<" + tagName + ">"),
					xmlString.indexOf("</" + tagName + ">") + tagName.length() + 3);
		}
		return output;
	}

	/// Xml to Json conversion
	public String convertXMLintoJsonString(String xmlString){
		String jsonPrettyPrintString = "";
		try {
			JSONObject jsonObj = XML.toJSONObject(xmlString);
			jsonPrettyPrintString = jsonObj.toString();
			return jsonPrettyPrintString;
		}catch(Exception e) {
			return jsonPrettyPrintString;
		}
		
	}

	/// Load parameter to payload and retrieve request
	public String injectDataIntoRequestString(String requestString, Map<String, Object> inputElemets) {

		if (inputElemets != null) {
			for (Map.Entry<String, Object> entry : inputElemets.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();

				String inputElement = "[{%" + key + "%}]";
				String tempStr = requestString.replace(inputElement, value);
				requestString = tempStr;
			}
		}
		return requestString;
	}

	//// Oauth Token Generator
	public String callOAuth2Token(Properties serviceProps) throws Exception {
		String token = "";

		JSONObject obj = null;
		try {

			/***
			 * @param grant_type mandatory,not null
			 * @param client_id mandatory,not null
			 * @param client_secret mandatory,not null
			 * @param scope mandatory,not null
			 * @param token_URL mandatory,not null
			 * @param keystore mandatory,not null
			 * @param keystoreType mandatory,not null
			 * @param keystorePwd mandatory,not null
			 * @param truststore mandatory,not null
			 * @param truststoreType mandatory,not null
			 * @param truststoreType mandatory,not null
			 * 
			 * all credentials stored in parametric.properties file
			 * call token api with credential and retrieve oauth2 token
			 * 
			 ***/
		
			String tpSystem = serviceProps.getProperty("tpSystem");
			String serviceName = serviceProps.getProperty("serviceName");
			/// TOKEN CREDENTIALS
			String grantType = serviceProps.getProperty(tpSystem + ".grant_type");
			String clientId = serviceProps.getProperty(tpSystem + ".client_id");
			String clientSecret = serviceProps.getProperty(tpSystem + ".client_secret");
			String scope = serviceProps.getProperty(tpSystem + "." + serviceName + ".scope");

			String tokenUrl = serviceProps.getProperty("tokenURL");
			String tokenUrlTimeout = serviceProps.getProperty("tokenTimeOut");

			String keyStoreFile = serviceProps.getProperty(tpSystem + ".keyStore");
			String keyStoreType = serviceProps.getProperty(tpSystem + ".keyStoreType");
			String keyStorePwd = serviceProps.getProperty(tpSystem + ".keyStorePwd");

			String trustStoreFile = serviceProps.getProperty(tpSystem + ".trustStore");
			String trustStoreType = serviceProps.getProperty(tpSystem + ".trustStoreType");
			String trustStorePwd = serviceProps.getProperty(tpSystem + ".trustStorePwd");

			/// HTTP CLIENT CONFIG
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			/// SSL CONTEXT SETUP
			httpClient = (CloseableHttpClient) sslContextSetup(keyStoreFile, keyStoreType, keyStorePwd, trustStoreFile,
					trustStoreType, trustStorePwd);

			/// TIMEOUT CONFIGURATION
			RequestConfig timoutConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(Integer.parseInt(tokenUrlTimeout))
					.setConnectTimeout(Integer.parseInt(tokenUrlTimeout))
					.setSocketTimeout(Integer.parseInt(tokenUrlTimeout)).build();

			HttpPost httpPost = new HttpPost(tokenUrl);
			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
			httpPost.setEntity(new StringEntity("grant_type=" + grantType + "&client_id=" + clientId + "&client_secret="
					+ clientSecret + "&scope=" + scope));
			httpPost.setConfig(timoutConfig);

			HttpResponse response = httpClient.execute(httpPost);

			String content = EntityUtils.toString(response.getEntity());
			obj = new JSONObject(content);

			/// GENERATED TOKEN
			if (obj != null && obj.has("access_token")) {
				token = obj.getString("access_token");

				if (token != null && !"".equalsIgnoreCase(token.trim())) {
					token = "Bearer " + token;
				}
			} else {
				throw new Exception("The token response is : " + obj.toString());
			}
			return token;
		} catch (NullPointerException e) {
			throw new com.api.ivr.exception.CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
					"Exception Occured : Could not load token credentials from properties or not found "
							+ e.getMessage());
		} catch (Exception e) {
			throw new com.api.ivr.exception.CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
					"Exception Occured : OAuth Token Creation failed - " + e.getMessage());
		}
	}

	//// SSL context setup
	public HttpClient sslContextSetup(String keyStoreFile, String keyStoreType, String keyStorePwd,
			String trustStoreFile, String trustStoreType, String trustStorePwd)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException,
			FileNotFoundException, IOException, UnrecoverableKeyException {

		KeyStore clientStore = KeyStore.getInstance(keyStoreType);
		String decodePassword = new String(Base64.getDecoder().decode(keyStorePwd.getBytes()));
		clientStore.load(new FileInputStream(new File(propertyfilepath + keyStoreFile)), decodePassword.toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(clientStore, decodePassword.toCharArray());
		KeyManager[] kms = kmf.getKeyManagers();

		TrustManager[] trustAllCerts = null;

		if (trustStoreFile != null && !"".equalsIgnoreCase(trustStoreFile.trim())) {
			KeyStore trustStore = KeyStore.getInstance(trustStoreType);
			String decodeTrustPasswd = new String(Base64.getDecoder().decode(trustStorePwd.getBytes()));
			trustStore.load(new FileInputStream(new File(propertyfilepath + trustStoreFile)),
					decodeTrustPasswd.toCharArray());

			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);
			trustAllCerts = tmf.getTrustManagers();
		} else {
			trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}
			} };
		}

		final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

		sslContext.init(kms, trustAllCerts, new SecureRandom());
		SSLContext.setDefault(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.setSSLContext(sslContext).build();
		return httpClient;
	}

	/// BAsic Token Generator
	public String callBasicAuthToken(String userName, String password) throws Exception {
		String token = "";

		try {
			String valueToEncode = userName + ":" + password;
			token = "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
		} catch (NullPointerException e) {
			throw new com.api.ivr.exception.CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
					"Exception Occured : Could not load token credentials from properties or not found "
							+ e.getMessage());
		} catch (Exception e) {
			throw new com.api.ivr.exception.CustomException(com.api.ivr.global.constants.GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
					"Exception Occured : Basic Token Creation failed - " + e.getMessage());
		}

		return token;
	}

	//// Trust all certificate
	/*public HttpClient certificateExclude() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
						return true;
					}
				}).build()).build();

		return httpClient;
	}*/
	
	public HttpClient certificateExclude() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		TrustManager[] trustAllCerts = null;
		
		trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

		sslContext.init(null, trustAllCerts, new SecureRandom());
		SSLContext.setDefault(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.setSSLContext(sslContext).build();
		return httpClient;
	}



	//// Generate Transaction Sequence Number(Euronet)
	public String generateSequenceNumber() {
		String sequenceNumber = "000000000000000";
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMddHHmmssSSS");
			sequenceNumber = dateTimeFormat.format(new Date());
//			Random r = new Random(System.currentTimeMillis());
//			sequenceNumber = sequenceNumber + ((1 + r.nextInt(2)) * 10 + r.nextInt(10));
			SecureRandom rand = new SecureRandom();
	        int rand_int1 = rand.nextInt(100);
	        sequenceNumber = sequenceNumber + String.format("%02d", rand_int1);
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return sequenceNumber;
	}

	/// RSA Data encryption
	public String encryptDataRSA(String exponent, String modulus, String encryptedBlock, String data,
			String sessionID) {
		String encData = "";
		String plainString = data + "_-_" + encryptedBlock;
		PublicKey publicKey = null;
		BigInteger keyInt = new BigInteger(modulus, 16);
		BigInteger exponentInt = new BigInteger(exponent, 16);

		try {
			System.out.println("Encryption logic entered");
			RSAPublicKeySpec keySpeck = new RSAPublicKeySpec(keyInt, exponentInt);
			System.out.println("Key factory get instance");
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpeck);
			System.out.println("RSA Padding");
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encryptedData = cipher.doFinal(plainString.getBytes("UTF-8"));
			System.out.println("Encryption completed");
			encData = new String(Base64.getEncoder().encode(encryptedData));
		} catch (Exception e) {
			System.out.println("Exception Occured - " + e.getMessage());
		}

		return encData;
	}

	/// Generate Card PIN Data(PIN Generation/Reset/Validate)
	
	/// Generate Card Info Data(PIN Generation/Reset/validate)
	

	/// Generate Transaction Reference Number(PIN Generation)
	public String generateTxnRefNo() {
		SimpleDateFormat dateFromat = new SimpleDateFormat("MMddHHmmss");
		String txnRefNo = dateFromat.format(new Date());

//		Random r = new Random();
//		txnRefNo = txnRefNo + String.format("%05d", r.nextInt(99999));
		
		SecureRandom rand = new SecureRandom();
        int rand_int1 = rand.nextInt(100000);
        txnRefNo = txnRefNo + String.format("%05d", rand_int1);
		return txnRefNo;
	}

	
	

	

	

	
	
	public String getErrorCodeInFormat(String errorCode) {
		if (errorCode.length() < 6) {
			errorCode = "000000" + errorCode;
			errorCode = errorCode.substring(errorCode.length() - 6);
		}

		return errorCode;
	}
	
	public SimpleDateFormat getDateFormat(String dateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/India"));
		return simpleDateFormat;

	}
	
	public String getOTPExpiryMsgTemplate(String msgTemplate) {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/India"));
		String myTime = simpleDateFormat.format(new Date());

		Date d = null;
		try {
			d = simpleDateFormat.parse(myTime);
		} catch (ParseException e) {
			System.out.println("Exception Occured while parsing otp Expiry time");
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.SECOND, 100);
		String expiredTime = simpleDateFormat.format(cal.getTime());
		
		msgTemplate = msgTemplate.replace("[{%otpExpireTime%}]", expiredTime);
		return msgTemplate;
	}
	
}
