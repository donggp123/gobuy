package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.MberInfo;

import java.util.Map;


/**
* @date 2017-09-08
* @author dgb
* 
*/
public interface MberInfoDao extends BaseDao<MberInfo, Integer> {

    MberInfo getMyProFile(Map<String, Object> params);
}