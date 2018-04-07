package com.cndinuo.service.impl;
import com.cndinuo.domain.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndinuo.dao.MessageDao;
import com.cndinuo.service.MessageService;
import com.cndinuo.base.AbstractService;
import javax.annotation.PostConstruct;


/**
* @date 2017-09-01
* @author dgb
* 
*/
@Service("messageService")
public class MessageServiceImpl extends AbstractService<Message, Integer> implements MessageService {

	@Autowired
	private MessageDao messageDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(messageDao);
	}
}