package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.Order;
import com.cndinuo.page.Page;

import java.util.List;
import java.util.Map;


/**
* @date 2017-09-12
* @author dgb
* 
*/
public interface OrderDao extends BaseDao<Order, Integer>{

    Order getMrhtOrderView(Map<String, Object> params);

    Order getOrderByOrderNo(String orderNo);

    Order getMrhtOrderDetails(Map<String, Object> params);

    List<Order> getOrdersByMberId(Page page);

    int updateByStatus(Map<String, Object> params);

    List<Order> getOrderByPage(Page page);

    Order getCommView(Map<String, Object> params);

    List<Order> getRiderRobByPage(Page page);

    List<Order> getMrhtByLatelyOrder(Page page);

    List<Order> getOrderByMrhtId(Map<String, Object> params);

    List<Order> getMrhtOrderAll(Page page);

    Integer getRiderOrderCountByMberId(Integer mberId);
}