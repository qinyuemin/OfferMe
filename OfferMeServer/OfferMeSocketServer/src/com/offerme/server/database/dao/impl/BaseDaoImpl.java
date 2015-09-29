package com.offerme.server.database.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.offerme.server.database.dao.BaseDao;
import com.offerme.server.util.Log;

public class BaseDaoImpl implements BaseDao{
	
	static Log myLog = new Log("com.offerme.server.database.dao");
	
	protected PreparedStatement pstmt = null;
	
	 /**
     * 释放连接
     */
	public void free()
    {
		try {

			if (this.pstmt != null) {
				this.pstmt.close();
			}
		} catch (SQLException e) {

			myLog.error(Log.getStackInfo(e));
		}
    }

}
