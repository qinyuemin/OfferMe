package com.offerme.server.service;

import com.offerme.server.model.business.message.PollingMessageList;

public interface PollingSrvc {
	
	PollingMessageList getComingMessages(Integer userId);
}
