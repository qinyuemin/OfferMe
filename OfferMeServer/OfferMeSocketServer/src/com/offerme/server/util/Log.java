package com.offerme.server.util;
import org.apache.log4j.Logger;

import com.offerme.server.MainServer;

public class Log {
	
	private Logger logger;
	private boolean bLog;
	
	public Log(String name) {
		
		logger = Logger.getLogger(name);
		bLog = MainServer.getMainServer().m_SettingData.bLog;
	}
	
	public void error(Object message) {
		
		if(bLog)
		{
			logger.error(message);
		}		
	}
	
	public void info(Object message) {
		
		if(bLog)
		{
			logger.info(message);
		}
	}

	/**
	 * 获取异常的堆栈信息
	 * @param e 异常
	 * @return
	 */
	public static String getStackInfo(Exception e){
		StringBuffer sb = new StringBuffer();
		sb.append(e.toString() + "\n");
		for (int i = 0; i < e.getStackTrace().length; i++) {
			sb.append(e.getStackTrace()[i] + "\n");
		}
		return sb.toString();
	}

	/**
	 * 如果该异常为数据库异常，我们将输出其执行的SQL语句
	 * @param e 异常
	 * @param sql SQL语句
	 * @return
	 */
	public static String getStackInfo(Exception e, String SQL){
		StringBuffer sb = new StringBuffer();
		if(e.getLocalizedMessage() != null)
		{
			if (e.getLocalizedMessage().indexOf("ORA") != -1 || e.getLocalizedMessage().indexOf("SQL") != -1 || e.toString().indexOf("MySQL") != -1)
			{
				sb.append(SQL + "\n");
			}
		}
		sb.append(e.toString() + "\n");
		for (int i = 0; i < e.getStackTrace().length; i++) {
			sb.append(e.getStackTrace()[i] + "\n");
		}
		return sb.toString();
	}
	
	/**
	 * 可以在异常信息前，添加用户自定义的错误信息
	 * @param err 显示错误信息
	 * @param e 异常
	 * @return
	 */
	public static String getErrorStackInfo(Exception e, String err){
		StringBuffer sb = new StringBuffer();
		sb.append(err + "\n" + e.toString() + "\n");
		for (int i = 0; i < e.getStackTrace().length; i++) {
			sb.append(e.getStackTrace()[i] + "\n");
		}
		return sb.toString();
	}
}
