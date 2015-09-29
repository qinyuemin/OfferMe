package com.offerme.server;

import com.offerme.server.service.threadpool.ThreadPoolMgn;
import com.offerme.server.util.Log;

/**
 * 存放全局变量
 * @author Edouard.Zhang
 *
 */
public class GlobalData {

	static Log myLog = new Log("com.logi.lpromis.server");
	
	private static ThreadPoolMgn poolMgn = null;

	public static ThreadPoolMgn getPoolMgn() {
		return poolMgn;
	}
	public static void setPoolMgn(ThreadPoolMgn poolMgn) {
		GlobalData.poolMgn = poolMgn;
	}
	
}
