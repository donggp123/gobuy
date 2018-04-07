package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.MrhtInfo;


/**
* @date 2017-08-28
* @author dgb
* 
*/
public interface MrhtInfoService extends BaseService<MrhtInfo, Integer> {

    MrhtInfo getByMrhtId(Integer id);
    boolean updateByMrhtAccount(MrhtInfo mrhtInfo);

    boolean updateByStoreImage(MrhtInfo mrhtInfo);
}