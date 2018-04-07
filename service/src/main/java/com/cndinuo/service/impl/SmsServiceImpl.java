package com.cndinuo.service.impl;

import com.alibaba.fastjson.JSON;
import com.cndinuo.base.AbstractService;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.dao.SmsDao;
import com.cndinuo.domain.Sms;
import com.cndinuo.service.SmsService;
import com.cndinuo.utils.HttpManager;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
* @date 2017-09-08
* @author dgb
* 
*/
@Service("smsService")
public class SmsServiceImpl extends AbstractService<Sms, Integer> implements SmsService {

	@Autowired
	private SmsDao smsDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(smsDao);
	}

	/**
	 * 发送短信验证码
	 * @param mobile
	 * @param ip
	 * @return
	 */
	@Override
	public RespData sendCode(String mobile,String ip) {

		if (StringUtil.isEmpty(mobile)) {
			return RespData.errorMsg("手机号不能为空！");
		}

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mobile",mobile);
			params.put("ip", ip);
			Sms sms = this.getOneByMap(params);
			if(sms != null){
				long currectTime = System.currentTimeMillis();
				long createTime = sms.getCreateTime().getTime();
				if ((currectTime - createTime) / 1000 < 120) {	//发送的间隔小于120s不能再发送
					return RespData.errorMsg("请在"+(currectTime - createTime) / 1000 + "s后再发送！");
				}
			}

			params.remove("mobile");
			params.put("date", new Date());
			int ipCount = smsDao.getCountByMap(params);
			if (ipCount >= 10) {		//判断当前ip发送的数量，每个ip每天只能发送10条
				return RespData.errorMsg("今天的操作过于频繁，请明天再试");
			}

			params.remove("ip");
			params.put("mobile", mobile);
			int mobileCount = smsDao.getCountByMap(params);
			if (mobileCount >= 10) {//判断当前mobile发送的数量，每个mobile每天只能发送10条
				return RespData.errorMsg("今天的操作过于频繁，请明天再试");
			}

			String verifCode = StringUtil.randomNum(6);
			Date date = new Date();
			Calendar expireTime = Calendar.getInstance();
			expireTime.add(Calendar.MINUTE, 15);
			sms = new Sms();
			sms.setMobile(mobile);
			sms.setVerifCode(verifCode);
			sms.setIp(ip);
			sms.setCreateTime(date);
			sms.setExpireTime(expireTime.getTime());
			sms.setIsUse((byte) 0);	//未使用
			smsDao.insert(sms);
			//发送短信
			this.sendMsg(mobile,String.format(Const.SMS_VERIF_CODE_TEMPLATE,verifCode));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送短信发生异常：" + e);
			throw e;
		}

		return RespData.successMsg("短信已发送!");
	}

	/**
	 * 校验验证码是否正确
	 * @param mobile
	 * @param verifCode
	 * @return
	 */
	@Override
	public int isCorrect(String mobile, String verifCode) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		params.put("verifCode", verifCode);
		params.put("isUse", 0);		//查询未使用的
		Sms sms = this.getOneByMap(params);
		if (sms == null) {
			return -1;  			//验证码错误
		}
		long currentTime = System.currentTimeMillis();
		long expireTime = sms.getExpireTime().getTime();
		if (expireTime - currentTime < 0) {
			return 0;   			//验证码过期了
		}

		sms.setIsUse((byte) 1);  	//设置为已使用
		sms.setUseTime(new Date());	//使用时间
		smsDao.updateById(sms);
		return 1;					//验证通过
	}

	@Override
	public void sendMsg(String mobile,String content) {
		log.info("开始发送短信  mobile==" + mobile + " @@ content==" + content);
		try {
			content = StringUtil.encode(content);
			String url = String.format(Const.SMS_URL, Const.SMS_USER, Const.SMS_PWD, mobile, content);
			RespData data = HttpManager.doGet(url);
			log.info("发送短信结果：" + JSON.toJSONString(data));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送短信发生异常" + e);
		}
	}

}