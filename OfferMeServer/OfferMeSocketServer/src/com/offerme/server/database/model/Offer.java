package com.offerme.server.database.model;

import java.sql.Timestamp;

/**
 * 
 * @author Edouard.Zhang
 * 
 */
public class Offer {

	/**
	 * OFFER_ID: INT
	 */
	private int offerId;
	/**
	 * USER_ID: INT 发内推用户ID
	 */
	private int userId;
	/**
	 * TITLE: VARCHAR(50) 标题
	 */
	private String title;
	/**
	 * CONTENT: TEXT 内容
	 */
	private String content;
	/**
	 * COMPANY:VARCHAR(50) 公司
	 */
	private String company;
	/**
	 * CITY: VARCHAR(50) 地区
	 */
	private String city;
	/**
	 * COUNTRY:VARCHAR(50) 国家
	 */
	private String country;
	/**
	 * ADDRESS:VARCHAR(255) 地址
	 */
	private String address;
	/**
	 * 发布Offer的用户对象
	 */
	private User offerUser;
	/**
	 * CRE_DT:DATE 发布日期
	 */
	private Timestamp date;
	/**
	 * STATUS:VARCHAR 状态
	 */
	private String status;
	/**
	 * TRADE:VARCHAR 行业
	 */
	private String trade;
	/**
	 * OFFER_MAIL:VARCHAR 接受邮箱
	 */
	private String offerMail;
	/**
	 * SALARY:String 月薪
	 */
	private String salary;
	/**
	 * 被收藏数量
	 */
	private int favoriteCount;

	private String workyear;

	private String education;

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

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getOfferUser() {
		return offerUser;
	}

	public void setOfferUser(User offerUser) {
		this.offerUser = offerUser;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getOfferMail() {
		return offerMail;
	}

	public void setOfferMail(String offeremail) {
		this.offerMail = offeremail;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

}
