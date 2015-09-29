package com.offerme.server.service;

import java.util.List;

import com.offerme.server.model.db.Message;

public interface MessageSrvc {
	
	Message getMessage(Integer messageId);
	
	List<Message> getMessage(Integer sUserID,Integer rUserId);
	
	void updateMessage(Message message);
	
	Integer insertMessage(Message message);
	
	void deleteMessage(Integer messageId);

	List<Message> getMessagesByReceiverIncrem(int rUserId);

}
