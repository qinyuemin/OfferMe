package com.offerme.server.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.business.login.LoginResponse;
import com.offerme.server.service.LoginSrvc;
import com.offerme.server.test.core.BaseTestCase;

public class LoginSrvcTest extends BaseTestCase {
	
	@Autowired
	private LoginSrvc loginSrvc;

	@Test
	public void testLogin() {
		LoginResponse res = loginSrvc.login("yuemin.qin@yahoo.com", "123456");
		assertEquals(new Integer(37), res.getUserId());
		assertEquals("hhghj", res.getCv().getColleage());
		assertEquals(5, res.getPublishOffer().size());
		assertEquals("43", res.getFavoriteOffer().get(0).getOfferID());
		assertEquals("43", res.getApplyOffer().get(0).getOfferID());
	}

}
