package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.OrderDelivery;

import java.math.BigDecimal;


/**
* @date 2017-09-19
* @author dgb
* 
*/
public interface OrderDeliveryService extends BaseService<OrderDelivery, Integer>{

    int saveDelivery(String orderNo, Integer mrhtId, BigDecimal deliveryFee);
}