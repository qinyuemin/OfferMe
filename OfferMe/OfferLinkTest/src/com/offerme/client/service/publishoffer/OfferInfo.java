package com.offerme.client.service.publishoffer;

import java.io.Serializable;

public class OfferInfo implements Serializable {

	private static final long serialVersionUID = -6095847226572647391L;
	private String userName = null;
	private String entreprise = null;
	private String city = null;
	private String description = null;
	private String post = null;
	private String date = null;
	private String userID = null;
	private String offerID = null;
	private String domain = null;
	private String mailbox = null;
	private String offerOwnerID = null;
	private String education = null;
	private String workyear = null;
	private Boolean favorite = false;
	private Boolean applied = false;
	private Boolean isAccedpted = false;
	private Boolean isComplete = false;
	private String salary = null;
	private PublisherInfo publisherInfo = null;

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getWorkyear() {
		return workyear;
	}

	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}

	public Boolean getIsAccedpted() {
		return isAccedpted;
	}

	public void setIsAccedpted(Boolean isAccedpted) {
		this.isAccedpted = isAccedpted;
	}

	public String getOfferOwnerID() {
		return offerOwnerID;
	}

	public void setOfferOwnerID(String offerOwnerID) {
		this.offerOwnerID = offerOwnerID;
	}

	public Boolean getApplied() {
		return applied;
	}

	public void setApplied(Boolean applied) {
		this.applied = applied;
	}

	public PublisherInfo getPublisherInfo() {
		return publisherInfo;
	}

	public void setPublisherInfo(PublisherInfo publisherInfo) {
		this.publisherInfo = publisherInfo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getOfferID() {
		return offerID;
	}

	public void setOfferID(String offerID) {
		this.offerID = offerID;
	}

	public Boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}

	public String getUserID() {
		return userID;
	}

	public Boolean getFavorite() {
		return favorite;
	}

	public void setFavorite(Boolean favorite) {
		this.favorite = favorite;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getDate() {
		if (date != null && date.length() >= 10) {
			date = date.substring(0, 10);
		}
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public boolean isAllSet() {
		if (entreprise.length() != 0 && city.length() != 0
				&& description.length() != 0 && post.length() != 0
				&& salary.length() != 0 && domain.length() != 0
				&& workyear.length() != 0 && education.length() != 0) {
			return true;
		}
		return false;
	}

	public boolean checkEmailAddress(String address, String addressDefault) {
		if (address != null && address.length() != 0) {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(address)
					.matches();
		}
		mailbox = addressDefault;
		return true;
	}

}
