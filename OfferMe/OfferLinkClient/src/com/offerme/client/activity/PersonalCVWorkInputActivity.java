package com.offerme.client.activity;

import java.util.ArrayList;
import java.util.Calendar;

import com.offerme.R;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.cv.PersonalWorkInfo;
import com.umeng.analytics.MobclickAgent;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalCVWorkInputActivity extends ActionBarActivity {
	public final static String WORK_INFO_INPUT = "WorkInfoInput";
	public final static String POSITION_MODIFIED = "PositionModified";
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private PersonalWorkInfo workInfo = new PersonalWorkInfo();
	private ArrayList<String> works = new ArrayList<String>();
	private EditText entreprise;
	private EditText post;
	private TextView yearStart;
	private TextView yearEnd;
	private ImageView deleteWork;
	private FrameLayout backLayout;
	private Toast toast;
	private Dialog dialog;
	private int position;
	private String mPageName = "PersonalWorkInputActivity";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		setContentView(R.layout.activity_personal_cv_input);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.activity_personal_cv_input_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
		initWidget();
		initData();
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

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		this.setIntent(intent);
	}
	
	@Override
	public void onBackPressed() {
		if (setWorkInfo()) {
			utilSrvc.gotoPersonalWorkList(this, workInfo);
		}
	}

	private void initWidget() {
		entreprise = (EditText) findViewById(R.id.personal_cv_input_entreprise);
		post = (EditText) findViewById(R.id.personal_cv_input_post);
		yearStart = (TextView) findViewById(R.id.personal_cv_input_date_start);
		yearEnd = (TextView) findViewById(R.id.personal_cv_input_date_end);
		deleteWork = (ImageView) findViewById(R.id.personal_cv_input_delete);
		backLayout = (FrameLayout) findViewById(R.id.personal_cv_bar_back_layout);
	}

	private void initData() {
		Calendar cal = Calendar.getInstance();
		entreprise.setText(null);
		post.setText(null);
		yearStart.setText(String.valueOf(cal.get(Calendar.YEAR)));
		yearEnd.setText(yearEnd.getHint().toString());
		deleteWork.setOnClickListener(new deleteClick(this));
		backLayout.setOnClickListener(new backClick(this));
		yearStart.setOnClickListener(new SelectStartYear(this));
		yearEnd.setOnClickListener(new SelectEndYear(this));
	}

	private void loadData() {
		workInfo = (PersonalWorkInfo) getIntent().getSerializableExtra(
				WORK_INFO_INPUT);
		position = (Integer) getIntent()
				.getSerializableExtra(POSITION_MODIFIED);
		works = workInfo.getWorks();
		if (works.size() != 0) {
			switch (position) {
			case 0:
				entreprise.setText(works.get(0));
				setWorkYear(works.get(1));
				post.setText(works.get(2));
				break;
			case 1:
				entreprise.setText(works.get(3));
				setWorkYear(works.get(4));
				post.setText(works.get(5));
				break;
			case 2:
				entreprise.setText(works.get(6));
				setWorkYear(works.get(7));
				post.setText(works.get(8));
				break;
			default:
				break;
			}
		}
		if (entreprise.getText() != null) {
			entreprise.setSelection(entreprise.getText().toString().length());
		}
		if (post.getText() != null) {
			post.setSelection(post.getText().toString().length());
		}
	}

	private void showText(String text) {
		toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	private void setWorkYear(String year) {
		if (year == null || year.length() < 4 || year.length() > 15) {
			yearStart.setText(yearStart.getText().toString());
			yearEnd.setText(yearEnd.getText().toString());
			return;
		}
		yearStart.setText(year.substring(0, 4));
		yearEnd.setText(year.substring(7));
	}

	private boolean isSetWorkYear() {
		boolean resualt = false;
		if (yearStart.getText() == null) {
			showText("起始年份未填写");
			return resualt;
		} else if (yearStart.getText().toString().equalsIgnoreCase("至今")) {
			showText("起始年份不能为至今哦~");
			return resualt;
		}
		if (yearEnd.getText() == null) {
			showText("截止年份未填写");
			return resualt;
		} else if (!yearEnd.getText().toString().equalsIgnoreCase("至今")) {
			Integer start = Integer.parseInt(yearStart.getText().toString());
			Integer end = Integer.parseInt(yearEnd.getText().toString());
			if (start > end) {
				showText("起始年份大于截止年份了哦~");
				return resualt;
			}
		}
		return true;
	}

	private boolean isSetEntreprise() {
		if (entreprise.getText() == null
				|| entreprise.getText().toString().length() == 0) {
			return false;
		}
		return true;
	}

	private boolean isSetPost() {
		if (post.getText() == null || post.getText().toString().length() == 0) {
			return false;
		}
		return true;
	}

	public boolean setWorkInfo() {
		if (!isSetWorkYear() || !isSetEntreprise() || !isSetPost()) {
			//showText("信息填写不完全哦~");
			return true;
		}
		switch (position) {
		case 0:
			works.set(0, entreprise.getText().toString());
			works.set(1, yearStart.getText().toString() + " ~ "
					+ yearEnd.getText().toString());
			works.set(2, post.getText().toString());
			break;
		case 1:
			works.set(3, entreprise.getText().toString());
			works.set(4, yearStart.getText().toString() + " ~ "
					+ yearEnd.getText().toString());
			works.set(5, post.getText().toString());
			break;
		case 2:
			works.set(6, entreprise.getText().toString());
			works.set(7, yearStart.getText().toString() + " ~ "
					+ yearEnd.getText().toString());
			works.set(8, post.getText().toString());
			break;
		default:
			break;
		}
		workInfo.setWorks(works);
		return true;
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

	private class clickSelectStartYear implements OnClickListener {
		private String yearSelected;

		public clickSelectStartYear(String year) {
			yearSelected = year;
		}

		@Override
		public void onClick(View view) {
			yearStart.setText(yearSelected);
			dialog.dismiss();
		}

	}

	private class SelectStartYear implements OnClickListener {
		private Context context;

		public SelectStartYear(Context ctx) {
			context = ctx;
		}

		private void initYearSelectView(View myView) {
			Button click95 = (Button) myView
					.findViewById(R.id.personal_cv_year_95);
			Button click96 = (Button) myView
					.findViewById(R.id.personal_cv_year_96);
			Button click97 = (Button) myView
					.findViewById(R.id.personal_cv_year_97);
			Button click98 = (Button) myView
					.findViewById(R.id.personal_cv_year_98);
			Button click99 = (Button) myView
					.findViewById(R.id.personal_cv_year_99);
			Button click00 = (Button) myView
					.findViewById(R.id.personal_cv_year_00);
			Button click01 = (Button) myView
					.findViewById(R.id.personal_cv_year_01);
			Button click02 = (Button) myView
					.findViewById(R.id.personal_cv_year_02);
			Button click03 = (Button) myView
					.findViewById(R.id.personal_cv_year_03);
			Button click04 = (Button) myView
					.findViewById(R.id.personal_cv_year_04);
			Button click05 = (Button) myView
					.findViewById(R.id.personal_cv_year_05);
			Button click06 = (Button) myView
					.findViewById(R.id.personal_cv_year_06);
			Button click07 = (Button) myView
					.findViewById(R.id.personal_cv_year_07);
			Button click08 = (Button) myView
					.findViewById(R.id.personal_cv_year_08);
			Button click09 = (Button) myView
					.findViewById(R.id.personal_cv_year_09);
			Button click10 = (Button) myView
					.findViewById(R.id.personal_cv_year_10);
			Button click11 = (Button) myView
					.findViewById(R.id.personal_cv_year_11);
			Button click12 = (Button) myView
					.findViewById(R.id.personal_cv_year_12);
			Button click13 = (Button) myView
					.findViewById(R.id.personal_cv_year_13);
			Button click14 = (Button) myView
					.findViewById(R.id.personal_cv_year_14);
			Button click15 = (Button) myView
					.findViewById(R.id.personal_cv_year_15);
			Button click16 = (Button) myView
					.findViewById(R.id.personal_cv_year_16);
			Button click17 = (Button) myView
					.findViewById(R.id.personal_cv_year_17);
			Button click18 = (Button) myView
					.findViewById(R.id.personal_cv_year_18);
			Button click19 = (Button) myView
					.findViewById(R.id.personal_cv_year_19);
			Button click20 = (Button) myView
					.findViewById(R.id.personal_cv_year_20);
			Button clickNow = (Button) myView
					.findViewById(R.id.personal_cv_year_now);
			click95.setOnClickListener(new clickSelectStartYear(click95
					.getText().toString()));
			click96.setOnClickListener(new clickSelectStartYear(click96
					.getText().toString()));
			click97.setOnClickListener(new clickSelectStartYear(click97
					.getText().toString()));
			click98.setOnClickListener(new clickSelectStartYear(click98
					.getText().toString()));
			click99.setOnClickListener(new clickSelectStartYear(click99
					.getText().toString()));
			click00.setOnClickListener(new clickSelectStartYear(click00
					.getText().toString()));
			click01.setOnClickListener(new clickSelectStartYear(click01
					.getText().toString()));
			click02.setOnClickListener(new clickSelectStartYear(click02
					.getText().toString()));
			click03.setOnClickListener(new clickSelectStartYear(click03
					.getText().toString()));
			click04.setOnClickListener(new clickSelectStartYear(click04
					.getText().toString()));
			click05.setOnClickListener(new clickSelectStartYear(click05
					.getText().toString()));
			click06.setOnClickListener(new clickSelectStartYear(click06
					.getText().toString()));
			click07.setOnClickListener(new clickSelectStartYear(click07
					.getText().toString()));
			click08.setOnClickListener(new clickSelectStartYear(click08
					.getText().toString()));
			click09.setOnClickListener(new clickSelectStartYear(click09
					.getText().toString()));
			click10.setOnClickListener(new clickSelectStartYear(click10
					.getText().toString()));
			click11.setOnClickListener(new clickSelectStartYear(click11
					.getText().toString()));
			click12.setOnClickListener(new clickSelectStartYear(click12
					.getText().toString()));
			click13.setOnClickListener(new clickSelectStartYear(click13
					.getText().toString()));
			click14.setOnClickListener(new clickSelectStartYear(click14
					.getText().toString()));
			click15.setOnClickListener(new clickSelectStartYear(click15
					.getText().toString()));
			click16.setOnClickListener(new clickSelectStartYear(click16
					.getText().toString()));
			click17.setOnClickListener(new clickSelectStartYear(click17
					.getText().toString()));
			click18.setOnClickListener(new clickSelectStartYear(click18
					.getText().toString()));
			click19.setOnClickListener(new clickSelectStartYear(click19
					.getText().toString()));
			click20.setOnClickListener(new clickSelectStartYear(click20
					.getText().toString()));
			clickNow.setOnClickListener(new clickSelectStartYear(clickNow
					.getText().toString()));

		}

		@Override
		public void onClick(View view) {
			LayoutInflater factory = (LayoutInflater) PersonalCVWorkInputActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_select_year, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			setWindow(540, 600);
			initYearSelectView(myView);
		}

	}

	private class clickSelectEndYear implements OnClickListener {
		private String yearSelected;

		public clickSelectEndYear(String year) {
			yearSelected = year;
		}

		@Override
		public void onClick(View view) {
			yearEnd.setText(yearSelected);
			dialog.dismiss();
		}

	}

	private class SelectEndYear implements OnClickListener {
		private Context context;

		public SelectEndYear(Context ctx) {
			context = ctx;
		}

		private void initYearSelectView(View myView) {
			Button click95 = (Button) myView
					.findViewById(R.id.personal_cv_year_95);
			Button click96 = (Button) myView
					.findViewById(R.id.personal_cv_year_96);
			Button click97 = (Button) myView
					.findViewById(R.id.personal_cv_year_97);
			Button click98 = (Button) myView
					.findViewById(R.id.personal_cv_year_98);
			Button click99 = (Button) myView
					.findViewById(R.id.personal_cv_year_99);
			Button click00 = (Button) myView
					.findViewById(R.id.personal_cv_year_00);
			Button click01 = (Button) myView
					.findViewById(R.id.personal_cv_year_01);
			Button click02 = (Button) myView
					.findViewById(R.id.personal_cv_year_02);
			Button click03 = (Button) myView
					.findViewById(R.id.personal_cv_year_03);
			Button click04 = (Button) myView
					.findViewById(R.id.personal_cv_year_04);
			Button click05 = (Button) myView
					.findViewById(R.id.personal_cv_year_05);
			Button click06 = (Button) myView
					.findViewById(R.id.personal_cv_year_06);
			Button click07 = (Button) myView
					.findViewById(R.id.personal_cv_year_07);
			Button click08 = (Button) myView
					.findViewById(R.id.personal_cv_year_08);
			Button click09 = (Button) myView
					.findViewById(R.id.personal_cv_year_09);
			Button click10 = (Button) myView
					.findViewById(R.id.personal_cv_year_10);
			Button click11 = (Button) myView
					.findViewById(R.id.personal_cv_year_11);
			Button click12 = (Button) myView
					.findViewById(R.id.personal_cv_year_12);
			Button click13 = (Button) myView
					.findViewById(R.id.personal_cv_year_13);
			Button click14 = (Button) myView
					.findViewById(R.id.personal_cv_year_14);
			Button click15 = (Button) myView
					.findViewById(R.id.personal_cv_year_15);
			Button click16 = (Button) myView
					.findViewById(R.id.personal_cv_year_16);
			Button click17 = (Button) myView
					.findViewById(R.id.personal_cv_year_17);
			Button click18 = (Button) myView
					.findViewById(R.id.personal_cv_year_18);
			Button click19 = (Button) myView
					.findViewById(R.id.personal_cv_year_19);
			Button click20 = (Button) myView
					.findViewById(R.id.personal_cv_year_20);
			Button clickNow = (Button) myView
					.findViewById(R.id.personal_cv_year_now);
			click95.setOnClickListener(new clickSelectEndYear(click95.getText()
					.toString()));
			click96.setOnClickListener(new clickSelectEndYear(click96.getText()
					.toString()));
			click97.setOnClickListener(new clickSelectEndYear(click97.getText()
					.toString()));
			click98.setOnClickListener(new clickSelectEndYear(click98.getText()
					.toString()));
			click99.setOnClickListener(new clickSelectEndYear(click99.getText()
					.toString()));
			click00.setOnClickListener(new clickSelectEndYear(click00.getText()
					.toString()));
			click01.setOnClickListener(new clickSelectEndYear(click01.getText()
					.toString()));
			click02.setOnClickListener(new clickSelectEndYear(click02.getText()
					.toString()));
			click03.setOnClickListener(new clickSelectEndYear(click03.getText()
					.toString()));
			click04.setOnClickListener(new clickSelectEndYear(click04.getText()
					.toString()));
			click05.setOnClickListener(new clickSelectEndYear(click05.getText()
					.toString()));
			click06.setOnClickListener(new clickSelectEndYear(click06.getText()
					.toString()));
			click07.setOnClickListener(new clickSelectEndYear(click07.getText()
					.toString()));
			click08.setOnClickListener(new clickSelectEndYear(click08.getText()
					.toString()));
			click09.setOnClickListener(new clickSelectEndYear(click09.getText()
					.toString()));
			click10.setOnClickListener(new clickSelectEndYear(click10.getText()
					.toString()));
			click11.setOnClickListener(new clickSelectEndYear(click11.getText()
					.toString()));
			click12.setOnClickListener(new clickSelectEndYear(click12.getText()
					.toString()));
			click13.setOnClickListener(new clickSelectEndYear(click13.getText()
					.toString()));
			click14.setOnClickListener(new clickSelectEndYear(click14.getText()
					.toString()));
			click15.setOnClickListener(new clickSelectEndYear(click15.getText()
					.toString()));
			click16.setOnClickListener(new clickSelectEndYear(click16.getText()
					.toString()));
			click17.setOnClickListener(new clickSelectEndYear(click17.getText()
					.toString()));
			click18.setOnClickListener(new clickSelectEndYear(click18.getText()
					.toString()));
			click19.setOnClickListener(new clickSelectEndYear(click19.getText()
					.toString()));
			click20.setOnClickListener(new clickSelectEndYear(click20.getText()
					.toString()));
			clickNow.setOnClickListener(new clickSelectEndYear(clickNow
					.getText().toString()));
		}

		@Override
		public void onClick(View view) {
			LayoutInflater factory = (LayoutInflater) PersonalCVWorkInputActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_select_year, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			setWindow(540, 600);
			initYearSelectView(myView);
		}

	}

	private class backClick implements OnClickListener {
		private Context context;

		public backClick(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			if (setWorkInfo()) {
				utilSrvc.gotoPersonalWorkList(context, workInfo);
			}
		}

	}

	private class deleteClick implements OnClickListener {
		private Context context;

		public deleteClick(Context ctx) {
			context = ctx;
		}

		private void deleteWorks() {
			int startPosition = (position + 1) * 3 - 1;
			works.remove(startPosition);
			works.remove(startPosition - 1);
			works.remove(startPosition - 2);
			workInfo.setWorks(works);
		}

		@Override
		public void onClick(View view) {
			deleteWorks();
			utilSrvc.gotoPersonalWorkList(context, workInfo);
		}

	}
}
