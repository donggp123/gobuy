package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.MberComment;
import com.cndinuo.page.Page;

import java.util.List;
import java.util.Map;


/**
* @date 2017-09-12
* @author dgb
* 
*/
public interface MberCommentDao extends BaseDao<MberComment, Integer>{

    List<Map<String,Object>> getCommentByMap(Page page);

    List<Map<String,Object>> getGoodsByCommList(Page page);

    List<Map<String, Object>> getCommentListForGoods(Map<String, Object> params);

    Map<String ,Object> getCountByComment(Map<String, Object> params);

    List<MberComment> getSatisfactionRiderCommonList(Page<MberComment> params);

    List<MberComment> getGeneralRiderCommonList(Page<MberComment> params);
}