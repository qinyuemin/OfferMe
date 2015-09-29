package com.offerme.client.activity;

import java.util.ArrayList;

import com.offerme.R;
import com.offerme.client.service.DeleteOfferSrvc;
import com.offerme.client.service.PersonalSettingSrvc;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.personalsetting.PersonalSetting;
import com.offerme.client.service.publishoffer.OfferInfo;
import com.offerme.client.service.publishoffer.PublisherInfo;
import com.offerme.client.service.search.SearchResulatItem;
import com.umeng.analytics.MobclickAgent;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class PersonalSettingActivity extends Fragment {

	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private DeleteOfferSrvc deleteOffer = DeleteOfferSrvc.getInstance();
	private PersonalSettingSrvc personalSettingSrvc = PersonalSettingSrvc
			.getInstance();
	private PersonalSetting data;
	private Context context;
	private TabHost offerTab;
	private int[] tabs = { R.id.personal_setting_offer_favorite,
			R.id.personal_setting_offer_applied };
	private ImageView profileImg;
	private ImageView settingImg;
	private Button deleteButton;
	private TextView name;
	private TextView city;
	private TextView enterprise;
	private TextView email;
	private TextView post;
	private ListView favoriteOfferList;
	private ListView appliedOfferList;
	private BaseAdapter favoriteAdapter;
	private BaseAdapter appliedAdapter;
	private RelativeLayout layout;
	private LinearLayout infoLayout;
	private ArrayList<OfferInfo> favoriteInDataBase = new ArrayList<OfferInfo>();
	private ArrayList<OfferInfo> appliedInDataBase = new ArrayList<OfferInfo>();
	private int activeColor = Color.parseColor("#1e90d2");
	private int inactiveColor = Color.parseColor("#FFFFFF");
	private float downPosition;
	private float upPosition;
	private float movePosition;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_personal_setting, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = this.getActivity();
		initWidget();
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this.getActivity());
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this.getActivity());
		loadData();
		if (favoriteAdapter != null) {
			favoriteAdapter.notifyDataSetChanged();
		}
		if (appliedAdapter != null) {
			appliedAdapter.notifyDataSetChanged();
		}
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		this.getActivity().onWindowFocusChanged(hasFocus);
	}

	private class gotoPersonalCV implements OnClickListener {
		@Override
		public void onClick(View view) {
			utilSrvc.gotoPersonalCVFromPerson(context);
		}
	}

	private class gotoPersonalInfo implements OnClickListener {
		@Override
		public void onClick(View view) {
			utilSrvc.gotoPersonalInfoPage(context);
		}
	}

	private class gotoSetting implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			utilSrvc.gotoSettingPage(context);
		}
	}

	private void initWidget() {
		initActionBar();
		layout = (RelativeLayout) getActivity().findViewById(R.id.ps_to_cv);
		infoLayout = (LinearLayout) getActivity().findViewById(
				R.id.ps_info_layout);
		profileImg = (ImageView) getActivity().findViewById(R.id.ps_profile);
		name = (TextView) getActivity().findViewById(R.id.ps_name);
		post = (TextView) getActivity().findViewById(R.id.ps_post);
		city = (TextView) getActivity().findViewById(R.id.ps_city);
		enterprise = (TextView) getActivity().findViewById(R.id.ps_enterprise);
		email = (TextView) getActivity().findViewById(R.id.ps_email);
		offerTab = (TabHost) getActivity().findViewById(R.id.tabhost);
		favoriteOfferList = (ListView) getActivity().findViewById(
				R.id.personal_setting_offerList_favorite);
		appliedOfferList = (ListView) getActivity().findViewById(
				R.id.personal_setting_offerList_applied);
		offerTab.setup();
		offerTab.addTab(offerTab
				.newTabSpec("OfferFavorite")
				.setIndicator(
						this.getString(R.string.setting_person_favorite_offer))
				.setContent(tabs[0]));
		offerTab.addTab(offerTab
				.newTabSpec("OfferPublished")
				.setIndicator(
						this.getString(R.string.setting_person_applied_offer))
				.setContent(tabs[1]));
		offerTab.setCurrentTab(0);
		offerTab.getTabWidget().getChildAt(0).setBackgroundColor(activeColor);
		offerTab.getTabWidget().getChildAt(1).setBackgroundColor(inactiveColor);
		TextView tv0 = (TextView) offerTab.getTabWidget().getChildAt(0)
				.findViewById(android.R.id.title);
		TextView tv1 = (TextView) offerTab.getTabWidget().getChildAt(1)
				.findViewById(android.R.id.title);
		tv0.setTextColor(inactiveColor);
		tv0.setTextSize(15);
		tv1.setTextColor(activeColor);
		tv1.setTextSize(15);
		offerTab.setOnTabChangedListener(new tabchangeListener());
		favoriteOfferList.setAdapter(new favoriteListListener());
		appliedOfferList.setAdapter(new appliedListListener());
		favoriteAdapter = (BaseAdapter) favoriteOfferList.getAdapter();
		appliedAdapter = (BaseAdapter) appliedOfferList.getAdapter();
		layout.setOnClickListener(new gotoPersonalCV());
		infoLayout.setOnClickListener(new gotoPersonalInfo());
		personalSettingSrvc.setAppliedAdapter(appliedAdapter);
		personalSettingSrvc.setFavoriteAdapter(favoriteAdapter);
	}

	public void initActionBar() {
		settingImg = (ImageView) this.getActivity().findViewById(
				R.id.home_page_bar_right);
		settingImg.setOnClickListener(new gotoSetting());
	}

	private void loadData() {
		data = personalSettingSrvc.loadLocalPersonalSetting(this.getActivity());
		name.setText(data.getName());
		post.setText(data.getPost());
		city.setText(data.getCity());
		enterprise.setText(data.getEnterprise());
		email.setText(data.getEmail());
		if (data.getProfile() != null) {
			Bitmap imageBitmap = BitmapFactory.decodeByteArray(
					data.getProfile(), 0, data.getProfile().length);
			imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 80, 80, true);
			profileImg.setImageBitmap(imageBitmap);
		} else {
			Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.dafaultuser);
			imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 80, 80, true);
			profileImg.setImageBitmap(imageBitmap);
		}
		favoriteInDataBase = data.getFavoriteList();
		appliedInDataBase = data.getApplyList();
	}

	private void doAnimation(Context context, View view, int animId) {
		Animation animation = AnimationUtils.loadAnimation(context, animId);
		view.startAnimation(animation);
	}

	private class tabchangeListener implements OnTabChangeListener {

		@Override
		public void onTabChanged(String tabID) {

			for (int i = 0; i < tabs.length; i++) {
				offerTab.getTabWidget().getChildAt(i)
						.setBackgroundColor(inactiveColor);
				TextView tv = (TextView) offerTab.getTabWidget().getChildAt(i)
						.findViewById(android.R.id.title);
				tv.setTextColor(activeColor);
			}
			offerTab.getTabWidget().getChildAt(offerTab.getCurrentTab())
					.setBackgroundColor(activeColor);
			TextView tv = (TextView) offerTab.getTabWidget()
					.getChildAt(offerTab.getCurrentTab())
					.findViewById(android.R.id.title);
			tv.setTextColor(inactiveColor);
		}
	}

	private class favoriteListListener extends BaseAdapter {

		@Override
		public int getCount() {
			return favoriteInDataBase.size();
		}

		@Override
		public Object getItem(int position) {
			return favoriteInDataBase.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			OfferInfo offer = (OfferInfo) this.getItem(position);
			if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(
						R.layout.activity_personal_setting_favorite_list, null);
			}
			TextView title = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_favorite_title);
			TextView date = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_favorite_date);
			TextView city = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_favorite_city);
			TextView entreprise = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_favorite_entreprise);
			TextView domain = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_favorite_domain);
			TextView salary = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_favorite_salary);
			Button delete = (Button) convertView
					.findViewById(R.id.personal_setting_offer_favorite_delete);
			title.setText(offer.getPost());
			date.setText(offer.getDate());
			city.setText(offer.getCity());
			entreprise.setText(offer.getEntreprise());
			domain.setText(offer.getDomain());
			salary.setText(offer.getSalary());
			convertView.setOnTouchListener(new offerDelete(delete, position,
					true));
			delete.setOnClickListener(new deleteClick(offer, String
					.valueOf(offer.getOfferOwnerID()),
					DeleteOfferSrvc.FOR_FAVORITE));
			return convertView;
		}
	}

	private class appliedListListener extends BaseAdapter {

		@Override
		public int getCount() {
			return appliedInDataBase.size();
		}

		@Override
		public Object getItem(int position) {
			return appliedInDataBase.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			OfferInfo offer = (OfferInfo) this.getItem(position);
			if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(
						R.layout.activity_personal_setting_offer_list, null);
			}
			TextView title = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_apply_title);
			TextView date = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_apply_date);
			TextView city = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_apply_city);
			TextView entreprise = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_apply_entreprise);
			TextView domain = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_apply_domain);
			TextView salary = (TextView) convertView
					.findViewById(R.id.personal_setting_offer_apply_salary);
			Button delete = (Button) convertView
					.findViewById(R.id.personal_setting_offer_apply_delete);
			title.setText(offer.getPost());
			date.setText(offer.getDate());
			city.setText(offer.getCity());
			entreprise.setText(offer.getEntreprise());
			domain.setText(offer.getDomain());
			salary.setText(offer.getSalary());
			convertView.setOnTouchListener(new offerDelete(delete, position,
					false));
			delete.setOnClickListener(new deleteClick(offer, String
					.valueOf(offer.getOfferOwnerID()),
					DeleteOfferSrvc.FOR_APPLIED));
			return convertView;
		}
	}

	private class offerDelete implements OnTouchListener {

		private Button deleteOffer;
		private boolean isFavorite;
		private int position;

		public offerDelete(Button delete, int pos, boolean isFavoriteOffer) {
			deleteOffer = delete;
			upPosition = 0;
			isFavorite = isFavoriteOffer;
			position = pos;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downPosition = event.getX();
				if (deleteButton != null
						&& deleteButton.getVisibility() == View.VISIBLE) {
					deleteButton.setVisibility(View.GONE);
					doAnimation(context, deleteButton, R.anim.deleteout);
				}
				deleteButton = deleteOffer;
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
			} else if (downPosition == upPosition) {
				onItemClick(position);
			}
			return true;
		}

		private void onItemClick(int position) {

			if (isFavorite) {
				SearchResulatItem item = convertObject(favoriteInDataBase
						.get(position));
				utilSrvc.gotoPublisherFromPersonalFavorite(context, item);
			} else {
				SearchResulatItem item = convertObject(appliedInDataBase
						.get(position));
				utilSrvc.gotoPublisherFromPersonalPublished(context, item);
			}
		}

		private SearchResulatItem convertObject(OfferInfo info) {
			SearchResulatItem item = new SearchResulatItem("", "", "", "");
			String publisherName = null;
			String mail = null;
			PublisherInfo publisher = new PublisherInfo();
			publisherName = info.getPublisherInfo().getName();
			mail = info.getPublisherInfo().getEmail();
			publisher = info.getPublisherInfo();
			item = new SearchResulatItem(publisherName, info.getEntreprise(),
					info.getCity(), info.getPost());

			if (info.getMailbox() != null && info.getMailbox().length() > 0) {
				item.setMailbox(info.getMailbox());
			} else {
				item.setMailbox(mail);
			}
			item.setDate(info.getDate());
			item.setDescription(info.getDescription());
			item.setDomain(info.getDomain());
			item.setOfferID(info.getOfferID());
			item.setFavorite(info.getFavorite());
			item.setApplied(info.getApplied());
			item.setSalary(info.getSalary());
			item.setTitle(info.getPost());
			item.setWorkyear(info.getWorkyear());
			item.setEducation(info.getEducation());
			item.setPublisherInfo(publisher);
			return item;
		}
	}

	private class deleteClick implements OnClickListener {

		private OfferInfo info;
		private String deleteType;

		public deleteClick(OfferInfo offer, String user, String type) {
			info = offer;
			deleteType = type;
		}

		@Override
		public void onClick(View view) {
			if (utilSrvc.isNetworkConnected(context)) {
				if (deleteButton != null) {
					deleteButton.setVisibility(View.GONE);
				}
				String personID = String.valueOf(data.getUserId());
				if (!deleteType.contains(DeleteOfferSrvc.FOR_APPLIED)) {
					deleteOffer.setOfferDelete(context, info, personID,
							favoriteInDataBase, deleteType);
				} else {
					deleteOffer.setOfferDelete(context, info, personID,
							appliedInDataBase, deleteType);
				}

			} else {
				utilSrvc.showConnectDialog(context);
			}
		}
	}

}
