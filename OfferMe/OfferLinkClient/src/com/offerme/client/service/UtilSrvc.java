package com.offerme.client.service;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.offerme.client.service.chat.ChatFriend;
import com.offerme.client.service.cv.PersonalCV;
import com.offerme.client.service.cv.PersonalWorkInfo;
import com.offerme.client.service.inscription.InscriptionInfo;
import com.offerme.client.service.publishoffer.OfferInfo;
import com.offerme.client.service.search.SearchKeyword;
import com.offerme.client.service.search.SearchResulat;
import com.offerme.client.service.search.SearchResulatItem;
import com.offerme.client.service.util.ActivityJump;
import com.offerme.client.service.util.ActivityManager;
import com.offerme.client.service.util.GlobalValue;
import com.offerme.client.service.util.MultiMedia;
import com.offerme.client.service.util.NetworkMonitor;

public class UtilSrvc {
	private final static UtilSrvc instance = new UtilSrvc();
	private ActivityManager activityManager = ActivityManager.getInstance();
	private ActivityJump jumpSrvc;
	private NetworkMonitor networkMonitor;
	private GlobalValue globalValue;
	private MultiMedia media;

	private UtilSrvc() {
		jumpSrvc = new ActivityJump();
		networkMonitor = new NetworkMonitor();
		media = new MultiMedia();
		globalValue = new GlobalValue();
	}

	public static UtilSrvc getInstance() {
		return instance;
	}

	public void addNewActivity(Activity activity) {
		activityManager.addNewActivity(activity);
	}

	public void exit() {
		activityManager.exit();
	}

	public boolean gotoHomeInPage(Context ctx) {
		return jumpSrvc.gotoHomeInPage(ctx);
	}

	public boolean gotoForgetPwdPage(Context ctx) {
		return jumpSrvc.gotoForgetPwdPage(ctx);
	}

	public boolean gotoLoggedInPage(Context ctx) {
		return jumpSrvc.gotoLoggedInPage(ctx);
	}

	public boolean gotoLoggedInFromUser(Context ctx) {
		return jumpSrvc.gotoLoggedInFromUser(ctx);
	}

	public boolean gotoLoggedInFromChat(Context ctx, Integer friendID) {
		return jumpSrvc.gotoLoggedInFromChat(ctx, friendID);
	}

	public boolean gotoLoggedInFromChat(Context ctx) {
		return jumpSrvc.gotoLoggedInFromChat(ctx);
	}

	public boolean gotoLoggedInFromPublish(Context ctx) {
		return jumpSrvc.gotoLoggedInFromPublish(ctx);
	}

	public boolean gotoInscriptionPage(Context ctx) {
		return jumpSrvc.gotoInscriptionPage(ctx);
	}

	public boolean gotoInscriptionDetailPage(Context ctx, InscriptionInfo info) {
		return jumpSrvc.gotoInscriptionDetailPage(ctx, info);
	}

	public boolean gotoPublishOfferPage(Context ctx) {
		return jumpSrvc.gotoPublishOfferPage(ctx);
	}

	public boolean gotoPublishOfferFromList(Context ctx, OfferInfo offer) {
		return jumpSrvc.gotoPublishOfferFromList(ctx, offer);
	}

	public boolean gotoSearchPage(Context ctx, SearchKeyword keyword) {
		return jumpSrvc.gotoSearchPage(ctx, keyword);
	}

	public boolean gotoPersonalInfoPage(Context ctx) {
		return jumpSrvc.gotoPersonalInfoPage(ctx);
	}

	public boolean gotoPersonalCVFromPerson(Context ctx) {
		return jumpSrvc.gotoPersonalCVFromPerson(ctx);
	}

	public boolean gotoPersonalCVFromReceived(Context ctx, PersonalCV cv) {
		return jumpSrvc.gotoPersonalCVFromReceived(ctx, cv);
	}

	public boolean gotoPersonalCVFromWorkList(Context ctx,
			PersonalWorkInfo workInfo) {
		return jumpSrvc.gotoPersonalCVFromWorkList(ctx, workInfo);
	}

	public boolean gotoPersonalWorkInput(Context ctx,
			PersonalWorkInfo workInfo, int position) {
		return jumpSrvc.gotoPersonalWorkInput(ctx, workInfo, position);
	}

	public boolean gotoPersonalWorkList(Context ctx, PersonalWorkInfo workInfo) {
		return jumpSrvc.gotoPersonalWorkList(ctx, workInfo);
	}

	public boolean gotoPublisherInfoPage(Context ctx, SearchResulatItem item) {
		return jumpSrvc.gotoPublisherInfoPage(ctx, item);
	}

	public boolean gotoPublisherFromDetailInfo(Context ctx,
			SearchResulatItem item) {
		return jumpSrvc.gotoPublisherFromDetailInfo(ctx, item);
	}

	public boolean gotoPublisherFromPersonalFavorite(Context ctx,
			SearchResulatItem item) {
		return jumpSrvc.gotoPublisherFromPersonalFavorite(ctx, item);
	}

	public boolean gotoPublisherFromPersonalPublished(Context ctx,
			SearchResulatItem item) {
		return jumpSrvc.gotoPublisherFromPersonalPublished(ctx, item);
	}

	public boolean gotoChatPage(Context ctx) {
		return jumpSrvc.gotoChatPage(ctx);
	}

	public boolean gotoChatPage(Context ctx, ChatFriend friend) {
		return jumpSrvc.gotoChatPage(ctx, friend);
	}

	public boolean gotoChatPageFromPublisher(Context ctx, ChatFriend friend,
			SearchResulatItem item) {
		return jumpSrvc.gotoChatPageFromPublisher(ctx, friend, item);
	}

	public boolean gotoChatPageFromCV(Context ctx, ChatFriend friend,
			PersonalCV cv) {
		return jumpSrvc.gotoChatPageFromCV(ctx, friend, cv);
	}

	public boolean gotoChatCVList(Context ctx) {
		return jumpSrvc.gotoChatCVList(ctx);
	}

	public boolean gotoSettingPage(Context ctx) {
		return jumpSrvc.gotoSettingPage(ctx);
	}

	public boolean gotoAboutUSPage(Context ctx) {
		return jumpSrvc.gotoAboutUSPage(ctx);
	}

	public boolean gotoSettingFeedBack(Context ctx) {
		return jumpSrvc.gotoSettingFeedback(ctx);
	}

	public boolean gotoDetailInfoPage(Context ctx, SearchResulatItem item) {
		return jumpSrvc.gotoDetailPage(ctx, item);
	}

	public boolean isNetworkConnected(Context ctx) {
		return networkMonitor.isNetworkConnected(ctx);
	}

	public void showConnectDialog(Context ctx) {
		networkMonitor.showConnectDialog(ctx);
	}

	public Uri getUriFromBitMap(Bitmap imageBitmap, Context ctx) {
		return media.getUriFromBitMap(imageBitmap, ctx);
	}

	public byte[] getImageByte(Bitmap imageBitmap) {
		return media.getImageByte(imageBitmap);
	}

	public Bitmap getImageBitmap(Bitmap imageBitmap, int targetSize) {
		return media.getImageBitmap(imageBitmap, targetSize);
	}

	public Integer getSystemID() {
		return GlobalValue.SYSTEMID;
	}

	public boolean isWeiboAccount() {
		return globalValue.isWeiboAccount();
	}

	public void setWeiboAccount(boolean isWeibo) {
		globalValue.setWeiboAccount(isWeiboAccount());
	}

	public String findProvinceById(int id) {
		return globalValue.findProvincByID(id);
	}

	public ArrayList<String> getUserColumName() {
		return globalValue.getUserColumName();
	}

	public ArrayList<String> getCVColumName() {
		return globalValue.getCVColumName();
	}

	public ArrayList<String> getOfferColumName() {
		return globalValue.getOfferColumName();
	}

	public ArrayList<String> getFriendColumName() {
		return globalValue.getFriendColumName();
	}

	public ArrayList<String> getMessageColumName() {
		return globalValue.getMessageColumName();
	}

	public String getCurrentCity() {
		return globalValue.getCurrentCity();
	}

	public void setCurrentCity(String currentCity) {
		globalValue.setCurrentCity(currentCity);
	}

	public boolean isHasOfferChanged() {
		return globalValue.isHasOfferChanged();
	}

	public void setHasOfferChanged(boolean hasOfferChanged) {
		globalValue.setHasOfferChanged(hasOfferChanged);
	}

	public boolean isEnd() {
		return globalValue.isEnd();
	}

	public void setEnd(boolean isEnd) {
		globalValue.setEnd(isEnd);
	}

	public SearchKeyword getKeyword() {
		return globalValue.getKeyword();
	}

	public void setKeyword(SearchKeyword keyword) {
		globalValue.setKeyword(keyword);
	}

	public void setSearchResualt(SearchResulat rlt) {
		globalValue.setSearchResualt(rlt);
	}

	public boolean HasSearchResualt() {
		if (globalValue.getSearchResualt().getSize() != 0) {
			return true;
		}
		return false;
	}

	public SearchResulat getSearchResualt() {
		return globalValue.getSearchResualt();
	}

	public ArrayList<OfferInfo> getOfferList() {
		return globalValue.getOfferList();
	}

	public void setOfferList(ArrayList<OfferInfo> offerList) {
		globalValue.setOfferList(offerList);
	}

	public Integer getToastTime() {
		return globalValue.getToastTime();
	}

	public String getWXappID() {
		return globalValue.getWXappID();
	}

	public String getWXappSecret() {
		return globalValue.getWXappSecret();
	}
}
