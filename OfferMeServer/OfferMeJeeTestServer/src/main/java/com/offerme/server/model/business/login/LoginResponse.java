package com.offerme.server.model.business.login;

import java.util.ArrayList;

import com.offerme.server.model.business.offer.OfferInfo;
import com.offerme.server.model.business.personinfo.PersonalCV;

public class LoginResponse {

	private int responseCode = -1;
	private Integer userId;
	private String name;
	private String city;
	private String enterprise;
	private String email;
	private String age;
	private String workyear;
	private String post;
	private String phoneNumber;
	private boolean isMailPublic = false;
	private boolean isPhonePublic = false;
	private PersonalCV cv;
	private ArrayList<OfferInfo> favoriteOffer = new ArrayList<OfferInfo>();
	private ArrayList<OfferInfo> publishOffer = new ArrayList<OfferInfo>();
	private ArrayList<OfferInfo> applyOffer = new ArrayList<OfferInfo>();
	private byte[] profile;

	public PersonalCV getCv() {
		return cv;
	}

	public void setCv(PersonalCV cv) {
		this.cv = cv;
	}

	public ArrayList<OfferInfo> getApplyOffer() {
		return applyOffer;
	}

	public void setApplyOffer(ArrayList<OfferInfo> applyOffer) {
		this.applyOffer = applyOffer;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getWorkyear() {
		return workyear;
	}

	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public boolean isMailPublic() {
		return isMailPublic;
	}

	public void setMailPublic(boolean isMailPublic) {
		this.isMailPublic = isMailPublic;
	}

	public boolean isPhonePublic() {
		return isPhonePublic;
	}

	public void setPhonePublic(boolean isPhonePublic) {
		this.isPhonePublic = isPhonePublic;
	}

	public ArrayList<OfferInfo> getFavoriteOffer() {
		return favoriteOffer;
	}

	public void setFavoriteOffer(ArrayList<OfferInfo> favoriteOffer) {
		this.favoriteOffer = favoriteOffer;
	}

	public ArrayList<OfferInfo> getPublishOffer() {
		return publishOffer;
	}

	public void setPublishOffer(ArrayList<OfferInfo> publishOffer) {
		this.publishOffer = publishOffer;
	}

	public byte[] getProfile() {
		return profile;
	}

	public void setProfile(byte[] profile) {
		this.profile = profile;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
