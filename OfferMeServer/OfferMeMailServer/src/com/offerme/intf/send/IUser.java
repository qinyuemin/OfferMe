package com.offerme.intf.send;

public interface IUser
{
	public int getUserId();

	public void setUserId(int userId);

	public String getEmail();

	public void setEmail(String email);
	
	public String getPhone();

	public void setPhone(String mobile);


	public String getIdCodeForEmail();
	
	public void setIdCodeForEmail(String idCodeForEmail);
}
