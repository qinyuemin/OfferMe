package com.offerme.server.dao;

import java.util.List;

import com.offerme.server.dao.core.BaseDao;
import com.offerme.server.model.db.Message;

public interface MessageDao  extends BaseDao<Message>{
	
	 List<Message> getMessage(Integer sUserID, Integer rUserId);
	 
	 List<Message> getMessagesByReceiverIncrem(int rUserId);

}
