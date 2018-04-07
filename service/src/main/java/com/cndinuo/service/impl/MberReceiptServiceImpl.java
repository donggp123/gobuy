package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.MberReceiptDao;
import com.cndinuo.dao.MemberDao;
import com.cndinuo.domain.MberReceipt;
import com.cndinuo.domain.Member;
import com.cndinuo.service.MberReceiptService;
import com.cndinuo.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
* @date 2017-09-09
* @author dgb
* 
*/
@Service("mberReceiptService")
public class MberReceiptServiceImpl extends AbstractService<MberReceipt, Integer> implements MberReceiptService {

	@Autowired
	private MberReceiptDao mberReceiptDao;

	@Autowired
	private MemberDao memberDao;
	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mberReceiptDao);
	}

	@Override
	public RespData add(Map<String, Object> params) throws Exception {
		log.info("开始添加数据");
        if (StringUtil.isEmpty(params.get("token") + "")) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        Member member = memberDao.getByToken(params.get("token").toString());
        if (member == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
        }
        try {
			if (params != null && params.size() > 0) {
				Map<String,Object> m = new HashMap<String, Object>();
                if ("1".equals(params.get("isDefault").toString())){
                    m.put("isDefault", 0);
                    m.put("mberId", member.getId());
                    mberReceiptDao.updateByMap(m);
                }
                MberReceipt receipt = new MberReceipt();
                receipt = (MberReceipt) populateBean(params, receipt);
                receipt.setMberId(member.getId());
                mberReceiptDao.insert(receipt);
                return RespData.successMsg("添加成功");
            }

		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加数据异常：" + e);
			throw e;
		}
        return RespData.successMsg("添加失败！");
	}

	@Override
	public RespData edit(Map<String, Object> params) {
		log.info("查询一条返回到前台");
        if (StringUtil.isEmpty(params.get("token") + "")) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        Member member = memberDao.getByToken(params.get("token").toString());
        if (member == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
        }

        MberReceipt receipt = mberReceiptDao.getById(Integer.parseInt(params.get("id").toString()));
        return RespData.successMsg("", receipt);


	}

	@Override
	public RespData save(Map<String, Object> params) throws Exception {
		log.info("开始保存地址");
        if (StringUtil.isEmpty(params.get("token") + "")) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        Member member = memberDao.getByToken(params.get("token").toString());
        if (member == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
        }
		try {
			if (params != null && params.size() > 0) {
				Map<String, Object> m = new HashMap<String, Object>();
                if ("1".equals(params.get("isDefault").toString())){
                    m.put("isDefault", 0);
                    m.put("mberId", member.getId());
                    mberReceiptDao.updateByMap(m);
                }
                MberReceipt receipt = new MberReceipt();
                receipt = (MberReceipt) populateBean(params, receipt);
                mberReceiptDao.updateById(receipt);
                return RespData.successMsg("地址修改成功");
            }
		}catch (Exception e){
			e.printStackTrace();
			log.error("地址修改发生异常"+e);
			throw e;
		}
        return RespData.successMsg("地址修改失败");
	}

	@Override
	public RespData del(Map<String, Object> params) {
		log.info("开始删除");
		if(StringUtil.isEmpty(params.get("ids")+"")){
            return RespData.errorMsg("请选择要删除的地址!");
        }
        if (StringUtil.isEmpty(params.get("token") + "")) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        Member member = memberDao.getByToken(params.get("token").toString());
        if (member == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
        }
		try {
            params.put("mberId", member.getId());
            mberReceiptDao.deleteByMap(params);
            return RespData.successMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除过程发生异常"+e);
			throw e;
		}
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