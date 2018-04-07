package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.MberComment;
import com.cndinuo.page.Page;

import java.util.List;
import java.util.Map;


/**
* @date 2017-09-12
* @author dgb
* 
*/
public interface MberCommentService extends BaseService<MberComment, Integer>{

    RespData getCommentByMap(Map<String, Object> params);


    RespData getGoodsByCommList(Map<String,Object> params);

    /**
     * 用户对商家下某一商品的评价
     * @param mrhtId
     * @param goodsId
     * @return
     */
    List<Map<String, Object>> getCommentListForGoods(String mrhtId, String goodsId);

    RespData getCommView(Map<String, Object> params);

    RespData save(Map<String, Object> params) throws Exception;

    /**
     * 用户对骑手的评价列表(满意)
     * @param params
     * @return
     */
    Page<MberComment> getSatisfactionRiderCommonList(Map<String, Object> params);

    /**
     * 用户对骑手的评价列表(一般)
     * @param params
     * @return
     */
    Page<MberComment> getGeneralRiderCommonList(Map<String, Object> params);

    /**
     * 骑手提出复议
     *
     * @param comId
     * @param token
     * @return
     */
    RespData reconsider(String token,String comId);
}