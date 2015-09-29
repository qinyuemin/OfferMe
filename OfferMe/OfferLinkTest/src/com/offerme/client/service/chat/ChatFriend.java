package com.offerme.client.service.chat;

import java.io.Serializable;

public class ChatFriend implements Serializable {

	private static final long serialVersionUID = -4961466236595791453L;
	private Integer friendId;
	private String name;
	private byte[] portrait = null;
	private Integer portraitVersion = -100;
	private String lastMessageId;
	private ChatMessage lastMessage;

	public Integer getPortraitVersion() {
		return portraitVersion;
	}

	public void setPortraitVersion(Integer portraitVersion) {
		this.portraitVersion = portraitVersion;
	}

	public Integer getFriendId() {
		return friendId;
	}

	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPortrait() {
		return portrait;
	}

	public void setPortrait(byte[] portrait) {
		this.portrait = portrait;
	}

	public String getLastMessageId() {
		return lastMessageId;
	}

	public void setLastMessageId(String lastMessageId) {
		this.lastMessageId = lastMessageId;
	}

	public ChatMessage getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(ChatMessage lastMessage) {
		this.lastMessage = lastMessage;
	}

}
