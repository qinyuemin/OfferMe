package com.offerme.server.database.model;

public class OfferUser {

	/**
	 * OFFER_USER_ID: INT
	 */
	private int offerUserId;
	/**
	 * OFFER_ID: INT
	 */
	private int offerId;
	private Offer offer;
	/**
	 * USER_ID: INT
	 */
	private int userId;
	private User user;
	/**
	 * STATUS: varchar
	 */
	private String status;
	public int getOfferUserId() {
		return offerUserId;
	}
	public void setOfferUserId(int offerUserId) {
		this.offerUserId = offerUserId;
	}
	public int getOfferId() {
		return offerId;
	}
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
