package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.Order;
import com.cndinuo.domain.OrderDelivery;
import com.cndinuo.domain.OrderReturn;
import com.cndinuo.domain.SettleAccount;

import java.util.List;


/**
* @date 2017-09-23
* @author dgb
* 
*/
public interface SettleAccountService extends BaseService<SettleAccount, Integer>{

    void createSettleOrder();

    java.util.List<Order> getSettleOrderBySettleNo(String settleNo);

    List<OrderReturn> getSettleReturnOrderBySettleNo(String settleNo);

    List<OrderDelivery> getOrderDeliveryByMrhtId(Integer mrhtId);

    RespData settle(Integer id);
}