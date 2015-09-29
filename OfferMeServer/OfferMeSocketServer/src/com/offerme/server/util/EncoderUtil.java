package com.offerme.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class EncoderUtil {
	
	static Logger myLog = Logger.getLogger("com.offerme.server.util");
	
	public static String encryptMD5(String str) throws Exception
	{
		// 加密后结果
		String strEncrypt = "";

		if ("".equals(str))
		{
			return "";
		}
		try
		{
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			byte[] bPwd = str.getBytes("UTF-8");
			mdTemp.update(bPwd);
			bPwd = mdTemp.digest();
			int n;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < bPwd.length; offset++)
			{
				n = bPwd[offset];
				if (n < 0)
				{
					n += 256;
				}
				if (n < 16)
				{
					buf.append("0");
				}
				buf.append(Integer.toHexString(n));
			}

			strEncrypt = buf.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			myLog.error(Log.getStackInfo(e));
			e.printStackTrace();
		}
		catch (Exception e)
		{
			myLog.error(Log.getStackInfo(e));
			e.printStackTrace();
		}
		return strEncrypt;
	}

}
