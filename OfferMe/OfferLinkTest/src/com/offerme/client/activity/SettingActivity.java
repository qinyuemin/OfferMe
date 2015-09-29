package com.offerme.client.activity;

import com.offerme.R;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.util.GlobalValue;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingActivity extends ActionBarActivity {
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private LocalPersonInfo info = LocalPersonInfo.getInstance(this);
	private CheckBox acceptMessage;
	private ImageView logout;
	private ImageView feedback;
	private FrameLayout backToLogin;
	private LinearLayout publish;
	private String mPageName = "SettingActivity";
	private FeedbackAgent agent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		utilSrvc.addNewActivity(this);
		agent = new FeedbackAgent(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(mPageName);
		MobclickAgent.onPause(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
		initWidget();
		loadData();
	}

	@Override
	public void onBackPressed() {
		utilSrvc.gotoLoggedInFromUser(this);
		return;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

	public void initWidget() {
		initActionBar();
		acceptMessage = (CheckBox) findViewById(R.id.setting_accept_message);
		logout = (ImageView) findViewById(R.id.setting_logout);
		backToLogin = (FrameLayout) findViewById(R.id.setting_back_layout);
		publish = (LinearLayout) findViewById(R.id.setting_publish);
		feedback = (ImageView) findViewById(R.id.setting_feedback);
	}

	public void initActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar()
				.setCustomView(R.layout.activity_setting_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
	}

	public void loadData() {
		setChecked();
		acceptMessage.setOnClickListener(new acceptClickListener());
		logout.setOnClickListener(new logoutClickListener(this));
		publish.setOnClickListener(new publishClickListener(this));
		backToLogin.setOnClickListener(new backClickListener(this));
		feedback.setOnClickListener(new feedbackClickListener(this));
	}

	private void setChecked() {
		String value = info.getValue(LocalPersonInfo.ACCEPT_MESSAGE);
		if (value != null) {
			acceptMessage.setChecked(Boolean.parseBoolean(value));
		} else {
			acceptMessage.setChecked(true);
		}
	}

	private String getURL() {
		// TODO: for download the app
		String url = "http://";
		return url;
	}

	private String getShareTitle() {
		String title = "Thanks for downloading our app";
		return title;
	}

	private class publishClickListener implements OnClickListener {
		private Context context;

		public publishClickListener(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View arg0) {
			String appID = utilSrvc.getWXappID();
			String appSecret = utilSrvc.getWXappSecret();
			try {
				UMSocialService mController = UMServiceFactory
						.getUMSocialService(GlobalValue.SHAREDESCRIPTION,
								RequestType.SOCIAL);
				mController.getConfig().enableSIMCheck(false);
				UMWXHandler wxHandler = new UMWXHandler(context, appID,
						appSecret);
				UMWXHandler wxCircleHandler = new UMWXHandler(context, appID,
						appSecret);
				UMImage urlImage = new UMImage(context,
						"http://www.umeng.com/images/pic/home/social/img-1.png");
				wxHandler.addToSocialSDK();
				wxCircleHandler.setToCircle(true);
				wxCircleHandler.addToSocialSDK();

				CircleShareContent circleMedia = new CircleShareContent();
				circleMedia.setShareContent("OfferLink虔诚为您服务");
				circleMedia.setTitle(getShareTitle());
				circleMedia.setShareImage(urlImage);
				circleMedia.setTargetUrl(getURL());
				mController.setShareMedia(circleMedia);
				// mController.openShare(this, false);

				mController.postShare(context, SHARE_MEDIA.WEIXIN_CIRCLE,
						new SnsPostListener() {

							@Override
							public void onStart() {
							}

							@Override
							public void onComplete(SHARE_MEDIA platform,
									int eCode, SocializeEntity entity) {
								if (eCode == 200) {
									Toast.makeText(SettingActivity.this,
											"分享成功.", Toast.LENGTH_SHORT).show();
								}
							}
						});

			} catch (Exception e) {
				//e.printStackTrace();
			}
		}

	}

	private class logoutClickListener implements OnClickListener {

		private Context context;

		public logoutClickListener(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			info.clearMemory();
			utilSrvc.getSearchResualt().clearResulat();
			finish();
			utilSrvc.exit();
			utilSrvc.gotoHomeInPage(context);
		}

	}

	private class acceptClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			info.setAcceptMessage(String.valueOf(acceptMessage.isChecked()));
		}

	}

	private class backClickListener implements OnClickListener {
		private Context context;

		public backClickListener(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			utilSrvc.gotoLoggedInFromUser(context);
		}

	}

	private class feedbackClickListener implements OnClickListener {
		private Context context;

		public feedbackClickListener(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			if (utilSrvc.isNetworkConnected(context)) {
				LocalPersonInfo personInfo = LocalPersonInfo
						.getInstance(context);
				if (personInfo.getBoolean(LocalPersonInfo.IS_FIRST_FEEDBACK)) {
					String userid = personInfo.getValue(LocalPersonInfo.USERID);
					agent.getDefaultConversation().addUserReply(
							"My user id is: " + userid);
					personInfo.setFirstFeedBack(false);
				}
				agent.startFeedbackActivity();
			} else {
				utilSrvc.showConnectDialog(context);
			}
		}

	}

}
