package com.offerme.client.service.cv;

import java.io.Serializable;
import java.util.ArrayList;

public class PersonalWorkInfo implements Serializable {

	private static final long serialVersionUID = 7091837232548346556L;
	private String firstWork = null;
	private String firstWorkPost = null;
	private String firstWorkyear = null;
	private String secondWork = null;
	private String secondWorkPost = null;
	private String secondWorkyear = null;
	private String thirdWork = null;
	private String thirdWorkPost = null;
	private String thirdWorkyear = null;
	private boolean isFirstWorkComplete = false;
	private boolean isSecondWorkComplete = false;
	private boolean isThirdWorkComplete = false;
	private ArrayList<String> completeArray = new ArrayList<String>();

	public void setWorks() {
		if (isFirstWorkComplete()) {
			completeArray.add(firstWork);
			completeArray.add(firstWorkyear);
			completeArray.add(firstWorkPost);
		}
		if (isSecondWorkComplete()) {
			completeArray.add(secondWork);
			completeArray.add(secondWorkyear);
			completeArray.add(secondWorkPost);
		}
		if (isThirdWorkComplete()) {
			completeArray.add(thirdWork);
			completeArray.add(thirdWorkyear);
			completeArray.add(thirdWorkPost);
		}
	}

	public void setWorks(ArrayList<String> list) {
		clearWorks();
		completeArray = new ArrayList<String>(list);
		for (int count = 0; count < completeArray.size();) {
			if (count < 3) {
				isFirstWorkComplete = true;
				firstWork = list.get(count);
				firstWorkyear = list.get(count + 1);
				firstWorkPost = list.get(count + 2);
			} else if (count < 6) {
				isSecondWorkComplete = true;
				secondWork = list.get(count);
				secondWorkyear = list.get(count + 1);
				secondWorkPost = list.get(count + 2);
			} else if (count < 9) {
				isThirdWorkComplete = true;
				thirdWork = list.get(count);
				thirdWorkyear = list.get(count + 1);
				thirdWorkPost = list.get(count + 2);
			}
			count = count + 3;
		}
	}

	public ArrayList<String> getWorks() {
		return completeArray;
	}

	public void clearWorks() {
		completeArray=null;
		firstWork = null;
		firstWorkyear = null;
		firstWorkPost = null;
		secondWork = null;
		secondWorkyear = null;
		secondWorkPost = null;
		thirdWork = null;
		thirdWorkyear = null;
		thirdWorkPost = null;
		isFirstWorkComplete = false;
		isSecondWorkComplete = false;
		isThirdWorkComplete = false;
	}

	public boolean isFirstWorkComplete() {
		if (firstWork != null && firstWorkyear != null && firstWorkPost != null
				&& firstWork.length() != 0 && firstWorkyear.length() != 0
				&& firstWorkPost.length() != 0) {
			isFirstWorkComplete = true;
		}
		return isFirstWorkComplete;
	}

	public boolean isSecondWorkComplete() {
		if (secondWork != null && secondWorkyear != null
				&& secondWorkPost != null && secondWork.length() != 0
				&& secondWorkyear.length() != 0 && secondWorkPost.length() != 0) {
			isSecondWorkComplete = true;
		}
		return isSecondWorkComplete;
	}

	public boolean isThirdWorkComplete() {
		if (thirdWork != null && thirdWorkyear != null && thirdWorkPost != null
				&& thirdWork.length() != 0 && thirdWorkyear.length() != 0
				&& thirdWorkPost.length() != 0) {
			isThirdWorkComplete = true;
		}
		return isThirdWorkComplete;
	}

	public String getFirstWorkPost() {
		return firstWorkPost;
	}

	public void setFirstWorkPost(String firstWorkPost) {
		this.firstWorkPost = firstWorkPost;
	}

	public String getSecondWorkPost() {
		return secondWorkPost;
	}

	public void setSecondWorkPost(String secondWorkPost) {
		this.secondWorkPost = secondWorkPost;
	}

	public String getThirdWorkPost() {
		return thirdWorkPost;
	}

	public void setThirdWorkPost(String thirdWorkPost) {
		this.thirdWorkPost = thirdWorkPost;
	}

	public String getFirstWork() {
		return firstWork;
	}

	public void setFirstWork(String firstWork) {
		this.firstWork = firstWork;
	}

	public String getFirstWorkyear() {
		return firstWorkyear;
	}

	public void setFirstWorkyear(String firstWorkyear) {
		this.firstWorkyear = firstWorkyear;
	}

	public String getSecondWork() {
		return secondWork;
	}

	public void setSecondWork(String secondWork) {
		this.secondWork = secondWork;
	}

	public String getSecondWorkyear() {
		return secondWorkyear;
	}

	public void setSecondWorkyear(String secondWorkyear) {
		this.secondWorkyear = secondWorkyear;
	}

	public String getThirdWork() {
		return thirdWork;
	}

	public void setThirdWork(String thirdWork) {
		this.thirdWork = thirdWork;
	}

	public String getThirdWorkyear() {
		return thirdWorkyear;
	}

	public void setThirdWorkyear(String thirdWorkyear) {
		this.thirdWorkyear = thirdWorkyear;
	}
}
