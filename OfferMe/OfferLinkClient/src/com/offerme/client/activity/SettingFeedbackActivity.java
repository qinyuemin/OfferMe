package com.offerme.client.activity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.offerme.R;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.SendMessageSrvc;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.chat.ChatMessage;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

public class SettingFeedbackActivity extends ActionBarActivity {
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private SendMessageSrvc sendMessageSrvc = SendMessageSrvc.getInstance();
	private LocalPersonInfo personInfo = LocalPersonInfo.getInstance(this);
	private EditText feedbackMessage;
	public static Integer SYSTEMID = 0;
	private String mPageName = "SettingFeedbackActivity";
	//private FeedbackAgent agent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_feedback);
		utilSrvc.addNewActivity(this);
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
	}

	@Override
	public void onBackPressed() {
		utilSrvc.gotoSettingPage(this);
		return;
	}

	public void backToSetting(View view) {
		utilSrvc.gotoSettingPage(this);
	}

	@SuppressLint("SimpleDateFormat")
	private String getDate() {
		Timestamp ts = new Timestamp(new Date().getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(ts);
	}

	public void submit(View view) {
		String contString = feedbackMessage.getText().toString();
		if (contString.length() > 0) {
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setDate(getDate());
			chatMessage.setLocalUserId(Integer.parseInt(personInfo
					.getValue(LocalPersonInfo.USERID)));
			chatMessage.setUserId(SYSTEMID);
			chatMessage.setName("");
			chatMessage.setComMsg(false);
			chatMessage.setText(contString);
			feedbackMessage.setText("");
			if (utilSrvc.isNetworkConnected(this)) {
				sendMessageSrvc.sendMessage(this, chatMessage);
			} else {
				utilSrvc.showConnectDialog(this);
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

	private void initActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.activity_setting_feedbak_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
	}

	private void initWidget() {
		initActionBar();
		feedbackMessage = (EditText) findViewById(R.id.feedback_text);
	}
}
