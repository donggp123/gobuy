package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.*;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.OrderStatusEnum;
import com.cndinuo.eunm.OrderTrackEnum;
import com.cndinuo.eunm.RiderInfoStatusEnum;
import com.cndinuo.eunm.SettleStatusEnum;
import com.cndinuo.page.Page;
import com.cndinuo.service.*;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
* @date 2017-09-12
* @author dgb
*
*/
@Service("orderService")
public class OrderServiceImpl extends AbstractService<Order, Integer> implements OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private OrderTrackService orderTrackService;
	@Autowired
    private SysSettingService settingService;
	@Autowired
    private MrhtGoodsDao goodsDao;
	@Autowired
    private OrderReturnDao returnDao;
	@Autowired
	private OrderDeliveryDao deliveryDao;
	@Autowired
    private MerchantService merchantService;
	@Autowired
	private MberRiderInfoDao riderInfoDao;
	@Autowired
	private SysDictService  sysDictService;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(orderDao);
	}

	@Override
	public RespData getMrhtOrderView(Map<String, Object> params) {
		log.info("商家接单页面");
		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		if (StringUtil.isEmpty(params.get("orderNo") + "")) {
			return RespData.errorMsg("订单号不能为空");
		}
		Member member = memberDao.getByToken(params.get("token").toString());
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		Order or = orderDao.getMrhtOrderView(params);
		List<OrderItem> item = orderItemDao.getByMap(params);
		params.clear();
		params.put("order", or);
		params.put("orderItem", item);
		return RespData.successMsg("",params);
	}


	@Override
	public Order getOrderByOrderNo(String orderNo) {
		return orderDao.getOrderByOrderNo(orderNo);
	}

	@Override
	public RespData getOrderList(String token) {
		log.info("全部订单列表");
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
        List<Order> orders = orderDao.getOrdersByMberId(page);
        for (Order order :orders) {
        	int totalNum = 0;
            params.put("orderNo", order.getOrderNo());
            List<OrderItem> items = orderItemDao.getGoodsByOrderNo(params);
			for (OrderItem item : items) {
				totalNum += item.getNum();
			}
			order.setTotalNum(totalNum);
			order.setItem(items.get(0));

        }
        page.setResults(orders);
        return RespData.successMsg("",page);
	}

	@Override
	public RespData confirmReceipt(String token, String orderNo) {
		log.info("确认收货");
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
			Order order = orderDao.getOrderByOrderNo(orderNo);
			if (order.getOrderStatus() == OrderStatusEnum.GOODS_SENT_OUT.getCode().byteValue()) { //商品已送出
				params.put("orderStatus",OrderStatusEnum.ORDER_COMPLETE.getCode().byteValue()); //订单完成
				params.put("version", order.getVersion());
				orderDao.updateByStatus(params);
                return RespData.successMsg("确认收货成功!");
			}else {
                return RespData.errorMsg("确认收货失败!");
            }
        } catch (Exception e) {
			e.printStackTrace();
			log.error("确认收货失败，系统异常：" + e);
			throw e;
		}
	}

    /**
     * 判断商家是否接单
     * @throws Exception
     */
	@Override
	public void judgeMrhtIsOrders() throws Exception {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderStatus", OrderStatusEnum.STAY_MERCHANT_ORDERS.getCode());
            List<Order> orders = orderDao.getByMap(params);
            String value = settingService.getByKey("mrht_orders_time");
            for (Order order : orders) {
                long orderTime = order.getOrderTime().getTime();
                long currentTime = System.currentTimeMillis();
                if ((currentTime - orderTime) / 60000 > Integer.parseInt(value)) {

                    //商家没有接单  还原库存，并生成退款单
                    params.clear();
                    params.put("orderNo", order.getOrderNo());
                    List<OrderItem> items = orderItemDao.getByMap(params);
                    int count = 0;
                    for (OrderItem item : items) {
                        MrhtGoods goods = goodsDao.getById(item.getGoodsId());
                        goods.setStockNum(goods.getStockNum() + item.getNum());
                        int result = goodsDao.updateById(goods);
                        if (result > 0) {
                            count ++;
                        }
                    }
                    if (items.size() != count) {
                        throw new Exception("还原库存失败");
                    }
                    //更新订单状态为商家未接单
                    params.clear();
                    params.put("orderStatus", OrderStatusEnum.RETURN_APPROVE_ORDER.getCode());
                    params.put("version", order.getVersion());
                    params.put("orderNo", order.getOrderNo());
                    params.put("remark", OrderStatusEnum.MERCHANT_REJECT_ORDER.getName());
                    orderDao.updateByStatus(params);
                    //生成退款单
                    OrderReturn ret = new OrderReturn();
                    Integer retId = returnDao.getLastReturnNo();
                    if (retId == null) {
                        retId = 0;
                    }
                    String retNo = "T"+StringUtil.formatDate(new Date(),"yyyyMMddHHmmss")+(retId+1)+order.getMberId();
                    ret.setRetNo(retNo);
                    ret.setOrderNo(order.getOrderNo());
                    ret.setRetPrice(order.getTotalPrice().add(order.getDeliveryFee()));//退还支付金额包含配送费
                    ret.setRemark(OrderStatusEnum.MERCHANT_REJECT_ORDER.getName());
                    ret.setMberId(order.getMberId());
                    ret.setMrhtId(order.getMrhtId());
                    ret.setActualAmount(order.getTotalPrice().add(order.getDeliveryFee()));
                    ret.setRetStatus(OrderStatusEnum.RETURN_APPROVE_ORDER.getCode().byteValue());
                    ret.setSettleStatus(SettleStatusEnum.STAY_SETTLE.getCode().byteValue());
                    ret.setRetTime(new Date());
                    returnDao.insert(ret);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("判断商家是否接单定时任务发生异常："+e);
            throw e;
        }
    }

	@Override
	public Page<Order> getOrderByPage(Map<String,Object> params) {
		Page page = new Page();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		List<Order> list = orderDao.getOrderByPage(page);
		page.setResults(list);
		return page;
	}

	@Override
	public RespData getOrderTracking(String token, String orderNo) {
		log.info("订单跟踪");
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Member member = memberDao.getByToken(token);
		if (member == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<OrderTrack> orderTrackList = orderTrackService.getLastOrderTracking(orderNo);
		List<SysDict> dicMap = sysDictService.getByTableAndField("order_track","track_status");
		for (SysDict dic: dicMap){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", dic.getValue());
			map.put("name", dic.getName());
			map.put("flag", false);
			if (dic.getValue().equals(orderTrackList.get(0).getTrackStatus().toString())) {
				map.put("flag", true);
			}
			map.put("trackTime", "");
			for (OrderTrack orderTrack : orderTrackList) {
				if (dic.getValue().equals(orderTrack.getTrackStatus().toString())) {
					map.put("trackTime", orderTrack.getTrackTime());
				}
			}
			list.add(map);
		}
		return RespData.successMsg("", list);
	}

	@Override
	public RespData updateStatusByDelivery(String deliveryType, String orderNo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderNo", orderNo);
        params.put("deliveryType", deliveryType);
        return merchantService.orders(params);
	}


	@Override
	public RespData robOrder(String token, String orderNo) {
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		if (StringUtil.isEmpty(orderNo)) {
			return RespData.errorMsg("订单号不能为空!");
		}
		Member m = memberDao.getByToken(token);
		if (m == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}

		try {
            MberRiderInfo info = new MberRiderInfo();
            info.setMberId(m.getId());
            info.setStatus(RiderInfoStatusEnum.COMPLATE.getCode().byteValue());
            List<MberRiderInfo> infos = riderInfoDao.getByEntity(info);
            if (infos != null && infos.size() > 0) {
                info = infos.get(0);
                if (info.getDeposit() == null) {
                    return RespData.errorMsg("请先缴纳合作佣金!");
                }
                String rider_deposit = settingService.getByKey("rider_deposit");
                int temp = StringUtil.toInt(rider_deposit);
                int deposit = info.getDeposit().intValue();
                if (deposit < temp) {
                    return RespData.errorMsg("请先缴纳合作佣金!");
                }
            }else {
                return RespData.errorMsg("您的申请还没有审核通过,暂时还不能接单!");
            }
			Integer riderOrderNum = Integer.valueOf(settingService.getByKey("reder_order_num"));
			Integer orderCount = orderDao.getRiderOrderCountByMberId(m.getId());
            if (orderCount >= riderOrderNum) {
                return RespData.errorMsg("您手中未完成的订单过多,请及时配送!");
            }
            Order order = orderDao.getOrderByOrderNo(orderNo);
			if (order == null) {
				return RespData.errorMsg("订单号错误");
			}else {
				if (order.getOrderStatus() >= OrderStatusEnum.RIDER_HURRY_TO_MERCHANT.getCode().byteValue()) {
					return RespData.errorMsg("手慢了,已有骑手接单!");
				}
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderStatus", OrderStatusEnum.RIDER_HURRY_TO_MERCHANT.getCode());
			map.put("version", order.getVersion());
			map.put("orderNo", orderNo);
			int count = orderDao.updateByStatus(map);
			if (count > 0) {
				//保存对手接单记录
				OrderDelivery delivery = new OrderDelivery();
				delivery.setOrderNo(orderNo);
				delivery.setObjId(m.getId());
				delivery.setDeliveryFee(order.getDeliveryFee());
				delivery.setDeliveryTime(new Date());
				deliveryDao.insert(delivery);
				//保存订单跟踪记录
				orderTrackService.saveTrack(orderNo,OrderTrackEnum.ORDER_RIDER_TO_MERCHANT.getCode().byteValue());
				return RespData.successMsg("抢单成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("骑手抢单发生异常：" + e);
			throw e;
		}
		return RespData.errorMsg("抢单失败!");
	}

	@Override
	public RespData getMrhtByLatelyOrder(Map<String,Object> params) {
		log.info("开始获取最近三天订单数据");
		if (StringUtil.isEmpty(params.get("mrhtId") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Page page = new Page();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		List<Order> list = orderDao.getMrhtByLatelyOrder(page);
		for (Order o: list) {
			params.put("orderNo", o.getOrderNo());
			List<OrderItem> orderItems = orderItemDao.getByMap(params);
			o.setItems(orderItems);
		}
		page.setResults(list);
		return RespData.successMsg("",page);
	}

	@Override
	public RespData getMrhtOrderAll(Map<String, Object> params) {
		if (StringUtil.isEmpty(params.get("mrhtId") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		Page page = new Page();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		List<Order> list = orderDao.getMrhtOrderAll(page);
		for (Order o: list) {
			params.put("orderNo", o.getOrderNo());
			List<OrderItem> orderItems = orderItemDao.getByMap(params);
			o.setItems(orderItems);
		}
		page.setResults(list);
		return RespData.successMsg("",page);
	}

	@Override
	public RespData getHaveOrderList(String mrhtId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mrhtId", mrhtId);
		params.put("orderStatus", OrderStatusEnum.MERCHANT_RECEIVED_ORDER.getCode().byteValue());
		List<Order> orders = orderDao.getOrderByMrhtId(params);
		for (Order order:orders) {
			params.put("orderNo", order.getOrderNo());
			List<OrderItem> items = orderItemDao.getGoodsByOrderNo(params);
			order.setItems(items);
			//TODO  到用户的距离
			List<MberRiderInfo> riders = riderInfoDao.getRiderByOrderNo(params);
			order.setRiderInfos(riders);
		}
		return RespData.successMsg("", orders);
	}

	@Override
	public RespData sendTo(String orderNo) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderNo", orderNo);
			Order order = orderDao.getOrderByOrderNo(orderNo);
			if (order.getOrderStatus() == OrderStatusEnum.GOODS_SENT_OUT.getCode().byteValue()) { //商品已送出
				params.put("orderStatus",OrderStatusEnum.ORDER_COMPLETE.getCode().byteValue()); //订单完成
				params.put("version", order.getVersion());
				orderDao.updateByStatus(params);
				orderTrackService.saveTrack(orderNo, OrderTrackEnum.ORDER_GOODS_ARRIVED.getCode().byteValue()); //商品已送达
				return RespData.successMsg("确认送达成功!");
			}else {
				return RespData.errorMsg("确认送达失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("确认送达失败，系统异常：" + e);
			throw e;
		}
	}
}