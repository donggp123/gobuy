package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.SysSetting;


/**
* @date 2017-08-19
* @author dgb
* 
*/
public interface SysSettingService extends BaseService<SysSetting, Integer> {

    String getByKey(String img_server);
}