package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.*;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.CommentTypeEnum;
import com.cndinuo.page.Page;
import com.cndinuo.service.MberCommentService;
import com.cndinuo.service.MberInfoService;
import com.cndinuo.service.SysSettingService;
import com.cndinuo.service.UploadService;
import com.cndinuo.utils.StringUtil;
import com.cndinuo.utils.TextFilterUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;


/**
* @date 2017-09-12
* @author dgb
*
*/
@Service("mberCommentService")
public class MberCommentServiceImpl extends AbstractService<MberComment, Integer> implements MberCommentService {

	@Autowired
	private MberCommentDao mberCommentDao;
	@Autowired
	private MberCommentReplyDao commentReplyDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MberInfoService infoService;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private UploadService uploadService;
	@Autowired
	private SysSettingService settingService;
	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mberCommentDao);
	}

	@Override
	public RespData getCommentByMap(Map<String, Object> params) {
		log.info("获取用户评价列表");
		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Member member = memberDao.getByToken(params.get("token").toString());
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		params.put("mberId", member.getId());
		Page page = new Page();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		List<Map<String,Object>> list = mberCommentDao.getCommentByMap(page);
		String imgPath = settingService.getByKey("img_server");
		for (Map m : list) {
			List<String> img = new ArrayList<>();
			if(StringUtil.isNotEmpty(StringUtil.toString(m.get("comImage")))){
				String[] images = m.get("comImage").toString().split(";");
				for (int i = 0;i<images.length; i++) {
					img.add(imgPath + "/" + images[i]);
				}
			}
			m.put("comImage", img);
		}
		page.setResults(list);
		return RespData.successMsg("", page);
	}

	@Override
	public RespData getGoodsByCommList(Map<String,Object> params) {
		log.info("获取商品评价列表");
		if (StringUtil.isEmpty(params.get("id")+"")) {
			return RespData.errorMsg("商家id不能为空");
		}

		Page page = new Page();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		String imgPath = settingService.getByKey("img_server");
		List<Map<String,Object>> list = mberCommentDao.getGoodsByCommList(page);
		for (Map m: list) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("comId", m.get("id"));
			map.put("mrhtId", params.get("id"));
			List<MberCommentReply> reply = commentReplyDao.getByMap(map);
			m.put("commentReply", reply);
			List<String> img = new ArrayList<>();
			if(StringUtil.isNotEmpty(StringUtil.toString(m.get("comImage")))){
                String[] images = m.get("comImage").toString().split(";");
                for (int i = 0;i<images.length; i++) {
                    img.add(imgPath + "/" + images[i]);
                }
            }
			m.put("comImage", img);
		}
		Map<String ,Object> counts = mberCommentDao.getCountByComment(params);
		page.setP(counts);
		page.setResults(list);
		return RespData.successMsg("", page);
	}

	@Override
	public RespData getCommView(Map<String, Object> params) {
		log.info("评价页面接口");
		if (StringUtil.isEmpty(params.get("orderNo") + "")) {
			return RespData.errorMsg("订单号不能为空");
		}

		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}

		Member member = memberDao.getByToken(params.get("token").toString());
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录超时，请重新登录");
		}
		params.put("mberId", member.getId());
		List<OrderItem> item = orderItemDao.getGoodsByOrderNo(params);
		MberInfo info = infoService.getById(member.getId());
		params.clear();
		if (item != null && item.size() > 0) {
			params.put("orderItem",item.get(0));
		}
		params.put("deliClerkId", member.getId());
		params.put("nickName", info.getNickName());
		params.put("headIcon", info.getHeadIcon());
		return RespData.successMsg("",params);
	}

	@Override
	public List<Map<String, Object>> getCommentListForGoods(String mrhtId, String goodsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mrhtId", mrhtId);
		params.put("goodsId", goodsId);
		params.put("comType", 2);
		return mberCommentDao.getCommentListForGoods(params);
	}

	@Override
	public RespData save(Map<String, Object> params) throws Exception {
		log.info("开始保存");

		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Order or = orderDao.getOrderByOrderNo(params.get("orderNo").toString());
		if (or == null) {
			return RespData.errorMsg("订单号错误");
		}

		Member member = memberDao.getByToken(params.get("token").toString());
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		try {
			String goodsIds = "";
			List<OrderItem> orderItems = orderItemDao.getOrderItemByGoods(params.get("orderNo").toString());
			for (OrderItem oi : orderItems) {
				goodsIds += oi.getGoodsId() + ",";
			}
			goodsIds = goodsIds.substring(0, goodsIds.length() - 1);
			if (StringUtil.isNotEmpty(params.get("comLevel") + "")
					|| StringUtil.isNotEmpty(params.get("contnet") + "")
					|| StringUtil.isNotEmpty(params.get("comImage")+ "")) {
				String img = params.get("comImage").toString();
				String[] images = img.split(",");
				String imgPath = "";
				for (int i = 0 ;i<images.length;i++) {
					String comImage = uploadService.uploadByteArr(images[i], String.format("%s.jpg", params.get("token").toString()));
					String[] filePath = comImage.split("@");
					imgPath += filePath[1] + ";";
				}
				MberComment comment = new MberComment();
				comment = (MberComment) populateBean(params, comment);
				comment.setComTime(new Date());
				comment.setGoodsId(goodsIds);
				comment.setMrhtId(or.getMrhtId());
				comment.setMberId(member.getId());
				comment.setContent(TextFilterUtil.checkSensitiveWord(comment.getContent()));
				comment.setComType(CommentTypeEnum.GOODS.getCode().byteValue());
				comment.setComImage(imgPath.substring(0,imgPath.length()-1));
				mberCommentDao.insert(comment);

			}
			if (StringUtil.isNotEmpty(params.get("deliClerkContent") + "")
					|| StringUtil.isNotEmpty(params.get("deliClerkComLevel") + "")) {
				MberComment comm = new MberComment();
				comm = (MberComment) populateBean(params, comm);
				comm.setMberId(member.getId());
				comm.setComLevel((byte)Integer.parseInt(params.get("deliClerkComLevel").toString()));
				comm.setDeliClerkId(Integer.parseInt(params.get("deliClerkID").toString()));
				comm.setComType(CommentTypeEnum.RIDER.getCode().byteValue());
				comm.setComImage("");
				comm.setMrhtId(or.getMrhtId());
				comm.setGoodsId(goodsIds);
				comm.setComTime(new Date());
				comm.setContent(TextFilterUtil.checkSensitiveWord(params.get("deliClerkContent").toString()));
				mberCommentDao.insert(comm);
			}
			return RespData.successMsg("评价成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存发生异常 " + e);
			throw e;
		}
	}

	@Override
	public Page<MberComment> getSatisfactionRiderCommonList(Map<String, Object> params) {
		Member member = memberDao.getByToken(params.get("token").toString());
		Page<MberComment> page = new Page<MberComment>();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		params.put("deliClerkId", member.getId());
		params.put("comType", 3);
		page.setP(params);
		List<MberComment> list = mberCommentDao.getSatisfactionRiderCommonList(page);
		page.setResults(list);
		return page;
	}

	@Override
	public Page<MberComment> getGeneralRiderCommonList(Map<String,Object> params) {

		Member member = memberDao.getByToken(params.get("token").toString());
		Page<MberComment> page = new Page<MberComment>();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		params.put("deliClerkId", member.getId());
		params.put("comType", 3);
		page.setP(params);
		List<MberComment> list = mberCommentDao.getGeneralRiderCommonList(page);
		page.setResults(list);
		return page;
	}

	@Override
	public RespData reconsider(String token,String comId) {
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("comId", comId);
        params.put("reconsider", 1);
        mberCommentDao.updateByMap(params);
		return RespData.successMsg("复议成功");
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