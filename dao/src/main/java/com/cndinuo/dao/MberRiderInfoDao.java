package com.cndinuo.dao;

import com.cndinuo.domain.MberRiderInfo;
import com.cndinuo.base.BaseDao;

import java.util.List;
import java.util.Map;


/**
* @date 2017-09-14
* @author dgb
* 
*/
public interface MberRiderInfoDao extends BaseDao<MberRiderInfo, Integer>{

    MberRiderInfo getRiderByBalance(Map<String, Object> params);

    List<MberRiderInfo> getRiderByOrderNo(Map<String, Object> params);

    List<Map<String,Object>> getRiderByLngAndLat(Map<String, Object> params);

    MberRiderInfo getByMberId(Integer mberId);

    Map<String,Object> getRiderInfo(Integer mberId);
}