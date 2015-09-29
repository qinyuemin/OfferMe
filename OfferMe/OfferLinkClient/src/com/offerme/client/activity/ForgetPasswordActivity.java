package com.offerme.client.activity;

import com.offerme.R;
import com.offerme.client.service.ForgetPasswordSrvc;
import com.offerme.client.service.UtilSrvc;
import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ForgetPasswordActivity extends ActionBarActivity {

	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private ForgetPasswordSrvc forgetSrvc = ForgetPasswordSrvc.getInstance();
	private TextView mailInscript;
	private FrameLayout backClick;
	private FrameLayout sendMailAddress;
	private String mPageName = "ForgetPasswordActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		setContentView(R.layout.activity_forget_password);
		initActionBar();
		initWidget();
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(mPageName);
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

	@Override
	public void onBackPressed() {
		utilSrvc.gotoHomeInPage(this);
	}

	private void initActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.activity_forget_password_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
	}

	private void initWidget() {
		mailInscript = (TextView) findViewById(R.id.forget_pwd_text);
		backClick = (FrameLayout) findViewById(R.id.forget_pwd_bar_return_layout);
		sendMailAddress = (FrameLayout) findViewById(R.id.forget_pwd_bar_save_layout);
		backClick.setOnClickListener(new backClickListener(this));
		sendMailAddress.setOnClickListener(new sendClickListener(this));
	}

	private class backClickListener implements OnClickListener {

		private Context context;

		public backClickListener(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			utilSrvc.gotoHomeInPage(context);
		}
	}

	private class sendClickListener implements OnClickListener {
		private Context context;

		public sendClickListener(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			if (utilSrvc.isNetworkConnected(context)) {
				String address = mailInscript.getText().toString();
				forgetSrvc.submit(address, context);
			} else {
				utilSrvc.showConnectDialog(context);
			}
		}

	}
}
