package com.cndinuo.service.impl;
import com.cndinuo.domain.MrhtBalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.MrhtBalanceDao;
import com.cndinuo.service.MrhtBalanceService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-09-23
* @author dgb
* 
*/
@Service("mrhtBalanceService")
public class MrhtBalanceServiceImpl extends AbstractService<MrhtBalance, Integer> implements MrhtBalanceService {

	@Autowired
	private MrhtBalanceDao mrhtBalanceDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mrhtBalanceDao);
	}
}