package com.offerme.client.service.chat;

import java.io.Serializable;

public class ChatMessage implements Serializable {

	private static final long serialVersionUID = 7345677150061555226L;
	private Integer userId;
	private String messageId;
	private String name;
	private String date;
	private String text;
	private Integer localUserId;
	private Integer profileVersion = -100;
	private byte[] profile = null;
	private boolean isComMsg = true;

	public ChatMessage() {
	}

	public Integer getProfileVersion() {
		return profileVersion;
	}

	public void setProfileVersion(Integer profileVersion) {
		this.profileVersion = profileVersion;
	}

	public byte[] getProfile() {
		return profile;
	}

	public void setProfile(byte[] profile) {
		this.profile = profile;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getLocalUserId() {
		return localUserId;
	}

	public void setLocalUserId(Integer localUserId) {
		this.localUserId = localUserId;
	}

	public boolean isComMsg() {
		return isComMsg;
	}

	public void setComMsg(boolean isComMsg) {
		this.isComMsg = isComMsg;
	}

}
