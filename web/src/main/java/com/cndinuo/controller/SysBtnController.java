package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.domain.SysBtn;
import com.cndinuo.domain.SysMenu;
import com.cndinuo.page.Page;
import com.cndinuo.service.SysBtnService;
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

@Controller
@RequestMapping("btn")
public class SysBtnController {

    private static final Logger log = LoggerFactory.getLogger("controller");

    @Autowired
    private SysBtnService sysBtnService;
    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("list")
    public String list(@RequestParam Map params, Model m) {
        Page<SysBtn> page = sysBtnService.getByPage(params);
        List<SysMenu> menus = sysMenuService.getByMap(null);
        m.addAttribute("page", page);
        m.addAttribute("params", params);
        m.addAttribute("menus", menus);
        return "btn/list";
    }

    @RequestMapping("edit")
    public String edit(SysBtn btn, Model m) {
        if(btn.getId()!= null && btn.getId() > 0){
            btn = sysBtnService.getById(btn.getId());
        }
        List<SysMenu> menus = sysMenuService.getByMap(null);
        m.addAttribute("menus", menus);
        m.addAttribute("btn", btn);
        return "btn/edit";
    }

    @RequestMapping("save")
    public @ResponseBody RespData save(SysBtn btn) {
        int flag = 0;
        try {
            if (btn.getId() != null && btn.getId() > 0) {
                flag = sysBtnService.updateById(btn);
            }else {
                btn = sysBtnService.insert(btn);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg("系统发生异常!");
        }
        if (flag > 0 || btn.getId() > 0) {
            return RespData.successMsg("操作成功!");
        }
        return RespData.errorMsg("操作失败！");
    }

    @RequestMapping("del")
    public @ResponseBody RespData del(@RequestParam Map params) {
        try {
            if (sysBtnService.deleteByMap(params) > 0) {
                return RespData.successMsg("删除成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg("系统发生异常！");
        }
        return RespData.errorMsg("删除失败");
    }
}
