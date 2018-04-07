package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.MemberDao;
import com.cndinuo.dao.OrderDao;
import com.cndinuo.dao.OrderItemDao;
import com.cndinuo.dao.OrderReturnDao;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.*;
import com.cndinuo.page.Page;
import com.cndinuo.service.*;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @date 2017-09-16
* @author dgb
* 
*/
@Service("orderReturnService")
public class OrderReturnServiceImpl extends AbstractService<OrderReturn, Integer> implements OrderReturnService {

	@Autowired
	private OrderReturnDao orderReturnDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private SmsService smsService;
	@Autowired
	private MberRiderInfoService riderInfoService;
	@Autowired
	private AlipayService alipayService;
	@Autowired
	private AlipayFlowService alipayFlowService;
	@Autowired
	private WxPayService wxPayService;
	@Autowired
	private WxpayFlowService wxpayFlowService;


	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(orderReturnDao);
	}

	@Override
	public RespData getRefundInfo(String token, String orderNo) {
		log.info("申请退款页面(退款信息)");
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Member member = memberDao.getByToken(token);
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", orderNo);
		params.put("mberId", member.getId());
		Order order = orderService.getOneByMap(params);
		List<OrderItem> items = orderItemDao.getGoodsByOrderNo(params);
		if (items != null && items.size() > 0) {
			order.setItem(items.get(0));
		}
		return RespData.successMsg("",order);
	}

	@Override
	public RespData getRefundReason(String token) {
		log.info("申请退款页面(退款原因)");
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Member member = memberDao.getByToken(token);
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		List<SysDict> dicMap = sysDictService.getByTableAndField("order_return","ret_reason");
		return RespData.successMsg("",dicMap);
	}

	@Override
	public RespData save(String orderNo,Integer status,String retNo,String remark) {
		Map<String, Object> params = new HashMap<String , Object>();
		Order order = orderDao.getOrderByOrderNo(orderNo);
		try {
			if (status == OrderStatusEnum.RETURN_REJECT_ORDER.getCode()) {//拒绝退款
				params.put("orderNo", orderNo);
				params.put("retStatus", OrderStatusEnum.RETURN_REJECT_ORDER.getCode());
				params.put("retNo", retNo);
				params.put("remark", remark);
				orderReturnDao.updateByMap(params);
				params.remove("remark");
				params.put("orderStatus", OrderStatusEnum.ORDER_COMPLETE.getCode());
				orderDao.updateByMap(params);
				//TODO  短信通知用户退款拒绝待测试
				smsService.sendMsg(order.getReceiptMobile(),String.format(Const.SMS_REJECT_RETURN_RODER,order.getReceiptName(),order.getOrderNo()));
			}else{//同意退款
				params.put("orderNo", orderNo);
				params.put("retStatus", OrderStatusEnum.RETURN_COMPLETE.getCode());
				params.put("retNo", retNo);
				orderReturnDao.updateByMap(params);
				params.put("orderStatus", OrderStatusEnum.ORDER_COMPLETE.getCode());
				orderDao.updateByMap(params);
				//TODO 短信通知 退款操作
				byte payType = order.getPayType();
				if (payType == PayTypeEnum.WECHAT.getCode()) {          //微信退款

				}else if(payType == PayTypeEnum.ALIPAY.getCode()){      //支付宝退款

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("退款操作发生异常："+e);
			return RespData.errorMsg(Const.ERROR_MSG);
		}
		return RespData.successMsg("操作成功");
	}

	@Override
	public RespData getRefundList(String token) {
		log.info("退款列表");
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Member member = memberDao.getByToken(token);
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mberId", member.getId());
		Page page = new Page();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		List<OrderReturn> orderReturns = orderReturnDao.getRefundByMberId(page);
		for (OrderReturn orderReturn: orderReturns) {
			params.put("orderNo", orderReturn.getOrderNo());
			List<OrderItem> items = orderItemDao.getRefundByOrderNo(params);
			orderReturn.setItem(items.get(0));
		}
		page.setResults(orderReturns);
		return RespData.successMsg("", page);
	}

	@Override
	public RespData submitRefundApply(String token, String orderNo, String retType, String retReason) {
		log.info("申请退款");
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Member member = memberDao.getByToken(token);
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderNo", orderNo);
			params.put("mberId", member.getId());
			Order order = orderService.getOneByMap(params);
			if (order.getOrderStatus() != null) {
				params.clear();
				params.put("orderStatus", OrderStatusEnum.RETURN_APPROVE_ORDER.getCode().byteValue()); //退款审核中
				params.put("orderNo", orderNo);
				params.put("version", order.getVersion());
				orderDao.updateByStatus(params);
			}

			//生成退款单
			OrderReturn ret = new OrderReturn();
			Integer retId = orderReturnDao.getLastReturnNo();
			if (retId == null) {
				retId = 0;
			}
			String retNo = "T"+StringUtil.formatDate(new Date(),"yyyyMMddHHmmss")+(retId+1)+member.getId();
			ret.setRetNo(retNo);
			ret.setOrderNo(order.getOrderNo());
			ret.setRetPrice(order.getTotalPrice());//退还支付金额
			ret.setMberId(order.getMberId());
			ret.setMrhtId(order.getMrhtId());
			ret.setRetReason(retReason);
			ret.setRetStatus(OrderStatusEnum.RETURN_APPROVE_ORDER.getCode().byteValue());
            ret.setSettleStatus(SettleStatusEnum.STAY_SETTLE.getCode().byteValue());
            ret.setRetTime(new Date());
			orderReturnDao.insert(ret);
			return RespData.successMsg("申请退款成功，待系统审核！");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("申请退款失败，系统异常："+e);
			return RespData.errorMsg(Const.ERROR_MSG);
		}
	}

	@Override
	public RespData refundDeposit(String token) {
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(), "请先登录");
		}

		Member m = memberDao.getByToken(token);
		if (m == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(), "登录已过期,请重新登录");
		}
		MberRiderInfo info = riderInfoService.getByMberId(m.getId());
		if (info == null) {
			return RespData.errorMsg("没有骑手信息");
		}
		try {
			//查询骑手充值订单
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("mberId",m.getId());
			params.put("orderStatus", OrderStatusEnum.ORDER_COMPLETE.getCode());
			params.put("orderType", OrderTypeEnum.ORDER_RECHARGE.getCode());
			List<Order> orders = orderDao.getByMap(params);
			if (orders != null && orders.size() > 0) {

				if (info.getDeposit().intValue() > 0) {

					Order order = orders.get(0);
					Integer retId = orderReturnDao.getLastReturnNo();
					if (retId == null) {
						retId = 0;
					}
					String retNo = StringUtil.formatDate(new Date(),"yyyyMMddHHmmss")+(retId+1)+m.getId();
					OrderReturn ret = new OrderReturn();
					ret.setRetNo(retNo);
					ret.setOrderNo(order.getOrderNo());
					ret.setRetPrice(info.getDeposit());
					ret.setRemark("退还骑手押金");
					ret.setMberId(order.getMberId());
					ret.setActualAmount(info.getDeposit());
					ret.setRetStatus(OrderStatusEnum.RETURN_APPROVE_ORDER.getCode().byteValue());
					ret.setRetTime(new Date());
					orderReturnDao.insert(ret);
					if(order.getPayType() == PayTypeEnum.WECHAT.getCode().byteValue()){ //微信
						int totalFee = info.getDeposit().multiply(BigDecimal.valueOf(100)).intValue();
						int refundFee = info.getDeposit().multiply(BigDecimal.valueOf(100)).intValue();
						boolean result = wxPayService.wxRefund(retNo, order.getOrderNo(),totalFee,refundFee);
						if(result){
							params.clear();
							params.put("orderNo", order.getOrderNo());
							params.put("orderStatus", OrderStatusEnum.RETURN_COMPLETE.getCode());
							orderDao.updateByStatus(params);
							ret.setRetStatus(OrderStatusEnum.RETURN_COMPLETE.getCode().byteValue());
							orderReturnDao.updateById(ret);
							info.setDeposit(BigDecimal.valueOf(0));
							riderInfoService.updateById(info);
                            MberRiderBalance balance = new MberRiderBalance();
                            balance.setMberId(order.getMberId());
                            balance.setType(BalanceTypeEnum.refund.getCode().byteValue());
                            balance.setAmount(new BigDecimal(refundFee));
                            balance.setCreateTime(new Date());
							return RespData.successMsg("合作佣金退还成功!");
						}
					}else { //支付宝
						params.clear();
						params.put("orderNo", order.getOrderNo());
						AlipayFlow flow = alipayFlowService.getOneByMap(params);
						String refundAmount = info.getDeposit().toString();
						boolean result = alipayService.alipayRefund(order.getOrderNo(), flow.getTradeNo(), refundAmount, retNo);
						if(result){
							params.put("orderStatus", OrderStatusEnum.RETURN_COMPLETE.getCode());
							orderDao.updateByStatus(params);
							ret.setRetStatus(OrderStatusEnum.RETURN_COMPLETE.getCode().byteValue());
							orderReturnDao.updateById(ret);
							info.setDeposit(BigDecimal.valueOf(0));
							riderInfoService.updateById(info);
							MberRiderBalance balance = new MberRiderBalance();
							balance.setMberId(order.getMberId());
							balance.setType(BalanceTypeEnum.refund.getCode().byteValue());
							balance.setAmount(new BigDecimal(refundAmount));
							balance.setCreateTime(new Date());
							return RespData.successMsg("合作佣金退还成功!");
						}
					}
				}else{
					return RespData.errorMsg("没有要退还的合作佣金");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("退还骑手押金发生异常:" + e);
			throw e;
		}
		return RespData.errorMsg("退还合作佣金失败");
	}
}