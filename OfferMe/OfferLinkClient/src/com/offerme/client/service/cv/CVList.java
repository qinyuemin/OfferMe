package com.offerme.client.service.cv;

import java.util.ArrayList;
import java.util.List;

public class CVList {
	private int responseCode = -1;
	private List<PersonalCV> cvList;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public List<PersonalCV> getCVList() {
		if (cvList == null) {
			cvList = new ArrayList<PersonalCV>();
		}
		return cvList;
	}

	public void setCVList(List<PersonalCV> chatMessageList) {
		this.cvList = chatMessageList;
	}
}
