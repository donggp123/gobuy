package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.MrhtGoods;

import java.util.List;
import java.util.Map;


/**
* @date 2017-08-31
* @author dgb
* 
*/
public interface MrhtGoodsDao extends BaseDao<MrhtGoods, Integer> {

    List<Map<String,Object>> getListForGoods(Map<String, Object> params);

    List<MrhtGoods> getGoodsByBarCode(String barCode);

    Map<String, Object> getGoodsDetail(Map<String, Object> params);

}