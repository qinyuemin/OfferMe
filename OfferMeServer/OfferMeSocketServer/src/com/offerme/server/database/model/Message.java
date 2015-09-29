package com.offerme.server.database.model;

import java.sql.Timestamp;

/**
 * 站内信/反馈
 * 
 * @author Edouard.Zhang
 * 
 */
public class Message {

	/**
	 * MESSAGE_ID: INT
	 */
	private int messageId;
	/**
	 * SUSER_ID: INT 发送者ID
	 */
	private int sUserId;
	/**
	 * RUSER_ID: INT 接受者ID
	 */
	private int rUserId;
	/**
	 * TITLE: VARCHAR(50) 标题
	 */
	private String title;
	/**
	 * CONTENT: TEXT 内容
	 */
	private String content;
	/**
	 * DATE: 发送时间
	 */
	private Timestamp date;
	/**
	 * 发送邮件的用户对象
	 */
	private User SUser;
	/**
	 * 接受邮件的用户对象
	 */
	private User RUser;
	/**
	 * 状态(已读，未读)
	 */
	private int status;

	private int profileVersion;

	public int getProfileVersion() {
		return profileVersion;
	}

	public void setProfileVersion(int profileVersion) {
		this.profileVersion = profileVersion;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getsUserId() {
		return sUserId;
	}

	public void setsUserId(int sUserId) {
		this.sUserId = sUserId;
	}

	public int getrUserId() {
		return rUserId;
	}

	public void setrUserId(int rUserId) {
		this.rUserId = rUserId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public User getSUser() {
		return SUser;
	}

	public void setSUser(User sUser) {
		SUser = sUser;
	}

	public User getRUser() {
		return RUser;
	}

	public void setRUser(User rUser) {
		RUser = rUser;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
