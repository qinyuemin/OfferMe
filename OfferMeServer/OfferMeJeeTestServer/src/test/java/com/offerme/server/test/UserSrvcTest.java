package com.offerme.server.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.business.inscription.InscriptionInfo;
import com.offerme.server.model.db.User;
import com.offerme.server.service.UserSrvc;
import com.offerme.server.test.core.BaseTestCase;

public class UserSrvcTest extends BaseTestCase{

	@Autowired
	private UserSrvc userSrvc;
	
	@Test
	public void testGetUserById() {
		User user = userSrvc.getUserById(47);
		assertEquals("wenyao.ji@yahoo.com", user.getEmail());
	}

	@Test
	public void testGetUserByEmail() {
		User user = userSrvc.getUserByEmail("wenyao.ji@yahoo.com");
		assertEquals(new Integer(47), user.getUserId());
	}
	
	@Test
	public void testGetUserByEmailAndPassword() {
		User user = userSrvc.getUserByEmailAndPassword("wenyao.ji@yahoo.com", "123456");
		assertEquals(new Integer(47), user.getUserId());
	}
	
	@Test
	public void testInscriptUser() {
		InscriptionInfo info = new InscriptionInfo();
		info.setName("jee");
		info.setCity("paris");
		info.setEntreprise("caceis");
		info.setEmail("jee@gmail.com");
		info.setTelephoneNumber("0687233423");
		info.setPassword("abc");
		
		userSrvc.inscriptUser(info);
		
		User user = userSrvc.getUserByEmailAndPassword("jee@gmail.com", "abc");
		assertEquals("caceis", user.getCompany());
	}

}
