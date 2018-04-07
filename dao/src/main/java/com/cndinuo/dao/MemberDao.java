package com.cndinuo.dao;

import com.cndinuo.base.BaseDao;
import com.cndinuo.domain.Member;


/**
* @date 2017-09-08
* @author dgb
* 
*/
public interface MemberDao extends BaseDao<Member, Integer> {

    Member getByMobile(String mobile);

    Member getByToken(String token);
}