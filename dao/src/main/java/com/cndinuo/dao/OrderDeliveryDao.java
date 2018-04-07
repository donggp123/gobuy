package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.OrderDelivery;
import com.cndinuo.page.Page;

import java.util.List;
import java.util.Map;


/**
* @date 2017-09-19
* @author dgb
* 
*/
public interface OrderDeliveryDao extends BaseDao<OrderDelivery, Integer>{

    List<OrderDelivery> getRiderByOrderDelivery(Map<String, Object> params);

    List<OrderDelivery> getRiderByTodayOrder(Page page);

    List<OrderDelivery> getRiderHistoryByOrder(Page page);
}