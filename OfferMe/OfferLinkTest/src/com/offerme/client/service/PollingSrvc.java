package com.offerme.client.service;

import android.content.Context;

import com.offerme.client.http.HttpRequest;
import com.offerme.client.http.HttpServiceType;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.cv.PollingMessageList;

public class PollingSrvc {

	private static PollingSrvc chatSrvc = new PollingSrvc();

	private PollingSrvc() {
	}

	public static PollingSrvc getInstance() {
		return chatSrvc;
	}

	public PollingMessageList getComingMessages(Context ctx) {

		LocalPersonInfo personInfo = LocalPersonInfo.getInstance(ctx);
		if (personInfo.getValue(LocalPersonInfo.USERID) == null) {
			return null;
		}
		Integer userId = Integer.valueOf(personInfo
				.getValue(LocalPersonInfo.USERID));
		PollingMessageList comMsgs = HttpRequest.getHttpResponse(
				HttpServiceType.POLLING, userId, PollingMessageList.class);
		return comMsgs;
	}

}
