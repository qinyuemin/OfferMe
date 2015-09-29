package com.offerme.client.activity;

import java.util.ArrayList;

import com.offerme.R;
import com.offerme.client.service.DeleteOfferSrvc;
import com.offerme.client.service.PersonalSettingSrvc;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.personalsetting.PersonalSetting;
import com.offerme.client.service.publishoffer.OfferInfo;
import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class PublishOfferListActivity extends Fragment {

	private float downPosition;
	private float upPosition;
	private float movePosition;
	private ImageView addNewOffer;
	private ListView publishedOfferList;
	private Button deleteButton;
	private BaseAdapter offerAdapter;
	private PersonalSetting data;
	private Context context;
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private DeleteOfferSrvc deleteOffer = DeleteOfferSrvc.getInstance();
	private PersonalSettingSrvc personalSettingSrvc = PersonalSettingSrvc
			.getInstance();
	private ArrayList<OfferInfo> publishedInDataBase = new ArrayList<OfferInfo>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_publish_offerlist, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = this.getActivity();
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
		try {
			initView();
			loadData();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	private void initView() {
		initActionBar();
		publishedOfferList = (ListView) this.getActivity().findViewById(
				R.id.publish_offerlist_listview);
		publishedOfferList.setAdapter(new publishedListListener());
		offerAdapter = (BaseAdapter) publishedOfferList.getAdapter();
		personalSettingSrvc.setPublishedAdapter(offerAdapter);
	}

	private void loadData() {
		data = personalSettingSrvc.loadLocalPersonalSetting(this.getActivity());
		publishedInDataBase = data.getPublishList();
		offerAdapter.notifyDataSetChanged();
		utilSrvc.setOfferList(publishedInDataBase);
	}

	public void initActionBar() {
		addNewOffer = (ImageView) this.getActivity().findViewById(
				R.id.publish_offerlist_addOffer);
		addNewOffer.setOnClickListener(new gotoPublishOffer());
	}

	private class publishedListListener extends BaseAdapter {

		@Override
		public int getCount() {
			return publishedInDataBase.size();
		}

		@Override
		public Object getItem(int position) {
			return publishedInDataBase.get(position);
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
						R.layout.activity_publish_offerlist_item, null);
			}
			TextView title = (TextView) convertView
					.findViewById(R.id.publish_offerlist_item_title);
			TextView date = (TextView) convertView
					.findViewById(R.id.publish_offerlist_item_date);
			TextView city = (TextView) convertView
					.findViewById(R.id.publish_offerlist_item_city);
			TextView entreprise = (TextView) convertView
					.findViewById(R.id.publish_offerlist_item_entreprise);
			TextView domain = (TextView) convertView
					.findViewById(R.id.publish_offerlist_item_domain);
			TextView salary = (TextView) convertView
					.findViewById(R.id.publish_offerlist_item_salary);
			Button delete = (Button) convertView
					.findViewById(R.id.publish_offerlist_item_delete);
			String salaryText = offer.getSalary();
			title.setText(offer.getPost());
			date.setText(offer.getDate());
			city.setText(offer.getCity());
			entreprise.setText(offer.getEntreprise());
			domain.setText(offer.getDomain());
			if (salaryText != null && !salaryText.contains("面议")) {
				salaryText = salaryText + "元/月";
			}
			salary.setText(salaryText);
			convertView.setOnTouchListener(new offerDelete(delete, position));
			delete.setOnClickListener(new deleteClick(offer));
			return convertView;
		}
	}

	private class offerDelete implements OnTouchListener {

		private Button deleteOffer;
		private int position;

		public offerDelete(Button delete, int pos) {
			deleteOffer = delete;
			upPosition = 0;
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
			utilSrvc.gotoPublishOfferFromList(context,
					publishedInDataBase.get(position));
		}
	}

	private void doAnimation(Context context, View view, int animId) {
		Animation animation = AnimationUtils.loadAnimation(context, animId);
		view.startAnimation(animation);
	}

	private class deleteClick implements OnClickListener {

		private OfferInfo info;

		public deleteClick(OfferInfo offer) {
			info = offer;
		}

		@Override
		public void onClick(View view) {
			if (utilSrvc.isNetworkConnected(context)) {
				if (deleteButton != null) {
					deleteButton.setVisibility(View.GONE);
				}
				String myID = String.valueOf(data.getUserId());
				deleteOffer.setOfferDelete(context, info, myID,
						publishedInDataBase, DeleteOfferSrvc.FOR_PUBLISHED);
			} else {
				utilSrvc.showConnectDialog(context);
			}
		}
	}

	private class gotoPublishOffer implements OnClickListener {

		@Override
		public void onClick(View view) {
			utilSrvc.gotoPublishOfferPage(getActivity());
		}
	}
}
