package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.dao.SysAreaDao;
import com.cndinuo.domain.SysArea;
import com.cndinuo.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @date 2017-08-26
* @author dgb
* 
*/
@Service("sysAreaService")
public class SysAreaServiceImpl extends AbstractService<SysArea, Integer> implements SysAreaService {

	@Autowired
	private SysAreaDao sysAreaDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(sysAreaDao);
	}


	@Override
	public List<SysArea> getByParentId(String id,String parentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",id);
		params.put("parentId",parentId);
		List<SysArea> list = super.getByMap(params);
		return list;
	}

	@Override
	public RespData getAreaForHome() {
        SysArea provice = sysAreaDao.getById(150000);
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", provice.getId());
        root.put("name", provice.getName());
        root.put("lng", provice.getLng());
        root.put("lat", provice.getLat());

        SysArea city = sysAreaDao.getById(150100);
        Map<String, Object> child = new HashMap<String, Object>();
        child.put("id", city.getId());
        child.put("name", city.getName());
        child.put("lng", city.getLng());
        child.put("lat", city.getLat());
        root.put("child", child);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("parentId",city.getId());
        List<SysArea> district = sysAreaDao.getByMap(params);
        List<Map<String, Object>> sons = new ArrayList<Map<String, Object>>();
        for(SysArea area : district) {
            Map<String, Object> son = new HashMap<String, Object>();
            son.put("id", area.getId());
            son.put("name", area.getName());
            son.put("lng", area.getLng());
            son.put("lat", area.getLat());
            sons.add(son);
        }
        child.put("child", sons);

        return RespData.successMsg("",root);
	}
}