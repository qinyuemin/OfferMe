package com.offerme.server.model.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "apply_info")
public class ApplyInfo {

	private Integer applyInfoId;
	private Integer applyUserId;
	private Integer offerId;
	private Integer offerOwnerId;
	private Date applyDT;
	private Integer status;

	@Id 
	@GeneratedValue
	@Column(name = "APPLY_INFO_ID")
	public Integer getApplyInfoId() {
		return applyInfoId;
	}

	public void setApplyInfoId(Integer applyInfoId) {
		this.applyInfoId = applyInfoId;
	}

	@Column(name = "APPLY_USER_ID")
	public Integer getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(Integer applyUserId) {
		this.applyUserId = applyUserId;
	}

	@Column(name = "OFFER_ID")
	public Integer getOfferId() {
		return offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	@Column(name = "OFFER_OWNER_ID")
	public Integer getOfferOwnerId() {
		return offerOwnerId;
	}

	public void setOfferOwnerId(Integer offerOwnerId) {
		this.offerOwnerId = offerOwnerId;
	}

	@Column(name = "APPLY_DT")
	public Date getApplyDT() {
		return applyDT;
	}

	public void setApplyDT(Date applyDT) {
		this.applyDT = applyDT;
	}

	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
