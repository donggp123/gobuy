package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.dao.OrderTrackDao;
import com.cndinuo.domain.OrderTrack;
import com.cndinuo.service.OrderTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;


/**
* @date 2017-09-15
* @author dgb
* 
*/
@Service("orderTrackService")
public class OrderTrackServiceImpl extends AbstractService<OrderTrack, Integer> implements OrderTrackService {

	@Autowired
	private OrderTrackDao orderTrackDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(orderTrackDao);
	}

	@Override
	public void saveTrack(String orderNo, Byte trackStatus) {
		OrderTrack track = new OrderTrack();
		track.setOrderNo(orderNo);
		track.setTrackStatus(trackStatus);
		track.setTrackTime(new Date());
		orderTrackDao.insert(track);
	}

	@Override
	public List<OrderTrack> getOrderNoByTrack(String orderNo) {
		return orderTrackDao.getOrderNoByTrack(orderNo);
	}

	@Override
	public List<OrderTrack> getLastOrderTracking(String orderNo) {
		return orderTrackDao.getLastOrderTracking(orderNo);
	}
}