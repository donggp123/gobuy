package com.cndinuo.dao;


import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.AlipayWithdrawCash;


/**
* @date 2017-10-27
* @author dgb
* 
*/
public interface AlipayWithdrawCashDao extends BaseDao<AlipayWithdrawCash, Integer>{

    Integer getLastId();
}