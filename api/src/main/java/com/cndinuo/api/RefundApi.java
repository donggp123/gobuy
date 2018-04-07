package com.cndinuo.api;

import com.cndinuo.base.BaseApi;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.Order;
import com.cndinuo.domain.OrderItem;
import com.cndinuo.domain.OrderReturn;
import com.cndinuo.domain.SysDict;
import com.cndinuo.filter.JSON;
import com.cndinuo.page.Page;
import com.cndinuo.service.OrderReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("refund")
public class RefundApi extends BaseApi {

    @Autowired
    private OrderReturnService orderReturnService;


    @RequestMapping(value = "info", method = RequestMethod.POST)
    @JSON(type = Order.class, include = "orderTime, item")
    @JSON(type = OrderItem.class,include = "num, payPrice, goodsId, goodsName, goodsImage, payPrice")
    public RespData refundInfo(@RequestBody Map<String,String> params){
        log.info("申请退款页面（退款信息） params == " + params);
        RespData data = orderReturnService.getRefundInfo(params.get("token"),params.get("orderNo"));
        return data;
    }

    @RequestMapping(value = "reason", method = RequestMethod.POST)
    @JSON(type = SysDict.class, include = "name, value")
    public RespData refundReason(@RequestBody Map<String,String> params){
        log.info("申请退款原因 params == " + params);
        RespData data = orderReturnService.getRefundReason(params.get("token"));
        return data;
    }

    @RequestMapping(value = "apply", method = RequestMethod.POST)
    @ResponseBody
    public RespData refundApply(@RequestBody Map<String,String> params){
        log.info("申请退款 params == " + params);
        RespData data = orderReturnService.submitRefundApply(params.get("token"), params.get("orderNo"), params.get("retType"), params.get("retReason"));
        return data;
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @JSON(type = OrderReturn.class, include = "orderNo, retPrice, retNum, retTime, mrhtId, mrhtName, actualAmount, retStatus, retReason, item,statusName")
    @JSON(type = OrderItem. class, include = "goodsId, goodsName, goodsSpec, goodsImage")
    @JSON(type = Page.class,filter = "p")
    public RespData refundList(@RequestBody Map<String,String> params){
        log.info("退款列表 params == " + params);
        RespData data = orderReturnService.getRefundList(params.get("token"));
        return data;
    }

    @RequestMapping(value = "refunddeposit", method = RequestMethod.POST)
    @ResponseBody
    public RespData refundDeposit(@RequestBody Map<String, String> params) {
        log.info("骑手申请退还押金 params == " + params);
        try {
            RespData data = orderReturnService.refundDeposit(params.get("token"));
            log.info("退还押金结果：" + com.alibaba.fastjson.JSON.toJSONString(data));
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("骑手申请退还押金发生异常:" + e);
            return RespData.errorMsg(Const.ERROR_MSG);
        }
    }
}
