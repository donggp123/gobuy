package com.cndinuo.service.impl;

import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.*;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.*;
import com.cndinuo.service.*;
import com.cndinuo.utils.MoneyUtil;
import com.cndinuo.utils.StringUtil;
import com.cndinuo.utils.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

@Service("wxPayService")
public class WxPayServiceImpl implements WxPayService {

    private static final Logger log = LoggerFactory.getLogger("wxpay");
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private WxpayFlowDao flowDao;
    @Autowired
    private OrderTempDao tempDao;
    @Autowired
    private OrderItemDao itemDao;
    @Autowired
    private OrderTrackService trackService;
    @Autowired
    private MberRiderInfoService riderInfoService;
    @Autowired
    private MrhtGoodsDao goodsDao;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MberRiderBalanceService mberRiderBalanceService;

    @Value("${wxpay.notifyurl}")
    private String notifyurl;

    @Override
    public RespData unifiedOrder(String token, String orderNo) {

        if (StringUtil.isEmpty(token)) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        if (StringUtil.isEmpty(orderNo)) {
            return RespData.errorMsg("订单号不能为空!");
        }

        Member m = memberDao.getByToken(token);
        if (m == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已经过期，请重新登录");
        }

        OrderTemp order = tempDao.getByOrderNo(orderNo);
        if (order == null) {
            return RespData.errorMsg("订单号错误!");
        }
        try {
            SortedMap<Object, Object> data = this.packingData(order);
            //请求微信统一下单接口
            String requestXml = WXPayUtil.MapToXml(data);
            log.info("微信支付统一下单请求报文=="+requestXml);
            String result = WXPayUtil.httpsRequest(Const.WX_PAY_UNIFIED_ORDER_URL,
                    Const.WX_REQUEST_METHOD_POST, requestXml);

            Map<Object,Object> resultMap = WXPayUtil.parseXml(result);

            log.info("微信支付统一下单响应报文==="+resultMap.toString());

            String return_code = (String) resultMap.get("return_code");
            String result_code = (String) resultMap.get("result_code");

            if("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)){
                String prepay_id = (String) resultMap.get("prepay_id");
                long timestamp = System.currentTimeMillis()/1000;
                SortedMap<Object,Object> payXml = new TreeMap<Object,Object>();
                String noncestr = WXPayUtil.getRandomString(32);
                payXml.put("appid", Const.WX_PAY_APPID);
                payXml.put("partnerid", Const.WX_PAY_MCH_ID);
                payXml.put("prepayid", prepay_id);
                payXml.put("package", "Sign=WXPay");
                payXml.put("noncestr", noncestr);
                payXml.put("timestamp", timestamp);
                String paySign = WXPayUtil.createSign("UTF-8", payXml);
                payXml.put("sign", paySign);
                log.info("返回app去支付报文：" + payXml.toString());
                return RespData.successMsg("请求成功", payXml);

            }else{
                return RespData.errorMsg("请求失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求统一下单接口发生异常!" + e);
            return RespData.errorMsg(Const.ERROR_MSG);
        }
    }


    private SortedMap<Object, Object> packingData(OrderTemp order) {

        //拼接请求参数
        String nonce_str = WXPayUtil.getRandomString(32);
        SortedMap<Object,Object> data = new TreeMap<Object,Object>();
        data.put("appid", Const.WX_PAY_APPID);                                           //公众号id
        data.put("mch_id", Const.WX_PAY_MCH_ID);                                         //商户id
        data.put("nonce_str", nonce_str);                                                //随机字符串
        data.put("body", order.getGoodsName());                                          //商品或支付单简要描述
        data.put("out_trade_no",order.getOrderNo());                                     //商户订单号
        data.put("total_fee", MoneyUtil.y2f(String.valueOf(order.getTotalPrice())));     //总金额-单位为分
        data.put("spbill_create_ip", "127.0.0.1");                                       //终端ip
        log.info("微信回调地址==="+notifyurl);
        data.put("notify_url", notifyurl);                                               //回调地址
        data.put("trade_type", Const.WX_TRADE_TYPE);                                     //交易类型-app

        //以下是选填参数
        data.put("device_info", "");              //设备号
        data.put("fee_type", "");                 //货币类型
        data.put("time_start", "");               //交易起始时间-订单生成时间，格式为yyyyMMddHHmmss
        data.put("time_expire", "");              //交易结束时间-订单失效时间，格式为yyyyMMddHHmmss
        data.put("goods_tag", "");                //商品标记
        data.put("detail", "");                   //商品名称明细列表
        data.put("attach", "");                   //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
        data.put("product_id", "");               //商品ID
        data.put("limit_pay", "");                //指定支付方式
        data.put("openid", "");                   //用户标识
        String orderSign = WXPayUtil.createSign("UTF-8", data);
        data.put("sign", orderSign);//签名
        return data;
    }


    @Override
    public String wxBack(Map<String, String> resultMap) {
        log.info("微信支付完成回调通知：" + resultMap.toString());
        Map<Object,Object> returnMap = new HashMap<Object,Object>();
        OrderTemp temp = null;
        try {
            String resultCode = resultMap.get("result_code");
            String orderNo = resultMap.get("out_trade_no");
            temp = tempDao.getByOrderNo(orderNo);
            //判断是否已经处理过
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("orderNo", orderNo);
            List<WxpayFlow> list = flowDao.getByMap(params);
            if (list == null || list.size() == 0) {
                WxpayFlow flow = new WxpayFlow();
                if(saveFlow(resultMap,flow)==0){
                    returnMap.put("return_code", "FAIL");
                    returnMap.put("return_msg", "处理失败");
                    String retXml = WXPayUtil.MapToXml(returnMap);
                    log.info("微信支付回调报文流水入库失败===="+retXml);
                    return retXml;
                }
                if ("SUCCESS".equals(resultCode)) {
                    //更新订单表状态(要将临时表中的订单记录同步到订单表)，订单跟踪表记录
                    this.tempToOrder(orderNo);
                }else {
                    if (temp.getOrderType() == OrderTypeEnum.ORDER_SHOPPING.getCode().byteValue()) {
                        //支付失败将库存还原
                        List<OrderTemp> items = tempDao.getItemsByOrderNo(orderNo);
                        for (OrderTemp item : items) {
                            MrhtGoods goods = goodsDao.getById(item.getGoodsId());
                            params.clear();
                            params.put("id", item.getGoodsId());
                            params.put("stockNum", goods.getStockNum() + item.getNum());
                            goodsDao.updateByMap(params);
                            item.setOrderStatus(OrderStatusEnum.ORDER_TEMP_FAIL.getCode().byteValue());
                            tempDao.updateById(item);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
            log.error("支付回调报文处理失败==" + e);
            returnMap.put("return_code", "FAIL");
            returnMap.put("return_msg", "处理失败");
            String retXml = WXPayUtil.MapToXml(returnMap);
            return retXml;
        }
        returnMap.put("return_code", "SUCCESS");
        returnMap.put("return_msg", "处理成功");
        String retXml = WXPayUtil.MapToXml(returnMap);
        log.info("支付回调报文处理成功====："+retXml);
        //TODO 通知商家有新的订单待测试
        if (temp.getOrderType() == OrderTypeEnum.ORDER_SHOPPING.getCode().byteValue()) {
            merchantService.notifyMerchantNewOrder(temp.getOrderNo());
        }
        return retXml;
    }

    /**
     * 保存微信支付流水
     * @param resultMap
     * @param flow
     * @return
     */
    private int saveFlow(Map<String, String> resultMap,WxpayFlow flow) {
        flow.setDeviceInfo(resultMap.get("device_info"));
        flow.setNonceStr(resultMap.get("nonce_str"));
        flow.setSign(resultMap.get("sign"));
        flow.setResultCode(resultMap.get("result_code"));
        flow.setErrCode(resultMap.get("err_code"));
        flow.setErrCodeDes(resultMap.get("err_code_des"));
        flow.setOpenId(resultMap.get("openid"));
        flow.setIsSubscribe(resultMap.get("is_subscribe"));
        flow.setTradeType(resultMap.get("trade_type"));
        flow.setBankType(resultMap.get("bank_type"));
        flow.setTotalFee(StringUtil.toInt(resultMap.get("total_fee")));
        flow.setFeeType(resultMap.get("fee_type"));
        flow.setCashFee(StringUtil.toInt(resultMap.get("cash_fee")));
        flow.setCashFeeType(resultMap.get("cash_fee_type"));
        flow.setCouponFee(StringUtil.toInt(resultMap.get("coupon_fee")));
        flow.setCouponCount(StringUtil.toInt(resultMap.get("coupon_count")));
        flow.setTransactionId(resultMap.get("transaction_id"));
        flow.setOutTradeNo(resultMap.get("out_trade_no"));
        flow.setAttach(resultMap.get("attach"));
        flow.setTimeEnd(resultMap.get("time_end"));
        OrderTemp temp = tempDao.getByOrderNo(resultMap.get("out_trade_no"));
        flow.setMberId(temp.getMberId());
        int result = flowDao.insert(flow);
        return result;
    }

    /**
     * 临时订单表中的记录同步到订单表中
     * @param orderNo
     */
    private OrderTemp tempToOrder(String orderNo){
        OrderTemp temp = tempDao.getByOrderNo(orderNo);
        Order order = new Order();
        BeanUtils.copyProperties(temp, order);
        order.setSalesPrice(temp.getTotalPrice().subtract(temp.getDeliveryFee()));
        order.setPayType(PayTypeEnum.WECHAT.getCode().byteValue());
        order.setPayTime(new Date());
        order.setSettleStatus(SettleStatusEnum.STAY_SETTLE.getCode().byteValue());
        if (temp.getOrderType() == OrderTypeEnum.ORDER_SHOPPING.getCode().byteValue()) {
            order.setOrderStatus(OrderStatusEnum.STAY_MERCHANT_ORDERS.getCode().byteValue());
            orderDao.insert(order);
            List<OrderTemp> items = tempDao.getItemsByOrderNo(orderNo);
            for (OrderTemp item : items) {
                OrderItem it = new OrderItem();
                BeanUtils.copyProperties(item, it);
                itemDao.insert(it);
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("mrhtId", order.getMberId());
            MberRiderInfo info = riderInfoService.getOneByMap(params);
            BigDecimal deposit = info.getDeposit();
            if (deposit == null) {
                deposit = BigDecimal.valueOf(0);
            }
            deposit = deposit.add(order.getTotalPrice());
            params.put("deposit", deposit);
            params.put("id", info.getId());
            riderInfoService.updateByMap(params);           //更新到骑手信息表中
            MberRiderBalance balance = new MberRiderBalance();
            balance.setCreateTime(new Date());
            balance.setAmount(deposit);
            balance.setType(BalanceTypeEnum.Recharge.getCode().byteValue());
            balance.setMberId(order.getMberId());
            mberRiderBalanceService.insert(balance);
            order.setOrderStatus(OrderStatusEnum.ORDER_COMPLETE.getCode().byteValue());
            orderDao.insert(order);
        }
        temp.setOrderStatus(OrderStatusEnum.ORDER_TEMP_COMPLETE.getCode().byteValue());
        tempDao.updateById(temp);
        trackService.saveTrack(orderNo, OrderTrackEnum.ORDER_PAY_SUCCESS.getCode().byteValue());
        return temp;
    }

    @Override
    public RespData queryOrder(String token, String orderNo) {
        log.info("查询和微信支付是否成功: token==" + token + " @@ orderNo==" + orderNo);
        if (StringUtil.isEmpty(token)) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        if (StringUtil.isEmpty(orderNo)) {
            return RespData.errorMsg("订单号不能为空!");
        }

        try {
            Member m = memberDao.getByToken(token);
            if (m == null) {
                return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已经过期，请重新登录");
            }
            Order order = orderDao.getOrderByOrderNo(orderNo);
            if (order == null) {
                return RespData.errorMsg("订单号错误!");
            }

            //拼接请求参数
            String nonce_str = WXPayUtil.getRandomString(32);
            SortedMap<Object,Object> data = new TreeMap<Object,Object>();
            data.put("appid", Const.WX_PAY_APPID);
            data.put("mch_id", Const.WX_PAY_MCH_ID);
            data.put("out_trade_no", order.getOrderNo());
            data.put("nonce_str", nonce_str);
            data.put("sign", WXPayUtil.createSign("UTF-8", data));

            String requestXml = WXPayUtil.MapToXml(data);
            log.info("查询订单支付是否成功请求报文===="+requestXml);
            String result = WXPayUtil.httpsRequest(Const.WX_PAY_ORDER_QUERY,
                    Const.WX_REQUEST_METHOD_POST, requestXml);
            System.out.println("查询订单支付是否成功响应报文===="+result);
            Map<Object,Object> map = WXPayUtil.parseXml(result);

            String return_code = (String) map.get("return_code");
            String result_code = (String) map.get("result_code");
            if("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)){
                String trade_state = (String) map.get("trade_state");
                if("REFUND".equals(trade_state)){
                    return RespData.successMsg(101,"转入退款",null);
                }else if("NOTPAY".equals(trade_state)){
                    return RespData.successMsg(102,"末支付",null);
                }else if("CLOSED".equals(trade_state)){
                    return RespData.successMsg(103,"已关闭",null);
                }else if("REVOKED".equals(trade_state)){
                    return RespData.successMsg(104,"已撤销",null);
                }else if("USERPAYING".equals(trade_state)){
                    return RespData.successMsg(105,"用户支付中",null);
                }else if("PAYERROR".equals(trade_state)){
                    return RespData.successMsg(106,"支付失败",null);
                }else {
                    return RespData.successMsg("支付成功!");
                }

            }else{
                String err_code_des = (String) map.get("err_code_des");
                return RespData.successMsg(107,err_code_des,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询订单是否成功发生异常: " + e);
            return RespData.errorMsg(Const.ERROR_MSG);
        }
    }

    @Override
    public boolean wxRefund(String refundNo, String outTradeNo, int totalFee, int refundFee) {
        log.info("发起微信退款：" +
                "refundNo=="+refundNo+
                " @@ outTradeNo=="+outTradeNo+
                " @@ totalFee=="+totalFee+
                " @@ refundFee=="+refundNo);
        boolean flag = false;
        try {

            SortedMap<Object, Object> reqData = new TreeMap<Object, Object>();
            String nonce_str = WXPayUtil.getRandomString(32);
            //拼接退款请求报文
            reqData.put("appid", Const.WX_PAY_APPID);           //appid
            reqData.put("mch_id", Const.WX_PAY_MCH_ID);         //商户号
            reqData.put("nonce_str", nonce_str);                //随机字符串
            reqData.put("out_trade_no",outTradeNo);
            reqData.put("out_refund_no", refundNo);             //退款单号
            reqData.put("total_fee", totalFee);
            reqData.put("refund_fee", refundFee);
            reqData.put("refund_fee_type", "CNY");
            reqData.put("op_user_id", Const.WX_PAY_MCH_ID);
            reqData.put("device_info", "");
            String refundSign = WXPayUtil.createSign("UTF-8", reqData);
            reqData.put("sign", refundSign);
            String requestXml = WXPayUtil.MapToXml(reqData);
            log.info("请求退款报文：" + requestXml);

            String certPath = "";
            String result = WXPayUtil.wxFefundHttps(Const.WX_PAY_REFUND_URL, requestXml,certPath);
            log.info("退款响应报文=="+result);
            Map<Object,Object> resultMap = WXPayUtil.parseXml(result);
            String return_code = (String) resultMap.get("return_code");
            if("SUCCESS".equals(return_code)){
                //更新微信流水退款渠道和退款单号
                String refund_channel = (String) resultMap.get("refund_channel");//退款渠道
                if(StringUtil.isEmpty(refund_channel)){
                    refund_channel = "ORIGINAL";                                 //原路退回
                }
                String refund_id = (String) resultMap.get("refund_id");          //微信退款单号
                Map<String,Object> params = new HashMap<String, Object>();
                params.put("refundChannel", refund_channel);
                params.put("refundId", refund_id);
                params.put("orderNo", outTradeNo);
                flowDao.updateByMap(params);
                flag = true;
                log.info("微信退款结果==【"+flag+"】");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("微信退款发生异常：" + e);
            return flag;
        }
        return flag;
    }
}
