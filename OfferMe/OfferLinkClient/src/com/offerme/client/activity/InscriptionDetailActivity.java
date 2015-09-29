package com.offerme.client.activity;

import com.offerme.R;
import com.offerme.client.service.InscriptionSrvc;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.inscription.InscriptionInfo;
import com.umeng.analytics.MobclickAgent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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

public class InscriptionDetailActivity extends ActionBarActivity {
	public final static String INSCRIPTION_INFO = "InscriptionInfo";
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private InscriptionSrvc inscriptionSrvc = InscriptionSrvc
			.getInstance(utilSrvc);
	private InscriptionInfo info;
	private final int TAKE_PICTURE = 2;
	private final int GET_GALLERY = 1;
	private byte[] imageByte = null;
	private Uri imaginePath = null;
	private EditText textName = null;
	private EditText textEntreprise = null;
	private EditText textEmail = null;
	private EditText textTelephone = null;
	private EditText textPost = null;
	private TextView textAge = null;
	private TextView textWorkyear = null;
	private TextView textCity = null;
	private CheckBox mailPublic = null;
	private CheckBox phonePublic = null;
	private ImageView profile = null;
	private Dialog processDialog = null;
	private Dialog dialog = null;
	private FrameLayout backClick = null;
	private FrameLayout profileLayout = null;
	private FrameLayout ageLayout = null;
	private FrameLayout workLayout = null;
	private FrameLayout cityLayout = null;
	private Intent intent;
	private String mPageName = "InscriptionDetailActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		setContentView(R.layout.activity_personal_setting_info);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.activity_personal_setting_info_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
		initWidget();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
		loadData();
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

	private class backToInscription implements OnClickListener {

		private Context context;

		public backToInscription(Context ctx) {
			this.context = ctx;
		}

		@Override
		public void onClick(View view) {
			utilSrvc.gotoInscriptionPage(context);
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		this.setIntent(intent);
	}

	private void initWidget() {
		textName = (EditText) findViewById(R.id.personal_name);
		textEntreprise = (EditText) findViewById(R.id.personal_entreprise);
		textTelephone = (EditText) findViewById(R.id.personal_phone);
		textEmail = (EditText) findViewById(R.id.personal_email);
		textPost = (EditText) findViewById(R.id.personal_post);
		textAge = (TextView) findViewById(R.id.personal_age);
		textCity = (TextView) findViewById(R.id.personal_city);
		textWorkyear = (TextView) findViewById(R.id.personal_workyear);
		mailPublic = (CheckBox) findViewById(R.id.personal_email_checkbox);
		phonePublic = (CheckBox) findViewById(R.id.personal_phone_checkbox);
		profile = (ImageView) findViewById(R.id.personal_profile);
		backClick = (FrameLayout) findViewById(R.id.person_inscript_back_click);
		profileLayout = (FrameLayout) findViewById(R.id.personal_profile_layout);
		ageLayout = (FrameLayout) findViewById(R.id.personal_age_layout);
		workLayout = (FrameLayout) findViewById(R.id.personal_workyear_layout);
		cityLayout = (FrameLayout) findViewById(R.id.personal_city_layout);
		profileLayout.setOnClickListener(new UploadPhoto(this));
		ageLayout.setOnClickListener(new SelectAge(this));
		workLayout.setOnClickListener(new SelectWorkYear(this));
		cityLayout.setOnClickListener(new SelectCity(this));
		// ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter
		// .createFromResource(this, R.array.city_list,
		// R.layout.spinner_type);
		// cityAdapter.setDropDownViewResource(R.layout.spinner_dropdown_type);
		// textCity.setAdapter(cityAdapter);
		backClick.setOnClickListener(new backToInscription(this));
	}

	private void loadData() {
		intent = this.getIntent();
		info = (InscriptionInfo) intent.getSerializableExtra("InscriptionInfo");
		textEmail.setText(info.getEmail());
	}

	public void Submit(View view) {
		if (utilSrvc.isNetworkConnected(this)) {
			String firstwork = getResources().getString(
					R.string.personal_setting_first_work);
			// info.setCity(textCity.getSelectedItem().toString());
			info.setCity(textCity.getText().toString());
			info.setEmail(textEmail.getText().toString());
			info.setEntreprise(textEntreprise.getText().toString());
			info.setName(textName.getText().toString());
			info.setAge(textAge.getText().toString());
			if (textWorkyear.getText().toString().contains(firstwork)) {
				info.setWorkyear("0");
			} else {
				info.setWorkyear(textWorkyear.getText().toString());
			}
			info.setPost(textPost.getText().toString());
			info.setTelephoneNumber(textTelephone.getText().toString());
			info.setEmailPublished(mailPublic.isChecked());
			info.setPhonePublished(phonePublic.isChecked());
			info.setPhotoByte(imageByte);
			inscriptionSrvc.checkInscripInfo(this, info);
			if (info.isComplete()) {
				setProcessDialog();
				inscriptionSrvc.submit(this, processDialog, info);
			}
		} else {
			utilSrvc.showConnectDialog(this);
		}
	}

	private void setProcessDialog() {
		LayoutInflater factory = LayoutInflater.from(this);
		View dialogView = factory.inflate(R.layout.dialog_process, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		processDialog = builder.create();
		((AlertDialog) processDialog).setView(dialogView, 0, 0, 0, 0);
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

	public void photoCancel(View view) {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	private class clickSelectCity implements OnClickListener {
		private String city;

		public clickSelectCity(String cityName) {
			city = cityName;
		}

		@Override
		public void onClick(View view) {
			textCity.setText(city);
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
				clickHangzhou.setOnClickListener(new clickSelectCity("杭州"));
				clickNanjing.setOnClickListener(new clickSelectCity("南京"));
				clickSuzhou.setOnClickListener(new clickSelectCity("苏州"));
				clickWuhan.setOnClickListener(new clickSelectCity("武汉"));
				clickXianggang.setOnClickListener(new clickSelectCity("香港"));
				clickTaibei.setOnClickListener(new clickSelectCity("台北"));
			}
		}

		@Override
		public void onClick(View view) {
			LayoutInflater factory = (LayoutInflater) InscriptionDetailActivity.this
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

	private class clickSelectWorkyear implements OnClickListener {
		private String workyear;
		private Context context;

		public clickSelectWorkyear(int year, Context ctx) {
			workyear = String.valueOf(year);
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			String firstwork = context.getResources().getString(
					R.string.personal_setting_first_work);
			if (workyear.equalsIgnoreCase("0")) {
				textWorkyear.setText(firstwork);
			} else {
				textWorkyear.setText(workyear);
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
			LayoutInflater factory = (LayoutInflater) InscriptionDetailActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory
					.inflate(R.layout.dialog_select_workyear, null);
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

	private class clickSelectAge implements OnClickListener {

		private String age;

		public clickSelectAge(int ageSelected) {
			age = String.valueOf(ageSelected);
		}

		@Override
		public void onClick(View view) {
			textAge.setText(age);
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
			LayoutInflater factory = (LayoutInflater) InscriptionDetailActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_select_age, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			setWindow(540, 600);
			initAgeSelectView(myView);
		}
	}

	private class clickSelectPhoto implements OnClickListener {
		@Override
		public void onClick(View view) {
			inscriptionSrvc.uploadPhoto(InscriptionDetailActivity.this,
					"FromLib");
			dialog.dismiss();
		}
	}

	private class clickTakePhoto implements OnClickListener {
		@Override
		public void onClick(View view) {
			inscriptionSrvc.uploadPhoto(InscriptionDetailActivity.this,
					"FromCamera");
			dialog.dismiss();
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
			LayoutInflater factory = (LayoutInflater) InscriptionDetailActivity.this
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
				} catch (Exception e) {
					// //e.printStackTrace();
				}
				break;
			case TAKE_PICTURE:
				try {
					Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
					imageBitmap = utilSrvc.getImageBitmap(imageBitmap, 80);
					imageByte = utilSrvc.getImageByte(imageBitmap);
					profile.setImageBitmap(imageBitmap);
				} catch (Exception e) {
					// //e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}
}
