package com.api.ivr.hosthelper;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author TA
 * @category This helper is used to call endpoint API and return response
 */

@Component
public class HostHelper {

	@Autowired
	com.api.ivr.util.Utilities utilities;

	public Response invokeHttpsWebservice(Map<String, Object> inputParams) throws Exception {

		Properties serviceProps = (Properties) inputParams.get("serviceProperties");

		String sessionId = serviceProps.getProperty("sessionId");
		

		String endPoint = serviceProps.getProperty("endPoint");
		String timeout = serviceProps.getProperty("timeOut");

		String token = serviceProps.getProperty("token");
		String httpMethod = serviceProps.getProperty("httpMethod");
		String requestBody = serviceProps.getProperty("requestBody");

		long start = System.currentTimeMillis();
System.out.println( " INPUT PARAMS == endPoint: " + endPoint + ", timeout: "
						+ timeout + ", token: " + token + ", Http Method: " + httpMethod + ", request: " + requestBody);
		String strResponse = null;
		HttpResponse response = null;
		Response responseMessage = null;

		try {

			/// HTTP CLIENT CONFIG
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			/// SSL CONTEXT TRUST ALL CERTIFICATE
			httpClient = (CloseableHttpClient) utilities.certificateExclude();

			/// HTTP POST/GET CALLING
			if (httpMethod.equalsIgnoreCase(HttpMethod.POST)) {
				HttpPost httpPost = getHttpPostParams(serviceProps, inputParams);
				response = httpClient.execute(httpPost);
			} else if (httpMethod.equalsIgnoreCase(HttpMethod.GET)) {
				HttpGet httpGet = getHttpGetParams(serviceProps, inputParams);
				response = httpClient.execute(httpGet);
			}

			long end = System.currentTimeMillis();
			long seconds = TimeUnit.MILLISECONDS.toSeconds(end - start);


			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {

				HttpEntity entity = response.getEntity();
				if (entity != null) {
					strResponse = EntityUtils.toString(entity);
					responseMessage = Response.status(response.getStatusLine().getStatusCode()).entity(strResponse)
							.build();
				} else {
					System.out.println("Error While Accessing HTTP Service : " +response.getStatusLine().getStatusCode());
				}
			} else {
				String msg = "";
				if (response != null && response.getEntity() != null) {
					msg = EntityUtils.toString(response.getEntity());
					responseMessage = Response.status(response.getStatusLine().getStatusCode()).entity(msg).build();
				}
			}
		} catch (SocketException e) {
			System.out.println("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ ". Socket Exception MSG IS " + e.getMessage());
			throw new SocketException(e.getMessage());
		} catch (SocketTimeoutException e) {
			System.out.println("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ ". Socket TimeOut Exception MSG IS " + e.getMessage());

			throw new SocketTimeoutException(e.getMessage());
		} catch (ConnectTimeoutException e) {
			System.out.println("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ ". Connect TimeOut Exception MSG IS " + e.getMessage());
			throw new ConnectTimeoutException(e.getMessage());
		} catch (IOException e) {
			System.out.println("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ ". IO Exception MSG IS" + e.getMessage());

			throw new IOException(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()	+ ". Illegal Argument Exception MSG IS ");

			throw new IllegalArgumentException(e.getMessage());
		} catch (Exception e) {
			System.out.println("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ ". Exception MSG IS " + e.getMessage());
		}
		return responseMessage;
	}

	private HttpGet getHttpGetParams(Properties serviceProps, Map<String, Object> inputParam) {

		String endPoint = serviceProps.getProperty("endPoint");
		String timeOut = serviceProps.getProperty("timeOut");
		String token = serviceProps.getProperty("token");
		String requestBody = serviceProps.getProperty("requestBody");

		HttpGet httpGet = null;

		if (requestBody != null) {
			httpGet = new HttpGet(endPoint + "?" + requestBody);
		} else {
			httpGet = new HttpGet(endPoint);
		}

		/// TIMEOUT CONFIGURATION
		RequestConfig timoutConfig = RequestConfig.custom().setConnectionRequestTimeout(Integer.parseInt(timeOut))
				.setConnectTimeout(Integer.parseInt(timeOut)).setSocketTimeout(Integer.parseInt(timeOut)).build();

		httpGet.setConfig(timoutConfig);

		/// ADD HEADER PARAMETER
		if (inputParam.containsKey("httpHeaderParams")) {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) inputParam.get("httpHeaderParams");
			if (map != null) {

				for (Entry<String, String> entry : map.entrySet()) {
					httpGet.setHeader(entry.getKey(), entry.getValue());
				}
			}
		}

		/// ADD TOKEN
		if (token != null && !token.trim().equalsIgnoreCase(""))
			httpGet.setHeader(HttpHeaders.AUTHORIZATION, token);

		return httpGet;
	}

	private HttpPost getHttpPostParams(Properties serviceProps, Map<String, Object> inputParam) {

		String endPoint = serviceProps.getProperty("endPoint");
		String timeout = serviceProps.getProperty("timeOut");

		String token = serviceProps.getProperty("token");

		String requestBody = serviceProps.getProperty("requestBody");
		HttpPost httpPost = new HttpPost(endPoint);
		if (requestBody != null) {
			StringEntity stringEntity = new StringEntity(requestBody, "UTF-8");
			stringEntity.setChunked(true);
			httpPost.setEntity(stringEntity);
		}

		/// TIMEOUT CONFIGURATION
		RequestConfig timoutConfig = RequestConfig.custom().setConnectionRequestTimeout(Integer.parseInt(timeout))
				.setConnectTimeout(Integer.parseInt(timeout)).setSocketTimeout(Integer.parseInt(timeout)).build();
		httpPost.setConfig(timoutConfig);

		/// ADD HEADER PARAMETER
		if (inputParam.containsKey("httpHeaderParams")) {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) inputParam.get("httpHeaderParams");
			if (map != null) {

				for (Entry<String, String> entry : map.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
		}

		/// ADD TOKEN
		if (token != null && !"".equalsIgnoreCase(token.trim()))
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, token);

		return httpPost;
	}

}
