package com.offerme.server.database.model;

import java.util.HashMap;

public class Classement {
	
	/**
	 * CLASSMENT_ID: INT
	 */
	private int classmentId;
	/**
	 * NAME: VARCHAR(50) 分类名称
	 */
	private String name;
	/**
	 * LIB: VARCHAR(255) 分类说明
	 */
	private String lib;
	/**
	 * 属于分类的所有细分类型结合
	 */
	private HashMap<String, ClassementDetail> classementDetailMap;
	
	public int getClassmentId() {
		return classmentId;
	}
	public void setClassmentId(int classmentId) {
		this.classmentId = classmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLib() {
		return lib;
	}
	public void setLib(String lib) {
		this.lib = lib;
	}
	public HashMap<String, ClassementDetail> getClassementDetailMap() {
		return classementDetailMap;
	}
	public void setClassementDetailMap(HashMap<String, ClassementDetail> classementDetailMap) {
		this.classementDetailMap = classementDetailMap;
	}
	
	

}
