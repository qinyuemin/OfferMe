package com.offerme.client.service.search;

import java.io.Serializable;

public class SearchKeyword implements Serializable {

	private static final long serialVersionUID = 7416335947299146102L;
	private String city;
	private String entreprise;
	private Integer personID;
	private String lastOfferID;
	private int position;

	public SearchKeyword(Integer userID) {
		this.personID = userID;
		this.lastOfferID = null;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getLastOfferID() {
		return lastOfferID;
	}

	public void setLastOfferID(String lastOfferID) {
		this.lastOfferID = lastOfferID;
	}

	public void setPersonID(Integer personID) {
		this.personID = personID;
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

}
