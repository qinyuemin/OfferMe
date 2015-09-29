package com.offerme.server.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.db.Message;
import com.offerme.server.service.MessageSrvc;
import com.offerme.server.test.core.BaseTestCase;

public class MessageSrvcTest extends BaseTestCase {

	@Autowired
	private MessageSrvc messageSrvc;
	
	@Test
	public void testGetMessage() {
		Message message = messageSrvc.getMessage(1);
		assertEquals(1, message.getMessageId().intValue());
	}
	
}
