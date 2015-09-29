package com.offerme.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
/**
 * 动态加载
 * @author Edouard.Zhang
 *
 */
public class DynamicUtil {
	
	static Logger myLog = Logger.getLogger("com.logi.lpromis.tool");
	
	public static Class<?> getClass(String strModuleName, String strClassName) throws Exception
	{
		Class<?> reClass = null;

		// 类名，方法名不能为空
		if (strClassName == null || strClassName.trim().length() < 1)
		{
			throw new IllegalArgumentException("类名不能为空！");
		}

		if (strModuleName == null)
		{
			reClass = Class.forName(strClassName);
		}
		else
		{
			List<URL> list = new ArrayList<URL>();
			String path = System.getProperty("user.dir") + "/";

			strModuleName = path + strModuleName;

			list.addAll(getAllFilesR(new File(strModuleName)));

			URL[] urls = new URL[list.size()];
			for (int i = 0; i < urls.length; i++)
			{
				urls[i] = (URL) list.get(i);
			}

			// 装载class文件
			reClass = new URLClassLoader(urls, ClassLoader.getSystemClassLoader()).loadClass(strClassName);
		}

		return reClass;
	}
	
	/**
	 * 递归获取文件URL
	 * @param file
	 * @return
	 */
	private static List<URL> getAllFilesR(File file)
	{
		List<URL> list = new ArrayList<URL>();
		if (file == null || !file.exists())
		{ // 如果文件不存在，不添加文件URL
			return list;
		}
		if (file.isDirectory())
		{ // 如果是目录，遍历下面所有文件
			String[] subfiles = file.list();
			for (int i = 0; i < subfiles.length; i++)
			{
				list.addAll(getAllFilesR(new File(file.getAbsolutePath() + "/" + subfiles[i])));
			}
		}
		else
		{ // 如果是文件，添加文件URL
			try
			{
				list.add(file.getCanonicalFile().toURI().toURL());
			}
			catch (MalformedURLException e)
			{
				//@@@PTZ
				myLog.error(Log.getStackInfo(e));
				e.printStackTrace();
			}
			catch (IOException e)
			{
				//@@@PTZ
				myLog.error(Log.getStackInfo(e));
				e.printStackTrace();
			}
		}
		return list;
	}

}
