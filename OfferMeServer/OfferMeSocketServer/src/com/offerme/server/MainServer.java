package com.offerme.server;

import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.xml.DOMConfigurator;

import com.offerme.server.service.threadpool.ThreadPoolMgn;
import com.offerme.server.service.threadpool.ThreadTask;
import com.offerme.server.util.Log;

/**
 * 主线程
 * @author Edouard.Zhang
 *
 */
public class MainServer {
	
	static Log myLog = null;
	
	public ServiceSetting m_SettingData = new ServiceSetting();
	
	private SocketListener sockListener;
	
	/**
	 * 总服务线程
	 */
	private static MainServer mainServer = null;
	
	public static MainServer getMainServer()
	{
		return mainServer;
	}
	
	public static void main(String[] args) {
		
		try
		{
			mainServer = new MainServer();
			myLog = new Log("com.offerme.server");
			String strCurDir = System.getProperty("user.dir");
			DOMConfigurator.configure(strCurDir + "/XMLFiles/Log4JConfig.xml");
			
			mainServer.Start();
			
			
			
			int readData = 0;
			while (readData != 13 && readData != 10)
			{
				Thread.sleep(100000);
			}
		}
		catch (Exception e)
		{
			myLog.error(e.getMessage());
			myLog.error(Log.getStackInfo(e));
		}
		
	}
	
	/**
	 * 启动服务
	 * 
	 */
	public void Start()
	{
		initSettings();
		if(sockListener == null)
		{
			sockListener = new SocketListener();
			sockListener.setName("sockListener");
			sockListener.setPriority(Thread.MAX_PRIORITY);
			sockListener.start();
			myLog.info("sockListener started!");
			System.out.println("sockListener started!");
		}
	}
	
	/**
	 * 初始化
	 * @param filename
	 */
	public void initSettings()
	{
		String strCurDir = System.getProperty("user.dir");
		try {
			File inf = new File( strCurDir +"/XMLFiles/"+ "/initial-data.properties");
			FileInputStream is = new FileInputStream(inf);
			Properties dbProps = new Properties();
			dbProps.load(is);
			
			m_SettingData.intPort= Integer.parseInt(dbProps.getProperty("intPort"));
			m_SettingData.intReceiveDataWaiting= Integer.parseInt(dbProps.getProperty("intReceiveDataWaiting"));
			m_SettingData.bLog = Integer.parseInt(dbProps.getProperty("bLog"))==1?true:false;
			m_SettingData.bStatistique = Integer.parseInt(dbProps.getProperty("bStatistique"))==1?true:false;
			
		} catch (Exception e) {
		
		}
		

	}
	
	/**
	 * Socket监听线程
	 * @author Edouard.Zhang
	 *
	 */
	public class SocketListener extends Thread 
	{
		
		ThreadPoolMgn poolMgn = new ThreadPoolMgn();
		ThreadPoolExecutor threadPool = poolMgn.getProcessThreadPool();
		
		public void run()
		{
			
			ServerSocket thread_listener;
			try
			{
				// 线程池放到全局
				GlobalData.setPoolMgn(poolMgn);
				System.out.println("SocketListener port：" + m_SettingData.intPort);
				thread_listener = new ServerSocket(m_SettingData.intPort);

				Socket clientSocket = null;
				while (true)
				{
					myLog.info("Accept Wait");
					clientSocket = thread_listener.accept();
					clientSocket.setSoTimeout(m_SettingData.intReceiveDataWaiting);
					myLog.info("AcceptSocket");
					ThreadTask threadtask = new ThreadTask(getMainServer(), clientSocket);
					threadPool.execute(threadtask);
				}
			}
			catch (Exception e)
			{
				String message = "Logis Server failed in Listener:" + e.getLocalizedMessage();
				myLog.error(message);
				myLog.error(Log.getStackInfo(e));
			}
		}
	}	

}
