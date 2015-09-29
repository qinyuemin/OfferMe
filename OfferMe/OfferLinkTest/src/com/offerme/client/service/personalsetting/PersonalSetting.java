package com.offerme.client.service.personalsetting;

import java.util.ArrayList;

import com.offerme.client.service.publishoffer.OfferInfo;

public class PersonalSetting {

	private Integer userId;
	private String profileImg;
	private String name;
	private String city;
	private String enterprise;
	private String email;
	private String age;
	private String post;
	private String workyear;
	private String phoneNumber;
	private byte[] profile = null;
	private boolean isEmailPublic = false;
	private boolean isPhonePublic = false;
	private ArrayList<OfferInfo> favoriteList = new ArrayList<OfferInfo>();
	private ArrayList<OfferInfo> publishList = new ArrayList<OfferInfo>();
	private ArrayList<OfferInfo> applyList = new ArrayList<OfferInfo>();

	public ArrayList<OfferInfo> getApplyList() {
		return applyList;
	}

	public void setApplyList(ArrayList<OfferInfo> applyList) {
		this.applyList = applyList;
	}

	public ArrayList<OfferInfo> getFavoriteList() {
		return favoriteList;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getWorkyear() {
		return workyear;
	}

	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}

	public void setFavoriteList(ArrayList<OfferInfo> favoriteList) {
		this.favoriteList = favoriteList;
	}

	public ArrayList<OfferInfo> getPublishList() {
		return publishList;
	}

	public void setPublishList(ArrayList<OfferInfo> publishList) {
		this.publishList = publishList;
	}

	public byte[] getProfile() {
		return profile;
	}

	public void setProfile(byte[] profile) {
		this.profile = profile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
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

	public boolean isEmailPublic() {
		return isEmailPublic;
	}

	public void setEmailPublic(boolean isEmailPublic) {
		this.isEmailPublic = isEmailPublic;
	}

	public boolean isPhonePublic() {
		return isPhonePublic;
	}

	public void setPhonePublic(boolean isPhonePublic) {
		this.isPhonePublic = isPhonePublic;
	}

}
