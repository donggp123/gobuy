package com.cndinuo.service;

import com.cndinuo.common.RespData;
import com.cndinuo.domain.MberRiderInfo;
import com.cndinuo.base.BaseService;

import java.util.Map;


/**
* @date 2017-09-14
* @author dgb
* 
*/
public interface MberRiderInfoService extends BaseService<MberRiderInfo, Integer>{

    RespData save(Map<String, Object> params) throws Exception;

    RespData adopt(Map params) throws  Exception;

    RespData getRiderRobList(Map<String, Object> params);

    RespData getRiderByOrderDelivery(Map<String, Object> params);

    RespData getRiderByTodayOrder(Map<String, Object> params);

    RespData getRiderHistoryByOrder(Map<String, Object> params);

    RespData getRiderByBalance(Map<String, Object> params);

    RespData updatePickUpByOrderStatusAndTrack(Map<String, Object> params);

    RespData updateSentToByOrderStatusAndTrack(Map<String, Object> params);

    MberRiderInfo getByMberId(Integer mberId);

    RespData getRiderInfo(String token);

    RespData saveInfo(Map<String, Object> params);
}