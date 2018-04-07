package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.domain.SysDict;
import com.cndinuo.page.Page;
import com.cndinuo.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    /**
     * 字典列表
     * @param params
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam Map params, Model model){
        Page<SysDict> page = sysDictService.getByPage(params);
        model.addAttribute("page",page);
        return "dict/list";
    }


    /**
     * 添加数据字典页面
     * @param sysDict
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit (SysDict sysDict,Model model){
        if(sysDict.getId() != null && sysDict.getId() > 0){
            sysDict = sysDictService.getById(sysDict.getId());
        }
        model.addAttribute("dict",sysDict);
        return "/dict/edit";
    }


    /**
     * 保存数据字典信息
     * @param sysDict
     * @return
     */
    @RequestMapping("save")
    public @ResponseBody RespData save(SysDict sysDict){
        int flag = 0;
        try {
            if (sysDict.getId() != null && sysDict.getId() >0){
                sysDict.setStatus((byte) 0);
                flag = sysDictService.updateById(sysDict);
            }else {
                sysDict.setStatus((byte) 0);
                sysDictService.insert(sysDict);
            }
            if (flag > 0 || sysDict.getId() >0){
                return RespData.successMsg(1,"操作成功",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return RespData.errorMsg("系统异常");
        }
        return RespData.errorMsg("操作失败");
    }

    /**
     * 一个删除和批量删除
     * @param params
     * @return
     */
    @RequestMapping("del")
    public @ResponseBody RespData del(@RequestParam Map params){
        try {
            if(sysDictService.deleteByMap(params)>0){
                return RespData.successMsg(1, "操作成功", null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return RespData.errorMsg("网路异常");
        }
        return RespData.errorMsg("删除失败");
    }

    /**
     * 启用和禁用
     * @return
     */
    @RequestMapping("enable")
    public @ResponseBody RespData enableAndDisable(@RequestParam Map params,Integer num){
        int flag = 0;
        String msg = num == 1 ? "禁用成功!" : "启用成功!";
        params.put("status",num);
        flag = sysDictService.updateByMap(params);
        if (flag > 0){
            return RespData.successMsg("操作成功");
        }
        return RespData.errorMsg("操作失败");
    }

}
