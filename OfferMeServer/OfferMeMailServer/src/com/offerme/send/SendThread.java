package com.offerme.send;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.offerme.intf.MessageFactory;
import com.offerme.intf.MessageFactory.MessageName;
import com.offerme.intf.send.ICallBack;
import com.offerme.intf.send.IMessage;
import com.offerme.intf.send.ISendMessage;
import com.offerme.send.database.dbpool.JDBCDataConnection;
import com.offerme.util.Log;

public class SendThread extends Thread implements ICallBack
{
	public static Logger myLog = Logger.getLogger("Send");
	
	public ArrayList<User> userList = new ArrayList<User>();
	
	/**
	 * 缓冲数据集
	 */
	private int SleepTime = 10;
	
	public SendThread(int sleep) throws Exception
	{
		if(sleep == 0)
		{
			SleepTime = 60;
		}
		else
		{
			SleepTime = sleep;
		}
	}
	
	/**
	 * 线程运行
	 */
	public void run()
	{
		System.out.println("[" + getCurrentTimeString() + "] The send  thread is runing");
		while (true)
		{
//			int userId = -1;
			try
			{
				// 从数据库加载要发送的数据
				userList = getSendData();
				// 根据发送类别，分别发送
				ISendMessage sendMessage = null;
				sendMessage = MessageFactory.getManage(MessageName.MAIL);
				if (sendMessage == null)
				{
					sendMessage = MessageFactory.getManage(MessageName.SMS);
					if (sendMessage == null)
					{
						break;
					}
				}
				
				for (User user : userList)
				{
					sendMessage.setCallBack(this);
					sendMessage.send(user);
				}
			}
			catch (Exception e)
			{
//				setSendError(userId, e.getMessage());
				myLog.error(Log.getStackInfo(e));
			}
			finally
			{
				JDBCDataConnection.disconnect();
			}
			try
			{
				// 轮巡休眠
				Thread.sleep(SleepTime * 1000);
			}
			catch (Exception e)
			{
				
			}
		}
	}

	private synchronized ArrayList<User> getSendData() throws Exception
	{
		userList.clear();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" SELECT USER_ID, PHONE, EMAIL FROM USER");
		sbSql.append(" WHERE ISENABLE = 0 ");
		PreparedStatement pstmt = null; 
		try {
			pstmt = JDBCDataConnection.getConnection().prepareStatement(sbSql.toString());
			ResultSet rs = pstmt.executeQuery();
			User user = new User();
			while (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt(1));
				user.setPhone(rs.getString(2));
				user.setEmail(rs.getString(3));
				userList.add(user);
			}
		} catch (Exception e) {
			myLog.error(Log.getStackInfo(e));
		}
		finally
		{
			if (pstmt != null) {
				pstmt.close();
			}
		}
		
		return userList;
	}
	
	public synchronized void setSendResult(IMessage message)
	{
		try
		{
			if(message.getBSuccess())
			{
				String sql = "update user " +
		    			"set ISENABLE=?,IDCODEFOREMAIL=? " +
		    			" where user_id=?";
				PreparedStatement pstmt = null; 
				try {
					pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
					pstmt.setInt(1, 2);
					pstmt.setString(2, message.getIdCode());
					pstmt.setInt(3, message.getUser().getUserId());
					pstmt.executeUpdate();
				} catch (SQLException e) {
					myLog.error(Log.getStackInfo(e));
				}
				finally
				{
					if (pstmt != null) {
						pstmt.close();
					}
				}
			}
			else
			{
				String sql = "update user " +
		    			"set ISENABLE=?" +
		    			" where user_id=?";
				PreparedStatement pstmt = null; 
				try {
					pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
					pstmt.setInt(1, 1);
					pstmt.setInt(2, message.getUser().getUserId());
					pstmt.executeUpdate();
				} catch (SQLException e) {
					myLog.error(Log.getStackInfo(e));
				}
				finally
				{
					if (pstmt != null) {
						pstmt.close();
					}
				}
			}
		}
		catch (Exception e)
		{
			myLog.error("往数据库回写状态时出错：", e);
		}
		
	}
	
	/**
	 * 设置错误信息
	 * @param notifyID
	 * @param message
	 */
	private synchronized void setSendError(int userId, String errorInfo)
	{
		try
		{
			String sql = "update user " +
	    			"set ISENABLE=?" +
	    			" where user_id=?";
			PreparedStatement pstmt = null; 
			try {
				pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
				pstmt.setInt(1, 1);
				pstmt.setInt(2, userId);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				myLog.error(Log.getStackInfo(e));
			}
			finally
			{
				if (pstmt != null) {
					pstmt.close();
				}
			}
		}
		catch (Exception e)
		{
			myLog.error("往数据库回写状态时出错：", e);
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
