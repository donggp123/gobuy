package com.cndinuo.api;

import com.cndinuo.base.BaseApi;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.Order;
import com.cndinuo.domain.OrderItem;
import com.cndinuo.domain.OrderTrack;
import com.cndinuo.filter.JSON;
import com.cndinuo.service.OrderService;
import com.cndinuo.service.OrderTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("order")
public class OrderApi extends BaseApi {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderTempService tempService;

    @RequestMapping(value = "orderview", method = RequestMethod.POST)
    @JSON(type = Order.class,include = "orderNo,mrhtId,mrhtName,orderStatus,statusName,deliveryFee,totalPrice,orderTime,payType,deliveryTime,receiptAddr,receiptAddr,storeImage")
    @JSON(type = OrderItem.class,filter = "goodsSpec,barCode")
    public RespData mrhtOrderView(@RequestBody Map<String,Object> params){
        log.info("商家接单页面 params == " + params);
        RespData data = orderService.getMrhtOrderView(params);
        log.info("返回结果"+ com.alibaba.fastjson.JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @JSON(type = Order.class, include = "mrhtId,mrhtName,item,orderStatus,totalNum,totalPrice,orderNo,orderTime")
    @JSON(type = OrderItem.class,include = "goodsId, goodsName, goodsSpec, goodsImage")
    public RespData list(@RequestBody Map<String,String> params){
        log.info("全部订单列表 params == " + params);
        RespData data = orderService.getOrderList(params.get("token"));
        return data;
    }

    @RequestMapping(value = "confirm",method = RequestMethod.POST)
    @ResponseBody
    public RespData confirmOrder(@RequestBody Map<String, Object> params) {
        log.info("用户提交订单： params==" + params);
        RespData data = null;
        try {
            data = tempService.save(params);
            log.info("订单保存结果：" + com.alibaba.fastjson.JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "recur",method = RequestMethod.POST)
    @ResponseBody
    public RespData recur(@RequestBody Map<String, String> params) {
        log.info("再来一单：params==" + params);
        RespData data = null;
        try {
            data = tempService.recur(params.get("orderNo"),params.get("token"));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "confirmreceipt", method = RequestMethod.POST)
    @ResponseBody
    public RespData confirmReceipt(@RequestBody Map<String,String> params){
        log.info("确认收货 params == " + params);
        RespData data = null;
        try {
            data = orderService.confirmReceipt(params.get("token"), params.get("orderNo"));
            log.info("确认收货结果：" + com.alibaba.fastjson.JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "tracking", method = RequestMethod.POST)
    @JSON(type = OrderTrack.class, include = "trackStatus, trackTime")
    public RespData orderTracking(@RequestBody Map<String,String> params){
        log.info("订单跟踪 params == " + params);
        RespData data = orderService.getOrderTracking(params.get("token"),params.get("orderNo"));
        return data;
    }

    @RequestMapping(value = "roborder",method = RequestMethod.POST)
    @ResponseBody
    public RespData robOrder(@RequestBody Map<String, String> params) {
        log.info("骑手开始抢单：params=="+params);
        RespData data = null;
        try {
            data = orderService.robOrder(params.get("token"), params.get("orderNo"));
            log.info("抢单结果：" + com.alibaba.fastjson.JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

}
