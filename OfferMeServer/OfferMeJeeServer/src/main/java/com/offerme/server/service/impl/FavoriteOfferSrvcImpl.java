package com.offerme.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offerme.server.model.business.favorite.FavoriteOffer;
import com.offerme.server.model.db.OfferUser;
import com.offerme.server.service.FavoriteOfferSrvc;
import com.offerme.server.service.OfferUserSrvc;
import com.offerme.server.util.Tool;

@Service("favoriteOfferSrvc")
@Transactional
public class FavoriteOfferSrvcImpl implements FavoriteOfferSrvc {

	@Autowired
	private OfferUserSrvc offerUserSrvc;

	@Override
	public String setFavoriteOffer(FavoriteOffer request) {

		OfferUser favorite = new OfferUser();
		favorite.setOfferId(Tool.parseInt(request.getOffer()));
		favorite.setUserId(Tool.parseInt(request.getUser()));
		try {
			if (request.isAdd()) {
				if (offerUserSrvc.getOfferUser(favorite.getOfferId(),
						favorite.getUserId()) == null) {
					offerUserSrvc.insertOfferUser(favorite);
				}
			} else {
				offerUserSrvc.deleteOfferUser(favorite.getOfferId(),
						favorite.getUserId());
			}
		} catch (Exception ex) {
			return null;
		}
		return "OK";
	}

}
