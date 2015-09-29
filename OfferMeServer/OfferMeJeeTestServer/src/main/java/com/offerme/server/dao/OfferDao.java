package com.offerme.server.dao;

import java.util.List;

import com.offerme.server.dao.core.BaseDao;
import com.offerme.server.model.db.Offer;

public interface OfferDao extends BaseDao<Offer> {

	Offer findById(Integer offerId);

	List<Offer> findByUserId(Integer userId);

	List<Offer> findByCityAndKeyword(String city, String keyword,
			String lastOfferID);
}
