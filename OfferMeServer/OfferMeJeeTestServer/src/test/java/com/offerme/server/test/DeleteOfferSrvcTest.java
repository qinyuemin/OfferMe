package com.offerme.server.test;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.db.OfferUser;
import com.offerme.server.service.DeleteOfferSrvc;
import com.offerme.server.service.OfferUserSrvc;
import com.offerme.server.test.core.BaseTestCase;

public class DeleteOfferSrvcTest  extends BaseTestCase{
	
	@Autowired
	private DeleteOfferSrvc deleteOfferSrvc;
	
	@Autowired
	private OfferUserSrvc offerUserSrvc;
	
	@Test
	public void testDeleteOffer()
	{
		// Insert first
		OfferUser offerUser = new OfferUser();
		offerUser.setOfferId(14);
		offerUser.setUserId(45);
		offerUserSrvc.insertOfferUser(offerUser);
		
		// delete
		String response = deleteOfferSrvc.deleteOffer("14", "45");
		assertFalse(!"ok".equals(response));
		
		// check
		offerUser = offerUserSrvc.getOfferUser(14, 45);
		assertFalse(offerUser != null);
	}
	

}
