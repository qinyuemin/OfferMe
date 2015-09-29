package com.offerme.client.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.offerme.R;
import com.offerme.client.localdata.LocalDataBase;
import com.offerme.client.service.LoginSrvc;
import com.offerme.client.service.UtilSrvc;
import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LoadingActivity extends ActionBarActivity {

	private Handler handler = new Handler();
	private LoginSrvc loginSrvc = LoginSrvc.getInstance();
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private LocalDataBase localDatabase = null;
	private ImageView image;
	private LocationClient mLocationClient = null;
	private MyLocationListener mMyLocationListener;
	private String mPageName = "LoadingActivity";
	private Context context = this;
	private boolean hasLoggedIn = false;
	private long startTime = 0;
	private long endTime = 0;
	private int LIMITTIME = 5000;
	private int UPDATETIME = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		utilSrvc.addNewActivity(this);
		updateDatabase();
		startAnalystic();
		initView();
		getCurrentCity();
		if (loginSrvc.isLoggedInOnce(this)) {
			hasLoggedIn = true;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(mPageName);
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

	private void updateDatabase() {
		localDatabase = LocalDataBase.getInstance(this);
		localDatabase.updateUserTable(utilSrvc.getUserColumName());
		localDatabase.updateOfferTable(utilSrvc.getOfferColumName());
		localDatabase.updateMessageTable(utilSrvc.getMessageColumName());
		localDatabase.updateFriendTable(utilSrvc.getFriendColumName());
		localDatabase.updateCVTable(utilSrvc.getCVColumName());
	}

	private void startAnalystic() {
		MobclickAgent.setDebugMode(true);
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.updateOnlineConfig(this);
	}

	private void initView() {
		image = (ImageView) findViewById(R.id.loading_logo);
		doAnimation(this, image, R.anim.laoding);
		startTime = System.currentTimeMillis();
	}

	private void getCurrentCity() {
		if (utilSrvc.isNetworkConnected(this)) {
			mLocationClient = new LocationClient(this.getApplicationContext());
			mMyLocationListener = new MyLocationListener();
			LocationClientOption option = new LocationClientOption();
			option.setLocationMode(LocationMode.Battery_Saving);
			option.setOpenGps(true);
			option.setCoorType("gcj02");
			option.setScanSpan(UPDATETIME);
			option.setIsNeedAddress(true);
			mLocationClient.setLocOption(option);
			mLocationClient.registerLocationListener(mMyLocationListener);
			mLocationClient.start();
		} else {
			startActivity();
		}
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location != null) {
				double latitude = location.getLatitude();
				double longtitude = location.getLongitude();
				endTime = System.currentTimeMillis();
				if ((latitude != 0 || longtitude != 0)
						&& (endTime - startTime < LIMITTIME)) {
					mLocationClient.stop();
					utilSrvc.setCurrentCity(location.getCity());
					startActivity();
					return;
				}
			}
			mLocationClient.stop();
			startActivity();

		}
	}

	private void startActivity() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent();
				if (hasLoggedIn) {
					intent = new Intent(context, LoggedInActivity.class);
				} else {
					intent = new Intent(context, HomePageActivity.class);
				}
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.enter, R.anim.exit);
			}
		}, 2000);
	}

	public void doAnimation(Context context, View view, int animId) {
		Animation animation = AnimationUtils.loadAnimation(context, animId);
		view.startAnimation(animation);
	}

}
