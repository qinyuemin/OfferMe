package com.offerme.server.database.model;

import java.sql.Timestamp;

public class ApplyInfo {

	private int applyUserId;

	private int offerId;

	private int offerOwnerId;

	private Timestamp applyDT;

	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(int applyUserId) {
		this.applyUserId = applyUserId;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public int getOfferOwnerId() {
		return offerOwnerId;
	}

	public void setOfferOwnerId(int offerOwnerId) {
		this.offerOwnerId = offerOwnerId;
	}

	public Timestamp getApplyDT() {
		return applyDT;
	}

	public void setApplyDT(Timestamp applyDT) {
		this.applyDT = applyDT;
	}

}
