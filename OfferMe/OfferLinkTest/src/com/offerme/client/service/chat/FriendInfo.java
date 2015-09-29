package com.offerme.client.service.chat;

import java.io.Serializable;

public class FriendInfo implements Serializable {

	private static final long serialVersionUID = -8585690207282521857L;
	private int responseCode = -1;
	private Integer friendId;
	private String name;
	private String city;
	private String enterprise;
	private String email;
	private String phoneNumber;
	private byte[] profile = null;
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public byte[] getProfile() {
		return profile;
	}
	public void setProfile(byte[] profile) {
		this.profile = profile;
	}
	
}
