package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.OrderReturn;


/**
* @date 2017-09-16
* @author dgb
* 
*/
public interface OrderReturnService extends BaseService<OrderReturn, Integer>{

    /**
     * 申请退款页面（退款信息）接口
     * @param token
     * @param orderNo
     * @return
     */
    RespData getRefundInfo(String token, String orderNo);

    /**
     * 申请退款页面（退款原因）
     * @param token
     * @return
     */
    RespData getRefundReason(String token);


    RespData save(String orderNo, Integer status,String retNo,String remark);

    /**
     * 退款接口（用户点击申请退款按钮）
     * @param token
     * @return
     */
    RespData submitRefundApply(String token, String orderNo, String retType, String retReason);

    /**
     * 退款列表
     * @param token
     * @return
     */
    RespData getRefundList(String token);

    RespData refundDeposit(String token);
}