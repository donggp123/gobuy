package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.MrhtGoods;

import java.util.List;
import java.util.Map;

/**
* @date 2017-08-31
* @author dgb
* 
*/
public interface MrhtGoodsService extends BaseService<MrhtGoods, Integer> {
    /**
     *用户点击不同分类标签，返回相应分类的所以商品
     * @param token
     * @return
     */
    List<Map<String, Object>> list(String token, String mrhtId, String goodsType, String keyWord);

    List<MrhtGoods> getGoodsByBarCode(String barCode);


    /**
     * 商品详情页
     * @param mrhtId
     * @param goodsId
     * @return
     */
    Map<String, Object> getGoodsDetail(String mrhtId, String goodsId);

}