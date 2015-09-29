package com.offerme.server.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.business.OfferBusinessBean;
import com.offerme.server.model.db.User;
import com.offerme.server.service.OfferSrvc;
import com.offerme.server.test.core.BaseTestCase;

public class OfferSrvcTest extends BaseTestCase {

	@Autowired
	private OfferSrvc offerSrvc;
	
	@Test
	public void testGetOfferById() {
		OfferBusinessBean offer = offerSrvc.getOfferById(22);
		User offerOwner = offer.getOfferOwner();
		assertEquals(new Integer(37), offer.getUserId());
		assertEquals("yuemin.qin@yahoo.com", offerOwner.getEmail());
	}
	
	@Test
	public void testGetOfferByUserId() {
		List<OfferBusinessBean> offers = offerSrvc.getOffersByUserId(37);
		assertEquals(5, offers.size());
	}
	
	@Test
	public void testGetAppliedOffersByUserId() {
		List<OfferBusinessBean> offers = offerSrvc.getAppliedOffersByUserId(37);
		assertEquals(new Integer(43), offers.get(0).getOfferId());
	}
	
	@Test
	public void testGetFavoriteOffersByUserId() {
		List<OfferBusinessBean> offerUsers = offerSrvc.getFavoriteOffersByUserId(37);
		assertEquals(new Integer(43), offerUsers.get(0).getOfferId());
	}
	
	@Test
	public void testGetOffersByCityAndKeyword() {
		/*List<OfferBusinessBean> offers = offerSrvc.getOffersByCityAndKeyword("上海", "ceo");
		assertEquals(new Integer(23), offers.get(0).getOfferId());
		assertEquals(new Integer(37), offers.get(0).getOfferOwner().getUserId());
		
		offers = offerSrvc.getOffersByCityAndKeyword("", "微软");
		assertEquals(2, offers.size());
		
		offers = offerSrvc.getOffersByCityAndKeyword("上海", "");
		assertEquals(10, offers.size());
		
		offers = offerSrvc.getOffersByCityAndKeyword("", "");
		assertEquals(17, offers.size());*/
	}
	
	@Test
	public void testGetFavoriteCount() {
		Integer count = offerSrvc.getFavoriteCount(22);
		assertEquals(new Integer(1), count);
		
		count = offerSrvc.getFavoriteCount(99);
		assertEquals(new Integer(0), count);
	}
}
