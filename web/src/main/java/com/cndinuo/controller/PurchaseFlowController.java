package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.MrhtPurchaseFlow;
import com.cndinuo.domain.Message;
import com.cndinuo.domain.UserManager;
import com.cndinuo.page.Page;
import com.cndinuo.service.MrhtPurchaseFlowService;
import com.cndinuo.websocket.CustomWSHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("flow")
public class PurchaseFlowController {

    @Autowired
    private MrhtPurchaseFlowService purchaseFlowService;

    @Autowired
    private CustomWSHandler handler;

    @Autowired
    private MrhtPurchaseFlowService mrhtPurchaseFlowService;




    @RequestMapping("save")
    public @ResponseBody RespData save(MrhtPurchaseFlow flow) {
        UserManager user = SessionUtils.getCurrentSysUser();

        try {
            Message message = purchaseFlowService.save(flow,user);
            handler.sendMessageToUser(message.getTo(),message);
            return RespData.successMsg("报价成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.successMsg("系统异常");
        }
    }
    /**
     * 查看商户采购流水
     * @param params
     * @param model
     * @return
     */
    @RequestMapping("look")
    public String look(@RequestParam Map params, Model model) {
        params.put("purId", params.get("purId"));
        params.put("isAccept", 0);
        Page<MrhtPurchaseFlow> pages = mrhtPurchaseFlowService.getByPage(params);
        model.addAttribute("page", pages);
        model.addAttribute("params",params);
        return "/purchase/look";
    }

    /**
     * 确认报价
     * @param flow
     * @return
     */
    @RequestMapping("quote")
    public @ResponseBody RespData quote(MrhtPurchaseFlow flow){
        UserManager user = SessionUtils.getCurrentSysUser();
        try {
            RespData respData = mrhtPurchaseFlowService.quote(flow, user);
            Message message = (Message) respData.getData();
            handler.sendMessageToUser(message.getTo(),message);
            return respData;
        }catch (Exception e){
            e.printStackTrace();
            return RespData.errorMsg("系统异常");
        }
    }

}
