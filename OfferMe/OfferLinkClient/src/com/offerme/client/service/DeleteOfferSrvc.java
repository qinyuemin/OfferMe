package com.offerme.client.service;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.offerme.client.http.HttpRequest;
import com.offerme.client.http.HttpServiceType;
import com.offerme.client.localdata.LocalDataBase;
import com.offerme.client.service.publishoffer.OfferInfo;
import com.offerme.client.service.search.SearchResulat;
import com.offerme.client.service.socket.SocketCommunication;

public class DeleteOfferSrvc implements SocketSrvc {
	public static String FOR_FAVORITE = "favorite";
	public static String FOR_APPLIED = "applied";
	public static String FOR_PUBLISHED = "published";
	private static DeleteOfferSrvc deleteSrvc;
	private SocketCommunication socket;
	private OfferDelete deleteOffer;
	private Context context;
	private String resualt;
	private String deleteType;
	private OfferInfo info;
	private ArrayList<OfferInfo> offerList;
	private LocalDataBase dataBase = null;
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private SearchSrvc searchSrvc = SearchSrvc.getInstance(utilSrvc);
	private PersonalSettingSrvc personalSettingSrvc = PersonalSettingSrvc
			.getInstance();

	private DeleteOfferSrvc() {
	}

	public final static DeleteOfferSrvc getInstance() {
		if (deleteSrvc == null) {
			deleteSrvc = new DeleteOfferSrvc();
		}
		return deleteSrvc;
	}

	public void setOfferDelete(Context ctx, OfferInfo offer, String user,
			ArrayList<OfferInfo> list, String type) {
		deleteOffer = new OfferDelete(offer.getOfferID(), user);
		dataBase = LocalDataBase.getInstance(ctx);
		deleteType = type;
		offerList = list;
		context = ctx;
		info = offer;
		startDelete();
	}

	public void startDelete() {
		if (!deleteType.contains(FOR_APPLIED)) {
			socket = new SocketCommunication(this);
			socket.execute();
		} else {
			deleteOffer();
			refreshList();
		}
	}

	public void removeOfferFromList() {
		int position = -1;
		for (int count = 0; count < offerList.size(); count++) {
			if (offerList.get(count).getOfferID()
					.equalsIgnoreCase(info.getOfferID())) {
				position = count;
			}
		}
		if (position >= 0) {
			offerList.remove(position);
		}
	}

	public void deleteOffer() {
		try {
			if (deleteType.contains(FOR_FAVORITE)) {
				info.setFavorite(false);
				dataBase.deleteFavoriteOffer(info);
				removeOfferFromList();
			} else if (deleteType.contains(FOR_APPLIED)) {
				info.setApplied(false);
				dataBase.deleteAppliedOffer(info);
				removeOfferFromList();
			} else {
				dataBase.deletePublishedOffer(info);
				removeOfferFromList();
			}
		} catch (Exception e) {
		}
	}

	public void refreshList() {
		searchSrvc.refreshList();
		if (deleteType.contains(FOR_FAVORITE)) {
			resetSearchResualt(false);
			personalSettingSrvc.refreshFavorite();
		} else if (deleteType.contains(FOR_APPLIED)) {
			resetSearchResualt();
			personalSettingSrvc.refreshApplied();
		} else {
			personalSettingSrvc.refreshPublished();
		}
	}

	private void resetSearchResualt(boolean isFavorite) {
		SearchResulat resualts = utilSrvc.getSearchResualt();
		for (int count = 0; count < resualts.getSize(); count++) {
			if (resualts.getItem(count).getOfferID()
					.equalsIgnoreCase(info.getOfferID())) {
				resualts.getItem(count).setFavorite(isFavorite);
			}
		}
	}

	private void resetSearchResualt() {
		SearchResulat resualts = utilSrvc.getSearchResualt();
		for (int count = 0; count < resualts.getSize(); count++) {
			if (resualts.getItem(count).getOfferID()
					.equalsIgnoreCase(info.getOfferID())) {
				resualts.getItem(count).setApplied(true);
			}
		}
	}

	@Override
	public void onPreExecute() {
	}

	@Override
	public Boolean doInBackground(String... params) {
		resualt = HttpRequest.getHttpResponseStr(HttpServiceType.DELETE_OFFER,
				deleteOffer);
		if (resualt != null && !resualt.contains("Fail")) {
			return true;
		}
		return false;
	}

	@Override
	public void onProgressUpdate(Integer... values) {
		socket.cancel(true);
	}

	@Override
	public void onPostExecute(Boolean success) {
		if (success) {
			deleteOffer();
			// dataBase.visiterAllData(LocalDataBase.OFFERTABLENAME);
			Toast toast = Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			refreshList();
		} else {
			Toast toast = Toast
					.makeText(context, "抱歉，删除失败", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	@Override
	public void onCancelled() {
	}

	private class OfferDelete {
		private String OfferID;
		private String UserID;

		public OfferDelete(String offer, String user) {
			OfferID = offer;
			UserID = user;
		}
	}

}
