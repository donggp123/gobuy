package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.MberFavorite;

import java.util.Map;


/**
* @date 2017-09-09
* @author dgb
* 
*/
public interface MberFavoriteService extends BaseService<MberFavorite, Integer>{

    /**
     * 收藏商家
     * @param token
     * @param mrhtId
     * @return
     */
    RespData favorite(String token, String mrhtId);

    /**
     * 批量删除
     * @param params
     * @return
     */
    RespData delete(Map<String, Object> params);
}