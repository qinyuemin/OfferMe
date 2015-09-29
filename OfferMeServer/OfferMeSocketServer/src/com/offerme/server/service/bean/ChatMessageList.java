package com.offerme.server.service.bean;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageList {
	
	private int responseCode = -1;
	private List<ChatMessage> chatMessageList;
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public List<ChatMessage> getChatMessageList() {
		if(chatMessageList == null) {
			chatMessageList = new ArrayList<ChatMessage>();
		}
		return chatMessageList;
	}
	public void setChatMessageList(List<ChatMessage> chatMessageList) {
		this.chatMessageList = chatMessageList;
	}

}
