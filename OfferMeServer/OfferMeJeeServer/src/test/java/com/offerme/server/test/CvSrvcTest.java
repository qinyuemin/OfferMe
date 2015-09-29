package com.offerme.server.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.business.personinfo.PersonalCV;
import com.offerme.server.model.db.Cv;
import com.offerme.server.service.CvSrvc;
import com.offerme.server.test.core.BaseTestCase;
import com.offerme.server.util.JsonUtil;

public class CvSrvcTest extends BaseTestCase {

	@Autowired
	private CvSrvc cvSrvc;
	
	@Test
	public void testGetCvByUserId() {
		Cv cv = cvSrvc.getCvByUserId(1);
		assertEquals(new Integer(1), cv.getCvId());
	}
	
	@Test
	public void testSaveCV() {
		// clear first
		cvSrvc.deleteCV(45);
		
		// test insert
		String test = "{\"workInfo\":{\"completeArray\":[\"jnbb\",\"5\"]},\"userID\":\"45\",\"name\":\"test\",\"post\":\"Ingenieur\",\"age\":\"27\",\"entreprise\":\"samsung\",\"workyear\":\"5\",\"education\":\"硕士\",\"colleage\":\"hhghj\"}";
		PersonalCV pCV = JsonUtil.jsonToBean(test, PersonalCV.class);
		String response = cvSrvc.saveCV(pCV);
		assertFalse(!"ok".equals(response));
		
		Cv cv =  cvSrvc.getCvByUserId(45);
		assertFalse(cv == null);
		assertEquals("hhghj", cv.getSchool());
		assertEquals("samsung",cv.getCurrentCompany());
		assertEquals("Ingenieur",cv.getCurrentPost());
		
		// test update 
		test = "{\"workInfo\":{\"completeArray\":[\"jnbb\",\"5\"]},\"userID\":\"45\",\"name\":\"test\",\"post\":\"Ingenieur\",\"age\":\"27\",\"entreprise\":\"bbbb\",\"workyear\":\"5\",\"education\":\"硕士\",\"colleage\":\"aaaa\"}";
		 pCV = JsonUtil.jsonToBean(test, PersonalCV.class);
		 response = cvSrvc.saveCV(pCV);
		assertFalse(!"ok".equals(response));
		
		cv =  cvSrvc.getCvByUserId(45);
		assertFalse(cv == null);
		assertEquals("aaaa", cv.getSchool());
		assertEquals("bbbb",cv.getCurrentCompany());
		assertEquals("Ingenieur",cv.getCurrentPost());
	}
}
