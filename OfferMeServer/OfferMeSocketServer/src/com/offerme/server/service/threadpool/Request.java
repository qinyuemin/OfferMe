package com.offerme.server.service.threadpool;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 请求对象
 * 
 * @author Edouard.Zhang
 * 
 */
public class Request {
	
	/**
	 * 服务编号
	 */
	private int serviceRequestType = -1;
	/**
	 * 参数列表
	 */
	private HashMap<Integer,String> paramList = new HashMap<Integer,String>();
	/**
	 * 分页开始行数
	 */
	private int start = -1;
	/**
	 * 分页大小
	 */
	private int size  = -1;
	/**
	 * 排序
	 */
	private String order = "";
	/**
	 * 过滤
	 */
	private String where = "";

	public int getServiceRequestType() {
		return serviceRequestType;
	}

//	public ArrayList<String> getParamList() {
//		return paramList;
//	}
	
	public String getParam(int key)
	{
		return paramList.get(Integer.valueOf(key));
	}
	
	public int getStart() {
		return start;
	}

	public int getSize() {
		return size;
	}

	public String getOrder() {
		return order;
	}

	public String getWhere() {
		return where;
	}

	public static class KeyWords
	{
		// 请求号
		public static String requestType = "type";
		// 分页开始行数
		public static String start = "start";
		// 分页大小
		public static String size = "size";
		// 排序
		public static String order = "order";
		// 过滤
		public static String where = "where";
	}

	private static ThreadLocal<Request> request = new ThreadLocal<Request>();
	
	public static Request getRequest(String json)
	{
		request.set(new Request(json));
		return request.get();
	}

	public static Request getRequest()
	{
		return request.get();
	}

	
	public static void clearRequest()
	{
		request.remove();
	}
	
	public void clearCondition()
	{
		start = -1;
		size  = -1;
		order = "";
		where = "";
	}
	
	private Request(String json) {
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
//		JsonArray jsonArray = jsonElement.getAsJsonArray(); 
//		Iterator<JsonElement> it = jsonArray.iterator();
//		int i = 0;
//		while (it.hasNext()) {
//			
//			jsonElement = (JsonElement) it.next(); 
//			json = jsonElement.toString();
//			if(i==0)
//			{
//				serviceRequestType = jsonElement.getAsInt();
//			}
//			else
//			{
//				paramList.add(json);
//			}
//			i++;
//		}
		String key = "";
		for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			key = entry.getKey();
			jsonElement = entry.getValue();
			
			if(KeyWords.requestType.equals(key))
			{
				serviceRequestType = jsonElement.getAsInt();
			}
			else if(KeyWords.start.equals(key))
			{
				start = jsonElement.getAsInt();
			}
			else if(KeyWords.size.equals(key))
			{
				size = jsonElement.getAsInt();
			}
			else if(KeyWords.order.equals(key))
			{
				order = jsonElement.getAsString();
			}
			else if(KeyWords.where.equals(key))
			{
				where = jsonElement.getAsString();
			}
			else
			{
				paramList.put(Integer.valueOf(key), jsonElement.toString());
//				paramList.add(jsonElement.getAsString());
			}
		}


	}

}
