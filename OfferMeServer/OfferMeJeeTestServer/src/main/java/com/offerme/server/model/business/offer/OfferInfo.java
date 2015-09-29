package com.offerme.server.model.business.offer;

import java.util.Date;

public class OfferInfo {

	private String entreprise;
	private String city;
	private String description;
	private String post;
	private String userID;
	private String userName;
	private String offerID;
	private String domain;
	private String mailbox;
	private String offerOwnerID;
	private String education = null;
	private String workyear = null;
	private Boolean favorite = false;
	private Boolean applied = false;
	private Boolean isComplete = false;
	private Boolean isAccedpted = false;
	private String salary;
	private Date date;
	private PublisherInfo publisherInfo;

	public Boolean getIsAccedpted() {
		return isAccedpted;
	}

	public void setIsAccedpted(Boolean isAccedpted) {
		this.isAccedpted = isAccedpted;
	}

	public Boolean getApplied() {
		return applied;
	}

	public void setApplied(Boolean applied) {
		this.applied = applied;
	}

	public String getOfferOwnerID() {
		return offerOwnerID;
	}

	public void setOfferOwnerID(String offerOwnerID) {
		this.offerOwnerID = offerOwnerID;
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

	public String getOfferID() {
		return offerID;
	}

	public void setOfferID(String offerID) {
		this.offerID = offerID;
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

	public String getEntreprise() {
		return entreprise;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
}
