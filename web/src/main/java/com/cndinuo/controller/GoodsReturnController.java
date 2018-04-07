package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.MrhtStatusEnum;
import com.cndinuo.eunm.MrhtTypeEnum;
import com.cndinuo.eunm.PurchaseStatusEnum;
import com.cndinuo.page.Page;
import com.cndinuo.service.MerchantService;
import com.cndinuo.service.MrhtGoodsReturnService;
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
@RequestMapping("return")
public class GoodsReturnController {

    @Autowired
    private MrhtGoodsReturnService goodsReturnService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private CustomWSHandler handler;

    /**
     * 商户退货列表
     * @param params
     * @param m
     * @return
     */
    @RequestMapping("merchant")
    public String merchant(@RequestParam Map params, Model m){

        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("mrhtId", user.getId());
        Page page = goodsReturnService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("user", user);
        return "return/list";
    }

    /**
     * 供应商退货列表
     * @param params
     * @param m
     * @return
     */
    @RequestMapping("supplier")
    public String supplier(@RequestParam Map params, Model m){

        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("supplierId", user.getId());
        Page page = goodsReturnService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("user", user);
        return "return/list";
    }


    @RequestMapping("edit")
    public String edit(MrhtGoodsReturn ret, Model m) {

        if (ret.getId() != null && ret.getId() > 0) {
            ret = goodsReturnService.getById(ret.getId());
        }
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("mrhtType", MrhtTypeEnum.SUPPLIER.getCode());
        params.put("status", MrhtStatusEnum.AUDITED.getCode());
        List<Merchant> supplers = merchantService.getByMap(params);
        List<SysDict> dicts = sysDictService.getByTableAndField("mrht_goods", "is_return");

        m.addAttribute("ret", ret);
        m.addAttribute("dicts", dicts);
        m.addAttribute("supplers",supplers);
        return "return/edit";
    }


    @RequestMapping("iseditable")
    public @ResponseBody RespData isEditable(Integer id) {
        MrhtGoodsReturn ret = goodsReturnService.getById(id);
        if (ret != null && ret.getRetStatus() == PurchaseStatusEnum.STAY_AGREE.getCode().byteValue()) {
            return RespData.successMsg("可以编辑");
        }
        return RespData.errorMsg("此退货单所处状态不可以编辑");
    }

    @RequestMapping("save")
    public @ResponseBody RespData save(MrhtGoodsReturn ret) {
        UserManager user = SessionUtils.getCurrentSysUser();
        int count = 0;
        if (ret.getId() != null && ret.getId() > 0) {
            count = goodsReturnService.update(ret,user);
        }else{
            Message message = goodsReturnService.save(ret,user);
            if (message != null){
                handler.sendMessageToUser(ret.getSupplierId(),message);
            }else{
                return RespData.errorMsg("没有要退货的信息或者退货数量大于了库存数量，请您核对后在操作");
            }
        }
        if (count > 0 || ret.getId() > 0) {
            String msg = count > 0 ? "更新成功！" : "添加成功";
            return RespData.successMsg(msg);
        }
        return RespData.errorMsg("操作失败!");
    }

    /**
     * 同意或拒绝
     * @param num
     * @return
     */
    @RequestMapping("retGoods")
    public @ResponseBody RespData retGoods(Integer num,Integer id,String remark){
        try {
            UserManager user = SessionUtils.getCurrentSysUser();
            Message message = goodsReturnService.retGoods(num, id,remark,user);
            handler.sendMessageToUser(message.getTo(),message);
        } catch (Exception e) {
            return RespData.errorMsg("系统异常！");
        }

        return RespData.successMsg("操作成功！");
    }

    @RequestMapping("remark")
    public String remark(Integer id,Model m,Integer num){
        m.addAttribute("id", id);
        m.addAttribute("num", num);
        return "return/remark";
    }
}
