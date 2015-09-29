package com.offerme.server.model.business;

import com.offerme.server.model.db.Offer;
import com.offerme.server.model.db.User;

public class OfferBusinessBean extends Offer {

	private User offerOwner;
	
	private Integer favoriteCount;
	
	public OfferBusinessBean(Offer offer) {
		super();
		if(offer != null){
			setOfferId(offer.getOfferId());
			setUserId(offer.getUserId());
			setTitle(offer.getTitle());
			setContent(offer.getContent());
			setCompany(offer.getCompany());
			setCity(offer.getCity());
			setCountry(offer.getCountry());
			setAddress(offer.getAddress());
			setCreDt(offer.getCreDt());
			setStatus(offer.getStatus());
			setTrade(offer.getTrade());
			setOfferMail(offer.getOfferMail());
			setSalary(offer.getSalary());
			setWorkyear(offer.getWorkyear());
			setEducation(offer.getEducation());
		}
	}

	public User getOfferOwner() {
		return offerOwner;
	}

	public void setOfferOwner(User offerOwner) {
		this.offerOwner = offerOwner;
	}

	public Integer getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
}
