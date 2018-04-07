package com.cndinuo.service.impl;
import com.cndinuo.domain.AlipayWithdrawCash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.AlipayWithdrawCashDao;
import com.cndinuo.service.AlipayWithdrawCashService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-10-27
* @author dgb
* 
*/
@Service("alipayWithdrawCashService")
public class AlipayWithdrawCashServiceImpl extends AbstractService<AlipayWithdrawCash, Integer> implements AlipayWithdrawCashService {

	@Autowired
	private AlipayWithdrawCashDao alipayWithdrawCashDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(alipayWithdrawCashDao);
	}

	@Override
	public Integer getLastId() {
		return alipayWithdrawCashDao.getLastId();
	}

}