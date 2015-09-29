package com.offerme.server.service;

import com.offerme.server.database.dao.UserDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.database.model.User;
import com.offerme.server.service.bean.PersonalSetting;

public class PersonalSettingSrvc {

	public String savePersonalSettingOnServer(PersonalSetting setting) {
		if (setting == null || setting.getUserId() == null) {
			return "KO";
		}
		UserDao dao = DaoImplManage.getUserDaoInstance();
		User user = null;
		try {
			user = dao.getUser(setting.getUserId());
			if (user != null) {
				convertPersonalSettingToUser(setting, user);
				dao.updateUser(user);
			}
		} catch (Exception e1) {
			return "KO";
		}
		return "OK";
	}

	private void convertPersonalSettingToUser(PersonalSetting setting, User user) {
		// if update the portrait, PORTRAITUPDATETIME plus 1
		if (setting.getProfile() != null) {
			user.setPortrait(setting.getProfile());
			user.setPortraitUpdateDt(user.getPortraitUpdateDt() + 1);
		}
		user.setName(setting.getName());
		user.setCity(setting.getCity());
		user.setCompany(setting.getEnterprise());
		user.setPost(setting.getPost());
		user.setWorkyear(Integer.parseInt(setting.getWorkyear()));
		user.setAge(Integer.parseInt(setting.getAge()));
		user.setPhone(setting.getPhoneNumber());
		user.setEmail(setting.getEmail());
		user.setPhonePublic(isPublic(setting.isPhonePublic()));
		user.setEmailPublic(isPublic(setting.isEmailPublic()));
	}

	private int isPublic(boolean isPublic) {
		if (isPublic) {
			return 1;
		}
		return 0;
	}
}
