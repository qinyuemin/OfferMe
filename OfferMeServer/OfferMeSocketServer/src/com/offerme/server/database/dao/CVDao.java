package com.offerme.server.database.dao;

import java.lang.reflect.InvocationTargetException;

import com.offerme.server.database.model.CV;

public interface CVDao  extends BaseDao{

	public int insertCV(CV cv) throws InvocationTargetException;
    
    public void updateCV(CV cv) throws InvocationTargetException;
    
    public void deleteCV(int userID) throws InvocationTargetException;
    
    public CV getCV(int userID) throws InvocationTargetException;

}
