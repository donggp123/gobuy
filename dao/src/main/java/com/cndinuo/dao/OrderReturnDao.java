package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.OrderReturn;
import com.cndinuo.page.Page;

import java.util.List;


/**
* @date 2017-09-16
* @author dgb
* 
*/
public interface OrderReturnDao extends BaseDao<OrderReturn, Integer>{

    Integer getLastReturnNo();

    List<OrderReturn> getRefundByMberId(Page page);

}