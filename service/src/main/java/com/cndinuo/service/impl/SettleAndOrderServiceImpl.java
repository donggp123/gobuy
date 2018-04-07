package com.cndinuo.service.impl;
import com.cndinuo.domain.SettleAndOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.SettleAndOrderDao;
import com.cndinuo.service.SettleAndOrderService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-09-23
* @author dgb
* 
*/
@Service("settleAndOrderService")
public class SettleAndOrderServiceImpl extends AbstractService<SettleAndOrder, Integer> implements SettleAndOrderService {

	@Autowired
	private SettleAndOrderDao settleAndOrderDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(settleAndOrderDao);
	}
}