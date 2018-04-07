package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.dao.*;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.DeviceTypeEnum;
import com.cndinuo.eunm.MrhtStatusEnum;
import com.cndinuo.eunm.OrderStatusEnum;
import com.cndinuo.eunm.OrderTrackEnum;
import com.cndinuo.page.Page;
import com.cndinuo.service.MerchantService;
import com.cndinuo.service.OrderDeliveryService;
import com.cndinuo.service.OrderTrackService;
import com.cndinuo.utils.Base64;
import com.cndinuo.utils.MD5;
import com.cndinuo.utils.StringUtil;
import com.cndinuo.utils.UPushUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @date 2017-08-28
* @author dgb
* 
*/
@Service("merchantService")
public class MerchantServiceImpl extends AbstractService<Merchant, Integer> implements MerchantService {

	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private MrhtInfoDao mrhtInfoDao;
	@Autowired
    private OrderDao orderDao;
	@Autowired
    private OrderTrackService trackService;
	@Autowired
    private OrderDeliveryService deliveryService;
	@Autowired
	private MberRiderInfoDao riderInfoDao;
	@Autowired
    private OrderItemDao itemDao;
	@Autowired
    private MrhtAccountDao accountDao;

	@Value("${productionMode}")
	private boolean productionMode;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(merchantDao);
	}

	@Override
	public RespData getLoginForApp(String loginName, String password) {
		if (StringUtil.isEmpty(loginName)) {
			return RespData.errorMsg("用户名不能为空!");
		}
		if (StringUtil.isEmpty(password)) {
			return RespData.errorMsg("密码不能为空!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		Merchant mrht = this.getOneByMap(params);
		if (mrht == null) {
			return RespData.errorMsg("用户名不存在!");
		}
		password = MD5.encode(password);
		password = StringUtil.saltCode(mrht.getSalt(), password);
		if (!mrht.getPassword().equals(password)) {
			return RespData.errorMsg("密码不正确!");
		}

		params.clear();
		params.put("mrhtId", mrht.getId());
		return RespData.successMsg("登录成功!", params);
	}

    /**
     * 商家接单（前端和后台同一个方法）
     * @param params
     * @return
     */
    @Override
    public RespData orders(Map<String, Object> params) {
        String orderNo = StringUtil.toString(params.get("orderNo"));
        String deliveryType = StringUtil.toString(params.get("deliveryType"));
        if (StringUtil.isEmpty(orderNo)) {
            return RespData.errorMsg("订单不能为空!");
        }
        if (StringUtil.isEmpty(deliveryType)) {
            return RespData.errorMsg("请选择配送方式");
        }
        try {
            Order order = orderDao.getOrderByOrderNo(orderNo);
            if (order == null) {
                return RespData.errorMsg("订单号错误!");
            }
            if (order.getOrderStatus() > OrderStatusEnum.STAY_MERCHANT_ORDERS.getCode().byteValue()) {
                return RespData.errorMsg("该订单已经处理过了");
            }
            Byte type = Byte.parseByte(deliveryType);
            if (type == 1) { //商家自送
                params.put("version", order.getVersion().toString());
                params.put("orderStatus", OrderStatusEnum.GOODS_SENT_OUT.getCode());
                orderDao.updateByStatus(params);
                trackService.saveTrack(orderNo, OrderTrackEnum.ORDER_MERCHANT_ORDERS.getCode().byteValue());
                trackService.saveTrack(orderNo, OrderTrackEnum.ORDER_RIDER_TO_MERCHANT.getCode().byteValue());
                trackService.saveTrack(orderNo, OrderTrackEnum.ORDER_GOODS_SEND_OUT.getCode().byteValue());
                deliveryService.saveDelivery(orderNo, order.getMrhtId(), order.getDeliveryFee()); //添加配送信息
                return RespData.successMsg("接单成功");
            }else if(type == 2){ //配送员配送
                params.put("version", order.getVersion().toString());
                params.put("orderStatus", OrderStatusEnum.MERCHANT_RECEIVED_ORDER.getCode());
                orderDao.updateByStatus(params);
                trackService.saveTrack(orderNo, OrderTrackEnum.ORDER_MERCHANT_ORDERS.getCode().byteValue());
				MrhtInfo info = mrhtInfoDao.getByMrhtId(order.getMrhtId());
				//TODO 通知配送员待测试
				//查出所有符合条件的配送员发送通知
				params.clear();
				params.put("lng", info.getLng());
				params.put("lat", info.getLat());
				List<Map<String, Object>> riders = riderInfoDao.getRiderByLngAndLat(params);
                String title = "新订单通知";
                String ticker = "您有新的订单，请查收!";
                params.clear();
                params.put("orderNo", orderNo);
                params.put("mrhtName",order.getMrhtName());
                params.put("mrhtId", order.getMrhtId());
                params.put("deliveryFee", order.getDeliveryFee());
                params.put("deliveryTime", order.getDeliveryTime());
                params.put("mobile", info.getMobile());
                params.put("orderStatus", order.getOrderStatus());
                params.put("storeImage", info.getStoreImage());
                params.put("distance", "");
                for (Map<String, Object> rider : riders) {
                    params.replace("distance", rider.get("distance"));
                    int deviceType = StringUtil.toInt(rider.get("deviceType"));
                    String deviceToken = StringUtil.toString(rider.get("deviceToken"));
                    if (deviceType == DeviceTypeEnum.ANDROID.getCode()) { //android设备
                        UPushUtil.sendAndroidUnicast(deviceToken,title,ticker, params.toString(),productionMode);
                    } else if (deviceType == DeviceTypeEnum.IOS.getCode()) { //IOS设备
                        UPushUtil.sendIOSUnicast(deviceToken,title,params.toString(),productionMode);
                    }
                }
                return RespData.successMsg("接单成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("商家接单发生异常:" + e);
            throw e;
        }
        return RespData.errorMsg("接单失败!");
    }

    @Override
	public boolean save(Merchant merchant, MrhtInfo mrhtInfo) {
		int result = 0;
		try {
			String password = MD5.encode(merchant.getPassword());
			String saltSource = Base64.encode((merchant.getLoginName()+password).getBytes()).replace("\n","");
			merchant.setPassword(StringUtil.saltCode(saltSource,password));
			merchant.setSalt(saltSource);
			merchant.setStatus(MrhtStatusEnum.PENDING_AUDIT.getCode().byteValue());
			merchant.setCreateTime(new Date());
			merchant.setDeleted((byte) 0);
			merchant = super.insert(merchant);
			mrhtInfo.setMrhtId(merchant.getId());
			result = mrhtInfoDao.insert(mrhtInfo);
			//账户信息
			MrhtAccount account = new MrhtAccount();
            account.setMrhtId(merchant.getId());
            accountDao.insert(account);
        } catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result > 0 ? true : false;
	}

	@Override
	public boolean changePwd(UserManager user) {
		String password = MD5.encode(user.getPassword());
		String saltSource = Base64.encode((user.getLoginName()+password).getBytes()).replace("\n","");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("password", StringUtil.saltCode(saltSource,password));
		params.put("salt",saltSource);
		int result = merchantDao.updateByMap(params);
		return result > 0 ? true : false;
	}

	@Override
	public boolean updateByStatus(Merchant merchant) {
		int result = merchantDao.updateByStatus(merchant);
		return result > 0 ? true : false;
	}

	@Override
	public Merchant getByPurId(Integer purId) {
		return merchantDao.getByPurId(purId);
	}


	@Override
	public Page<Map<String, Object>> getMrhtForHomeByPage(Map<String, Object> params) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		List<Map<String, Object>> data = merchantDao.getMrhtForHomeByPage(page);
		page.setResults(data);
		return page;
	}

	@Override
	public Map<String, Object> getByDistanceAndTime(Map<String, Object> params) {
		return merchantDao.getByDistanceAndTime(params);
	}

    @Override
    public RespData uploadDeviceToken(Map<String, Object> params) {
        int count = merchantDao.updateByMap(params);
        if (count > 0) {
            return RespData.successMsg("上传成功");
        }
        return RespData.errorMsg("上传失败!");
    }

    /**
     * 通知商家有新订单
     * @param orderNo
     */
    @Override
    public void notifyMerchantNewOrder(String orderNo) {
        Order order = orderDao.getOrderByOrderNo(orderNo);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderNo", orderNo);
        String goodsName = "";
        List<OrderItem> items = itemDao.getByMap(data);
        for (OrderItem item : items) {
            goodsName += item.getGoodsName() + ",";
        }
        goodsName = StringUtil.isNotEmpty(goodsName) ? goodsName.substring(0, goodsName.length() - 1) : "";
        data.clear();
        data.put("orderNo", orderNo);
        data.put("receiptName", order.getReceiptName());
        data.put("receiptMobile", order.getReceiptMobile());
        data.put("goodsName", goodsName);
        String title = "新订单通知!";
        String ticker = "您有新的订单，请查收!";
        Merchant merchant = merchantDao.getById(order.getMberId());
        if (merchant.getDeviceType() == DeviceTypeEnum.ANDROID.getCode().byteValue()) { //android设备
            UPushUtil.sendAndroidUnicast(merchant.getDeviceToken(),title,ticker,data.toString(),productionMode);
        } else if (merchant.getDeviceType() == DeviceTypeEnum.IOS.getCode().byteValue()) { //IOSy设备
            UPushUtil.sendIOSUnicast(merchant.getDeviceToken(), title, data.toString(), productionMode);
        }
    }
}