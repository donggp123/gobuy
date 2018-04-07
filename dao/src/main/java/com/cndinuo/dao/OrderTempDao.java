package com.cndinuo.dao;

import com.cndinuo.domain.OrderTemp;
import com.cndinuo.base.BaseDao;

import java.util.List;


/**
* @date 2017-09-15
* @author dgb
* 
*/
public interface OrderTempDao extends BaseDao<OrderTemp, Integer>{

    Integer getLastOrderId();

    OrderTemp getByOrderNo(String orderNo);

    List<OrderTemp> getItemsByOrderNo(String orderNo);
}