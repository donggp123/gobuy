package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.MrhtPurchaseFlow;
import com.cndinuo.domain.Message;
import com.cndinuo.domain.UserManager;


/**
* @date 2017-08-31
* @author dgb
* 
*/
public interface MrhtPurchaseFlowService extends BaseService<MrhtPurchaseFlow, Integer> {

    Message save(MrhtPurchaseFlow flow, UserManager user) throws Exception;

    RespData quote(MrhtPurchaseFlow flow, UserManager user);
}