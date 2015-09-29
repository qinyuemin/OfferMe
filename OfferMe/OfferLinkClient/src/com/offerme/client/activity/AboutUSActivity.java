package com.offerme.client.activity;

import com.offerme.R;
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

public class AboutUSActivity extends ActionBarActivity {
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private FrameLayout backLayout;
	private String mPageName = "AboutUSActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);
		initActionBar();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

	public void initActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar()
				.setCustomView(R.layout.activity_aboutus_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
	}

	private void initWidget() {
		backLayout = (FrameLayout) findViewById(R.id.aboutas_back_layout);
		backLayout.setOnClickListener(new backClickListener(this));
	}

	private class backClickListener implements OnClickListener {
		private Context context;

		public backClickListener(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			utilSrvc.gotoSettingPage(context);
		}

	}

}
