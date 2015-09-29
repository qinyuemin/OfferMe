package com.offerme.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offerme.server.dao.OfferUserDao;
import com.offerme.server.model.db.OfferUser;
import com.offerme.server.service.OfferUserSrvc;


@Service("offerUserSrvc")
@Transactional
public class OfferUserSrvcImpl implements OfferUserSrvc{
	
	@Autowired
	private OfferUserDao offerUserDao;

	@Override
	public OfferUser getOfferUser(Integer offerUserId) {
		return offerUserDao.get(offerUserId);
	}

	@Override
	public void updateOfferUser(OfferUser offerUser) {
		offerUserDao.update(offerUser);
	}

	@Override
	public Integer insertOfferUser(OfferUser offerUser) {
		return offerUserDao.save(offerUser);
	}

	@Override
	public void deleteOfferUser(Integer offerUserId) {
		offerUserDao.delete(offerUserId);
	}

	@Override
	public void deleteOfferUser(Integer offerId, Integer userId) {
		offerUserDao.deleteOfferUser(offerId, userId);
	}

	@Override
	public OfferUser getOfferUser(Integer offerId, Integer userId) {
		return offerUserDao.getOfferUser(offerId,userId);
	}

}
