package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.dao.MrhtPurchaseDao;
import com.cndinuo.dao.MrhtPurchaseFlowDao;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.PurchaseStatusEnum;
import com.cndinuo.service.MerchantService;
import com.cndinuo.service.MessageService;
import com.cndinuo.service.MrhtPurchaseFlowService;
import com.cndinuo.service.MrhtPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
* @date 2017-08-31
* @author dgb
* 
*/
@Service("MrhtPurchaseFlowService")
public class MrhtPurchaseFlowServiceImpl extends AbstractService<MrhtPurchaseFlow, Integer> implements MrhtPurchaseFlowService {

	@Autowired
	private MrhtPurchaseFlowDao mrhtPurchaseFlowDao;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MrhtPurchaseService purchaseService;
	@Autowired
	private MrhtPurchaseDao mrhtPurchaseDao;
	@Autowired
	private MerchantService merchantService;


	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mrhtPurchaseFlowDao);
	}

	/**
	 * 保存报价
	 * @param flow
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public synchronized Message save(MrhtPurchaseFlow flow, UserManager user) throws Exception{

		try {
			flow.setSupplierId(user.getId());
			flow.setSupplierName(user.getRealName());
			flow.setIsAccept((byte) 0);
			int insert = mrhtPurchaseFlowDao.insert(flow);
			if (insert > 0) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", flow.getPurId());
				params.put("status", PurchaseStatusEnum.QUOTED_PRICE.getCode());
				purchaseService.updateByMap(params);
			}
			Merchant merchant = merchantService.getByPurId(flow.getPurId());
			Message message = new Message();
			message.setTitle("报价");
			message.setFrom(user.getId());
			message.setFromName(user.getRealName());
			message.setTo(merchant.getId());
			message.setToName(merchant.getMrhtName());
			message.setStatus((byte) 0);
			message.setSendTime(new Date());
			message.setText(String.format(Const.MSG_TEMPLATE_QOUTE,user.getRealName(), flow.getPurId()));
			messageService.insert(message);
			return message;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public RespData quote(MrhtPurchaseFlow flow, UserManager user) {

		try {
 			Map<String, Object> params = new HashMap<String, Object>();
			Message message = new Message();
			String msg = "";
			int id = flow.getId();
			if (flow.getIsAccept() == 1){
				params.put("id", flow.getId());
				params.put("isAccept", flow.getIsAccept());
				params.put("purId", flow.getPurId());
				int result = mrhtPurchaseFlowDao.updateByMap(params);
				flow = mrhtPurchaseFlowDao.getById(flow.getId());
				if(result > 0){
					MrhtPurchase pur = purchaseService.getById(flow.getPurId());
					params.put("status", PurchaseStatusEnum.STAY_DELIVER.getCode());
					params.put("id", flow.getPurId());
					params.put("originalPrice", flow.getQuote());
					params.put("isReturn", flow.getIsReturn());
					params.put("supplierId", flow.getSupplierId());
					params.put("supplierName", flow.getSupplierName());
					params.put("payType", 1);
					params.put("totalPrice", Float.parseFloat(flow.getQuote().toString())*pur.getNum());
					int num = mrhtPurchaseDao.updateByMap(params);
					if (num > 0){
						params.remove("id");
						params.replace("isAccept",2);
						mrhtPurchaseFlowDao.updateByMap(params);
						msg = "您已同意报价请等待发货";
					}
				}
				message.setTitle("同意报价");
				message.setText(String.format(Const.MSG_TEMPLATE_CONFIRM, user.getRealName(),params.get("purId")));
			}else{
				params.put("purId", flow.getPurId());
				params.put("isAccept", 0);
				int flag = mrhtPurchaseFlowDao.getCountByMap(params);

				if(flag != 1){
					params.put("id",flow.getId());
					params.put("isAccept",2);
					params.put("purId", flow.getPurId());
					mrhtPurchaseFlowDao.updateByMap(params);
				}else{
					params.put("id", flow.getPurId());
					params.put("status", PurchaseStatusEnum.STAY_QUOTE.getCode());
					int num = mrhtPurchaseDao.updateByMap(params);
					params.put("id",flow.getId());
					params.put("isAccept",2);
					params.put("purId", flow.getPurId());
					mrhtPurchaseFlowDao.updateByMap(params);
				}
				message.setTitle("拒绝报价");
				message.setText(String.format(Const.MSG_TEMPLATE_CHANCEL,user.getRealName(),params.get("purId")));
				msg = "您已拒绝报价请等待新的报价";
			}
			MrhtPurchaseFlow flows = mrhtPurchaseFlowDao.getById(id);
			message.setFrom(user.getId());
			message.setFromName(user.getRealName());
			message.setTo(flows.getSupplierId());
			message.setToName(flows.getSupplierName());
			message.setStatus((byte) 0);
			message.setSendTime(new Date());
			messageService.insert(message);
			return RespData.successMsg(msg,message);
		}catch (Exception e){
			throw e;
		}
	}

}