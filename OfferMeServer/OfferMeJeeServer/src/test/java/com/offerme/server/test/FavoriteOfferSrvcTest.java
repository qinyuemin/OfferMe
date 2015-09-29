package com.offerme.server.test;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.business.favorite.FavoriteOffer;
import com.offerme.server.model.db.OfferUser;
import com.offerme.server.service.FavoriteOfferSrvc;
import com.offerme.server.service.OfferUserSrvc;
import com.offerme.server.test.core.BaseTestCase;

public class FavoriteOfferSrvcTest extends BaseTestCase{
	
	@Autowired
	private FavoriteOfferSrvc favoriteOfferSrvc;
	
	@Autowired
	private OfferUserSrvc offerUserSrvc;
	
	@Test
	public void testSetFavorite()
	{
		// test Add
		FavoriteOffer favorite = new FavoriteOffer();
		favorite.setAdd(true);
		favorite.setOffer("14");
		favorite.setUser("45");
		
		String response = favoriteOfferSrvc.setFavoriteOffer(favorite);
		
		assertFalse(!"OK".equals(response));
		
		OfferUser offerUser = offerUserSrvc.getOfferUser(14, 45);
		assertFalse(offerUser == null);
		
		// test Remove
		favorite = new FavoriteOffer();
		favorite.setAdd(false);
		favorite.setOffer("14");
		favorite.setUser("45");
		
		response = favoriteOfferSrvc.setFavoriteOffer(favorite);
		
		assertFalse(!"OK".equals(response));
		
		offerUser = offerUserSrvc.getOfferUser(14, 45);
		assertFalse(offerUser != null);
		
	}

}
