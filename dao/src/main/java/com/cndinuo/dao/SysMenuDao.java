package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.SysMenu;

import java.util.List;
import java.util.Map;


/**
* @date 2017-08-19
* @author dgb
* 
*/
public interface SysMenuDao extends BaseDao<SysMenu, Integer> {

    List<SysMenu> getMenu(Map<String, Object> params);

    List<SysMenu> getMenus(Map<String, Object> params);
}