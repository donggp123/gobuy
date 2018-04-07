package com.cndinuo.controller;

import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.Order;
import com.cndinuo.domain.OrderItem;
import com.cndinuo.domain.OrderTrack;
import com.cndinuo.domain.UserManager;
import com.cndinuo.page.Page;
import com.cndinuo.service.MrhtInfoService;
import com.cndinuo.service.OrderItemService;
import com.cndinuo.service.OrderService;
import com.cndinuo.service.OrderTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private MrhtInfoService infoService;
    @Autowired
    private OrderItemService itemService;
    @Autowired
    private OrderTrackService trackService;
    /**
     * 商家后台订单列表
     * @param params
     * @param m
     * @return
     */
    @RequestMapping("mrhtOrderList")
    public String mrhtOrderList(@RequestParam Map params, Model m) {
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("mrhtId", user.getId());
        Page<Order> page = orderService.getByPage(params);
        m.addAttribute("params", params);
        m.addAttribute("page", page);
        return "order/mrht-list";
    }

    /**
     * 商家后台订单列表详情
     * @param orderNo
     * @param m
     * @return
     */
    @RequestMapping("mrhtOrderView")
    public String mrhtOrderView(String orderNo, Model m) {
        details(orderNo, m);
        return "order/mrht-view";
    }

    @RequestMapping("delivery")
    public @ResponseBody RespData delivery(String deliveryType, String orderNo) {
        try {
            RespData data = orderService.updateStatusByDelivery(deliveryType, orderNo);
            return data;
        } catch (Exception e){
            e.printStackTrace();
            return RespData.errorMsg("接单失败");
        }
    }

    @RequestMapping("list")
    public String list(@RequestParam Map params, Model model) {
        Page<Order> page = orderService.getOrderByPage(params);
        model.addAttribute("page", page);
        return "/order/list";
    }

    @RequestMapping("details")
    public String details(String orderNo,Model model){
        List<OrderTrack> track = trackService.getOrderNoByTrack(orderNo);
        Order order = orderService.getOrderByOrderNo(orderNo);
        List<OrderItem> orderItems = itemService.getOrderItemByGoods(orderNo);
        model.addAttribute("orderItem", orderItems);
        model.addAttribute("order", order);
        model.addAttribute("track", track);
        return "order/details";
    }

    @RequestMapping("sendTo")
    public @ResponseBody RespData sendTo(String orderNo){
        try {
            RespData data = orderService.sendTo(orderNo);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
    }
}
