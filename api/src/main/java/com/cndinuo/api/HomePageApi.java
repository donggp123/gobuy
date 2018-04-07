package com.cndinuo.api;

import com.cndinuo.base.BaseApi;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.MrhtGoods;
import com.cndinuo.filter.JSON;
import com.cndinuo.page.Page;
import com.cndinuo.service.MerchantService;
import com.cndinuo.service.MrhtGoodsService;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("home")
public class HomePageApi extends BaseApi {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MrhtGoodsService goodsService;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public RespData list(@RequestBody Map<String, Object> params) {
        log.info("请求首页商家列表：params==" + params);
        Page<Map<String, Object>> page = merchantService.getMrhtForHomeByPage(params);
        return RespData.successMsg("", page);
    }

    @RequestMapping(value = "scan", method = RequestMethod.POST)
    @JSON(type = MrhtGoods.class,include = "id,goodsName,goodsImage,goodsSpec,sellPrice,mrhtId,mrhtName")
    public RespData scan (@RequestBody Map<String ,String> params){
        if (StringUtil.isEmpty(params.get("barCode"))) {
            return RespData.successMsg("", new ArrayList<MrhtGoods>());
        }
        List<MrhtGoods> goods = goodsService.getGoodsByBarCode(params.get("barCode"));
        return RespData.successMsg("", goods);
    }
}
