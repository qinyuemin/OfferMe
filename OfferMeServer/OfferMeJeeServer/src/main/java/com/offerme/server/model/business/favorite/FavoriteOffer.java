package com.offerme.server.model.business.favorite;

public class FavoriteOffer {

	private String user;
	private String offer;
	private boolean isAdd;

	public String getUser() {
		return user;
	}

	public String getOffer() {
		return offer;
	}

	public boolean isAdd() {
		return isAdd;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}
	
	
}
