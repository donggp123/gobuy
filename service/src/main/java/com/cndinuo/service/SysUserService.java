package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.SysUser;
import com.cndinuo.domain.UserManager;

import java.util.Map;


/**
* @date 2017-08-19
* @author dgb
* 
*/
public interface SysUserService extends BaseService<SysUser, Integer> {

    boolean changePwd(SysUser user);

    UserManager getUserByName(Map<String, Object> params);
}