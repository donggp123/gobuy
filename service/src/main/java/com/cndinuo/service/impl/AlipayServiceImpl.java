package com.cndinuo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.*;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.*;
import com.cndinuo.service.*;
import com.cndinuo.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

@Service("alipayService")
public class AlipayServiceImpl implements AlipayService {

    private static final Logger log = LoggerFactory.getLogger("alipay");

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private AlipayFlowService flowService;
    @Autowired
    private OrderTempDao tempDao;
    @Autowired
    private OrderItemDao itemDao;
    @Autowired
    private MberRiderInfoService riderInfoService;
    @Autowired
    private OrderTrackService trackService;
    @Autowired
    private MrhtGoodsDao goodsDao;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AlipayWithdrawCashService alipayWithdrawCashService;
    @Autowired
    private MberRiderBalanceService mberRiderBalanceService;
    @Autowired
    private MerchantService merchantService;

    @Value("${alipay.notifyurl}")
    private String notifyUrl;


    @Override
    public RespData getOrderInfo(String token, String orderNo) {
        log.info("支付宝支付获取订单信息: token="+token+" @@ orderNo="+orderNo);
        if (StringUtil.isEmpty(token)) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        if (StringUtil.isEmpty(orderNo)) {
            return RespData.errorMsg("订单号不能为空!");
        }
        OrderTemp order = tempDao.getByOrderNo(orderNo);
        if (order == null) {
            return RespData.errorMsg("订单号错误!");
        }
        AlipayClient client = new DefaultAlipayClient(Const.ALIPAY_URL,Const.ALIPAY_APP_ID,
                Const.ALIPAY_APP_PRIVATE_KEY,Const.DATA_TYPE,Const.INPUT_CHARSET,Const.ALIPAY_PUBLIC_KEY,Const.SIGN_TYPE);

        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("GOBUY支付");
        model.setSubject(order.getGoodsName());
        model.setOutTradeNo(order.getOrderNo());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(StringUtil.toString(order.getTotalPrice()));
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        log.info("支付宝回调地址===" + notifyUrl);
        request.setNotifyUrl(notifyUrl);
        try {
            AlipayTradeAppPayResponse response = client.sdkExecute(request);
            Map<String,Object> orderInfo = new HashMap<String, Object>();
            orderInfo.put("orderInfo", response.getBody());
            return RespData.successMsg("获取成功!", orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取支付信息发生异常!" + e);
            return RespData.errorMsg(Const.ERROR_MSG);
        }
    }


    @Override
    public String alipayBack(Map requestParams) {
        log.info("==================支付宝支付完成回调开始===========================");
        try {
            Map<String,String> params = new HashMap<String,String>();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            log.info("支付宝回调参数：params==" + params);
            String trade_status = params.get("trade_status");
            String out_trade_no = params.get("out_trade_no");
            OrderTemp temp = tempDao.getByOrderNo(out_trade_no);
            boolean flag = AlipaySignature.rsaCheckV1(params, Const.ALIPAY_PUBLIC_KEY, Const.SIGN_TYPE);
            //获取支付宝的通知返回参数
            if(flag){//验证成功
                log.info("支付宝回调验证成功");
                if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("orderNo", out_trade_no);
                    AlipayFlow flow = flowService.getOneByMap(map);
                    if(null == flow){
                        if(saveFlow(requestParams) == 0){
                            log.info("保存支付宝支付流水发生异常");
                            return "failure";
                        }
                        this.tempToOrder(out_trade_no);
                    }
                }
                //TODO 通知商家有新的订单待测试
                if (temp.getOrderType() == OrderTypeEnum.ORDER_SHOPPING.getCode().byteValue()) {
                    merchantService.notifyMerchantNewOrder(temp.getOrderNo());
                }
                return "success";	//请不要修改或删除

            }else{//验证失败
                log.info("支付宝回调验证失败");
                //支付失败将库存还原
                List<OrderTemp> items = tempDao.getItemsByOrderNo(out_trade_no);
                for (OrderTemp item : items) {
                    MrhtGoods goods = goodsDao.getById(item.getGoodsId());
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", item.getGoodsId());
                    map.put("stockNum", goods.getStockNum() + item.getNum());
                    goodsDao.updateByMap(map);
                    item.setOrderStatus(OrderStatusEnum.ORDER_TEMP_FAIL.getCode().byteValue());
                    tempDao.updateById(item);
                }
                return "failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝支付完成回调发生异常: " + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "failure";
        }
    }

    /**
     * 临时订单表中的记录同步到订单表中
     * @param orderNo
     */
    private void tempToOrder(String orderNo){
        OrderTemp temp = tempDao.getByOrderNo(orderNo);
        Order order = new Order();
        BeanUtils.copyProperties(temp, order);
        order.setSalesPrice(temp.getTotalPrice().subtract(temp.getDeliveryFee()));
        order.setPayType(PayTypeEnum.ALIPAY.getCode().byteValue());
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
    }
    /**
     * 支付宝流水参数拼装
     * @param requestParams
     * @return
     */
    private int saveFlow(Map requestParams){
        AlipayFlow flow = new AlipayFlow();
        flow.setNotifyTime((Date) requestParams.get("notify_time"));
        flow.setNotifyType(StringUtil.toString(requestParams.get("notify_type")));
        flow.setNotifyId(StringUtil.toString(requestParams.get("notify_id")));
        flow.setSignType(StringUtil.toString(requestParams.get("sign_type")));
        flow.setSign(StringUtil.toString(requestParams.get("sign")));
        flow.setTradeNo(StringUtil.toString(requestParams.get("trade_no")));
        flow.setOutTradeNo(StringUtil.toString(requestParams.get("out_trade_no")));
        flow.setOutBizNo(StringUtil.toString(requestParams.get("out_biz_no")));
        flow.setBuyerId(StringUtil.toString(requestParams.get("buyer_id")));
        flow.setBuyerLoginId(StringUtil.toString(requestParams.get("buyer_login_id")));
        flow.setSellerId(requestParams.get("seller_id").toString());
        flow.setSellerEmail(requestParams.get("seller_email").toString());
        flow.setTradeStatus(requestParams.get("trade_status").toString());
        flow.setTotalAmount((BigDecimal) requestParams.get("total_amount"));
        flow.setReceiptAmount((BigDecimal) requestParams.get("receipt_amount"));
        flow.setInvoiceAmount((BigDecimal) requestParams.get("invoice_amount"));
        flow.setBuyerPayAmount((BigDecimal) requestParams.get("buyer_pay_amount"));
        flow.setPointAmount((BigDecimal) requestParams.get("point_amount"));
        flow.setRefundFee((BigDecimal) requestParams.get("refund_fee"));
        flow.setSubject(requestParams.get("subject").toString());
        flow.setBody(requestParams.get("body").toString());
        flow.setGmtCreate((Date) requestParams.get("gmt_create"));
        flow.setGmtPayment((Date) requestParams.get("gmt_payment"));
        flow.setGmtRefund((Date) requestParams.get("gmt_refund"));
        flow.setGmtClose((Date) requestParams.get("gmt_close"));
        flow.setFundBillList(StringUtil.toString(requestParams.get("fund_bill_list")));
        flow.setPassbackParams(StringUtil.toString(requestParams.get("passback_params")));
        flow.setVoucherDetailList(StringUtil.toString(requestParams.get("voucher_detail_list")));
        flowService.insert(flow);
        return flow.getId();
    }

    @Override
    public boolean alipayRefund(String orderNo, String tradeNo, String refundAmount, String retNo) {
        boolean flag = false;
        AlipayClient client = new DefaultAlipayClient(Const.ALIPAY_URL,
                Const.ALIPAY_APP_ID, Const.ALIPAY_APP_PRIVATE_KEY, Const.DATA_TYPE,
                Const.INPUT_CHARSET, Const.ALIPAY_PUBLIC_KEY, Const.SIGN_TYPE);

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":"+orderNo+"," +
                "\"trade_no\":"+tradeNo+"," +
                "\"refund_amount\":"+refundAmount+"," +
                "\"refund_reason\":\"正常退款\"," +
                "\"out_request_no\":"+retNo+"," +
                "\"operator_id\":\"\"," +
                "\"store_id\":\"\"," +
                "\"terminal_id\":\"\"" +
                " }");

        try {
            AlipayTradeRefundResponse response = client.execute(request);
            if(response.isSuccess()){
                flag = true;
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("outTradeNo", orderNo);
                params.put("refundFee", response.getRefundFee());
                params.put("gmtRefund",response.getGmtRefundPay());
                params.put("fundChange",response.getFundChange());
                params.put("refundDetailItemList", JSON.toJSONString(response.getRefundDetailItemList()));
                flowService.updateByMap(params);
            }else {
                flag = false;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("支付宝退款发生异常：" + e);
            return flag;
        }
        return flag;
    }


    @Override
    public RespData withdrawCash(String token, String amount) throws AlipayApiException {
        if (StringUtil.isEmpty(token)) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(), "请先登录");
        }
        if (StringUtil.isEmpty(amount) || Integer.valueOf(amount) <=0) {
            return RespData.errorMsg("请输入要提取的金额");
        }
        Member m = memberService.getByToken(token);
        if (m == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(), "登录已过期，请重新登录");
        }
        try {
            MberRiderInfo info = riderInfoService.getByMberId(m.getId());
            BigDecimal cash = new BigDecimal(amount);

            Integer lastId = alipayWithdrawCashService.getLastId();
            if (lastId == null) {
                lastId = 0;
            }
            String outBizNo = StringUtil.formatDate(new Date(),"yyyyMMddHHmmss")+(lastId+1)+m.getId();

            if (info.getBalance().compareTo(cash) == 1
                    || info.getBalance().compareTo(cash) == 0) {

                info.setBalance(info.getBalance().subtract(cash));

                AlipayClient alipayClient = new DefaultAlipayClient(Const.ALIPAY_URL,
                        Const.ALIPAY_APP_ID,Const.ALIPAY_APP_PRIVATE_KEY,Const.DATA_TYPE,Const.
                        INPUT_CHARSET,Const.ALIPAY_PUBLIC_KEY,Const.SIGN_TYPE);
                AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
                request.setBizContent("{" +
                        "\"out_biz_no\":\""+outBizNo+"\"," +
                        "\"payee_type\":\"ALIPAY_LOGONID\"," +
                        "\"payee_account\":\""+info.getAlipay()+"\"," +
                        "\"amount\":\""+amount+"\"," +
                        "\"payer_show_name\":\"蒂诺网络科技有限公司\"," +
                        "\"payee_real_name\":\""+info.getAuthName()+"\"," +
                        "\"remark\":\"骑手提现\"," +
                        "}");
                AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
                if (response.isSuccess()) {
                    if("10000".equals(response.getCode())){
                        AlipayWithdrawCash awc = new AlipayWithdrawCash();
                        awc.setAmount(amount);
                        awc.setCreateTime(new Date());
                        awc.setMberId(m.getId());
                        awc.setOutBizNo(outBizNo);
                        awc.setPayeeAccount(info.getAlipay());
                        awc.setPayeeType("ALIPAY_LOGONID");
                        awc.setPayeeRealName(info.getAuthName());
                        awc.setPayerShowName("蒂诺网络科技有限公司");
                        awc.setRemark("骑手提现");
                        alipayWithdrawCashService.insert(awc);

                        riderInfoService.updateById(info);
                        awc.setOrderId(response.getOrderId());
                        awc.setPayDate(response.getPayDate());
                        alipayWithdrawCashService.updateById(awc);
                        MberRiderBalance balance = new MberRiderBalance();
                        balance.setMberId(m.getId());
                        balance.setType(BalanceTypeEnum.Withdrawals.getCode().byteValue());
                        balance.setAmount(cash);
                        balance.setCreateTime(new Date());
                        mberRiderBalanceService.insert(balance);
                        return RespData.successMsg("提现成功");
                    }else{
                        return RespData.errorMsg(response.getSubMsg());
                    }
                }else{
                    return RespData.errorMsg(response.getMsg());
                }
            }else{
                return RespData.errorMsg("提现金额大于账户余额");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("骑手提现发生异常:" + e);
            throw e;
        }
    }
}
