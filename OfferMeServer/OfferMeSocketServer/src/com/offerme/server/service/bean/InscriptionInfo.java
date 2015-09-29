package com.offerme.server.service.bean;

public class InscriptionInfo {

	private byte[] photoByte = null;
	private String name = null;
	private String city = null;
	private String entreprise = null;
	private String email = null;
	private String telephoneNumber = null;
	private String password = null;
	private String workyear = null;
	private String age = null;
	private String post = null;
	private boolean isEmailPublished = false;
	private boolean isPhonePublished = false;

	public String getWorkyear() {
		return workyear;
	}

	public void setWorkyear(String workyear) {
		this.workyear = workyear;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEmailPublished() {
		return isEmailPublished;
	}

	public void setEmailPublished(boolean isEmailPublished) {
		this.isEmailPublished = isEmailPublished;
	}

	public boolean isPhonePublished() {
		return isPhonePublished;
	}

	public void setPhonePublished(boolean isPhonePublished) {
		this.isPhonePublished = isPhonePublished;
	}

	public byte[] getPhotoByte() {
		return photoByte;
	}

	public void setPhotoByte(byte[] photoByte) {
		this.photoByte = photoByte;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
