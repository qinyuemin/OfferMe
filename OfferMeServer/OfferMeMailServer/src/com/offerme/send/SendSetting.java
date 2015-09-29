package com.offerme.send;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.offerme.intf.MessageFactory;
import com.offerme.intf.SendConfig;


/**
 * XML文件对应变量
 * 
 */
public class SendSetting
{
	
	private static final String file = System.getProperty("user.dir") + "/Config/SendConfig.xml";

	/**
	 * 配置项集合
	 */
	private static HashMap<String, String> setMap = new HashMap<String, String>();
	
	public static void initSendConfig() throws Exception
	{ 
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(file));
		Element root = document.getRootElement();

		for (Iterator<Element> iterator = root.elementIterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			String name = element.getName();
			setMap.put(name, element.getStringValue());
			if(element.element(SendConfig.columns.ENABLE) != null && "1".equals(element.element(SendConfig.columns.ENABLE).getStringValue()))
			{
				String jarName = "";
				String className = "";
				String mothedName = "";
				String mod = "";
				jarName = element.element(SendConfig.columns.JARNAME) == null?"":element.element(SendConfig.columns.JARNAME).getStringValue();
				className = element.element(SendConfig.columns.CLASSNAME) == null?"":element.element(SendConfig.columns.CLASSNAME).getStringValue();
				mothedName = element.element(SendConfig.columns.METHODNAME) == null?"":element.element(SendConfig.columns.METHODNAME).getStringValue();
				mod = element.element(SendConfig.columns.MOD) == null?"":element.element(SendConfig.columns.MOD).getStringValue();
				SendConfig mc = new SendConfig(jarName, className, mothedName, mod);
				
				try
				{
					MessageFactory.addManage(name, mc);
					System.out.println(name + " is runing...");
				}
				catch (Exception e)
				{
					throw new Exception(name + " load faild...");
				}
			}
		}
	}
	
	/**
	 * 获取属性信息
	 * @param PropertyName
	 * @return
	 */
	public static String getProperty(String PropertyName)
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
