package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.dao.MrhtInfoDao;
import com.cndinuo.domain.MrhtInfo;
import com.cndinuo.service.MrhtInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
* @date 2017-08-28
* @author dgb
* 
*/
@Service("mrhtInfoService")
public class MrhtInfoServiceImpl extends AbstractService<MrhtInfo, Integer> implements MrhtInfoService {

	@Autowired
	private MrhtInfoDao mrhtInfoDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mrhtInfoDao);
	}


	@Override
	public MrhtInfo getByMrhtId(Integer mrhtId) {
		MrhtInfo mrhtInfo = mrhtInfoDao.getByMrhtId(mrhtId);
		return mrhtInfo;
	}

	@Override
	public boolean updateByMrhtAccount(MrhtInfo mrhtInfo) {
		int num = mrhtInfoDao.updateByMrhtAccount(mrhtInfo);
		return num > 0 ? true : false;
	}

	@Override
	public boolean updateByStoreImage(MrhtInfo mrhtInfo) {
		int result = mrhtInfoDao.updateByStoreImage(mrhtInfo);
		return result > 0 ? true : false;
	}
}