package com.cndinuo.service.impl;
import com.cndinuo.domain.MberInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.MberInfoDao;
import com.cndinuo.service.MberInfoService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-09-08
* @author dgb
* 
*/
@Service("mberInfoService")
public class MberInfoServiceImpl extends AbstractService<MberInfo, Integer> implements MberInfoService {

	@Autowired
	private MberInfoDao mberInfoDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mberInfoDao);
	}
}