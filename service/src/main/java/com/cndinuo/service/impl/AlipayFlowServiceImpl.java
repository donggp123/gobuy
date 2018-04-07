package com.cndinuo.service.impl;
import com.cndinuo.domain.AlipayFlow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.AlipayFlowDao;
import com.cndinuo.service.AlipayFlowService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-09-14
* @author dgb
* 
*/
@Service("alipayFlowService")
public class AlipayFlowServiceImpl extends AbstractService<AlipayFlow, Integer> implements AlipayFlowService {

	@Autowired
	private AlipayFlowDao alipayFlowDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(alipayFlowDao);
	}
}