package com.offerme.send.mail.style;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Map.Entry;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.offerme.intf.send.IMessageMail;


public class SMTPSendMail implements IMailSend
{
	private String SMTPServer = "";
	private String FromMailAddress = "";
	private String FromMailPassword = "";
	private String Username = "";
	private String Password = "";
	private String Nick = "";
	private String title = "";
	private String port = "";
	
	public class SMTPSet
	{
		public static final String SMTPServer = "SMTPServer";
		public static final String FromMailAddress = "FromMailAddress";
		public static final String FromMailPassword = "FromMailPassword";
		public static final String Username = "Username";
		public static final String Password = "Password";
		public static final String Nick = "Nick";
		public static final String Title = "Title";
		public static final String Port = "Port";
	}
	
	public void initSetting(HashMap<String, String> styleSetMap) throws Exception
	{
		// 服务器地址
		SMTPServer = getProperty(styleSetMap, SMTPSet.SMTPServer);
		// From邮件地址
		FromMailAddress = getProperty(styleSetMap, SMTPSet.FromMailAddress);
		// From邮件密码
		FromMailPassword = getProperty(styleSetMap, SMTPSet.FromMailPassword);
		// 用户名
		Username = getProperty(styleSetMap, SMTPSet.Username);
		// 用户密码
		Password = getProperty(styleSetMap, SMTPSet.Password);
		// 别名
		Nick =  getProperty(styleSetMap, SMTPSet.Nick);
		// 标题
		title = getProperty(styleSetMap, SMTPSet.Title);
		// 端口
		port = getProperty(styleSetMap, SMTPSet.Port);
	}

	private Session getSession()
	{
		Properties props = new Properties();
//		props.put("mail.host", SMTPServer);
		props.put("mail.smtp.host", SMTPServer);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
	    props.setProperty("mail.smtp.port", port);
		return Session.getDefaultInstance(props, new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(FromMailAddress, FromMailPassword);
			}
		});
	}
	
	/**
	 * 获取属性信息
	 * @param PropertyName
	 * @return
	 */
	public String getProperty(HashMap<String, String> styleSetMap, String PropertyName) throws Exception
	{
		if(PropertyName == null || "".equals(PropertyName))
		{
			throw new Exception("不存在指定的属性(" + PropertyName + ")");
		}
		else
		{
			for (Entry<String, String> entry : styleSetMap.entrySet())
			{
				if(entry.getKey().equalsIgnoreCase(PropertyName))
				{
					return entry.getValue();
				}
			}
			throw new Exception("不存在指定的属性(" + PropertyName + ")");
		}
	}
	
	/**
	 * 发送邮件
	 * 
	 * @param IMessageMail 邮件
	 * @throws MessagingException
	 */
	public void send(IMessageMail mail) throws Exception
	{
		Session session = getSession();
		MimeMessage msg = new MimeMessage(session);
		// 发件人
		msg.setFrom(parse(FromMailAddress)[0]);
		// 收件人
		msg.setRecipients(Message.RecipientType.TO, parse(mail.getUser().getEmail()));
		
		// 主题
		msg.setSubject(title, "gb18030");
		// 内容，包括主体（HTML格式）和附件
		MimeMultipart mp = new MimeMultipart();
		MimeBodyPart p = new MimeBodyPart();
		String idCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0,4);
		mail.setIdCode(idCode);
		// 设置正文
		p.setContent("以下是来自【伯乐去哪儿了】随机生成的4位随机码\n" + idCode, "text/html;charset=gb18030");
		mp.addBodyPart(p);
		// 设置内容
		msg.setContent(mp);
		// 设置发送时间
		msg.setSentDate(new Date());
		msg.saveChanges();
		
		// 建立连接
		Transport transport = session.getTransport();
		transport.connect();
		// 发送消息
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
//		Transport.send(msg);
	}
	
	/**
	 * 解析地址集合字符串
	 * 
	 * @param addressSet
	 * @return
	 * @throws AddressException
	 */
	private InternetAddress[] parse(String addressSet) throws AddressException
	{
		ArrayList<InternetAddress> list = new ArrayList<InternetAddress>();
		StringTokenizer tokens = new StringTokenizer(addressSet, ";");
		while (tokens.hasMoreTokens())
		{
			list.add(new InternetAddress(tokens.nextToken().trim()));
		}
		InternetAddress[] addressArray = new InternetAddress[list.size()];
		return list.toArray(addressArray);
	}
}
