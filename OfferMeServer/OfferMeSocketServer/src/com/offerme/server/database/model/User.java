package com.offerme.server.database.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.offerme.server.database.model.Offer;

/**
 * 用户
 * 
 * @author Edouard.Zhang
 * 
 */
public class User {

	public static class Enable {
		public static int NewUser = 0;

		public static int ValidateErrUser = 1;

		public static int ValidatedUser = 2;

		public static int DisabledUser = -1;
	}

	public User() {
		userId = -1;
		name = "";
		nameFamily = "";
		city = "";
		country = "";
		company = "";
		nickName = "";
		email = "";
		phone = "";
		psw = "";
		portrait = null;
		post = "";
		workyear=0;
		signUpDt = new Timestamp(System.currentTimeMillis());
		signInCount = 1;
		signLastDt = new Timestamp(System.currentTimeMillis());
		isEnable = Enable.NewUser;
	}

	/**
	 * USER_ID:INT
	 */
	private int userId;
	/**
	 * NAME:VARCHAR(20) 名
	 */
	private String name;
	/**
	 * NAME_FAMILY:VARCHAR(20) 姓
	 */
	private String nameFamily;
	/**
	 * CITY:VARCHAR(50) 城市
	 */
	private String city;
	/**
	 * COUNTRY:VARCHAR(50) 国家
	 */
	private String country;
	/**
	 * COMPANY:VARCHAR(50) 公司
	 */
	private String company;
	/**
	 * NICKNAME:VARCHAR(50) 昵称/用户名
	 */
	private String nickName;
	/**
	 * EMAIL:VARCHAR(50) 邮箱
	 */
	private String email;
	/**
	 * PHONE:VARCHAR(20) 手机
	 */
	private String phone;
	/**
	 * PSW:VARCHAR(32) 密码
	 */
	private String psw;
	/**
	 * PORTRAIT:BLOB 头像
	 */
	private byte[] portrait;
	/**
	 * SIGNUP_DT:DATETIME 注册时间
	 */
	private Timestamp signUpDt;
	/**
	 * SIGNIN_COUNT:INT 登录次数
	 */
	private int signInCount;
	/**
	 * SIGN_LAST_DT:DATE 上次登录时间
	 */
	private Timestamp signLastDt;

	private List<Offer> favoriteOfferList = new ArrayList<Offer>();
	/**
	 * ISENABLE: INT 是否有效
	 */
	private int isEnable;
	/**
	 * EMAILPUBLIC: INT 邮箱是否公开
	 */
	private int emailPublic;

	/**
	 * PHONEPUBLIC: INT 手机是否公开
	 */
	private int phonePublic;

	/**
	 * FAVORITE: INT 喜好数
	 */
	private int favorite;

	/**
	 * PORTRAITUPDATETIME: INT 头像更新次数
	 */
	private int portraitUpdateDt;

	private int age;

	private String post;
	
	private int workyear;

	public int getWorkyear() {
		return workyear;
	}

	public void setWorkyear(int workyear) {
		this.workyear = workyear;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameFamily() {
		return nameFamily;
	}

	public void setNameFamily(String nameFamily) {
		this.nameFamily = nameFamily;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public byte[] getPortrait() {
		return portrait;
	}

	public void setPortrait(byte[] portrait) {
		this.portrait = portrait;
	}

	public Timestamp getSignUpDt() {
		return signUpDt;
	}

	public void setSignUpDt(Timestamp signUpDate) {
		this.signUpDt = signUpDate;
	}

	public int getSignInCount() {
		return signInCount;
	}

	public void setSignInCount(int signInTime) {
		this.signInCount = signInTime;
	}

	public Timestamp getSignLastDt() {
		return signLastDt;
	}

	public void setSignLastDt(Timestamp signLastDate) {
		this.signLastDt = signLastDate;
	}

	public int getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}

	public List<Offer> getFavoriteOfferList() {
		return favoriteOfferList;
	}

	public void setFavoriteOfferList(List<Offer> favoriteOfferList) {
		this.favoriteOfferList = favoriteOfferList;
	}

	public void addFavoriteOfferMap(Offer offer) {

		if (!favoriteOfferList.contains(offer)) {
			favoriteOfferList.add(offer);
		}
	}

	public void removeFavoriteOfferMap(Offer offer) {

		if (favoriteOfferList.contains(offer)) {
			favoriteOfferList.remove(offer);
		}
	}

	public int getEmailPublic() {
		return emailPublic;
	}

	public void setEmailPublic(int emailPublic) {
		this.emailPublic = emailPublic;
	}

	public int getPhonePublic() {
		return phonePublic;
	}

	public void setPhonePublic(int phonePublic) {
		this.phonePublic = phonePublic;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public int getPortraitUpdateDt() {
		return portraitUpdateDt;
	}

	public void setPortraitUpdateDt(int portraitupdatetime) {
		this.portraitUpdateDt = portraitupdatetime;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
