package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.PurchaseStatusEnum;
import com.cndinuo.page.Page;
import com.cndinuo.service.MrhtGoodsService;
import com.cndinuo.service.MrhtPurchaseFlowService;
import com.cndinuo.service.MrhtPurchaseService;
import com.cndinuo.service.SysDictService;
import com.cndinuo.websocket.CustomWSHandler;
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
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    private MrhtPurchaseService mrhtPurchaseService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private MrhtPurchaseFlowService mrhtPurchaseFlowService;

    @Autowired
    private MrhtGoodsService mrhtGoodsService;

    @Autowired
    private CustomWSHandler handler;

    /**
     * 供应商主页list
     * @param params
     * @param model
     * @return
     */
    @RequestMapping("home")
    public String supplierHome(@RequestParam Map params, Model model) {

        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("status",PurchaseStatusEnum.STAY_QUOTE.getCode());
        Page<MrhtPurchase> page = mrhtPurchaseService.getByPage(params);

        params.put("supplierId", user.getId());
        params.replace("status",PurchaseStatusEnum.STAY_DELIVER.getCode());
        List<MrhtPurchase> mrhtPurchase = mrhtPurchaseService.getByMap(params);
        model.addAttribute("page", page);
        model.addAttribute("params", params);
        model.addAttribute("mrhtPurchase", mrhtPurchase);
        return "merchant/supplier-home";
    }

    /**
     * 报价
     * @param params
     * @param mrhtPurchase
     * @param m
     * @return
     */
    @RequestMapping("offer")
    public String offer(@RequestParam Map params, MrhtPurchase mrhtPurchase, Model m) {

        UserManager user = SessionUtils.getCurrentSysUser();
        if (mrhtPurchase.getId() != null && mrhtPurchase.getId() > 0) {
            mrhtPurchase = mrhtPurchaseService.getById(mrhtPurchase.getId());
            params.put("mrhtId", user.getId());
            params.put("barCode", mrhtPurchase.getBarCode());
            MrhtGoods goods = mrhtGoodsService.getOneByMap(params);
            m.addAttribute("goods", goods);
        }
        List<SysDict> dicMap = sysDictService.getByTableAndField("mrht_goods","is_return");
        m.addAttribute("purchase", mrhtPurchase);
        m.addAttribute("dicMap",dicMap);
        return "purchase/offer";
    }

    @RequestMapping("isoffer")
    public @ResponseBody RespData isOffer(Integer id) {
        UserManager user = SessionUtils.getCurrentSysUser();
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("supplierId", user.getId());
        params.put("purId", id);
        params.put("isAccept", 0);
        MrhtPurchaseFlow flow = mrhtPurchaseFlowService.getOneByMap(params);
        if (flow != null) {
            return RespData.errorMsg("您已报过价");
        }
        return RespData.successMsg("可以报价");
    }
    @RequestMapping("save")
    public @ResponseBody RespData save(MrhtPurchase mrhtPurchase) {
        MrhtPurchaseFlow mrhtPurchaseFlow = new MrhtPurchaseFlow();
        if (mrhtPurchase.getId() != null && mrhtPurchase.getId() > 0) {
            mrhtPurchaseFlowService.insert(mrhtPurchaseFlow);
        }
        return RespData.successMsg("保存成功");
    }

    /**
     * 供应商主页操作采购状态
     * @param mrhtPurchase
     * @return
     */
    @RequestMapping("butt")
    public @ResponseBody RespData butt(MrhtPurchase mrhtPurchase) {
        UserManager user = SessionUtils.getCurrentSysUser();
        try {
            RespData respData = mrhtPurchaseService.butt(mrhtPurchase, user);
            Message message = (Message) respData.getData();
            handler.sendMessageToUser(message.getTo(),message);
            return respData;
        }catch (Exception e){
            e.printStackTrace();
            return RespData.errorMsg("系统异常");
        }
    }

    /**
     * 出货管理
     * @param params
     * @param m
     * @return
     */
    @RequestMapping("deliver")
    public String deliver(@RequestParam Map params, Model m) {
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("supplierId", user.getId());
        params.put("status", PurchaseStatusEnum.PUR_COMPLETE.getCode());
        Page<MrhtPurchase> page = mrhtPurchaseService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("params", params);
        return "purchase/deliver";
    }
}
