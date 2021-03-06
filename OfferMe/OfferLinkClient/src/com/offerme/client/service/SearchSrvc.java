package com.offerme.client.service;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.offerme.client.activity.SearchOfferActivity;
import com.offerme.client.http.HttpRequest;
import com.offerme.client.http.HttpServiceType;
import com.offerme.client.service.search.SearchKeyword;
import com.offerme.client.service.search.SearchResulat;
import com.offerme.client.service.socket.SocketCommunication;

public class SearchSrvc implements SocketSrvc {

	private static SearchSrvc searchSrvc;
	private UtilSrvc utilSrvc;
	private SocketCommunication socket;
	private Context context;
	private SearchKeyword keyword;
	private ListAdapter resualtAdapter;
	private SearchResulat searchResualt;
	private SearchResulat searchData;
	private Dialog processDialog = null;
	private int size;

	private SearchSrvc(UtilSrvc service) {
		utilSrvc = service;
	}

	public final static SearchSrvc getInstance(UtilSrvc service) {
		if (searchSrvc == null) {
			searchSrvc = new SearchSrvc(service);
		}
		return searchSrvc;
	}

	public void refreshList() {
		HeaderViewListAdapter listAdapter = (HeaderViewListAdapter) resualtAdapter;
		if (listAdapter != null) {
			ListAdapter ladapter = listAdapter.getWrappedAdapter();
			((BaseAdapter) ladapter).notifyDataSetChanged();
		}
	}

	public void startSearch(Context ctx, Dialog dialog,
			SearchKeyword searchKeyword, ListAdapter adapter,
			SearchResulat resualt, int requestsize) {
		context = ctx;
		keyword = searchKeyword;
		resualtAdapter = adapter;
		searchResualt = resualt;
		processDialog = dialog;
		size = requestsize;
		socket = new SocketCommunication(this);
		socket.execute();
	}

	public void initSearch(Context ctx, Dialog dialog,
			SearchKeyword searchKeyword, ListAdapter adapter,
			SearchResulat resualt) {
		context = ctx;
		keyword = searchKeyword;
		resualtAdapter = adapter;
		searchResualt = resualt;
		processDialog = dialog;
		size = SearchOfferActivity.OFFERPERPAGE;
		socket = new SocketCommunication(this);
		socket.execute();
	}

	@Override
	public void onPreExecute() {
		if (processDialog != null) {
			processDialog.setCancelable(true);
			processDialog.show();
			Window window = processDialog.getWindow();
			window.setGravity(Gravity.CENTER);
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.alpha = 0.5f;
			lp.width = 200;
			lp.height = 300;
			window.setAttributes(lp);
		}
	}

	@Override
	public Boolean doInBackground(String... params) {
		//System.out.println("Search->doInBackground->Show me the data: "
		//		+ keyword.getLastOfferID());
		searchData = HttpRequest.getHttpResponse(HttpServiceType.SEARCH_OFFER,
				keyword, SearchResulat.class);

		if (searchData != null) {
			return true;
		}
		return false;
	}

	@Override
	public void onProgressUpdate(Integer... values) {
	}

	@Override
	public void onPostExecute(Boolean success) {
		if (processDialog != null) {
			processDialog.dismiss();
		}
		Toast toast = null;
		if (success) {
			if (searchData.getSize() == 0) {
				utilSrvc.setEnd(true);
			}
			if (searchData.getSize() != size) {
				utilSrvc.setEnd(true);
			} else {
				utilSrvc.setEnd(false);
			}
			searchResualt.addSearchResualt(searchData);
			utilSrvc.setSearchResualt(searchResualt);
			refreshList();
		} else {
			toast = Toast.makeText(context, "搜索失败", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	@Override
	public void onCancelled() {
		socket.cancel(true);
	}

}
