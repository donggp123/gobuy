package com.cndinuo.service;

import com.cndinuo.common.RespData;
import com.cndinuo.domain.MberFabulous;
import com.cndinuo.base.BaseService;


/**
* @date 2017-09-09
* @author dgb
* 
*/
public interface MberFabulousService extends BaseService<MberFabulous, Integer>{

    /**
     * 商品点赞
     * @param token
     * @param mrhtId
     * @param goodsId
     * @return
     */
    RespData fabulous(String token, String mrhtId, String goodsId);
}