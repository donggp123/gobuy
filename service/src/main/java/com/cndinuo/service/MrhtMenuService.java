package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.MrhtMenu;
import com.cndinuo.domain.SysMenu;

import java.util.List;


/**
* @date 2017-08-28
* @author dgb
* 
*/
public interface MrhtMenuService extends BaseService<MrhtMenu, Integer> {

    List<SysMenu> getMrhtMenuToSysMenu(Byte type);
}