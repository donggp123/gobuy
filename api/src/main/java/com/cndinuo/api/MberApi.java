package com.cndinuo.api;

import com.alibaba.fastjson.JSON;
import com.cndinuo.base.BaseApi;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.MberInfo;
import com.cndinuo.domain.Member;
import com.cndinuo.service.MberRiderInfoService;
import com.cndinuo.service.MberRiderPositionService;
import com.cndinuo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("mber")
public class MberApi extends BaseApi {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MberRiderInfoService riderInfoService;
    @Autowired
    private MberRiderPositionService riderPositionService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public RespData register(@RequestBody Map<String, String> params) {
        log.info("新用户注册：params==" + params);
        RespData data = null;
        try {
            data = memberService.register(params.get("mobile"), params.get("password"), params.get("verifCode"));
            log.info("注册结果：" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public RespData login(@RequestBody Map<String, String> params) {
        log.info("用户登录：params==" + params);
        RespData data = null;
        try {
            data = memberService.login(params.get("mobile"), params.get("password"));
            log.info("登录结果：" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }


    @RequestMapping(value = "editnickname", method = RequestMethod.POST)
    @ResponseBody
    public RespData editNickName(@RequestBody Map<String, String> params) {
        log.info("用户修改昵称： params==" + params);
        RespData data = null;
        try {
            data = memberService.editNickName(params.get("token"), params.get("nickName"));
            log.info("修改结果：" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }


    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public RespData uploadHeadIcon(@RequestBody Map<String, String> params) {
        log.info("用户上传头像： params==" + params);
        RespData data = null;
        try {
            data = memberService.uploadHeadIcon(params.get("token"), params.get("headIcon"));
            log.info("上传结果：" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }


    @RequestMapping(value = "opinion", method = RequestMethod.POST)
    @ResponseBody
    public RespData opinion(@RequestBody Map<String, String> params) {
        log.info("接受用户的反馈意见 params == " + params);
        RespData data = null;
        try {
            data = memberService.opinion(params.get("token"), params.get("content"));
            log.info("接受结果： " + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "isexist", method = RequestMethod.POST)
    @ResponseBody
    public RespData isExist(@RequestBody Map<String, Object> params) {
        log.info("判断手机号是否注册 params == " + params);
        List<Member> data = memberService.getByMap(params);
        if (data != null && data.size() > 0) {
            return RespData.errorMsg("该手机号已经注册请换其他号码");
        }
        return RespData.successMsg("可以注册");
    }

    @RequestMapping(value = "modifymobile", method = RequestMethod.POST)
    @ResponseBody
    public RespData modifyMobile(@RequestBody Map<String, String> params) {
        log.info("用户修改手机号：params==" + params);
        RespData data = null;
        try {
            data = memberService.modifyMobile(params.get("mobile"), params.get("newMobile"), params.get("verifCode"));
            log.info("修改结果：" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "forgotpwd", method = RequestMethod.POST)
    @ResponseBody
    public RespData forgotPwd(@RequestBody Map<String, String> params) {
        log.info("用户忘记密码修改为新的密码：params==" + params);
        RespData data = null;
        try {
            data = memberService.forgotPwd(params.get("mobile"), params.get("verifCode"), params.get("newPwd"));
            log.info("修改新密码结果：" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "modifypwd", method = RequestMethod.POST)
    @ResponseBody
    public RespData modifyPwd(@RequestBody Map<String, String> params) {
        log.info("用户修改密码：params==" + params);
        RespData data = null;
        try {
            data = memberService.modifyPwd(params);
            log.info("修改密码结果：" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "oauth2", method = RequestMethod.POST)
    @ResponseBody
    public RespData oauth2(@RequestBody Map<String, String> params) {
        log.info("用户使用第三方登录：params==" + params);
        RespData data = null;
        try {
            data = memberService.oauth2(params.get("uid"), params.get("oauthType"), params.get("photoUrl"), params.get("nickName"));
            log.info("登录结果：" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "riderauthinfo", method = RequestMethod.POST)
    @ResponseBody
    public RespData riderAuthInfo(@RequestBody Map<String, Object> params) {
        log.info("骑手认证 params==" + params);
        RespData data = null;
        try {
            data = riderInfoService.save(params);
            log.info("保存结果" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "riderposition", method = RequestMethod.POST)
    @ResponseBody
    public RespData riderPositionInfo(@RequestBody Map<String, Object> params) {
        log.info("骑手位置信息 params==" + params);
        RespData data = null;
        try {
            data = riderPositionService.updateByPosition(params);
            log.info("修改结果" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "devicetoken", method = RequestMethod.POST)
    @ResponseBody
    public RespData uploadDeviceToken(@RequestBody Map<String, Object> params) {
        log.info("用户上传设备号：params==" + params);
        RespData data = memberService.uploadDeviceToken(params);
        return data;
    }

    @RequestMapping(value = "myprofile", method = RequestMethod.POST)
    @com.cndinuo.filter.JSON(type = MberInfo.class,include = "nickName,headIcon")
    public RespData myProfile(@RequestBody Map<String, Object> params){
        log.info("获取用户的头像和昵称 params==" +params);
        RespData data = memberService.getMyProFile(params);
        log.info("返回结果" + JSON.toJSONString(data));
        return data;
    }
}
