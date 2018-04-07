package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.Message;
import com.cndinuo.domain.MrhtGoodsReturn;
import com.cndinuo.domain.UserManager;


/**
* @date 2017-09-04
* @author dgb
* 
*/
public interface MrhtGoodsReturnService extends BaseService<MrhtGoodsReturn, Integer> {

    int update(MrhtGoodsReturn ret, UserManager user);

    Message save(MrhtGoodsReturn ret, UserManager user);

    Message retGoods(Integer num, Integer id,String remark, UserManager user);
}