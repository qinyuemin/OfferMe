package com.offerme.server.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.db.ApplyInfo;
import com.offerme.server.service.ApplySrvc;
import com.offerme.server.test.core.BaseTestCase;

public class ApplySrvcTest extends BaseTestCase {

	@Autowired
	private ApplySrvc applySrvc;
	
	@Test
	public void testGetApplyInfosByApplyUserId() {
		List<ApplyInfo> applies = applySrvc.getApplyInfosByApplyUserId(37);
		assertEquals(new Integer(43), applies.get(0).getOfferId());
	}
}
