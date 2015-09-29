package com.offerme.server.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.business.message.ChatMessage;
import com.offerme.server.model.db.Message;
import com.offerme.server.service.MessageSrvc;
import com.offerme.server.service.SendMessageSrvc;
import com.offerme.server.test.core.BaseTestCase;

public class SendMessageSrvcTest  extends BaseTestCase {
	
	@Autowired
	private SendMessageSrvc sendMessageSrvc;
	@Autowired
	private MessageSrvc messageSrvc;
	
	@Test
	public void testSendMessage()
	{
		ChatMessage message = new ChatMessage();
		message.setLocalUserId(1);
		message.setUserId(2);
		message.setText("test");
		Integer messageId = sendMessageSrvc.saveMessage(message);
		assertFalse(messageId.intValue() == -1);
		
		List<Message> messageList = messageSrvc.getMessage(1,2);
		
		assertFalse(messageList.size() == 0);
		
	}

}
