package com.offerme.server.model.business.offer;

public class OfferDelete {
	private String OfferID;
	private String UserID;

	public OfferDelete(String offer, String user) {
		OfferID = offer;
		UserID = user;
	}

	public String getOfferID() {
		return OfferID;
	}

	public void setOfferID(String offerID) {
		OfferID = offerID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}
}
