package com.cndinuo.service.impl;
import com.cndinuo.domain.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.OrderItemDao;
import com.cndinuo.service.OrderItemService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;
import java.util.List;


/**
* @date 2017-09-13
* @author dgb
* 
*/
@Service("orderItemService")
public class OrderItemServiceImpl extends AbstractService<OrderItem, Integer> implements OrderItemService {

	@Autowired
	private OrderItemDao orderItemDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(orderItemDao);
	}

	@Override
	public List<OrderItem> getOrderItemByGoods(String orderNo) {
		return orderItemDao.getOrderItemByGoods(orderNo);
	}
}