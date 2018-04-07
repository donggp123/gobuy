package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.domain.AppVersion;
import com.cndinuo.page.Page;
import com.cndinuo.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("version")
public class AppVersionController {

    @Autowired
    private AppVersionService versionService;


    @RequestMapping("list")
    public String list(@RequestParam Map params ,Model model){
        Page<AppVersion> page = versionService.getByPage(params);
        model.addAttribute("page", page);
        model.addAttribute("params", params);
        return "version/list";
    }


    @RequestMapping("edit")
    public String edit(AppVersion version,Model model){
        if (version.getId() != null && version.getId() > 0) {
            version = versionService.getById(version.getId());
        }
        model.addAttribute("version", version);
        return "version/edit";
    }


    @RequestMapping("save")
    public @ResponseBody RespData save(AppVersion version){
        int flag = 0;
        try {
            if (version.getId() != null && version.getId() > 0){
                flag = versionService.updateById(version);
            }else{
                version.setStatus((byte) 1);
                versionService.insert(version);
            }
            if (flag > 0 || version.getId() > 0){
                return RespData.successMsg("操作成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return RespData.errorMsg("系统异常");
        }
        return RespData.errorMsg("操作失败");
    }

    @RequestMapping("del")
    public @ResponseBody RespData del(@RequestParam Map params){
        int result = versionService.deleteByMap(params);
        if (result > 0){
          return RespData.successMsg("删除成功");
        }
          return RespData.errorMsg("删除失败");
    }


    /**
     * 启用和禁用
     * @return
     */
    @RequestMapping("enable")
    public @ResponseBody RespData enableAndDisable(@RequestParam Map params,AppVersion app,Integer num){
        int flag = 0;
        String msg = num == 1 ? "该版本已禁用!" : " 该版本已启用!";
        app = versionService.getById(app.getId());
        if (app.getStatus().intValue() == num){
            return RespData.errorMsg(msg);
        }
        flag = versionService.enableAndDisable(params, app, num);

        if (flag > 0){
            return RespData.successMsg("操作成功");
        }
        return RespData.errorMsg("操作失败");
    }


    @RequestMapping("isShelves")
    public @ResponseBody RespData isShelves(Integer id){
        AppVersion app = versionService.getById(id);
        if (app.getStatus() == 0){
            return RespData.errorMsg("不可编辑禁用以后才可编辑");
        }
        return RespData.successMsg("");
    }

    /**
     * 时间转换
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}
