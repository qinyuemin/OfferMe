package com.offerme.server.model.business.search;

public class SearchKeyword {

	private String city;
	private String entreprise;
	private int personID;
	private int page;
	private String lastOfferID;

	public SearchKeyword(Integer userID) {
		this.personID = userID;
		this.page = 1;
	}

	public void setPersonID(Integer personID) {
		this.personID = personID;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Integer getPersonID() {
		return personID;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}

	public String getLastOfferID() {
		return lastOfferID;
	}

	public void setLastOfferID(String lastOfferID) {
		this.lastOfferID = lastOfferID;
	}
}
