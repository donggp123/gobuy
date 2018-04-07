package com.cndinuo.service.impl;
import com.cndinuo.domain.MberOpinion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.MberOpinionDao;
import com.cndinuo.service.MberOpinionService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-09-08
* @author dgb
* 
*/
@Service("mberOpinionService")
public class MberOpinionServiceImpl extends AbstractService<MberOpinion, Integer> implements MberOpinionService {

	@Autowired
	private MberOpinionDao mberOpinionDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mberOpinionDao);
	}
}