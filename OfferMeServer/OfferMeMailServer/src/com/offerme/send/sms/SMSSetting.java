package com.offerme.send.sms;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SMSSetting
{
	
	private static final String file = System.getProperty("user.dir") + "/Config/SMSConfig.xml";
	
	/**
	 * 属性配置项
	 */
	public class Setting
	{
		// WebService地址
		public static final String WebServiceAddress = "WebServiceAddress";
		// 请求方法名称
		public static final String OperationName = "OperationName";
		// 请求UserName
		public static final String UserName = "UserName";
		// 请求PassWord
		public static final String Password = "Password";
	}

	/**
	 * 配置项集合
	 */
	private HashMap<String, String> setMap = new HashMap<String, String>();
	
	/**
	 * 初始化配置信息
	 * @throws Exception
	 */
	public SMSSetting() throws Exception
	{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(file));
		Element root = document.getRootElement();
		for (Iterator<Element> iterator = root.elementIterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			String name = element.getName();
			setMap.put(name, element.getStringValue());
		}
		
		// 输出配置信息
		System.out.println(" --- SMS Config ---");
		for (Entry<String, String> set : setMap.entrySet())
		{
			System.out.println(set.getKey() + "	:	" + set.getValue());
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
