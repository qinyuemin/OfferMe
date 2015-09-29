package com.offerme.client.service;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.offerme.client.http.HttpRequest;
import com.offerme.client.http.HttpServiceType;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.inscription.InscriptionInfo;
import com.offerme.client.service.socket.SocketCommunication;

public class InscriptionSrvc implements SocketSrvc {

	private static InscriptionSrvc inscriptionSrvc = null;
	private final int TAKE_PICTURE = 2;
	private final int GET_GALLERY = 1;
	private UtilSrvc utilSrvc;
	private SocketCommunication socket;
	private LocalPersonInfo personInfo = null;
	private Context context;
	private InscriptionInfo info;
	private Dialog processDialog;
	private Toast toast = null;
	private String resualt = null;
	private String text = null;

	private InscriptionSrvc(UtilSrvc service) {
		utilSrvc = service;
	}

	public final static InscriptionSrvc getInstance(UtilSrvc service) {
		if (inscriptionSrvc == null) {
			inscriptionSrvc = new InscriptionSrvc(service);
		}
		return inscriptionSrvc;
	}

	public void submit(Context ctx, Dialog pdialog,
			InscriptionInfo inscriptionInfo) {
		resualt = null;
		context = ctx;
		info = inscriptionInfo;
		processDialog = pdialog;
		personInfo = LocalPersonInfo.getInstance(context);
		socket = new SocketCommunication(this);
		socket.execute();
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

	public void checkInscripInfo(Context ctx, InscriptionInfo info) {
		if (!info.isAllSet()) {
			text = "抱歉，您的信息填写不全";
			showText(text);
			info.setComplete(false); // this part should be exist!
		} else if (!info.checkEmailAddress(info.getEmail())) {
			text = "抱歉，邮箱格式不符合要求";
			showText(text);
			info.setComplete(false); // this part should be exist!
		} else {
			info.setComplete(true);
		}
	}

	private void showText(String text) {
		toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public void onPreExecute() {
		processDialog.show();
		Window window = processDialog.getWindow();
		window.setGravity(Gravity.CENTER);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.5f;
		lp.width = 200;
		lp.height = 300;
		window.setAttributes(lp);
	}
	
	public Boolean doInBackground(String... params) {
		resualt = HttpRequest.getHttpResponseStr(
				HttpServiceType.INSCRIPT, info);
		System.out.println("Show me the userID got: " + resualt);
		if (resualt != null && !resualt.contains("-1")) {
			return true;
		}
		return false;
	}

	public void onProgressUpdate(Integer... values) {

	}

	public void onPostExecute(Boolean success) {
		processDialog.dismiss();
		if (success) {
			text = "恭喜，注册成功";
			showText(text);
			personInfo.setInscriptionInfo(info);
			personInfo.setUserID(resualt.substring(1, resualt.length() - 1));
			personInfo.showAllValue();
			utilSrvc.gotoLoggedInPage(context);
		} else {
			if (resualt.contains("-1")) {
				text = "邮箱已经被注册";
				showText(text);
			} else {
				text = "注册失败";
				showText(text);
			}

		}
	}

	public void onCancelled() {
		socket.cancel(true);
	}

}
