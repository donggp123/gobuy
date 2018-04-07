package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.Const;
import com.cndinuo.dao.SysUserDao;
import com.cndinuo.domain.SysUser;
import com.cndinuo.domain.UserManager;
import com.cndinuo.service.SysUserService;
import com.cndinuo.utils.Base64;
import com.cndinuo.utils.MD5;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
* @date 2017-08-19
* @author dgb
* 
*/
@Service("sysUserService")
public class SysUserServiceImpl extends AbstractService<SysUser, Integer> implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(sysUserDao);
	}

	@Override
	public int updateById(SysUser record) {
		record.setUpdateTime(new Date());
		return super.updateById(record);
	}

	@Override
	public SysUser insert(SysUser record) {
		String password = MD5.encode(Const.INIT_PASSWORD);
		String saltSource = Base64.encode((record.getLoginName()+password).getBytes()).replace("\n","");
		record.setPassword(StringUtil.saltCode(saltSource,password));
		record.setCreateTime(new Date());
		record.setDeleted((byte) 0);
		record.setSalt(saltSource);
		return super.insert(record);
	}

	public boolean changePwd(SysUser user) {
		String password = MD5.encode(user.getPassword());
		String saltSource = Base64.encode((user.getLoginName()+password).getBytes()).replace("\n","");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("password", StringUtil.saltCode(saltSource,password));
		params.put("salt",saltSource);
		int result = sysUserDao.updateByMap(params);
		return result > 0 ? true : false;
	}

	@Override
	public UserManager getUserByName(Map<String, Object> params) {
		return sysUserDao.getUserByName(params);
	}
}