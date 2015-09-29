package com.offerme.server.dao.core;

public interface BaseDao<T> {

	Integer save(T entity);
	
	void update(T entity);
	
	void delete(Integer entityId);
	
	T get(Integer entityId);
}
