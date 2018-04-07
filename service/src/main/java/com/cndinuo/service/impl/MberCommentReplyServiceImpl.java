package com.cndinuo.service.impl;
import com.cndinuo.base.AbstractService;
import com.cndinuo.common.RespData;
import com.cndinuo.dao.MberCommentReplyDao;
import com.cndinuo.domain.MberCommentReply;
import com.cndinuo.service.MberCommentReplyService;
import com.cndinuo.utils.TextFilterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;


/**
* @date 2017-09-14
* @author dgb
* 
*/
@Service("mberCommentReplyService")
public class MberCommentReplyServiceImpl extends AbstractService<MberCommentReply, Integer> implements MberCommentReplyService {

	@Autowired
	private MberCommentReplyDao mberCommentReplyDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mberCommentReplyDao);
	}

	@Override
	public RespData save(MberCommentReply commentReply) {
		try {
			commentReply.setReplyTime(new Date());
			commentReply.setContent(TextFilterUtil.checkSensitiveWord(commentReply.getContent()));
			commentReply = super.insert(commentReply);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return RespData.successMsg("回复成功");

	}
}