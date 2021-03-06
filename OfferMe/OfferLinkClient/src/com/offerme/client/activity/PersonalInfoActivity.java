package com.offerme.client.activity;

import com.offerme.R;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.PersonalSettingSrvc;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.personalsetting.PersonalSetting;
import com.umeng.analytics.MobclickAgent;

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
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalInfoActivity extends ActionBarActivity {
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private PersonalSettingSrvc personalSettingSrvc = PersonalSettingSrvc
			.getInstance();
	private final int TAKE_PICTURE = 2;
	private final int GET_GALLERY = 1;
	private byte[] imageByte = null;
	private Uri imaginePath = null;
	private PersonalSetting data = new PersonalSetting();
	private EditText name = null;
	private EditText mail = null;
	private EditText phone = null;
	private EditText entreprise = null;
	private EditText post = null;
	private TextView age = null;
	private TextView workyear = null;
	private TextView city = null;
	private CheckBox mailCheck = null;
	private CheckBox phoneCheck = null;
	private ImageView profile = null;
	private Dialog dialog;
	private LocalPersonInfo personInfo = LocalPersonInfo.getInstance(this);
	private FrameLayout backClick = null;
	private FrameLayout profileLayout = null;
	private FrameLayout ageLayout = null;
	private FrameLayout workLayout = null;
	private FrameLayout cityLayout = null;
	private String mPageName = "PersonalInfoActivity";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		initActionBar();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
		try {
			initWidget();
			loadData();
		} catch (Exception e) {
		}
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
		utilSrvc.gotoLoggedInFromUser(this);
		return;
	}

	private class backToPersonalSetting implements OnClickListener {

		private Context context;

		public backToPersonalSetting(Context ctx) {
			this.context = ctx;
		}

		@Override
		public void onClick(View view) {
			utilSrvc.gotoLoggedInFromUser(context);
		}

	}

	private void initActionBar() {
		setContentView(R.layout.activity_personal_setting_info);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.activity_personal_setting_info_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
	}

	private void initWidget() {
		name = (EditText) findViewById(R.id.personal_name);
		mail = (EditText) findViewById(R.id.personal_email);
		phone = (EditText) findViewById(R.id.personal_phone);
		post = (EditText) findViewById(R.id.personal_post);
		entreprise = (EditText) findViewById(R.id.personal_entreprise);
		mailCheck = (CheckBox) findViewById(R.id.personal_email_checkbox);
		phoneCheck = (CheckBox) findViewById(R.id.personal_phone_checkbox);
		profile = (ImageView) findViewById(R.id.personal_profile);
		age = (TextView) findViewById(R.id.personal_age);
		city = (TextView) findViewById(R.id.personal_city);
		workyear = (TextView) findViewById(R.id.personal_workyear);
		backClick = (FrameLayout) findViewById(R.id.person_inscript_back_click);
		profileLayout = (FrameLayout) findViewById(R.id.personal_profile_layout);
		ageLayout = (FrameLayout) findViewById(R.id.personal_age_layout);
		workLayout = (FrameLayout) findViewById(R.id.personal_workyear_layout);
		cityLayout = (FrameLayout) findViewById(R.id.personal_city_layout);
		// ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter
		// .createFromResource(this, R.array.city_list,
		// R.layout.spinner_type);
		// cityAdapter.setDropDownViewResource(R.layout.spinner_dropdown_type);
		// city.setAdapter(cityAdapter);
		profileLayout.setOnClickListener(new UploadPhoto(this));
		ageLayout.setOnClickListener(new SelectAge(this));
		workLayout.setOnClickListener(new SelectWorkYear(this));
		cityLayout.setOnClickListener(new SelectCity(this));
		backClick.setOnClickListener(new backToPersonalSetting(this));
	}

	private void loadData() {
		if (personInfo != null) {
			String year = personInfo.getValue(LocalPersonInfo.WORKYEAR);
			name.setText(personInfo.getValue(LocalPersonInfo.NAME));
			mail.setText(personInfo.getValue(LocalPersonInfo.EMAIL));
			phone.setText(personInfo.getValue(LocalPersonInfo.TELEPHONE));
			age.setText(personInfo.getValue(LocalPersonInfo.AGE));
			post.setText(personInfo.getValue(LocalPersonInfo.POST));
			entreprise.setText(personInfo.getValue(LocalPersonInfo.ENTREPRISE));
			if (year.equalsIgnoreCase("0")) {
				String firstwork = getResources().getString(
						R.string.personal_setting_first_work);
				workyear.setText(firstwork);
			} else {
				workyear.setText(year);
			}

			city.setText(personInfo.getValue(LocalPersonInfo.CITY));
			phoneCheck.setChecked(personInfo
					.getBoolean(LocalPersonInfo.IS_PHONE_PUBLISHED));
			mailCheck.setChecked(personInfo
					.getBoolean(LocalPersonInfo.IS_EMAIL_PUBLISHED));
			if (personInfo.getValue(LocalPersonInfo.PROFILE) != null) {
				if (imageByte == null
						&& personInfo.getValue(LocalPersonInfo.PROFILE) != null) {
					imageByte = Base64.decode(
							personInfo.getValue(LocalPersonInfo.PROFILE),
							Base64.DEFAULT);
				}
				if (imageByte != null) {
					Bitmap imageBitmap = BitmapFactory.decodeByteArray(
							imageByte, 0, imageByte.length);
					profile.setImageBitmap(imageBitmap);
				}
			}
			setCursorPosition(name);
			setCursorPosition(mail);
			setCursorPosition(phone);
			setCursorPosition(entreprise);
			setCursorPosition(post);
		}
	}

	private void setCursorPosition(EditText editor) {
		String content = editor.getText().toString();
		if (content != null) {
			editor.setSelection(content.length());
		}
	}

	private void updateData() {
		String firstwork = getResources().getString(
				R.string.personal_setting_first_work);
		data.setName(getTextInfo(name,
				personInfo.getValue(LocalPersonInfo.NAME)));
		data.setPost(getTextInfo(post,
				personInfo.getValue(LocalPersonInfo.POST)));
		data.setEnterprise(getTextInfo(entreprise,
				personInfo.getValue(LocalPersonInfo.ENTREPRISE)));
		data.setEmail(getTextInfo(mail,
				personInfo.getValue(LocalPersonInfo.EMAIL)));
		data.setPhoneNumber(getTextInfo(phone,
				personInfo.getValue(LocalPersonInfo.TELEPHONE)));
		data.setEmailPublic(mailCheck.isChecked());
		data.setPhonePublic(phoneCheck.isChecked());
		data.setAge(age.getText().toString());
		if (workyear.getText().toString().contains(firstwork)) {
			data.setWorkyear("0");
		} else {
			data.setWorkyear(workyear.getText().toString());
		}
		data.setCity(city.getText().toString());
		if (imageByte != null) {
			data.setProfile(imageByte);
		} else if (personInfo.getValue(LocalPersonInfo.PROFILE) != null) {
			data.setProfile(Base64.decode(
					personInfo.getValue(LocalPersonInfo.PROFILE),
					Base64.DEFAULT));
		} else {
			data.setProfile(null);
		}
		//resetImageByte();
	}

	public void resetImageByte() {
		imageByte = null;
	}

	public void Submit(View view) {
		if (utilSrvc.isNetworkConnected(this)) {
			updateData();
			personalSettingSrvc.savePersonalSetting(this, data);
		} else {
			utilSrvc.showConnectDialog(this);
		}
	}

	private String getTextInfo(EditText text, String defaultValue) {
		String resualt;
		if (text.getText().toString().length() != 0) {
			resualt = text.getText().toString();
		} else {
			resualt = text.getHint().toString();
			text.setHint(defaultValue);
		}
		return resualt;
	}

	public void photoCancel(View view) {
		if (dialog.isShowing()) {
			dialog.dismiss();
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

	private class clickSelectCity implements OnClickListener {
		private String cityClicked;

		public clickSelectCity(String cityName) {
			cityClicked = cityName;
		}

		@Override
		public void onClick(View view) {
			city.setText(cityClicked);
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
			LayoutInflater factory = (LayoutInflater) PersonalInfoActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_select_city, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.show();
			setWindow(540, 600);
			initCitySelectView(myView);
		}
	}

	private class clickSelectWorkyear implements OnClickListener {
		private String workyearSelected;
		private Context context;

		public clickSelectWorkyear(int year, Context ctx) {
			workyearSelected = String.valueOf(year);
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			String firstwork = context.getResources().getString(
					R.string.personal_setting_first_work);
			if (workyearSelected.equalsIgnoreCase("0")) {
				workyear.setText(firstwork);
			} else {
				workyear.setText(workyearSelected);
			}
			dialog.dismiss();
		}
	}

	private class SelectWorkYear implements OnClickListener {
		private Context context;

		public SelectWorkYear(Context ctx) {
			this.context = ctx;
		}

		private void initWorkyearSelectView(View myView) {
			if (myView != null) {
				Button click0 = (Button) myView
						.findViewById(R.id.personal_workyear_0);
				Button click1 = (Button) myView
						.findViewById(R.id.personal_workyear_1);
				Button click2 = (Button) myView
						.findViewById(R.id.personal_workyear_2);
				Button click3 = (Button) myView
						.findViewById(R.id.personal_workyear_3);
				Button click4 = (Button) myView
						.findViewById(R.id.personal_workyear_4);
				Button click5 = (Button) myView
						.findViewById(R.id.personal_workyear_5);
				Button click6 = (Button) myView
						.findViewById(R.id.personal_workyear_6);
				Button click7 = (Button) myView
						.findViewById(R.id.personal_workyear_7);
				Button click8 = (Button) myView
						.findViewById(R.id.personal_workyear_8);
				Button click9 = (Button) myView
						.findViewById(R.id.personal_workyear_9);
				Button click10 = (Button) myView
						.findViewById(R.id.personal_workyear_10);
				Button click11 = (Button) myView
						.findViewById(R.id.personal_workyear_11);
				Button click12 = (Button) myView
						.findViewById(R.id.personal_workyear_12);
				Button click13 = (Button) myView
						.findViewById(R.id.personal_workyear_13);
				Button click14 = (Button) myView
						.findViewById(R.id.personal_workyear_14);
				Button click15 = (Button) myView
						.findViewById(R.id.personal_workyear_15);
				Button click16 = (Button) myView
						.findViewById(R.id.personal_workyear_16);
				Button click17 = (Button) myView
						.findViewById(R.id.personal_workyear_17);
				Button click18 = (Button) myView
						.findViewById(R.id.personal_workyear_18);
				Button click19 = (Button) myView
						.findViewById(R.id.personal_workyear_19);
				Button click20 = (Button) myView
						.findViewById(R.id.personal_workyear_20);
				click0.setOnClickListener(new clickSelectWorkyear(0, context));
				click1.setOnClickListener(new clickSelectWorkyear(1, context));
				click2.setOnClickListener(new clickSelectWorkyear(2, context));
				click3.setOnClickListener(new clickSelectWorkyear(3, context));
				click4.setOnClickListener(new clickSelectWorkyear(4, context));
				click5.setOnClickListener(new clickSelectWorkyear(5, context));
				click6.setOnClickListener(new clickSelectWorkyear(6, context));
				click7.setOnClickListener(new clickSelectWorkyear(7, context));
				click8.setOnClickListener(new clickSelectWorkyear(8, context));
				click9.setOnClickListener(new clickSelectWorkyear(9, context));
				click10.setOnClickListener(new clickSelectWorkyear(10, context));
				click11.setOnClickListener(new clickSelectWorkyear(11, context));
				click12.setOnClickListener(new clickSelectWorkyear(12, context));
				click13.setOnClickListener(new clickSelectWorkyear(13, context));
				click14.setOnClickListener(new clickSelectWorkyear(14, context));
				click15.setOnClickListener(new clickSelectWorkyear(15, context));
				click16.setOnClickListener(new clickSelectWorkyear(16, context));
				click17.setOnClickListener(new clickSelectWorkyear(17, context));
				click18.setOnClickListener(new clickSelectWorkyear(18, context));
				click19.setOnClickListener(new clickSelectWorkyear(19, context));
				click20.setOnClickListener(new clickSelectWorkyear(20, context));
			}
		}

		@Override
		public void onClick(View view) {
			LayoutInflater factory = (LayoutInflater) PersonalInfoActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory
					.inflate(R.layout.dialog_select_workyear, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.show();
			setWindow(540, 600);
			initWorkyearSelectView(myView);
		}
	}

	private class clickSelectAge implements OnClickListener {

		private String ageClicked;

		public clickSelectAge(int ageSelected) {
			ageClicked = String.valueOf(ageSelected);
		}

		@Override
		public void onClick(View view) {
			age.setText(ageClicked);
			dialog.dismiss();
		}

	}

	private class SelectAge implements OnClickListener {
		private Context context;

		public SelectAge(Context ctx) {
			context = ctx;
		}

		private void initAgeSelectView(View myView) {
			if (myView != null) {
				Button click18 = (Button) myView
						.findViewById(R.id.personal_age_18);
				Button click19 = (Button) myView
						.findViewById(R.id.personal_age_19);
				Button click20 = (Button) myView
						.findViewById(R.id.personal_age_20);
				Button click21 = (Button) myView
						.findViewById(R.id.personal_age_21);
				Button click22 = (Button) myView
						.findViewById(R.id.personal_age_22);
				Button click23 = (Button) myView
						.findViewById(R.id.personal_age_23);
				Button click24 = (Button) myView
						.findViewById(R.id.personal_age_24);
				Button click25 = (Button) myView
						.findViewById(R.id.personal_age_25);
				Button click26 = (Button) myView
						.findViewById(R.id.personal_age_26);
				Button click27 = (Button) myView
						.findViewById(R.id.personal_age_27);
				Button click28 = (Button) myView
						.findViewById(R.id.personal_age_28);
				Button click29 = (Button) myView
						.findViewById(R.id.personal_age_29);
				Button click30 = (Button) myView
						.findViewById(R.id.personal_age_30);
				Button click31 = (Button) myView
						.findViewById(R.id.personal_age_31);
				Button click32 = (Button) myView
						.findViewById(R.id.personal_age_32);
				Button click33 = (Button) myView
						.findViewById(R.id.personal_age_33);
				Button click34 = (Button) myView
						.findViewById(R.id.personal_age_34);
				Button click35 = (Button) myView
						.findViewById(R.id.personal_age_35);
				Button click36 = (Button) myView
						.findViewById(R.id.personal_age_36);
				Button click37 = (Button) myView
						.findViewById(R.id.personal_age_37);
				Button click38 = (Button) myView
						.findViewById(R.id.personal_age_38);
				Button click39 = (Button) myView
						.findViewById(R.id.personal_age_39);
				Button click40 = (Button) myView
						.findViewById(R.id.personal_age_40);
				Button click41 = (Button) myView
						.findViewById(R.id.personal_age_41);
				Button click42 = (Button) myView
						.findViewById(R.id.personal_age_42);
				Button click43 = (Button) myView
						.findViewById(R.id.personal_age_43);
				Button click44 = (Button) myView
						.findViewById(R.id.personal_age_44);
				Button click45 = (Button) myView
						.findViewById(R.id.personal_age_45);
				click18.setOnClickListener(new clickSelectAge(18));
				click19.setOnClickListener(new clickSelectAge(19));
				click20.setOnClickListener(new clickSelectAge(20));
				click21.setOnClickListener(new clickSelectAge(21));
				click22.setOnClickListener(new clickSelectAge(22));
				click23.setOnClickListener(new clickSelectAge(23));
				click24.setOnClickListener(new clickSelectAge(24));
				click25.setOnClickListener(new clickSelectAge(25));
				click26.setOnClickListener(new clickSelectAge(26));
				click27.setOnClickListener(new clickSelectAge(27));
				click28.setOnClickListener(new clickSelectAge(28));
				click29.setOnClickListener(new clickSelectAge(29));
				click30.setOnClickListener(new clickSelectAge(30));
				click31.setOnClickListener(new clickSelectAge(31));
				click32.setOnClickListener(new clickSelectAge(32));
				click33.setOnClickListener(new clickSelectAge(33));
				click34.setOnClickListener(new clickSelectAge(34));
				click35.setOnClickListener(new clickSelectAge(35));
				click36.setOnClickListener(new clickSelectAge(36));
				click37.setOnClickListener(new clickSelectAge(37));
				click38.setOnClickListener(new clickSelectAge(38));
				click39.setOnClickListener(new clickSelectAge(39));
				click40.setOnClickListener(new clickSelectAge(40));
				click41.setOnClickListener(new clickSelectAge(41));
				click42.setOnClickListener(new clickSelectAge(42));
				click43.setOnClickListener(new clickSelectAge(43));
				click44.setOnClickListener(new clickSelectAge(44));
				click45.setOnClickListener(new clickSelectAge(45));
			}
		}

		@Override
		public void onClick(View view) {
			LayoutInflater factory = (LayoutInflater) PersonalInfoActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_select_age, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.show();
			setWindow(540, 600);
			initAgeSelectView(myView);
		}
	}

	private class clickSelectPhoto implements OnClickListener {
		@Override
		public void onClick(View view) {
			boolean returnValue = false;
			returnValue = personalSettingSrvc.uploadPhoto(
					PersonalInfoActivity.this, "FromLib");
			if (returnValue) {
				dialog.dismiss();
			}
		}
	}

	private class clickTakePhoto implements OnClickListener {
		@Override
		public void onClick(View view) {
			boolean returnValue = false;
			returnValue = personalSettingSrvc.uploadPhoto(
					PersonalInfoActivity.this, "FromCamera");
			if (returnValue) {
				dialog.dismiss();
			}
		}
	}

	private class UploadPhoto implements OnClickListener {

		private Context context;

		public UploadPhoto(Context ctx) {
			context = ctx;
		}

		private void initProfileSelectView(View myView) {
			if (myView != null) {
				TextView selectPhoto = (TextView) myView
						.findViewById(R.id.fromPhotoLib);
				TextView takePhoto = (TextView) myView
						.findViewById(R.id.fromCamera);
				selectPhoto.setOnClickListener(new clickSelectPhoto());
				takePhoto.setOnClickListener(new clickTakePhoto());
			}
		}

		@Override
		public void onClick(View arg0) {
			LayoutInflater factory = (LayoutInflater) PersonalInfoActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_select_photo, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.show();
			setWindow(540, -1);
			initProfileSelectView(myView);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && null != data) {
			switch (requestCode) {
			case GET_GALLERY:
				imaginePath = data.getData();
				try {
					Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(
							this.getContentResolver(), imaginePath);
					imageBitmap = utilSrvc.getImageBitmap(imageBitmap, 80);
					imageByte = utilSrvc.getImageByte(imageBitmap);
					profile.setImageBitmap(imageBitmap);
					if (!imageBitmap.isRecycled()) {
						// imageBitmap.recycle();
						imageBitmap = null;
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
				break;
			case TAKE_PICTURE:
				try {
					Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
					imageBitmap = utilSrvc.getImageBitmap(imageBitmap, 80);
					imageByte = utilSrvc.getImageByte(imageBitmap);
					// imaginePath = utilSrvc.getUriFromBitMap(imageBitmap,
					// this);
					profile.setImageBitmap(imageBitmap);
					if (!imageBitmap.isRecycled()) {
						// imageBitmap.recycle();
						imageBitmap = null;
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}
}
