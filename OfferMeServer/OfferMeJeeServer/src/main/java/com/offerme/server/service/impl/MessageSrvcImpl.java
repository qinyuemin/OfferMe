package com.offerme.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offerme.server.dao.MessageDao;
import com.offerme.server.model.db.Message;
import com.offerme.server.service.MessageSrvc;

@Service("messageSrvc")
@Transactional
public class MessageSrvcImpl implements MessageSrvc {
	
	@Autowired
	private MessageDao messageDao;

	@Override
	public Message getMessage(Integer messageId) {
		return messageDao.get(messageId);
	}

	@Override
	public void updateMessage(Message message) {
		messageDao.update(message);
	}

	@Override
	public Integer insertMessage(Message message) {
		return messageDao.save(message);
	}

	@Override
	public void deleteMessage(Integer messageId) {
		messageDao.delete(messageId);
	}

	@Override
	public List<Message> getMessage(Integer sUserID, Integer rUserId) {
		return messageDao.getMessage(sUserID,rUserId);
	}

	@Override
	public List<Message> getMessagesByReceiverIncrem(int rUserId) {
		return messageDao.getMessagesByReceiverIncrem(rUserId);
	}

}
