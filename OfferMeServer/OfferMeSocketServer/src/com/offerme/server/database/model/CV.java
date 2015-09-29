package com.offerme.server.database.model;

import java.sql.Timestamp;

public class CV {

	private int cvId;

	private int userId;

	private int jobYear;

	private Timestamp date;

	private String currentCompany;

	private String currentPost;

	private String diploma;

	private String school;

	private String jobCompany01;

	private String jobPost01;

	private String jobYear01;

	private String jobCompany02;

	private String jobPost02;

	private String jobYear02;

	private String jobCompany03;

	private String jobPost03;

	private String jobYear03;

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getCvId() {
		return cvId;
	}

	public void setCvId(int cvId) {
		this.cvId = cvId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getJobYear() {
		return jobYear;
	}

	public void setJobYear(int jobYear) {
		this.jobYear = jobYear;
	}

	public String getCurrentCompany() {
		return currentCompany;
	}

	public void setCurrentCompany(String currentCompany) {
		this.currentCompany = currentCompany;
	}

	public String getCurrentPost() {
		return currentPost;
	}

	public void setCurrentPost(String currentPost) {
		this.currentPost = currentPost;
	}

	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getJobCompany01() {
		return jobCompany01;
	}

	public void setJobCompany01(String jobCompany01) {
		this.jobCompany01 = jobCompany01;
	}

	public String getJobPost01() {
		return jobPost01;
	}

	public void setJobPost01(String jobPost01) {
		this.jobPost01 = jobPost01;
	}

	public String getJobYear01() {
		return jobYear01;
	}

	public void setJobYear01(String jobYear01) {
		this.jobYear01 = jobYear01;
	}

	public String getJobCompany02() {
		return jobCompany02;
	}

	public void setJobCompany02(String jobCompany02) {
		this.jobCompany02 = jobCompany02;
	}

	public String getJobPost02() {
		return jobPost02;
	}

	public void setJobPost02(String jobPost02) {
		this.jobPost02 = jobPost02;
	}

	public String getJobYear02() {
		return jobYear02;
	}

	public void setJobYear02(String jobYear02) {
		this.jobYear02 = jobYear02;
	}

	public String getJobCompany03() {
		return jobCompany03;
	}

	public void setJobCompany03(String jobCompany03) {
		this.jobCompany03 = jobCompany03;
	}

	public String getJobPost03() {
		return jobPost03;
	}

	public void setJobPost03(String jobPost03) {
		this.jobPost03 = jobPost03;
	}

	public String getJobYear03() {
		return jobYear03;
	}

	public void setJobYear03(String jobYear03) {
		this.jobYear03 = jobYear03;
	}

}
