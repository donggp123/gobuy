package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.SysDict;

import java.util.List;


/**
* @date 2017-08-25
* @author dgb
* 
*/
public interface SysDictService extends BaseService<SysDict, Integer> {

    List<SysDict> getByTableAndField(String table, String field);
}