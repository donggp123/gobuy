package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.Member;

import java.util.Map;


/**
* @date 2017-09-08
* @author dgb
* 
*/
public interface MemberService extends BaseService<Member, Integer> {

    RespData register(String mobile, String password, String verifCode);

    RespData login(String mobile, String password);

    Member getByMobile(String mobile);

    Member getByToken(String token);

    RespData editNickName(String token, String nickName);

    RespData uploadHeadIcon(String token, String headIcon);

    RespData opinion(String token, String content);

    RespData modifyMobile(String mobile, String newMobile, String verifCode);

    RespData forgotPwd(String mobile, String verifCode, String newPwd);

    RespData modifyPwd(Map<String, String> params);

    RespData oauth2(String uid, String oauthType, String photoUrl, String nickName);

    RespData uploadDeviceToken(Map<String, Object> params);

    RespData getMyProFile(Map<String, Object> params);
}