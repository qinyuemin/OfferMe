package com.offerme.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.offerme.server.dao.OfferUserDao;
import com.offerme.server.dao.core.BaseDaoImpl;
import com.offerme.server.model.db.OfferUser;

@Repository("offerUserDao")
public class OfferUserDaoImpl extends BaseDaoImpl<OfferUser> implements OfferUserDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<OfferUser> findByUserId(Integer userId) {
		
		Query query = this.getSession().createQuery("from OfferUser where userId = ?");
		query.setInteger(0, userId);
		
		return query.list();
	}

	@Override
	public void delete(Integer entityId) {
		OfferUser offerUser = (OfferUser) this.getSession().get(OfferUser.class, entityId);
		if(offerUser != null)
			this.getSession().delete(offerUser);
	}

	@Override
	public OfferUser get(Integer entityId) {
		return (OfferUser) this.getSession().get(OfferUser.class, entityId);
	}

	@Override
	public void deleteOfferUser(Integer offerId, Integer userId) {
		
		Query query = this.getSession().createQuery("from OfferUser where offerId = ? and userId = ? ");
		query.setInteger(0, offerId);
		query.setInteger(1, userId);
		
		OfferUser offerUser =  (OfferUser)query.uniqueResult();
		if(offerUser != null)
			this.getSession().delete(offerUser);
	}

	@Override
	public OfferUser getOfferUser(Integer offerId, Integer userId) {
		Query query = this.getSession().createQuery("from OfferUser where offerId = ? and userId = ? ");
		query.setInteger(0, offerId);
		query.setInteger(1, userId);
		
		return (OfferUser)query.uniqueResult();
	}

	@Override
	public Integer findFavoriteCount(Integer offerId) {
		Query query = this.getSession().createQuery(
				"select count(*) from OfferUser where offerId = ?");
		query.setInteger(0, offerId);
		return ((Long) query.uniqueResult()).intValue();
	}

}
