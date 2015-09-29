package com.offerme.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offerme.server.model.business.inscription.InscriptionInfo;
import com.offerme.server.model.business.login.LoginRequest;
import com.offerme.server.model.business.login.LoginResponse;
import com.offerme.server.service.ForgetPasswordSrvc;
import com.offerme.server.service.LoginSrvc;
import com.offerme.server.service.UserSrvc;
import com.offerme.server.util.JsonUtil;

@Controller
public class UserController {

	@Autowired
	private LoginSrvc loginSrvc;

	@Autowired
	private UserSrvc userSrvc;

	@Autowired
	private ForgetPasswordSrvc forgetPwdSrvc;

	@ResponseBody
	@RequestMapping("/login")
	public LoginResponse login(String param) {
		LoginRequest req = JsonUtil.jsonToBean(param, LoginRequest.class);
		return loginSrvc.login(req.getLogin(), req.getPassword());
	}

	@ResponseBody
	@RequestMapping("/inscript")
	public String inscript(String param) {

		InscriptionInfo info = JsonUtil
				.jsonToBean(param, InscriptionInfo.class);
		return String.valueOf(userSrvc.inscriptUser(info));
	}

	@ResponseBody
	@RequestMapping("/forgetpassword")
	public String sendMail(String param) {
		String mail = JsonUtil.jsonToBean(param, String.class);
		return forgetPwdSrvc.sendMail(mail);
	}

}
