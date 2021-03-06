package com.offerme.client.service;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.offerme.R;
import com.offerme.client.http.HttpRequest;
import com.offerme.client.http.HttpServiceType;
import com.offerme.client.localdata.LocalDataBase;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.personalsetting.PersonalSetting;
import com.offerme.client.service.socket.SocketCommunication;

public class PersonalSettingSrvc implements SocketSrvc {

	private static PersonalSettingSrvc personalSettingSrvc = new PersonalSettingSrvc();

	private final int TAKE_PICTURE = 2;
	private final int GET_GALLERY = 1;
	private Context context;
	private SocketCommunication socket;
	private Dialog processDialog;
	private BaseAdapter favoriteList;
	private BaseAdapter appliedList;
	private BaseAdapter publishedList;
	private LocalPersonInfo personInfo;
	private LocalDataBase localData;
	private PersonalSetting personalSetting;
	//private UtilSrvc utilSrvc = UtilSrvc.getInstance();

	private PersonalSettingSrvc() {

	}

	public static PersonalSettingSrvc getInstance() {
		return personalSettingSrvc;
	}

	public void refreshFavorite() {
		if (favoriteList != null) {
			favoriteList.notifyDataSetChanged();
			//utilSrvc.gotoPersonalInfoPage(context);
		}
	}

	public void refreshApplied() {
		if (appliedList != null) {
			appliedList.notifyDataSetChanged();
		}
	}

	public void refreshPublished() {
		if (publishedList != null) {
			publishedList.notifyDataSetChanged();
			//utilSrvc.gotoLoggedInFromPublish(context);
		}
	}

	public void setFavoriteAdapter(BaseAdapter favoriteAdapter) {
		favoriteList = favoriteAdapter;
	}

	public void setAppliedAdapter(BaseAdapter appliedAdapter) {
		appliedList = appliedAdapter;
	}

	public void setPublishedAdapter(BaseAdapter publishedAdapter) {
		publishedList = publishedAdapter;
	}

	public boolean uploadPhoto(ActionBarActivity activity, String source) {
		if ("FromLib".equals(source)) {
			Intent intentLib = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			activity.startActivityForResult(intentLib, GET_GALLERY);
		} else if ("FromCamera".equals(source)) {
			Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			if (intentCamera.resolveActivity(activity.getPackageManager()) != null) {
				activity.startActivityForResult(intentCamera, TAKE_PICTURE);
			}
		}
		return true;
	}

	public PersonalSetting loadLocalPersonalSetting(Context ctx) {
		PersonalSetting setting = new PersonalSetting();
		personInfo = LocalPersonInfo.getInstance(ctx);
		if (personInfo.getValue(LocalPersonInfo.USERID) != null) {
			localData = LocalDataBase.getInstance(ctx);
			setting.setName(personInfo.getValue(LocalPersonInfo.NAME));
			setting.setCity(personInfo.getValue(LocalPersonInfo.CITY));
			setting.setPost(personInfo.getValue(LocalPersonInfo.POST));
			setting.setEnterprise(personInfo
					.getValue(LocalPersonInfo.ENTREPRISE));
			setting.setEmail(personInfo.getValue(LocalPersonInfo.EMAIL));
			setting.setPhoneNumber(personInfo
					.getValue(LocalPersonInfo.TELEPHONE));
			setting.setEmailPublic(personInfo
					.getBoolean(LocalPersonInfo.IS_EMAIL_PUBLISHED));
			setting.setPhonePublic(personInfo
					.getBoolean(LocalPersonInfo.IS_PHONE_PUBLISHED));
			setting.setUserId(Integer.parseInt(personInfo
					.getValue(LocalPersonInfo.USERID)));
			if (personInfo.getValue(LocalPersonInfo.PROFILE) != null) {
				setting.setProfile(Base64.decode(
						personInfo.getValue(LocalPersonInfo.PROFILE),
						Base64.DEFAULT));
			} else {
				setting.setProfile(null);
			}
			setting.setFavoriteList(localData.findFavoriteOffer());
			setting.setPublishList(localData.findPublishedOffer());
			setting.setApplyList(localData.findAppliedOffer());
		}
		return setting;
	}

	private void saveLocalPersonalSetting(Context ctx, PersonalSetting setting) {
		personInfo = LocalPersonInfo.getInstance(ctx);
		personInfo.setName(setting.getName());
		personInfo.setAge(setting.getAge());
		personInfo.setPhoneNumber(setting.getPhoneNumber());
		personInfo.setEmail(setting.getEmail());
		personInfo.setWorkyear(setting.getWorkyear());
		personInfo.setPost(setting.getPost());
		personInfo.setCity(setting.getCity());
		personInfo.setEnterprise(setting.getEnterprise());
		personInfo.setEmailPublished(setting.isEmailPublic());
		personInfo.setPhonePublished(setting.isPhonePublic());
		if (setting.getProfile() != null) {
			personInfo.setProfile(Base64.encodeToString(setting.getProfile(),
					Base64.DEFAULT));
		}
	}

	public void savePersonalSetting(Context ctx, PersonalSetting setting) {
		context = ctx;
		setProcessDialog();
		socket = new SocketCommunication(this);
		personalSetting = setting;
		personalSetting.setUserId(Integer.parseInt(personInfo
				.getValue(LocalPersonInfo.USERID)));
		socket.execute();
	}

	private void setProcessDialog() {
		LayoutInflater factory = LayoutInflater.from(context);
		View dialogView = factory.inflate(R.layout.dialog_process, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		processDialog = builder.create();
		((AlertDialog) processDialog).setView(dialogView, 0, 0, 0, 0);
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
		String res = HttpRequest.getHttpResponseStr(
				HttpServiceType.PERSONAL_SETTING, personalSetting);
		System.out.println("Show me the resualt: " + res);
		if (res != null && res.contains("OK")) {
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
		Toast toast = null;
		if (success) {
			// Save personal setting in local file
			saveLocalPersonalSetting(context, personalSetting);
			toast = Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		} else {
			toast = Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	@Override
	public void onCancelled() {
		socket.cancel(true);
	}

}
