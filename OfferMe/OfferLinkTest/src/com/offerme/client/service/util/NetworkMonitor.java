package com.offerme.client.service.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

public class NetworkMonitor {

	public boolean isNetworkConnected(Context ctx) {
		ConnectivityManager connectManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectManager != null) {
			NetworkInfo wifiInfo = connectManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			NetworkInfo mobileInfo = connectManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (wifiInfo.isConnected() || mobileInfo.isConnected()) {
				return true;
			}
		}
		return false;
	}

	public void showConnectDialog(final Context ctx) {

		// 方案一：使用Dialog
		/*
		 * AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		 * builder.setTitle("提示").setIcon(android.R.drawable.ic_dialog_alert)
		 * .setMessage("设置网络") .setPositiveButton("确定", new OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) {
		 * Intent intent = new Intent( Settings.ACTION_WIRELESS_SETTINGS);
		 * ctx.startActivity(intent); } }).setNegativeButton("取消", new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) {
		 * dialog.dismiss(); } }).setCancelable(true); builder.create().show();
		 */

		// 方案二：使用toast
		Toast toast = Toast.makeText(ctx, "网络不给力啊", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

}
