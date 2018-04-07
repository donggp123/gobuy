package com.cndinuo.api;

import com.alibaba.fastjson.JSON;
import com.cndinuo.base.BaseApi;
import com.cndinuo.common.RespData;
import com.cndinuo.service.WxPayService;
import com.cndinuo.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxPayApi extends BaseApi{

    @Autowired
    private WxPayService wxPayService;

    /**
     * 微信统一下单接口
     * @param params
     * @return
     */
    @RequestMapping(value = "unified",method = RequestMethod.POST)
    public RespData unifiedOrder(@RequestBody Map<String, String> params) {
        log.info("用户发起支付请求，统一下单接口：params==" + params);
        RespData data = wxPayService.unifiedOrder(params.get("token"), params.get("orderNo"));
        log.info("请求结果：" + JSON.toJSONString(data));
        return data;
    }

    /**
     * 微信支付完成回调地址
     * @param request
     * @param response
     */
    @RequestMapping(value = "wxback")
    public void wxBack(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> resultMap = WXPayUtil.parseXml(request);
        log.info("支付完成通知报文＝＝＝"+resultMap.toString());
        String resultXml = wxPayService.wxBack(resultMap);
        log.info("回调结果：" + resultXml.toString());
        outputJson(response,resultXml);
    }

    /**
     * 支付完成调用该接口查询是否支付成功
     * @param params
     * @return
     */
    @RequestMapping(value = "queryorder",method = RequestMethod.POST)
    public RespData queryOrder(@RequestBody Map<String, String> params) {
        log.info("支付完成查询是否支付成功： params===" + params);
        RespData data = wxPayService.queryOrder(params.get("token"), params.get("orderNo"));
        log.info("查询结果:" + data);
        return data;
    }
}
