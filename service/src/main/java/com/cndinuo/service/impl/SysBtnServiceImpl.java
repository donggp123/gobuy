package com.cndinuo.service.impl;
import com.cndinuo.domain.SysBtn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.SysBtnDao;
import com.cndinuo.service.SysBtnService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-08-24
* @author dgb
* 
*/
@Service("sysBtnService")
public class SysBtnServiceImpl extends AbstractService<SysBtn, Integer> implements SysBtnService {

	@Autowired
	private SysBtnDao sysBtnDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(sysBtnDao);
	}
}