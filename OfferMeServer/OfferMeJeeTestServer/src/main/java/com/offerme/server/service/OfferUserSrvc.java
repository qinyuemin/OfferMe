package com.offerme.server.service;

import com.offerme.server.model.db.OfferUser;

public interface OfferUserSrvc {
	
	OfferUser getOfferUser(Integer offerUserId);
	
	OfferUser getOfferUser(Integer offerId,Integer userId);
	
	void updateOfferUser(OfferUser offerUser);
	
	Integer insertOfferUser(OfferUser offerUser);
	
	void deleteOfferUser(Integer offerUserId);
	
	void deleteOfferUser(Integer offerId, Integer userId);

}
