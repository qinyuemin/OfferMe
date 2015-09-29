package com.offerme.server.database.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.offerme.server.database.model.Message;

public interface MessageDao extends BaseDao{
	
	public int insertMessage(Message message) throws InvocationTargetException;
    
    public void updateMessage(Message message) throws InvocationTargetException;
    
    public void deleteMessage(int messageId) throws InvocationTargetException;
    
    public Message getMessage(int messageId) throws InvocationTargetException;
    
    public List<Message> getMessagesBySender(int sUserId) throws InvocationTargetException;
    /**
     * 增量获取未读
     * @param rUserId
     * @return
     * @throws Exception
     */
    public List<Message> getMessagesByReceiverIncrem(int rUserId) throws InvocationTargetException;
    /**
     * 增量获取全部
     * @param rUserId
     * @return
     * @throws Exception
     */
    public List<Message> getMessagesByReceiver(int rUserId) throws InvocationTargetException;

}
