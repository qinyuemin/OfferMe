package com.offerme.server.util;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonUtil {

	public static <T> T jsonToBean(String json, Class<T> cla) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
		return (T) gson.fromJson(json, cla);
	}

	public static <T> ArrayList<T> jsonToBeanList(String json, Class<T> cla) {

		ArrayList<T> returnList = new ArrayList<T>();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		JsonArray jsonArray = jsonElement.getAsJsonArray(); 
		Iterator<JsonElement> it = jsonArray.iterator();
		while (it.hasNext()) { // 循环
			jsonElement = (JsonElement) it.next(); 
			json = jsonElement.toString();
			T t = gson.fromJson(json, cla); 
			returnList.add(t);
		}
		return returnList;
	}
	
	public static <T> String beanToJsonString(T t){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String jsonString = gson.toJson(t,t.getClass());
		return jsonString;
	}
	
	public static <T> JsonElement beanToJsonElement(T t){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String jsonString = gson.toJson(t,t.getClass());
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(jsonString);
		return jsonElement;
	}
}
