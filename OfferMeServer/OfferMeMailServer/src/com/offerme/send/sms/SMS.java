package com.offerme.send.sms;

import com.offerme.intf.send.IMessageSMS;
import com.offerme.intf.send.IUser;

public class SMS implements IMessageSMS
{
	// 信息内容
	private String content = "";
	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	// 接收号码组
	private String[] mobile = null;
	private IUser user = null;
	private String idCode = null;
	// 信息ID
	private String m_messageID = null;
	// 状态
	private boolean m_bSuccess = false;
	// 错误信息
	private String m_errorInfo = "";

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
	
	public String[] getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		if(mobile != null && "".equals(mobile) == false)
		{
			if(mobile.indexOf(',') >= 0)
			{
				String[] str = mobile.split(",");
				if(str.length > 0)
				{
					// 先计算出有多少有效号码
					int num = 0;
					for (int i = 0; i < str.length; i++)
					{
						if(str[i] != null && "".equals(str[i]) == false)
						{
							num = num + 1;
						}
					}
					// 初始化接收号码
					this.mobile = new String[num];
					num = 0;
					for (int i = 0; i < str.length; i++)
					{
						if(str[i] != null && "".equals(str[i]) == false)
						{
							this.mobile[num] = str[i];
							num = num + 1;
						}
					}
				}
			}
			else
			{
				this.mobile = new String[]{ mobile };
			}
		}
		else
		{
			this.mobile = null;
		}
	}

	public void setMobile(String[] mobile)
	{
		if(mobile != null)
		{
			// 先计算出有多少有效号码
			int num = 0;
			for (int i = 0; i < mobile.length; i++)
			{
				if(mobile[i] != null && "".equals(mobile[i]) == false)
				{
					num = num + 1;
				}
			}
			// 初始化接收号码
			this.mobile = new String[num];
			num = 0;
			for (int i = 0; i < mobile.length; i++)
			{
				if(mobile[i] != null && "".equals(mobile[i]) == false)
				{
					this.mobile[num] = mobile[i];
					num = num + 1;
				}
			}
		}
		else
		{
			this.mobile = null;
		}
	}

	public void load(IUser user) throws Exception
	{
		try
		{
			this.user = user;
			setMobile(user.getPhone());
			
		}
		catch (Exception e)
		{
			throw new Exception("解析NOTIFY为短信时出错：" + e.getMessage());
		}
	}

	public String getMessageID()
	{
		return m_messageID;
	}

	public void setMessageID(String messageID)
	{
		this.m_messageID = messageID;
	}
	
	public boolean getBSuccess()
	{
		return m_bSuccess;
	}

	public void setBSuccess(boolean bSuccess)
	{
		m_bSuccess = bSuccess;
	}
	
	public String getErrorInfo()
	{
		return m_errorInfo;
	}

	public void setErrorInfo(String errorInfo)
	{
		m_errorInfo = errorInfo;
	}

	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}
}
