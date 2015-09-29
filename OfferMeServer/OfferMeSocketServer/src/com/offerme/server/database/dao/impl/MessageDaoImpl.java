package com.offerme.server.database.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.offerme.server.database.dao.MessageDao;
import com.offerme.server.database.dao.UserDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.database.dbpool.JDBCDataConnection;
import com.offerme.server.database.model.Message;
import com.offerme.server.database.model.User;
import com.offerme.server.service.threadpool.Request;
import com.offerme.server.util.Log;
import com.offerme.server.util.ResultSetToModel;
import com.offerme.server.util.SqlCommand;

public class MessageDaoImpl extends BaseDaoImpl implements MessageDao {

	private UserDao userDao;

	public MessageDaoImpl() {
		this.userDao = DaoImplManage.getUserDaoInstance();
	}

	public int insertMessage(Message message) throws InvocationTargetException {
		String sql = "Insert into message (SENDER_ID,RECEIVER_ID,TITLE,CONTENT,CRE_DT,STATUS,PROFILEVERSION) Values (?,?,?,?,?,?,?)";
		int idReturn = -1;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql, Statement.RETURN_GENERATED_KEYS);
			this.pstmt.setInt(1, message.getsUserId());
			this.pstmt.setInt(2, message.getrUserId());
			this.pstmt.setString(3, message.getTitle());
			this.pstmt.setString(4, message.getContent());
			this.pstmt.setTimestamp(5, new Timestamp(new Date().getTime()));
			this.pstmt.setInt(6, 0);
			this.pstmt.setInt(7, message.getProfileVersion());

			ResultSet rs = null;
			this.pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				idReturn = rs.getInt(1);
			}
			message.setMessageId(idReturn);
			return idReturn;
		} catch (SQLException e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
	}

	public void updateMessage(Message message) throws InvocationTargetException {
		String sql = "update message "
				+ " set SENDER_ID=?,RECEIVER_ID=?,TITLE=?,CONTENT=?,CRE_DT=?,STATUS=?,PROFILEVERSION=?"
				+ " where MESSAGE_ID = ?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, message.getsUserId());
			this.pstmt.setInt(2, message.getrUserId());
			this.pstmt.setString(3, message.getTitle());
			this.pstmt.setString(4, message.getContent());
			this.pstmt.setTimestamp(5, message.getDate());
			this.pstmt.setInt(6, message.getStatus());
			this.pstmt.setInt(7, message.getProfileVersion());
			this.pstmt.setInt(8, message.getMessageId());
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
	}

	public void deleteMessage(int messageId) throws InvocationTargetException {
		String sql = "delete from message " + " where MESSAGE_ID = ?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, messageId);
			this.pstmt.executeUpdate();
		} catch (SQLException e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
	}

	public Message getMessage(int messageId) throws InvocationTargetException {
		Request request = Request.getRequest();
		String where = "(MESSAGE_ID = ?)";
		String order = " CRE_DT desc ";
		if (request != null && request.getWhere() != null && !"".equals(request.getWhere())) {
			where = where + " and (" + request.getWhere() + ")";
		}
		if (request != null && request.getOrder() != null && !"".equals(request.getOrder())) {
			order = order + " , " + request.getOrder();
		}
		SqlCommand sqlCommand = new SqlCommand(
				"MESSAGE_ID as messageId, SENDER_ID as sUserId, RECEIVER_ID as rUserId,TITLE,CONTENT,CRE_DT as date,STATUS,PROFILEVERSION",
				"message", where, request==null?-1:request.getStart(), request==null?-1:request.getSize(), order);
		// String sql =
		// "select MESSAGE_ID as messageId, SENDER_ID as sUserId, RECEIVER_ID as rUserId,TITLE,CONTENT,CRE_DT,STATUS from message "
		// +
		// " where MESSAGE_ID = ?";
		Message message = null;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sqlCommand.getSql());
			this.pstmt.setInt(1, messageId);
			ResultSet rs = this.pstmt.executeQuery();
			if(request!= null)
			{
				request.clearCondition();
			}
			

			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs,
					"com.offerme.server.database.model.Message");
			if (obj.length > 0) {
				message = (Message) obj[0];
				User user = userDao.getUser(message.getsUserId());
				message.setSUser(user);
				user = userDao.getUser(message.getrUserId());
				message.setRUser(user);
			}
		} catch (Exception e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
		return message;

	}

	@Override
	public List<Message> getMessagesBySender(int sUserId)
			throws InvocationTargetException {

		Request request = Request.getRequest();
		String where = "(SENDER_ID = ?)";
		String order = " CRE_DT desc ";
		if (request!= null && request.getWhere() != null && !"".equals(request.getWhere())) {
			where = where + " and (" + request.getWhere() + ")";
		}
		if (request!= null && request.getOrder() != null && !"".equals(request.getOrder())) {
			order = order + " , " + request.getOrder();
		}
		SqlCommand sqlCommand = new SqlCommand(
				"MESSAGE_ID as messageId, SENDER_ID as sUserId, RECEIVER_ID as rUserId,TITLE,CONTENT,CRE_DT as date,STATUS,PROFILEVERSION",
				"message", where, request==null?-1:request.getStart(), request==null?-1:request.getSize(), order);
		List<Object> msgList = null;
		List<Message> msgListReturn = new ArrayList<Message>();
		// String sql =
		// "select MESSAGE_ID as messageId, SENDER_ID as sUserId, RECEIVER_ID as rUserId,TITLE,CONTENT,CRE_DT,STATUS from message "
		// +
		// " where SENDER_ID = ?";
		Message message = null;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sqlCommand.getSql());
			this.pstmt.setInt(1, sUserId);
			ResultSet rs = this.pstmt.executeQuery();
			request.clearCondition();

			msgList = ResultSetToModel.parseDataEntityBeanList(rs,
					"com.offerme.server.database.model.Message");
			User user = null;
			for (int i = 0; i < msgList.size(); i++) {
				message = (Message) msgList.get(i);
				user = userDao.getUser(message.getsUserId());
				message.setSUser(user);
				user = userDao.getUser(message.getrUserId());
				message.setRUser(user);
				msgListReturn.add(message);
			}

		} catch (Exception e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
		return msgListReturn;
	}

	@Override
	public List<Message> getMessagesByReceiverIncrem(int rUserId)
			throws InvocationTargetException {

		Request request = Request.getRequest();
		String where = "(RECEIVER_ID = ? and STATUS = 0)";
		String order = " CRE_DT desc ";
		if (request != null && request.getWhere() != null && !"".equals(request.getWhere())) {
			where = where + " and (" + request.getWhere() + ")";
		}
		if (request != null && request.getOrder() != null && !"".equals(request.getOrder())) {
			order = order + " , " + request.getOrder();
		}
		SqlCommand sqlCommand = new SqlCommand(
				"MESSAGE_ID as messageId, SENDER_ID as sUserId, RECEIVER_ID as rUserId,TITLE,CONTENT,CRE_DT as date,STATUS,PROFILEVERSION",
				"message", where, request==null?-1:request.getStart(), request==null?-1:request.getSize(), order);
		List<Object> msgList = null;
		List<Message> msgListReturn = new ArrayList<Message>();
		// String sql =
		// "select MESSAGE_ID as messageId, SENDER_ID as sUserId, RECEIVER_ID as rUserId,TITLE,CONTENT,CRE_DT,STATUS from message "
		// +
		// " where RECEIVER_ID = ? and STATUS = 0";
		Message message = null;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sqlCommand.getSql());
			this.pstmt.setInt(1, rUserId);
			ResultSet rs = this.pstmt.executeQuery();
			request.clearCondition();

			msgList = ResultSetToModel.parseDataEntityBeanList(rs,
					"com.offerme.server.database.model.Message");
			User user = null;
			for (int i = 0; i < msgList.size(); i++) {
				message = (Message) msgList.get(i);
				user = userDao.getUser(message.getsUserId());
				message.setSUser(user);
				user = userDao.getUser(message.getrUserId());
				message.setRUser(user);
				msgListReturn.add(message);
				message.setStatus(1);
				updateMessage(message);
			}
		} catch (Exception e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
		return msgListReturn;
	}

	public List<Message> getMessagesByReceiver(int rUserId)
			throws InvocationTargetException {

		Request request = Request.getRequest();
		String where = "(RECEIVER_ID = ?)";
		String order = " CRE_DT desc ";
		if (request != null && request.getWhere() != null && !"".equals(request.getWhere())) {
			where = where + " and (" + request.getWhere() + ")";
		}
		if (request != null && request.getOrder() != null && !"".equals(request.getOrder())) {
			order = order + " , " + request.getOrder();
		}
		SqlCommand sqlCommand = new SqlCommand(
				"MESSAGE_ID as messageId, SENDER_ID as sUserId, RECEIVER_ID as rUserId,TITLE,CONTENT,CRE_DT as date,STATUS,PROFILEVERSION",
				"message", where, request==null?-1:request.getStart(), request==null?-1:request.getSize(), order);
		List<Object> msgList = null;
		List<Message> msgListReturn = new ArrayList<Message>();
		// String sql =
		// "select MESSAGE_ID as messageId, SENDER_ID as sUserId, RECEIVER_ID as rUserId,TITLE,CONTENT,CRE_DT,STATUS from message "
		// +
		// " where RECEIVER_ID = ?";
		Message message = null;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sqlCommand.getSql());
			this.pstmt.setInt(1, rUserId);
			ResultSet rs = this.pstmt.executeQuery();

			msgList = ResultSetToModel.parseDataEntityBeanList(rs,
					"com.offerme.server.database.model.Message");
			User user = null;
			for (int i = 0; i < msgList.size(); i++) {
				message = (Message) msgList.get(i);
				user = userDao.getUser(message.getsUserId());
				message.setSUser(user);
				user = userDao.getUser(message.getrUserId());
				message.setRUser(user);
				msgListReturn.add(message);
				// 设置message为已读 并且更新到数据库
				message.setStatus(1);
				updateMessage(message);
			}

		} catch (Exception e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
		return msgListReturn;
	}

}
