package com.offerme.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offerme.server.model.business.applyoffer.ApplyOfferRequest;
import com.offerme.server.model.db.ApplyInfo;
import com.offerme.server.service.ApplyOfferSrvc;
import com.offerme.server.service.ApplySrvc;
import com.offerme.server.service.OfferSrvc;
import com.offerme.server.util.Tool;

@Service("applyOfferSrvc")
@Transactional
public class ApplyOfferSrvcImpl implements ApplyOfferSrvc {

	@Autowired
	private ApplySrvc applySrvc;
	
	@Autowired
	private OfferSrvc offerSrvc;

	@Override
	public String applyOffer(ApplyOfferRequest request) {
		String strReturn = "";

		ApplyInfo info = new ApplyInfo();
		info.setOfferId(Tool.parseInt(request.getOfferID()));
		info.setApplyUserId(Tool.parseInt(request.getUserIDSend()));
		info.setOfferOwnerId(Tool.parseInt(request.getUserIDReceive()));
		info.setApplyDT(Tool.getNowDate());
		info.setStatus(0);
		try {
			if (offerSrvc.getOfferById(info.getOfferId()) != null) {
				if (applySrvc.getApplyInfo(info.getApplyUserId(),
						info.getOfferId()) == null) {
					applySrvc.saveApplyInfo(info);
				}
				strReturn = "OK";
			} else {
				strReturn = "KO";
			}

		} catch (Exception e) {
			e.printStackTrace();
			strReturn = "NOTOK";
		}
		return strReturn;
	}

}
