package com.cndinuo.utils;

import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpManager {
	
	private static Logger LOG = LoggerFactory.getLogger(HttpManager.class);
	
	/**
	 * 最大连接数
	 */
	private static final int MAX_TOTAL_CONNECTIONS = 200;
	
	/**
	 * 获取连接最大等待时间
	 */
	private static final int MAX_WAIT_TIMEOUT = 6000;
	
	/**
	 * 每个路由最大连接数
	 */
	public static final int MAX_ROUTE_CONNTCIONS = 100;
	
	/**
	 * 连接超时时间
	 */
	public static final int CONNECT_TIMEOUT = 3000;
	
	/**
	 * 字符集编码
	 */
	private static final String CHARSET = "UTF-8";
	public static final String CONTENT_TYPE = "application/json";
	
	/**
	 * 读取超时时间
	 */
	public static final int READ_TIMEOUT = 3000;

	
	/**
	 * @param url 请求路径
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static RespData doGet(String url) {
		return doGet(url,CHARSET);
	}
	
	public static RespData doGet(String url ,String userName ,String password) {
		return doGet(url,userName,password,CHARSET);
	}
	
	/**
	 * @param url 请求路径
	 * @param charset 字符集
	 * @return ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static RespData doGet(String url ,String charset) {
		return doGet(url, null, null , charset);
	}
	
	/**
	 * @param url 请求路径
	 * @param userName 用户名
	 * @param password 密码
	 * @param charset 字符集
	 * @return ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static RespData doGet(String url ,String userName ,String password , String charset) {
		if(StringUtils.isBlank(url))
			throw new IllegalAccessError("入参url 为空");
		
		if(StringUtils.isBlank(charset)) {
			charset = CHARSET;
		}

		HttpGet request = new HttpGet(url);
		
		if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
			byte[] credentials = Base64.encodeBase64((userName + ":" + password).getBytes(StandardCharsets.UTF_8));
			request.setHeader("Authorization", "Basic " + new String(credentials, StandardCharsets.UTF_8));
		}
		
		return send(request, charset);
	}
	
	/**
	 * @param url 请求路径
	 * @param params 入参
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static <K,V> RespData doGet(String url ,Map<K,V> params) {
		return doGet(url, params, CHARSET);
	}
	
	/**
	 * @param url 请求路径
	 * @param params 请求参数
	 * @param charset 字符集
	 *  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 * @return
	 */
	public static <K,V> RespData doGet(String url , Map< K , V > params, String charset) {
		return doGet(url,null,null,params,charset);
	}
	
	/**
	 * @param url 请求路径
	 * @param userName 用户名
	 * @param password 密码
	 * @param params 请求参数
	 * @param charset 字符集
	 *  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 * @return
	 */
	public static <K,V> RespData doGet(String url ,String userName, String password , Map<K,V> params,String charset) {
		
		if(StringUtils.isBlank(url)) {//正则验证url
			throw new IllegalArgumentException("url 为空");
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(url);
		
		try {
			List<NameValuePair> pairsList = getParamsList(params);
			
			if(pairsList.size() > 0) {
				buffer.append("?");
				buffer.append(EntityUtils.toString(new UrlEncodedFormEntity(pairsList,charset)));
			}
			
			HttpGet request = new HttpGet(buffer.toString());
			
			if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
				byte[] credentials = Base64.encodeBase64((userName + ":" + password).getBytes(StandardCharsets.UTF_8));
				request.setHeader("Authorization", "Basic " + new String(credentials, StandardCharsets.UTF_8));
			}
			
			return send(request,charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param url 请求url
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static RespData doPost(String url) {
		return doPost(url, CHARSET);
	}
	
	/**
	 * @param url 请求url
	 * @param charset 字符集
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static RespData doPost(String url ,String charset) {
		return doPost(url, null, null, charset);
	}
	
	/**
	 * @param url 请求路径
	 * @param userName 用户名
	 * @param password 用户密码
	 * @param charset 字符集
	 * @return ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static RespData doPost(String url , String userName , String password ,String charset) {
		
		if(StringUtils.isBlank(url))
			throw new IllegalArgumentException("入参url为空!!");
		
		if(StringUtils.isBlank(charset))
			charset = CHARSET;
		
		HttpPost request = new HttpPost(url);
		
		if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
			byte[] credentials = Base64.encodeBase64((userName + ":" + password).getBytes(StandardCharsets.UTF_8));
			request.setHeader("Authorization", "Basic " + new String(credentials, StandardCharsets.UTF_8));
		}

		return send(request,charset);
		
	}

	/**
	 * @param url 请求路径
	 * @param data 请求参数
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static < T > RespData post(String url ,T data) {
		return doPost(url, data, CHARSET,null);
	}
	
	/**
	 * @param url 请求路径
	 * @param data 请求参数
	 * @param data context_type
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static < T > RespData post(String url ,T data,String context_type) {
		return doPost(url, data, CHARSET,context_type);
	}
	
	/**
	 * @param url 请求路径
	 * @param userName  用户名
	 * @param password 用户密码
	 * @param data 请求参数
	 * @param data context_type
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static < T > RespData post(String url, String userName ,String password, T data,String context_type) {
		return doPost(url, userName, password, data, CHARSET,context_type);
	}
	
	/**
	 * @param url 请求路径
	 * @param data 请求参数
	 * @param charset  字符集
	 * @param data context_type
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static < T > RespData doPost(String url ,T data , String charset,String context_type) {
		return doPost(url,null , null , data ,charset,context_type);
	}
	
	/**
	 * @param url 请求路径
	 * @param userName  用户名
	 * @param password 用户密码
	 * @param data 请求参数
	 * @param charset 字符集
	 * @return
	 */
	public static < T > RespData doPost(String url ,String userName ,String password , T data , String charset ,String content_type) {
		
		if(StringUtils.isBlank(url))
			throw new IllegalArgumentException("入参url为空!!");
		
		String result = data == null ? "" : data.toString().trim();
		
		if(StringUtils.isBlank(result))
			return doPost(url);
		
		try {
			HttpPost request = new HttpPost(url);
			
			if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
				byte[] credentials = Base64.encodeBase64((userName + ":" + password).getBytes(StandardCharsets.UTF_8));
				request.setHeader("Authorization", "Basic " + new String(credentials, StandardCharsets.UTF_8));
			}
			
			StringEntity entity = new StringEntity(result, charset);
			entity.setContentEncoding(charset);
			if (content_type != null){
				entity.setContentType(CONTENT_TYPE);
			}
			request.setEntity(entity);
			
			return send(request,charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	/**
	 * @param url 请求路径
	 * @param params 请求参数
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static <K , V > RespData doPost(String url ,Map<K,V> params) {
		return doPost(url, params, CHARSET);
	}
	
	/**
	 * @param url 请求路径
	 * @param params 请求参数
	 * @param charset  字符集
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static <K , V > RespData doPost(String url ,Map<K,V> params , String charset) {
		return doPost(url, null, null, params, charset);
	}
	
	/**
	 * @param url 请求路径
	 * @param userName 用户名
	 * @param password 密码
	 * @param params 请求参数
	 * @param charset 字符集
	 * @return ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static <K , V > RespData doPost(String url , String userName , String password , Map< K, V > params , String charset) {
		
		if(StringUtils.isBlank(url))
			throw new IllegalArgumentException("入参url为空!!");
		
		if(params == null || params.size() == 0)
			return doPost(url);
		
		try {
			
			List<NameValuePair> pairsList = getParamsList(params);
			
			HttpPost request = new HttpPost(url);
			
			if(pairsList.size() > 0) {
				request.setEntity(new UrlEncodedFormEntity(pairsList,charset));
			}
			
			if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
				byte[] credentials = Base64.encodeBase64((userName + ":" + password).getBytes(StandardCharsets.UTF_8));
				request.setHeader("Authorization", "Basic " + new String(credentials, StandardCharsets.UTF_8));
			}
			
			return send(request,charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * @param request  get or post 请求
	 * @param charset 字符集
	 * @return
	 */
	private static <T extends HttpRequestBase> RespData send(T request , String charset) {

		RespData data = new RespData(RetCode.ACTIVE_FAILURE.getCode());
		
		CloseableHttpResponse response = null;
		try {
			
			CloseableHttpClient httpClient = buildHttpClient();
			request.setConfig(buildRequestConfig());
			response = httpClient.execute(request);
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			HttpEntity entity = response.getEntity();
			
			String result = null;
			
			if(entity != null) {
				result = EntityUtils.toString(entity, CHARSET);
			}
			
			data.setCode(statusCode);
			data.setData(result);
			
			return data;
			
		} catch (Exception e) {
			LOG.error("", e);
			return data;
		} finally {
			if(response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				
		}
	}
	
	/**
	 * 构建公用RequestConfig
	 * 
	 * @return
	 */
	public static RequestConfig buildRequestConfig() {
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(MAX_WAIT_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).build();
		return requestConfig;
	}
	
	/**
	 * @param params 参数map
	 * 构造参数map
	 */
	private static <K,V> List<NameValuePair> getParamsList(Map<K,V> params) {
		
		List<NameValuePair> pairsList = new ArrayList<NameValuePair>();
		
		if(params == null || params.size() == 0)
			return pairsList;
					
		for(Map.Entry<K, V> entry : params.entrySet()) {
			K key = entry.getKey();
			V value = entry.getValue();
			if(key == null || value == null || StringUtils.isBlank(key.toString()) || StringUtils.isBlank(value.toString())) {
				LOG.info("参数的key 或者value 为空!!!");
				continue;
			}
			pairsList.add(new BasicNameValuePair(key.toString(), value.toString()));
		}
		
		return pairsList;
	}
	
	/**
	 * 构造httpclient
	 * @return
	 * 
	 */
	private static CloseableHttpClient buildHttpClient() {
		
		PoolingHttpClientConnectionManager pcm = new PoolingHttpClientConnectionManager();
		pcm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		pcm.setDefaultMaxPerRoute(MAX_ROUTE_CONNTCIONS);
		
		return  HttpClients.custom().setConnectionManager(pcm).build();
	}
	
	
	/**
	 * @param url 请求路径
	 * @param userName 用户名
	 * @param password 密码
	 * @param params 请求参数
	 * @param charset 字符集
	 * @return ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static <K , V > RespData doPut(String url , String userName , String password , Map< K, V > params , String charset) {
		
		if(StringUtils.isBlank(url))
			throw new IllegalArgumentException("入参url为空!!");
		
		try {
			List<NameValuePair> pairsList = getParamsList(params);
			
			HttpPut request = new HttpPut(url);
			if(pairsList.size() > 0) {
				request.setEntity(new UrlEncodedFormEntity(pairsList,charset));
			}
			
			if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
				byte[] credentials = Base64.encodeBase64((userName + ":" + password).getBytes(StandardCharsets.UTF_8));
				request.setHeader("Authorization", "Basic " + new String(credentials, StandardCharsets.UTF_8));
			}
			
			return send(request,charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * @param url 请求url
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static RespData doPut(String url) {
		return doPut(url, CHARSET);
	}
	/**
	 * @param url 请求url
	 * @param charset 字符集
	 * @return  ResponseDTO  返回结果  code :状态码   attach : 返回内容
	 */
	public static RespData doPut(String url ,String charset) {
		return doPost(url, null, null, charset);
	}

	
}
