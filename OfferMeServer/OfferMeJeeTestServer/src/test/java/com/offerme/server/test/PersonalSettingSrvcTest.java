package com.offerme.server.test;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.business.personalsetting.PersonalSetting;
import com.offerme.server.service.PersonalSettingSrvc;
import com.offerme.server.test.core.BaseTestCase;

public class PersonalSettingSrvcTest extends BaseTestCase{
	
	@Autowired
	private PersonalSettingSrvc svrc;
	
	@Test
	public void testPersonalSetting()
	{
		PersonalSetting p = new PersonalSetting();
		// TODO get data for PersonalSetting
		String response = svrc.savePersonalSettingOnServer(p);
		
		assertFalse(!"OK".equals(response));
		
		// TODO PersonalSetting need more Test
	}

}
