package com.offerme.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offerme.server.model.business.message.PollingMessageList;
import com.offerme.server.service.PollingSrvc;
import com.offerme.server.util.JsonUtil;

@Controller
public class PollingController {

	@Autowired
	private PollingSrvc pollingSrvc;

	@ResponseBody
	@RequestMapping("/polling")
	public PollingMessageList getComingMessages(String param) {
		Integer  userId = JsonUtil.jsonToBean(param, Integer.class);
		PollingMessageList messageList = pollingSrvc.getComingMessages(userId);
		return messageList;
	}
}
