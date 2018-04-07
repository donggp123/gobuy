package com.cndinuo.api;

import com.cndinuo.base.BaseApi;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.service.MberFabulousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("fabulous")
public class FabulousApi extends BaseApi{

    @Autowired
    private MberFabulousService mberFabulousService;

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public RespData edit(@RequestBody Map<String, String> params) {
        log.info("商品点赞：params=="+params);
        RespData data = null;
        try {
            data = mberFabulousService.fabulous(params.get("token"), params.get("mrhtId"), params.get("goodsId"));
            log.info("商品点赞结果：" + com.alibaba.fastjson.JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        log.info("点赞结果："+data);
        return data;
    }
}
