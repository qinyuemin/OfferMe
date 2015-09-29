package com.offerme.server.model.business.personinfo;

import java.util.ArrayList;

public class PersonalWorkInfo {

	private String firstWork = null;
	private String firstWorkPost = null;
	private String firstWorkyear = null;
	private String secondWork = null;
	private String secondWorkPost = null;
	private String secondWorkyear = null;
	private String thirdWork = null;
	private String thirdWorkPost = null;
	private String thirdWorkyear = null;
	private ArrayList<String> completeArray = new ArrayList<String>();

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

	public void setWorks(ArrayList<String> list) {
		completeArray = list;
	}

	public ArrayList<String> getWorks() {
		return completeArray;
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
