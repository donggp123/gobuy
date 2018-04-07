package com.cndinuo.api;

import com.alibaba.fastjson.JSON;
import com.cndinuo.base.BaseApi;
import com.cndinuo.common.RespData;
import com.cndinuo.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("area")
public class AreaApi extends BaseApi {

    @Autowired
    private SysAreaService areaService;

    @RequestMapping(value = "home",method = RequestMethod.POST)
    public RespData home() {
        log.info("获取首页省市区");
        RespData data = areaService.getAreaForHome();
        log.info("获取结果："+ JSON.toJSONString(data));
        return data;
    }
}
