package com.offerme.server.service.impl;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offerme.server.dao.UserDao;
import com.offerme.server.model.db.User;
import com.offerme.server.service.ForgetPasswordSrvc;
import com.offerme.server.util.EncoderUtil;

@Service("forgetPasswordSrvc")
@Transactional
public class ForgetPasswordSrvcImpl implements ForgetPasswordSrvc {
	@Autowired
	private UserDao userdao;
	private User user;
	private final static String SMTPHOST = "120.27.39.10";
	private final static String PORT = "25";
	private final static String USERNAME = "offerlink";
	private final static String PASSWORD = "123456";
	private final static String FROM = "offerlink@offerlink.com";

	@Override
	public String sendMail(String mail) {
		try {
			user = userdao.findByEmail(mail);
			if (user != null) {
				send(user);
				return "OK";
			} else {
				return "KO";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void send(User user) {
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.host", SMTPHOST);
			props.put("mail.smtp.port", PORT);
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.auth", "true");
			OfferLinkAuthenticator authenticator = new OfferLinkAuthenticator();
			Session session = Session.getDefaultInstance(props, authenticator);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					user.getEmail()));
			message.setSubject("[OfferLink]忘记密码服务");
			message.setContent(setMessageBody(user));
			message.setSentDate(new Date());
			Transport transport = session.getTransport();
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			} 
			catch (MessagingException e) {
				e.printStackTrace();
		}
	}

	private class OfferLinkAuthenticator extends Authenticator {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(USERNAME, PASSWORD);
		}
	}

	private MimeMultipart setMessageBody(User user) {
		MimeMultipart multipart = new MimeMultipart();
		try {

			MimeBodyPart body = new MimeBodyPart();
			body.setContent(
					"您好," + user.getName() + ": 您的账号密码是，"
							+ EncoderUtil.decrypt(user.getPsw())
							+ "。 要好好记住，下次不要忘记了哦~^.^",
					"text/html;charset=gb18030");
			multipart.addBodyPart(body);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return multipart;
	}
}
