package com.cndinuo.service;

import com.cndinuo.base.BaseService;
import com.cndinuo.domain.MrhtGoodsClass;
import com.cndinuo.vo.GoodsClassVO;

import java.util.List;


/**
* @date 2017-09-01
* @author dgb
* 
*/
public interface MrhtGoodsClassService extends BaseService<MrhtGoodsClass, Integer> {

    List<GoodsClassVO> getSelectData();
}