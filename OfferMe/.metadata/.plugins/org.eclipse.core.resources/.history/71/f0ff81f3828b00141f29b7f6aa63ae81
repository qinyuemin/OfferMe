package com.offerme.client.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.offerme.client.socket.util.JSONUtil;

public class HttpRequest {

	// local server
	public static final String LOCAL_URL = "http://192.168.1.3:8080/OfferMeJeeServer/";

	// remote server
	public static final String REMOTE_URL = "http://120.27.39.10:8080/";

	public static String getHttpResponseStr(String service, Object param) {

		String res = null;

		HttpClient client = new DefaultHttpClient();
		// HttpPost method = new HttpPost(LOCAL_URL + service);
		HttpPost method = new HttpPost(REMOTE_URL + service);

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("param", JSONUtil
					.beanToJson(param)));
			method.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = client.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				// Read the response body
				res = EntityUtils.toString(response.getEntity());
			}
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
		} catch (ClientProtocolException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}

		return res;
	}

	public static String getHttpResponseStr(String service,
			List<NameValuePair> params) {

		String res = null;

		HttpClient client = new DefaultHttpClient();
		//HttpPost method = new HttpPost(LOCAL_URL + service);
		HttpPost method = new HttpPost(REMOTE_URL + service);

		try {
			method.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = client.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				// Read the response body
				res = EntityUtils.toString(response.getEntity());
			}
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
		} catch (ClientProtocolException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}

		return res;
	}

	public static <T> T getHttpResponse(String service, Object param,
			Class<T> replyClass) {
		try {
			String strRes = getHttpResponseStr(service, param);
			return JSONUtil.jsonToBean(strRes, replyClass);
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

	public static <T> T getHttpResponse(String service,
			List<NameValuePair> params, Class<T> replyClass) {
		try {
			String strRes = getHttpResponseStr(service, params);
			return JSONUtil.jsonToBean(strRes, replyClass);
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}

	}
}
