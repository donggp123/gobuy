package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.MberFavoriteDao;
import com.cndinuo.domain.MberFavorite;
import com.cndinuo.domain.Member;
import com.cndinuo.service.MberFavoriteService;
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
@Service("mberFavoriteService")
public class MberFavoriteServiceImpl extends AbstractService<MberFavorite, Integer> implements MberFavoriteService {

	@Autowired
	private MberFavoriteDao mberFavoriteDao;

	@Autowired
	private MemberService memberService;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mberFavoriteDao);
	}

	@Override
	public RespData favorite(String token, String mrhtId) {
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		if (StringUtil.isEmpty(mrhtId)) {
			return RespData.errorMsg("收藏商家信息不能为空");
		}
		Member member = memberService.getByToken(token);
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已经过期，请重新登录");
		}
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("mberId", member.getId());
			params.put("mrhtId", mrhtId);
			MberFavorite favorite = this.getOneByMap(params);
			if (favorite != null) {
				mberFavoriteDao.deleteById(favorite.getId());
				log.info("取消收藏成功");
				return RespData.successMsg("取消收藏");

			}
			favorite = new MberFavorite();
			favorite.setMberId(member.getId());
			favorite.setMrhtId(Integer.parseInt(mrhtId));
			favorite.setFavTime(new Date());
			mberFavoriteDao.insert(favorite);
			log.info("收藏成功");
			return RespData.successMsg("收藏成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("收藏失败，系统异常："+e);
			throw e;
		}

	}

	@Override
	public RespData delete(Map<String, Object> params) {
		if (StringUtil.isEmpty(params.get("token").toString())) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Member member = memberService.getByToken(params.get("token").toString());
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已经过期，请重新登录");
		}
		try {
			mberFavoriteDao.deleteByMap(params);
			return RespData.successMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除失败，系统异常："+e);
			throw e;
		}
	}
}