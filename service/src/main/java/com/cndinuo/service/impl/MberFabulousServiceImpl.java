package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.MberFabulousDao;
import com.cndinuo.domain.MberFabulous;
import com.cndinuo.domain.Member;
import com.cndinuo.service.MberFabulousService;
import com.cndinuo.service.MemberService;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
* @date 2017-09-09
* @author dgb
* 
*/
@Service("mberFabulousService")
public class MberFabulousServiceImpl extends AbstractService<MberFabulous, Integer> implements MberFabulousService {

	@Autowired
	private MberFabulousDao mberFabulousDao;

	@Autowired
	private MemberService memberService;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mberFabulousDao);
	}

	@Override
	public RespData fabulous(String token, String mrhtId, String goodsId) {
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		if (StringUtil.isEmpty(mrhtId)) {
			return RespData.errorMsg("商家信息不能为空");
		}
		if (StringUtil.isEmpty(goodsId)) {
			return RespData.errorMsg("商品信息不能为空");
		}
		Member member = memberService.getByToken(token);
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("mberId", member.getId());
			params.put("mrhtId", mrhtId);
			params.put("goodsId", goodsId);
			MberFabulous fabulous = this.getOneByMap(params);
			if (fabulous != null) {
				mberFabulousDao.deleteById(fabulous.getId());
				log.info("取消点赞成功");
				return RespData.successMsg("取消点赞");

			}
			fabulous = new MberFabulous();
			fabulous.setMberId(member.getId());
			fabulous.setMrhtId(Integer.parseInt(mrhtId));
			fabulous.setGoodsId(Integer.parseInt(goodsId));
			fabulous.setFabTime(new Date());
			mberFabulousDao.insert(fabulous);
			log.info("点赞成功");
			return RespData.successMsg("点赞成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("点赞失败，系统异常："+e);
			throw e;
		}

	}

}