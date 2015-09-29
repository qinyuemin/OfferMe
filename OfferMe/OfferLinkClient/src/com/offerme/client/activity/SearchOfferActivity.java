package com.offerme.client.activity;

import com.offerme.R;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.SearchSrvc;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.search.SearchKeyword;
import com.offerme.client.service.search.SearchResulat;
import com.offerme.client.service.search.SearchResulatItem;
import com.umeng.analytics.MobclickAgent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView.OnEditorActionListener;

public class SearchOfferActivity extends Fragment {

	public static final String KEYWORD = "Keyword";
	public static int OFFERPERPAGE = 15;
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private SearchSrvc searchSrvc = SearchSrvc.getInstance(utilSrvc);
	private LocalPersonInfo personInfo = LocalPersonInfo
			.getInstance(getActivity());
	private SearchResulat resultData = new SearchResulat();
	private BaseAdapter resualtAdapter = new searchResualtList();
	private SearchKeyword keyword = null;
	private EditText textEntreprise = null;
	private Spinner textCity = null;
	private Context context = null;
	private ListView resultList = null;
	private View searchView = null;
	private View endView = null;
	private TextView searchAction = null;
	private Dialog processDialog = null;
	private String cityLocation = "";
	private int lastItem = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_search, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = this.getActivity();
		try {
			initView();
		} catch (Exception e) {
			//e.printStackTrace();
		}
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
	}

	private void initSearch() {
		if (utilSrvc.isNetworkConnected(context)) {
			setKeyword();
			setProcessDialog();
			searchSrvc.initSearch(context, processDialog, keyword,
					resultList.getAdapter(), resultData);
		} else {
			utilSrvc.showConnectDialog(context);
		}
	}

	private void initView() {
		initActionBar();
		resultList = (ListView) getActivity().findViewById(
				R.id.search_resualt_list);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		searchView = layoutInflater.inflate(
				R.layout.activity_search_progress_view, null);
		endView = layoutInflater.inflate(R.layout.activity_search_end_view,
				null);
		resultList.addFooterView(searchView);
		if (utilSrvc.HasSearchResualt()) {
			resultData = utilSrvc.getSearchResualt();
		}
		resultList.setAdapter(resualtAdapter);
		resultList.setOnScrollListener(new scrollListener());
		clearFootView();
		initSearch();
	}

	public void initActionBar() {
		keyword = utilSrvc.getKeyword();
		searchAction = (TextView) getActivity()
				.findViewById(R.id.search_action);
		searchAction.setOnClickListener(new searchJob());
		textEntreprise = (EditText) getActivity().findViewById(
				R.id.search_content);
		textCity = (Spinner) getActivity().findViewById(R.id.search_region);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this.getActivity(), R.array.city_list,
				R.layout.spinner_type_search);
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_type);
		textCity.setAdapter(adapter);
		if (keyword != null) {
			textCity.setSelection(findCityPosition(keyword.getCity()));
			textEntreprise.setText(keyword.getEntreprise());
		} else {
			getCurrentCity();
			textCity.setSelection(findCityPosition(cityLocation));
		}
		setKeyword();
		textEntreprise.setOnEditorActionListener(new search());
	}

	private void clearFootView() {
		try {
			resultList.removeFooterView(searchView);
			resultList.removeFooterView(endView);
		} catch (Exception e) {
		}
	}

	private int findCityPosition(String cityName) {
		for (int count = 0; count < textCity.getCount(); count++) {
			if (cityName.contains(textCity.getItemAtPosition(count).toString())) {
				return count;
			}
		}
		return 0;
	}

	private void getCurrentCity() {
		cityLocation = utilSrvc.getCurrentCity();
		if (cityLocation == null) {
			cityLocation = personInfo.getValue(LocalPersonInfo.CITY);
		}
	}

	public void startSearch() {
		clearFootView();
		if (utilSrvc.isNetworkConnected(context)) {
			resultData = new SearchResulat();
			setKeyword();
			setProcessDialog();
			searchSrvc.startSearch(context, processDialog, keyword,
					resultList.getAdapter(), resultData, OFFERPERPAGE);
		} else {
			utilSrvc.showConnectDialog(context);
		}
	}

	public void continueSearch() {
		if (utilSrvc.isNetworkConnected(context)) {
			setKeyword();
			setProcessDialog();
			searchSrvc.startSearch(context, null, keyword,
					resultList.getAdapter(), resultData, OFFERPERPAGE);
		} else {
			utilSrvc.showConnectDialog(context);
		}
	}

	private boolean hasCityChanged() {
		if (!keyword.getCity().equalsIgnoreCase(
				String.valueOf(textCity.getSelectedItem()))) {
			return true;
		}
		return false;
	}

	private boolean hasEntrepriseChanged() {
		if (!keyword.getEntreprise().equalsIgnoreCase(
				String.valueOf(textEntreprise.getText()))) {
			return true;
		}
		return false;
	}

	private boolean hasKeywordChanged() {
		if (hasCityChanged() || hasEntrepriseChanged()) {
			return true;
		}
		return false;
	}

	private void setKeyword() {
		if (keyword != null && hasKeywordChanged()) {
			resultData.clearResulat();
		}
		keyword = new SearchKeyword(Integer.valueOf(personInfo
				.getValue(LocalPersonInfo.USERID)));
		keyword.setEntreprise(String.valueOf(textEntreprise.getText()));
		keyword.setCity(String.valueOf(textCity.getSelectedItem()));
		keyword.setPosition(textCity.getSelectedItemPosition());
		if (resultData.getSize() != 0) {
			keyword.setLastOfferID(resultData.getItem(resultData.getSize() - 1)
					.getOfferID());
		}
		utilSrvc.setKeyword(keyword);
	}

	private void setProcessDialog() {
		InputMethodManager inputmanager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputmanager.hideSoftInputFromWindow(textEntreprise.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
		LayoutInflater factory = LayoutInflater.from(context);
		View dialogView = factory.inflate(R.layout.dialog_process, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		processDialog = builder.create();
		((AlertDialog) processDialog).setView(dialogView, 0, 0, 0, 0);
	}

	private class searchJob implements OnClickListener {
		@Override
		public void onClick(View view) {
			startSearch();
		}
	}

	private class search implements OnEditorActionListener {

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			startSearch();
			if (actionId == EditorInfo.IME_ACTION_SEARCH) {
				return true;
			}
			return false;
		}
	}

	private class onClickListener implements OnClickListener {

		private int position = 0;

		public onClickListener(int pos) {
			this.position = pos;
		}

		@Override
		public void onClick(View view) {
			utilSrvc.setKeyword(keyword);
			utilSrvc.gotoPublisherInfoPage(context,
					resultData.getItem(position));
		}
	}

	private Bitmap setProfileImg(SearchResulatItem item) {
		Bitmap imageBitmap = null;
		if (item.getPublisherInfo().getProfile() != null) {
			imageBitmap = BitmapFactory.decodeByteArray(item.getPublisherInfo()
					.getProfile(), 0,
					item.getPublisherInfo().getProfile().length);

		} else if (context.getResources() != null) {
			imageBitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.dafaultuser01);
		}
		if (imageBitmap != null) {
			imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 80, 80, true);
		}
		return imageBitmap;
	}

	private class searchResualtList extends BaseAdapter {

		@Override
		public int getCount() {
			return resultData.getSize();
		}

		@Override
		public Object getItem(int position) {
			return resultData.getItem(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SearchResulatItem itemList = (SearchResulatItem) this
					.getItem(position);
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				LayoutInflater layoutInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(
						R.layout.activity_search_item, null);
				holder.profile = (ImageView) convertView
						.findViewById(R.id.resualt_profile);
				holder.postName = (TextView) convertView
						.findViewById(R.id.resualt_post);
				holder.date = (TextView) convertView
						.findViewById(R.id.resualt_date);
				holder.enterpriseName = (TextView) convertView
						.findViewById(R.id.resualt_enterpriser);
				holder.salary = (TextView) convertView
						.findViewById(R.id.resualt_salary);
				holder.cityName = (TextView) convertView
						.findViewById(R.id.resualt_city);
				holder.domain = (TextView) convertView
						.findViewById(R.id.resualt_domain);
				holder.favoriteBox = (CheckBox) convertView
						.findViewById(R.id.resualt_favorite);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			try {
				holder.profile.setImageBitmap(setProfileImg(itemList));
				holder.postName.setText(itemList.getTitle());
				holder.date.setText(itemList.getDate());
				holder.enterpriseName.setText(itemList.getEntreprise());
				holder.salary.setText(itemList.getSalary());
				holder.cityName.setText(itemList.getCity());
				holder.domain.setText(itemList.getDomain());
				holder.favoriteBox.setChecked(itemList.isFavorite());
				convertView.setOnClickListener(new onClickListener(position));
			} catch (Exception e) {
				// //e.printStackTrace();
			}
			return convertView;
		}

		public final class ViewHolder {
			public ImageView profile;
			public TextView postName;
			public TextView date;
			public TextView enterpriseName;
			public TextView salary;
			public TextView cityName;
			public TextView domain;
			public CheckBox favoriteBox;
		}
	}

	private class scrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			lastItem = firstVisibleItem + visibleItemCount - 1;
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (utilSrvc.isEnd()) {
				clearFootView();
				resultList.addFooterView(endView);
			} else if (((lastItem + 1) % OFFERPERPAGE) == 0
					|| lastItem == resultList.getCount() - 1) {
				clearFootView();
				resultList.addFooterView(searchView);
				continueSearch();
			}
		}

	}

}
