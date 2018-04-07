package com.cndinuo.service.impl;
import com.cndinuo.base.AbstractService;
import com.cndinuo.dao.SysSettingDao;
import com.cndinuo.domain.SysSetting;
import com.cndinuo.service.SysSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
* @date 2017-08-19
* @author dgb
* 
*/
@Service("sysSettingService")
public class SysSettingServiceImpl extends AbstractService<SysSetting, Integer> implements SysSettingService {

	@Autowired
	private SysSettingDao sysSettingDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(sysSettingDao);
	}

	@Override
	public SysSetting insert(SysSetting record) {

		record.setDeleted((byte) 0);
		return super.insert(record);
	}

	@Override
	public int updateById(SysSetting record) {
		return super.updateById(record);
	}

	@Override
	public String getByKey(String key) {
		return sysSettingDao.getByKey(key);
	}
}