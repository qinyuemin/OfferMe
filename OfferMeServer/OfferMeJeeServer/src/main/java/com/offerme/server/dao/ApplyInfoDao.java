package com.offerme.server.dao;

import java.util.List;

import com.offerme.server.dao.core.BaseDao;
import com.offerme.server.model.db.ApplyInfo;

public interface ApplyInfoDao extends BaseDao<ApplyInfo> {

	List<ApplyInfo> findByApplyUserId(Integer applyUserId);

	public List<ApplyInfo> findByOwnerUserId(Integer offerOwnerId);
	
	public ApplyInfo getApplyInfo(int userId, int offerId);
}
