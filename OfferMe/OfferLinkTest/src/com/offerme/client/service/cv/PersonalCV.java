package com.offerme.client.service.cv;

import java.io.Serializable;

public class PersonalCV implements Serializable {

	private static final long serialVersionUID = -1430402350953085830L;
	private PersonalWorkInfo workInfo;
	private String userID = null;
	private byte[] profile = null;
	private String date = null;
	private String name = null;
	private String post = null;
	private String age = null;
	private String city = null;
	private String postApplied = null;
	private String postID = null;
	private String entreprise = null;
	private String workyear = null;
	private String education = null;
	private String colleage = null;

	public String getPostID() {
		return postID;
	}

	public void setPostID(String postID) {
		this.postID = postID;
	}

	public String getPostApplied() {
		return postApplied;
	}

	public void setPostApplied(String postApplied) {
		this.postApplied = postApplied;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public byte[] getProfile() {
		return profile;
	}

	public void setProfile(byte[] profile) {
		this.profile = profile;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public PersonalWorkInfo getWorkInfo() {
		return workInfo;
	}

	public void setWorkInfo(PersonalWorkInfo workInfo) {
		this.workInfo = workInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
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

	public String getColleage() {
		return colleage;
	}

	public void setColleage(String colleage) {
		this.colleage = colleage;
	}

}
