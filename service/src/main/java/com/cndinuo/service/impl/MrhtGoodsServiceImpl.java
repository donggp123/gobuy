package com.cndinuo.service.impl;
import com.cndinuo.base.AbstractService;
import com.cndinuo.dao.MrhtGoodsDao;
import com.cndinuo.domain.Member;
import com.cndinuo.domain.MrhtGoods;
import com.cndinuo.service.MemberService;
import com.cndinuo.service.MrhtGoodsService;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @date 2017-08-31
* @author dgb
* 
*/
@Service("merchantGoodsService")
public class MrhtGoodsServiceImpl extends AbstractService<MrhtGoods, Integer> implements MrhtGoodsService {

	@Autowired
	private MrhtGoodsDao mrhtGoodsDao;

	@Autowired
	private MemberService memberService;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mrhtGoodsDao);
	}

	@Override
	public List<MrhtGoods> getGoodsByBarCode(String barCode) {
		List<MrhtGoods> list = mrhtGoodsDao.getGoodsByBarCode(barCode);
		return list;
	}

	@Override
	public List<Map<String, Object>> list(String token, String mrhtId, String goodsType, String keyWord) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(token)) {
			Member member = memberService.getByToken(token);
			if (member != null) {
				log.info("已登录");
				params.put("mberId", member.getId());
			}
		}
		if (StringUtil.isNotEmpty(goodsType)) {
			params.put("goodsType", goodsType);
		}
		if (StringUtil.isNotEmpty(keyWord)) {
			params.put("keyWord", keyWord);
		}
		params.put("mrhtId", mrhtId);
		List<Map<String, Object>> list = mrhtGoodsDao.getListForGoods(params);
		return list;
	}

	@Override
	public Map<String, Object> getGoodsDetail(String mrhtId, String goodsId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mrhtId", mrhtId);
		params.put("goodsId", goodsId);
		return mrhtGoodsDao.getGoodsDetail(params);
	}


}