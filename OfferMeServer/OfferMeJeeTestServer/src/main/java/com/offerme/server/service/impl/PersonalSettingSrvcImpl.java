package com.offerme.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offerme.server.model.business.personalsetting.PersonalSetting;
import com.offerme.server.model.db.User;
import com.offerme.server.service.PersonalSettingSrvc;
import com.offerme.server.service.UserSrvc;

@Service("personalSettingSrvc")
@Transactional
public class PersonalSettingSrvcImpl implements PersonalSettingSrvc{

	@Autowired
	private UserSrvc userSrvc;
	
	
	@Override
	public String savePersonalSettingOnServer(PersonalSetting setting) {
		
		try {
			User user = userSrvc.getUserById(setting.getUserId());
			if (user != null) {
				convertPersonalSettingToUser(setting, user);
				userSrvc.update(user);
			}
		} catch (Exception e) {
			return "KO";
		}
		return "OK";
	}
	
	private void convertPersonalSettingToUser(PersonalSetting setting, User user) {
		// if update the portrait, PORTRAITUPDATETIME plus 1
		if (setting.getProfile() != null) {
			user.setPortrait(setting.getProfile());
			user.setPortraitUpdateCount(user.getPortraitUpdateCount() + 1);
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
