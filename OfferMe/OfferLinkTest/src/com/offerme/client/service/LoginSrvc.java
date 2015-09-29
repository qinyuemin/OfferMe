package com.offerme.client.service;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Base64;
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
import com.offerme.client.service.cv.PersonalCV;
import com.offerme.client.service.login.LoginRequest;
import com.offerme.client.service.login.LoginResponse;
import com.offerme.client.service.publishoffer.OfferInfo;
import com.offerme.client.service.socket.SocketCommunication;

public class LoginSrvc implements SocketSrvc {

	private static LoginSrvc loginSrvc = new LoginSrvc();
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private Context context;
	private SocketCommunication socket;
	private Dialog processDialog;
	private LocalPersonInfo personInfo;
	private LocalDataBase database;
	private PersonalCV cv = null;
	private LoginRequest req = null;
	private LoginResponse res = null;

	private LoginSrvc() {

	}

	public static LoginSrvc getInstance() {
		return loginSrvc;
	}

	public boolean isLoggedInOnce(Context ctx) {
		personInfo = LocalPersonInfo.getInstance(ctx);
		database = LocalDataBase.getInstance(ctx);
		if (personInfo.getValue(LocalPersonInfo.USERID) != null) {
			return true;
		}
		return false;
	}

	private void setDialog() {
		LayoutInflater factory = LayoutInflater.from(context);
		View dialogView = factory.inflate(R.layout.dialog_process, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		processDialog = builder.create();
		((AlertDialog) processDialog).setView(dialogView, 0, 0, 0, 0);
	}

	public void submit(Context ctx, LoginRequest loginRequest) {
		context = ctx;
		setDialog();
		socket = new SocketCommunication(this);
		req = loginRequest;
		res = new LoginResponse();
		socket.execute();
	}

	private void saveLoginInfo() {
		try {
			personInfo.clearMemory();
			// database.deleteDataBase(LocalDataBase.OFFERTABLENAME);
			// database.deleteDataBase(LocalDataBase.USERTABLENAME);
			cv = res.getCv();
			personInfo.setUserID(String.valueOf(res.getUserId()));
			personInfo.setName(res.getName());
			personInfo.setAge(res.getAge());
			personInfo.setCity(res.getCity());
			personInfo.setEnterprise(res.getEnterprise());
			personInfo.setEmail(res.getEmail());
			personInfo.setPost(res.getPost());
			personInfo.setWorkyear(res.getWorkyear());
			personInfo.setPhoneNumber(res.getPhoneNumber());
			personInfo.setPhonePublished(res.isPhonePublic());
			personInfo.setEmailPublished(res.isMailPublic());
			personInfo.setEducation(cv.getEducation());
			personInfo.setColleage(cv.getColleage());
			personInfo.setWorks(cv.getWorkInfo().getWorks());
			if (res.getProfile() != null) {
				personInfo.setProfile(Base64.encodeToString(res.getProfile(),
						Base64.DEFAULT));
			}
			insertOfferList(res.getFavoriteOffer());
			insertOfferList(res.getPublishOffer());
			insertOfferList(res.getApplyOffer());
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	private void insertOfferList(ArrayList<OfferInfo> offerList) {
		for (int count = 0; count < offerList.size(); count++) {
			database.insertOffer(offerList.get(count));
		}
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
		res = HttpRequest.getHttpResponse(
				HttpServiceType.LOGIN, req, LoginResponse.class);
		if (res != null && res.getResponseCode() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPostExecute(Boolean success) {
		processDialog.dismiss();
		Toast toast = null;
		String message = null;
		if (success) {
			message = context.getResources().getString(
					R.string.loggedin_success);
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			saveLoginInfo();
			utilSrvc.gotoLoggedInPage(context);
		} else {
			if (res == null) {
				message = context.getResources().getString(
						R.string.loggedin_fail_network);
				toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			} else if (res.getResponseCode() == -2) {
				message = context.getResources().getString(
						R.string.loggedin_fail_password);
				toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			}
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}

	}

	@Override
	public void onCancelled() {
		socket.cancel(true);
	}

}
