package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.MrhtStock;
import com.cndinuo.domain.UserManager;
import com.cndinuo.service.MrhtStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("stock")
public class StockController {

    @Autowired
    private MrhtStockService mrhtStockService;

    @RequestMapping("edit")
    public String editStock(@RequestParam Map params, Model m){
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("mrhtId", user.getId());
        MrhtStock mrhtStock = mrhtStockService.getOneByMap(params);
        if (mrhtStock != null && mrhtStock.getStockLimit() != null && mrhtStock.getPurNum() != null) {
            m.addAttribute("mrhtStock",mrhtStock);
        }else{
            mrhtStock = new MrhtStock();
            mrhtStock.setStockLimit(5);
            mrhtStock.setPurNum(10);
            mrhtStock.setMrhtId(user.getId());
            mrhtStock = mrhtStockService.insert(mrhtStock);
        }
        m.addAttribute("mrhtStock",mrhtStock);
        return "stock/edit";
    }

    @RequestMapping("save")
    public @ResponseBody RespData saveStock(@RequestParam Map params){
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("mrhtId", user.getId());
        int result = 0;
        try {
            result = mrhtStockService.updateByMap(params);
        }catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg("系统异常！");
        }
        if (result > 0) {
            return RespData.successMsg("操作成功!");
        }
        return RespData.errorMsg("操作失败!");
    }
}
