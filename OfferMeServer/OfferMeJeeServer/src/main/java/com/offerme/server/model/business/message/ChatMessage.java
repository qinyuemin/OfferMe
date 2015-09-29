package com.offerme.server.model.business.message;

public class ChatMessage {

	public static int DEFAULT_PROFILE_VERSION = -100;
	private Integer messageId;
	private Integer userId;
	private String name;
	private String date;
	private String text;
	private Integer localUserId;
	private Integer profileVersion = DEFAULT_PROFILE_VERSION;
	private byte[] profile = null;
	private boolean isComMsg = true;

	public ChatMessage() {
	}

	public byte[] getProfile() {
		return profile;
	}

	public Integer getProfileVersion() {
		return profileVersion;
	}

	public void setProfileVersion(Integer profileVersion) {
		this.profileVersion = profileVersion;
	}

	public void setProfile(byte[] profile) {
		this.profile = profile;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
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
