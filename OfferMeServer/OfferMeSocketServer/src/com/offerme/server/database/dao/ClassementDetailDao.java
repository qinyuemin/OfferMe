package com.offerme.server.database.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.offerme.server.database.model.ClassementDetail;


public interface ClassementDetailDao extends BaseDao{
	
	public int insertClassementDetail(ClassementDetail classementDetail) throws InvocationTargetException;
    
    public void updateClassementDetail(ClassementDetail classementDetail) throws InvocationTargetException;
    
    public void deleteClassementDetail(int classementDetailId) throws InvocationTargetException;
    
    public ClassementDetail getClassementDetail(int classementDetailId) throws InvocationTargetException;
    
    public HashMap<String, ClassementDetail> getSubClassementDetail(int classementDetailId) throws InvocationTargetException;
    /**
     * 判断国家城市是否新增,且建立上下级关系
     * @param country
     * @param city
     * @throws Exception
     */
    public void checkforInsertCountryCity(String country, String city) throws InvocationTargetException;
    

    //TODO 新增USER 或者 offer的之后需要通过判断维护 ClassementDetail

}
