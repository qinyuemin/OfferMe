package com.offerme.client.service.chat;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = -554301856864982205L;
	private Integer senderId;
	private String senderName;
	private Integer receiverId;
	private String receiverName;
	private String subject;
	private String content;
	
	public Integer getSenderId() {
		return senderId;
	}
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public Integer getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
