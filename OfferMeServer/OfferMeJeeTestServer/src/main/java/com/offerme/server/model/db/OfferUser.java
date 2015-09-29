package com.offerme.server.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "offer_user")
public class OfferUser {

	private Integer offerUserId;
	private Integer userId;
	private Integer offerId;
	private String status;

	@Id 
	@GeneratedValue 
	@Column(name = "OFFER_USER_ID")
	public Integer getOfferUserId() {
		return offerUserId;
	}

	public void setOfferUserId(Integer offerUserId) {
		this.offerUserId = offerUserId;
	}

	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "OFFER_ID")
	public Integer getOfferId() {
		return offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
