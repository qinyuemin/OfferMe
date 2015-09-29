package com.offerme.client.activity;

import com.offerme.R;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.PublishOfferSrvc;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.publishoffer.OfferInfo;
import com.umeng.analytics.MobclickAgent;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;

public class PublishOfferActivity extends ActionBarActivity {

	public static final String MODIFYOFFER = "ModifyOffer";
	public static final String ADDOFFER = "isForAddOffer";
	private EditText mailbox = null;
	private EditText description = null;
	private EditText post = null;
	private TextView salary = null;
	private TextView entreprise = null;
	private TextView city = null;
	private TextView domain = null;
	private TextView education = null;
	private TextView workyear = null;
	private LinearLayout cityLayout = null;
	private LinearLayout domainLayout = null;
	private LinearLayout educationLayout = null;
	private LinearLayout workyearLayout = null;
	private LinearLayout salaryLayout = null;
	private Dialog dialog = null;
	private LocalPersonInfo info = LocalPersonInfo.getInstance(this);
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private PublishOfferSrvc publishOfferSrvc = PublishOfferSrvc
			.getInstance(utilSrvc);
	private OfferInfo offerInfo = new OfferInfo();
	private OfferInfo offer = null;
	private boolean isForAddOffer = true;
	private Intent intent;
	private String mPageName = "PublishOfferActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		setContentView(R.layout.activity_publish_offer);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.activity_publish_offer_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
		intent = this.getIntent();
		initWidget();
		loadData();
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

	@Override
	public void onBackPressed() {
		utilSrvc.gotoLoggedInFromPublish(this);
		return;
	}

	public void backToPublishOfferList(View view) {
		utilSrvc.gotoLoggedInFromPublish(this);
		return;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		this.setIntent(intent);
		this.intent = intent;
		loadData();
	}

	public void initWidget() {
		mailbox = (EditText) findViewById(R.id.mailboxName);
		description = (EditText) findViewById(R.id.contentDescript);
		post = (EditText) findViewById(R.id.postName);
		entreprise = (TextView) findViewById(R.id.enterpriseName);
		salary = (TextView) findViewById(R.id.salaryName);
		city = (TextView) findViewById(R.id.cityName);
		domain = (TextView) findViewById(R.id.domainName);
		education = (TextView) findViewById(R.id.educationName);
		workyear = (TextView) findViewById(R.id.workyearName);
		cityLayout = (LinearLayout) findViewById(R.id.publish_offer_city_layout);
		domainLayout = (LinearLayout) findViewById(R.id.publish_offer_domain_layout);
		educationLayout = (LinearLayout) findViewById(R.id.publish_offer_education_layout);
		workyearLayout = (LinearLayout) findViewById(R.id.publish_offer_workyear_layout);
		salaryLayout = (LinearLayout) findViewById(R.id.publish_offer_salary_layout);
		cityLayout.setOnClickListener(new SelectCity(this));
		educationLayout.setOnClickListener(new SelectEducation(this));
		domainLayout.setOnClickListener(new SelectDomain(this));
		workyearLayout.setOnClickListener(new SelectWorkyear(this));
		salaryLayout.setOnClickListener(new SelectSalary(this));
	}

	public void loadData() {
		if (!intent.getBooleanExtra(ADDOFFER, false)) {
			isForAddOffer = false;
			offer = (OfferInfo) intent.getSerializableExtra(MODIFYOFFER);
			initModifyOffer();
		} else {
			isForAddOffer = true;
			initAddOffer();
		}
	}

	private void initModifyOffer() {
		TextView title = (TextView) this
				.findViewById(R.id.publish_offer_bar_center);
		title.setText(this.getResources().getText(
				R.string.title_activity_publish_modify_offer));
		entreprise.setText(offer.getEntreprise());
		description.setText(offer.getDescription());
		post.setText(offer.getPost());
		city.setText(offer.getCity());
		domain.setText(offer.getDomain());
		education.setText(offer.getEducation());
		workyear.setText(offer.getWorkyear());
		String salaryText = offer.getSalary();
		if (salaryText != null) {
			salary.setText(offer.getSalary());
		} else {
			salary.setText("面议");
		}
		if (isMailboxPublic()) {
			mailbox.setText(offer.getMailbox());
			mailbox.setSelection(offer.getMailbox().length());
		}
		setCursorPosition(mailbox);
		setCursorPosition(post);
	}

	private void initAddOffer() {
		TextView title = (TextView) this
				.findViewById(R.id.publish_offer_bar_center);
		title.setText(this.getResources().getText(
				R.string.title_activity_publish_add_offer));
		post.setText(null);
		description.setText(null);
		salary.setText(null);
		education.setText(null);
		workyear.setText(null);
		entreprise.setText(info.getValue(LocalPersonInfo.ENTREPRISE));
		city.setText(info.getValue(LocalPersonInfo.CITY));
		if (isMailboxPublic()) {
			mailbox.setText(info.getValue(LocalPersonInfo.EMAIL));
			mailbox.setSelection(info.getValue(LocalPersonInfo.EMAIL).length());
		}
	}

	private void setCursorPosition(EditText editor) {
		String content = editor.getText().toString();
		if (content != null) {
			editor.setSelection(content.length());
		}
	}

	private boolean isMailboxPublic() {
		if (!info.getBoolean(LocalPersonInfo.IS_EMAIL_PUBLISHED)) {
			mailbox.setText(getResources().getText(
					R.string.publisher_mailbox_not_public));
			mailbox.setFocusable(false);
			return false;
		}
		return true;
	}

	public void submit(View view) {
		if (utilSrvc.isNetworkConnected(PublishOfferActivity.this)) {
			offerInfo = new OfferInfo();
			if (!isMailboxPublic()) {
				offerInfo.setMailbox(info.getValue(LocalPersonInfo.EMAIL));
			} else {
				offerInfo.setMailbox(mailbox.getText().toString());
			}
			offerInfo.setOfferOwnerID(info.getValue(LocalPersonInfo.USERID));
			offerInfo.setEntreprise(entreprise.getText().toString());
			offerInfo.setCity(city.getText().toString());
			offerInfo.setDescription(description.getText().toString());
			offerInfo.setPost(post.getText().toString());
			offerInfo.setDomain(domain.getText().toString());
			offerInfo.setSalary(salary.getText().toString());
			offerInfo.setEducation(education.getText().toString());
			offerInfo.setWorkyear(workyear.getText().toString());
			if (offer != null && !isForAddOffer) {
				offerInfo.setOfferID(offer.getOfferID());
			}
			publishOfferSrvc.submit(PublishOfferActivity.this, offerInfo);
		} else {
			utilSrvc.showConnectDialog(PublishOfferActivity.this);
		}
	}

	private void setWindow(int width, int height) {
		if (dialog != null) {
			Window window = dialog.getWindow();
			window.setGravity(Gravity.CENTER);
			WindowManager.LayoutParams lp = window.getAttributes();
			if (width >= 0) {
				lp.width = width;
			}
			if (height >= 0) {
				lp.height = height;
			}
			window.setAttributes(lp);
		}
	}

	private class clickSelectSalary implements OnClickListener {
		private String salarySelected;

		public clickSelectSalary(String salary) {
			salarySelected = salary;
		}

		@Override
		public void onClick(View view) {
			salary.setText(salarySelected);
			dialog.dismiss();
		}
	}

	private class SelectSalary implements OnClickListener {
		private Context context;

		public SelectSalary(Context ctx) {
			this.context = ctx;
		}

		private void initSalarySelectView(View myView) {
			Button salary0 = (Button) myView.findViewById(R.id.offer_salary_0);
			Button salary5 = (Button) myView.findViewById(R.id.offer_salary_5);
			Button salary10 = (Button) myView
					.findViewById(R.id.offer_salary_10);
			Button salary15 = (Button) myView
					.findViewById(R.id.offer_salary_15);
			Button salary20 = (Button) myView
					.findViewById(R.id.offer_salary_20);
			Button salary25 = (Button) myView
					.findViewById(R.id.offer_salary_25);
			Button salary30 = (Button) myView
					.findViewById(R.id.offer_salary_30);
			Button salary35 = (Button) myView
					.findViewById(R.id.offer_salary_35);
			Button salary40 = (Button) myView
					.findViewById(R.id.offer_salary_40);
			Button salary45 = (Button) myView
					.findViewById(R.id.offer_salary_45);
			Button salary50 = (Button) myView
					.findViewById(R.id.offer_salary_50);
			salary0.setOnClickListener(new clickSelectSalary(salary0.getText()
					.toString()));
			salary5.setOnClickListener(new clickSelectSalary(salary5.getText()
					.toString()));
			salary10.setOnClickListener(new clickSelectSalary(salary10
					.getText().toString()));
			salary15.setOnClickListener(new clickSelectSalary(salary15
					.getText().toString()));
			salary20.setOnClickListener(new clickSelectSalary(salary20
					.getText().toString()));
			salary25.setOnClickListener(new clickSelectSalary(salary25
					.getText().toString()));
			salary30.setOnClickListener(new clickSelectSalary(salary30
					.getText().toString()));
			salary35.setOnClickListener(new clickSelectSalary(salary35
					.getText().toString()));
			salary40.setOnClickListener(new clickSelectSalary(salary40
					.getText().toString()));
			salary45.setOnClickListener(new clickSelectSalary(salary45
					.getText().toString()));
			salary50.setOnClickListener(new clickSelectSalary(salary50
					.getText().toString()));
		}

		@Override
		public void onClick(View view) {
			LayoutInflater factory = (LayoutInflater) PublishOfferActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_select_salary, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			setWindow(540, 600);
			initSalarySelectView(myView);
		}
	}

	private class clickSelectWorkyear implements OnClickListener {
		private String workyearSelected;

		public clickSelectWorkyear(String year) {
			workyearSelected = year;
		}

		@Override
		public void onClick(View view) {
			workyear.setText(workyearSelected);
			dialog.dismiss();
		}
	}

	private class SelectWorkyear implements OnClickListener {
		private Context context;

		public SelectWorkyear(Context ctx) {
			this.context = ctx;
		}

		private void initWorkyearSelectView(View myView) {
			Button workyear0 = (Button) myView
					.findViewById(R.id.offer_workyear_0);
			Button workyear3 = (Button) myView
					.findViewById(R.id.offer_workyear_3);
			Button workyear5 = (Button) myView
					.findViewById(R.id.offer_workyear_5);
			Button workyear10 = (Button) myView
					.findViewById(R.id.offer_workyear_10);
			Button workyear99 = (Button) myView
					.findViewById(R.id.offer_workyear_99);
			workyear0.setOnClickListener(new clickSelectWorkyear(workyear0
					.getText().toString()));
			workyear3.setOnClickListener(new clickSelectWorkyear(workyear3
					.getText().toString()));
			workyear5.setOnClickListener(new clickSelectWorkyear(workyear5
					.getText().toString()));
			workyear10.setOnClickListener(new clickSelectWorkyear(workyear10
					.getText().toString()));
			workyear99.setOnClickListener(new clickSelectWorkyear(workyear99
					.getText().toString()));
		}

		@Override
		public void onClick(View arg0) {
			LayoutInflater factory = (LayoutInflater) PublishOfferActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(
					R.layout.dialog_select_offer_workyear, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			setWindow(540, 600);
			initWorkyearSelectView(myView);
		}
	}

	private class clickSelectDomain implements OnClickListener {
		private String domainSelected;

		public clickSelectDomain(String domain) {
			domainSelected = domain;
		}

		@Override
		public void onClick(View view) {
			domain.setText(domainSelected);
			dialog.dismiss();
		}
	}

	private class SelectDomain implements OnClickListener {
		private Context context;

		public SelectDomain(Context ctx) {
			this.context = ctx;
		}

		private void initDomainSelectView(View myView) {
			Button internet = (Button) myView
					.findViewById(R.id.offer_domain_internet);
			Button it = (Button) myView.findViewById(R.id.offer_domain_it);
			Button telecommunication = (Button) myView
					.findViewById(R.id.offer_domain_telecommunication);
			Button finance = (Button) myView
					.findViewById(R.id.offer_domain_finance);
			internet.setOnClickListener(new clickSelectDomain(internet
					.getText().toString()));
			it.setOnClickListener(new clickSelectDomain(it.getText().toString()));
			telecommunication.setOnClickListener(new clickSelectDomain(
					telecommunication.getText().toString()));
			finance.setOnClickListener(new clickSelectDomain(finance.getText()
					.toString()));
		}

		@Override
		public void onClick(View view) {
			LayoutInflater factory = (LayoutInflater) PublishOfferActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_select_domain, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			setWindow(540, 600);
			initDomainSelectView(myView);
		}
	}

	private class clickSelectEducation implements OnClickListener {

		private String educationLevel;

		public clickSelectEducation(String level) {
			educationLevel = level;
		}

		@Override
		public void onClick(View view) {
			education.setText(educationLevel);
			dialog.dismiss();
		}
	}

	private class SelectEducation implements OnClickListener {
		private Context context;

		public SelectEducation(Context ctx) {
			this.context = ctx;
		}

		private void initEducationSelectView(View myView) {
			Button doctorat = (Button) myView
					.findViewById(R.id.offer_education_doctorat);
			Button master = (Button) myView
					.findViewById(R.id.offer_education_master);
			Button licence = (Button) myView
					.findViewById(R.id.offer_education_licence);
			Button belowlicence = (Button) myView
					.findViewById(R.id.offer_education_belowlicence);
			doctorat.setOnClickListener(new clickSelectEducation(doctorat
					.getText().toString()));
			master.setOnClickListener(new clickSelectEducation(master.getText()
					.toString()));
			licence.setOnClickListener(new clickSelectEducation(licence
					.getText().toString()));
			belowlicence.setOnClickListener(new clickSelectEducation(
					belowlicence.getText().toString()));
		}

		@Override
		public void onClick(View arg0) {
			LayoutInflater factory = (LayoutInflater) PublishOfferActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_select_education,
					null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			setWindow(540, 600);
			initEducationSelectView(myView);
		}
	}

	private class clickSelectCity implements OnClickListener {
		private String citySelected;

		public clickSelectCity(String cityName) {
			citySelected = cityName;
		}

		@Override
		public void onClick(View view) {
			city.setText(citySelected);
			dialog.dismiss();
		}
	}

	private class SelectCity implements OnClickListener {
		private Context context;

		public SelectCity(Context ctx) {
			this.context = ctx;
		}

		private void initCitySelectView(View myView) {
			if (myView != null) {
				Button clickBeijing = (Button) myView
						.findViewById(R.id.personal_city_beijing);
				Button clickShanghai = (Button) myView
						.findViewById(R.id.personal_city_shanghai);
				Button clickShenzheng = (Button) myView
						.findViewById(R.id.personal_city_shenzheng);
				Button clickGuangzhou = (Button) myView
						.findViewById(R.id.personal_city_guangzhou);
				Button clickHangzhou = (Button) myView
						.findViewById(R.id.personal_city_hangzhou);
				Button clickNanjing = (Button) myView
						.findViewById(R.id.personal_city_nanjing);
				Button clickSuzhou = (Button) myView
						.findViewById(R.id.personal_city_suzhou);
				Button clickWuhan = (Button) myView
						.findViewById(R.id.personal_city_wuhan);
				Button clickXianggang = (Button) myView
						.findViewById(R.id.personal_city_xianggang);
				Button clickTaibei = (Button) myView
						.findViewById(R.id.personal_city_taibei);
				clickBeijing.setOnClickListener(new clickSelectCity("北京"));
				clickShanghai.setOnClickListener(new clickSelectCity("上海"));
				clickShenzheng.setOnClickListener(new clickSelectCity("深圳"));
				clickGuangzhou.setOnClickListener(new clickSelectCity("广州"));
				clickHangzhou.setOnClickListener(new clickSelectCity("广州"));
				clickNanjing.setOnClickListener(new clickSelectCity("杭州"));
				clickSuzhou.setOnClickListener(new clickSelectCity("南京"));
				clickWuhan.setOnClickListener(new clickSelectCity("武汉"));
				clickXianggang.setOnClickListener(new clickSelectCity("香港"));
				clickTaibei.setOnClickListener(new clickSelectCity("台北"));
			}
		}

		@Override
		public void onClick(View view) {
			LayoutInflater factory = (LayoutInflater) PublishOfferActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_select_city, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			setWindow(540, 600);
			initCitySelectView(myView);
		}
	}

}
