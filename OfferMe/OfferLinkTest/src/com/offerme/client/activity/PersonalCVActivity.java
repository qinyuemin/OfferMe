package com.offerme.client.activity;

import java.util.ArrayList;

import com.offerme.R;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.SaveCVSrvc;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.chat.ChatFriend;
import com.offerme.client.service.cv.PersonalCV;
import com.offerme.client.service.cv.PersonalWorkInfo;
import com.umeng.analytics.MobclickAgent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonalCVActivity extends ActionBarActivity {
	public final static String FROM_NONCV = "FromNOCV";
	public final static String FROM_CVRECEIVED = "FromCVReceived";
	public final static String WORK_INFO_CV = "WorkInfoCV";
	private Intent intent;
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private LocalPersonInfo personInfo = LocalPersonInfo.getInstance(this);
	private SaveCVSrvc saveSrvc = SaveCVSrvc.getInstance(utilSrvc);
	private PersonalWorkInfo works = new PersonalWorkInfo();
	private PersonalCV cv = new PersonalCV();
	private PersonalCV intentCV = null;
	private TextView title;
	private ImageView saveCV;
	private ImageView profile;
	private ImageView detailArrow;
	private ImageView sendMail;
	private TextView name;
	private TextView age;
	private TextView post;
	private TextView city;
	private TextView entreprise;
	private TextView workyear;
	private TextView workyearText;
	private TextView educationText;
	private TextView educationSpinner;
	private TextView colleageText;
	private EditText colleageEdit;
	private TextView firstWork;
	private TextView firstWorkyear;
	private TextView firstWorkPost;
	private TextView secondWork;
	private TextView secondWorkyear;
	private TextView secondWorkPost;
	private TextView thirdWork;
	private TextView thirdWorkyear;
	private TextView thirdWorkPost;
	private TextView firstAnneeText;
	private TextView secondAnneeText;
	private TextView thirdAnneeText;
	private TextView firstSeperator;
	private TextView secondeSeperator;
	private RelativeLayout workInputLayout;
	private FrameLayout mailLayout;
	private Dialog dialog = null;
	private String mPageName = "PersonalCVActivity";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		setContentView(R.layout.activity_personal_cv);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.activity_personal_cv_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
		initWidget();
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
		back();
		return;
	}

	public void backClick(View view) {
		back();
	}

	private void back() {
		if (intent.getBooleanExtra(FROM_NONCV, false)) {
			utilSrvc.gotoLoggedInFromUser(this);
		} else {
			utilSrvc.gotoChatCVList(this);
		}
	}

	private void initWidget() {
		intent = this.getIntent();
		title = (TextView) findViewById(R.id.personal_cv_bar_center);
		saveCV = (ImageView) findViewById(R.id.personal_cv_bar_right);
		profile = (ImageView) findViewById(R.id.personal_cv_profile);
		detailArrow = (ImageView) findViewById(R.id.personal_cv_detail_arrow);
		sendMail = (ImageView) findViewById(R.id.personal_cv_mail_send);
		name = (TextView) findViewById(R.id.personal_cv_name);
		age = (TextView) findViewById(R.id.personal_cv_age);
		post = (TextView) findViewById(R.id.personal_cv_post);
		city = (TextView) findViewById(R.id.personal_cv_city);
		entreprise = (TextView) findViewById(R.id.personal_cv_enterprise);
		workyear = (TextView) findViewById(R.id.personal_cv_workyear);
		workyearText = (TextView) findViewById(R.id.personal_cv_workyear_text);
		educationText = (TextView) findViewById(R.id.personal_cv_education_text);
		educationSpinner = (TextView) findViewById(R.id.personal_cv_education_spinner);
		colleageText = (TextView) findViewById(R.id.personal_cv_colleage_text);
		colleageEdit = (EditText) findViewById(R.id.personal_cv_colleage_edit);
		firstWork = (TextView) findViewById(R.id.personal_cv_first_work);
		firstWorkyear = (TextView) findViewById(R.id.personal_cv_first_work_year);
		firstWorkPost = (TextView) findViewById(R.id.personal_cv_first_work_post);
		secondWork = (TextView) findViewById(R.id.personal_cv_second_work);
		secondWorkyear = (TextView) findViewById(R.id.personal_cv_second_work_year);
		secondWorkPost = (TextView) findViewById(R.id.personal_cv_second_work_post);
		thirdWork = (TextView) findViewById(R.id.personal_cv_third_work);
		thirdWorkyear = (TextView) findViewById(R.id.personal_cv_third_work_year);
		thirdWorkPost = (TextView) findViewById(R.id.personal_cv_third_work_post);
		firstAnneeText = (TextView) findViewById(R.id.personal_cv_first_text);
		secondAnneeText = (TextView) findViewById(R.id.personal_cv_second_text);
		thirdAnneeText = (TextView) findViewById(R.id.personal_cv_third_text);
		firstSeperator = (TextView) findViewById(R.id.personal_cv_first_work_seperator);
		secondeSeperator = (TextView) findViewById(R.id.personal_cv_second_work_seperator);
		workInputLayout = (RelativeLayout) findViewById(R.id.personal_cv_workinput_layout);
		mailLayout = (FrameLayout) findViewById(R.id.personal_cv_mail_layout);
		educationSpinner.setOnClickListener(new SelectEducation(this));
		firstWork.setVisibility(View.GONE);
		firstWorkyear.setVisibility(View.GONE);
		firstWorkPost.setVisibility(View.GONE);
		secondWork.setVisibility(View.GONE);
		secondWorkyear.setVisibility(View.GONE);
		secondWorkPost.setVisibility(View.GONE);
		thirdWork.setVisibility(View.GONE);
		thirdWorkyear.setVisibility(View.GONE);
		thirdWorkPost.setVisibility(View.GONE);
		firstSeperator.setVisibility(View.GONE);
		secondeSeperator.setVisibility(View.GONE);
	}

	private void loadData() {
		intentCV = (PersonalCV) intent.getSerializableExtra(FROM_CVRECEIVED);
		initActionBar();
		initPersonInfo();
		initWorkYear();
		initEducation();
		initColleage();
		initWork();
		intentCV = null;
	}

	private void initActionBar() {
		if (intent.getBooleanExtra(FROM_NONCV, false)) {
			title.setText(this.getResources()
					.getText(R.string.personal_cv_mine));
			saveCV.setVisibility(View.VISIBLE);
		} else {
			title.setText(this.getResources().getText(
					R.string.personal_cv_received));
			saveCV.setVisibility(View.GONE);
		}
	}

	private void initPersonInfo() {
		if (intent.getBooleanExtra(FROM_NONCV, false)) {
			name.setText(personInfo.getValue(LocalPersonInfo.NAME));
			age.setText(personInfo.getValue(LocalPersonInfo.AGE));
			post.setText(personInfo.getValue(LocalPersonInfo.POST));
			city.setText(personInfo.getValue(LocalPersonInfo.CITY));
			entreprise.setText(personInfo.getValue(LocalPersonInfo.ENTREPRISE));
			String img = personInfo.getValue(LocalPersonInfo.PROFILE);
			if (img != null) {
				byte[] imgArray = Base64.decode(img, Base64.DEFAULT);
				Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgArray, 0,
						imgArray.length);
				imgBitmap = Bitmap.createScaledBitmap(imgBitmap, 80, 80, true);
				profile.setImageBitmap(imgBitmap);
			}
		} else if (intentCV != null) {
			name.setText(intentCV.getName());
			age.setText(intentCV.getAge());
			post.setText(intentCV.getPost());
			city.setText(intentCV.getCity());
			entreprise.setText(intentCV.getEntreprise());
			byte[] img = intentCV.getProfile();
			if (img != null) {
				Bitmap imgBitmap = BitmapFactory.decodeByteArray(img, 0,
						img.length);
				imgBitmap = Bitmap.createScaledBitmap(imgBitmap, 80, 80, true);
				profile.setImageBitmap(imgBitmap);
			}
		}
	}

	private void initWorkYear() {
		String year = null;
		workyearText.setVisibility(View.VISIBLE);
		if (intent.getBooleanExtra(FROM_NONCV, false)) {
			year = personInfo.getValue(LocalPersonInfo.WORKYEAR);
		} else if (intentCV != null) {
			year = intentCV.getWorkyear();
		}
		if (year != null) {
			if (year.equalsIgnoreCase("0")) {
				workyear.setText("应届生");
				workyearText.setVisibility(View.GONE);
			} else {
				workyear.setText(year);
			}
		}
	}

	private void initEducation() {
		String education = null;
		if (intent.getBooleanExtra(FROM_NONCV, false)) {
			educationText.setVisibility(View.GONE);
			educationSpinner.setVisibility(View.VISIBLE);
			education = personInfo.getValue(LocalPersonInfo.EDUCATION);
			educationSpinner.setText(education);
		} else if (intentCV != null) {
			educationSpinner.setVisibility(View.GONE);
			educationText.setVisibility(View.VISIBLE);
			education = intentCV.getEducation();
			educationText.setText(education);
		}
	}

	private void initColleage() {
		if (intent.getBooleanExtra(FROM_NONCV, false)) {
			colleageText.setVisibility(View.GONE);
			colleageEdit.setVisibility(View.VISIBLE);
			String colleage = personInfo.getValue(LocalPersonInfo.COLLEAGE);
			if (colleage != null) {
				colleageEdit.setText(colleage);
				colleageEdit.setSelection(colleage.length());
			}
		} else if (intentCV != null) {
			colleageEdit.setVisibility(View.GONE);
			colleageText.setVisibility(View.VISIBLE);
			colleageText.setText(intentCV.getColleage());
		}
	}

	private void initWork() {
		firstAnneeText.setVisibility(View.GONE);
		secondAnneeText.setVisibility(View.GONE);
		thirdAnneeText.setVisibility(View.GONE);
		firstWork.setVisibility(View.GONE);
		firstWorkyear.setVisibility(View.GONE);
		firstWorkPost.setVisibility(View.GONE);
		secondWork.setVisibility(View.GONE);
		secondWorkyear.setVisibility(View.GONE);
		secondWorkPost.setVisibility(View.GONE);
		thirdWork.setVisibility(View.GONE);
		thirdWorkyear.setVisibility(View.GONE);
		mailLayout.setVisibility(View.VISIBLE);
		detailArrow.setVisibility(View.VISIBLE);
		if (intent.getBooleanExtra(FROM_NONCV, false)) {
			mailLayout.setVisibility(View.GONE);
			PersonalWorkInfo workInfo = (PersonalWorkInfo) intent
					.getSerializableExtra(WORK_INFO_CV);
			ArrayList<String> worklist = new ArrayList<String>();
			if (workInfo != null) {
				ArrayList<String> worksfrominput = workInfo.getWorks();
				worklist = setWorkExperience(worksfrominput);
				workInfo.setWorks(worklist);
				works.setWorks(worklist);
			} else {
				worklist = setWorkExperience(personInfo.getWorkList());
				works.setWorks(worklist);
			}
			workInputLayout.setOnClickListener(new gotoWorkList(this));

		} else if (intentCV != null) {
			detailArrow.setVisibility(View.GONE);
			mailLayout.setVisibility(View.VISIBLE);
			ArrayList<String> data = new ArrayList<String>();
			if (intentCV.getWorkInfo().getFirstWork() != null) {
				data.add(intentCV.getWorkInfo().getFirstWork());
				data.add(intentCV.getWorkInfo().getFirstWorkyear());
				data.add(intentCV.getWorkInfo().getFirstWorkPost());
			}
			if (intentCV.getWorkInfo().getSecondWork() != null) {
				data.add(intentCV.getWorkInfo().getSecondWork());
				data.add(intentCV.getWorkInfo().getSecondWorkyear());
				data.add(intentCV.getWorkInfo().getSecondWorkPost());
			}
			if (intentCV.getWorkInfo().getThirdWork() != null) {
				data.add(intentCV.getWorkInfo().getThirdWork());
				data.add(intentCV.getWorkInfo().getThirdWorkyear());
				data.add(intentCV.getWorkInfo().getThirdWorkPost());
			}
			setWorkExperience(data);
			sendMail.setOnClickListener(new gotoChatPage(this, intentCV));
		}
	}

	private ArrayList<String> setWorkExperience(ArrayList<String> initData) {
		ArrayList<String> worklist = new ArrayList<String>();
		firstAnneeText.setVisibility(View.GONE);
		secondAnneeText.setVisibility(View.GONE);
		thirdAnneeText.setVisibility(View.GONE);
		for (int count = 0; count < initData.size();) {
			if (count < 3) {
				firstWork.setText(initData.get(count));
				firstWorkyear.setText(initData.get(count + 1));
				firstWorkPost.setText(initData.get(count + 2));
				firstWork.setVisibility(View.VISIBLE);
				firstWorkyear.setVisibility(View.VISIBLE);
				firstWorkPost.setVisibility(View.VISIBLE);
				firstAnneeText.setVisibility(View.VISIBLE);
			} else if (count < 6) {
				secondWork.setText(initData.get(count));
				secondWorkyear.setText(initData.get(count + 1));
				secondWorkPost.setText(initData.get(count + 2));
				secondWork.setVisibility(View.VISIBLE);
				secondWorkyear.setVisibility(View.VISIBLE);
				secondWorkPost.setVisibility(View.VISIBLE);
				firstSeperator.setVisibility(View.VISIBLE);
				secondAnneeText.setVisibility(View.VISIBLE);
			} else if (count < 9) {
				thirdWork.setText(initData.get(count));
				thirdWorkyear.setText(initData.get(count + 1));
				thirdWorkPost.setText(initData.get(count + 2));
				thirdWork.setVisibility(View.VISIBLE);
				thirdWorkyear.setVisibility(View.VISIBLE);
				thirdWorkPost.setVisibility(View.VISIBLE);
				secondeSeperator.setVisibility(View.VISIBLE);
				thirdAnneeText.setVisibility(View.VISIBLE);
			}
			worklist.add(initData.get(count));
			worklist.add(initData.get(count + 1));
			worklist.add(initData.get(count + 2));
			count = count + 3;
		}
		return worklist;
	}

	public void SaveCV(View view) {
		if (utilSrvc.isNetworkConnected(this)) {
			cv.setUserID(personInfo.getValue(LocalPersonInfo.USERID));
			cv.setName(String.valueOf(name.getText()));
			cv.setAge(String.valueOf(age.getText()));
			cv.setPost(String.valueOf(post.getText()));
			cv.setEntreprise(String.valueOf(entreprise.getText()));
			cv.setEducation(getEducation());
			cv.setColleage(getColleage());
			cv.setWorkInfo(works);
			if (workyear.getText().toString().equalsIgnoreCase("应届生")) {
				cv.setWorkyear("0");
			} else {
				cv.setWorkyear(String.valueOf(workyear.getText()));
			}
			if (saveSrvc.checkInputInfo(this, cv)) {
				saveSrvc.save();
			}
		} else {
			utilSrvc.showConnectDialog(this);
		}
	}

	private String getEducation() {
		if (intent.getBooleanExtra(FROM_NONCV, false)) {
			return String.valueOf(educationSpinner.getText());
		} else {
			return String.valueOf(educationText.getText());
		}
	}

	private String getColleage() {
		if (intent.getBooleanExtra(FROM_NONCV, false)) {
			return String.valueOf(colleageEdit.getText());
		} else {
			return String.valueOf(colleageText.getText());
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

	private class clickSelectEducation implements OnClickListener {

		private String educationLevel;

		public clickSelectEducation(String level) {
			educationLevel = level;
		}

		@Override
		public void onClick(View view) {
			educationSpinner.setText(educationLevel);
			dialog.dismiss();
		}
	}

	private class SelectEducation implements OnClickListener {
		private Context context;

		public SelectEducation(Context ctx) {
			context = ctx;
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
		public void onClick(View view) {
			LayoutInflater factory = (LayoutInflater) PersonalCVActivity.this
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

	private class gotoWorkList implements OnClickListener {
		private Context context;

		public gotoWorkList(Context ctx) {
			context = ctx;
		}

		@Override
		public void onClick(View view) {
			if (intent.getBooleanExtra(FROM_NONCV, false)) {
				utilSrvc.gotoPersonalWorkList(context, works);
			}
		}
	}

	private class gotoChatPage implements OnClickListener {
		private Context context;
		private PersonalCV personCV;

		public gotoChatPage(Context ctx, PersonalCV cv) {
			context = ctx;
			personCV = cv;
		}

		@Override
		public void onClick(View view) {
			if (personCV != null) {
				ChatFriend friend = new ChatFriend();
				friend.setFriendId(Integer.parseInt(personCV.getUserID()));
				friend.setName(personCV.getName());
				friend.setPortrait(personCV.getProfile());
				friend.setPortraitVersion(-1);
				utilSrvc.gotoChatPageFromCV(context, friend, personCV);
			}
		}
	}
}
