package com.offerme.server.database.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.offerme.server.database.dao.OfferUserDao;
import com.offerme.server.database.dbpool.JDBCDataConnection;
import com.offerme.server.database.model.OfferUser;
import com.offerme.server.util.Log;
import com.offerme.server.util.ResultSetToModel;

public class OfferUserDaoImpl extends BaseDaoImpl implements OfferUserDao{

//	private OfferDao offerDao;
	
	public OfferUserDaoImpl() {
//		this.offerDao = DaoImplManage.getOfferDaoInstance();
	}
	
	public void insertOfferUser(OfferUser offerUser) throws InvocationTargetException
	{
		String sql = "Insert into offer_user (user_id,offer_id, status)Values(?,?,?)";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			this.pstmt.setInt(1, offerUser.getUserId());
			this.pstmt.setInt(2, offerUser.getOfferId());
			this.pstmt.setString(3, offerUser.getStatus());
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
		finally
		{
			this.free();
		}		
	}
    
    public void updateOfferUser(OfferUser offerUser) throws InvocationTargetException
    {
    	String sql = "update offer_user " +
    			"set offer_id=?,user_id=?,status=? " +
    			"where offer_id=? and user_id=?";
    	try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setInt(1, offerUser.getOfferId());
			this.pstmt.setInt(2, offerUser.getUserId());
			this.pstmt.setString(3, offerUser.getStatus());
			this.pstmt.setInt(4, offerUser.getOfferId());
			this.pstmt.setInt(5, offerUser.getUserId());
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
    
    public void deleteOfferUser(int offerId, int userId) throws InvocationTargetException
    {
    	String sql = "delete from offer_user " +
				" where offer_id=? and user_id=?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setInt(1, offerId);
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
    
    public void deleteOfferUser(OfferUser offerUser) throws InvocationTargetException
    {
    	String sql = "delete from offer_user " +
				" where offer_id=? and user_id=?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setInt(1, offerUser.getOfferId());
			this.pstmt.setInt(2, offerUser.getUserId());
			this.pstmt.execute();
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
    
    public OfferUser getOfferUser(int offerId, int userId) throws InvocationTargetException
    {
    	String sql = "select offer_id as offerId, USER_ID as userId,status from offer_user " +
				" where offer_id = ? and USER_ID= ?";
    	OfferUser offerUser = null;
    	try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setInt(1, offerId);
			this.pstmt.setInt(2, userId);
			ResultSet rs = this.pstmt.executeQuery();

			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs, "com.offerme.server.database.model.OfferUser");
			if(obj.length >0)
			{
				offerUser = (OfferUser)obj[0];
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
    	
    	return offerUser;
    }
    
    public boolean checkFavorite(int offerId, int userId) throws InvocationTargetException
    {
    	boolean bFavorite = false;
    	String sql = "select offer_id as offerId, USER_ID as userId, status from offer_user " +
				" where offer_id = ? and USER_ID = ? ";
    	try {
    		this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
    		this.pstmt.setInt(1, offerId);
    		this.pstmt.setInt(2, userId);
    		ResultSet rs = this.pstmt.executeQuery();
    		if(rs.next())
    		{
    			bFavorite = true;
    		}
    	}catch (SQLException e) {
			
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err);  
		}
    	finally
		{
			this.free();
		}
    	return bFavorite;
    }
    
    public List<Integer> getOfferListIdByUserId(int userId)throws InvocationTargetException
    {
    	List<Integer> offerIdList = new ArrayList<Integer>();
    	String sql = "select offer_id as offerId from offer_user " +
				" where USER_ID = ? ";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, userId);
			ResultSet rs = this.pstmt.executeQuery();
			List<Object> obj = ResultSetToModel.parseDataEntityBeanList(rs,
					"java.lang.Integer");
			Integer offerId = null;
			for (Object object : obj) {
				offerId = (Integer) object;
				offerIdList.add(offerId);
			}
		} catch (Exception e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
		return offerIdList;
    }
    
    public int getFavoriteOfferCount(int offerId)throws InvocationTargetException
    {
    	int count = 0;
    	String sql = "select count(*) as count from offer_user " +
				" where OFFER_ID = ? ";
    	try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, offerId);
			ResultSet rs = this.pstmt.executeQuery();
			if(rs.next())
			{
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
    	return count;
    }
}
