package com.offerme.server.model.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "offer")
public class Offer {

	private Integer offerId;
	private Integer userId;
	private String title;
	private String content;
	private String company;
	private String city;
	private String country;
	private String address;
	private Date creDt;
	private String status;
	private String trade;
	private String offerMail;
	private String salary;
	private String workyear;
	private String education;

	@Id
	@GeneratedValue
	@Column(name = "OFFER_ID")
	public Integer getOfferId() {
		return offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "COMPANY")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CRE_DT")
	public Date getCreDt() {
		return creDt;
	}

	public void setCreDt(Date creDt) {
		this.creDt = creDt;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "TRADE")
	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	@Column(name = "OFFER_MAIL")
	public String getOfferMail() {
		return offerMail;
	}

	public void setOfferMail(String offerMail) {
		this.offerMail = offerMail;
	}

	@Column(name = "SALARY")
	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	@Column(name = "EDUCATION")
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "WORKYEAR")
	public String getWorkyear() {
		return workyear;
	}

	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}
}
