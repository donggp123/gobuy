package com.cndinuo.service;

import com.cndinuo.common.RespData;

import java.util.Map;

public interface WxPayService {

    RespData unifiedOrder(String token, String orderNo);

    String wxBack(Map<String, String> resultMap);

    RespData queryOrder(String token, String orderNo);

    boolean wxRefund(String refundNo, String outTradeNo, int totalFee, int refundFee);
}
