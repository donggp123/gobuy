package com.cndinuo.controller;

import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.PurchaseStatusEnum;
import com.cndinuo.exception.CustomException;
import com.cndinuo.page.Page;
import com.cndinuo.service.MrhtGoodsService;
import com.cndinuo.service.MrhtPurchaseFlowService;
import com.cndinuo.service.MrhtPurchaseService;
import com.cndinuo.service.MessageService;
import com.cndinuo.websocket.CustomWSHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("purchase")
public class PurchaseController {

    @Autowired
    private MrhtPurchaseService mrhtPurchaseService;
    @Autowired
    private MrhtPurchaseFlowService mrhtPurchaseFlowService;
    @Autowired
    private MrhtGoodsService mrhtGoodsService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CustomWSHandler handler;

    @RequestMapping("list")
    public String list(@RequestParam Map params, Model m){
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("mrhtId", user.getId());
        Page<MrhtPurchase> page = mrhtPurchaseService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("params", params);
        return "purchase/list";
    }

    @RequestMapping("edit")
    public String edit (MrhtPurchase mrhtPurchase,Integer goodsId, Model m){
        if (goodsId != null && goodsId > 0) {
            MrhtGoods goods = mrhtGoodsService.getById(goodsId);
            m.addAttribute("goods", goods);
        }
        if (mrhtPurchase.getId() != null && mrhtPurchase.getId() > 0) {
            mrhtPurchase = mrhtPurchaseService.getById(mrhtPurchase.getId());
        }
        m.addAttribute("purchase", mrhtPurchase);
        return "purchase/edit";
    }

    @RequestMapping("iseditable")
    public @ResponseBody RespData isEditable(Integer id) {
        MrhtPurchase purchase = mrhtPurchaseService.getById(id);
        if (purchase != null && purchase.getStatus() == PurchaseStatusEnum.STAY_QUOTE.getCode().byteValue()
                || purchase.getStatus() == PurchaseStatusEnum.QUOTED_PRICE.getCode().byteValue()) {
            return RespData.successMsg("可以编辑");
        }
        return RespData.errorMsg("此采购单所处状态不可编辑");
    }

    @RequestMapping("save")
    public @ResponseBody RespData save(MrhtPurchase purchase){
        UserManager user = SessionUtils.getCurrentSysUser();
        int result = 0;
        try {
            if (purchase.getId() != null && purchase.getId() > 0){
                result = mrhtPurchaseService.updateById(purchase);
            }else {
                purchase = mrhtPurchaseService.save(purchase, user);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg("系统异常！");
        }
        if (result > 0 || purchase != null) {
            return RespData.successMsg("操作成功!");
        }
        return RespData.errorMsg("操作失败!");
    }


    @RequestMapping("view")
    public String view(Model model , Integer id){
        UserManager user = SessionUtils.getCurrentSysUser();
        MrhtPurchase merchPur = mrhtPurchaseService.getById(id);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("purId", merchPur.getId());
        Page<MrhtPurchaseFlow> page = mrhtPurchaseFlowService.getByPage(params);
        model.addAttribute("user", user);
        model.addAttribute("merchPur",merchPur);
        model.addAttribute("page", page);
        return "purchase/view";
    }

    /**
     * 确认收货
     * @param id
     * @return
     */
    @RequestMapping("confirm")
    public @ResponseBody RespData confirm(Integer id) throws Exception{
        UserManager user = SessionUtils.getCurrentSysUser();
        try {
            Message message = mrhtPurchaseService.confirms(id, user);
            handler.sendMessageToUser(message.getTo(),message);
            return RespData.successMsg("收货成功，请您给付款");
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException("系统异常");
        }
    }

    /**
     * 取消采购
     * @param id
     * @return
     */
    @RequestMapping("cancel")
    public @ResponseBody RespData cancel(Integer id){
        UserManager user = SessionUtils.getCurrentSysUser();
        try {
            List<MrhtPurchaseFlow> flow = mrhtPurchaseService.cancel(id, user);
            if(flow != null && flow.size() > 0){
                for (MrhtPurchaseFlow f: flow) {
                    Message message = new Message();
                    message.setTitle("拒绝报价");
                    message.setText(String.format(Const.MSG_TEMPLATE_CHANCEL,user.getRealName(),f.getPurId()));
                    message.setFrom(user.getId());
                    message.setFromName(user.getRealName());
                    message.setTo(f.getSupplierId());
                    message.setToName(f.getSupplierName());
                    message.setStatus((byte) 0);
                    message.setSendTime(new Date());
                    messageService.insert(message);
                    handler.sendMessageToUser(message.getTo(),message);
                }
            }
            return RespData.successMsg("取消成功");
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
