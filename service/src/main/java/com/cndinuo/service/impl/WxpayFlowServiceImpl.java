package com.cndinuo.service.impl;
import com.cndinuo.domain.WxpayFlow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.WxpayFlowDao;
import com.cndinuo.service.WxpayFlowService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-09-14
* @author dgb
* 
*/
@Service("wxpayFlowService")
public class WxpayFlowServiceImpl extends AbstractService<WxpayFlow, Integer> implements WxpayFlowService {

	@Autowired
	private WxpayFlowDao wxpayFlowDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(wxpayFlowDao);
	}
}