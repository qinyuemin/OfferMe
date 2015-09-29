package com.offerme.server.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offerme.server.dao.ApplyInfoDao;
import com.offerme.server.dao.CvDao;
import com.offerme.server.model.db.ApplyInfo;
import com.offerme.server.model.db.Cv;
import com.offerme.server.service.ApplySrvc;

@Service("applySrvc")
@Transactional
public class ApplySrvcImpl implements ApplySrvc {
	
	@Autowired
	private ApplyInfoDao applyInfoDao;
	
	@Autowired
	private CvDao cvDao;

	@Override
	public List<ApplyInfo> getApplyInfosByApplyUserId(Integer applyUserId) {
		return applyInfoDao.findByApplyUserId(applyUserId);
	}
	
	@Override
	public List<ApplyInfo> getApplyInfosByOwnerUserId(Integer offerOwnerId) {
		return applyInfoDao.findByOwnerUserId(offerOwnerId);
	}
	
	@Override
	public ApplyInfo getApplyInfo(int userId, int offerId){
		return applyInfoDao.getApplyInfo(userId, offerId);
	}
	
	@Override
	public Integer saveApplyInfo(ApplyInfo info){
		return applyInfoDao.save(info);
	}

	@Override
	public List<Hashtable<Cv, Integer>> getCommingCV(int offerOwnerId) {
		
		List<Hashtable<Cv, Integer>> cvList = new ArrayList<Hashtable<Cv, Integer>>();
		List<ApplyInfo> applyInfoList = applyInfoDao.findByOwnerUserId(offerOwnerId);
		for (ApplyInfo applyInfo : applyInfoList) {
			Hashtable<Cv, Integer> cvInfo = new Hashtable<Cv, Integer>();
			Cv cv = cvDao.findByUserId(applyInfo.getApplyUserId());
			if (cv != null) {
				cv.setDate(applyInfo.getApplyDT());
				cvInfo.put(cv, applyInfo.getOfferId());
				cvList.add(cvInfo);
			}
			applyInfo.setStatus(1);
			applyInfoDao.update(applyInfo);
		}
		
		return cvList;
	}
}
