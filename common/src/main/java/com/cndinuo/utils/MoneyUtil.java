package com.cndinuo.utils;

import java.math.BigDecimal;

public class MoneyUtil {

	/**
	 * 元 转 分
	 * @param yuan
	 * @return
	 */
	public static String y2f(String yuan){
		if(yuan==null ||"".equals(yuan)){
			return "";
		}
		String fen = "" ;
		BigDecimal bdyuan = new BigDecimal(yuan);
		//解决精度问题
		BigDecimal bdfen = bdyuan.multiply(new BigDecimal(100)).divide(new BigDecimal(1),1, BigDecimal.ROUND_HALF_UP) ;
		fen = String.valueOf(bdfen.toBigInteger());
		return fen;
	}
	/**
	 * 分 转 元 
	 * @param fen
	 * @return
	 */
	public static String f2y(String fen){
		if(fen==null ||"".equals(fen)){
			return "";
		}
		String yuan = "" ;
		BigDecimal bdfen = new BigDecimal(fen);
		BigDecimal bdyuan = bdfen.divide(new BigDecimal(100));
		yuan = bdyuan.toPlainString();
		return yuan;
	}

}
