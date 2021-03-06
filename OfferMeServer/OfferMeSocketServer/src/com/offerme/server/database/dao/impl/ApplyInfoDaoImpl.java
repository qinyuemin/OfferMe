package com.offerme.server.database.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.offerme.server.database.dao.ApplyInfoDao;
import com.offerme.server.database.dao.CVDao;
import com.offerme.server.database.dao.OfferDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.database.dbpool.JDBCDataConnection;
import com.offerme.server.database.model.ApplyInfo;
import com.offerme.server.database.model.CV;
import com.offerme.server.database.model.Offer;
import com.offerme.server.service.threadpool.Request;
import com.offerme.server.util.Log;
import com.offerme.server.util.ResultSetToModel;
import com.offerme.server.util.SqlCommand;

public class ApplyInfoDaoImpl extends BaseDaoImpl implements ApplyInfoDao {

	public void insertApplyInfo(ApplyInfo applyInfo)
			throws InvocationTargetException {
		String sql = "Insert into APPLY_INFO (APPLY_USER_ID,OFFER_ID,OFFER_OWNER_ID,APPLY_DT,STATUS) Values(?,?,?,?,?)";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql, Statement.RETURN_GENERATED_KEYS);
			this.pstmt.setInt(1, applyInfo.getApplyUserId());
			this.pstmt.setInt(2, applyInfo.getOfferId());
			this.pstmt.setInt(3, applyInfo.getOfferOwnerId());
			this.pstmt.setTimestamp(4, applyInfo.getApplyDT());
			this.pstmt.setInt(5, applyInfo.getStatus());

			this.pstmt.executeUpdate();
		} catch (Exception e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
	}

	public void updateApplyInfo(ApplyInfo applyInfo)
			throws InvocationTargetException {
		String sql = "update APPLY_INFO "
				+ "set APPLY_USER_ID=?,OFFER_ID=?,OFFER_OWNER_ID=?,APPLY_DT=?,STATUS=?"
				+ " where APPLY_USER_ID=? AND OFFER_ID=?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, applyInfo.getApplyUserId());
			this.pstmt.setInt(2, applyInfo.getOfferId());
			this.pstmt.setInt(3, applyInfo.getOfferOwnerId());
			this.pstmt.setTimestamp(4, applyInfo.getApplyDT());
			this.pstmt.setInt(5, applyInfo.getStatus());
			this.pstmt.setInt(6, applyInfo.getApplyUserId());
			this.pstmt.setInt(7, applyInfo.getOfferId());
			this.pstmt.executeUpdate();
		} catch (SQLException e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}

	}

	public void deleteApplyInfo(int userId, int offerId)
			throws InvocationTargetException {
		String sql = "delete from APPLY_INFO "
				+ " where APPLY_USER_ID=?,OFFER_ID=?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, userId);
			this.pstmt.setInt(2, offerId);
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}

	}

	public ApplyInfo getApplyInfo(int userId, int offerId)
			throws InvocationTargetException {
		Request request = Request.getRequest();
		String where = "(APPLY_USER_ID=? and OFFER_ID=?)";
		if (request != null && request.getWhere() != null
				&& !"".equals(request.getWhere())) {
			where = where + " and (" + request.getWhere() + ")";
		}
		SqlCommand sqlCommand = new SqlCommand(
				"APPLY_USER_ID as applyUserId,OFFER_ID as offerId,OFFER_OWNER_ID as offerOwnerId,APPLY_DT as applyDT,STATUS as status",
				"APPLY_INFO", where, request == null ? -1 : request.getStart(),
				request == null ? -1 : request.getSize(), request == null ? ""
						: request.getOrder());
		ApplyInfo applyInfo = null;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sqlCommand.getSql());
			this.pstmt.setInt(1, userId);
			this.pstmt.setInt(2, offerId);
			ResultSet rs = this.pstmt.executeQuery();

			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs,
					"com.offerme.server.database.model.ApplyInfo");
			if (obj.length > 0) {
				applyInfo = (ApplyInfo) obj[0];
			}

		} catch (Exception e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
		return applyInfo;
	}

	public List<Offer> getPersonApplyOffer(int userId)
			throws InvocationTargetException {
		List<Offer> offerList = new ArrayList<Offer>();
		String sql = "select APPLY_USER_ID as applyUserId,OFFER_ID as offerId,OFFER_OWNER_ID as offerOwnerId,APPLY_DT as applyDT,STATUS as status from APPLY_INFO "
				+ " where APPLY_USER_ID = ? ";
		OfferDao offerDao = DaoImplManage.getOfferDaoInstance();
		ApplyInfo applyInfo = null;
		Offer offer = null;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, userId);
			ResultSet rs = this.pstmt.executeQuery();

			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs,
					"com.offerme.server.database.model.ApplyInfo");
			for (Object object : obj) {
				applyInfo = (ApplyInfo) object;
				offer = offerDao.getOffer(applyInfo.getOfferId());
				offerList.add(offer);
			}

		} catch (Exception e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
		return offerList;
	}

	@Override
	public List<Hashtable<CV, Integer>> getCommingCV(int userId)
			throws InvocationTargetException {
		Request request = Request.getRequest();
		List<Hashtable<CV, Integer>> cvList = new ArrayList<Hashtable<CV, Integer>>();
		String where = "(OFFER_OWNER_ID=? and STATUS=0)";
		if (request != null && request.getWhere() != null
				&& !"".equals(request.getWhere())) {
			where = where + " and (" + request.getWhere() + ")";
		}
		SqlCommand sqlCommand = new SqlCommand(
				"APPLY_USER_ID as applyUserId,OFFER_ID as offerId,OFFER_OWNER_ID as offerOwnerId,APPLY_DT as applyDT,STATUS as status",
				"APPLY_INFO", where, request == null ? -1 : request.getStart(),
				request == null ? -1 : request.getSize(), request == null ? ""
						: request.getOrder());
		CVDao cvDao = DaoImplManage.getCVDaoInstance();
		ApplyInfo applyInfo = null;
		CV cv = null;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sqlCommand.getSql());
			this.pstmt.setInt(1, userId);
			ResultSet rs = this.pstmt.executeQuery();

			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs,
					"com.offerme.server.database.model.ApplyInfo");
			for (Object object : obj) {
				Hashtable<CV, Integer> cvInfo = new Hashtable<CV, Integer>();
				applyInfo = (ApplyInfo) object;
				cv = cvDao.getCV(applyInfo.getApplyUserId());
				if (cv != null) {
					cv.setDate(applyInfo.getApplyDT());
					cvInfo.put(cv, applyInfo.getOfferId());
					cvList.add(cvInfo);
					applyInfo.setStatus(1);
					updateApplyInfo(applyInfo);
				}
			}
		} catch (Exception e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
		return cvList;
	}

}
