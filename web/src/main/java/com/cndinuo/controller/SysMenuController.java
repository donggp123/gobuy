package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.SysMenu;
import com.cndinuo.domain.UserManager;
import com.cndinuo.page.Page;
import com.cndinuo.service.SysMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by sunwei on 2017/8/23.
 */
@Controller
@RequestMapping("menu")
public class SysMenuController {
    private static final Logger log = LoggerFactory.getLogger("controller");

    @Autowired
    private SysMenuService sysMenuService;


    @RequestMapping("list")
    public String list(@RequestParam Map params, Model m) {
        Page<SysMenu> page = sysMenuService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("params", params);
        return "menu/list";
    }

    @RequestMapping("edit")
    public String edit(SysMenu menu,Model m){
        List<SysMenu> menus = null;
        if (menu.getId() != null && menu.getId() > 0) {
            menu = sysMenuService.getById(menu.getId());
        }
        menus = sysMenuService.getByMap(null);
        m.addAttribute("menu", menu);
        m.addAttribute("menus", menus);
        return "menu/edit";
    }

    @RequestMapping("save")
    public @ResponseBody
    RespData save(SysMenu menu) {
        log.info("添加菜单");
        UserManager user = SessionUtils.getCurrentSysUser();
        int result = 0;
        try {
            if (menu.getId() != null && menu.getId() > 0) {
                menu.setUpdateBy(user.getId());
                result = sysMenuService.updateById(menu);
            }else{
                menu.setCreateBy(user.getId());
                menu = sysMenuService.insert(menu);
            }
            if (result > 0 || menu.getId() > 0) {
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
            if(sysMenuService.deleteByMap(params)>0){
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
