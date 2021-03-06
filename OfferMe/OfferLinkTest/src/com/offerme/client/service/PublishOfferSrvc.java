package com.offerme.client.service;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.offerme.R;
import com.offerme.client.http.HttpRequest;
import com.offerme.client.http.HttpServiceType;
import com.offerme.client.localdata.LocalDataBase;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.publishoffer.OfferInfo;
import com.offerme.client.service.socket.SocketCommunication;

public class PublishOfferSrvc implements SocketSrvc {

	private static PublishOfferSrvc publishOfferSrvc = null;
	private LocalPersonInfo personInfo = null;
	private LocalDataBase dataBase = null;
	private Toast toast;
	private OfferInfo info;
	private UtilSrvc utilSrvc;
	private SocketCommunication socket;
	private Context context;
	private Dialog processDialog;
	private String resualt = null;
	private String text = null;

	private PublishOfferSrvc(UtilSrvc service) {
		this.utilSrvc = service;
	}

	public static PublishOfferSrvc getInstance(UtilSrvc service) {
		if (publishOfferSrvc == null) {
			publishOfferSrvc = new PublishOfferSrvc(service);
		}
		return publishOfferSrvc;
	}

	public void submit(Context ctx, OfferInfo offerInfo) {
		resualt = null;
		context = ctx;
		info = offerInfo;
		personInfo = LocalPersonInfo.getInstance(context);
		dataBase = LocalDataBase.getInstance(context);
		if (checkOfferInfo(info)) {
			info.setUserID(personInfo.getValue(LocalPersonInfo.USERID));
			setProcessDialog();
			socket = new SocketCommunication(this);
			socket.execute();
		}
	}

	private void setProcessDialog() {
		LayoutInflater factory = LayoutInflater.from(context);
		View dialogView = factory.inflate(R.layout.dialog_process, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		processDialog = builder.create();
		((AlertDialog) processDialog).setView(dialogView, 0, 0, 0, 0);
	}

	private boolean checkOfferInfo(OfferInfo offerInfo) {
		if (!offerInfo.isAllSet()) {
			text = (String) context.getResources().getText(
					R.string.publisher_offer_not_complete);
			showText(text);
			offerInfo.setIsComplete(false);
			return false;
		} else if (!offerInfo.checkEmailAddress(offerInfo.getMailbox(),
				personInfo.getValue(LocalPersonInfo.EMAIL))) {
			text = (String) context.getResources().getText(
					R.string.publisher_offer_mailformat_error);
			showText(text);
			offerInfo.setIsComplete(false);
			return false;
		} else {
			offerInfo.setIsComplete(true);
		}
		return true;
	}

	private void showText(String text) {
		toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onPreExecute() {
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
	
	@Override
	public Boolean doInBackground(String... params) {
		resualt = HttpRequest.getHttpResponseStr(
				HttpServiceType.PUBLISH_OFFER, info);
		System.out.println("PublishOffer->doInBackground: Show me the reply got: "
				+ resualt);
		if (resualt != null) {
			return true;
		}
		return false;
	}

	@Override
	public void onProgressUpdate(Integer... values) {

	}

	@Override
	public void onPostExecute(Boolean success) {
		processDialog.dismiss();
		if (success) {
			if (dataBase.hasOffer(info.getOfferID())) {
				text = (String) context.getResources().getText(
						R.string.publisher_offer_modify_success);
			} else {
				text = (String) context.getResources().getText(
						R.string.publisher_offer_success);
			}
			info.setOfferID(resualt.substring(12, resualt.length() - 1));
			info.setDate(resualt.substring(1, 11));
			dataBase.insertOffer(info);
			showText(text);
			utilSrvc.gotoLoggedInFromPublish(context);
		} else {
			if (dataBase.hasOffer(info.getOfferID())) {
				text = (String) context.getResources().getText(
						R.string.publisher_offer_modify_fail);
			} else {
				text = (String) context.getResources().getText(
						R.string.publisher_offer_fail);
			}
			showText(text);
		}
	}

	@Override
	public void onCancelled() {
		socket.cancel(true);
	}

}
