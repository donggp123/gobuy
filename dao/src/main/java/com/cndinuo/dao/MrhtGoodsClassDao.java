package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.MrhtGoodsClass;
import com.cndinuo.vo.GoodsClassVO;

import java.util.List;


/**
* @date 2017-09-01
* @author dgb
* 
*/
public interface MrhtGoodsClassDao extends BaseDao<MrhtGoodsClass, Integer> {

    List<GoodsClassVO> getSelectData();
}