package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.SysMenu;

import java.util.List;
import java.util.Map;


/**
* @date 2017-08-19
* @author dgb
* 
*/
public interface SysMenuService extends BaseService<SysMenu, Integer> {

    List<SysMenu> getMenu(Map<String, Object> params);

    List<SysMenu> getMenuAndBtn(String roleIds);
}