package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.Order;
import com.cndinuo.domain.OrderItem;
import com.cndinuo.domain.OrderReturn;
import com.cndinuo.domain.UserManager;
import com.cndinuo.page.Page;
import com.cndinuo.service.OrderItemService;
import com.cndinuo.service.OrderReturnService;
import com.cndinuo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("orderReturn")
public class ReturnController {

    @Autowired
    private OrderReturnService returnService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService itemService;

    @RequestMapping("list")
    public String list(@RequestParam Map params, Model model) {
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("mrhtId", user.getId());
        Page<OrderReturn> page = returnService.getByPage(params);
        model.addAttribute("page", page);
        return "orderReturn/list";
    }

    @RequestMapping("details")
    public String details(String orderNo,String retNo,Model model){
        Order order = orderService.getOrderByOrderNo(orderNo);
        List<OrderItem> orderItems = itemService.getOrderItemByGoods(orderNo);
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("orderNo", orderNo);
        params.put("retNo", retNo);
        OrderReturn or = returnService.getOneByMap(params);
        model.addAttribute("order", order);
        model.addAttribute("orderItem", orderItems);
        model.addAttribute("retNo", retNo);
        model.addAttribute("orderReturn", or);
        return "orderReturn/details";
    }

    @RequestMapping("remark")
    public String remark(String orderNo,Integer status,String retNo,Model model){
        model.addAttribute("status", status);
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("retNo", retNo);
        return "orderReturn/remark";
    }

    @RequestMapping("save")
    public @ResponseBody RespData save(String orderNo, String retNo ,Integer status,String remark){
        RespData data = returnService.save(orderNo,status,retNo,remark);
        return data;
    }
}
