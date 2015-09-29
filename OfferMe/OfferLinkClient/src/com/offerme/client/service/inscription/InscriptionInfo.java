package com.offerme.client.service.inscription;

import java.io.Serializable;

import android.net.Uri;

public class InscriptionInfo implements Serializable {

	private static final long serialVersionUID = 1L;
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
	private boolean isComplete = false;

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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public byte[] getPhotoByte() {
		return photoByte;
	}

	public void setPhotoByte(byte[] photoByte) {
		this.photoByte = photoByte;
	}

	public Uri stringTouri(String path) {
		return Uri.parse(path);
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

	public boolean isAllSet() {
		if (password.length() != 0 && telephoneNumber.length() != 0
				&& email.length() != 0 && entreprise.length() != 0
				&& city.length() != 0 && name.length() != 0
				&& post.length() != 0 && age.length() != 0
				&& workyear.length() != 0) {
			return true;
		}
		return false;
	}

	public boolean checkEmailAddress(String address) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(address).matches();
	}

	public String get(String paraName) {
		String rtlValue = null;
		if (paraName.equals("Name")) {
			if (this.getName() != null) {
				rtlValue = this.getName();
			} else {
				return null;
			}
		} else if (paraName.equals("city")) {
			if (this.getCity() != null) {
				rtlValue = this.getCity();
			} else {
				return null;
			}
		} else if (paraName.equals("entreprise")) {
			if (this.getEntreprise() != null) {
				rtlValue = this.getEntreprise();
			} else {
				return null;
			}
		} else if (paraName.equals("email")) {
			if (this.getEmail() != null) {
				rtlValue = this.getEmail();
			} else {
				return null;
			}
		} else if (paraName.equals("telephoneNumber")) {
			if (this.getTelephoneNumber() != null) {
				rtlValue = this.getTelephoneNumber();
			} else {
				return null;
			}
		} else if (paraName.equals("password")) {
			if (this.getPassword() != null) {
				rtlValue = this.getPassword();
			} else {
				return null;
			}
		} else if (paraName.equals("workyear")) {
			if (this.getWorkyear() != null) {
				rtlValue = this.getPassword();
			} else {
				return null;
			}
		} else if (paraName.equals("age")) {
			if (this.getAge() != null) {
				rtlValue = this.getPassword();
			} else {
				return null;
			}
		} else if (paraName.equals("post")) {
			if (this.getPost() != null) {
				rtlValue = this.getPost();
			} else {
				return null;
			}
		}
		return rtlValue;
	}
}
