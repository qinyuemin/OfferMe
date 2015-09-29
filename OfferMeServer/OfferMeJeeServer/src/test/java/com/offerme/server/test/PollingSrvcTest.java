package com.offerme.server.test;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.business.message.PollingMessageList;
import com.offerme.server.service.PollingSrvc;
import com.offerme.server.test.core.BaseTestCase;

public class PollingSrvcTest extends BaseTestCase{
	
	@Autowired
	private PollingSrvc pollingSrvc;
	
	@Test
	public void testPolling()
	{
		int userId = 2;
		PollingMessageList messageList = pollingSrvc.getComingMessages(userId);
		
		assertFalse(messageList == null);
		
		// TODO Polling need more Test
		
	
	}

}
