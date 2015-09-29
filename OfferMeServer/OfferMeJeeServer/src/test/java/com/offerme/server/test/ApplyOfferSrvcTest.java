package com.offerme.server.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.business.applyoffer.ApplyOfferRequest;
import com.offerme.server.model.db.ApplyInfo;
import com.offerme.server.service.ApplyOfferSrvc;
import com.offerme.server.service.ApplySrvc;
import com.offerme.server.test.core.BaseTestCase;
import com.offerme.server.util.JsonUtil;
import com.offerme.server.util.Tool;

public class ApplyOfferSrvcTest  extends BaseTestCase{

	@Autowired
	private ApplyOfferSrvc applyOfferSrvc;
	
	@Autowired
	private ApplySrvc applySrvc;
	
	@Test
	public void testApplyOffer()
	{
		String test = "{userIDSend:37,offerID:44,offerOwnerId:1}";
		ApplyOfferRequest request = JsonUtil.jsonToBean(test, ApplyOfferRequest.class);
		String response = applyOfferSrvc.applyOffer(request);
		assertFalse(!"ok".equals(response));
		
		ApplyInfo applyInfo = applySrvc.getApplyInfo(Tool.parseInt(request.getUserIDSend()) , Tool.parseInt(request.getOfferID()));
		assertFalse(applyInfo == null);
		assertEquals(applyInfo.getApplyUserId().intValue(), 37);
		assertEquals(applyInfo.getOfferOwnerId().intValue(), 1);
		assertEquals(applyInfo.getOfferId().intValue(), 44);
	}
}
