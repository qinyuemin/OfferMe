package com.offerme.client.activity;

import com.offerme.R;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.ApplyOfferSrvc;
import com.offerme.client.service.SetFavoriteOfferSrvc;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.chat.ChatFriend;
import com.offerme.client.service.publishoffer.PublisherInfo;
import com.offerme.client.service.search.SearchResulatItem;
import com.offerme.client.service.util.GlobalValue;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.TextView;

public class PublisherInfoActivity extends ActionBarActivity {

	public static final String FROM_FAVORITE = "IsFromPersonalFavorite";
	public static final String FROM_APPLIED = "IsFromPersonalAppied";
	public static final String FROM_DETAIL = "IsFromPublisherDetail";
	public static final String FROM_PERSONAL_APPLIED = "PublisherFromPersonalApplied";
	public static final String FROM_PERSONAL_FAVORITE = "PublisherFromPersonalFavorite";
	public static final String FROM_PUBLISHER_DETAIL = "PublisherFromDetail";
	public static final String PUBLISHER_ITEM = "Publisher";
	private LocalPersonInfo info = null;
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private ApplyOfferSrvc applyOfferSrvc = ApplyOfferSrvc.getInstance();
	private SetFavoriteOfferSrvc favoriteSrvc;
	private SearchResulatItem searchResualt;
	private TextView publisher;
	private TextView mail;
	private TextView date;
	private TextView post;
	private TextView city;
	private TextView domain;
	private TextView entreprise;
	private TextView description;
	private TextView education;
	private TextView workyear;
	private TextView salary;
	private TextView personPost;
	private TextView personEntreprise;
	private ImageView profile;
	private ImageView sendCV;
	private ImageView CVSent;
	private ImageView sendMail;
	private ImageView contactPublisher;
	private ImageView moreInfo;
	private FrameLayout contactLayout;
	private LinearLayout detailInfoLayout;
	private CheckBox isFavorite;
	private boolean isFromPersonalFavorite = false;
	private boolean isFromPersonalApplied = false;
	private boolean isFromPubliserDetail = false;
	private Intent intent = null;
	private Dialog dialog;
	private String mPageName = "PublisherInfoActivity";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		setContentView(R.layout.activity_publisher_offerinfo);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.activity_publisher_offerinfo_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
		favoriteSrvc = SetFavoriteOfferSrvc.getInstance();
		utilSrvc = UtilSrvc.getInstance();
		info = LocalPersonInfo.getInstance(this);
		intent = this.getIntent();
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadData();
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(mPageName);
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

	@Override
	public void onBackPressed() {
		backClick();
	}

	public void backToLoggedIn(View view) {
		backClick();
	}

	private void backClick() {
		if (!isFromPersonalFavorite && !isFromPersonalApplied
				&& !isFromPubliserDetail) {
			utilSrvc.gotoLoggedInPage(this);
		} else if (isFromPubliserDetail) {
			utilSrvc.gotoDetailInfoPage(this, searchResualt);
		} else if ((isFromPersonalApplied || isFromPersonalFavorite)
				&& !isFromPubliserDetail) {
			utilSrvc.gotoLoggedInFromUser(this);
		}
		return;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		this.setIntent(intent);
		this.intent = intent;
		// loadData();
	}

	private void initView() {
		publisher = (TextView) findViewById(R.id.publisher_offer_info_name);
		personPost = (TextView) findViewById(R.id.publisher_offer_info_person_post);
		personEntreprise = (TextView) findViewById(R.id.publisher_offer_info_person_entreprise);
		mail = (TextView) findViewById(R.id.publisher_offer_info_mail);
		date = (TextView) findViewById(R.id.publisher_offer_info_date);
		post = (TextView) findViewById(R.id.publisher_offer_info_post);
		city = (TextView) findViewById(R.id.publisher_offer_info_city);
		domain = (TextView) findViewById(R.id.publisher_offer_info_domain);
		entreprise = (TextView) findViewById(R.id.publisher_offer_info_entrerprise);
		description = (TextView) findViewById(R.id.publisher_offer_info_description);
		education = (TextView) findViewById(R.id.publisher_offer_info_education);
		workyear = (TextView) findViewById(R.id.publisher_offer_info_workyear);
		salary = (TextView) findViewById(R.id.publisher_offer_info_salary);
		profile = (ImageView) findViewById(R.id.publisher_offer_info_profile);
		sendCV = (ImageView) findViewById(R.id.publisher_offer_info_send_cv);
		CVSent = (ImageView) findViewById(R.id.publisher_offer_info_send_cv_sent);
		sendMail = (ImageView) findViewById(R.id.publisher_offer_info_sendmail);
		contactPublisher = (ImageView) findViewById(R.id.publisher_offer_info_phone);
		moreInfo = (ImageView) findViewById(R.id.publisher_offer_info_publisher);
		contactLayout = (FrameLayout) findViewById(R.id.publisher_offer_info_phone_layout);
		detailInfoLayout = (LinearLayout) findViewById(R.id.publisher_offer_info_layout);
		isFavorite = (CheckBox) findViewById(R.id.publisher_offer_info_favorite);
	}

	private void loadData() {
		setVisibility(View.VISIBLE);
		contactLayout.setVisibility(View.VISIBLE);
		moreInfo.setVisibility(View.VISIBLE);
		if (intent.getBooleanExtra(FROM_FAVORITE, false)) {
			searchResualt = (SearchResulatItem) intent
					.getSerializableExtra(FROM_PERSONAL_FAVORITE);
			isFromPersonalFavorite = true;
			moreInfo.setVisibility(View.GONE);
		} else if (intent.getBooleanExtra(FROM_APPLIED, false)) {
			searchResualt = (SearchResulatItem) intent
					.getSerializableExtra(FROM_PERSONAL_APPLIED);
			isFromPersonalApplied = true;
			moreInfo.setVisibility(View.GONE);
		} else if (intent.getBooleanExtra(FROM_DETAIL, false)) {
			searchResualt = (SearchResulatItem) intent
					.getSerializableExtra(FROM_PUBLISHER_DETAIL);
			isFromPubliserDetail = true;
		} else {
			searchResualt = (SearchResulatItem) intent
					.getSerializableExtra(PUBLISHER_ITEM);
			isFromPersonalFavorite = false;
			isFromPersonalApplied = false;
			isFromPubliserDetail = false;
		}// this part will be change
		publisher.setText(searchResualt.getName());
		date.setText(searchResualt.getDate());
		post.setText(searchResualt.getTitle());
		city.setText(searchResualt.getCity());
		domain.setText(searchResualt.getDomain());
		workyear.setText(searchResualt.getWorkyear());
		education.setText(searchResualt.getEducation());
		entreprise.setText(searchResualt.getEntreprise());
		description.setText(searchResualt.getDescription());
		personPost.setText(searchResualt.getPublisherInfo().getPost());
		personEntreprise.setText(searchResualt.getPublisherInfo()
				.getEntreprise());
		profile.setImageBitmap(setProfileImg());
		isFavorite.setChecked(searchResualt.isFavorite());
		isFavorite.setOnClickListener(new favoriteClickListener(this));
		detailInfoLayout.setOnClickListener(new detailClickListener(this));
		if (searchResualt.getSalary().equalsIgnoreCase("面议")) {
			salary.setText(searchResualt.getSalary());
		} else {
			String salaryText = searchResualt.getSalary();
			String unit = getResources().getString(
					R.string.publisher_salary_unit);
			salary.setText(salaryText + unit);
		}
		if (searchResualt.getPublisherInfo().isMailPublished()) {
			mail.setText(searchResualt.getMailbox());
		} else {
			mail.setText(getResources().getString(
					R.string.publisher_offer_mail_not_public));
		}
		if (!searchResualt.getPublisherInfo().isPhonePublished()) {
			contactLayout.setVisibility(View.GONE);
		}

		if (searchResualt.isApplied()) {
			if (searchResualt.getPublisherInfo().isPhonePublished()) {
				contactLayout.setVisibility(View.VISIBLE);
				contactPublisher.setVisibility(View.VISIBLE);
			}
			sendCV.setVisibility(View.GONE);
			CVSent.setVisibility(View.VISIBLE);
		}
		if (isMyOffer()) {
			setVisibility(View.GONE);
		}
	}

	private boolean isMyOffer() {
		boolean resualt = false;
		String offerOwnerID = String.valueOf(searchResualt.getPublisherInfo()
				.getUserID());
		String personID = info.getValue(LocalPersonInfo.USERID);
		if (offerOwnerID.equalsIgnoreCase(personID)) {
			resualt = true;
		}
		return resualt;
	}

	private void setVisibility(int view) {
		isFavorite.setVisibility(view);
		contactPublisher.setVisibility(view);
		CVSent.setVisibility(view);
		sendMail.setVisibility(view);
		sendCV.setVisibility(view);
	}

	private Bitmap setProfileImg() {
		Bitmap imageBitmap;
		if (searchResualt.getPublisherInfo().getProfile() != null) {
			imageBitmap = BitmapFactory.decodeByteArray(searchResualt
					.getPublisherInfo().getProfile(), 0, searchResualt
					.getPublisherInfo().getProfile().length);
		} else {
			imageBitmap = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.dafaultuser01);
		}
		imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 80, 80, true);
		return imageBitmap;
	}

	public void checkApplyandFavorite() {
		Boolean isSetApply = applyOfferSrvc.isAppliedOffer(this,
				searchResualt.getOfferID());
		Boolean isSetFavorite = favoriteSrvc.isOfferFavorite(this,
				searchResualt.getOfferID());
		searchResualt.setApplied(isSetApply);
		searchResualt.setFavorite(isSetFavorite);
	}

	public void sendCV(View view) {
		if (utilSrvc.isNetworkConnected(this)) {
			checkApplyandFavorite();
			applyOfferSrvc.sendCV(this, searchResualt, sendCV, CVSent);
		} else {
			utilSrvc.showConnectDialog(this);
		}
	}

	public void sendMailFromOffer(View view) {
		PublisherInfo publisherInfo = searchResualt.getPublisherInfo();
		if (publisherInfo != null) {
			ChatFriend friend = new ChatFriend();
			friend.setFriendId(publisherInfo.getUserID());
			friend.setName(publisherInfo.getName());
			friend.setPortrait(publisherInfo.getProfile());
			friend.setPortraitVersion(-1);
			utilSrvc.gotoChatPageFromPublisher(this, friend, searchResualt);
		}
	}

	public void dialogCall(View view) {
		Uri uri = Uri.parse("tel:"
				+ searchResualt.getPublisherInfo().getTelephone());
		Intent it = new Intent();
		it.setAction(Intent.ACTION_CALL);
		it.setData(uri);
		this.startActivity(it);
	}

	public void dialogCancel(View view) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public void callPublisher(View view) {
		if (searchResualt.getPublisherInfo().isPhonePublished()) {
			LayoutInflater factory = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_phone, null);

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView, 0, 0, 0, 0);
			dialog.show();
			TextView phonenumber = (TextView) dialog
					.findViewById(R.id.dialog_phone_number);
			phonenumber
					.setText(searchResualt.getPublisherInfo().getTelephone());

		} else {
			Toast toast = Toast.makeText(this, "抱歉，对方未公开手机号",
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	private String sharedJobName() {
		String job = "Haha, I found an interesting job: "
				+ searchResualt.getTitle() + " in "
				+ searchResualt.getEntreprise() + ". You could contact him by "
				+ searchResualt.getMailbox();
		return job;
	}

	private String setURL() {
		String protocol = "http://120.27.39.10:15000/shareInfo?";
		String companyName = "companyName=" + searchResualt.getEntreprise();
		String jobName = "jobName=" + searchResualt.getTitle();
		String area = "area=" + searchResualt.getCity();
		String salary = "salary=" + searchResualt.getSalary();
		String ability = "ability=";
		String date = "date=" + searchResualt.getDate();
		String url = protocol + companyName + "&" + jobName + "&" + area + "&"
				+ salary + "&" + ability + "&" + date;
		return url;
	}

	public void ShareJob(View view) {
		String appID = utilSrvc.getWXappID();
		String appSecret = utilSrvc.getWXappSecret();
		try {
			UMSocialService mController = UMServiceFactory.getUMSocialService(
					GlobalValue.SHAREDESCRIPTION, RequestType.SOCIAL);
			mController.getConfig().enableSIMCheck(false);
			mController.getConfig().closeToast();
			UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
			UMWXHandler wxCircleHandler = new UMWXHandler(this, appID,
					appSecret);
			UMImage urlImage = new UMImage(this,
					"http://www.umeng.com/images/pic/home/social/img-1.png");
			wxHandler.addToSocialSDK();
			wxCircleHandler.setToCircle(true);
			wxCircleHandler.addToSocialSDK();

			CircleShareContent circleMedia = new CircleShareContent();
			// circleMedia.setShareContent(sharedJobName());
			// circleMedia.setTitle("Test Message From OfferLink Client");
			circleMedia.setShareContent("OfferLink虔诚为您服务");
			circleMedia.setTitle(sharedJobName());
			circleMedia.setShareImage(urlImage);
			circleMedia.setTargetUrl(setURL());
			mController.setShareMedia(circleMedia);
			//mController.openShare(this, false);
			mController.postShare(this, SHARE_MEDIA.WEIXIN_CIRCLE,
					new SnsPostListener() {
						@Override
						public void onStart() {
						}

						@Override
						public void onComplete(SHARE_MEDIA platform, int eCode,
								SocializeEntity entity) {
							if (eCode == 200) {
								Toast.makeText(PublisherInfoActivity.this,
										"分享成功", Toast.LENGTH_SHORT).show();
							}
						}

					});

		} catch (Exception e) {
			//e.printStackTrace();
		}

	}

	private class favoriteClickListener implements OnClickListener {
		private Context context;

		public favoriteClickListener(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View buttonView) {
			if (utilSrvc.isNetworkConnected(context)) {
				checkApplyandFavorite();
				favoriteSrvc.setFavoriteOffer(context, searchResualt,
						isFavorite.isChecked(), isFavorite);
			} else {
				utilSrvc.showConnectDialog(context);
			}
		}
	}

	private class detailClickListener implements OnClickListener {
		private Context context;

		public detailClickListener(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View arg0) {
			if (!isFromPersonalFavorite && !isFromPersonalApplied) {
				System.out.println("Show me the offerid: "
						+ searchResualt.getName());
				utilSrvc.gotoDetailInfoPage(context, searchResualt);
			} else {
				moreInfo.setVisibility(View.GONE);
				// utilSrvc.gotoLoggedInFromUser(context);
			}
		}
	}
}
