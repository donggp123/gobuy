package com.cndinuo.api;

import com.cndinuo.base.BaseApi;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.OrderStatusEnum;
import com.cndinuo.filter.JSON;
import com.cndinuo.service.*;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("mrht")
public class MrhtApi extends BaseApi{

    @Autowired
    private MrhtGoodsClassService goodsClassService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MberFavoriteService favoriteService;
    @Autowired
    private MrhtInfoService infoService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public RespData login(@RequestBody Map<String, String> params) {
        log.info("商家开始登录: params==" + params);
        RespData data = merchantService.getLoginForApp(params.get("loginName"), params.get("password"));
        log.info("登录结果：" + com.alibaba.fastjson.JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "orders",method = RequestMethod.POST)
    @ResponseBody
    public RespData orders(@RequestBody Map<String, Object> params) {
        log.info("商家开始接单: params==" + params);
        RespData data = merchantService.orders(params);
        log.info("接单结果：" + com.alibaba.fastjson.JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "clazz",method = RequestMethod.POST)
    @JSON(type = MrhtGoodsClass.class,include = "code,name")
    public RespData clazz(){
        log.info("返回商户商品类别");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("parCode", 0);
        List<MrhtGoodsClass> goodsClass = goodsClassService.getByMap(params);
        return RespData.successMsg("", goodsClass);
    }


    @RequestMapping(value = "details",method = RequestMethod.POST)
    @ResponseBody
    public RespData details(@RequestBody Map<String,Object> params){
        log.info("返回商家详情 params == " + params);
        if (StringUtil.isEmpty(params.get("id")+"")) {
            return RespData.errorMsg("商家id不能为空");
        }
        if (StringUtil.isEmpty(params.get("lng")+"") && StringUtil.isEmpty(params.get("lat")+"")){
            return RespData.errorMsg("获取地理位置失败");
        }
        Map<String,Object> map = merchantService.getByDistanceAndTime(params);
        //判断用户是否收藏商家
        if (StringUtil.isNotEmpty(params.get("token").toString())) {
            Member member = memberService.getByToken(params.get("token").toString());
            if (member != null) {
                params.put("mberId", member.getId());
                MberFavorite favorite = favoriteService.getOneByMap(params);
                if (favorite != null) {
                    map.put("isFavorite", 1);
                }
            }else{
                map.put("isFavorite", 0);
            }
        }else{
            map.put("isFavorite", 0);
        }
        map.put("storeImage", map.get("storeImage"));
        return RespData.successMsg("", map);
    }


    @RequestMapping(value = "info",method = RequestMethod.POST)
    @JSON(type = MrhtInfo.class,include = "address,mobile,busCert,officeTime")
    public RespData info(@RequestBody Map<String,String> params){
        if (StringUtil.isEmpty(params.get("mrhtId"))) {
            return RespData.errorMsg("商家id不能为空");
        }
        MrhtInfo mrht = infoService.getByMrhtId(Integer.parseInt(params.get("mrhtId")));
        return RespData.successMsg("", mrht);
    }

    @RequestMapping(value = "latelyorder",method = RequestMethod.POST)
    @JSON(type = OrderItem.class,include = "num,goodsName")
    @JSON(type = Order.class,include = "orderStatus,orderNo,receiptName,receiptMobile,items")
    public RespData latelyOrder(@RequestBody Map<String,Object> params){
        log.info("商家最近3天订单");
        params.put("orderStatus", OrderStatusEnum.ORDER_COMPLETE.getCode());
        RespData data = orderService.getMrhtByLatelyOrder(params);
        log.info("返回结果" + com.alibaba.fastjson.JSON.toJSONString(data));
        return data;
    }

    @RequestMapping("mrhtorderall")
    @JSON(type = OrderItem.class,include = "num,goodsName")
    @JSON(type = Order.class,include = "orderStatus,orderNo,receiptName,receiptMobile,items")
    public RespData mrhtOrderAll(@RequestBody Map<String,Object> params){
        log.info("请求商户的所有订单");
        params.put("orderStatus", OrderStatusEnum.ORDER_COMPLETE.getCode());
        RespData data = orderService.getMrhtOrderAll(params);
        log.info("返回结果" + com.alibaba.fastjson.JSON.toJSONString(data));
        return data;
    }

    @RequestMapping("orderlistview")
    @JSON(type = OrderItem.class,include = "num,goodsName")
    @JSON(type = Order.class,include = "orderStatus,orderNo,receiptName,receiptMobile,items")
    public RespData orderListView(@RequestBody Map<String ,Object> params){
        log.info("商家接单列表");
        params.put("orderStatus", OrderStatusEnum.STAY_MERCHANT_ORDERS.getCode());
        RespData data = orderService.getMrhtOrderAll(params);
        log.info("返回结果" + com.alibaba.fastjson.JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "haveorderlist", method = RequestMethod.POST)
    @JSON(type = Order.class, include = "orderNo, receiptName, receiptMobile, items, riderInfos")
    @JSON(type = OrderItem.class, include = "goodsName")
    @JSON(type = MberRiderInfo.class, include = "authName, authPhone")
    public RespData haveOrderList(@RequestBody Map<String,String> params) {
        log.info("商家已接订单");
        if (StringUtil.isEmpty(params.get("mrhtId"))) {
            return RespData.errorMsg("商家id不能为空");
        }
        RespData data = orderService.getHaveOrderList(params.get("mrhtId"));
        return data;
    }

    @RequestMapping(value = "devicetoken",method = RequestMethod.POST)
    @ResponseBody
    public RespData uploadDeviceToken(@RequestBody Map<String, Object> params) {
        log.info("商家开始上传设备号 params==" + params);
        RespData data = merchantService.uploadDeviceToken(params);
        return data;
    }
}
