package com.offerme.server.model.business.message;

import java.util.ArrayList;
import java.util.List;

import com.offerme.server.model.business.personinfo.PersonalCV;

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

	public void setCVList(List<PersonalCV> cv) {
		this.cvList = cv;
	}

}
