package com.offerme.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.offerme.server.dao.ApplyInfoDao;
import com.offerme.server.dao.core.BaseDaoImpl;
import com.offerme.server.model.db.ApplyInfo;

@Repository("appplyInfoDao")
public class ApplyInfoDaoImpl extends BaseDaoImpl<ApplyInfo> implements
		ApplyInfoDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<ApplyInfo> findByApplyUserId(Integer applyUserId) {

		Query query = this.getSession().createQuery(
				"from ApplyInfo where applyUserId = ?");
		query.setInteger(0, applyUserId);

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplyInfo> findByOwnerUserId(Integer offerOwnerId) {
		Query query = this.getSession().createQuery(
				"from ApplyInfo where offerOwnerId = ? and status = 0");
		query.setInteger(0, offerOwnerId);

		return query.list();
	}

	@Override
	public ApplyInfo getApplyInfo(int userId, int offerId) {
		Query query = this.getSession().createQuery(
				"from ApplyInfo where applyUserId = ? and offerId = ?");
		query.setInteger(0, userId);
		query.setInteger(1, offerId);

		return (ApplyInfo) query.uniqueResult();
	}

	@Override
	public void delete(Integer entityId) {
		ApplyInfo applyInfo = (ApplyInfo) this.getSession().get(
				ApplyInfo.class, entityId);
		if (applyInfo != null)
			this.getSession().delete(applyInfo);
	}

	@Override
	public ApplyInfo get(Integer entityId) {
		return (ApplyInfo) this.getSession().get(ApplyInfo.class, entityId);
	}
}
