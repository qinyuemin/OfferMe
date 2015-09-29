package com.offerme.server.database.dao.proxy;

import java.lang.reflect.InvocationHandler; 
import java.lang.reflect.Method;

/**
 * 反射加载
 * @author Edouard.Zhang
 *
 */
public class InvocationHandlerImp implements InvocationHandler{ 
    private Object obj;

    public InvocationHandlerImp(Object object) { 
        super(); 
        this.obj = object; 
    }

    @Override 
    public Object invoke(Object proxy, Method method, Object[] args) 
            throws Throwable { 
        
//        String methodName = method.getName(); 
//        System.out.println("执行方法: " + methodName); 
		Object result = null;
		try {
			result = method.invoke(obj, args);
		} catch (Exception e) {
			throw e;
		}

		return result;
    } 
    
} 
