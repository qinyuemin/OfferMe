package com.offerme.server.service;

import com.offerme.server.database.dao.MessageDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.database.model.Message;
import com.offerme.server.service.bean.ChatMessage;
import com.offerme.server.util.JSONUtil;
import com.offerme.server.util.Log;

public class SendMessageSrvc {
	private static final Log LOG = new Log(
			"com.offerme.server.service.ChatSrvc");
	private MessageDao msgDao = DaoImplManage.getMessageDaoInstance();

	public Integer saveMessage(String request) {
		ChatMessage chatMessage = JSONUtil.jsonToBean(request,
				ChatMessage.class);

		Message message = convertChatMessageToMessage(chatMessage);
		Integer messageId = -1;
		try {
			messageId = msgDao.insertMessage(message);
		} catch (Exception e) {
			messageId = -1;
			LOG.error("Cannot save new message, sender:"
					+ chatMessage.getLocalUserId());
		}
		return messageId;
	}

	private Message convertChatMessageToMessage(ChatMessage chatMessage) {
		Message message = new Message();
		message.setsUserId(chatMessage.getLocalUserId());
		message.setrUserId(chatMessage.getUserId());
		message.setContent(chatMessage.getText());
		message.setProfileVersion(chatMessage.getProfileVersion());
		return message;
	}
}
