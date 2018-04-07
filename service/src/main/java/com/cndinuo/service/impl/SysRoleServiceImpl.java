package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.dao.SysAuthorDao;
import com.cndinuo.dao.SysRoleDao;
import com.cndinuo.domain.SysAuthor;
import com.cndinuo.domain.SysRole;
import com.cndinuo.eunm.ResTypeEnum;
import com.cndinuo.service.SysRoleService;
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
@Service("sysRoleService")
public class SysRoleServiceImpl extends AbstractService<SysRole, Integer> implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysAuthorDao sysAuthorDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(sysRoleDao);
	}

	@Override
	public int updateById(SysRole record) {
		record.setUpdateTime(new Date());
		try {
			if (record != null && record.getId() > 0) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("roleId", record.getId());
				sysAuthorDao.deleteByMap(params);
			}
			super.updateById(record);
			return saveAuthor(record);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public SysRole insert(SysRole record) {
		record.setCreateTime(new Date());
		record.setDeleted((byte) 0);
		try {
			record = super.insert(record);
			saveAuthor(record);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return record;
	}


	private int saveAuthor(SysRole role) {
		int count = 0;
		if (StringUtil.isNotEmpty(role.getMenuIds())) {
			SysAuthor author = new SysAuthor();
			author.setRoleId(role.getId());
			String[] menuIds = role.getMenuIds().split(",");
			for(String id : menuIds){
				author.setResId(Integer.parseInt(id));
				author.setResType(ResTypeEnum.RES_TYPE_MENU.getCode().byteValue());
				sysAuthorDao.insert(author);
				count ++;
			}
		}
		if (StringUtil.isNotEmpty(role.getBtnIds())) {
			String[] btnIds = role.getBtnIds().split(",");
			SysAuthor author = new SysAuthor();
			author.setRoleId(role.getId());
			for (String btn : btnIds) {
				author.setResId(Integer.parseInt(btn));
				author.setResType(ResTypeEnum.RES_TYPE_BTN.getCode().byteValue());
				sysAuthorDao.insert(author);
				count ++;
			}
		}
		return count;
	}

	@Override
	public int deleteByMap(Map<String, Object> params) {
		params.put("roleIds", params.get("ids"));
		sysAuthorDao.deleteByMap(params);
		return super.deleteByMap(params);
	}
}