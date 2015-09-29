package com.offerme.client.service;

import java.util.ArrayList;

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
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.cv.PersonalCV;
import com.offerme.client.service.socket.SocketCommunication;

public class SaveCVSrvc implements SocketSrvc {

	private static SaveCVSrvc saveSrvc = null;
	private LocalPersonInfo personInfo = null;
	private UtilSrvc utilSrvc;
	private PersonalCV cv;
	private SocketCommunication socket;
	private Context context;
	private Dialog processDialog;
	private Toast toast;

	private SaveCVSrvc(UtilSrvc service) {
		this.utilSrvc = service;
	}

	public static SaveCVSrvc getInstance(UtilSrvc service) {
		if (saveSrvc == null) {
			saveSrvc = new SaveCVSrvc(service);
		}
		return saveSrvc;
	}

	public void save() {
		personInfo = LocalPersonInfo.getInstance(context);
		setProcessDialog();
		socket = new SocketCommunication(this);
		socket.execute();
	}

	public boolean checkInputInfo(Context ctx, PersonalCV personcv) {
		this.context = ctx;
		cv = personcv;
		if (cv.getEducation() == null || cv.getColleage() == null
				|| cv.getEducation().length() == 0 || cv.getWorkInfo() == null
				|| cv.getWorkInfo().getWorks().size() == 0) {
			showText(context.getResources().getString(
					R.string.personal_cv_complete));
			return false;
		}
		return true;
	}

	private void showText(String text) {
		toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	private void setProcessDialog() {
		LayoutInflater factory = LayoutInflater.from(context);
		View dialogView = factory.inflate(R.layout.dialog_process, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		processDialog = builder.create();
		((AlertDialog) processDialog).setView(dialogView, 0, 0, 0, 0);
	}

	private void updatePersonInfo() {
		insertWorks();
		insertEducation();
		insertColleage();
	}

	private void insertColleage() {
		personInfo.setColleage(cv.getColleage());
	}

	private void insertEducation() {
		personInfo.setEducation(cv.getEducation());
	}

	private void insertWorks() {
		personInfo.clearWorks();
		ArrayList<String> works = cv.getWorkInfo().getWorks();
		for (int count = 0; count < works.size();) {
			if (count < 3) {
				personInfo.setFirstWork(works.get(count), works.get(count + 1),
						works.get(count + 2));
			} else if (count < 6) {
				personInfo.setSecondWork(works.get(count),
						works.get(count + 1), works.get(count + 2));
			} else if (count < 9) {
				personInfo.setThirdWork(works.get(count), works.get(count + 1),
						works.get(count + 2));
			}
			count = count + 3;
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
		String rlt = HttpRequest.getHttpResponseStr(
				HttpServiceType.SAVE_CV, cv);
		System.out.println("SaveCV->doInBackground: Show me the reply got: " + rlt);
		if (rlt != null) {
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
			updatePersonInfo();
			showText(context.getResources().getString(
					R.string.personal_cv_save_success));
			utilSrvc.gotoLoggedInFromUser(context);
		} else {
			showText(context.getResources().getString(
					R.string.personal_cv_save_fail));
		}
	}

	@Override
	public void onCancelled() {
		socket.cancel(true);
	}

}
