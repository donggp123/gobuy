package com.cndinuo.service;

import com.alipay.api.AlipayApiException;
import com.cndinuo.common.RespData;

import java.util.Map;

public interface AlipayService {
    RespData getOrderInfo(String token, String orderNo);

    String alipayBack(Map requestParams);

    boolean alipayRefund(String orderNo,String tradeNo,String refundAmount,String retNo);

    RespData withdrawCash(String token, String amount) throws AlipayApiException;
}
