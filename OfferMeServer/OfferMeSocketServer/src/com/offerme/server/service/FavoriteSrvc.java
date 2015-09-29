package com.offerme.server.service;

import java.lang.reflect.InvocationTargetException;

import com.offerme.server.database.dao.OfferUserDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.database.model.OfferUser;
import com.offerme.server.util.JSONUtil;

public class FavoriteSrvc {
	private OfferUserDao offeruserdao = DaoImplManage.getOfferUserDaoInstance();

	public String setFavoriteOffer(String request) {
		OfferUser favorite = new OfferUser();
		FavoriteOfferBean info = JSONUtil.jsonToBean(request,
				FavoriteOfferBean.class);
		favorite.setOfferId(Integer.parseInt(info.getOffer()));
		favorite.setUserId(Integer.parseInt(info.getUser()));
		try {
			if (info.isAdd()) {
				if (offeruserdao.getOfferUser(favorite.getOfferId(),
						favorite.getUserId()) == null) {
					offeruserdao.insertOfferUser(favorite);
				}
			} else {
				offeruserdao.deleteOfferUser(favorite);
			}
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			return null;
		}
		return "OK";
	}

	private class FavoriteOfferBean {
		private String user;
		private String offer;
		private boolean isAdd;

		public String getUser() {
			return user;
		}

		public String getOffer() {
			return offer;
		}

		public boolean isAdd() {
			return isAdd;
		}
	}

}
