package com.offerme.client.activity;

import java.util.ArrayList;
import java.util.List;

import com.offerme.R;
import com.offerme.client.localdata.LocalDataBase;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.cv.PersonalCV;
import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ChatCVListActivity extends ActionBarActivity {

	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private LocalDataBase localDataBase = LocalDataBase.getInstance(this);
	private List<PersonalCV> allCV = new ArrayList<PersonalCV>();
	private BaseAdapter cvAdapter = new cvListView();
	private ListView cvList;
	private Context context;
	private Button deleteButton;
	private String mPageName = "ChatCVListActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cv_list);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar()
				.setCustomView(R.layout.activity_cv_list_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
		context = this;
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
		initView();
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
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		this.setIntent(intent);
	}

	@Override
	public void onBackPressed() {
		utilSrvc.gotoLoggedInFromChat(this);
		return;
	}

	public void backToFriendList(View view) {
		utilSrvc.gotoLoggedInFromChat(this);
	}

	public void initView() {
		cvList = (ListView) findViewById(R.id.cv_received_list);
		cvList.setAdapter(cvAdapter);
	}

	public void loadData() {
		allCV = localDataBase.findAllCVs();
	}

	private class touchListener implements OnTouchListener {

		private int position;
		private float downPosition = 0;
		private float upPosition = 0;
		private float movePosition = 0;
		private Button deleteCV;

		public touchListener(Button button, int pos) {
			position = pos;
			deleteCV = button;
		}

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downPosition = event.getX();
				if (deleteButton != null
						&& deleteButton.getVisibility() == View.VISIBLE) {
					deleteButton.setVisibility(View.GONE);
					doAnimation(context, deleteButton, R.anim.deleteout);
				}
				deleteButton = deleteCV;
				break;
			case MotionEvent.ACTION_UP:
				upPosition = event.getX();
				break;
			case MotionEvent.ACTION_MOVE:
				movePosition = event.getX();
				if (downPosition - movePosition > 10) {
					deleteButton.setVisibility(View.VISIBLE);
					doAnimation(context, deleteButton, R.anim.deletein);
					return true;
				} else if (movePosition - downPosition > 10) {
					return true;
				} else {
					return true;
				}
			default:
				break;
			}
			if (upPosition == 0) {
				return true;
			} else if (upPosition == downPosition) {
				utilSrvc.gotoPersonalCVFromReceived(context,
						allCV.get(position));
			}
			return true;
		}

	}

	private void doAnimation(Context context, View view, int animId) {
		Animation animation = AnimationUtils.loadAnimation(context, animId);
		view.startAnimation(animation);
	}

	private class DeleteListener implements OnClickListener {

		private int position;

		public DeleteListener(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View view) {
			TextView deleteButton = (TextView) view
					.findViewById(R.id.cv_apply_delete);
			deleteButton.setVisibility(View.GONE);
			localDataBase.deleteCV(
					Integer.parseInt(allCV.get(position).getUserID()),
					Integer.parseInt(allCV.get(position).getPostID()));
			allCV.remove(position);
			cvAdapter.notifyDataSetChanged();
		}
	}

	private class cvListView extends BaseAdapter {

		@Override
		public int getCount() {
			return allCV.size();
		}

		@Override
		public Object getItem(int position) {
			return allCV.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			PersonalCV cv = (PersonalCV) this.getItem(position);
			if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(
						R.layout.activity_cv_list_item, null);
			}
			ImageView profile = (ImageView) convertView
					.findViewById(R.id.cv_apply_profile);
			TextView name = (TextView) convertView
					.findViewById(R.id.cv_apply_name);
			TextView post = (TextView) convertView
					.findViewById(R.id.cv_apply_post);
			TextView date = (TextView) convertView
					.findViewById(R.id.cv_apply_date_text);
			Button delete = (Button) convertView
					.findViewById(R.id.cv_apply_delete);
			name.setText(cv.getName());
			post.setText(cv.getPostApplied());
			date.setText(cv.getDate());
			if (cv.getProfile() != null) {
				Bitmap imageBitmap = BitmapFactory.decodeByteArray(
						cv.getProfile(), 0, cv.getProfile().length);
				imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 80, 80,
						true);
				profile.setImageBitmap(imageBitmap);
			}
			convertView.setOnTouchListener(new touchListener(delete, position));
			delete.setOnClickListener(new DeleteListener(position));
			return convertView;
		}

	}

}
