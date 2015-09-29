package com.offerme.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offerme.server.model.db.Offer;
import com.offerme.server.service.DeleteOfferSrvc;
import com.offerme.server.service.OfferSrvc;
import com.offerme.server.service.OfferUserSrvc;
import com.offerme.server.util.Tool;

@Service("deleteOfferSrvc")
@Transactional
public class DeleteOfferSrvcImpl implements DeleteOfferSrvc {

	@Autowired
	private OfferSrvc offerSrvc;

	@Autowired
	private OfferUserSrvc offerUserSrvc;

	@Override
	public String deleteOffer(String offerID, String userID) {
		String strReturn = "";
		try {
			Offer offer = offerSrvc.getOfferById(Tool.parseInt(offerID));
			if (offer != null) {
				if (offer.getUserId() == Tool.parseInt(userID)) {
					offerSrvc.deleteOffer(offer.getOfferId());
				} else {
					offerUserSrvc.deleteOfferUser(offer.getOfferId(),
							Tool.parseInt(userID));
				}
			}
			strReturn = "OK";
		} catch (Exception e) {
			e.printStackTrace();
			strReturn = "Fail";
		}
		return strReturn;
	}

}
