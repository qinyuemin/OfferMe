package com.offerme.server.model.business.applyoffer;

public class ApplyOfferRequest {

	private String userIDSend;
	private String userIDReceive;
	private String offerID;
	
	public String getUserIDReceive() {
		return userIDReceive;
	}

	public void setUserIDReceive(String userIDReceive) {
		this.userIDReceive = userIDReceive;
	}

	public String getUserIDSend() {
		return userIDSend;
	}

	public void setUserIDSend(String userIDSend) {
		this.userIDSend = userIDSend;
	}

	public String getOfferID() {
		return offerID;
	}

	public void setOfferID(String offerID) {
		this.offerID = offerID;
	}
	
}
