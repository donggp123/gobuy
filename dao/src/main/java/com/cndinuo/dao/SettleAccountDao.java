package com.cndinuo.dao;

import com.cndinuo.domain.Order;
import com.cndinuo.domain.OrderDelivery;
import com.cndinuo.domain.OrderReturn;
import com.cndinuo.domain.SettleAccount;
import com.cndinuo.base.BaseDao;

import java.util.List;


/**
* @date 2017-09-23
* @author dgb
* 
*/
public interface SettleAccountDao extends BaseDao<SettleAccount, Integer>{

    Integer getLastSettleNo();

    List<Order> getSettleOrderBySettleNo(String settleNo);

    List<OrderReturn> getSettleReturnOrderBySettleNo(String settleNo);

    List<OrderDelivery> getOrderDeliveryByMrhtId(Integer mrhtId);

    void updateOrderToSettled(String settleNo);

    void updateOrderReturnToSettled(String settleNo);
}