package com.offerme.send.mail;

import org.apache.log4j.Logger;

import com.offerme.intf.send.ICallBack;
import com.offerme.intf.send.IMessage;
import com.offerme.intf.send.ISendMessage;
import com.offerme.intf.send.IUser;
import com.offerme.send.mail.MailSetting.MailSendStyle;
import com.offerme.send.mail.style.IMailSend;
import com.offerme.send.mail.style.NOTESendMail;
import com.offerme.send.mail.style.SMTPSendMail;
import com.offerme.util.Log;


public class MailManage implements ISendMessage
{
	public static Logger myLog = Logger.getLogger("Send");
	
	// 单例模式邮件管理
	private static MailManage mail = null;
	// 邮件设置
	private static MailSetting mail_set = null;
	// 回调接口
	private ICallBack backResult = null;
	// 邮件发送对象
	private static IMailSend mailSend = null;
	
	/**
	 * 获取邮件管理
	 * @return
	 * @throws Exception
	 */
	public ISendMessage getSendMessage() throws Exception
	{
		if(mail == null)
		{
			mail = new MailManage();
			mail_set = new MailSetting();
			String style = mail_set.getProperty(MailSetting.STYLE);
			if(style.equals(MailSendStyle.SMTP))
			{
				mailSend = new  SMTPSendMail();
			}
			else if(style.equals(MailSendStyle.NOTES))
			{
				mailSend = new  NOTESendMail();
			}
			else
			{
				throw new Exception(MailSetting.STYLE + "(" + style + ")不支持！");
			}
			mailSend.initSetting(mail_set.styleSetMap);
		}
		return mail;
	}

	public void send(IUser user) throws Exception
	{
		Mail mail = new Mail();
		mail.load(user);
		try
		{
			mailSend.send(mail);
			// 设置发送成功
			mail.setBSuccess(true);
			myLog.info(mail.getUser().getEmail()  + " 发送邮件成功！");
			System.out.println(mail.getUser().getEmail()  + " 发送邮件成功！");
			backResult.setSendResult(mail);
		}
		catch (Exception e)
		{
			// 设置发送失败
			mail.setBSuccess(false);
			mail.setErrorInfo(e.getMessage());
			myLog.error(mail.getUser().getEmail() + " 发送邮件失败！");
			myLog.error(Log.getStackInfo(e));
			System.out.println(mail.getUser().getEmail() + " 发送邮件失败（" + e.getMessage() + "）！");
			backResult.setSendResult(mail);
		}
	}

	public IMessage receive(Object msgID) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 设置回调接口
	 */
	public void setCallBack(ICallBack callBack) throws Exception
	{
		backResult = callBack;
	}

}
