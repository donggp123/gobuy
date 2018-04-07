package com.cndinuo.service.impl;
import com.cndinuo.domain.MrhtStock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.MrhtStockDao;
import com.cndinuo.service.MrhtStockService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-08-30
* @author dgb
* 
*/
@Service("MrhtStockService")
public class MrhtStockServiceImpl extends AbstractService<MrhtStock, Integer> implements MrhtStockService {

	@Autowired
	private MrhtStockDao mrhtStockDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mrhtStockDao);
	}

}