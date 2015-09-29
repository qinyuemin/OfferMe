package com.offerme.send;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.offerme.util.Log;

/**
 * 服务主入口
 * 
 */
public class StartService
{
	public static Logger myLog = Logger.getLogger("Send");

	public static final String ConfigPath = System.getProperty("user.dir") + "/Config/MessageConfig.xml";
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			System.out.println("[" + getCurrentTimeString() + "]" + " Server is starting...");
			
			// 记载Log4J日志
			DOMConfigurator.configure(System.getProperty("user.dir") + "/Config/SendLog4J.xml");
			
			// 加载配置定义的发送方式
			SendSetting.initSendConfig();
			// 发送邮件线程
			SendThread sendthread = new SendThread(Integer.parseInt(SendSetting.getProperty("Sleep").trim()));
			sendthread.setName("SendThread");
			sendthread.setPriority(Thread.NORM_PRIORITY);
			sendthread.start();
		}
		catch (Exception e)
		{
			myLog.error("发送服务启动出错!");
			myLog.error(Log.getStackInfo(e));
		}
	}

	/**
	 * 内部函数，获取当前时间的字符串格式
	 * 
	 * @return
	 */
	private static String getCurrentTimeString()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return df.format(new Date());
	}

}
