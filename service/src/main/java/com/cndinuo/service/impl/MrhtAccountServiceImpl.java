package com.cndinuo.service.impl;
import com.cndinuo.domain.MrhtAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.MrhtAccountDao;
import com.cndinuo.service.MrhtAccountService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-09-23
* @author dgb
* 
*/
@Service("mrhtAccountService")
public class MrhtAccountServiceImpl extends AbstractService<MrhtAccount, Integer> implements MrhtAccountService {

	@Autowired
	private MrhtAccountDao mrhtAccountDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mrhtAccountDao);
	}
}