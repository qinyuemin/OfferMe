package com.offerme.client.service.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class ActivityManager extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();
	public static ActivityManager manager = null;

	private ActivityManager() {
	}

	public static ActivityManager getInstance() {
		if (manager == null) {
			manager = new ActivityManager();
		}
		return manager;
	}

	public void addNewActivity(Activity activity) {
		activityList.add(activity);
	}

	public void exit() {
		for (int count = 0; count < activityList.size(); count++) {
			activityList.get(count).finish();
		}
		//System.exit(0);
	}

}
