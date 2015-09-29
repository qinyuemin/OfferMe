package com.offerme.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offerme.server.model.business.personalsetting.PersonalSetting;
import com.offerme.server.service.PersonalSettingSrvc;
import com.offerme.server.util.JsonUtil;

@Controller
public class PersonalSettingController {
	
	@Autowired
	private PersonalSettingSrvc srvc;
	
	@ResponseBody
	@RequestMapping("/personalsetting")
	public String savePersonalSettingOnServer(String param)
	{
		PersonalSetting p = JsonUtil.jsonToBean(param, PersonalSetting.class);
		return srvc.savePersonalSettingOnServer(p);
	}

}
