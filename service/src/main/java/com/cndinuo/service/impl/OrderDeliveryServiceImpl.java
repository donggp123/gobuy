package com.cndinuo.service.impl;
import com.cndinuo.base.AbstractService;
import com.cndinuo.dao.OrderDeliveryDao;
import com.cndinuo.domain.OrderDelivery;
import com.cndinuo.service.OrderDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;


/**
* @date 2017-09-19
* @author dgb
* 
*/
@Service("orderDeliveryService")
public class OrderDeliveryServiceImpl extends AbstractService<OrderDelivery, Integer> implements OrderDeliveryService {

	@Autowired
	private OrderDeliveryDao orderDeliveryDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(orderDeliveryDao);
	}

	@Override
	public int saveDelivery(String orderNo, Integer mrhtId, BigDecimal deliveryFee) {
		OrderDelivery delivery = new OrderDelivery();
		delivery.setOrderNo(orderNo);
		delivery.setDeliveryTime(new Date());
		delivery.setDeliveryFee(deliveryFee);
		delivery.setObjId(mrhtId);
		return orderDeliveryDao.insert(delivery);
	}
}