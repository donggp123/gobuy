package com.cndinuo.utils;

import com.cndinuo.common.Const;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;


public class WXPayUtil {

	/**
	 * 生成随机字符串
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }  
	
	/** 
     * 微信支付签名算法sign 
     * @param characterEncoding 
     * @param parameters 
     * @return 
     */  
    @SuppressWarnings("rawtypes")
	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Entry entry = (Entry)it.next();
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v) 
            		&& !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + Const.WX_PAY_MCH_KEY);
        System.out.println(sb.toString());
        String sign = MD5.encode(sb.toString()).toUpperCase();
        return sign;  
    }  
	
    /**
     * 将Map转换成xml
     * @param map
     * @return
     */
    public static String MapToXml(Map<Object,Object> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		sb.append("\n");
		Set<Entry<Object, Object>> entrys = map.entrySet();
		for(Entry<Object, Object> entry : entrys){
			Object key = entry.getKey();
			Object value = entry.getValue();
			sb.append("<"+key+">");
			sb.append("<![CDATA["+value+"]]>");
			sb.append("</"+key+">");
			sb.append("\n");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 将xml转换成map
	 * @param request
	 * @return
	 */
    public static Map<String,String> parseXml(HttpServletRequest request){
    	Map<String,String> map = new HashMap<String,String>();
    	try {
    		//解析xml
    		InputStream is = request.getInputStream();
    		SAXReader reader = new SAXReader();
    		Document doc = reader.read(is);
    		Element root = doc.getRootElement();
    		List<Element> elements = root.elements();
    		
    		//遍历节点封装成对象
    		for(Element e : elements){
    			String name = e.getName();
    			String text = e.getText();
    			map.put(name, text);
    		}
    		is.close();
			is = null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	return map;
    }
    /**
     * 将xml转换成Map
     * @param xml
     * @return
     */
	public static Map<Object,Object> parseXml(String xml){
		Map<Object,Object> map = new HashMap<Object, Object>();
		try {
			//解析xml
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			List<Element> elements = root.elements();
			
			//遍历节点封装成对象
			for(Element e : elements){
				String name = e.getName();
				String text = e.getText();
				map.put(name, text);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}
    
	/**
	 * 发送请求（https）
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return 
	 */
	public static String httpsRequest(String requestUrl,String requestMethod,String outputStr){

		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {new WX509TrustManager()};
			SSLContext context = SSLContext.getInstance("SSL","SunJSSE");
			context.init(null,tm,new SecureRandom());
			
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = context.getSocketFactory();
			
			// 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
			URL url = new URL(requestUrl); // 创建URL对象
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			//下面这段代码实现向Web页面发送数据，实现与网页的交互访问 
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(requestMethod);
			
			if(null != outputStr){
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			
			// 取得该连接的输入流，以读取响应内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			
			while((str = bufferedReader.readLine()) != null){
				buffer.append(str);
			}
			
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			
			return buffer.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String wxFefundHttps(String requestUrl,String param,String certPath){
		StringBuffer sb = new StringBuffer();
		try {
			//指定读取证书格式为PKCS12
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			//读取本机存放的PKCS12证书文件
			FileInputStream instream = new FileInputStream(new File(certPath));
			try {
				//指定PKCS12的密码(商户ID)
				keyStore.load(instream, Const.WX_PAY_MCH_ID.toCharArray());
			} finally {
				instream.close();
			}
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, Const.WX_PAY_MCH_ID.toCharArray()).build();
			//指定TLS版本
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
			sslcontext,new String[] { "TLSv1" },null,
			SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			//设置httpclient的SSLSocketFactory
			CloseableHttpClient httpclient = HttpClients.custom()
			.setSSLSocketFactory(sslsf)
			.build();
			
			HttpPost post = new HttpPost(Const.WX_PAY_REFUND_URL);
			StringEntity entity = new StringEntity(param,"UTF-8");
			post.addHeader("Content-Type", "text/xml");
			post.setEntity(entity);
			CloseableHttpResponse response = httpclient.execute(post);
			
			HttpEntity result = response.getEntity();
			System.out.println(result);
			

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (entity != null) {
				System.out.println("Response content length: " + result.getContentLength());
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(result.getContent()));
				String text = "";
				while ((text = bufferedReader.readLine()) != null) {
					sb.append(text);
					System.out.println(sb.toString());
				}
			}
			EntityUtils.consume(entity);
			response.close();
			httpclient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String urlEncodeUTF8(String str){
		String result = str;
		try {
			result = URLEncoder.encode(str,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

class WX509TrustManager implements X509TrustManager {
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
