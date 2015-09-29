package com.offerme.server.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offerme.server.model.business.message.ChatMessage;
import com.offerme.server.model.db.Message;
import com.offerme.server.service.MessageSrvc;
import com.offerme.server.service.SendMessageSrvc;

@Service("sendMessageSrvc")
@Transactional
public class SendMessageSrvcImpl implements SendMessageSrvc {

	@Autowired
	private MessageSrvc messageSrvc;

	@Override
	public Integer saveMessage(ChatMessage chatMessage) {
		Integer messageId = -1;
		try {
			Message message = new Message();
			message.setsUserId(chatMessage.getLocalUserId());
			message.setrUserId(chatMessage.getUserId());
			message.setContent(chatMessage.getText());
			message.setProfileVersion(chatMessage.getProfileVersion());
			message.setDate(new Date());
			message.setStatus(0);
			messageId = messageSrvc.insertMessage(message);
		} catch (Exception e) {
			messageId = -1;
		}
		return messageId;
	}

}
