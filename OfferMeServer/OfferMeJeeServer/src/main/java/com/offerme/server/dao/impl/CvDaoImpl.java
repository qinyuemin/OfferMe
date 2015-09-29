package com.offerme.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.offerme.server.dao.CvDao;
import com.offerme.server.dao.core.BaseDaoImpl;
import com.offerme.server.model.db.Cv;

@Repository("cvDao")
public class CvDaoImpl extends BaseDaoImpl<Cv> implements CvDao {

	@Override
	public Cv findByUserId(Integer userId) {
		Query query = this.getSession().createQuery("from Cv where userId = ?");
		query.setInteger(0, userId);
		
		List<?> cvs = query.list();
		return (Cv) (!CollectionUtils.isEmpty(cvs)? cvs.get(0) : null);
	}

	@Override
	public void delete(Integer entityId) {
		Cv cv = (Cv) this.getSession().get(Cv.class, entityId);
		if(cv != null)
			this.getSession().delete(cv);		
	}

	@Override
	public Cv get(Integer entityId) {
		return (Cv) this.getSession().get(Cv.class, entityId);
	}

}
