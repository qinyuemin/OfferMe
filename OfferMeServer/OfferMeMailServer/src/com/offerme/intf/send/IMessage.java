package com.offerme.intf.send;

/**
 * 消息接口
 * 
 */
public interface IMessage 
{
	public String getMessageID();
	
	public void setMessageID(String messageID);
	
	public boolean getBSuccess();
	
	public void setBSuccess(boolean isSuccess);
	
	public String getErrorInfo();
	
	public void setErrorInfo(String errorInfo);
	
	public void load(IUser user) throws Exception;
	
	public String getIdCode();
	
	public void setIdCode(String idCode);
	
	public IUser getUser();
	
}
