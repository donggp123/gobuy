package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.dao.SysAuthorDao;
import com.cndinuo.dao.SysBtnDao;
import com.cndinuo.dao.SysMenuDao;
import com.cndinuo.domain.SysAuthor;
import com.cndinuo.domain.SysBtn;
import com.cndinuo.domain.SysMenu;
import com.cndinuo.eunm.ResTypeEnum;
import com.cndinuo.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @date 2017-08-19
* @author dgb
* 
*/
@Service("sysMenuService")
public class SysMenuServiceImpl extends AbstractService<SysMenu, Integer> implements SysMenuService {

	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysBtnDao sysBtnDao;
	@Autowired
	private SysAuthorDao sysAuthorDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(sysMenuDao);
	}

	public List<SysMenu> getMenu(Map<String, Object> params) {
		params.put("parentId", -1);
		List<SysMenu> menus = sysMenuDao.getMenu(params);
		for (SysMenu menu : menus) {
			params.replace("parentId", menu.getId());
			List<SysMenu> child = sysMenuDao.getMenu(params);
			if (null != child && child.size() > 0) {
				params.put("resType", ResTypeEnum.RES_TYPE_BTN.getCode());
				List<SysBtn> btns = sysBtnDao.getByMap(params);
				menu.setBtns(btns);
				menu.setChilds(child);
			}
		}
		return menus;
	}

	@Override
	public List<SysMenu> getAll() {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("parentId", -1);
		List<SysMenu> menus = sysMenuDao.getMenus(params);

		return recursion(menus);
	}

	private List<SysMenu> recursion(List<SysMenu> menus) {
		Map<String, Object> params = new HashMap<String,Object>();
		for (SysMenu menu : menus) {
			params.put("parentId", menu.getId());
			List<SysMenu> child = sysMenuDao.getMenus(params);
			if (null != child && child.size() > 0) {
				menu.setChilds(child);
				recursion(child);
			}
		}
		return menus;
	}

	@Override
	public SysMenu insert(SysMenu record) {

		record.setCreateTime(new Date());
		record.setDeleted((byte) 0);
		return super.insert(record);
	}

	@Override
	public int updateById(SysMenu record) {
		record.setUpdateTime(new Date());
		return super.updateById(record);
	}

	@Override
	public List<SysMenu> getMenuAndBtn(String roleIds) {
		Map<String, Object> params = new HashMap<>();
		List<SysMenu> menus = this.getAll();
		for (SysMenu menu : menus) {
			for (SysMenu child : menu.getChilds()) {
				params.clear();
				params.put("menuId", child.getId());
				List<SysBtn> btns = sysBtnDao.getByMap(params);
				child.setBtns(btns);
			}
		}
		if (roleIds != null) {
			params.clear();
			params.put("roleIds", roleIds);
			List<SysAuthor> authors = sysAuthorDao.getByMap(params);
			for (SysAuthor author : authors) {
				for (SysMenu menu : menus) {

					if(author.getResType() == ResTypeEnum.RES_TYPE_MENU.getCode().byteValue()){
						if (menu.getId() == author.getResId()) {
							menu.setChecked(true);
						}
					}
					for (SysMenu child : menu.getChilds()) {
						if(author.getResType() == ResTypeEnum.RES_TYPE_MENU.getCode().byteValue()){
							if (child.getId() == author.getResId()) {
								child.setChecked(true);
							}
						}
						for (SysBtn btn : child.getBtns()) {
							if(author.getResType() == ResTypeEnum.RES_TYPE_BTN.getCode().byteValue()){
								if (btn.getId() == author.getResId()) {
									btn.setChecked(true);
								}
							}
						}
					}
				}
			}
		}
		return menus;
	}

}