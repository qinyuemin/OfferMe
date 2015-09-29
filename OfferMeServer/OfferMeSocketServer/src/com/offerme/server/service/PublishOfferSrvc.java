package com.offerme.server.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.offerme.server.database.dao.OfferDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.database.model.Offer;
import com.offerme.server.service.bean.OfferInfo;
import com.offerme.server.util.JSONUtil;

public class PublishOfferSrvc {

	private Timestamp timestamp = null;

	public String publishOffer(String request) throws Exception {
		OfferDao dao = DaoImplManage.getOfferDaoInstance();
		OfferInfo info = JSONUtil.jsonToBean(request, OfferInfo.class);
		Offer offer = new Offer();
		offer.setUserId(Integer.parseInt(info.getUserID()));
		offer.setCompany(info.getEntreprise());
		offer.setCity(info.getCity());
		offer.setTitle(info.getPost());
		offer.setDate(getDate());
		offer.setContent(info.getDescription());
		offer.setOfferMail(info.getMailbox());
		offer.setTrade(info.getDomain());
		offer.setSalary(info.getSalary());
		offer.setWorkyear(info.getWorkyear());
		offer.setEducation(info.getEducation());
		int returnValue = -1;
		if (info.getOfferID() != null) {
			offer.setOfferId(Integer.valueOf(info.getOfferID()));
			dao.updateOffer(offer);
			returnValue = Integer.valueOf(info.getOfferID());
		} else {
			returnValue = dao.insertOffer(offer);
		}
		return getDate().toString().substring(0, 10) + " " + returnValue;
	}

	private Timestamp getDate() throws ParseException {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(ts);
		timestamp = new Timestamp(dateFormat.parse(date).getTime());
		return timestamp;
	}

}
