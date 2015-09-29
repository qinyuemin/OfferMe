package com.offerme.send.database.dbpool;

import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * 
 * 统一控制数据库的连接和释放
 * @author Edouard.Zhang
 * 
 */
public class JDBCDataConnection {

	static Logger myLog = Logger.getLogger("com.offerme.server.database.db");

	private static ThreadLocal<Connection> conn = new ThreadLocal<Connection>();

	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public static Connection getConnection() {
		
		if(conn.get() == null)
		{
			try {
				conn.set(DataPoolManager.getConnection("OfferMe"));
			} catch (Exception e) {
				myLog.error("获取数据库连接失败"+e.getMessage());
			}
		}
		return conn.get();
	}

	public static void disconnect(){
	
		if (conn.get() != null) {
			try {
				conn.get().close();
//				conn = new ThreadLocal<Connection>();
				conn.remove();
			} catch (SQLException e) {
				myLog.error("释放数据库连接失败"+e.getMessage());
			}
		}
	}

}
