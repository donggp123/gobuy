package com.cndinuo.service;

import com.cndinuo.common.RespData;
import com.cndinuo.domain.MberRiderPosition;
import com.cndinuo.base.BaseService;

import java.util.Map;


/**
* @date 2017-09-14
* @author dgb
* 
*/
public interface MberRiderPositionService extends BaseService<MberRiderPosition, Integer>{

    RespData updateByPosition(Map<String, Object> params) throws Exception;
}