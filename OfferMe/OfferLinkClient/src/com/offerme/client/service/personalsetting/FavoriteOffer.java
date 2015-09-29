package com.offerme.client.service.personalsetting;

public class FavoriteOffer {
	private String user;
	private String offer;
	private boolean isAdd;

	public FavoriteOffer(String userID, String offerID, boolean isAdd) {
		this.user = userID;
		this.offer = offerID;
		this.isAdd = isAdd;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	public boolean isAdd() {
		return isAdd;
	}
}
