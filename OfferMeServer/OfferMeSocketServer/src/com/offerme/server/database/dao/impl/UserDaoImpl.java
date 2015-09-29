package com.offerme.server.database.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import com.offerme.server.database.dao.ClassementDetailDao;
import com.offerme.server.database.dao.OfferDao;
//import com.offerme.server.database.dao.OfferUserDao;
import com.offerme.server.database.dao.UserDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.database.dbpool.JDBCDataConnection;
import com.offerme.server.database.model.Offer;
import com.offerme.server.database.model.OfferUser;
import com.offerme.server.database.model.User;
import com.offerme.server.service.threadpool.Request;
import com.offerme.server.util.Log;
import com.offerme.server.util.ResultSetToModel;
import com.offerme.server.util.SqlCommand;

public class UserDaoImpl extends BaseDaoImpl implements UserDao{
	
	//private ClassementDetailDao classementDetailDao;
	//private OfferUserDao offerUserDao;
	private OfferDao offerDao;

	public UserDaoImpl() {
		//this.offerUserDao = DaoImplManage.getOfferUserDaoInstance();
		this.offerDao = DaoImplManage.getOfferDaoInstance(this);
		//this.classementDetailDao = DaoImplManage.getClassementDetailDaoInstance();
	}
	
	public UserDaoImpl(OfferDao offerDao) {
		//this.offerUserDao = DaoImplManage.getOfferUserDaoInstance();
		this.offerDao = offerDao;
		//this.classementDetailDao = DaoImplManage.getClassementDetailDaoInstance();
	}

	public int insertUser(User user) throws InvocationTargetException
	{	
		checkUserEMAIL(user.getEmail());
		//checkUserPhone(user.getPhone());//don't need to check the phone number actuellment
		String sql = "Insert into user (NAME,NAME_FAMILY,CITY,COUNTRY,COMPANY,NICKNAME,EMAIL,PHONE,PSW,PORTRAIT,SIGNUP_DT,SIGNIN_COUNT,SIGN_LAST_DT,IS_ENABLE,EMAIL_PUBLIC,PHONE_PUBLIC,FAVORITE,AGE,POST,WORKYEAR)Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int idReturn= -1;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			this.pstmt.setString(1, user.getName());
			this.pstmt.setString(2, user.getNameFamily());
			this.pstmt.setString(3, user.getCity());
			this.pstmt.setString(4, user.getCountry());
			this.pstmt.setString(5, user.getCompany());
			this.pstmt.setString(6, user.getNickName());
			this.pstmt.setString(7, user.getEmail());
			this.pstmt.setString(8, user.getPhone());
			this.pstmt.setString(9, user.getPsw());
			this.pstmt.setBytes(10, user.getPortrait());
			this.pstmt.setTimestamp(11, user.getSignUpDt());
			this.pstmt.setInt(12, user.getSignInCount());
			this.pstmt.setTimestamp(13, user.getSignLastDt());
			this.pstmt.setInt(14, user.getIsEnable());
			this.pstmt.setInt(15, user.getEmailPublic());
			this.pstmt.setInt(16, user.getPhonePublic());
			this.pstmt.setInt(17, user.getFavorite());
			this.pstmt.setInt(18, user.getAge());
			this.pstmt.setString(19, user.getPost());
			this.pstmt.setInt(20, user.getWorkyear());
			ResultSet rs=null;
			this.pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
            	idReturn = rs.getInt(1);
            }
			return idReturn;
		} catch (SQLException e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
		finally
		{
			this.free();
		}
	}
    
    public void updateUser(User user) throws InvocationTargetException
    {
    	String sql = "update user " +
    			"set NAME=?,NAME_FAMILY=?,CITY=?,COUNTRY=?,COMPANY=?,NICKNAME=?,EMAIL=?,PHONE=?,PSW=?,PORTRAIT=?,SIGNUP_DT=?,SIGNIN_COUNT=?,SIGN_LAST_DT=?,IS_ENABLE=?,EMAIL_PUBLIC=?,PHONE_PUBLIC=?,FAVORITE=?,PORTRAIT_UPDATE_DT=?,AGE=?,POST=?,WORKYEAR=?" +
    			" where user_id=?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setString(1, user.getName());
			this.pstmt.setString(2, user.getNameFamily());
			this.pstmt.setString(3, user.getCity());
			this.pstmt.setString(4, user.getCountry());
			this.pstmt.setString(5, user.getCompany());
			this.pstmt.setString(6, user.getNickName());
			this.pstmt.setString(7, user.getEmail());
			this.pstmt.setString(8, user.getPhone());
			this.pstmt.setString(9, user.getPsw());
			this.pstmt.setBytes(10, user.getPortrait());
			this.pstmt.setTimestamp(11, user.getSignUpDt());
			this.pstmt.setInt(12, user.getSignInCount());
			this.pstmt.setTimestamp(13, user.getSignLastDt());
			this.pstmt.setInt(14, user.getIsEnable());
			this.pstmt.setInt(15, user.getEmailPublic());
			this.pstmt.setInt(16, user.getPhonePublic());
			this.pstmt.setInt(17, user.getFavorite());
			this.pstmt.setInt(18, user.getPortraitUpdateDt());
			this.pstmt.setInt(19, user.getAge());
			this.pstmt.setString(20, user.getPost());
			this.pstmt.setInt(21, user.getWorkyear());
			this.pstmt.setInt(22, user.getUserId());
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
			
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
		finally
		{
			this.free();
		}
	}
    
    public void disableUser(int userId) throws InvocationTargetException
    {
    	String sql = "update user " +
    			" set isenable = "+ String.valueOf(User.Enable.DisabledUser)+
				" where user_id = ?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setInt(1, userId);
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
		finally
		{
			this.free();
		}
    }
    
    public void deleteUser(int userId) throws InvocationTargetException
    {
    	String sql = "delete from user " +
				" where user_id = ?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setInt(1, userId);
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
		finally
		{
			this.free();
		}
    }
    
    public User getUser(int userId) throws InvocationTargetException
    {
    	Request request = Request.getRequest();
    	String where = "(user_id = ?)";
    	if(request!= null && request.getWhere() != null && !"".equals(request.getWhere()))
    	{
    		where = where + " and (" + request.getWhere() + ")";
    	}
		SqlCommand sqlCommand = new SqlCommand(
				"USER_ID as userId, NAME,NAME_FAMILY as nameFamily,CITY,COUNTRY,COMPANY,NICKNAME,EMAIL,PHONE,PSW,PORTRAIT,SIGNUP_DT as signupDt,SIGNIN_COUNT as signinCount,SIGN_LAST_DT as signLastDt,IS_ENABLE as isEnable,EMAIL_PUBLIC as emailPublic,PHONE_PUBLIC as phonePublic,FAVORITE,PORTRAIT_UPDATE_DT as portraitUpdateDt,AGE,POST,WORKYEAR ",
				"user", where,request==null?-1:request.getStart(), request==null?-1:request.getSize(), request==null?"":request.getOrder());
//    	String sql = "select USER_ID as userId, NAME,NAME_FAMILY as nameFamily,CITY,COUNTRY,COMPANY,NICKNAME,EMAIL,PHONE,PSW,PORTRAIT,SIGNUP_DT,SIGNIN_COUNT,SIGN_LAST_DT,IS_ENABLE,EMAIL_PUBLIC,PHONE_PUBLIC from user " +
//				" where (user_id = ?)";
    	User user = null;
    	try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sqlCommand.getSql());
			this.pstmt.setInt(1, userId);
			ResultSet rs = this.pstmt.executeQuery();
		
			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs, "com.offerme.server.database.model.User");
			if(obj.length > 0)
			{
				user = (User)obj[0];
			}
			
		} catch (Exception e) {
			
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
    	finally
		{
			this.free();
		}
    	
    	return user;
    }
    
    public User[] getAllUser() throws InvocationTargetException
    {
    	Request request = Request.getRequest();
		SqlCommand sqlCommand = new SqlCommand(
				"USER_ID as userId, NAME,NAME_FAMILY as nameFamily,CITY,COUNTRY,COMPANY,NICKNAME,EMAIL,PHONE,PSW,PORTRAIT,SIGNUP_DT as signupDt,SIGNIN_COUNT as signinCount,SIGN_LAST_DT as signLastDt,IS_ENABLE as isEnable,EMAIL_PUBLIC  as emailPublic,PHONE_PUBLIC as phonePublic,FAVORITE,PORTRAIT_UPDATE_DT as portraitUpdateDt,AGE,POST,WORKYEAR ",
				"user", request==null?"":request.getWhere(), request==null?-1:request.getStart(), request==null?-1:request.getSize(), request==null?"":request.getOrder());
//    	String sql = "select USER_ID as userId, NAME,NAME_FAMILY as nameFamily,CITY,COUNTRY,COMPANY,NICKNAME,EMAIL,PHONE,PSW,PORTRAIT,SIGNUP_DT,SIGNIN_COUNT,SIGN_LAST_DT,IS_ENABLE,EMAIL_PUBLIC,PHONE_PUBLIC from user ";
    	User[] userArray = null;
    	User user = null;
    	try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sqlCommand.getSql());
			ResultSet rs = this.pstmt.executeQuery();

			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs, "com.offerme.server.database.model.User");
			for (Object object : obj) {
				user = (User)object;
			}
			userArray = (User[])obj;
			
		} catch (Exception e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
    	finally
		{
			this.free();
		}
    	return userArray;
    }
    
    public List<User> getAllUserList() throws InvocationTargetException
    {
    	Request request = Request.getRequest();
		SqlCommand sqlCommand = new SqlCommand(
				"USER_ID as userId, NAME,NAME_FAMILY as nameFamily,CITY,COUNTRY,COMPANY,NICKNAME,EMAIL,PHONE,PSW,PORTRAIT,SIGNUP_DT as signupDt,SIGNIN_COUNT as signinCount,SIGN_LAST_DT as signLastDt,IS_ENABLE as isEnable,EMAIL_PUBLIC as emailPublic,PHONE_PUBLIC as phonePublic,FAVORITE,PORTRAIT_UPDATE_DT as portraitUpdateDt,AGE,POST,WORKYEAR ",
				"user", request==null?"":request.getWhere(), request==null?-1:request.getStart(), request==null?-1:request.getSize(), request==null?"":request.getOrder());
//    	String sql = "select USER_ID as userId, NAME,NAME_FAMILY as nameFamily,CITY,COUNTRY,COMPANY,NICKNAME,EMAIL,PHONE,PSW,PORTRAIT,SIGNUP_DT,SIGNIN_COUNT,SIGN_LAST_DT,IS_ENABLE,EMAIL_PUBLIC,PHONE_PUBLIC from user ";
    	List<User> userList = new ArrayList<User>();
    	User user = null;
    	try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sqlCommand.getSql());
			ResultSet rs = this.pstmt.executeQuery();
			
			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs, "com.offerme.server.database.model.User");
			for (Object object : obj) {
				user = (User)object;
				userList.add(user);
			}
			
		} catch (Exception e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
    	finally
		{
			this.free();
		}
    	return userList;
    }
	
	private void checkUserEMAIL(String email) throws InvocationTargetException
	{		
    	String sql = "select count(USER_ID) from user " +
				" where EMAIL = ?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setString(1, email);
			
			ResultSet rs = this.pstmt.executeQuery();
			int count = -1;
            if (rs.next()) {
            	count = rs.getInt(1);
            }
			if(count>0)
			{	
				throw new InvocationTargetException(null, "邮箱已经被注册");
			}
			else
			{
				return;
			}
		} catch (SQLException e) {
			
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
		finally
		{
			this.free();
		}
	}
    
    private void checkUserPhone(String phone) throws InvocationTargetException
	{		
    	String sql = "select count(USER_ID) from user " +
				" where PHONE = ?";
    	
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setString(1, phone);
			
			ResultSet rs = this.pstmt.executeQuery();
			int count = -1;
            if (rs.next()) {
            	count = rs.getInt(1);
            }
			if(count>0)
			{
				throw new InvocationTargetException(null, "手机号已经被注册");
			}
			else
			{
				return;
			}
		} catch (SQLException e) {
			
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
		finally
		{
			this.free();
		}
	}

	public User getUserByEmailAndPassword(String email, String password)
			throws InvocationTargetException {
    	String sql = "select USER_ID as userId, NAME,NAME_FAMILY as nameFamily,CITY,COUNTRY,COMPANY,NICKNAME,EMAIL,PHONE,PSW,PORTRAIT,SIGNUP_DT as signupDt,SIGNIN_COUNT as signinCount,SIGN_LAST_DT as signLastDt,IS_ENABLE as isEnable,EMAIL_PUBLIC  as emailPublic,PHONE_PUBLIC as phonePublic,FAVORITE,PORTRAIT_UPDATE_DT as portraitUpdateDt,AGE,POST,WORKYEAR from user " +
				" where EMAIL = ? and PSW = ?";
    	User user = null;
    	try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setString(1, email);
			this.pstmt.setString(2, password);
			ResultSet rs = this.pstmt.executeQuery();
			
			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs, "com.offerme.server.database.model.User");
			if(obj.length > 0) {
				user = (User)obj[0];
			}
			
		} catch (Exception e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		} finally {
			this.free();
		}
    	return user;
	}
    
	public List<Offer> getFavoriteOfferByUserId(int userId) throws InvocationTargetException
    {
    	List<Offer> offerList = new ArrayList<Offer>();
    	String sql = "select offer_id as offerId, USER_ID as userId, status from offer_user " +
				" where USER_ID = ? ";
    	OfferUser offerUser = null;
    	Offer offer = null;
    	try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setInt(1, userId);
			ResultSet rs = this.pstmt.executeQuery();

			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs, "com.offerme.server.database.model.OfferUser");
			for (Object object : obj) {
				offerUser = (OfferUser)object;
				offer = offerDao.getOffer(offerUser.getOfferId());
				offerList.add(offer);					
			}
			
		} catch (Exception e) {
			
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
    	finally
		{
			this.free();
		}
    	return offerList;
    }
	
	public boolean ValidateUser(int userId, String validateCode)throws InvocationTargetException
	{
		boolean bValidate  = false;
		String sql = " select USER_ID as userId, IS_ENABLE, MAIL_IDENTIFY_CODE from user " +
				" where USER_ID = ? ";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setInt(1, userId);
			ResultSet rs = this.pstmt.executeQuery();

			if(rs.next())
			{
				//int isenable = rs.getInt("IS_ENABLE");
				String originCode = rs.getString("MAIL_IDENTIFY_CODE");
				if(validateCode.equalsIgnoreCase(originCode))
				{
					bValidate = true;
				}
			}
			
		} catch (SQLException e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		} finally {
			this.free();
		}
    	
		return bValidate;
	}
    
	public void changeLike(int userId, int change) throws InvocationTargetException
	{
		String sql = "update user " +
    			"set FAVORITE=? " +
    			" where user_id=?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setInt(1, change);
			this.pstmt.setInt(2, userId);
			
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
			
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
		finally
		{
			this.free();
		}
	}
}
