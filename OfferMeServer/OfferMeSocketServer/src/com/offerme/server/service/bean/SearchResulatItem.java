package com.offerme.server.service.bean;

public class SearchResulatItem {

	private String offerID;
	private String date;
	private String name;
	private String entreprise;
	private String city;
	private String title;
	private String domain;
	private String salary;
	private String mailbox;
	private String workyear;
	private String education;
	private String description;
	private boolean isFavorite;
	private boolean isApplied;
	private OfferInfo[] offerInfo = null;
	private PublisherInfo publisherInfo = null;
	private int favoriteCount = 0;

	public SearchResulatItem(String offerID, String title) {
		this.offerID = offerID;
		this.title = title;
	}

	public String getWorkyear() {
		return workyear;
	}

	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public boolean isApplied() {
		return isApplied;
	}

	public void setApplied(boolean isApplied) {
		this.isApplied = isApplied;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public String getOfferID() {
		return offerID;
	}

	public void setOfferID(String offerID) {
		this.offerID = offerID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public OfferInfo[] getOfferInfo() {
		return offerInfo;
	}

	public void setOfferInfo(OfferInfo[] offerInfo) {
		this.offerInfo = offerInfo;
	}

	public PublisherInfo getPublisherInfo() {
		return publisherInfo;
	}

	public void setPublisherInfo(PublisherInfo publisherInfo) {
		this.publisherInfo = publisherInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

}
