package com.offerme.server.service;

import com.offerme.server.model.business.message.ChatMessage;

public interface SendMessageSrvc {
	
	Integer saveMessage(ChatMessage message);

}
