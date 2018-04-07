package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.AppVersion;

import java.util.Map;


/**
* @date 2017-09-08
* @author dgb
* 
*/
public interface AppVersionService extends BaseService<AppVersion, Integer> {

    int enableAndDisable(Map<String,Object> params, AppVersion app, Integer num);
}