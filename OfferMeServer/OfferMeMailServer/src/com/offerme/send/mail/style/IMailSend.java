package com.offerme.send.mail.style;

import java.util.HashMap;

import com.offerme.intf.send.IMessageMail;

public interface IMailSend
{

	public void initSetting(HashMap<String, String> styleSetMap) throws Exception;

	public void send(IMessageMail mail) throws Exception;
}
