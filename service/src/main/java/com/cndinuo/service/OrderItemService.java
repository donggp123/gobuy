package com.cndinuo.service;

import com.cndinuo.domain.OrderItem;
import com.cndinuo.base.BaseService;

import java.util.List;


/**
* @date 2017-09-13
* @author dgb
* 
*/
public interface OrderItemService extends BaseService<OrderItem, Integer>{

    List<OrderItem> getOrderItemByGoods(String orderNo);
}