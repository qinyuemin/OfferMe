package com.offerme.server.service;

import java.lang.reflect.InvocationTargetException;

import com.offerme.server.database.dao.UserDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.database.model.User;
import com.offerme.server.service.bean.InscriptionInfo;
//import com.offerme.server.util.EncoderUtil;
import com.offerme.server.util.JSONUtil;

public class InscriptionSrvc {

	public String inscriptUser(String request) {
		UserDao dao = DaoImplManage.getUserDaoInstance();
		InscriptionInfo info = JSONUtil.jsonToBean(request,
				InscriptionInfo.class);
		User user = new User();
		// user.setNameFamily("");
		user.setName(info.getName());
		user.setCity(info.getCity());
		user.setCompany(info.getEntreprise());
		user.setEmail(info.getEmail());
		user.setPhone(info.getTelephoneNumber());
		user.setPsw(info.getPassword());
		user.setPost(info.getPost());
		user.setAge(Integer.parseInt(info.getAge()));
		user.setWorkyear(Integer.parseInt(info.getWorkyear()));
		// Should Use Md5 encrypt the psw after system test
		// user.setPsw(EncoderUtil.encryptMD5(info.getPassword()));
		user.setPortrait(info.getPhotoByte());
		user.setEmailPublic(isPublic(info.isEmailPublished()));
		user.setPhonePublic(isPublic(info.isPhonePublished()));
		int id = -1;
		try {
			id = dao.insertUser(user);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			if (e.getCause().toString().contains("邮箱已经被注册")) {
				return "-1";
			}
		}
		return String.valueOf(id);
	}

	private int isPublic(boolean boolValue) {
		int returnValue = 0;
		if (boolValue) {
			returnValue = 1;
		}
		return returnValue;
	}
}
