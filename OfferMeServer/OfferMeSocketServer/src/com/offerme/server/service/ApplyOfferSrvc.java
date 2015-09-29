package com.offerme.server.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.offerme.server.database.dao.ApplyInfoDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.database.model.ApplyInfo;
import com.offerme.server.service.bean.ApplyOfferMessage;
import com.offerme.server.util.JSONUtil;

public class ApplyOfferSrvc {
	private String OK = "ok";
	private String resualt = null;
	private Timestamp timestamp = null;
	private ApplyInfoDao applyDao = DaoImplManage.getApplyInfoDaoInstance();

	public String applyOffer(String request) throws Exception {
		ApplyOfferMessage applyMessage = JSONUtil.jsonToBean(request,
				ApplyOfferMessage.class);
		ApplyInfo info = new ApplyInfo();
		info.setOfferId(Integer.parseInt(applyMessage.getOfferID()));
		info.setApplyUserId(Integer.parseInt(applyMessage.getUserIDSend()));
		info.setOfferOwnerId(Integer.parseInt(applyMessage.getUserIDReceive()));
		info.setApplyDT(getDate());
		info.setStatus(0);
		try {
			if (applyDao.getApplyInfo(info.getApplyUserId(), info.getOfferId()) == null) {
				applyDao.insertApplyInfo(info);
			}
			resualt = OK;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resualt;
	}

	public Timestamp getDate() throws ParseException {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(ts);
		timestamp = new Timestamp(dateFormat.parse(date).getTime());
		return timestamp;
	}
}
