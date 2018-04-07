package com.cndinuo.service.impl;
import com.cndinuo.domain.SysAuthor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.SysAuthorDao;
import com.cndinuo.service.SysAuthorService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-08-24
* @author dgb
* 
*/
@Service("sysAuthorService")
public class SysAuthorServiceImpl extends AbstractService<SysAuthor, Integer> implements SysAuthorService {

	@Autowired
	private SysAuthorDao sysAuthorDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(sysAuthorDao);
	}
}