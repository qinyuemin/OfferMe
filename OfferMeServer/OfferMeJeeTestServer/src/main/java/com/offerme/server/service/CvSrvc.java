package com.offerme.server.service;

import com.offerme.server.model.business.personinfo.PersonalCV;
import com.offerme.server.model.db.Cv;

public interface CvSrvc {

	Cv getCvByUserId(Integer userId);
	
	void updateCV(Cv cv);
	
	Integer insertCV(Cv cv);
	
	void deleteCV(Integer cvId);
	
	String saveCV(PersonalCV pCV);
}
