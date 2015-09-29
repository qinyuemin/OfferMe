package com.offerme.send.mail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.offerme.intf.send.IMessageMail;
import com.offerme.intf.send.IUser;

public class Mail implements IMessageMail
{
	// 对应的用户对象
	private IUser user = null;
	private String idCode = null;

	private String m_messageID = null;
	private boolean m_bSuccess = false;
	private String m_errorInfo = null;
	
	public void load(IUser user) throws Exception
	{
		if(!isEmail(user.getEmail()))
		{
			throw new Exception("邮件地址(" + user.getEmail() + ")不是无效！");
		}
		this.setUser(user);
	}
	
	/**
	 * Email地址检查
	 * @return boolean 检查结果<br>
	 */
	private boolean isEmail(String pInput)
	{
		String regEx = "^[a-zA-Z0-9_\\.]+@[a-zA-Z0-9-]+[\\.a-zA-Z]+$";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(pInput);
		return matcher.matches();
	}

	public String getMessageID()
	{
		return m_messageID;
	}
	
	public boolean getBSuccess()
	{
		return m_bSuccess;
	}

	public String getErrorInfo()
	{
		return m_errorInfo;
	}
	
	public void setBSuccess(boolean bSuccess)
	{
		m_bSuccess = bSuccess;
	}

	public void setErrorInfo(String errorInfo)
	{
		m_errorInfo = errorInfo;
	}

	public void setMessageID(String messageID)
	{
		m_messageID = messageID;
	}

	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

}
