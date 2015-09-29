package com.offerme.server.service.threadpool;

import java.util.HashMap;
import com.offerme.server.util.JSONUtil;
/**
 * 返回对象
 * 
 * @author Edouard.Zhang
 * 
 */
public class Reply {

	private HashMap<String, Object> replyList = new HashMap<String, Object>();

	public void AddReply(String key, Object value) {
		replyList.put(key, value);
	}
	
	/**
	 * 设定错误信息
	 * 清空返回参数列表,塞入错误信息
	 * @param err
	 */
	public void SetError(String err) {
		replyList.clear();
		replyList.put("Error", err);
	}

	/**
	 * 返回json
	 * 
	 * @return
	 */
	public String getJsonReply() {
		// JSONArray jsonArray = new JSONArray();
		// JSONObject jsonObj = new JSONObject();
		//
		// for(Entry<String,Object> ent : replyList.entrySet())
		// {
		// jsonObj = JSONUtil.beanToJsonObj(ent.getValue());
		// jsonArray.add(jsonObj);
		// }
		return JSONUtil.beanToJson(replyList);
	}

}
