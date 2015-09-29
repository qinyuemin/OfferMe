package com.offerme.server.model.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "cv")
public class Cv {
	
	private Integer cvId;
	private Integer userId;
	private Integer jobYear;
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
	private Date date;
	
	@Id 
	@GeneratedValue 
	@Column(name = "CV_ID")
	public Integer getCvId() {
		return cvId;
	}

	public void setCvId(Integer cvId) {
		this.cvId = cvId;
	}

	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "JOB_YEAR")
	public Integer getJobYear() {
		return jobYear;
	}

	public void setJobYear(Integer jobYear) {
		this.jobYear = jobYear;
	}

	@Column(name = "CURRENT_COMPANY")
	public String getCurrentCompany() {
		return currentCompany;
	}

	public void setCurrentCompany(String currentCompany) {
		this.currentCompany = currentCompany;
	}

	@Column(name = "CURRENT_POST")
	public String getCurrentPost() {
		return currentPost;
	}

	public void setCurrentPost(String currentPost) {
		this.currentPost = currentPost;
	}

	@Column(name = "DIPLOMA")
	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	@Column(name = "SCHOOL")
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "JOB_COMPANY_01")
	public String getJobCompany01() {
		return jobCompany01;
	}

	public void setJobCompany01(String jobCompany01) {
		this.jobCompany01 = jobCompany01;
	}

	@Column(name = "JOB_POST_01")
	public String getJobPost01() {
		return jobPost01;
	}

	public void setJobPost01(String jobPost01) {
		this.jobPost01 = jobPost01;
	}

	@Column(name = "JOB_YEAR_01")
	public String getJobYear01() {
		return jobYear01;
	}

	public void setJobYear01(String jobYear01) {
		this.jobYear01 = jobYear01;
	}

	@Column(name = "JOB_COMPANY_02")
	public String getJobCompany02() {
		return jobCompany02;
	}

	public void setJobCompany02(String jobCompany02) {
		this.jobCompany02 = jobCompany02;
	}

	@Column(name = "JOB_POST_02")
	public String getJobPost02() {
		return jobPost02;
	}

	public void setJobPost02(String jobPost02) {
		this.jobPost02 = jobPost02;
	}

	@Column(name = "JOB_YEAR_02")
	public String getJobYear02() {
		return jobYear02;
	}

	public void setJobYear02(String jobYear02) {
		this.jobYear02 = jobYear02;
	}

	@Column(name = "JOB_COMPANY_03")
	public String getJobCompany03() {
		return jobCompany03;
	}

	public void setJobCompany03(String jobCompany03) {
		this.jobCompany03 = jobCompany03;
	}

	@Column(name = "JOB_POST_03")
	public String getJobPost03() {
		return jobPost03;
	}

	public void setJobPost03(String jobPost03) {
		this.jobPost03 = jobPost03;
	}

	@Column(name = "JOB_YEAR_03")
	public String getJobYear03() {
		return jobYear03;
	}

	public void setJobYear03(String jobYear03) {
		this.jobYear03 = jobYear03;
	}
	
	@Column(name = "DATE")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
