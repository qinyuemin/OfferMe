package com.offerme.server.service;

import com.offerme.server.model.business.inscription.InscriptionInfo;
import com.offerme.server.model.db.User;

public interface UserSrvc {
	
	User getUserById(Integer userId);
	
	User getUserByEmail(String email);
	
	User getUserByEmailAndPassword(String email, String password);
	
	Integer saveUser(User user);
	
	void update(User user);
	
	Integer inscriptUser(InscriptionInfo info);
	
}
