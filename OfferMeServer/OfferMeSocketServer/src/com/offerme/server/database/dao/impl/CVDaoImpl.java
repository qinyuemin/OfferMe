package com.offerme.server.database.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.offerme.server.database.dao.CVDao;
import com.offerme.server.database.dbpool.JDBCDataConnection;
import com.offerme.server.database.model.CV;
import com.offerme.server.service.threadpool.Request;
import com.offerme.server.util.Log;
import com.offerme.server.util.ResultSetToModel;
import com.offerme.server.util.SqlCommand;

public class CVDaoImpl extends BaseDaoImpl implements CVDao {

	public int insertCV(CV cv) throws InvocationTargetException {

		String sql = "Insert into CV (USER_ID,JOB_YEAR,CURRENT_COMPANY,CURRENT_POST,DIPLOMA,SCHOOL,JOB_COMPANY_01,JOB_POST_01,JOB_YEAR_01,JOB_COMPANY_02,JOB_POST_02,JOB_YEAR_02,JOB_COMPANY_03,JOB_POST_03,JOB_YEAR_03)Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int idReturn = -1;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql, Statement.RETURN_GENERATED_KEYS);
			this.pstmt.setInt(1, cv.getUserId());
			this.pstmt.setInt(2, cv.getJobYear());
			this.pstmt.setString(3, cv.getCurrentCompany());
			this.pstmt.setString(4, cv.getCurrentPost());
			this.pstmt.setString(5, cv.getDiploma());
			this.pstmt.setString(6, cv.getSchool());
			this.pstmt.setString(7, cv.getJobCompany01());
			this.pstmt.setString(8, cv.getJobPost01());
			this.pstmt.setString(9, cv.getJobYear01());
			this.pstmt.setString(10, cv.getJobCompany02());
			this.pstmt.setString(11, cv.getJobPost02());
			this.pstmt.setString(12, cv.getJobYear02());
			this.pstmt.setString(13, cv.getJobCompany03());
			this.pstmt.setString(14, cv.getJobPost03());
			this.pstmt.setString(15, cv.getJobYear03());

			ResultSet rs = null;
			this.pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				idReturn = rs.getInt(1);
			}

			return idReturn;
		} catch (SQLException e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
	}

	public void updateCV(CV cv) throws InvocationTargetException {

		String sql = "update CV "
				+ "set JOB_YEAR=?,CURRENT_COMPANY=?,CURRENT_POST=?,DIPLOMA=?,SCHOOL=?,JOB_COMPANY_01=?,JOB_POST_01=?,JOB_YEAR_01=?,JOB_COMPANY_02=?,JOB_POST_02=?,JOB_YEAR_02=?,JOB_COMPANY_03=?,JOB_POST_03=?,JOB_YEAR_03=? "
				+ " where USER_ID=?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, cv.getJobYear());
			this.pstmt.setString(2, cv.getCurrentCompany());
			this.pstmt.setString(3, cv.getCurrentPost());
			this.pstmt.setString(4, cv.getDiploma());
			this.pstmt.setString(5, cv.getSchool());
			this.pstmt.setString(6, cv.getJobCompany01());
			this.pstmt.setString(7, cv.getJobPost01());
			this.pstmt.setString(8, cv.getJobYear01());
			this.pstmt.setString(9, cv.getJobCompany02());
			this.pstmt.setString(10, cv.getJobPost02());
			this.pstmt.setString(11, cv.getJobYear02());
			this.pstmt.setString(12, cv.getJobCompany03());
			this.pstmt.setString(13, cv.getJobPost03());
			this.pstmt.setString(14, cv.getJobYear03());
			this.pstmt.setInt(15, cv.getUserId());
			this.pstmt.executeUpdate();
		} catch (SQLException e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}

	}

	public void deleteCV(int userID) throws InvocationTargetException {

		String sql = "delete from CV " + " where USER_ID = ?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, userID);
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
	}

	public CV getCV(int userID) throws InvocationTargetException {

		Request request = Request.getRequest();
		String where = "(USER_ID = ?)";
		if (request != null && request.getWhere() != null && !"".equals(request.getWhere())) {
			where = where + " and (" + request.getWhere() + ")";
		}
		SqlCommand sqlCommand = new SqlCommand(
				"USER_ID as userId,JOB_YEAR as jobYear,CURRENT_COMPANY as currentCompany,CURRENT_POST as currentPost,DIPLOMA,SCHOOL,JOB_COMPANY_01 as jobCompany01,JOB_POST_01 as jobPost01,JOB_YEAR_01 as jobYear01,JOB_COMPANY_02 as jobCompany02,JOB_POST_02 as jobPost02,JOB_YEAR_02 as jobYear02,JOB_COMPANY_03 as jobCompany03,JOB_POST_03 as jobPost03,JOB_YEAR_03 as jobYear03 ",
				"CV", where, request == null?-1:request.getStart(),request == null?-1: request.getSize(), request == null?"":request
						.getOrder());

		CV cv = null;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sqlCommand.getSql());
			this.pstmt.setInt(1, userID);
			ResultSet rs = this.pstmt.executeQuery();

			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs,
					"com.offerme.server.database.model.CV");
			if (obj.length > 0) {
				cv = (CV) obj[0];
			}

		} catch (Exception e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}

		return cv;
	}

}
