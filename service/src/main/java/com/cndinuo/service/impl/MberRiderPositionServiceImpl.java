package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.MberRiderPositionDao;
import com.cndinuo.dao.MemberDao;
import com.cndinuo.domain.MberRiderPosition;
import com.cndinuo.domain.Member;
import com.cndinuo.service.MberRiderPositionService;
import com.cndinuo.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Map;


/**
* @date 2017-09-14
* @author dgb
* 
*/
@Service("mberRiderPositionService")
public class MberRiderPositionServiceImpl extends AbstractService<MberRiderPosition, Integer> implements MberRiderPositionService {

	@Autowired
	private MberRiderPositionDao mberRiderPositionDao;

	@Autowired
	private MemberDao memberDao;
	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mberRiderPositionDao);
	}

	@Override
	public RespData updateByPosition(Map<String, Object> params) throws Exception {
		log.info("开始修改骑手位置");
		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}

		Member member = memberDao.getByToken(params.get("token").toString());
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		try {
			MberRiderPosition riderPosition = new MberRiderPosition();
			riderPosition = (MberRiderPosition) populateBean(params, riderPosition);
			riderPosition.setMberId(member.getId());
			riderPosition.setUpdateTime(new Date());
			mberRiderPositionDao.updateById(riderPosition);
			return RespData.successMsg("位置修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("修改时发生异常" + e);
			throw e;
		}
	}

	/**
	 * Map转object对象
	 * @param map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object populateBean(Map map, Object obj) throws Exception{
		BeanUtils.populate(obj, map);
		return obj;
	}
}