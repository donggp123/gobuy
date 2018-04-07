package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.MberReceipt;

import java.util.Map;


/**
* @date 2017-09-09
* @author dgb
* 
*/
public interface MberReceiptService extends BaseService<MberReceipt, Integer> {

    RespData add(Map<String, Object> params) throws Exception;

    RespData edit(Map<String, Object> params);

    RespData save(Map<String, Object> params) throws Exception;

    RespData del(Map<String, Object> params);

}