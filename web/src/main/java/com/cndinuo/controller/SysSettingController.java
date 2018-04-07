package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.domain.SysSetting;
import com.cndinuo.page.Page;
import com.cndinuo.service.SysSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by sunwei on 2017/8/25.
 */

@Controller
@RequestMapping("setting")
public class SysSettingController {
    private static final Logger log = LoggerFactory.getLogger("controller");

    @Autowired
    private SysSettingService sysSettingService;

    @RequestMapping("list")
    public String list(@RequestParam Map params, Model m){
        Page<SysSetting> page = sysSettingService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("params", params);
        return "setting/list";
    }

    @RequestMapping("edit")
    public String edit(SysSetting setting, Model m){
        if (setting.getId() != null && setting.getId() > 0) {
            setting = sysSettingService.getById(setting.getId());
        }
        m.addAttribute("setting", setting);
        return "setting/edit";
    }

    @RequestMapping("save")
    public @ResponseBody RespData save(SysSetting setting) {
        log.info("添加菜单");
        int result = 0;
        try {
            if (setting.getId() != null && setting.getId() > 0) {
                result = sysSettingService.updateById(setting);
            }else{
                setting = sysSettingService.insert(setting);
            }
            if (result > 0 || setting.getId() > 0) {
                return RespData.successMsg("操作成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("系统异常！" + e);
            return RespData.errorMsg("系统异常！");
        }
        return RespData.errorMsg("操作失败!");
    }

    @RequestMapping("del")
    public @ResponseBody RespData del(@RequestParam Map params){
        log.info("删除菜单 params=【"+params+"】");
        try {
            if(sysSettingService.deleteByMap(params)>0){
                return RespData.successMsg("已删除！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("系统异常！" + e);
            return RespData.errorMsg("系统异常！");
        }
        return RespData.errorMsg("删除失败!");

    }
}
