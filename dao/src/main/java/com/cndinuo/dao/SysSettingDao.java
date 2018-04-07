package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.SysSetting;


/**
* @date 2017-08-19
* @author dgb
* 
*/
public interface SysSettingDao extends BaseDao<SysSetting, Integer> {

    String getByKey(String key);
}