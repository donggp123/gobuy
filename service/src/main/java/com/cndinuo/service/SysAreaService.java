package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.SysArea;

import java.util.List;


/**
* @date 2017-08-26
* @author dgb
* 
*/
public interface SysAreaService extends BaseService<SysArea, Integer> {

    List<SysArea> getByParentId(String id,String parentId);

    RespData getAreaForHome();
}