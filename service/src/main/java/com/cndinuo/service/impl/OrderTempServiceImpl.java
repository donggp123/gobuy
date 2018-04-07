package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.*;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.OrderStatusEnum;
import com.cndinuo.eunm.OrderTrackEnum;
import com.cndinuo.eunm.OrderTypeEnum;
import com.cndinuo.service.OrderTempService;
import com.cndinuo.service.OrderTrackService;
import com.cndinuo.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @date 2017-09-15
* @author dgb
* 
*/
@Service("orderTempService")
public class OrderTempServiceImpl extends AbstractService<OrderTemp, Integer> implements OrderTempService {

	@Autowired
	private OrderTempDao orderTempDao;
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private MberReceiptDao receiptDao;
	@Autowired
    private MemberDao memberDao;
	@Autowired
    private MrhtGoodsDao goodsDao;
	@Autowired
    private OrderTrackService trackService;
	@Autowired
    private OrderDao orderDao;
	@Autowired
    private OrderItemDao orderItemDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(orderTempDao);
	}


	@Override
	public synchronized RespData save(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException {
		String token = params.get("token")+"";
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}

        try {
            Member m = memberDao.getByToken(token);
            if (m == null) {
                return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
            }
            Map<String, String> retData = new HashMap<String, String>();
            //生成订单号
            Integer orderId = orderTempDao.getLastOrderId();
            if (orderId == null) {
                orderId = 0;
            }
            String orderNo = StringUtil.formatDate(new Date(),"yyyyMMddHHmmss")+(orderId+1);

            Byte orderType = Byte.parseByte(params.get("orderType")+"");            //订单类型：1 购物，2 充值
            if (orderType == OrderTypeEnum.ORDER_SHOPPING.getCode().byteValue()) {
                Merchant merchant = merchantDao.getById(StringUtil.toInt(params.get("mrhtId")));
                if (merchant == null) {
                    return RespData.errorMsg("商家信息不能为空");
                }
                MberReceipt receipt = receiptDao.getById(StringUtil.toInt(params.get("receiptId")));
                if (receipt == null) {
                    return RespData.errorMsg("请选择收货地址!");
                }
                OrderTemp temp = new OrderTemp();
                temp.setMberId(m.getId());
                temp.setMrhtId(merchant.getId());
                temp.setMrhtName(merchant.getMrhtName());
                temp.setOrderType(orderType);
                temp.setOrderStatus(OrderStatusEnum.ORDER_TEMP_UNPAID.getCode().byteValue());
                temp.setOrderNo(orderNo);
                temp.setReceiptAddr(receipt.getAddr());
                temp.setReceiptMobile(receipt.getMobile());
                temp.setReceiptName(receipt.getName());
                temp.setTotalPrice(BigDecimal.valueOf(Double.parseDouble(params.get("totalPrice")+"")));
                temp.setOrderTime(new Date());
                temp.setDeliveryFee(BigDecimal.valueOf(Double.parseDouble(params.get("deliveryFee")+"")));
                temp.setDeliveryTime(StringUtil.toDate(params.get("deliveryTime")+"","yyyy-MM-dd"));
                List<Map<String, Object>> items = (List<Map<String, Object>>) params.get("items");
                for (Map<String,Object> item : items){
                    BeanUtils.populate(temp, item);
                    MrhtGoods goods = goodsDao.getById(StringUtil.toInt(item.get("goodsId")));
                    if (goods.getStatus() == 0) {
                        return RespData.errorMsg("商品已下架");
                    }
                    if (goods.getStockNum() <= 0) {
                        return RespData.errorMsg("商品【"+goods.getGoodsName()+"】库存量不足!");
                    }
                    int stockNum = goods.getStockNum();
                    int num = StringUtil.toInt(item.get("num"));
                    if (stockNum < num) {
                        return RespData.errorMsg("商品【"+goods.getGoodsName()+"】库存量不足!");
                    }
                    goods.setStockNum(stockNum - num);              //减去库存
                    orderTempDao.insert(temp);
                    goodsDao.updateById(goods);
                }
                trackService.saveTrack(orderNo,OrderTrackEnum.ORDER_CONFIRM.getCode().byteValue());//保存订单跟踪记录
                retData.put("orderNo", orderNo);
                return RespData.successMsg("提交订单成功", retData);
            }else if(orderType == OrderTypeEnum.ORDER_RECHARGE.getCode().byteValue()){
                OrderTemp temp = new OrderTemp();
                temp.setGoodsName("用户充值");
                temp.setOrderNo(orderNo);
                temp.setMberId(m.getId());
                temp.setTotalPrice(BigDecimal.valueOf(Double.parseDouble(params.get("totalPrice")+"")));
                temp.setOrderTime(new Date());
                temp.setOrderStatus(OrderStatusEnum.ORDER_TEMP_UNPAID.getCode().byteValue());
                temp.setOrderType(orderType);
                temp.setRemark("向平台充值");
                orderTempDao.insert(temp);
                trackService.saveTrack(orderNo,OrderTrackEnum.ORDER_CONFIRM.getCode().byteValue());//保存订单跟踪记录
                retData.put("orderNo", orderNo);
                return RespData.successMsg("提交订单成功", retData);
            }
        } catch (Exception e) {
		    e.printStackTrace();
            log.info("用户提交订单保存时发生异常:" + e);
            throw e;
        }
        return RespData.errorMsg("提交订单失败!");
    }
    
    @Override
    public RespData recur(String orderNo, String token) {
        if (StringUtil.isEmpty(token)) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        if (StringUtil.isEmpty(orderNo)) {
            return RespData.errorMsg("订单号不能为空");
        }
        Map<String, String> retData = new HashMap<String, String>();
        try {
            Member m = memberDao.getByToken(token);
            if (m == null) {
                return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
            }

            //生成订单号
            Integer orderId = orderTempDao.getLastOrderId();
            if (orderId == null) {
                orderId = 0;
            }
            String newOrderNo = StringUtil.formatDate(new Date(),"yyyyMMddHHmmss")+(orderId+1);
            Order order = orderDao.getOrderByOrderNo(orderNo);
            List<OrderItem> items = orderItemDao.getItemsByOrderNo(orderNo);
            OrderTemp temp = new OrderTemp();
            temp.setOrderStatus(OrderStatusEnum.ORDER_TEMP_UNPAID.getCode().byteValue());
            temp.setDeliveryFee(order.getDeliveryFee());
            temp.setMberId(m.getId());
            temp.setOrderType((byte) 1);
            temp.setMrhtId(order.getMrhtId());
            temp.setMrhtName(order.getMrhtName());
            temp.setOrderTime(new Date());
            temp.setTotalPrice(order.getTotalPrice());
            temp.setOrderNo(newOrderNo);
            temp.setReceiptName(order.getReceiptName());
            temp.setReceiptMobile(order.getReceiptMobile());
            temp.setReceiptAddr(order.getReceiptAddr());
            for (OrderItem item : items) {
                MrhtGoods goods = goodsDao.getById(item.getGoodsId());
                if (goods.getStatus() == 0) {
                    return RespData.errorMsg("商品已下架");
                }
                if (goods.getStockNum() <= 0) {
                    return RespData.errorMsg("商品【"+goods.getGoodsName()+"】库存量不足!");
                }
                int stockNum = goods.getStockNum();
                int num = item.getNum();
                if (stockNum < num) {
                    return RespData.errorMsg("商品【"+goods.getGoodsName()+"】库存量不足!");
                }
                temp.setGoodsId(goods.getId());
                temp.setGoodsName(goods.getGoodsName());
                temp.setNum(item.getNum());
                temp.setUnitPrice(item.getUnitPrice());
                temp.setPayPrice(item.getPayPrice());
                orderTempDao.insert(temp);
                goods.setStockNum(stockNum - num);              //减去库存
                goodsDao.updateById(goods);
            }
            retData.put("orderNo",newOrderNo);
            retData.put("totalPrice", StringUtil.toString(order.getTotalPrice()));
            return RespData.successMsg("提交订单成功",retData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("用户再来一单发生异常:" + e);
            throw e;
        }
    }
}