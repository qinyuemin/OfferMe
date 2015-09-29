package com.offerme.client.activity;

import com.offerme.R;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.chat.ChatFriend;
import com.offerme.client.service.publishoffer.OfferInfo;
import com.offerme.client.service.publishoffer.PublisherInfo;
import com.offerme.client.service.search.SearchResulatItem;
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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PublisherDetailInfoActivity extends ActionBarActivity {

	public final static String PUBLISHER_DETAIL = "PublisherDetail";
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private SearchResulatItem searchResualt;
	private Intent intent = null;
	private TextView publisher;
	private TextView mail;
	private TextView date;
	private TextView post;
	private TextView city;
	private TextView entreprise;
	private TextView salary;
	private TextView domain;
	private ImageView profile;
	private ImageView phone;
	private ImageView sendMail;
	private ListView resultList;
	private Dialog dialog;
	private Context context;
	private LocalPersonInfo info = null;
	private String mPageName = "PublisherDetailInfoActivity";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		setContentView(R.layout.activity_publisher_info);
		intent = this.getIntent();
		info = LocalPersonInfo.getInstance(this);
		initActionBar();
		initView();
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
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		this.setIntent(intent);
		this.intent = intent;
		loadData();
	}

	@Override
	public void onBackPressed() {
		utilSrvc.gotoPublisherInfoPage(this, searchResualt);
		return;
	}

	public void backToPublisherInfo(View view) {
		utilSrvc.gotoPublisherInfoPage(this, searchResualt);
		return;
	}

	private void initActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.activity_publisher_info_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
	}

	private void initView() {
		publisher = (TextView) findViewById(R.id.publisher_name_bar_center);
		profile = (ImageView) findViewById(R.id.publisher_profile);
		mail = (TextView) findViewById(R.id.publisher_mail);
		post = (TextView) findViewById(R.id.publisher_post);
		city = (TextView) findViewById(R.id.publisher_city);
		entreprise = (TextView) findViewById(R.id.publisher_entreprise);
		resultList = (ListView) findViewById(R.id.publisher_item_list);
		phone = (ImageView) findViewById(R.id.publisher_phone_call);
		sendMail = (ImageView) findViewById(R.id.publisher_send_mail);
	}

	private void loadData() {
		searchResualt = (SearchResulatItem) intent
				.getSerializableExtra(PUBLISHER_DETAIL);
		phone.setVisibility(View.VISIBLE);
		mail.setVisibility(View.VISIBLE);
		sendMail.setVisibility(View.VISIBLE);
		publisher.setText(searchResualt.getName());
		mail.setText(searchResualt.getMailbox());
		city.setText(searchResualt.getCity());
		post.setText(searchResualt.getPublisherInfo().getPost());
		entreprise.setText(searchResualt.getEntreprise());
		context = this;
		if (searchResualt.getPublisherInfo().isPhonePublished()
				&& searchResualt.isApplied()) {
			phone.setVisibility(View.VISIBLE);
		} else {
			phone.setVisibility(View.GONE);
		}
		if (!searchResualt.getPublisherInfo().isMailPublished()) {
			mail.setVisibility(View.GONE);
		}
		setProfileImg();
		resultList.setAdapter(new postInfoList());
		if (isMyOffer()) {
			sendMail.setVisibility(View.GONE);
		}
	}

	private boolean isMyOffer() {
		boolean resualt = false;
		String offerOwnerID = String.valueOf(searchResualt.getPublisherInfo()
				.getUserID());
		String personID = info.getValue(LocalPersonInfo.USERID);
		if (offerOwnerID.equalsIgnoreCase(personID)) {
			resualt = true;
		}
		return resualt;
	}

	public void sendMailFromPublisher(View view) {
		PublisherInfo publisherInfo = searchResualt.getPublisherInfo();
		if (publisherInfo != null) {
			ChatFriend friend = new ChatFriend();
			friend.setFriendId(publisherInfo.getUserID());
			friend.setName(publisherInfo.getName());
			friend.setPortrait(publisherInfo.getProfile());
			friend.setPortraitVersion(-1);
			utilSrvc.gotoChatPageFromPublisher(this, friend, searchResualt);
		}
	}

	private void setProfileImg() {
		Bitmap imageBitmap;
		if (searchResualt.getPublisherInfo().getProfile() != null) {
			imageBitmap = BitmapFactory.decodeByteArray(searchResualt
					.getPublisherInfo().getProfile(), 0, searchResualt
					.getPublisherInfo().getProfile().length);

		} else {
			imageBitmap = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.dafaultuser01);
		}
		imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 80, 80, true);
		profile.setImageBitmap(imageBitmap);
	}

	public void dialogCall(View view) {
		Uri uri = Uri.parse("tel:"
				+ searchResualt.getPublisherInfo().getTelephone());
		Intent it = new Intent();
		it.setAction(Intent.ACTION_CALL);
		it.setData(uri);
		this.startActivity(it);
	}

	public void dialogCancel(View view) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public void callPublisherFromInfo(View view) {
		if (searchResualt.getPublisherInfo().isPhonePublished()) {
			LayoutInflater factory = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.dialog_phone, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			dialog = builder.create();
			((AlertDialog) dialog).setView(myView, 0, 0, 0, 0);
			dialog.show();
			TextView phonenumber = (TextView) dialog
					.findViewById(R.id.dialog_phone_number);
			phonenumber
					.setText(searchResualt.getPublisherInfo().getTelephone());

		} else {
			Toast toast = Toast.makeText(this, "抱歉，对方未公开手机号",
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	private class itemClickListener implements OnClickListener {

		private OfferInfo offerInfo;

		public itemClickListener(OfferInfo offer) {
			offerInfo = offer;
		}

		@Override
		public void onClick(View view) {
			utilSrvc.gotoPublisherFromDetailInfo(context,
					convertObject(offerInfo));
		}

		private SearchResulatItem convertObject(OfferInfo info) {
			SearchResulatItem item = new SearchResulatItem("", "", "", "");
			String publisherName = null;
			String mail = null;
			PublisherInfo publisher = searchResualt.getPublisherInfo();
			publisherName = publisher.getName();
			mail = publisher.getEmail();
			if (info.getCity() == "null") {
				info.setCity("其他城市");
			}
			if (info.getEntreprise() == "null") {
				info.setEntreprise("未填写公司");
			}
			if (info.getPost() == "null") {
				info.setPost("未填写职位");
			}
			item = new SearchResulatItem(publisherName, info.getEntreprise(),
					info.getCity(), info.getPost());
			item.setDate(info.getDate());
			if (info.getDescription() != "null") {
				item.setDescription(info.getDescription());
			} else {
				item.setDescription("未描述Offer具体信息");
			}
			if (info.getDomain() != "null") {
				item.setDomain(info.getDomain());
			} else {
				item.setDomain("其他行业");
			}
			if (info.getMailbox() != null && info.getMailbox().length() > 0) {
				item.setMailbox(info.getMailbox());
			} else {
				item.setMailbox(mail);
			}
			item.setOfferID(info.getOfferID());
			item.setFavorite(info.getFavorite());
			item.setSalary(info.getSalary());
			item.setPublisherInfo(publisher);
			item.setFavorite(searchResualt.isFavorite());
			item.setApplied(searchResualt.isApplied());
			item.setOfferInfo(searchResualt.getOfferInfo());
			return item;
		}

	}

	private class postInfoList extends BaseAdapter {

		@Override
		public int getCount() {
			return searchResualt.getOfferInfo().length;
		}

		@Override
		public Object getItem(int position) {
			return searchResualt.getOfferInfo()[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			OfferInfo offer = (OfferInfo) this.getItem(position);
			if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) PublisherDetailInfoActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(
						R.layout.activity_publisher_info_post_item, null);
			}
			post = (TextView) convertView
					.findViewById(R.id.publisher_list_post);
			date = (TextView) convertView
					.findViewById(R.id.publisher_list_date);
			entreprise = (TextView) convertView
					.findViewById(R.id.publisher_list_entreprise);
			salary = (TextView) convertView
					.findViewById(R.id.publisher_list_salary);
			domain = (TextView) convertView
					.findViewById(R.id.publisher_list_domain);
			city = (TextView) convertView
					.findViewById(R.id.publisher_list_city);
			post.setText(offer.getPost());
			date.setText(offer.getDate());
			entreprise.setText(offer.getEntreprise());
			domain.setText(offer.getDomain());
			city.setText(offer.getCity());
			salary.setText(offer.getSalary());
			convertView.setOnClickListener(new itemClickListener(offer));
			return convertView;
		}

	}

}
