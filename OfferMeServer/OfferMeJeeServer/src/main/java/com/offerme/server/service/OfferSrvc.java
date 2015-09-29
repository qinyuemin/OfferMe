package com.offerme.server.service;

import java.util.List;

import com.offerme.server.model.business.OfferBusinessBean;
import com.offerme.server.model.business.offer.OfferInfo;
import com.offerme.server.model.db.Offer;

public interface OfferSrvc {

	OfferBusinessBean getOfferById(Integer offerId);

	List<OfferBusinessBean> getOffersByUserId(Integer userId);

	List<OfferBusinessBean> getOffersByCityAndKeyword(String city,
			String keyword, String lastOfferID);

	List<OfferBusinessBean> getAppliedOffersByUserId(Integer userId);

	List<OfferBusinessBean> getFavoriteOffersByUserId(Integer userId);

	Integer saveOffer(Offer offer);

	Integer saveOrUpdateOffer(Offer offer);

	Integer publishOffer(OfferInfo info);

	void deleteOffer(Integer offerID);

	Integer getFavoriteCount(Integer offerId);

}
