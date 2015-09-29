package com.offerme.server.database.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.List;

import com.offerme.server.database.model.ApplyInfo;
import com.offerme.server.database.model.CV;
import com.offerme.server.database.model.Offer;

public interface ApplyInfoDao extends BaseDao {

	public void insertApplyInfo(ApplyInfo applyInfo)
			throws InvocationTargetException;

	public void updateApplyInfo(ApplyInfo applyInfo)
			throws InvocationTargetException;

	public void deleteApplyInfo(int userId, int offerId)
			throws InvocationTargetException;

	public ApplyInfo getApplyInfo(int userId, int offerId)
			throws InvocationTargetException;

	public List<Offer> getPersonApplyOffer(int userId)
			throws InvocationTargetException;

	public List<Hashtable<CV, Integer>> getCommingCV(
			int userId) throws InvocationTargetException;

}
