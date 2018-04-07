package com.cndinuo.service.impl;
import com.cndinuo.domain.SysDict;

import com.ctc.wstx.cfg.ParsingErrorMsgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.SysDictDao;
import com.cndinuo.service.SysDictService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @date 2017-08-25
* @author dgb
* 
*/
@Service("sysDictService")
public class SysDictServiceImpl extends AbstractService<SysDict, Integer> implements SysDictService {

	@Autowired
	private SysDictDao sysDictDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(sysDictDao);
	}

	@Override
	public List<SysDict> getByTableAndField(String table, String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableName", table);
		params.put("fieldName", field);
		List<SysDict> dicts = sysDictDao.getByMap(params);
		return dicts;
	}
}