package com.offerme.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.offerme.server.model.db.User;
import com.offerme.server.service.UserSrvc;
import com.offerme.server.util.JsonUtil;

@Controller
@Deprecated
public class TestController {
	
	@Autowired
	private UserSrvc userSrvc;
	
	@RequestMapping("/testLogin")
	public ModelAndView login(String email, String psw) {
		
		ModelAndView mav = new ModelAndView();
		
		System.out.println(email);
		System.out.println(psw);
		
		User user = userSrvc.getUserByEmailAndPassword(email, psw);
		if(user != null) {
			mav.setViewName("success");
			return mav;
		} else {
			mav.setViewName("error");
			return mav;
		}
	}
	
	@ResponseBody
	@RequestMapping("/test")
	public User test(String request) {
		
		System.out.println(request);
		
		User user = JsonUtil.jsonToBean(request, User.class);
		user.setCompany("ca");
		
		return user;

	}

}
