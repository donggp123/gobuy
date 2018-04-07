package com.cndinuo.service;

import com.cndinuo.domain.AlipayWithdrawCash;
import com.cndinuo.base.BaseService;


/**
* @date 2017-10-27
* @author dgb
* 
*/
public interface AlipayWithdrawCashService extends BaseService<AlipayWithdrawCash, Integer>{

    Integer getLastId();
}