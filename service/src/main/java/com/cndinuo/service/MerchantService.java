package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.Merchant;
import com.cndinuo.domain.MrhtInfo;
import com.cndinuo.domain.UserManager;
import com.cndinuo.page.Page;

import java.util.Map;


/**
* @date 2017-08-28
* @author dgb
* 
*/
public interface MerchantService extends BaseService<Merchant, Integer> {

    boolean save(Merchant merchant, MrhtInfo mrhtInfo);
    boolean changePwd(UserManager user);

    boolean updateByStatus(Merchant merchant);

    Merchant getByPurId(Integer purId);

    Page<Map<String, Object>> getMrhtForHomeByPage(Map<String, Object> params);

    Map<String,Object> getByDistanceAndTime(Map<String, Object> params);

    RespData getLoginForApp(String loginName, String password);

    RespData orders(Map<String, Object> params);

    RespData uploadDeviceToken(Map<String, Object> params);

    void notifyMerchantNewOrder(String orderNo);
}