package com.offerme.server.service;

import java.lang.reflect.InvocationTargetException;

import com.offerme.server.database.dao.CVDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.service.bean.PersonalCV;
import com.offerme.server.service.bean.PersonalWorkInfo;
import com.offerme.server.database.model.CV;
import com.offerme.server.util.JSONUtil;

public class SaveCVSrvc {

	private String OK = "ok";
	private String resualt = null;

	private CVDao cvDao = DaoImplManage.getCVDaoInstance();

	public String saveCV(String request) {
		PersonalCV personCV = JSONUtil.jsonToBean(request, PersonalCV.class);
		PersonalWorkInfo workInfo = personCV.getWorkInfo();
		CV cv = new CV();
		cv.setUserId(Integer.parseInt(personCV.getUserID()));
		cv.setDiploma(personCV.getEducation());
		cv.setSchool(personCV.getColleage());
		cv.setCurrentCompany(personCV.getEntreprise());
		cv.setCurrentPost(personCV.getPost());
		cv.setJobYear(Integer.parseInt(personCV.getWorkyear()));
		System.out.println("Show me the size: "+personCV.getWorkInfo().getWorks().size());
		for (int count = 0; count < 9;) {
			if (count < 3) {
				cv.setJobCompany01(workInfo.getFirstWork());
				cv.setJobYear01(workInfo.getFirstWorkyear());
				cv.setJobPost01(workInfo.getFirstWorkPost());
			} else if (count < 6) {
				cv.setJobCompany02(workInfo.getSecondWork());
				cv.setJobYear02(workInfo.getSecondWorkyear());
				cv.setJobPost02(workInfo.getSecondWorkPost());
			} else if (count < 9) {
				cv.setJobCompany03(workInfo.getThirdWork());
				cv.setJobYear03(workInfo.getThirdWorkyear());
				cv.setJobPost03(workInfo.getThirdWorkPost());
			}
			count = count + 3;
		}
		try {
			if (cvDao.getCV(Integer.parseInt(personCV.getUserID())) != null) {
				cvDao.updateCV(cv);
			} else {
				cvDao.insertCV(cv);
			}
			resualt = OK;
		} catch (InvocationTargetException e) {
			e.printStackTrace();

		}
		return resualt;
	}
}
