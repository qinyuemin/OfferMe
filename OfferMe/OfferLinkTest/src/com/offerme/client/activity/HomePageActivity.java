package com.offerme.client.activity;

import com.offerme.R;
import com.offerme.client.service.LoginSrvc;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.login.LoginRequest;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateConfig;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageActivity extends ActionBarActivity {

	/** Services used in this activity */
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private LoginSrvc loginSrvc = LoginSrvc.getInstance();
	private String mPageName = "HomePageActivity";
	private EditText login;
	private EditText password;
	private ImageView loginButton;
	private ImageView signInButton;
	private TextView forgetPwd;
	private int exitClick = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		setContentView(R.layout.activity_home_page);
		try {
			updateVersion();
			initWidget();
		} catch (Exception e) {
			//e.printStackTrace();
		}
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.home_page, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

	@Override
	public void onBackPressed() {
		if (exitClick == 0) {
			Toast toast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			exitClick++;
		} else {
			exitClick = 0;
			utilSrvc.exit();
		}
		return;
	}

	private void updateVersion() {
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.setUpdateCheckConfig(false);
		UmengUpdateAgent.setAppkey("541d6642fd98c518d4025694");
		UmengUpdateAgent.setDeltaUpdate(false);
		UpdateConfig.setDebug(true);
		UmengUpdateAgent.update(this);
	}

	public void resetExitClick() {
		exitClick = 0;
	}

	private void initWidget() {
		getSupportActionBar().hide();
		login = (EditText) findViewById(R.id.home_page_login);
		password = (EditText) findViewById(R.id.home_page_password);
		loginButton = (ImageView) findViewById(R.id.home_page_login_button);
		signInButton = (ImageView) findViewById(R.id.home_page_sign_in);
		forgetPwd = (TextView) findViewById(R.id.home_page_forget_pwd);
		loginButton.setOnClickListener(new signIn(this));
		signInButton.setOnClickListener(new createAccount(this));
		forgetPwd.setOnClickListener(new forgetPassword(this));
	}

	private class signIn implements OnClickListener {
		private Context context;

		public signIn(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			resetExitClick();
			if (utilSrvc.isNetworkConnected(context)) {
				LoginRequest loginRequest = new LoginRequest();
				loginRequest.setLogin(login.getText().toString());
				loginRequest.setPassword(password.getText().toString());
				loginSrvc.submit(context, loginRequest);
			} else {
				utilSrvc.showConnectDialog(context);
			}
		}
	}

	private class createAccount implements OnClickListener {
		private Context context;

		public createAccount(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			resetExitClick();
			utilSrvc.gotoInscriptionPage(context);
		}

	}

	private class forgetPassword implements OnClickListener {
		private Context context;

		public forgetPassword(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			utilSrvc.gotoForgetPwdPage(context);
		}

	}
}
