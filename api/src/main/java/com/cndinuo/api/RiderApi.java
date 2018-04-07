package com.cndinuo.api;

import com.alibaba.fastjson.JSON;
import com.cndinuo.base.BaseApi;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.domain.*;
import com.cndinuo.page.Page;
import com.cndinuo.service.MberCommentService;
import com.cndinuo.service.MberRiderBalanceService;
import com.cndinuo.service.MberRiderInfoService;
import com.cndinuo.service.MemberService;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("rider")
public class RiderApi extends BaseApi {

    @Autowired
    private MberRiderInfoService riderInfoService;
    @Autowired
    private MberRiderBalanceService riderBalanceService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MberCommentService mberCommentService;

    @RequestMapping(value = "riderroblist", method = RequestMethod.POST)
    @com.cndinuo.filter.JSON(type = Order.class, include = "orderNo,mrhtName,mobile,deliveryType,deliveryTime,distance")
    public RespData riderRobList(@RequestBody Map<String, Object> params) {
        log.info("骑手抢单列表 params==" + params);
        RespData data = riderInfoService.getRiderRobList(params);
        log.info("返回结果", JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "riderorderview", method = RequestMethod.POST)
    @ResponseBody
    public RespData riderOrderView(@RequestBody Map<String, Object> params) {
        log.info("骑手已接单页面 params == " + params);
        RespData data = riderInfoService.getRiderByOrderDelivery(params);
        log.info("返回结果", JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "todayorder", method = RequestMethod.POST)
    @com.cndinuo.filter.JSON(type = OrderDelivery.class, include = "orderNo,mrhtName,trackTime,orderStatus")
    public RespData riderTodayOrder(@RequestBody Map<String, Object> params) {
        log.info("骑手今日订单 params == " + params);
        RespData data = riderInfoService.getRiderByTodayOrder(params);
        log.info("返回结果", JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "historyorder", method = RequestMethod.POST)
    @com.cndinuo.filter.JSON(type = OrderDelivery.class, include = "orderNo,mrhtName,deliveryTime,serviceTime")
    public RespData historyOrder(@RequestBody Map<String, Object> params) {
        log.info("骑手历史订单params" + params);
        RespData data = riderInfoService.getRiderHistoryByOrder(params);
        log.info("返回结果" + JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "balance", method = RequestMethod.POST)
    @com.cndinuo.filter.JSON(type = MberRiderInfo.class, include = "balance,deposit,authName,headIcon")
    public RespData balance(@RequestBody Map<String, Object> params) {
        log.info("骑手账户余额");
        RespData data = riderInfoService.getRiderByBalance(params);
        log.info("返回结果" + JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "balancedetails", method = RequestMethod.POST)
    @com.cndinuo.filter.JSON(type = MberRiderBalance.class, filter = "id,mberId")
    public RespData balanceDetails(@RequestBody Map<String, Object> params) {
        log.info("骑手账户余额明细");
        if (StringUtil.isEmpty(params.get("token") + "")) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        Member m = memberService.getByToken(params.get("token").toString());
        if (m == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录超时，请重新登录");
        }
        params.put("mberId", m.getId());
        params.put("flag", 1);
        Page<MberRiderBalance> page = riderBalanceService.getByPage(params);
        return RespData.successMsg("", page);
    }

    @RequestMapping(value = "rechargedetails")
    @com.cndinuo.filter.JSON(type = MberRiderBalance.class,filter = "id,mberId")
    public RespData rechargeDetails(@RequestBody Map<String, Object> params) {
        log.info("骑手获取充值明细： params==" + params);
        if (StringUtil.isEmpty(params.get("token") + "")) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        Member m = memberService.getByToken(params.get("token").toString());
        if (m == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录超时，请重新登录");
        }
        params.put("mberId", m.getId());
        params.put("flag", 2);
        Page<MberRiderBalance> page = riderBalanceService.getByPage(params);
        log.info("获取充值明细结果：" + JSON.toJSONString(page));
        return RespData.successMsg("", page);
    }

    @RequestMapping(value = "pickup", method = RequestMethod.POST)
    @ResponseBody
    public RespData pickUp(@RequestBody Map<String, Object> params) {
        log.info("骑手已取订单");
        RespData data = riderInfoService.updatePickUpByOrderStatusAndTrack(params);
        log.info("返回结果" + JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "sentto", method = RequestMethod.POST)
    @ResponseBody
    public RespData sentTo(@RequestBody Map<String,Object> params){
        log.info("骑手商品送到" + params);
        RespData data = riderInfoService.updateSentToByOrderStatusAndTrack(params);
        log.info("返回结果" + JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "reconsider",method = RequestMethod.POST)
    @ResponseBody
    public RespData reconsider(@RequestBody Map<String,String> params){
        log.info("骑手对订单评价提出复议 params == " + params);
        RespData data = mberCommentService.reconsider(params.get("token"),params.get("comId"));
        log.info("复议结果："+ JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "riderinfo",method = RequestMethod.POST)
    @ResponseBody
    public RespData riderInfo(@RequestBody Map<String,String> params){
        log.info("骑手获取个人信息 params==" + params);
        RespData data = riderInfoService.getRiderInfo(params.get("token"));
        log.info("获取结果："+JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "saveinfo",method = RequestMethod.POST)
    @ResponseBody
    public RespData saveInfo(@RequestBody Map<String, Object> params) {
        log.info("保存骑手个人信息 params == " + params);
        RespData data = riderInfoService.saveInfo(params);
        return data;
    }
}
