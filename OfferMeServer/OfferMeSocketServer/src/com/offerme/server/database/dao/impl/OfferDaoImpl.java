package com.offerme.server.database.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import com.offerme.server.database.dao.ClassementDetailDao;
import com.offerme.server.database.dao.OfferDao;
import com.offerme.server.database.dao.OfferUserDao;
import com.offerme.server.database.dao.UserDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.database.dbpool.JDBCDataConnection;
import com.offerme.server.database.model.Offer;
import com.offerme.server.database.model.User;
import com.offerme.server.service.threadpool.Request;
import com.offerme.server.util.Log;
import com.offerme.server.util.ResultSetToModel;
import com.offerme.server.util.SqlCommand;

public class OfferDaoImpl extends BaseDaoImpl implements OfferDao {

	private UserDao userDao;

	private OfferUserDao offerUserDao;

	public OfferDaoImpl() {
		this.userDao = DaoImplManage.getUserDaoInstance(this);
		this.offerUserDao = DaoImplManage.getOfferUserDaoInstance();
	}

	public OfferDaoImpl(UserDao userDao) {
		this.userDao = userDao;
		this.offerUserDao = DaoImplManage.getOfferUserDaoInstance();
	}

	public int insertOffer(Offer offer) throws InvocationTargetException {
		String sql = "Insert into offer (USER_ID,TITLE,CONTENT,COMPANY,CITY,COUNTRY,ADDRESS,CRE_DT,STATUS,TRADE,OFFER_MAIL,SALARY,WORKYEAR,EDUCATION)Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int idReturn = -1;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql, Statement.RETURN_GENERATED_KEYS);
			this.pstmt.setInt(1, offer.getUserId());
			this.pstmt.setString(2, offer.getTitle());
			this.pstmt.setString(3, offer.getContent());
			this.pstmt.setString(4, offer.getCompany());
			this.pstmt.setString(5, offer.getCity());
			this.pstmt.setString(6, offer.getCountry());
			this.pstmt.setString(7, offer.getAddress());
			this.pstmt.setTimestamp(8, offer.getDate());
			this.pstmt.setString(9, "有效");
			this.pstmt.setString(10, offer.getTrade());
			this.pstmt.setString(11, offer.getOfferMail());
			this.pstmt.setString(12, offer.getSalary());
			this.pstmt.setString(13, offer.getWorkyear());
			this.pstmt.setString(14, offer.getEducation());
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

	public void updateOffer(Offer offer) throws InvocationTargetException {
		String sql = "update offer"
				+ " set USER_ID=?,TITLE=?,CONTENT=?,COMPANY=?,CITY=?,COUNTRY=?,ADDRESS=?,CRE_DT=?,STATUS=?,TRADE=?,OFFER_MAIL=?,SALARY=?,WORKYEAR=?,EDUCATION=? "
				+ " where OFFER_ID=?";

		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, offer.getUserId());
			this.pstmt.setString(2, offer.getTitle());
			this.pstmt.setString(3, offer.getContent());
			this.pstmt.setString(4, offer.getCompany());
			this.pstmt.setString(5, offer.getCity());
			this.pstmt.setString(6, offer.getCountry());
			this.pstmt.setString(7, offer.getAddress());
			this.pstmt.setTimestamp(8, offer.getDate());
			this.pstmt.setString(9, offer.getStatus());
			this.pstmt.setString(10, offer.getTrade());
			this.pstmt.setString(11, offer.getOfferMail());
			this.pstmt.setString(12, offer.getSalary());
			this.pstmt.setString(13, offer.getWorkyear());
			this.pstmt.setString(14, offer.getEducation());
			this.pstmt.setInt(15, offer.getOfferId());
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		} finally {
			this.free();
		}
	}

	public void deleteOffer(int offerId) throws InvocationTargetException {
		String sql = "delete from offer " + " where offer_id = ?";
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, offerId);
			this.pstmt.executeUpdate();
		} catch (SQLException e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		}

	}

	public Offer getOffer(int offerId) throws InvocationTargetException {
		Request request = Request.getRequest();
		String where = "(OFFER_ID = ?)";
		String order = " OFFER_ID desc ";
		if (request != null && request.getWhere() != null
				&& !"".equals(request.getWhere())) {
			where = where + " and (" + request.getWhere() + ")";
		}
		if (request != null && request.getOrder() != null
				&& !"".equals(request.getOrder())) {
			order = order + " , " + request.getOrder();
		}
		SqlCommand sqlCommand = new SqlCommand(
				"OFFER_ID as offerId, USER_ID as userId,TITLE,CONTENT,COMPANY,CITY,COUNTRY,ADDRESS,CRE_DT as date,STATUS,TRADE,OFFER_MAIL as offerMail,SALARY,WORKYEAR,EDUCATION",
				"offer", where, request == null ? -1 : request.getStart(),
				request == null ? -1 : request.getSize(), order);
		// String sql =
		// "select OFFER_ID as offerId, USER_ID as userId,TITLE,CONTENT,COMPANY,CITY,COUNTRY,ADDRESS,CRE_DT,STATUS,TRADE,OFFER_MAIL,SALARY from offer "
		// +
		// " where OFFER_ID = ?";
		Offer offer = null;
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sqlCommand.getSql());
			this.pstmt.setInt(1, offerId);
			ResultSet rs = this.pstmt.executeQuery();
			if (request != null) {
				request.clearCondition();
			}

			Object[] obj = ResultSetToModel.parseDataEntityBeans(rs,
					"com.offerme.server.database.model.Offer");
			if (obj.length > 0) {
				offer = (Offer) obj[0];
				User user = userDao.getUser(offer.getUserId());
				offer.setOfferUser(user);
			}

		} catch (Exception e) {

			String err = Log.getStackInfo(e);
			myLog.error(err);
			throw new InvocationTargetException(e, err);
		}
		return offer;
	}

	public List<Offer> getOfferByDemand(String city, String keyword)
			throws InvocationTargetException {
		Request request = Request.getRequest();
		String order = " CRE_DT desc ";
		StringBuilder sbFilter = new StringBuilder();
		List<Offer> offerList = new ArrayList<Offer>();
		if (city != null && !"".equals(city)) {
			city = "%" + city + "%";
			sbFilter.append(" CITY like '");
			sbFilter.append(city);
			sbFilter.append("'");
		}
		if (keyword != null && !"".equals(keyword)) {
			keyword = "%" + keyword + "%";
			if (sbFilter.length() > 0) {
				sbFilter.append(" and ");
			}
			sbFilter.append(" (COMPANY like '");
			sbFilter.append(keyword);
			sbFilter.append("'");
			sbFilter.append(" or TITLE like '");
			sbFilter.append(keyword);
			sbFilter.append("') ");
		}
		if (request != null && request.getWhere() != null
				&& !"".equals(request.getWhere())) {
			if (sbFilter.length() > 0) {
				sbFilter.insert(0, "(");
				sbFilter.append(") and (");
				sbFilter.append(request.getWhere());
				sbFilter.append(")");
			} else {
				sbFilter.append(request.getWhere());
			}
		}
		if (request != null && request.getOrder() != null
				&& !"".equals(request.getOrder())) {
			order = order + " , " + request.getOrder();
		}
		SqlCommand sqlCommand = new SqlCommand(
				"OFFER_ID as offerId, USER_ID as userId,TITLE,CONTENT,COMPANY,CITY,COUNTRY,ADDRESS,CRE_DT as date,STATUS,TRADE,OFFER_MAIL as offerMail,SALARY,WORKYEAR,EDUCATION",
				"offer", sbFilter.toString(), request == null ? -1 : request
						.getStart(), request == null ? -1 : request.getSize(),
				order);
		// sbFilter.insert(0, sql);
		try {
			System.out.println("Show me the sql command: "
					+ sqlCommand.getSql());
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sqlCommand.getSql());
			// this.pstmt.setString(1, city);
			// this.pstmt.setString(2, comp);
			ResultSet rs = this.pstmt.executeQuery();
			request.clearCondition();

			List<Object> obj = ResultSetToModel.parseDataEntityBeanList(rs,
					"com.offerme.server.database.model.Offer");
			Offer offer = null;
			for (Object object : obj) {
				offer = (Offer) object;
				User user = userDao.getUser(offer.getUserId());
				if (user != null) {
					offer.setOfferUser(user);
					offer.setFavoriteCount(offerUserDao
							.getFavoriteOfferCount(offer.getOfferId()));
					offerList.add(offer);
				}
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

	public List<Offer> getOfferByRandom(int count)
			throws InvocationTargetException {
		String sql = " call getRandomOffer(?) ";
		List<Offer> offerList = new ArrayList<Offer>();
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sql);
			this.pstmt.setInt(1, count);
			ResultSet rs = this.pstmt.executeQuery();

			List<Object> obj = ResultSetToModel.parseDataEntityBeanList(rs,
					"com.offerme.server.database.model.Offer");
			Offer offer = null;
			for (Object object : obj) {
				offer = (Offer) object;
				User user = userDao.getUser(offer.getUserId());
				if (user != null) {
					offer.setOfferUser(user);
					offer.setFavoriteCount(offerUserDao
							.getFavoriteOfferCount(offer.getOfferId()));
					offerList.add(offer);
				}
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

	public List<Offer> getOfferListByUserId(int userId)
			throws InvocationTargetException {
		Request request = Request.getRequest();
		String where = "(USER_ID = ?)";
		String order = " CRE_DT desc ";
		if (request != null && request.getWhere() != null
				&& !"".equals(request.getWhere())) {
			where = where + " and (" + request.getWhere() + ")";
		}
		if (request != null && request.getOrder() != null
				&& !"".equals(request.getOrder())) {
			order = order + " , " + request.getOrder();
		}
		SqlCommand sqlCommand = new SqlCommand(
				"OFFER_ID as offerId, USER_ID as userId,TITLE,CONTENT,COMPANY,CITY,COUNTRY,ADDRESS,CRE_DT as date,STATUS,TRADE,OFFER_MAIL as offerMail,SALARY,WORKYEAR,EDUCATION",
				"offer", where, request == null ? -1 : request.getStart(),
				request == null ? -1 : request.getSize(), order);
		List<Offer> offerList = new ArrayList<Offer>();
		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sqlCommand.getSql());
			this.pstmt.setInt(1, userId);
			ResultSet rs = this.pstmt.executeQuery();
			request.clearCondition();

			List<Object> obj = ResultSetToModel.parseDataEntityBeanList(rs,
					"com.offerme.server.database.model.Offer");
			Offer offer = null;
			for (Object object : obj) {
				offer = (Offer) object;
				User user = userDao.getUser(offer.getUserId());
				if (user != null) {
					offer.setOfferUser(user);
					offerList.add(offer);
				}
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

	public List<Offer> getPublishOfferListByUserId(int userId)
			throws InvocationTargetException {
		Request request = Request.getRequest();
		String where = "(USER_ID = ?)";
		String order = " CRE_DT desc ";
		if (request != null && request.getWhere() != null
				&& !"".equals(request.getWhere())) {
			where = where + " and (" + request.getWhere() + ")";
		}
		if (request != null && request.getOrder() != null
				&& !"".equals(request.getOrder())) {
			order = order + " , " + request.getOrder();
		}
		SqlCommand sqlCommand = new SqlCommand(
				"OFFER_ID as offerId, USER_ID as userId,TITLE,CONTENT,COMPANY,CITY,COUNTRY,ADDRESS,CRE_DT as date,STATUS,TRADE,OFFER_MAIL as offerMail,SALARY,WORKYEAR,EDUCATION",
				"offer", where, request == null ? -1 : request.getStart(),
				request == null ? -1 : request.getSize(), order);
		List<Offer> offerList = new ArrayList<Offer>();

		try {
			this.pstmt = JDBCDataConnection.getConnection().prepareStatement(
					sqlCommand.getSql());
			this.pstmt.setInt(1, userId);
			ResultSet rs = this.pstmt.executeQuery();

			List<Object> obj = ResultSetToModel.parseDataEntityBeanList(rs,
					"com.offerme.server.database.model.Offer");
			Offer offer = null;
			User user = userDao.getUser(userId);
			if (user == null) {
				throw new Exception("用户不存在");
			}
			for (Object object : obj) {
				offer = (Offer) object;
				offer.setOfferUser(user);
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
}
