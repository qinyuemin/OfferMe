package com.offerme.send;


import com.offerme.intf.send.IUser;

public class User implements IUser
{
	private int userId;
	
	private String email;
	
	private String phone;
	
	private String idCodeForEmail;

	/**
	 * GetKeyCondition
	 */
	public String GetKeyCondition()
	{
		return "USER_ID = " + String.valueOf(userId);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCodeForEmail() {
		return idCodeForEmail;
	}

	public void setIdCodeForEmail(String idCodeForEmail) {
		this.idCodeForEmail = idCodeForEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
}
