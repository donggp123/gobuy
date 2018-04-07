package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.OrderTrack;

import java.util.List;


/**
* @date 2017-09-15
* @author dgb
* 
*/
public interface OrderTrackDao extends BaseDao<OrderTrack, Integer>{

    List<OrderTrack> getOrderNoByTrack(String orderNo);

    List<OrderTrack> getLastOrderTracking(String orderNo);
}