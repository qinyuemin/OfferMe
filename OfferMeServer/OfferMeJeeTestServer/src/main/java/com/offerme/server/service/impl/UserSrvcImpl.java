package com.offerme.server.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offerme.server.dao.UserDao;
import com.offerme.server.model.business.inscription.InscriptionInfo;
import com.offerme.server.model.db.User;
import com.offerme.server.service.UserSrvc;
import com.offerme.server.util.EncoderUtil;

@Service("userSrvc")
@Transactional
public class UserSrvcImpl implements UserSrvc {

	@Autowired
	private UserDao userDao;

	@Override
	public User getUserById(Integer userId) {
		return userDao.findById(userId);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		return userDao.findByEmailAndPassword(email, password);
	}

	@Override
	public Integer saveUser(User user) {
		return userDao.save(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public Integer inscriptUser(InscriptionInfo info) {
		int id = -1;
		try {
			User user = new User();
			user.setName(info.getName());
			user.setCity(info.getCity());
			user.setCompany(info.getEntreprise());
			user.setEmail(info.getEmail());
			user.setPhone(info.getTelephoneNumber());
			// user.setPsw(info.getPassword());
			user.setPsw(EncoderUtil.encrypt(info.getPassword()));
			user.setPortrait(info.getPhotoByte());
			user.setPost(info.getPost());
			user.setAge(Integer.parseInt(info.getAge()));
			user.setWorkyear(Integer.parseInt(info.getWorkyear()));
			user.setEmailPublic(info.isEmailPublished() ? 1 : 0);
			user.setPhonePublic(info.isPhonePublished() ? 1 : 0);
			user.setSignLastDt(new Date());
			user.setSignUpDt(new Date());
			if (userDao.findByEmail(user.getEmail()) == null) {
				id = saveUser(user);
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return id;
	}

}
