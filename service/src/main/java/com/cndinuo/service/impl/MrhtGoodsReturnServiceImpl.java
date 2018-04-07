package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.Const;
import com.cndinuo.dao.MessageDao;
import com.cndinuo.dao.MrhtGoodsReturnDao;
import com.cndinuo.domain.Message;
import com.cndinuo.domain.MrhtGoods;
import com.cndinuo.domain.MrhtGoodsReturn;
import com.cndinuo.domain.UserManager;
import com.cndinuo.eunm.PurchaseStatusEnum;
import com.cndinuo.service.MrhtGoodsReturnService;
import com.cndinuo.service.MrhtGoodsService;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
* @date 2017-09-04
* @author dgb
* 
*/
@Service("mrhtGoodsReturnService")
public class MrhtGoodsReturnServiceImpl extends AbstractService<MrhtGoodsReturn, Integer> implements MrhtGoodsReturnService {

	@Autowired
	private MrhtGoodsReturnDao mrhtGoodsReturnDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private MrhtGoodsService goodsService;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mrhtGoodsReturnDao);
	}


	@Override
	public int update(MrhtGoodsReturn record, UserManager user) {
		return super.updateById(record);
	}

	@Override
	public Message save(MrhtGoodsReturn ret, UserManager user) {
		Message message = new Message();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mrhtId", user.getId());
			params.put("barCode", ret.getBarCode());
			MrhtGoods goods = goodsService.getOneByMap(params);
			if(goods != null && goods.getSupplierId() > 0 && (goods.getStockNum() >= ret.getRetNum())){
				params.put("stockNum",goods.getStockNum()-ret.getRetNum());
				goodsService.updateByMap(params);
				if (StringUtil.isEmpty(ret.getGoodsName())){
					ret.setGoodsName(goods.getGoodsName());
				}
				ret.setMrhtId(user.getId());
				ret.setRetStatus(PurchaseStatusEnum.STAY_AGREE.getCode().byteValue());
				ret.setGoodsSpec(goods.getGoodsSpec());
				ret.setGoodsType(goods.getGoodsType());
				ret.setSupplierId(goods.getSupplierId());
				ret.setSupplierName(goods.getSupplierName());
				ret.setRetPrice(new BigDecimal(ret.getRetNum()*goods.getOriginalPrice().doubleValue()));
				ret.setRetTime(new Date());
				ret = super.insert(ret);
			}else{
				return message;
			}
			message.setTitle("退货申请");
			message.setText(String.format(Const.MSG_TEMPLATE_RETURN,user.getRealName(),ret.getId()));
			message.setFrom(user.getId());
			message.setFromName(user.getRealName());
			message.setTo(ret.getSupplierId());
			message.setToName(ret.getSupplierName());
			message.setSendTime(new Date());
			message.setStatus((byte) 0);
			messageDao.insert(message);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return message;
	}

	@Override
	public Message retGoods(Integer num, Integer id,String remark, UserManager user) {
        try {
            Map<String, Object> params = new HashMap<String,Object>();
            params.put("retStatus",num);
            params.put("id",id);
            params.put("remark",remark);
            mrhtGoodsReturnDao.updateByMap(params);
			MrhtGoodsReturn goodsReturn = mrhtGoodsReturnDao.getById(id);
			MrhtGoods goods = goodsService.getOneByMap(params);
			Message message = new Message();
            if(num == PurchaseStatusEnum.REJECT_RETURN.getCode().intValue()){
				params.clear();
				params.put("mrhtId", goodsReturn.getMrhtId());
				params.put("barCode", goodsReturn.getBarCode());
				params.put("stockNum",goods.getStockNum()+goodsReturn.getRetNum());
				goodsService.updateByMap(params);
                message.setTitle("拒绝退货");
                message.setText(String.format(Const.MSG_TEMPLATE_REJECT_RETURN,user.getRealName(),id));
            } else if (num == PurchaseStatusEnum.RET_COMPLETE.getCode().intValue()) {
                message.setTitle("同意退货");
                message.setText(String.format(Const.MSG_TEMPLATE_RETUTN_COMPLETE,user.getRealName(),id));
            }
            MrhtGoodsReturn ret = mrhtGoodsReturnDao.getById(id);
            message.setFrom(user.getId());
            message.setFromName(user.getRealName());
            message.setTo(ret.getMrhtId());
            message.setToName(ret.getMrhtName());
            message.setStatus((byte) 0);
            message.setSendTime(new Date());
            messageDao.insert(message);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
	}
}