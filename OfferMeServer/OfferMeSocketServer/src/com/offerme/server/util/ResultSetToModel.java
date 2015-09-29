package com.offerme.server.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultSetToModel {
	
	static HashMap<String,HashMap<String, MethodEntity>> hmMethodsMap = new HashMap<String, HashMap<String,MethodEntity>>();

	/**
	 * 实现结果集到实体对象/值对象/持久化对象转换
	 * 
	 * @param rsResult
	 *            ResultSet
	 * @param strEntity
	 *            String
	 * @throws Exception
	 * @return Object[]
	 */
	public static Object[] parseDataEntityBeans(ResultSet rsResult,
			String strEntity) throws Exception {
		
		List<Object> listResult = parseDataEntityBeanList(rsResult,strEntity);
		Class<?> classEntity = Class.forName(strEntity);
		// 以数组方式返回
		Object objResutlArray = Array.newInstance(classEntity,
				listResult.size());
		listResult.toArray((Object[]) objResutlArray);

		return (Object[]) objResutlArray;
	}
	
	/**
	 * 实现结果集到实体对象/值对象/持久化对象转换
	 * 
	 * @param rsResult
	 *            ResultSet
	 * @param strEntity
	 *            String
	 * @throws Exception
	 * @return List<Object> 
	 */
	public static List<Object> parseDataEntityBeanList(ResultSet rsResult,
			String strEntity) throws Exception {
		DataTableEntity dataTable = null;
		List<Object> listResult = new ArrayList<Object>();

		// 注册实体,strEntity指定的实体类名称字符串
		Class<?> classEntity = Class.forName(strEntity);
		
		HashMap<String, MethodEntity> hmMethods = null;
		
		boolean bBasicType = false;
		if(classEntity == String.class|| classEntity == BigDecimal.class || classEntity == Integer.class || classEntity == java.util.Date.class|| classEntity == java.sql.Date.class|| classEntity == Timestamp.class)
		{
			bBasicType =true;
		}
		else
		{
			hmMethods = hmMethodsMap.get(strEntity);
			if(hmMethods == null)
			{
				synchronized(hmMethodsMap)
				{
					hmMethods = hmMethodsMap.get(strEntity);
					if(hmMethods == null)
					{
						hmMethods = new HashMap<String, MethodEntity> ();
						// 获取实体中定义的方法
						for (int i = 0; i < classEntity.getDeclaredMethods().length; i++) {
							MethodEntity methodEntity = new MethodEntity();
							// 方法的名称
							String methodName = classEntity.getDeclaredMethods()[i].getName();
							String methodKey = methodName.toUpperCase();
							// 方法的参数
							Class<?>[] paramTypes = classEntity.getDeclaredMethods()[i]
									.getParameterTypes();
							methodEntity.setMethodName(methodName);
							methodEntity.setMethodParamTypes(paramTypes);

							// 处理方法重载
							if (hmMethods.containsKey(methodKey)) {
								methodEntity.setRepeatMethodNum(methodEntity
										.getRepeatMethodNum() + 1);
								methodEntity.setRepeatMethodsParamTypes(paramTypes);
							} else {
								hmMethods.put(methodKey, methodEntity);
							}
						}
						hmMethodsMap.put(strEntity, hmMethods);
					}
				}				
			}
			
			// 处理ResultSet结构体信息
			if (rsResult != null) {
				ResultSetMetaData rsMetaData = rsResult.getMetaData();
				int columnCount = rsMetaData.getColumnCount();
				dataTable = new DataTableEntity(columnCount);
				// 获取字段名称，类型
				for (int i = 0; i < columnCount; i++) {
					String columnName = rsMetaData.getColumnLabel(i + 1);
//					String columnName = rsMetaData.getColumnName(i + 1);
					int columnType = rsMetaData.getColumnType(i + 1);

					dataTable.setColumnName(columnName, i);
					dataTable.setColumnType(columnType, i);
				}
			}
		}
		
		if(bBasicType)
		{
			while (rsResult.next()) {
				Object objResult =  rsResult.getObject(1);
				listResult.add(objResult);
			}
		}
		else
		{
			// 处理ResultSet数据信息
			while (rsResult.next()) {
				// 调用方法，根据字段名在hsMethods中查找对应的set方法
				Object objResult = parseObjectFromResultSet(rsResult, dataTable,
						classEntity, hmMethods);
				listResult.add(objResult);
			}
		}
		
		// 以列表方式返回
		return listResult;
	}

	/**
	 * 从Resultset中解析出单行记录对象，存储在实体对象中
	 */
	public static Object parseObjectFromResultSet(ResultSet rs,
			DataTableEntity dataTable, Class<?> classEntity,
			HashMap<String, MethodEntity> hsMethods) throws Exception {
		Object objEntity = classEntity.newInstance();
		Method method = null;

		int nColumnCount = dataTable.getColumnCount();
		String[] strColumnNames = dataTable.getColumnNames();

		for (int i = 0; i < nColumnCount; i++) {
			// 获取字段值
			Object objColumnValue = rs.getObject(strColumnNames[i]);

			// HashMap中的方法名key值
			String strMethodKey = null;

			// 获取set方法名
			if (strColumnNames[i] != null) {
				strMethodKey = String.valueOf("SET"
						+ strColumnNames[i].toUpperCase());
			}
			// 值和方法都不为空,这里方法名不为空即可,值可以为空的
			if (strMethodKey != null) {
				// 判断字段的类型,方法名，参数类型
				try {
					MethodEntity methodEntity = (MethodEntity) hsMethods
							.get(strMethodKey);
					String methodName = methodEntity.getMethodName();
					int repeatMethodNum = methodEntity.getRepeatMethodNum();

					Class<?>[] paramTypes = methodEntity.getMethodParamTypes();
					method = classEntity.getMethod(methodName, paramTypes);

					// 如果重载方法数 >
					// 1，则判断是否有java.lang.IllegalArgumentException异常，循环处理
					try {
						// 设置参数,实体对象，实体对象方法参数
						method.invoke(objEntity,
								new Object[] { objColumnValue });
					} catch (java.lang.IllegalArgumentException e) {
						// 处理重载方法
						for (int j = 1; j < repeatMethodNum; j++) {
							try {
								Class<?>[] repeatParamTypes = methodEntity
										.getRepeatMethodsParamTypes(j - 1);
								method = classEntity.getMethod(methodName,
										repeatParamTypes);
								method.invoke(objEntity,
										new Object[] { objColumnValue });
								break;
							} catch (java.lang.IllegalArgumentException ex) {
								continue;
							}
						}
					}
				} catch (NoSuchMethodException e) {
					throw new NoSuchMethodException();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return objEntity;
	}
}
