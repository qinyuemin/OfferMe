package com.offerme.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.offerme.server.dao.MessageDao;
import com.offerme.server.dao.core.BaseDaoImpl;
import com.offerme.server.model.db.Message;

@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {

	@Override
	public void delete(Integer entityId) {
		Message message = (Message) this.getSession().get(Message.class,
				entityId);
		if (message != null)
			this.getSession().delete(message);
	}

	@Override
	public Message get(Integer entityId) {
		return (Message) this.getSession().get(Message.class, entityId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getMessage(Integer sUserID, Integer rUserId) {
		Query query = this.getSession().createQuery(
				"from Message where sUserId = ? and rUserId = ?");
		query.setInteger(0, sUserID);
		query.setInteger(1, rUserId);

		return (List<Message>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getMessagesByReceiverIncrem(int rUserId) {
		List<Message> messageList = new ArrayList<Message>();
		Query query = this
				.getSession()
				.createQuery(
						"from Message where rUserId = ? and STATUS = 0 order by date desc");
		query.setInteger(0, rUserId);
		messageList = query.list();
		if (!CollectionUtils.isEmpty(messageList)) {
			for (Message message : messageList) {
				message.setStatus(1);
				this.update(message);
			}
		}
		return messageList;
	}

}
