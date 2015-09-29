package com.offerme.server.service.bean;

public class ApplyOfferMessage {
	private String userIDSend;
	private String userIDReceive;
	private String offerID;
	public ApplyOfferMessage(String offer, String senderID, String receiverID) {
		offerID = offer;
		userIDSend = senderID;
		userIDReceive = receiverID;
	}

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
