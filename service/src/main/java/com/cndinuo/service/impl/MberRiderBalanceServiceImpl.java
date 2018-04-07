package com.cndinuo.service.impl;
import com.cndinuo.domain.MberRiderBalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.MberRiderBalanceDao;
import com.cndinuo.service.MberRiderBalanceService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-09-20
* @author dgb
* 
*/
@Service("mberRiderBalanceService")
public class MberRiderBalanceServiceImpl extends AbstractService<MberRiderBalance, Integer> implements MberRiderBalanceService {

	@Autowired
	private MberRiderBalanceDao mberRiderBalanceDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mberRiderBalanceDao);
	}
}