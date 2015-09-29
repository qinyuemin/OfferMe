package com.offerme.server.model.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "user")  
public class User {
	
	private Integer userId;
	private String name;
	private String city;
	private String country;
	private String company;
	private String nickName;
	private String email;
	private String phone;
	private String psw;
	private byte[] portrait;
	private Date signUpDt;
	private Integer signInCount;
	private Date signLastDt;
	private Integer isEnable;
	private Integer emailPublic;
	private Integer phonePublic;
	private String mailIdentibyCode;
	private Integer favorite;
	private Integer portraitUpdateCount;
	private Integer age;
	private String post;
	private Integer workyear;
	
	@Id 
	@GeneratedValue 
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "CITY")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "COUNTRY")
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Column(name = "COMPANY")
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Column(name = "NICKNAME")
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "PSW")
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	
	@Column(name = "PORTRAIT")
	public byte[] getPortrait() {
		return portrait;
	}
	public void setPortrait(byte[] portrait) {
		this.portrait = portrait;
	}
	
	@Column(name = "SIGNUP_DT")
	public Date getSignUpDt() {
		return signUpDt;
	}
	public void setSignUpDt(Date signUpDt) {
		this.signUpDt = signUpDt;
	}
	
	@Column(name = "SIGNIN_COUNT")
	public Integer getSignInCount() {
		return signInCount;
	}
	public void setSignInCount(Integer signInCount) {
		this.signInCount = signInCount;
	}
	
	@Column(name = "SIGN_LAST_DT")
	public Date getSignLastDt() {
		return signLastDt;
	}
	public void setSignLastDt(Date signLastDt) {
		this.signLastDt = signLastDt;
	}
	
	@Column(name = "IS_ENABLE")
	public Integer getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
	
	@Column(name = "EMAIL_PUBLIC")
	public Integer getEmailPublic() {
		return emailPublic;
	}
	public void setEmailPublic(Integer emailPublic) {
		this.emailPublic = emailPublic;
	}
	
	@Column(name = "PHONE_PUBLIC")
	public Integer getPhonePublic() {
		return phonePublic;
	}
	public void setPhonePublic(Integer phonePublic) {
		this.phonePublic = phonePublic;
	}
	
	@Column(name = "PORTRAIT_UPDATE_DT")
	public Integer getPortraitUpdateCount() {
		return portraitUpdateCount;
	}
	public void setPortraitUpdateCount(Integer portraitUpdateCount) {
		this.portraitUpdateCount = portraitUpdateCount;
	}
	
	@Column(name = "AGE")
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Column(name = "POST")
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
	@Column(name = "MAIL_IDENTIFY_CODE")
	public String getMailIdentibyCode() {
		return mailIdentibyCode;
	}
	public void setMailIdentibyCode(String mailIdentibyCode) {
		this.mailIdentibyCode = mailIdentibyCode;
	}
	
	@Column(name = "FAVORITE")
	public Integer getFavorite() {
		return favorite;
	}
	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
	}
	
	@Column(name = "WORKYEAR")
	public Integer getWorkyear() {
		return workyear;
	}
	public void setWorkyear(Integer workyear) {
		this.workyear = workyear;
	}
}
