package com.offerme.intf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.offerme.intf.SendConfig.MOD;
import com.offerme.intf.send.ISendMessage;
import com.offerme.util.DynamicUtil;


public class MessageFactory
{
	public static Logger myLog = Logger.getLogger("Send");
	
	public class MessageName
	{
		public static final String MAIL = "MAIL"; // 邮件
		
		public static final String SMS = "SMS"; // 短信
	}
	
	private static HashMap<String, SendConfig> manageMap = new HashMap<String, SendConfig>();
	
	public static void addManage(String manageName, SendConfig mc) throws Exception
	{
		try
		{
			if (mc.mod.equalsIgnoreCase(MOD.SINGLE))
			{
				// 加载单例
				Class<?> c = DynamicUtil.getClass(mc.jarName, mc.className);
				if (c == null)
				{
					throw new Exception("没能装载指定的类(" + mc.jarName + "," + mc.className + ")! ");
				}
				Method m = c.getMethod(mc.mothedName);
				m.invoke(c.newInstance());
				mc.setClassObj(c);
				mc.setMethod(m);
				manageMap.put(manageName.toUpperCase(), mc);
			}
			else if (mc.mod.equalsIgnoreCase(MOD.MULTI))
			{
				// 加载多例
				Class<?> c = DynamicUtil.getClass(mc.jarName, mc.className);
				if (c == null)
				{
					throw new Exception("没能装载指定的类(" + mc.jarName + "," + mc.className + ")! ");
				}
				c.newInstance();
				mc.setClassObj(c);
				manageMap.put(manageName.toUpperCase(), mc);
			}
			else
			{
				throw new Exception("Manage(" + manageName.toUpperCase() + ")的加载模式(" + mc.mod + ")不支持！");
			}
		}
		catch (Exception e)
		{
			if (e instanceof InvocationTargetException)
			{
				throw new Exception(((InvocationTargetException) e).getTargetException().getLocalizedMessage());
			}
			else
			{
				throw e;
			}
		}
	}
	
	/**
	 * 根据名称获取相应的Manage
	 * @param name
	 * @return
	 */
	public static ISendMessage getManage(String name) throws Exception
	{
		SendConfig mc = manageMap.get(name.toUpperCase());
		if(mc != null)
		{
			if (mc.mod.equalsIgnoreCase(MOD.SINGLE))
			{
				return (ISendMessage) mc.getMethod().invoke(mc.getClassObj().newInstance());
			}
			else if (mc.mod.equalsIgnoreCase(MOD.MULTI))
			{
				return (ISendMessage) mc.getClassObj().newInstance();
			}
			else
			{
				throw new Exception("(" + name + ")的加载模式(" + mc.mod + ")不支持！");
			}
		}
		return null;
	}
	
	public static ArrayList<ISendMessage> getManages() throws Exception
	{
		ArrayList<ISendMessage> register = new ArrayList<ISendMessage>();
		SendConfig mc = null;
		for (Entry<String, SendConfig> entry : manageMap.entrySet())
		{
			mc = entry.getValue();
			if (mc.mod.equalsIgnoreCase(MOD.SINGLE))
			{
				register.add((ISendMessage) mc.getMethod().invoke(mc.getClassObj()));
			}
			else if (mc.mod.equalsIgnoreCase(MOD.MULTI))
			{
				register.add((ISendMessage) mc.getClassObj().newInstance());
			}
			else
			{
				throw new Exception("(" + entry.getKey() + ")的加载模式(" + mc.mod + ")不支持！");
			}
		}
		return register;
	}
	
}
