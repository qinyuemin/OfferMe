package com.offerme.server.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncoderUtil {

	public static String encrypt(String str) {
		byte[] input;
		String resualt = "";
		try {
			input = str.getBytes("UTF8");
			resualt = (new BASE64Encoder()).encodeBuffer(input);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resualt;
	}

	public static String decrypt(String str) {
		byte[] output;
		String resualt="";
		try {
			output = (new BASE64Decoder()).decodeBuffer(str);
			resualt=new String(output,"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resualt;
	}
}
