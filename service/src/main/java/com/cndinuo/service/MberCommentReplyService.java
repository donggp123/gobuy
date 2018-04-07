package com.cndinuo.service;

import com.cndinuo.common.RespData;
import com.cndinuo.domain.MberCommentReply;
import com.cndinuo.base.BaseService;


/**
* @date 2017-09-14
* @author dgb
* 
*/
public interface MberCommentReplyService extends BaseService<MberCommentReply, Integer>{

    RespData save(MberCommentReply commentReply);
}