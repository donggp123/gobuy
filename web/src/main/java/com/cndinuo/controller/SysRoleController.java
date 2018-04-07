package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.SysMenu;
import com.cndinuo.domain.SysRole;
import com.cndinuo.domain.UserManager;
import com.cndinuo.page.Page;
import com.cndinuo.service.SysMenuService;
import com.cndinuo.service.SysRoleService;
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

@Controller
@RequestMapping("role")
public class SysRoleController {

    private static final Logger log = LoggerFactory.getLogger("controller");
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 角色列表
     * @param params
     * @param m
     * @return
     */
    @RequestMapping("list")
    public String list(@RequestParam Map params, Model m) {
        Page<SysRole> page = sysRoleService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("params", params);
        return "role/list";
    }


    @RequestMapping("edit")
    public String edit(SysRole role,Model m){
        List<SysMenu> menus = null;
        if (role.getId() != null && role.getId() > 0) {
            role = sysRoleService.getById(role.getId());
        }
        menus = sysMenuService.getMenuAndBtn(role.getId() != null ? role.getId().toString() : null);
        m.addAttribute("role", role);
        m.addAttribute("menus", menus);
        return "role/edit";
    }

    @RequestMapping("save")
    public @ResponseBody RespData save(SysRole role) {
        log.info("添加角色");
        UserManager user = SessionUtils.getCurrentSysUser();
        int result = 0;
        try {
            if (role.getId() != null && role.getId() > 0) {
                role.setUpdateBy(user.getId());
                result = sysRoleService.updateById(role);
            }else{
                role.setCreateBy(user.getId());
                role = sysRoleService.insert(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("系统异常！" + e);
            return RespData.errorMsg("系统异常！");
        }
        if (result > 0 || role != null) {
            return RespData.successMsg("操作成功!");
        }
        return RespData.errorMsg("操作失败!");
    }

    @RequestMapping("del")
    public @ResponseBody RespData del(@RequestParam Map params){
        log.info("删除角色 params=【"+params+"】");
        try {
            if(sysRoleService.deleteByMap(params)>0){
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
