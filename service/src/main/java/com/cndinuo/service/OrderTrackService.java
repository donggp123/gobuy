package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.OrderTrack;

import java.util.List;


/**
* @date 2017-09-15
* @author dgb
* 
*/
public interface OrderTrackService extends BaseService<OrderTrack, Integer>{

    void saveTrack(String orderNo, Byte trackStatus);

    List<OrderTrack> getOrderNoByTrack(String orderNo);

    List<OrderTrack> getLastOrderTracking(String orderNo);
}