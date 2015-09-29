package com.offerme.client.service.util;

import android.content.Context;
import android.content.Intent;

import com.offerme.client.activity.ChatActivity;
import com.offerme.client.activity.ChatCVListActivity;
import com.offerme.client.activity.ForgetPasswordActivity;
import com.offerme.client.activity.InscriptionDetailActivity;
import com.offerme.client.activity.PersonalCVActivity;
import com.offerme.client.activity.PersonalCVWorkInputActivity;
import com.offerme.client.activity.PersonalCVWorkListActivity;
import com.offerme.client.activity.PersonalInfoActivity;
//import com.offerme.client.activity.PersonalCVWorkInputActivity;
import com.offerme.client.activity.PublisherDetailInfoActivity;
import com.offerme.client.activity.SearchOfferActivity;
import com.offerme.client.activity.HomePageActivity;
import com.offerme.client.activity.InscriptionActivity;
import com.offerme.client.activity.LoggedInActivity;
import com.offerme.client.activity.PublishOfferActivity;
import com.offerme.client.activity.PublisherInfoActivity;
import com.offerme.client.activity.SettingActivity;
import com.offerme.client.activity.SettingFeedbackActivity;
import com.offerme.client.service.chat.ChatFriend;
import com.offerme.client.service.cv.PersonalCV;
import com.offerme.client.service.cv.PersonalWorkInfo;
import com.offerme.client.service.inscription.InscriptionInfo;
import com.offerme.client.service.publishoffer.OfferInfo;
import com.offerme.client.service.search.SearchKeyword;
import com.offerme.client.service.search.SearchResulatItem;

public class ActivityJump {

	private Intent intent;

	public ActivityJump() {
		intent = new Intent();
	}

	public boolean gotoHomeInPage(Context ctx) {
		intent.setClass(ctx, HomePageActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoForgetPwdPage(Context ctx) {
		intent.setClass(ctx, ForgetPasswordActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoLoggedInPage(Context ctx) {
		intent.setClass(ctx, LoggedInActivity.class);
		intent.putExtra(LoggedInActivity.FROM_USER, false);
		intent.putExtra(LoggedInActivity.FROM_CHAT, false);
		intent.putExtra(LoggedInActivity.FROM_PUBLISH, false);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoLoggedInFromUser(Context ctx) {
		intent.setClass(ctx, LoggedInActivity.class);
		intent.putExtra(LoggedInActivity.FROM_USER, true);
		intent.putExtra(LoggedInActivity.FROM_CHAT, false);
		intent.putExtra(LoggedInActivity.FROM_PUBLISH, false);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoLoggedInFromChat(Context ctx, Integer friendID) {
		intent.setClass(ctx, LoggedInActivity.class);
		intent.putExtra(LoggedInActivity.FROM_CHAT, true);
		intent.putExtra(LoggedInActivity.FROM_USER, false);
		intent.putExtra(LoggedInActivity.FROM_PUBLISH, false);
		intent.putExtra(LoggedInActivity.FRIENDID, friendID);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoLoggedInFromChat(Context ctx) {
		intent.setClass(ctx, LoggedInActivity.class);
		intent.putExtra(LoggedInActivity.FROM_CHAT, true);
		intent.putExtra(LoggedInActivity.FROM_USER, false);
		intent.putExtra(LoggedInActivity.FROM_PUBLISH, false);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoLoggedInFromPublish(Context ctx) {
		intent.setClass(ctx, LoggedInActivity.class);
		intent.putExtra(LoggedInActivity.FROM_PUBLISH, true);
		intent.putExtra(LoggedInActivity.FROM_USER, false);
		intent.putExtra(LoggedInActivity.FROM_CHAT, false);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoInscriptionPage(Context ctx) {
		intent.setClass(ctx, InscriptionActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoInscriptionDetailPage(Context ctx, InscriptionInfo info) {
		intent.setClass(ctx, InscriptionDetailActivity.class);
		intent.putExtra(InscriptionDetailActivity.INSCRIPTION_INFO, info);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPublishOfferPage(Context ctx) {
		intent.setClass(ctx, PublishOfferActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.putExtra(PublishOfferActivity.ADDOFFER, true);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPublishOfferFromList(Context ctx, OfferInfo offer) {
		intent.setClass(ctx, PublishOfferActivity.class);
		intent.putExtra(PublishOfferActivity.MODIFYOFFER, offer);
		intent.putExtra(PublishOfferActivity.ADDOFFER, false);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoSearchPage(Context ctx, SearchKeyword keyword) {
		intent.setClass(ctx, SearchOfferActivity.class);
		intent.putExtra(SearchOfferActivity.KEYWORD, keyword);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPublisherInfoPage(Context ctx, SearchResulatItem item) {
		intent.setClass(ctx, PublisherInfoActivity.class);
		intent.putExtra(PublisherInfoActivity.PUBLISHER_ITEM, item);
		intent.putExtra(PublisherInfoActivity.FROM_FAVORITE, false);
		intent.putExtra(PublisherInfoActivity.FROM_APPLIED, false);
		intent.putExtra(PublisherInfoActivity.FROM_DETAIL, false);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPublisherFromDetailInfo(Context ctx,
			SearchResulatItem item) {
		intent.setClass(ctx, PublisherInfoActivity.class);
		intent.putExtra(PublisherInfoActivity.FROM_PUBLISHER_DETAIL, item);
		intent.putExtra(PublisherInfoActivity.FROM_DETAIL, true);
		intent.putExtra(PublisherInfoActivity.FROM_FAVORITE, false);
		intent.putExtra(PublisherInfoActivity.FROM_APPLIED, false);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPublisherFromPersonalFavorite(Context ctx,
			SearchResulatItem item) {
		intent.setClass(ctx, PublisherInfoActivity.class);
		intent.putExtra(PublisherInfoActivity.FROM_PERSONAL_FAVORITE, item);
		intent.putExtra(PublisherInfoActivity.FROM_FAVORITE, true);
		intent.putExtra(PublisherInfoActivity.FROM_APPLIED, false);
		intent.putExtra(PublisherInfoActivity.FROM_DETAIL, false);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPublisherFromPersonalPublished(Context ctx,
			SearchResulatItem item) {
		intent.setClass(ctx, PublisherInfoActivity.class);
		intent.putExtra(PublisherInfoActivity.FROM_PERSONAL_APPLIED, item);
		intent.putExtra(PublisherInfoActivity.FROM_APPLIED, true);
		intent.putExtra(PublisherInfoActivity.FROM_FAVORITE, false);
		intent.putExtra(PublisherInfoActivity.FROM_DETAIL, false);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPersonalInfoPage(Context ctx) {
		intent.setClass(ctx, PersonalInfoActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPersonalCVFromPerson(Context ctx) {
		String empty = null;
		intent.setClass(ctx, PersonalCVActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.putExtra(PersonalCVActivity.FROM_NONCV, true);
		intent.putExtra(PersonalCVActivity.WORK_INFO_CV, empty);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPersonalCVFromReceived(Context ctx, PersonalCV cv) {
		intent.setClass(ctx, PersonalCVActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.putExtra(PersonalCVActivity.FROM_NONCV, false);
		intent.putExtra(PersonalCVActivity.FROM_CVRECEIVED, cv);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPersonalCVFromWorkList(Context ctx,
			PersonalWorkInfo workInfo) {
		intent.setClass(ctx, PersonalCVActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.putExtra(PersonalCVActivity.FROM_NONCV, true);
		intent.putExtra(PersonalCVActivity.WORK_INFO_CV, workInfo);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPersonalWorkInput(Context ctx,
			PersonalWorkInfo workInfo, int position) {
		intent.setClass(ctx, PersonalCVWorkInputActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.putExtra(PersonalCVWorkInputActivity.POSITION_MODIFIED, position);
		intent.putExtra(PersonalCVWorkInputActivity.WORK_INFO_INPUT, workInfo);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoPersonalWorkList(Context ctx, PersonalWorkInfo workInfo) {
		intent.setClass(ctx, PersonalCVWorkListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.putExtra(PersonalCVWorkListActivity.FROM_PERSONALCV, workInfo);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoDetailPage(Context ctx, SearchResulatItem item) {
		intent.setClass(ctx, PublisherDetailInfoActivity.class);
		intent.putExtra(PublisherDetailInfoActivity.PUBLISHER_DETAIL, item);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoChatPage(Context ctx) {
		intent.setClass(ctx, ChatActivity.class);
		intent.putExtra(ChatActivity.FROM_PUBLISHER, false);
		intent.putExtra(ChatActivity.FROM_CV, false);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoChatPage(Context ctx, ChatFriend friend) {
		intent.setClass(ctx, ChatActivity.class);
		intent.putExtra(ChatActivity.CHATFRIEND, friend);
		intent.putExtra(ChatActivity.FROM_PUBLISHER, false);
		intent.putExtra(ChatActivity.FROM_CV, false);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoChatPageFromPublisher(Context ctx, ChatFriend friend,
			SearchResulatItem item) {
		intent.setClass(ctx, ChatActivity.class);
		intent.putExtra(ChatActivity.CHATFRIEND, friend);
		intent.putExtra(ChatActivity.FROM_PUBLISHER, true);
		intent.putExtra(ChatActivity.FROM_CV, false);
		intent.putExtra(ChatActivity.FROM_CHAT, item);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoChatPageFromCV(Context ctx, ChatFriend friend,
			PersonalCV cv) {
		intent.setClass(ctx, ChatActivity.class);
		intent.putExtra(ChatActivity.FRIENDCV, cv);
		intent.putExtra(ChatActivity.CHATFRIEND, friend);
		intent.putExtra(ChatActivity.FROM_PUBLISHER, false);
		intent.putExtra(ChatActivity.FROM_CV, true);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoChatCVList(Context ctx) {
		intent.setClass(ctx, ChatCVListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoSettingPage(Context ctx) {
		intent.setClass(ctx, SettingActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

	public boolean gotoSettingFeedback(Context ctx) {
		intent.setClass(ctx, SettingFeedbackActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ctx.startActivity(intent);
		return true;
	}

}
