package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.Merchant;
import com.cndinuo.page.Page;

import java.util.List;
import java.util.Map;


/**
* @date 2017-08-28
* @author dgb
* 
*/
public interface MerchantDao extends BaseDao<Merchant, Integer> {

    int updateByStatus(Merchant merchant);

    Merchant getByPurId(Integer purId);

    List<Map<String,Object>> getMrhtForHomeByPage(Page<Map<String, Object>> page);

    Map<String, Object> getByDistanceAndTime(Map<String, Object> params);
}