package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.Sms;


/**
* @date 2017-09-08
* @author dgb
* 
*/
public interface SmsService extends BaseService<Sms, Integer> {

    int isCorrect(String mobile, String verifCode);

    RespData sendCode(String mobile,String ip);

    void sendMsg(String mobile,String content);
}