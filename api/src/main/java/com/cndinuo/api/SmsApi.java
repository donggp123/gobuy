package com.cndinuo.api;

import com.alibaba.fastjson.JSON;
import com.cndinuo.base.BaseApi;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.service.SmsService;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("sms")
public class SmsApi extends BaseApi{

    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "send",method = RequestMethod.POST)
    public RespData sendCode(@RequestBody Map<String, String> params, HttpServletRequest request) {
        log.info("发送短信验证码 params == " + params);
        String ip = StringUtil.getRemoteHost(request);
        RespData data = null;
        try {
            data = smsService.sendCode(params.get("mobile"), ip);
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        log.info("发送结果！"+ JSON.toJSONString(data));
        return data;
    }


    @RequestMapping("verification")
    public RespData verification(@RequestBody Map<String, String> params) {
        log.info("短信验证：params==" + params);
        int result = smsService.isCorrect(params.get("mobile"),params.get("verifCode"));
        switch (result){
            case -1:
                return RespData.errorMsg("验证码错误！");
            case 0:
                return RespData.errorMsg("验证码已过期！请重新发送");
        }

        return RespData.successMsg("验证通过");
    }

}
