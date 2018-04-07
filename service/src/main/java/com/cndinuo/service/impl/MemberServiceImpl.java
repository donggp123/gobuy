package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.dao.MberInfoDao;
import com.cndinuo.dao.MberOpinionDao;
import com.cndinuo.dao.MemberDao;
import com.cndinuo.domain.MberInfo;
import com.cndinuo.domain.MberOpinion;
import com.cndinuo.domain.Member;
import com.cndinuo.eunm.MberLogTypeEnum;
import com.cndinuo.eunm.MberTypeEnum;
import com.cndinuo.service.MemberService;
import com.cndinuo.service.SmsService;
import com.cndinuo.service.SysSettingService;
import com.cndinuo.service.UploadService;
import com.cndinuo.utils.Base64;
import com.cndinuo.utils.MD5;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
* @date 2017-09-08
* @author dgb
* 
*/
@Service("memberService")
public class MemberServiceImpl extends AbstractService<Member, Integer> implements MemberService {

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private SmsService smsService;
	@Autowired
	private MberInfoDao mberInfoDao;
	@Autowired
	private UploadService uploadService;
	@Autowired
	private MberOpinionDao opinionDao;
	@Autowired
	private SysSettingService settingService;


	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(memberDao);
	}

	@Override
	public Member getByMobile(String mobile) {
		return memberDao.getByMobile(mobile);
	}

	@Override
	public Member getByToken(String token) {
		return memberDao.getByToken(token);
	}

	@Override
	public RespData register(String mobile, String password, String verifCode) {
		log.info("用户开始注册");
		if (StringUtil.isEmpty(mobile)) {
			return RespData.errorMsg("手机号不能为空！");
		}
		if (StringUtil.isEmpty(password)) {
			return RespData.errorMsg("密码不能为空！");
		}
		if (StringUtil.isEmpty(verifCode)) {
			return RespData.errorMsg("验证码不能为空！");
		}

		try {
			int result = smsService.isCorrect(mobile, verifCode);
			switch (result) {
				case -1:
					return RespData.errorMsg("验证码错误！");
				case 0:
					return RespData.errorMsg("验证码已过期！请重新发送");
			}

			Member m = memberDao.getByMobile(mobile);
			if (m != null) {
				return RespData.errorMsg("该手机号已经注册过了,请换其它手机注册！");
			}

			String md5Pwd = MD5.encode(password);
			String salt = Base64.encode((mobile+md5Pwd).getBytes()).replace("\n","");

			m = new Member();
			m.setMobile(mobile);
			m.setInitPwd(password);
			m.setPassword(StringUtil.saltCode(salt,md5Pwd));
			m.setSalt(salt);
			m.setLogType(MberLogTypeEnum.MOBILE.getCode().byteValue()) ;        //登录方式：1 手机号，2 微信，3 qq，4 微博
			m.setMberType(MberTypeEnum.NORMAL_MBER.getCode().byteValue());  	// 注册默认为 1 普通会员

			String token = StringUtil.token(mobile);	//生成token
			m.setToken(token);
			memberDao.insert(m);

			Date date = new Date();
			MberInfo info = new MberInfo();
			info.setNickName(StringUtil.getRandStr(8)+"_"+m.getId());       //设置默认昵称
			info.setMberId(m.getId());
			info.setRegisterTime(date);
			info.setLastLogTime(date);
			info.setLogCount(1);
			mberInfoDao.insert(info);

			Map<String, Object> retData = new HashMap<String, Object>();
			retData.put("token", token);
			retData.put("mberType", m.getMberType());
			return RespData.successMsg("注册成功",retData);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("注册失败，系统异常：" + e);
			throw e;
		}
	}


	@Override
	public RespData login(String mobile, String password) {
		if (StringUtil.isEmpty(mobile)) {
			return RespData.errorMsg("手机号不能为空");
		}
		if (StringUtil.isEmpty(password)) {
			return RespData.errorMsg("密码不能为空!");
		}

		try {
			password = MD5.encode(password);
			Member m = memberDao.getByMobile(mobile);
			if (m == null) {
				return RespData.errorMsg("用户不存在！");
			}
			password = StringUtil.saltCode(m.getSalt(),password);
			if (!m.getPassword().equals(password)) {
				return RespData.errorMsg("登录密码错误!");
			}

			String token = StringUtil.token(mobile);			//每次登录都生成新的token
			m.setToken(token);
			memberDao.updateById(m);

			MberInfo info = mberInfoDao.getById(m.getId());

			info.setLastLogTime(new Date());
			info.setLogCount(info.getLogCount()+1);
			mberInfoDao.updateById(info);
			Map<String, Object> retData = new HashMap<String,Object>();
			retData.put("token", token);
			retData.put("mberType", m.getMberType());
			return RespData.successMsg("登录成功！", retData);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("登录失败，系统异常："+e);
			throw e;
		}
	}

	@Override
	public RespData editNickName(String token, String nickName) {
		try {

			if (StringUtil.isEmpty(token)) {
				return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
			}
			if(StringUtil.isEmpty(nickName)){
				return RespData.errorMsg("昵称不能为空！");
			}
			Member m = memberDao.getByToken(token);
			if (m == null) {
				return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已经过期，请重新登录");
			}

			MberInfo info = new MberInfo();
			info.setMberId(m.getId());
			info.setNickName(nickName);
			mberInfoDao.updateById(info);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("昵称修改失败！" + e);
			throw e;
		}
		return RespData.successMsg("修改成功！");
	}


	@Override
	public RespData uploadHeadIcon(String token, String headIcon) {
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		if (StringUtil.isEmpty(headIcon)) {
			return RespData.errorMsg("请选择上传图片");
		}
		Member m = memberDao.getByToken(token);
		if (m == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		try {
			String filePath = uploadService.uploadByteArr(headIcon, String.format("%s.jpg", token));
			if (filePath != null) {
				String[] strs = filePath.split("@");
				String imgServer = strs[0];
				String imgPath = strs[1];
				MberInfo info = new MberInfo();
				info.setMberId(m.getId());
				info.setHeadIcon(imgPath);
				mberInfoDao.updateById(info);
				Map<String, Object> retData = new HashMap<String, Object>();
				retData.put("headIcon", String.format("%s/%s", imgServer, imgPath));
				return RespData.successMsg("上传成功", retData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("上传失败，系统异常：" + e);
			throw e;
		}
		return RespData.errorMsg("上传失败");
	}

	@Override
	public RespData opinion(String token, String content) {
		log.info("开始保存反馈意见");
		if (StringUtil.isEmpty(token)) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}
		if (StringUtil.isEmpty(content)) {
			return RespData.errorMsg("反馈意见不能为空");
		}
		Member m = memberDao.getByToken(token);
		if (m == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		try {
			MberOpinion opinion = new MberOpinion();
			opinion.setMberId(m.getId());
			opinion.setContent(content);
			opinion.setOpTime(new Date());
			opinionDao.insert(opinion);
			log.info("反馈意见保存成功");
			return RespData.successMsg("您的意见我们已收到");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("保存失败：" + e);
			throw e;
		}
	}

	@Override
	public RespData modifyMobile(String mobile, String newMobile, String verifCode) {
		if (StringUtil.isEmpty(mobile)) {
			return RespData.errorMsg("旧手机号不能为空！");
		}
		if (StringUtil.isEmpty(newMobile)) {
			return RespData.errorMsg("新手机号不能为空！");
		}
		if (mobile.equals(newMobile)) {
			return RespData.errorMsg("旧手机号与新手机号一致，不需要修改！");
		}
		int result = smsService.isCorrect(newMobile,verifCode);
		switch (result){
			case -1:
				return RespData.errorMsg("验证码错误！");
			case 0:
				return RespData.errorMsg("验证码已过期！请重新发送");
		}
		try {

			Member m = memberDao.getByMobile(mobile);
			if (m == null) {
				return RespData.errorMsg("旧手机号不正确");
			}
			String md5Pwd = MD5.encode(m.getInitPwd());
			String salt = Base64.encode((newMobile+md5Pwd).getBytes()).replace("\n","");
			m.setMobile(newMobile);
			m.setPassword(StringUtil.saltCode(salt,md5Pwd));
			m.setSalt(salt);
			memberDao.updateById(m);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改手机号发生异常：" + e);
			throw e;
		}
		return RespData.successMsg("修改成功");
	}


    @Override
    public RespData forgotPwd(String mobile, String verifCode, String newPwd) {
        if (StringUtil.isEmpty(mobile)) {
            return RespData.errorMsg("手机号不能为空!");
        }
        if (StringUtil.isEmpty(verifCode)) {
            return RespData.errorMsg("验证码不能为空!");
        }
        if (StringUtil.isEmpty(newPwd)) {
            return RespData.errorMsg("新密码不能为空!");
        }
        int result = smsService.isCorrect(mobile, verifCode);
        switch (result){
            case -1:
                return RespData.errorMsg("验证码错误！");
            case 0:
                return RespData.errorMsg("验证码已过期！请重新发送");
        }
        try {

            Member m = memberDao.getByMobile(mobile);
            if (m == null) {
                return RespData.errorMsg("手机号不正确");
            }
            String md5Pwd = MD5.encode(newPwd);
            String salt = Base64.encode((mobile+md5Pwd).getBytes()).replace("\n","");
            m.setPassword(StringUtil.saltCode(salt,md5Pwd));
            m.setSalt(salt);
            m.setInitPwd(newPwd);
            memberDao.updateById(m);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改手机号发生异常：" + e);
            throw e;
        }
        return RespData.successMsg("修改成功");
    }

    @Override
    public RespData modifyPwd(Map<String, String> params) {
        String token = params.get("token");
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        String confirmPwd = params.get("confirmPwd");
        RespData data = verifParams(token,oldPwd,newPwd,confirmPwd);
        if (data != null) {
            return data;
        }

        try {
            Member m = memberDao.getByToken(token);
            if (m == null) {
                return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
            }
            if (!oldPwd.equals(m.getInitPwd())) {
                return RespData.errorMsg("当前密码不正确!");
            }
            String md5Pwd = MD5.encode(newPwd);
            String salt = Base64.encode((m.getMobile()+md5Pwd).getBytes()).replace("\n","");
            m.setPassword(StringUtil.saltCode(salt,md5Pwd));
            m.setSalt(salt);
            m.setInitPwd(newPwd);
            memberDao.updateById(m);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改密码发生异常：" + e);
            throw e;
        }
        return RespData.successMsg("修改成功!");
    }

    private RespData verifParams(String token,String oldPwd,String newPwd,String confirmPwd) {
        if (StringUtil.isEmpty(token)) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        if(StringUtil.isEmpty(oldPwd)){
            return RespData.errorMsg("当前密码不能为空!");
        }
        if (StringUtil.isEmpty(newPwd)) {
            return RespData.errorMsg("新密码不能为空");
        }
        if (StringUtil.isEmpty(confirmPwd)) {
            return RespData.errorMsg("确认新密码不能为空!");
        }
        if (oldPwd.equals(newPwd)) {
            return RespData.errorMsg("当前密码与新密码一致!");
        }
        if (!newPwd.equals(confirmPwd)) {
            return RespData.errorMsg("新密码与确认新密码不一致!");
        }
        return null;
    }

    @Override
    public RespData oauth2(String uid, String oauthType, String photoUrl, String nickName) {

        if (StringUtil.isEmpty(uid)) {
            return RespData.errorMsg("用户标识不能为空!");
        }
        if (StringUtil.isEmpty(oauthType)) {
            return RespData.errorMsg("用户登录方式不能为空!");
        }
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("openId", uid);
            params.put("logType", oauthType);
            Member m = this.getOneByMap(params);
            String token = StringUtil.token(uid);	//生成token
            if (m == null) {                        //为空时，第一次登录注册
                m = new Member();
                m.setMobile("");
                m.setInitPwd("");
                m.setPassword("");
                m.setSalt("");
                m.setLogType(Byte.parseByte(oauthType));
                m.setMberType(MberTypeEnum.NORMAL_MBER.getCode().byteValue());  			// 注册默认为 1 普通会员
                m.setToken(token);
                memberDao.insert(m);

                Date date = new Date();
                MberInfo info = new MberInfo();
                info.setHeadIcon(photoUrl);
                info.setNickName(nickName);
                info.setMberId(m.getId());
                info.setRegisterTime(date);
                info.setLastLogTime(date);
                info.setLogCount(1);
                mberInfoDao.insert(info);
            }else{
                m.setToken(token);
                memberDao.updateById(m);

                MberInfo info = mberInfoDao.getById(m.getId());
                info.setNickName(nickName);
                info.setHeadIcon(photoUrl);
                info.setLastLogTime(new Date());
                info.setLogCount(info.getLogCount()+1);
                mberInfoDao.updateById(info);
            }

            Map<String, Object> retData = new HashMap<String, Object>();
            retData.put("token", token);
            retData.put("nickName", nickName);
            retData.put("headIcon", photoUrl);
            return RespData.successMsg("登录成功",retData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("第三方登录发生异常：" + e);
            throw e;
        }
    }

	@Override
	public RespData uploadDeviceToken(Map<String, Object> params) {
		Member m = memberDao.getByToken(StringUtil.toString(params.get("token")));
		if (m == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		params.put("mberId", m.getId());
		int count = memberDao.updateByMap(params);
		if (count > 0) {
			return RespData.successMsg("上传成功!");
		}
		return RespData.errorMsg("上传失败");
	}

	@Override
	public RespData getMyProFile(Map<String, Object> params) {
		log.info("开始获取头像和昵称信息");
		if (StringUtil.isEmpty(params.get("token") + "")) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
		}

		Member mb = memberDao.getByToken(params.get("token").toString());

		if (mb == null) {
			return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
		}
		params.put("mberId", mb.getId());
		MberInfo mberInfo = mberInfoDao.getMyProFile(params);

		return RespData.successMsg("",mberInfo);
	}
}