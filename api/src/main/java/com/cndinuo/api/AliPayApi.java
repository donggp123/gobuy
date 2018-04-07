package com.cndinuo.api;

import com.alibaba.fastjson.JSON;
import com.cndinuo.base.BaseApi;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 支付宝支付
 */
@RestController
@RequestMapping("alipay")
public class AliPayApi extends BaseApi{

    @Autowired
    private AlipayService alipayService;

    /**
     * 支付宝支付获取订单信息
     * @param params
     * @return
     */
    @RequestMapping(value = "orderinfo")
    public RespData orderInfo(@RequestBody Map<String, String> params) {
        log.info("支付宝支付获取订单信息: params==" + params);
        RespData data = alipayService.getOrderInfo(params.get("token"), params.get("orderNo"));
        log.info("获取结果：" + JSON.toJSONString(data));
        return data;
    }


    @RequestMapping(value = "alipayback")
    public void alipayBack(HttpServletRequest request, HttpServletResponse response) {
        log.info("支付宝支付完成回调 alipayBack");
        try {
            PrintWriter out = response.getWriter();
            request.setCharacterEncoding("UTF-8");
            //获取支付宝POST过来反馈信息
            Map requestParams = request.getParameterMap();
            String result = alipayService.alipayBack(requestParams);
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "withdrawcash",method = RequestMethod.POST)
    public RespData withdrawCash(@RequestBody Map<String,String> params){
        log.info("调用支付宝提现接口 params == " + params);
        try {
            RespData data = alipayService.withdrawCash(params.get("token"),params.get("amount"));
            log.info("支付宝提现结果：" + JSON.toJSONString(data));
            return data;
        }catch (Exception e){
            e.printStackTrace();
            log.error("提现发生异常:"+e);
            return RespData.errorMsg(Const.ERROR_MSG);
        }
    }

}
