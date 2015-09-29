package com.offerme.server.model.business.message;

public class PollingMessageList {
	
	private int responseCode = -1;
	private ChatMessageList chatMessageList = null;
	private CVList cvList = null;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public ChatMessageList getChatMessageList() {
		return chatMessageList;
	}

	public void setChatMessageList(ChatMessageList chatMessageList) {
		this.chatMessageList = chatMessageList;
	}

	public CVList getCvList() {
		return cvList;
	}

	public void setCvList(CVList cvList) {
		this.cvList = cvList;
	}

	public boolean hasCVList() {
		if (this.cvList != null) {
			return true;
		}
		return false;
	}

	public boolean hasMessageList() {
		if (this.chatMessageList != null) {
			return false;
		}
		return false;
	}

}
