package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.*;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.*;
import com.cndinuo.page.Page;
import com.cndinuo.service.MberRiderInfoService;
import com.cndinuo.service.OrderTrackService;
import com.cndinuo.service.SmsService;
import com.cndinuo.service.UploadService;
import com.cndinuo.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
* @date 2017-09-14
* @author dgb
* 
*/
@Service("mberRiderInfoService")
public class MberRiderInfoServiceImpl extends AbstractService<MberRiderInfo, Integer> implements MberRiderInfoService {

	@Autowired
	private MberRiderInfoDao mberRiderInfoDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
    private MberInfoDao mberInfoDao;
	@Autowired
	private MberRiderPositionDao riderPositionDao;
	@Autowired
	private SmsService smsService;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderDeliveryDao deliveryDao;
	@Autowired
	private OrderTrackService trackService;
	@Autowired
	private MberRiderBalanceDao riderBalanceDao;
	@Autowired
	private UploadService uploadService;
	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mberRiderInfoDao);
	}

	@Transactional
	@Override
	public RespData save(Map<String, Object> params) throws Exception {
		log.info("开始保存骑手提交的信息");
		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}

		Member member = memberDao.getByToken(params.get("token").toString());
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}

		if (member.getMberType() == MberTypeEnum.RIDER.getCode().byteValue()) {
			return RespData.errorMsg("您已经是骑手了，请不要重复提交数据");
		}

		if (StringUtil.isEmpty(params.get("authCertBack") + "")) {
			return RespData.errorMsg("身份证背面照不能为空");
		}

		if (StringUtil.isEmpty(params.get("authCertFront") + "")) {
			return RespData.errorMsg("身份证正面照不能为空");
		}

		if (StringUtil.isEmpty(params.get("authCertHand") + "")) {
			return RespData.errorMsg("手持身份证照不能为空");
		}

		if (StringUtil.isEmpty(params.get("authCertNo") + "")) {
			return RespData.errorMsg("身份证号不能为空");
		}
		try {
			String authCertFront = uploadService.uploadByteArr(params.get("authCertFront").toString(), String.format("%s.jpg", params.get("token").toString()));
			String[] front = authCertFront.split("@");
			String imgPathFront = front[1];
			String authCertBack = uploadService.uploadByteArr(params.get("authCertBack").toString(), String.format("%s.jpg", params.get("token").toString()));
			String[] back = authCertBack.split("@");
			String imgPathBack = back[1];
			String authCertHand = uploadService.uploadByteArr(params.get("authCertHand").toString(), String.format("%s.jpg", params.get("token").toString()));
			String[] head = authCertBack.split("@");
			String imgPathHead = head[1];
			if (authCertFront != null && authCertBack != null && authCertHand != null) {
				MberRiderInfo riderInfo = new MberRiderInfo();
				riderInfo = (MberRiderInfo) populateBean(params, riderInfo);
				riderInfo.setMberId(member.getId());
				riderInfo.setStatus(RiderInfoStatusEnum.APPROVINIG.getCode().byteValue());
				riderInfo.setAuthCertBack(imgPathBack);
				riderInfo.setAuthCertFront(imgPathFront);
				riderInfo.setAuthCertHand(imgPathHead);
				mberRiderInfoDao.insert(riderInfo);
				MberRiderPosition riderPosition = new MberRiderPosition();
				riderPosition.setMberId(member.getId());
				riderPositionDao.insert(riderPosition);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("信息保存异常" + e);
			throw e;
		}
		return RespData.successMsg("信息保存成功");
	}

	@Override
	public RespData adopt(Map params) throws Exception {
		try {
			params.put("mberType", MberTypeEnum.RIDER.getCode());
			params.put("status", RiderInfoStatusEnum.COMPLATE.getCode());
			int result = memberDao.updateByMap(params);
			result = mberRiderInfoDao.updateByMap(params);
			if (result > 0) {
				MberRiderInfo info = (MberRiderInfo) mberRiderInfoDao.getByMap(params).get(0);
				String content = String.format(Const.SMS_SPPROVE_RIDER_ADOPT, info.getAuthName());
				smsService.sendMsg(info.getAuthPhone(),content);
				return RespData.successMsg("审核成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return RespData.errorMsg("操作失败");
	}


	@Override
	public RespData getRiderRobList(Map<String, Object> params) {
		log.info("开始获取抢单页面数据");
		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Member member = memberDao.getByToken(params.get("token").toString());
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		Page page = new Page();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		List<Order> list = orderDao.getRiderRobByPage(page);
		page.setResults(list);
		return RespData.successMsg("",page);
	}

	@Override
	public RespData getRiderByOrderDelivery(Map<String, Object> params) {
		log.info("获取骑手已接单页面数据");
		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}

		Member m = memberDao.getByToken(params.get("token").toString());
		if (m == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		params.put("objId", m.getId());
		List<OrderDelivery> list = deliveryDao.getRiderByOrderDelivery(params);
		return RespData.successMsg("",list);
	}

	@Override
	public RespData getRiderByTodayOrder(Map<String, Object> params) {
		log.info("开始获取骑手今日订单");
		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}

		Member m = memberDao.getByToken(params.get("token").toString());
		if (m == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		params.put("objId", m.getId());
		Page page = new Page();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		List<OrderDelivery> list = deliveryDao.getRiderByTodayOrder(page);
		page.setResults(list);
		return RespData.successMsg("",page);
	}

	@Override
	public RespData getRiderHistoryByOrder(Map<String, Object> params) {
		log.info("开始获取骑手历史订单");
		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Member m = memberDao.getByToken(params.get("token").toString());
		if (m == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}

		params.put("objId", m.getId());
		Page page = new Page();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		List<OrderDelivery> list = deliveryDao.getRiderHistoryByOrder(page);
		page.setResults(list);
		return RespData.successMsg("",page);
	}

	@Override
	public RespData getRiderByBalance(Map<String, Object> params) {
		log.info("开始获取骑手账户余额");
		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}

		Member m = memberDao.getByToken(params.get("token").toString());
		if (m == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录过期，请重新登录");
		}
		params.put("mberId", m.getId());
		MberRiderInfo riderInfo = mberRiderInfoDao.getRiderByBalance(params);
		return RespData.successMsg("",riderInfo);
	}

	@Override
	public RespData updatePickUpByOrderStatusAndTrack(Map<String, Object> params) {
		log.info("开始更新骑手已取单状态");
		if (StringUtil.isEmpty(params.get("token")+"")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		if (StringUtil.isEmpty(params.get("orderNo")+"")) {
			return RespData.errorMsg("订单不能为空");
		}
		Member mb = memberDao.getByToken(params.get("token").toString());
		if (mb == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		try {
			Order or = orderDao.getOrderByOrderNo(params.get("orderNo").toString());
			params.put("version", or.getVersion());
			params.put("orderStatus", OrderStatusEnum.GOODS_SENT_OUT.getCode());
			orderDao.updateByStatus(params);
			trackService.saveTrack(or.getOrderNo(), OrderTrackEnum.ORDER_GOODS_SEND_OUT.getCode().byteValue());
			return RespData.successMsg("取单成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("更新骑手已接单失败"+ e);
			return RespData.errorMsg("取单失败");
		}


	}

	@Override
	public RespData updateSentToByOrderStatusAndTrack(Map<String, Object> params) {
		log.info("开始更新骑手送达商品订单状态");
		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}

		if (StringUtil.isEmpty(params.get("orderNo") + "")) {
			return RespData.errorMsg("订单号不能为空");
		}

		Member mb = memberDao.getByToken(params.get("token").toString());

		if (mb == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}

		try {
			Order or = orderDao.getOrderByOrderNo(params.get("orderNo").toString());
			trackService.saveTrack(or.getOrderNo(), OrderTrackEnum.ORDER_GOODS_ARRIVED.getCode().byteValue());
			params.put("mberId", mb.getId());
			MberRiderInfo riderInfo = mberRiderInfoDao.getRiderByBalance(params);
			if (riderInfo.getBalance() == null) {
				riderInfo.setBalance(or.getDeliveryFee());
			}else{
				riderInfo.setBalance(riderInfo.getBalance().add(or.getDeliveryFee()));
			}
			mberRiderInfoDao.updateById(riderInfo);
			MberRiderBalance riderBalance = new MberRiderBalance();
			riderBalance.setAmount(or.getDeliveryFee());
			riderBalance.setCreateTime(new Date());
			riderBalance.setMberId(mb.getId());
			riderBalance.setType(BalanceTypeEnum.income.getCode().byteValue());
			riderBalanceDao.insert(riderBalance);
			return RespData.successMsg("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("订单状态更新出现异常" + e);
			return RespData.errorMsg("操作失败");
		}
	}

	@Override
	public MberRiderInfo getByMberId(Integer mberId) {
		return mberRiderInfoDao.getByMberId(mberId);
	}

	@Override
	public RespData getRiderInfo(String token) {
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(), "请先登录");
		}
		Member m = memberDao.getByToken(token);
		if (m == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		Map<String, Object> info = mberRiderInfoDao.getRiderInfo(m.getId());
		return RespData.successMsg("获取成功",info);
	}


    @Override
    public RespData saveInfo(Map<String, Object> params) {
        String token = StringUtil.toString(params.get("token"));
        if (StringUtil.isEmpty(token)) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(), "请先登录");
        }
        Member m = memberDao.getByToken(token);
        if (m == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
        }
        params.put("mberId", m.getId());
        int result = mberInfoDao.updateByMap(params);
        return result > 0 ? RespData.successMsg("修改成功") : RespData.errorMsg("修改失败");
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