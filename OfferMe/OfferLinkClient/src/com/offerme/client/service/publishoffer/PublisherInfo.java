package com.offerme.client.service.publishoffer;

import java.io.Serializable;

public class PublisherInfo implements Serializable {

	private static final long serialVersionUID = -9141908891411362572L;
	private String telephone;
	private String email;
	private String name;
	private String city;
	private String entreprise;
	private String post;
	private boolean isPhonePublished;
	private boolean isMailPublished;
	private byte[] profile;
	private int userID;

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public boolean isPhonePublished() {
		return isPhonePublished;
	}

	public void setPhonePublished(boolean isPhonePublished) {
		this.isPhonePublished = isPhonePublished;
	}

	public boolean isMailPublished() {
		return isMailPublished;
	}

	public void setMailPublished(boolean isMailPublished) {
		this.isMailPublished = isMailPublished;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getProfile() {
		return profile;
	}

	public void setProfile(byte[] profile) {
		this.profile = profile;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
}
