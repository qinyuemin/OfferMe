package com.offerme.server.service;

import com.offerme.server.model.business.login.LoginResponse;

public interface LoginSrvc {

	LoginResponse login(String email, String password);
}
