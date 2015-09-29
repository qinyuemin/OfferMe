package com.offerme.server.database.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.offerme.server.database.model.OfferUser;

public interface OfferUserDao extends BaseDao {
	
	public void insertOfferUser(OfferUser offerUser) throws InvocationTargetException;
    
    public void updateOfferUser(OfferUser offerUser) throws InvocationTargetException;
    
    public void deleteOfferUser(int offerId, int userId) throws InvocationTargetException;
    
    public void deleteOfferUser(OfferUser offerUser) throws InvocationTargetException;
    
    public OfferUser getOfferUser(int offerId, int userId) throws InvocationTargetException;
    
    public boolean checkFavorite(int offerId, int userId) throws InvocationTargetException;
    
    public List<Integer> getOfferListIdByUserId(int userId)throws InvocationTargetException;
    
    public int getFavoriteOfferCount(int offerId)throws InvocationTargetException;

}
