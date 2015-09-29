package com.offerme.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offerme.server.model.business.message.ChatMessage;
import com.offerme.server.service.SendMessageSrvc;
import com.offerme.server.util.JsonUtil;

@Controller
public class SendMessageController {
	
	@Autowired
	private SendMessageSrvc sendMessageSrv;
	
	@ResponseBody
	@RequestMapping("/saveMessage")
	public Integer saveMessage(String param)
	{
		ChatMessage message = JsonUtil.jsonToBean(param, ChatMessage.class);
		return sendMessageSrv.saveMessage(message);
	}

}
