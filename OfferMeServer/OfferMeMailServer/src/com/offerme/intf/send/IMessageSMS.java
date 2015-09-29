package com.offerme.intf.send;

public interface IMessageSMS extends IMessage
{
	public void setContent(String content);
	
	public String getContent();
	
	public String[] getMobile();
	
	public void setMobile(String mobile);
	
	public void setMobile(String[] mobile);
}
