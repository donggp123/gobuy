package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.dao.MrhtPurchaseDao;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.PurchaseStatusEnum;
import com.cndinuo.service.MessageService;
import com.cndinuo.service.MrhtGoodsService;
import com.cndinuo.service.MrhtPurchaseFlowService;
import com.cndinuo.service.MrhtPurchaseService;
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
* @date 2017-08-31
* @author dgb
* 
*/
@Service("MrhtPurchaseService")
public class MrhtPurchaseServiceImpl extends AbstractService<MrhtPurchase, Integer> implements MrhtPurchaseService {

	@Autowired
	private MrhtPurchaseDao mrhtPurchaseDao;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MrhtPurchaseFlowService mrhtPurchaseFlowService;

	@Autowired
	private MrhtGoodsService mrhtGoodsService;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mrhtPurchaseDao);
	}

	@Override
	public RespData butt(MrhtPurchase mrhtPurchase, UserManager user) {
		try {
			Message message = new Message();
			String msg = "";
			if (mrhtPurchase.getId() != null && mrhtPurchase.getId() > 0) {
				mrhtPurchase = mrhtPurchaseDao.getById(mrhtPurchase.getId());
				if (mrhtPurchase.getStatus() != null && mrhtPurchase.getStatus() == PurchaseStatusEnum.STAY_DELIVER.getCode().byteValue()) {
					mrhtPurchase.setStatus(PurchaseStatusEnum.STAY_RECEIPT.getCode().byteValue());
					msg = "您已确认发货请等待收款";
					message.setTitle("确认发货");
					message.setText(String.format(Const.MSG_TEMPLATE_DELIVER, user.getRealName(),mrhtPurchase.getId()));
				} else if (mrhtPurchase.getStatus() != null && mrhtPurchase.getStatus() == PurchaseStatusEnum.STAY_RECEIVABLES.getCode().byteValue()) {
					mrhtPurchase.setStatus(PurchaseStatusEnum.PUR_COMPLETE.getCode().byteValue());
					msg = "您已确认收款，订单完成";
					message.setTitle("确认收款");
					message.setText(String.format(Const.MSG_TEMPLATE_RECEIVABLES, user.getRealName(),mrhtPurchase.getId()));
				}
				mrhtPurchaseDao.updateById(mrhtPurchase);
				message.setFrom(mrhtPurchase.getSupplierId());
				message.setFromName(mrhtPurchase.getSupplierName());
				message.setTo(mrhtPurchase.getMrhtId());
				message.setToName(mrhtPurchase.getMrhtName());
				message.setStatus((byte) 0);
				message.setSendTime(new Date());
				messageService.insert(message);
				return RespData.successMsg(msg,message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RespData.errorMsg("系统异常");
		}
		return RespData.errorMsg("操作失败");
	}

	@Override
	public synchronized Message confirms(Integer id, UserManager user) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			params.put("status", PurchaseStatusEnum.STAY_RECEIVABLES.getCode());
			mrhtPurchaseDao.updateByMap(params);
			MrhtPurchase purchase = mrhtPurchaseDao.getById(id);
			params.clear();
			params.put("mrhtId", user.getId());
			params.put("barCode", purchase.getBarCode());
			MrhtGoods goods = mrhtGoodsService.getOneByMap(params);
			if (goods !=null){
				params.put("stockNum", (goods.getStockNum()+purchase.getNum()));
				params.put("supplierId", purchase.getSupplierId());
				params.put("supplierName", purchase.getSupplierName());
				params.put("goodsType", purchase.getGoodsType());
				params.put("goodsSpec", purchase.getGoodsSpec());
				params.put("originalPrice", purchase.getOriginalPrice());
				mrhtGoodsService.updateByMap(params);
			}else{
				params.clear();
				params.put("mrhtId", purchase.getSupplierId());
				params.put("barCode", purchase.getBarCode());
				goods = mrhtGoodsService.getOneByMap(params);
				goods.setMrhtId(user.getId());
				goods.setStockNum(purchase.getNum());
				goods.setGoodsType(purchase.getGoodsType());
				goods.setGoodsSpec(purchase.getGoodsSpec());
				goods.setSupplierId(purchase.getSupplierId());
				goods.setSupplierName(purchase.getSupplierName());
				goods.setOriginalPrice(goods.getSellPrice());
				goods.setSellPrice(new BigDecimal(0));
				goods.setStatus((byte) 0);
				goods.setDeleted((byte) 0);
				mrhtGoodsService.insert(goods);
			}
			params.clear();
			params.put("purId", purchase.getId());
			params.put("isAccept", 1);
			MrhtPurchaseFlow flow = mrhtPurchaseFlowService.getOneByMap(params);
			Message message = new Message();
			message.setTitle("已收货");
			message.setText(String.format(Const.MSG_TEMPLATE_RECEIVE,user.getRealName(),flow.getPurId()));
			message.setFrom(user.getId());
			message.setFromName(user.getRealName());
			message.setTo(flow.getSupplierId());
			message.setToName(flow.getSupplierName());
			message.setStatus((byte) 0);
			message.setSendTime(new Date());
			messageService.insert(message);
			return message;
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	public List<MrhtPurchaseFlow> cancel(Integer id, UserManager user) {
		try {
			mrhtPurchaseDao.deleteById(id);
			MrhtPurchase purchase = mrhtPurchaseDao.getById(id);
			Map<String, Object> params = new HashMap<String, Object>();
			if (purchase.getStatus() == PurchaseStatusEnum.STAY_RECEIVABLES.getCode().byteValue()){
				params.put("mrhtId", user.getId());
				params.put("barCode", purchase.getBarCode());
				MrhtGoods goods = mrhtGoodsService.getOneByMap(params);
				params.put("stockNum", goods.getStockNum() - purchase.getNum());
				mrhtGoodsService.updateByMap(params);
			}
			List<MrhtPurchaseFlow> flow = null;
			params.clear();
			params.put("purId", id);
			params.put("isAccept", 2);
			mrhtPurchaseFlowService.deleteByMap(params);
			flow= mrhtPurchaseFlowService.getByMap(params);
			return flow;
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	public MrhtPurchase save(MrhtPurchase mrhtPurchase, UserManager user) {
		try {
			mrhtPurchase.setStatus(PurchaseStatusEnum.STAY_QUOTE.getCode().byteValue());
			mrhtPurchase.setMrhtId(user.getId());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("barCode", mrhtPurchase.getBarCode());
			MrhtGoods goods = mrhtGoodsService.getOneByMap(params);
			if (goods != null) {
				if (StringUtil.isEmpty(mrhtPurchase.getGoodsName())) {
					mrhtPurchase.setGoodsName(goods.getGoodsName());
				}
				mrhtPurchase.setGoodsSpec(goods.getGoodsSpec());
				mrhtPurchase.setGoodsType(goods.getGoodsType());
			}
			mrhtPurchase = this.insert(mrhtPurchase);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return mrhtPurchase;
	}
}