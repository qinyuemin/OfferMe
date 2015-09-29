package com.offerme.intf;

import java.lang.reflect.Method;
public class SendConfig
{

	public class MOD
	{
		public static final String SINGLE = "SINGLE"; // 单例

		public static final String MULTI = "MULTI"; // 多例

		public static final String MULTI_POOL = "MULTI_POOL"; // 多例_对象池
	}
	
	public static class columns
	{
		public static final String ENABLE = "ENABLE";
		public static final String JARNAME = "JARNAME";
		public static final String CLASSNAME = "CLASSNAME";
		public static final String METHODNAME = "METHODNAME";
		public static final String MOD = "MOD";
	}
	
	public String jarName = "";
	public String className = "";
	public String mothedName = "";
	public String mod = "";

	public SendConfig(String jarName,String className,String mothedName,String mod)
	{
		this.jarName = jarName;
		this.className = className;
		this.mothedName = mothedName;
		this.mod = mod;
	}
	
	private Class<?> classObj = null;
	private Method method = null;
	
	public void setClassObj(Class<?> c)
	{
		classObj = c;
	}

	public Class<?> getClassObj()
	{
		return classObj;
	}

	public void setMethod(Method m)
	{
		method = m;
	}

	public Method getMethod()
	{
		return method;
	}
	
}
