package com.offerme.server.database.model;

import java.util.HashMap;

public class ClassementDetail {
	
	/**
	 * CLASSMENT_DETAIL_ID: INT
	 */
	private int classementDetailId;
	/**
	 * CLASSEMENT_ID: INT 分类ID
	 */
	private int classementId;
	/**
	 * P_CLASSEMENT_DETAIL_ID: INT 分类父ID
	 */
	private int pClassementDetailId;
	/**
	 * NAME:VARCHAR(50) 细分类名称
	 */
	private String name;
	/**
	 * LIB: VARCHAR(255) 细分类说明
	 */
	private String lib;
	/**
	 * 所属分类对象
	 */
	private Classement classement;
	/**
	 * 细分类型父对象
	 */
	private ClassementDetail upClassementDetail;
	/**
	 * 细分类型子对象集
	 */
	private HashMap<String, ClassementDetail> subClassementDetailMap;
	
	public int getClassementDetailId() {
		return classementDetailId;
	}
	public void setClassementDetailId(int classementDetailId) {
		this.classementDetailId = classementDetailId;
	}
	public int getClassementId() {
		return classementId;
	}
	public void setClassementId(int classementId) {
		this.classementId = classementId;
	}
	public int getpClassementDetailId() {
		return pClassementDetailId;
	}
	public void setpClassementDetailId(int pClassementDetailId) {
		this.pClassementDetailId = pClassementDetailId;
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
	public Classement getClassement() {
		return classement;
	}
	public void setClassement(Classement classement) {
		this.classement = classement;
	}
	public ClassementDetail getUpClassementDetail() {
		return upClassementDetail;
	}
	public void setUpClassementDetail(ClassementDetail upClassementDetail) {
		this.upClassementDetail = upClassementDetail;
	}
	public HashMap<String, ClassementDetail> getSubClassementDetailMap() {
		return subClassementDetailMap;
	}
	public void setSubClassementDetailMap(
			HashMap<String, ClassementDetail> subClassementDetailMap) {
		this.subClassementDetailMap = subClassementDetailMap;
	}
	
	

}
