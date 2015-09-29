package com.offerme.server.database.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.offerme.server.database.dao.ClassementDao;
import com.offerme.server.database.dbpool.JDBCDataConnection;
import com.offerme.server.database.model.Classement;
import com.offerme.server.service.threadpool.Request;
import com.offerme.server.util.Log;
import com.offerme.server.util.ResultSetToModel;
import com.offerme.server.util.SqlCommand;

public class ClassementDaoImpl extends BaseDaoImpl implements ClassementDao {
	
	public ClassementDaoImpl() {
		
	}
	
	public int insertClassement(Classement classement) throws InvocationTargetException
	{
		String sql = "Insert into classement (NAME,LIB)Values(?,?)";
		int idReturn= -1;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			this.pstmt.setString(1, classement.getName());
			this.pstmt.setString(2, classement.getLib());
			
			ResultSet rs=null;
			this.pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
            	idReturn = rs.getInt(1);
            }
            classement.setClassmentId(idReturn);
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
    
    public void updateClassement(Classement classement) throws InvocationTargetException
    {
    	String sql = "update classement " +
    			" set NAME=?,LIB=? " +
    			" where  CLASSMENT_ID = ?";
		int idReturn= -1;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setString(1, classement.getName());
			this.pstmt.setString(2, classement.getLib());
			this.pstmt.setInt(3, classement.getClassmentId());
			
			ResultSet rs=null;
			this.pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
            	idReturn = rs.getInt(1);
            }
            classement.setClassmentId(idReturn);
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
    
    public void deleteClassement(int classementId) throws InvocationTargetException
    {
    	String sql = "delete from classement " +
				" where CLASSMENT_ID = ?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sql);
			this.pstmt.setInt(1, classementId);
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e,err); 
		}
    }
    
    public Classement getClassement(int classementId) throws InvocationTargetException
    {
    	Request request = Request.getRequest();
    	String where = "(CLASSMENT_ID = ?)";
    	if(request.getWhere() != null && !"".equals(request.getWhere()))
    	{
    		where = where + " and (" + request.getWhere() + ")";
    	}
		SqlCommand sqlCommand = new SqlCommand(
				"CLASSMENT_ID as classmentId, NAME, LIB",
				"classement", where, request.getStart(), request.getSize(), request.getOrder());
//    	String sql = "select CLASSMENT_ID as classmentId, NAME, LIB from classement " +
//				" where CLASSMENT_ID = ?";
    	Classement classement = null;
    	try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(sqlCommand.getSql());
			this.pstmt.setInt(1, classementId);
			ResultSet rs = this.pstmt.executeQuery();
	
			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs, "com.offerme.server.database.model.Classement");
			if(obj.length  > 0)
			{
				classement = (Classement)obj[0];
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
    	return classement;
    }

}
