package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.Order;
import com.cndinuo.page.Page;

import java.util.Map;


/**
* @date 2017-09-12
* @author dgb
* 
*/
public interface OrderService extends BaseService<Order, Integer>{

    RespData getMrhtOrderView(Map<String, Object> params);

    Order getOrderByOrderNo(String orderNo);

    /**
     * 全部订单列表接口
     * @param token
     * @return
     */
    RespData getOrderList(String token);

    /**
     * 确认收货接口（修改订单状态为已完成）
     * @param token
     * @param orderNo
     * @return
     */
    RespData confirmReceipt(String token, String orderNo);

    void judgeMrhtIsOrders() throws Exception;

    Page<Order> getOrderByPage(Map<String, Object> params);

    /**
     * 订单跟踪
     * @param token
     * @param orderNo
     * @return
     */
    RespData getOrderTracking(String token, String orderNo);

    RespData updateStatusByDelivery(String deliveryType, String orderNo);

    RespData getMrhtByLatelyOrder(Map<String,Object> params);

    RespData robOrder(String token, String orderNo);

    RespData getMrhtOrderAll(Map<String, Object> params);

    /**
     * 商家接单列表（用户新下的订单）
     * @param mrhtId
     * @return
     */
    RespData getHaveOrderList(String mrhtId);

    /**
     * 商家后台 商品已送达
     * @param orderNo
     * @return
     */
    RespData sendTo(String orderNo);
}
