package com.offerme.server.database.dao;

import java.lang.reflect.InvocationTargetException;

import com.offerme.server.database.model.Classement;

public interface ClassementDao extends BaseDao {
	
	public int insertClassement(Classement classement) throws InvocationTargetException;
    
    public void updateClassement(Classement classement) throws InvocationTargetException;
    
    public void deleteClassement(int classementId) throws InvocationTargetException;
    
    public Classement getClassement(int classementId) throws InvocationTargetException;

}
