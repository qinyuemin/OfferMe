package com.offerme.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.offerme.server.dao.UserDao;
import com.offerme.server.dao.core.BaseDaoImpl;
import com.offerme.server.model.db.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	
	@Override
	public User findById(Integer userId) {
		
		Query query = this.getSession().createQuery("from User where userId = ?");
		query.setInteger(0, userId);
		
		return (User) query.uniqueResult();
	}

	@Override
	public User findByEmail(String email) {
		
		Query query = this.getSession().createQuery("from User where email = ?");
		query.setString(0, email);
		
		List<?> users = query.list();
		return (User) (!CollectionUtils.isEmpty(users)? users.get(0) : null);
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		
		Query query = this.getSession().createQuery("from User where email = ? and psw = ?");
		query.setString(0, email);
		query.setString(1, password);
		
		List<?> users = query.list();
		return (User) (!CollectionUtils.isEmpty(users)? users.get(0) : null);
	}

	@Override
	public void delete(Integer entityId) {
		User user = (User) this.getSession().get(User.class, entityId);
		if(user != null)
			this.getSession().delete(user != null);
	}

	@Override
	public User get(Integer entityId) {
		return (User) this.getSession().get(User.class, entityId);
	}

}
