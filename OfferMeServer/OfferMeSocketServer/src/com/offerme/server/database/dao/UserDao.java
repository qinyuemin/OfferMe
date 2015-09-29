package com.offerme.server.database.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.offerme.server.database.model.Offer;
import com.offerme.server.database.model.User;

public interface UserDao extends BaseDao{
	
	public int insertUser(User user) throws InvocationTargetException;
	    
    public void updateUser(User user) throws InvocationTargetException;
    
    public void disableUser(int userId) throws InvocationTargetException;
    
    public void deleteUser(int userId) throws InvocationTargetException;
    
    public User getUser(int userId) throws InvocationTargetException;
    
    public User getUserByEmailAndPassword(String email, String password) throws InvocationTargetException;
    
    public User[] getAllUser() throws InvocationTargetException;
    
    public List<User> getAllUserList() throws InvocationTargetException;
    /**
     * 根据userId获取该用户的所有收藏offer
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Offer> getFavoriteOfferByUserId(int userId) throws InvocationTargetException;
    /**
     * 验证用户
     * @return
     * @throws Exception
     */
    public boolean ValidateUser(int userId, String validateCode)throws InvocationTargetException;
    
    public void changeLike(int userId, int change)throws InvocationTargetException;

}
