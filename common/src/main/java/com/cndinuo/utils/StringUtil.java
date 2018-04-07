/*
 * Created on 2012-4-02 08:15:00
 *
 */
package com.cndinuo.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Liyiwei
 * 
 */
public class StringUtil {
	private static String[] chars = new String[]{
		"a","b","c","d","e","f","g","h", 
		"i","j","k","l","m","n","o","p", 
		"q","r","s","t","u","v","w","x", 
		"y","z","0","1","2","3","4","5", 
		"6","7","8","9","A","B","C","D", 
		"E","F","G","H","I","J","K","L", 
		"M","N","O","P","Q","R","S","T", 
		"U","V","W","X","Y","Z" 
	};
	
	public static boolean isEmpty(String value) {
		return (value == null || value.length() == 0 || value.trim().equals("")) ? true : false;
	}

	public static boolean isNotEmpty(String value) {
		return (value != null && value.length() > 0 && !value.trim().equals("")) ? true : false;
	}

	public static String notNull(String value) {
		return value == null ? "" : value.trim();
	}

	public static boolean equals(String value1, String value2) {
		return value1 == null ? false : value1.equals(value2);
	}

	public static String toString(String value) {
		return value == null ? "" : value.trim();
	}

	public static String toString(String value, String defaultValue) {
		return value == null ? defaultValue : value.trim();
	}

	public static String toString(int value) {
		return String.valueOf(value);
	}

	public static String toString(double value) {
		return String.valueOf(value);
	}

	public static String toString(Integer value) {
		return value == null ? "" : String.valueOf(value);
	}

	public static String toString(Integer value, String defaultValue) {
		return value == null ? defaultValue : String.valueOf(value);
	}

	public static String toString(Double value) {
		return value == null ? "" : String.valueOf(value);
	}

	public static String toString(Object value) {
		return value == null ? "" : String.valueOf(value);
	}

	public static int toInt(String value) {
		return toInt(value, 0);
	}

	public static int toInt(String value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		int num = defaultValue;
		try {
			num = Integer.parseInt(value);
		} catch (Exception e) {
		}
		return num;
	}

	public static int toInt(Integer value) {
		return value == null ? 0 : value.intValue();
	}

	public static int toInt(Integer value, int defaultValue) {
		return value == null ? defaultValue : value.intValue();
	}

	public static int toInt(Object value) {
		return value == null ? 0 : toInt(toString(value));
	}

	public static Integer toInteger(String value) {
		return value == null ? null : toInt(value);
	}

	public static double toDouble(String value) {
		return toDouble(value, 0.0);
	}

	public static double toDouble(String value, double defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		double num = defaultValue;
		try {
			num = Double.parseDouble(value);
		} catch (Exception e) {
		}
		return num;
	}

	public static Date toDate(String value, String formatDate) {
		if (value == null || value.equals(""))
			return null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
			return simpleDateFormat.parse(value);
		} catch (ParseException e) {
			return null;
		}
	}

	public static boolean toBoolean(String value) {
		if (value == null) {
			return false;
		}

		if (value.equals("TRUE") || value.equals("true") || value.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean toBoolean(Integer value) {
		if (value == null) {
			return false;
		}

		if (value.intValue() == 1) {
			return true;
		} else {
			return false;
		}
	}

	public static String searchLeft(String value, String word) {
		int pos = value.indexOf(word);
		if ((pos > 0) && (pos < (value.length() - 1))) {
			return value.substring(0, pos);
		}
		return value;
	}

	public static String searchRight(String value, String word) {
		int pos = value.indexOf(word);
		if ((pos > 0) && (pos < (value.length() - 1))) {
			return value.substring(pos + word.length());
		}
		return value;
	}

	public static String searchLastLeft(String value, String word) {
		int pos = value.lastIndexOf(word);
		if ((pos > 0) && (pos < (value.length() - 1))) {
			return value.substring(0, pos);
		}
		return value;
	}

	public static String searchLastRight(String value, String word) {
		int pos = value.lastIndexOf(word);
		if ((pos > 0) && (pos < (value.length() - 1))) {
			return value.substring(pos + word.length());
		}
		return value;
	}

	public static String formatDate(Date date, String formatDate) {
		if (date == null)
			return "";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
		return simpleDateFormat.format(date);
	}


	public static boolean isContain(String value, String values) {
		if (isEmpty(value) || isEmpty(values)) {
			return false;
		}
		String[] array = values.split(",");
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	public static String brief(String value, int length) {
		if (value == null) {
			return "";
		}
		if (value.length() > length)
			return value.substring(0, length - 1) + "...";
		else
			return value;
	}

	public static String join(String[] values, String seperator) {
		if (values == null || values.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(seperator).append(values[i]);
		}
		return sb.toString();
	}

	public static String join(List<?> values, String seperator) {
		if (values == null || values.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(values.get(0));
		for (int i = 1; i < values.size(); i++) {
			sb.append(seperator).append(values.get(i));
		}
		return sb.toString();
	}

	public static String repeat(String value, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(value);
		}

		return sb.toString();
	}

	public static String capFirst(String value) {
		if (value == null || value.trim().equals("")) {
			return "";
		}

		String val = value.toLowerCase();
		StringBuilder sb = new StringBuilder();
		boolean match = false;
		for (int i = 0; i < val.length(); i++) {
			char ch = val.charAt(i);
			if (match && ch >= 97 && ch <= 122) {
				ch -= 32;
			}
			if (ch != '_') {
				match = false;
				sb.append(ch);
			} else {
				match = true;
			}
		}
		return sb.toString();
	}

	public static String upperFirst(String value) {
		return Character.toUpperCase(value.charAt(0)) + value.substring(1);
	}

	public static String lowerFirst(String value) {
		return Character.toLowerCase(value.charAt(0)) + value.substring(1);
	}

	public static boolean pattern(String content, String regEx) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(content);
		return m.find();
	}

	public static String match(String content, String regEx) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(content);
		if (m.find()) {
			return m.group();
		}
		return null;
	}

	public static String match(String content, String regEx, int i) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(content);
		if (m.find()) {
			return m.group(i);
		}
		return null;
	}

	public static String iso2gb(String value) {
		if (value == null) {
			return "";
		} else {
			try {
				return new String(value.trim().getBytes("iso-8859-1"), "gb2312");
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
	}

	public static String encode(String value) {
		try {
			return URLEncoder.encode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String decode(String value) {
		try {
			return URLDecoder.decode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String encodeBase64(String content) {
		return new String(Base64.encode(content.getBytes()));
	}

	public static String decodeBase64(String content) {
		return new String(Base64.encode(content.getBytes()));
	}
	
	public static String getRandStr(int n) {
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < n; i++) {
			String indexStr = String.valueOf(random.nextInt(61));
			int index = Integer.valueOf(indexStr);
			String rand= chars[index];
			sRand += rand;
		}
		return sRand;
	}

	/**
	 * 密码加盐算法
	 * @param saltSource
	 * @param password
	 * @return
	 */
	public static String saltCode(String saltSource,String password) {
		String hashAlgorithmName = "MD5";
		Object salt = new Md5Hash(saltSource);
		int hashIterations = 1;
		Object result = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
		return result.toString();
	}

	/**
	 * 生成token
	 * @return
	 */
	public static String token(String key){
		return MD5.encode(StringUtil.toString(Math.abs(System.currentTimeMillis())+key));
	}

	/**
	 * 获取客户端ip
	 * @param request
	 * @return
	 */
	public static String getRemoteHost(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	/**
	 * 随机生成4位或者6位数字
	 * @param length
	 * @return
	 */
	public static String randomNum(int length) {
		if (length == 4) {
			return toString((int)((Math.random()*9+1)*1000));
		}else{
			return toString((int)((Math.random()*9+1)*100000));
		}
	}

	/**
	 * 格式化评论时间
	 * 1分之内，显示刚刚
	 * 1小时之内，显示xx分钟前
	 * 1天之内，显示xx小时前
	 * 5天之内，显示xx天前
	 * 超过5天，显示具体日期：MM-dd
	 * @param date
	 * @return
	 */
	public static String formatCommentDate(Date date){
		Date nowDate = new Date();
		long min = 60*1000l;                        //1分之内，显示刚刚
		long hour = 60*60*1000l;                    //1小时之内，显示xx分钟前
		long day = 60*60*1000*24l;                  //1天之内，显示xx小时前
		long day5 = 60*60*1000*24*5l;               //5天之内，显示xx天前
		long time = (nowDate.getTime()-date.getTime());
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		String str = "";
		if(min>time){
			str = "刚刚";
		}else if(hour>time){
			str = time/min+"分钟前";
		}else if(day>time){
			str = time/hour+"小时前";
		}else if(day5>time){
			str = time/day+"天前";
		}else if(day5<time){
			str = format.format(date);
		}
		return str;
	}

	/**
	 * 将手机号替换成152****1234
	 * @param mobile
	 * @return
	 */
	public static String replaceMobile(String mobile){
		String rep = mobile.substring(3, 7);
		mobile = mobile.replaceAll(rep,"****");
		return mobile;
	}
}