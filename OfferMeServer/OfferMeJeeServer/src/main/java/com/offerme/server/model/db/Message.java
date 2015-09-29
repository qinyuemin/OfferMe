package com.offerme.server.model.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message {

	private Integer messageId;
	private Integer sUserId;
	private Integer rUserId;
	private String title;
	private String content;
	private Date date;
	private Integer status;
	private Integer profileVersion;

	@Id
	@GeneratedValue
	@Column(name = "MESSAGE_ID")
	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	@Column(name = "SENDER_ID")
	public Integer getsUserId() {
		return sUserId;
	}

	public void setsUserId(Integer sUserId) {
		this.sUserId = sUserId;
	}

	@Column(name = "RECEIVER_ID")
	public Integer getrUserId() {
		return rUserId;
	}

	public void setrUserId(Integer rUserId) {
		this.rUserId = rUserId;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CRE_DT")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "PROFILEVERSION")
	public Integer getProfileVersion() {
		return profileVersion;
	}

	public void setProfileVersion(Integer profileVersion) {
		this.profileVersion = profileVersion;
	}

}
