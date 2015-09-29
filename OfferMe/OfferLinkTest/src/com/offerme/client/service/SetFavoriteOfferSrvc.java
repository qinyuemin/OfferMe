package com.offerme.client.service;

import android.content.Context;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.Toast;

import com.offerme.R;
import com.offerme.client.http.HttpRequest;
import com.offerme.client.http.HttpServiceType;
import com.offerme.client.localdata.LocalDataBase;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.personalsetting.FavoriteOffer;
import com.offerme.client.service.publishoffer.OfferInfo;
import com.offerme.client.service.search.SearchResulat;
import com.offerme.client.service.search.SearchResulatItem;
import com.offerme.client.service.socket.SocketCommunication;

public class SetFavoriteOfferSrvc implements SocketSrvc {

	private static SetFavoriteOfferSrvc favoriteSrvc;
	private LocalPersonInfo info = null;
	private SocketCommunication socket;
	// private SearchResulatItem offerResulat;
	private Context context;
	private FavoriteOffer favoriteOffer;
	private CheckBox favoriteBox;
	private OfferInfo offerInfo = new OfferInfo();
	private LocalDataBase dataBase = null;
	private SearchResulatItem item;
	private Toast toast;
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private SearchSrvc searchSrvc = SearchSrvc.getInstance(utilSrvc);
	private PersonalSettingSrvc personalSettingSrvc = PersonalSettingSrvc
			.getInstance();

	private SetFavoriteOfferSrvc() {
	}

	public final static SetFavoriteOfferSrvc getInstance() {
		if (favoriteSrvc == null) {
			favoriteSrvc = new SetFavoriteOfferSrvc();
		}
		return favoriteSrvc;
	}

	public boolean isOfferFavorite(Context context, String offerID) {
		if (dataBase == null) {
			dataBase = LocalDataBase.getInstance(context);
		}
		return dataBase.isOfferFavorite(offerID);
	}

	public void setFavoriteOffer(Context ctx, SearchResulatItem offer,
			boolean isAdded, CheckBox box) {
		context = ctx;
		item = offer;
		favoriteBox = box;
		info = LocalPersonInfo.getInstance(context);
		dataBase = LocalDataBase.getInstance(context);
		String userID = info.getValue(LocalPersonInfo.USERID);
		System.out.println("Show me the publiser id and user id: "
				+ offer.getPublisherInfo().getUserID() + " " + userID);
		setOfferInfo(userID, isAdded);
		favoriteOffer = new FavoriteOffer(userID, item.getOfferID(), isAdded);
		socket = new SocketCommunication(this);
		socket.execute();
	}

	private void setOfferInfo(String userID, boolean isAdded) {
		offerInfo.setCity(item.getCity());
		offerInfo.setDate(item.getDate());
		offerInfo.setDomain(item.getDomain());
		offerInfo.setDescription(item.getDescription());
		offerInfo.setEntreprise(item.getEntreprise());
		offerInfo.setPost(item.getTitle());
		offerInfo.setSalary(item.getSalary());
		offerInfo.setOfferID(item.getOfferID());
		offerInfo.setFavorite(isAdded);
		offerInfo.setApplied(item.isApplied());
		offerInfo.setWorkyear(item.getWorkyear());
		offerInfo.setEducation(item.getEducation());
		offerInfo.setUserID(userID);
		offerInfo.setOfferOwnerID(String.valueOf(item.getPublisherInfo()
				.getUserID()));
		offerInfo.setPublisherInfo(item.getPublisherInfo());
	}

	private void showText(String text) {
		toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	private void resetSearchResualt(boolean isFavorite) {
		SearchResulat resualts = utilSrvc.getSearchResualt();
		for (int count = 0; count < resualts.getSize(); count++) {
			if (resualts.getItem(count).getOfferID().equalsIgnoreCase(item.getOfferID())) {
				resualts.getItem(count).setFavorite(isFavorite);
			}
		}
	}

	@Override
	public void onPreExecute() {
	}
	
	@Override
	public Boolean doInBackground(String... params) {
		String resualt = HttpRequest.getHttpResponseStr(
				HttpServiceType.FAVORITE_OFFER, favoriteOffer);
		if (resualt != null) {
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
		String comment = null;
		if (success) {
			if (favoriteOffer.isAdd()) {
				dataBase.insertOffer(offerInfo);
				// dataBase.visiterAllData(LocalDataBase.OFFERTABLENAME);
			} else {
				dataBase.deleteFavoriteOffer(offerInfo);
				// dataBase.visiterAllData(LocalDataBase.OFFERTABLENAME);
			}
			comment = context.getResources().getString(
					R.string.search_favorite_offer_success);
			showText(comment);
			favoriteBox.setChecked(favoriteOffer.isAdd());
			resetSearchResualt(favoriteOffer.isAdd());
			searchSrvc.refreshList();
			personalSettingSrvc.refreshFavorite();
		} else {
			comment = context.getResources().getString(
					R.string.search_favorite_offer_fail);
			showText(comment);
			favoriteBox.setChecked(!favoriteOffer.isAdd());
		}
	}

	@Override
	public void onCancelled() {
	}

}
