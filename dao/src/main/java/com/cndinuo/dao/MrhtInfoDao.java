package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.MrhtInfo;


/**
* @date 2017-08-28
* @author dgb
* 
*/
public interface MrhtInfoDao extends BaseDao<MrhtInfo, Integer> {

    MrhtInfo getByMrhtId(Integer mrhtId);

    int updateByMrhtAccount(MrhtInfo mrhtInfo);

    int updateByStoreImage(MrhtInfo mrhtInfo);
}