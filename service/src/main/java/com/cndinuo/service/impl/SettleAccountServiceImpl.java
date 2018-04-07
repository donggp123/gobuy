package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.dao.*;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.*;
import com.cndinuo.service.SettleAccountService;
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
* @date 2017-09-23
* @author dgb
* 
*/
@Service("settleAccountService")
public class SettleAccountServiceImpl extends AbstractService<SettleAccount, Integer> implements SettleAccountService {

	@Autowired
	private SettleAccountDao settleAccountDao;
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private SysSettingDao settingDao;
	@Autowired
    private OrderDao orderDao;
	@Autowired
    private OrderReturnDao returnDao;
	@Autowired
    private SettleAndOrderDao saoDao;
	@Autowired
    private MrhtAccountDao mrhtAccountDao;
	@Autowired
    private MrhtBalanceDao balanceDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(settleAccountDao);
	}

	/**
	 * 生成结算单
	 */
	@Override
	public void createSettleOrder() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", MrhtStatusEnum.AUDITED.getCode());
            params.put("mrhtType", MrhtTypeEnum.MERCHANT.getCode());
            List<Merchant> mrhts = merchantDao.getByMap(params);
			int settleCycle = StringUtil.toInt(settingDao.getByKey("settle_cycle"));		//结算周期
            String settleRate = settingDao.getByKey("settle_rate");                         //结算比例
            BigDecimal rate = new BigDecimal(settleRate);

            for (Merchant mrht : mrhts) {
                SettleAndOrder sao = new SettleAndOrder();
                BigDecimal salesAmount = new BigDecimal(0);         //销售金额
                BigDecimal empAmount = new BigDecimal(0);           //佣金金额
                BigDecimal settleAmount = new BigDecimal(0);        //结算金额
                BigDecimal deliveryFee = new BigDecimal(0);         //配送费
                BigDecimal retPrice = new BigDecimal(0);            //退款金额

                SettleAccount account = new SettleAccount();
                Integer settleId = settleAccountDao.getLastSettleNo();
                if (settleId == null) {
                    settleId = 0;
                }
                String settleNo = "S" + StringUtil.formatDate(new Date(),"yyyyMMddHHmmss")+(settleId+1);
                params.clear();
                params.put("settleCycle", settleCycle);
                params.put("orderStatus", OrderStatusEnum.ORDER_COMPLETE.getCode());
                params.put("settleStatus", SettleStatusEnum.STAY_SETTLE.getCode());
                params.put("orderType", 1);
                params.put("mrhtId", mrht.getId());
                List<Order> orders = orderDao.getByMap(params);
                params.put("retStatus", OrderStatusEnum.RETURN_COMPLETE.getCode());
                List<OrderReturn> returns = returnDao.getByMap(params);

                for (Order order : orders) {
                    salesAmount = salesAmount.add(order.getSalesPrice());
                    if (order.getDeliveryType() == DeliveryTypeEnum.mrht_delivery.getCode().byteValue()) {
                        deliveryFee = deliveryFee.add(order.getDeliveryFee());
                    }
                    params.replace("settleStatus", SettleStatusEnum.SETTLE_ING.getCode());
                    params.put("version", order.getVersion());
                    params.put("orderNo", order.getOrderNo());
                    orderDao.updateByStatus(params);
                    sao.setOrderNo(order.getOrderNo());
                    sao.setSettleNo(settleNo);
                    sao.setType(SettleOrderTypeEnum.normal_order.getCode().byteValue());           //类型：1 正常订单，2 退款单
                    saoDao.insert(sao);
                }
                params.remove("orderNo");
                for (OrderReturn ret : returns) {
                    retPrice = retPrice.add(ret.getActualAmount());
                    params.put("retNo", ret.getRetNo());
                    params.replace("settleStatus", SettleStatusEnum.SETTLE_ING.getCode());
                    returnDao.updateByMap(params);
                    sao.setOrderNo(ret.getOrderNo());
                    sao.setRetNo(ret.getRetNo());
                    sao.setSettleNo(settleNo);
                    sao.setType(SettleOrderTypeEnum.return_order.getCode().byteValue());           //类型：1 正常订单，2 退款单
                    saoDao.insert(sao);
                }

                if (orders != null && orders.size() > 0) {
                    account.setMrhtId(mrht.getId());
                    account.setCreateTime(new Date());
                    account.setSettlePeriod(new Date());
                    account.setMrhtName(mrht.getMrhtName());
                    account.setRate(rate);
                    account.setSettleNo(settleNo);
                    account.setSettleStatus(SettleStatusEnum.STAY_SETTLE.getCode().byteValue());
                    account.setRetAmount(retPrice);
                    BigDecimal temp = salesAmount.subtract(retPrice);
                    empAmount = temp.multiply(rate).divide(BigDecimal.valueOf(100));
                    settleAmount = temp.subtract(empAmount);
                    settleAmount = settleAmount.add(deliveryFee);
                    account.setSalesAmount(salesAmount);
                    account.setEmpAmount(empAmount);
                    account.setSettleAmount(settleAmount);
                    account.setDeliveryFee(deliveryFee);
                    settleAccountDao.insert(account);
                }
            }

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

    @Override
    public List<Order> getSettleOrderBySettleNo(String settleNo) {
        List<Order> orders = settleAccountDao.getSettleOrderBySettleNo(settleNo);
        return orders;
    }

    @Override
    public List<OrderReturn> getSettleReturnOrderBySettleNo(String settleNo) {
        List<OrderReturn> returns = settleAccountDao.getSettleReturnOrderBySettleNo(settleNo);
        return returns;
    }

    @Override
    public List<OrderDelivery> getOrderDeliveryByMrhtId(Integer mrhtId) {
        List<OrderDelivery> deliveries = settleAccountDao.getOrderDeliveryByMrhtId(mrhtId);
        return deliveries;
    }

    @Override
    public RespData settle(Integer id) {
        try {
            SettleAccount settle = settleAccountDao.getById(id);

            //更新订单为结算完成
            settleAccountDao.updateOrderToSettled(settle.getSettleNo());

            //更新退款单为结算完成
            settleAccountDao.updateOrderReturnToSettled(settle.getSettleNo());

            //更新结算单为结算完成
            settle.setSettleStatus(SettleStatusEnum.SETTLE_COMPLATE.getCode().byteValue());
            settle.setSettleTime(new Date());
            settleAccountDao.updateById(settle);

            //更新商家账户表
            MrhtAccount account = mrhtAccountDao.getById(settle.getMrhtId());
            BigDecimal amount = account.getAmount();
            if (amount == null) {
                amount = new BigDecimal(0);
            }
            BigDecimal settleAmount = settle.getSettleAmount();
            if (settleAmount == null) {
                settleAmount = new BigDecimal(0);
            }
            amount = amount.add(settleAmount);
            account.setAmount(amount);
            mrhtAccountDao.updateById(account);

            //添加商家账户明细
            MrhtBalance balance = new MrhtBalance();
            balance.setAmount(settle.getSettleAmount());
            balance.setCreateTime(new Date());
            balance.setMrhtId(settle.getMrhtId());
            balance.setType(BalanceTypeEnum.income.getCode().byteValue());
            balanceDao.insert(balance);
            return RespData.successMsg("结算成功!");
        } catch (Exception e) {
            e.printStackTrace();
            RespData.errorMsg("结算订单发生异常,请稍后处理!");
            throw e;
        }
    }
}