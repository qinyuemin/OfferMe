package com.offerme.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.offerme.server.dao.ApplyInfoDao;
import com.offerme.server.dao.OfferDao;
import com.offerme.server.dao.OfferUserDao;
import com.offerme.server.dao.UserDao;
import com.offerme.server.model.business.OfferBusinessBean;
import com.offerme.server.model.business.offer.OfferInfo;
import com.offerme.server.model.db.ApplyInfo;
import com.offerme.server.model.db.Offer;
import com.offerme.server.model.db.OfferUser;
import com.offerme.server.service.OfferSrvc;

@Service("offerSrvc")
@Transactional
public class OfferSrvcImpl implements OfferSrvc {

	@Autowired
	private OfferDao offerDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private OfferUserDao offerUserDao;

	@Autowired
	private ApplyInfoDao applyInfoDao;

	@Override
	public OfferBusinessBean getOfferById(Integer offerId) {

		OfferBusinessBean offer = null;
		try {
			Offer offerDb = offerDao.findById(offerId);
			if (offerDb != null) {
				offer = new OfferBusinessBean(offerDb);
				offer.setOfferOwner(userDao.findById(offerDb.getUserId()));
				offer.setFavoriteCount(getFavoriteCount(offerDb.getOfferId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return offer;
	}

	@Override
	public List<OfferBusinessBean> getOffersByUserId(Integer userId) {

		List<OfferBusinessBean> offers = new ArrayList<OfferBusinessBean>();
		try {
			List<Offer> offerDbs = offerDao.findByUserId(userId);
			completeBusinessOffer(offers, offerDbs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return offers;
	}

	@Override
	public List<OfferBusinessBean> getOffersByCityAndKeyword(String city,
			String keyword, String lastOfferID) {

		List<OfferBusinessBean> offers = new ArrayList<OfferBusinessBean>();
		try {
			List<Offer> offerDbs = offerDao.findByCityAndKeyword(city, keyword,
					lastOfferID);
			completeBusinessOffer(offers, offerDbs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return offers;
	}

	@Override
	public List<OfferBusinessBean> getAppliedOffersByUserId(Integer userId) {

		List<OfferBusinessBean> offers = new ArrayList<OfferBusinessBean>();
		try {
			List<ApplyInfo> applies = applyInfoDao.findByApplyUserId(userId);
			if (!CollectionUtils.isEmpty(applies)) {
				for (ApplyInfo apply : applies) {
					OfferBusinessBean offer = getOfferById(apply.getOfferId());
					if (offer != null) {
						offers.add(offer);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return offers;
	}

	@Override
	public List<OfferBusinessBean> getFavoriteOffersByUserId(Integer userId) {

		List<OfferBusinessBean> offers = new ArrayList<OfferBusinessBean>();
		try {
			List<OfferUser> favorites = offerUserDao.findByUserId(userId);
			if (!CollectionUtils.isEmpty(favorites)) {
				for (OfferUser favorite : favorites) {
					OfferBusinessBean offer = getOfferById(favorite
							.getOfferId());
					if (offer != null) {
						offers.add(offer);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return offers;
	}

	@Override
	public Integer saveOffer(Offer offer) {
		try {
			return offerDao.save(offer);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Integer saveOrUpdateOffer(Offer offer) {
		try {
			if (offer.getOfferId() == null) {
				return offerDao.save(offer);
			}

			offerDao.update(offer);
			return offer.getOfferId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Integer publishOffer(OfferInfo info) {
		try {
			Offer offer = new Offer();
			offer.setUserId(Integer.parseInt(info.getUserID()));
			offer.setCompany(info.getEntreprise());
			offer.setCity(info.getCity());
			offer.setTitle(info.getPost());
			offer.setCreDt(new Date());
			offer.setContent(info.getDescription());
			offer.setOfferMail(info.getMailbox());
			offer.setTrade(info.getDomain());
			offer.setSalary(info.getSalary());
			offer.setWorkyear(info.getWorkyear());
			offer.setEducation(info.getEducation());

			if (info.getOfferID() != null) {
				offer.setOfferId(Integer.parseInt(info.getOfferID()));
			}

			return saveOrUpdateOffer(offer);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void deleteOffer(Integer offerID) {
		try {
			offerDao.delete(offerID);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void completeBusinessOffer(List<OfferBusinessBean> offers,
			List<Offer> offerDbs) {
		try {
			if (!CollectionUtils.isEmpty(offerDbs)) {
				for (Offer offerDb : offerDbs) {
					OfferBusinessBean offer = new OfferBusinessBean(offerDb);
					offer.setOfferOwner(userDao.findById(offerDb.getUserId()));
					offer.setFavoriteCount(getFavoriteCount(offerDb
							.getOfferId()));
					offers.add(offer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Integer getFavoriteCount(Integer offerId) {
		try {
			return offerUserDao.findFavoriteCount(offerId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
