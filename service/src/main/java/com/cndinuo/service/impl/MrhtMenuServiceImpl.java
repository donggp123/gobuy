package com.cndinuo.service.impl;
import com.cndinuo.base.AbstractService;
import com.cndinuo.dao.MrhtMenuDao;
import com.cndinuo.domain.MrhtMenu;
import com.cndinuo.domain.SysMenu;
import com.cndinuo.service.MrhtMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @date 2017-08-28
* @author dgb
* 
*/
@Service("MrhtMenuService")
public class MrhtMenuServiceImpl extends AbstractService<MrhtMenu, Integer> implements MrhtMenuService {

	@Autowired
	private MrhtMenuDao mrhtMenuDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mrhtMenuDao);
	}

	@Override
	public List<SysMenu> getMrhtMenuToSysMenu(Byte type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", -1);
		params.put("type", type);
		List<SysMenu> menus = mrhtMenuDao.getMrhtMenuToSysMenu(params);
		for (SysMenu menu : menus) {
			params.replace("parentId", menu.getId());
			List<SysMenu> childs = mrhtMenuDao.getMrhtMenuToSysMenu(params);
			menu.setChilds(childs);
		}
		return menus;
	}
}