package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.Message;
import com.cndinuo.domain.MrhtPurchase;
import com.cndinuo.domain.MrhtPurchaseFlow;
import com.cndinuo.domain.UserManager;

import java.util.List;


/**
* @date 2017-08-31
* @author dgb
* 
*/
public interface MrhtPurchaseService extends BaseService<MrhtPurchase, Integer> {

    RespData butt(MrhtPurchase MrhtPurchase, UserManager user);

    Message confirms(Integer id ,UserManager user);

    List<MrhtPurchaseFlow> cancel(Integer id , UserManager user);

    MrhtPurchase save(MrhtPurchase mrhtPurchase, UserManager user);
}