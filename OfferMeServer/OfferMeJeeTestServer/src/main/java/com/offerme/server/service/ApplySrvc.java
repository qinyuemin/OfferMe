package com.offerme.server.service;

import java.util.Hashtable;
import java.util.List;

import com.offerme.server.model.db.ApplyInfo;
import com.offerme.server.model.db.Cv;

public interface ApplySrvc {

	List<ApplyInfo> getApplyInfosByApplyUserId(Integer applyUserId);
	
	List<ApplyInfo> getApplyInfosByOwnerUserId(Integer offerOwnerId);
	
	ApplyInfo getApplyInfo(int userId, int offerId);
	
	Integer saveApplyInfo(ApplyInfo info);
	
	List<Hashtable<Cv, Integer>> getCommingCV(
			int userId);

}
