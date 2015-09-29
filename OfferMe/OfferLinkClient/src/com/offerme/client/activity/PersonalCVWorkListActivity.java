package com.offerme.client.activity;

import java.util.ArrayList;
import java.util.List;

import com.offerme.R;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.cv.PersonalWorkInfo;
import com.umeng.analytics.MobclickAgent;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalCVWorkListActivity extends ActionBarActivity {
	public final static String FROM_PERSONALCV = "FromPersonalCV";
	public final static String FROM_WORKINPUT = "FromWorkInput";
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private PersonalWorkInfo personWorks = new PersonalWorkInfo();
	private List<workItem> workItemList = new ArrayList<workItem>();
	private ListView worklist;
	private FrameLayout backLayout;
	private FrameLayout addLayout;
	private BaseAdapter worklistAdapter;
	private Toast toast;
	private Context context;
	private String mPageName = "PersonalCVWorkListActivity";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		context = this;
		setContentView(R.layout.activity_personal_cv_worklist);
		initActionBar();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
		initWidget();
		loadData();
		worklistAdapter.notifyDataSetChanged();
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

	private void showText(String text) {
		toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	private void back() {
		boolean isComplete = true;
		for (int count = 0; count < workItemList.size(); count++) {
			if (!workItemList.get(count).isComplete()) {
				isComplete = false;
			}
		}
		if (isComplete) {
			utilSrvc.gotoPersonalCVFromWorkList(this, personWorks);
		} else {
			showText("经历填写不完全哦~");
		}
	}

	private class backClick implements OnClickListener {
		@Override
		public void onClick(View view) {
			back();
		}
	}

	private class addWorkClick implements OnClickListener {
		@Override
		public void onClick(View view) {
			if (workItemList.size() >= 3) {
				showText("最多只能添加三段经历哦~");
			} else if (workItemList.size() >= 0) {
				int lastIndex = 0;
				if (workItemList.size() != 0) {
					lastIndex = workItemList.size() - 1;
				}
				if (workItemList.size() == 0
						|| workItemList.get(lastIndex).isComplete()) {
					workItem item = new workItem();
					item.setEntreprise(null);
					item.setPost(null);
					item.setYear(null);
					workItemList.add(item);
					ArrayList<String> info = personWorks.getWorks();
					info.add(null);
					info.add(null);
					info.add(null);
					personWorks.setWorks(info);
					worklistAdapter.notifyDataSetChanged();
					utilSrvc.gotoPersonalWorkInput(context, personWorks,
							workItemList.size() - 1);
				} else {
					showText("上一段经历还没填写完哦~");
				}
			}
		}
	}

	private void initActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.activity_personal_cv_worklist_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
	}

	private void initWidget() {
		backLayout = (FrameLayout) findViewById(R.id.personal_cv_worklist_bar_left_layout);
		addLayout = (FrameLayout) findViewById(R.id.personal_cv_worklist_bar_right_layout);
		worklist = (ListView) findViewById(R.id.personal_cv_worklist_listview);
		worklist.setAdapter(new workListListener());
		worklistAdapter = (BaseAdapter) worklist.getAdapter();
		backLayout.setOnClickListener(new backClick());
		addLayout.setOnClickListener(new addWorkClick());
	}

	private void loadData() {
		workItemList = new ArrayList<workItem>();
		personWorks = (PersonalWorkInfo) getIntent().getSerializableExtra(
				FROM_PERSONALCV);
		if (personWorks != null) {
			ArrayList<String> info = personWorks.getWorks();
			if (info.size() != 0) {
				for (int count = 0; count < info.size();) {
					workItem item = new workItem();
					if (count < 3) {
						item.setEntreprise(info.get(0));
						item.setYear(info.get(1));
						item.setPost(info.get(2));
					} else if (count < 6) {
						item.setEntreprise(info.get(3));
						item.setYear(info.get(4));
						item.setPost(info.get(5));
					} else if (count < 9) {
						item.setEntreprise(info.get(6));
						item.setYear(info.get(7));
						item.setPost(info.get(8));
					}
					workItemList.add(item);
					count = count + 3;
				}
			}
		}
	}

	private class workListListener extends BaseAdapter {

		@Override
		public int getCount() {
			return workItemList.size();
		}

		@Override
		public Object getItem(int position) {
			return workItemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			workItem item = workItemList.get(position);
			if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(
						R.layout.activity_personal_cv_worklist_item, null);
			}
			TextView entreprise = (TextView) convertView
					.findViewById(R.id.personal_cv_worklist_entreprise);
			TextView post = (TextView) convertView
					.findViewById(R.id.personal_cv_worklist_post);
			TextView year = (TextView) convertView
					.findViewById(R.id.personal_cv_worklist_date);
			entreprise.setText(item.getEntreprise());
			post.setText(item.getPost());
			year.setText(item.getYear());
			convertView.setOnClickListener(new gotoWorkInput(position));
			return convertView;
		}

		private class gotoWorkInput implements OnClickListener {
			private int position;

			public gotoWorkInput(int pos) {
				position = pos;
			}

			@Override
			public void onClick(View view) {
				utilSrvc.gotoPersonalWorkInput(context, personWorks, position);
			}
		}
	}

	private class workItem {
		private String entreprise;
		private String post;
		private String year;

		public workItem() {
			entreprise = null;
			post = null;
			year = null;
		}

		public boolean isComplete() {
			if (entreprise == null || post == null || year == null) {
				return false;
			}
			return true;
		}

		public String getEntreprise() {
			return entreprise;
		}

		public void setEntreprise(String entreprise) {
			this.entreprise = entreprise;
		}

		public String getPost() {
			return post;
		}

		public void setPost(String post) {
			this.post = post;
		}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

	}

}
