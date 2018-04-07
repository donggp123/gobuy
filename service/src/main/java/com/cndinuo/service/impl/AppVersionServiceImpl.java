package com.cndinuo.service.impl;
import com.cndinuo.domain.AppVersion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.AppVersionDao;
import com.cndinuo.service.AppVersionService;
import com.cndinuo.base.AbstractService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Map;


/**
* @date 2017-09-08
* @author dgb
*/
@Service("appVersionService")
public class AppVersionServiceImpl extends AbstractService<AppVersion, Integer> implements AppVersionService {

	@Autowired
	private AppVersionDao appVersionDao;

	@Autowired
	private AppVersionDao versionDao;
	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(appVersionDao);
	}

	@Transactional
	@Override
	public int enableAndDisable(Map<String, Object> params, AppVersion app, Integer num) {
		try {
			String msg = num == 1 ? "禁用成功!" : "启用成功!";
			if (num == 0){
				params.clear();
				params.put("status",1);
				params.put("device", app.getDevice());
				versionDao.updateByMap(params);
			}
			params.clear();
			params.put("status",num);
			params.put("id", app.getId());
			return versionDao.updateByMap(params);
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}