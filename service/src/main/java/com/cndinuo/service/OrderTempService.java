package com.cndinuo.service;

import com.cndinuo.common.RespData;
import com.cndinuo.domain.OrderTemp;
import com.cndinuo.base.BaseService;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;


/**
* @date 2017-09-15
* @author dgb
* 
*/
public interface OrderTempService extends BaseService<OrderTemp, Integer>{

    RespData save(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException;

    RespData recur(String orderNo,String token);
}