package com.offerme.client.activity;

import com.offerme.R;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.inscription.InscriptionInfo;
import com.umeng.analytics.MobclickAgent;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class InscriptionActivity extends ActionBarActivity {

	/** function created automatically */
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private InscriptionInfo inscriptionInfo = new InscriptionInfo();
	private EditText textEmail = null;
	private EditText textPassword = null;
	private LinearLayout textAgreement = null;
	private Dialog dialog = null;
	private String mPageName = "InscriptionActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		setContentView(R.layout.activity_inscription);
		initActionbar();
		initialize();
	}

	private void initActionbar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.activity_inscription_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
	}

	private void initialize() {
		textEmail = (EditText) findViewById(R.id.inscription_mail_text);
		textPassword = (EditText) findViewById(R.id.inscription_password_txt);
		textAgreement = (LinearLayout) findViewById(R.id.inscription_agreement);
		textAgreement.setOnClickListener(new agreementClickListener());
	}

	@Override
	protected void onResume() {
		super.onResume();
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

	public void backToLogin(View view) {
		utilSrvc.gotoHomeInPage(this);
	}

	private class agreementClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			LayoutInflater factory = (LayoutInflater) InscriptionActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_agreement, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					InscriptionActivity.this);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		}

	}

	public void goToInscriptionDetail(View view) {
		if (inscriptionInfo.checkEmailAddress(textEmail.getText().toString())) {
			try {
				if (textEmail.getText().toString().length() == 0
						|| textPassword.getText().toString().length() == 0) {
					Toast toast = Toast.makeText(this, "您的信息填写不全",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					inscriptionInfo.setEmail(textEmail.getText().toString());
					inscriptionInfo.setPassword(textPassword.getText()
							.toString());
					utilSrvc.gotoInscriptionDetailPage(this, inscriptionInfo);
				}
			} catch (Exception e) {
			}
		} else {
			Toast toast = Toast.makeText(this, "邮箱不符合格式", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

}
