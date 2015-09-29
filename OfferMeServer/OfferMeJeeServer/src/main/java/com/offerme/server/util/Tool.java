package com.offerme.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool {
	
	public static Date getNowDate() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateNow = new Date(System.currentTimeMillis());
		try {
			return dateFormat.parse(dateFormat.format(dateNow));
		} catch (Exception e) {
			return dateNow;
		}
	}
	
	public static String getNowDateStr() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateNow = new Date(System.currentTimeMillis());
		try {
			return dateFormat.format(dateNow);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getDateStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateFormat.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Integer parseInt(Object o)
	{
		try {
			if(o.getClass() == String.class)
				return Integer.parseInt((String)o);
			else
				return Integer.parseInt(o.toString());
		} catch (Exception e) {
			return 0 ;
		}
	}
}
