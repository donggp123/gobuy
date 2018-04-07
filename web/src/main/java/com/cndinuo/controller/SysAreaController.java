package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.domain.SysArea;
import com.cndinuo.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sys")
public class SysAreaController {

   @Autowired
    private SysAreaService sysAreaService;

   @RequestMapping("/area")
   public @ResponseBody RespData getAreaByParentId(String id,String parentId){
       List<SysArea> areas = sysAreaService.getByParentId(id,parentId);
       return RespData.successMsg("请求成功！",areas);
   }
}
