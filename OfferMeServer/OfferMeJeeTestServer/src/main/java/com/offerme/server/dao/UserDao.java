package com.offerme.server.dao;

import com.offerme.server.dao.core.BaseDao;
import com.offerme.server.model.db.User;

public interface UserDao extends BaseDao<User> {
	
	User findById(Integer userId);

	User findByEmail(String email);
	
	User findByEmailAndPassword(String email, String password);
}
