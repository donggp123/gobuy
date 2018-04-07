package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.MrhtMenu;
import com.cndinuo.domain.SysMenu;

import java.util.List;
import java.util.Map;


/**
* @date 2017-08-28
* @author dgb
* 
*/
public interface MrhtMenuDao extends BaseDao<MrhtMenu, Integer> {

    List<SysMenu> getMrhtMenuToSysMenu(Map<String,Object> params);
}