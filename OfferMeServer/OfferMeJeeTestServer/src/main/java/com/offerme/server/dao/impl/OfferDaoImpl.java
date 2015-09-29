package com.offerme.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.offerme.server.dao.OfferDao;
import com.offerme.server.dao.core.BaseDaoImpl;
import com.offerme.server.model.db.Offer;

@Repository("offerDao")
public class OfferDaoImpl extends BaseDaoImpl<Offer> implements OfferDao {

	private int OFFERNUMBER=15;
	
	@Override
	public Offer findById(Integer offerId) {

		Query query = this.getSession().createQuery(
				"from Offer where offerId = ?");
		query.setInteger(0, offerId);

		return (Offer) query.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Offer> findByUserId(Integer userId) {

		Query query = this.getSession().createQuery(
				"from Offer where userId = ?");
		query.setInteger(0, userId);

		return (List<Offer>) query.list();
	}

	@Override
	public void delete(Integer entityId) {
		Offer offer = (Offer) this.getSession().get(Offer.class, entityId);
		if (offer != null)
			this.getSession().delete(offer);
	}

	@Override
	public Offer get(Integer entityId) {
		return (Offer) this.getSession().get(Offer.class, entityId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Offer> findByCityAndKeyword(String city, String keyword,
			String lastOfferID) {

		boolean isWhere = false;
		StringBuffer sql = new StringBuffer("from Offer");
		if (!StringUtils.isEmpty(city)) {
			isWhere = true;
			sql.append(" where city like '%");
			sql.append(city);
			sql.append("%'");
		}

		if (!StringUtils.isEmpty(keyword)) {
			if (!isWhere) {
				sql.append(" where");
			} else {
				sql.append(" and");
			}
			sql.append(" (company like '%");
			sql.append(keyword);
			sql.append("%' or title like '%");
			sql.append(keyword);
			sql.append("%')");
		}

		if (lastOfferID != null) {
			if (!isWhere) {
				sql.append(" where");
			} else {
				sql.append(" and");
			}
			sql.append(" offerId < ");
			sql.append(lastOfferID);
		}
		sql.append(" order by creDt desc");

		Query query = this.getSession().createQuery(sql.toString());
		query.setFirstResult(0);
		query.setMaxResults(OFFERNUMBER);
		return query.list();
	}

}
