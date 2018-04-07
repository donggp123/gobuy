package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.SysUser;
import com.cndinuo.domain.UserManager;

import java.util.Map;


/**
* @date 2017-08-19
* @author dgb
* 
*/
public interface SysUserDao extends BaseDao<SysUser, Integer> {

    UserManager getUserByName(Map<String, Object> params);
}