package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.SettleStatusEnum;
import com.cndinuo.page.Page;
import com.cndinuo.service.SettleAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 结算管理
 */
@Controller
@RequestMapping("settle")
public class SettleController {

    @Autowired
    private SettleAccountService accountService;

    @RequestMapping("list")
    public String list(@RequestParam Map params, Model m){
        Page<SettleAccount> page = accountService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("params", params);
        return "settle/list";
    }

    @RequestMapping("toSettle")
    public String toSettle(Integer id,Model m) {
        SettleAccount settle = accountService.getById(id);
        settle.setSettleStatus(SettleStatusEnum.SETTLE_ING.getCode().byteValue());
        accountService.updateById(settle);
        List<Order> orders = accountService.getSettleOrderBySettleNo(settle.getSettleNo());
        List<OrderReturn> returns = accountService.getSettleReturnOrderBySettleNo(settle.getSettleNo());
        List<OrderDelivery> deliveries = accountService.getOrderDeliveryByMrhtId(settle.getMrhtId());
        m.addAttribute("settle", settle);
        m.addAttribute("orders", orders);
        m.addAttribute("returns", returns);
        m.addAttribute("deliveries", deliveries);
        return "settle/settle";
    }

    @RequestMapping("view")
    public String view(Integer id,Model m) {
        SettleAccount settle = accountService.getById(id);
        List<Order> orders = accountService.getSettleOrderBySettleNo(settle.getSettleNo());
        List<OrderReturn> returns = accountService.getSettleReturnOrderBySettleNo(settle.getSettleNo());
        List<OrderDelivery> deliveries = accountService.getOrderDeliveryByMrhtId(settle.getMrhtId());
        m.addAttribute("settle", settle);
        m.addAttribute("orders", orders);
        m.addAttribute("returns", returns);
        m.addAttribute("deliveries", deliveries);
        return "settle/view";
    }

    @RequestMapping("settle")
    @ResponseBody
    public RespData settle(Integer id) {
        RespData data = accountService.settle(id);
        return data;
    }

    /**
     * 商家结算列表
     * @param params
     * @param m
     * @return
     */
    @RequestMapping("mrhtList")
    public String mrhtList(@RequestParam Map params, Model m) {
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("mrhtId", user.getId());
        Page<SettleAccount> page = accountService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("params", params);
        return "settle/mrht-list";
    }
}
