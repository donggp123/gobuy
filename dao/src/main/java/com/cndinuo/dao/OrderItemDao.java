package com.cndinuo.dao;

import com.cndinuo.domain.OrderItem;
import com.cndinuo.base.BaseDao;

import java.util.List;
import java.util.Map;


/**
* @date 2017-09-13
* @author dgb
* 
*/
public interface OrderItemDao extends BaseDao<OrderItem, Integer>{

    List<OrderItem> getGoodsByOrderNo(Map<String, Object> params);

    List<OrderItem> getOrderItemByGoods(String orderNo);

    List<OrderItem> getRefundByOrderNo(Map<String, Object> params);

    List<OrderItem> getItemsByOrderNo(String orderNo);
}