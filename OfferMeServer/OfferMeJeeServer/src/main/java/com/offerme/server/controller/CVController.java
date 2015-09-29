package com.offerme.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offerme.server.model.business.personinfo.PersonalCV;
import com.offerme.server.service.CvSrvc;
import com.offerme.server.util.JsonUtil;

@Controller
public class CVController {
	
	@Autowired
	private CvSrvc cvSrvc;
	
	@ResponseBody
	@RequestMapping("/saveCV")
	public String saveCV(String param)
	{
		PersonalCV pCV = JsonUtil.jsonToBean(param, PersonalCV.class);
		return cvSrvc.saveCV(pCV);
	}

}
