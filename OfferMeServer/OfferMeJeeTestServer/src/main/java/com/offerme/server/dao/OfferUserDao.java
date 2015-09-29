package com.offerme.server.dao;

import java.util.List;

import com.offerme.server.dao.core.BaseDao;
import com.offerme.server.model.db.OfferUser;


public interface OfferUserDao extends BaseDao<OfferUser> {

	List<OfferUser> findByUserId(Integer userId);
	
	void deleteOfferUser(Integer offerId, Integer userId);
	
	OfferUser getOfferUser(Integer offerId,Integer userId);
	
	Integer findFavoriteCount(Integer offerId);
}
