package com.offerme.server.dao.core;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    /** 
     * 向DAO层注入SessionFactory 
     */  
	@Autowired
    private SessionFactory sessionFactory;  
  
    /** 
     * 获取当前工作的Session 
     */  
    protected Session getSession() {  
        return this.sessionFactory.getCurrentSession();  
    }  
  
    public Integer save(T entity) {  
    	return (Integer) this.getSession().save(entity);
    }

    @SuppressWarnings("unchecked")
	public void update(T entity) {
    	// TODO To solve the problem org.hibernate.NonUniqueObjectException: a different object with the same identifier value was alread
    	entity = (T) this.getSession().merge(entity);
        this.getSession().update(entity);  
    }  

}
