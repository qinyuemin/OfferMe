package com.offerme.server.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 统一使用gson
 * @author Edouard.Zhang
 *
 */
public class JSONUtil {
	
	/**
	 * 从ResultSet中所有数据转为JSON
	 * @param result
	 * @return
	 * @throws Exception
	 */
//	public static String getDataJSON(ResultSet result) throws Exception
//	{
//		// 获取列数
//		ResultSetMetaData metaData = result.getMetaData();
//		int columnCount = metaData.getColumnCount();
//
//		// 遍历ResultSet中的每条数据
//		JSONArray array = new JSONArray();
//		JSONObject jsonObj = null;
//		String columnName = "";
//		String value = "";
//		while (result.next())
//		{
//			jsonObj = new JSONObject();
//			// 遍历每一列
//			for (int j = 1; j <= columnCount; j++)
//			{
//				columnName = metaData.getColumnLabel(j);
//				value = result.getString(columnName);
//				jsonObj.put(columnName, value);
//			}
//			array.add(jsonObj);
//		}
//		return array.toString();
//	}
//	public static <T> T jsonToBean(String json, Class<T> cla){
//		String[] formats={"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"};
//		JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher(formats));
//		JSONObject jsonObject=JSONObject.fromObject(json);
//		try {
//			JSONObject.toBean(jsonObject,cla);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return (T) JSONObject.toBean(jsonObject,cla);
//	}
//	
//	public static <T> String beanToJson(T t){
//		JsonConfig config=new JsonConfig();
//		config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
//		JSONObject json=JSONObject.fromObject(t,config);
//		return json.toString();
//	}
//	
//	public static <T> JSONObject beanToJsonObj(T t){
//		JsonConfig config=new JsonConfig();
//		config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
//		JSONObject json=JSONObject.fromObject(t,config);
//		return json;
//	}
	
	public static <T> T jsonToBean(String json, Class<T> cla) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Timestamp.class,
						new TimestampTypeAdapter())
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return (T) gson.fromJson(json, cla);
	}

	public static <T> ArrayList<T> jsonToBeanList(String json, Class<T> cla) {

		ArrayList<T> returnList = new ArrayList<T>();
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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

	public static <T> String beanToJson(T t){
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String jsonString = gson.toJson(t,t.getClass());
		return jsonString;
	}
	
	public static <T> JsonElement beanToJsonElement(T t){
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String jsonString = gson.toJson(t,t.getClass());
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(jsonString);
		return jsonElement;
	}
}
