package com.offerme.send.mail;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MailSetting
{
	private static final String file = System.getProperty("user.dir") + "/Config/MailConfig.xml";
	
	/**
	 * 配置项集合
	 */
	private HashMap<String, String> setMap = new HashMap<String, String>();

	public HashMap<String, String> styleSetMap = new HashMap<String, String>();
	
	public static final String STYLE = "Style";
	
	public static final String MAIL = "Mail";
	
	public class MailSendStyle
	{
		public static final String SMTP = "SMTP";

		public static final String NOTES = "NOTES";
	}
	
	/**
	 * 构造函数，初始化各变量
	 * 
	 */
	public MailSetting() throws Exception
	{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(file));
		Element root = document.getRootElement();
//		String mailStyle = root.element(MAIL).element(STYLE).getStringValue();
		Element element = root.element(MAIL);
		for (Iterator<Element> iterator = element.elementIterator(); iterator.hasNext();) {
			Element subElement = (Element) iterator.next();
			String name = subElement.getName();
			setMap.put(name, subElement.getStringValue());
		}
		element = root.element(setMap.get(STYLE));
		for (Iterator<Element> iterator = element.elementIterator(); iterator.hasNext();) {
			Element subElement = (Element) iterator.next();
			String name = subElement.getName();
			styleSetMap.put(name, subElement.getStringValue());
		}
	}

	/**
	 * 获取属性信息
	 * @param PropertyName
	 * @return
	 */
	public String getProperty(String PropertyName)
	{
		if(PropertyName == null || "".equals(PropertyName))
		{
			return "";
		}
		else
		{
			for (Entry<String, String> entry : setMap.entrySet())
			{
				if(entry.getKey().equalsIgnoreCase(PropertyName))
				{
					return entry.getValue();
				}
			}
			return "";
		}
	}
}
