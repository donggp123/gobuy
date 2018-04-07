package com.cndinuo.api;

import com.cndinuo.base.BaseApi;
import com.cndinuo.common.RespData;
import com.cndinuo.service.MrhtGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("goods")
public class GoodsApi extends BaseApi {

    @Autowired
    private MrhtGoodsService mrhtGoodsService;

    @RequestMapping(value = "list",method = RequestMethod.POST)
    public RespData list(@RequestBody Map<String, String> params) {
        log.info("返回分类商品：params==" + params);
        List<Map<String, Object>> list = mrhtGoodsService.list(params.get("token"), params.get("mrhtId"), params.get("goodsType"), params.get("keyWord"));
        return RespData.successMsg("", list);
    }


    @RequestMapping(value = "detail",method = RequestMethod.POST)
    public RespData detail(@RequestBody Map<String, String> params) {
        log.info("商品详情：params=="+params);
        Map<String, Object> goods = mrhtGoodsService.getGoodsDetail(params.get("mrhtId"), params.get("goodsId"));
        return RespData.successMsg("", goods);
    }

}
