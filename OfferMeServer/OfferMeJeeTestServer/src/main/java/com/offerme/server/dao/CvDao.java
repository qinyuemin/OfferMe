package com.offerme.server.dao;

import com.offerme.server.dao.core.BaseDao;
import com.offerme.server.model.db.Cv;

public interface CvDao extends BaseDao<Cv> {

	Cv findByUserId(Integer userId);
}
