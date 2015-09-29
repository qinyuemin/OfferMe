package com.offerme.client.service.util;

import java.util.ArrayList;

import com.offerme.client.service.publishoffer.OfferInfo;
import com.offerme.client.service.search.SearchKeyword;
import com.offerme.client.service.search.SearchResulat;

public class GlobalValue {
	private SearchResulat resualt = new SearchResulat();
	private SearchKeyword keyword = null;
	private ArrayList<OfferInfo> offerList = new ArrayList<OfferInfo>();
	private boolean isEnd = false;
	private boolean isWeiboAccount = false;
	private boolean hasOfferChanged = false;
	private String currentCity = null;
	private final Integer toastTime = 500;
	private final String WXappID = "wxf3b27a07fbdea9b8";
	private final String WXappSecret = "3189d3f2c08993a4d6ff9f8be70ff8b9";
	public static final Integer SYSTEMID = 0;
	public static final String SHAREDESCRIPTION = "com.umeng.share";

	public Integer getToastTime() {
		return toastTime;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public boolean isWeiboAccount() {
		return isWeiboAccount;
	}

	public void setWeiboAccount(boolean isWeiboAccount) {
		this.isWeiboAccount = isWeiboAccount;
	}

	public boolean isHasOfferChanged() {
		return hasOfferChanged;
	}

	public void setHasOfferChanged(boolean hasOfferChanged) {
		this.hasOfferChanged = hasOfferChanged;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public SearchKeyword getKeyword() {
		return keyword;
	}

	public void setKeyword(SearchKeyword keyword) {
		this.keyword = keyword;
	}

	public void setSearchResualt(SearchResulat rlt) {
		resualt = rlt;
	}

	public ArrayList<OfferInfo> getOfferList() {
		return offerList;
	}

	public void setOfferList(ArrayList<OfferInfo> offerList) {
		this.offerList = offerList;
	}

	public boolean HasSearchResualt() {
		if (resualt.getSize() != 0) {
			return true;
		}
		return false;
	}

	public SearchResulat getSearchResualt() {
		return resualt;
	}

	public String getWXappID() {
		return WXappID;
	}

	public String getWXappSecret() {
		return WXappSecret;
	}


	public ArrayList<String> getUserColumName() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("TELEPHONE");
		names.add("MAIL");
		names.add("NAME");
		names.add("CITY");
		names.add("ENTREPRISE");
		names.add("ISPHONEPUBLIC");
		names.add("ISMAILPUBLIC");
		names.add("PROFILE");
		names.add("USERID");
		names.add("POST");
		return names;
	}

	public ArrayList<String> getCVColumName() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("CVUSERID");
		names.add("NAME");
		names.add("POST");
		names.add("AGE");
		names.add("ENTREPRISE");
		names.add("WORKYEAR");
		names.add("EDUCATION");
		names.add("COLLEAGE");
		names.add("FIRSTWORKENTREPRISE");
		names.add("FIRSTWORKYEAR");
		names.add("FIRSTWORKPOST");
		names.add("SECONDWORKENTREPRISE");
		names.add("SECONDWORKYEAR");
		names.add("SECONDWORKPOST");
		names.add("THIRDWORKENTREPRISE");
		names.add("THIRDWORKYEAR");
		names.add("THIRDWORKPOST");
		names.add("DATE");
		names.add("PROFILE");
		names.add("CITY");
		names.add("POSTID");
		names.add("POSTAPPLIED");
		names.add("USERID");
		return names;
	}

	public ArrayList<String> getOfferColumName() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("OFFERID");
		names.add("DATE");
		names.add("USERID");
		names.add("FAVORITE");
		names.add("ENTREPRISE");
		names.add("CITY");
		names.add("POST");
		names.add("DOMAIN");
		names.add("SALARY");
		names.add("DESCRIPTION");
		names.add("APPLIED");
		names.add("OFFEROWNERID");
		names.add("WORKYEAR");
		names.add("EDUCATION");
		names.add("MAIL");
		return names;
	}

	public ArrayList<String> getFriendColumName() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("FRIEND_ID");
		names.add("NAME");
		names.add("PORTRAIT");
		names.add("LAST_MESSAGE_ID");
		names.add("USERID");
		names.add("FRIEND_PROTRAIT_VERSION");
		return names;
	}

	public ArrayList<String> getMessageColumName() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("MESSAGE_ID");
		names.add("SENDER_ID");
		names.add("SENDER");
		names.add("RECEIVER_ID");
		names.add("RECEIVER");
		names.add("CONTENT");
		names.add("IS_COMING");
		names.add("RECEIVE_DATE");
		names.add("IS_READ");
		names.add("USERID");
		return names;
	}

	public String findProvincByID(int id) {
		String province = null;
		switch (id) {
		case 11:
			province = "北京";
			break;
		case 12:
			province = "天津";
			break;
		case 13:
			province = "河北";
			break;
		case 14:
			province = "山西";
			break;
		case 15:
			province = "内蒙古";
			break;
		case 21:
			province = "辽宁";
			break;
		case 22:
			province = "吉林";
			break;
		case 23:
			province = "黑龙江";
			break;
		case 31:
			province = "上海";
			break;
		case 32:
			province = "江苏";
			break;
		case 33:
			province = "浙江";
			break;
		case 34:
			province = "安徽";
			break;
		case 35:
			province = "福建";
			break;
		case 36:
			province = "江西";
			break;
		case 37:
			province = "山东";
			break;
		case 41:
			province = "河南";
			break;
		case 42:
			province = "湖北";
			break;
		case 43:
			province = "湖南";
			break;
		case 44:
			province = "广东";
			break;
		case 45:
			province = "广西";
			break;
		case 46:
			province = "海南";
			break;
		case 50:
			province = "重庆";
			break;
		case 51:
			province = "四川";
			break;
		case 52:
			province = "贵州";
			break;
		case 53:
			province = "云南";
			break;
		case 54:
			province = "西藏";
			break;
		case 61:
			province = "陕西";
			break;
		case 62:
			province = "甘肃";
			break;
		case 63:
			province = "青海";
			break;
		case 64:
			province = "宁夏";
			break;
		case 65:
			province = "新疆";
			break;
		case 71:
			province = "台湾";
			break;
		case 81:
			province = "香港";
			break;
		case 82:
			province = "澳门";
			break;
		default:
			break;
		}
		return province;
	}
}
