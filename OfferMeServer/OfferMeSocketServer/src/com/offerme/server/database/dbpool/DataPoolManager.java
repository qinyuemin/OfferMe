package com.offerme.server.database.dbpool;

import java.io.File;
import java.io.FileInputStream;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
/**
 * 数据库连接池
 * 
 * @author Edouard.Zhang
 */
public class DataPoolManager {
	private static HashMap<String, BasicDataSource> bds = new HashMap<String, BasicDataSource>();

	/**
	 * 获取数据源名称对应的数据源
	 * 
	 * @param dsName
	 * @return
	 */
	public static Connection getConnection(String dsName) throws Exception {
		Connection conn = null;
		if(!bds.containsKey(dsName.toUpperCase())){
			initDataSource(dsName.toUpperCase());
		}
		conn = ((BasicDataSource)bds.get(dsName.toUpperCase())).getConnection();
		return conn;
	}

	/**
	 * 根据传入数据源名称，初始化相应的数据源连接池对象
	 * 
	 * @param dsName 数据源名称
	 */
	public static void initDataSource(String dsName) throws Exception {
		// 读取连接池配置文件
		String path = System.getProperty("user.dir");
		try {
			
			File inf = new File(path + "/XMLFiles/" + "DBConfig.properties");
			FileInputStream is = new FileInputStream(inf);
			Properties dbProps = new Properties();
			dbProps.load(is);
			
			// 连接池 初始化方法
			// 数据源驱动
			String driverClassName = dbProps.getProperty("driverClassName");
			// 数据源链接地址
			String url = dbProps.getProperty("url");
			// 帐号
			String username = dbProps.getProperty("username");
			// 密码
			String password = dbProps.getProperty("password");
			// 初始化连接数		
			String initialSize = dbProps.getProperty("initialSize");
			// 最大连接数
			String maxActive = dbProps.getProperty("maxActive");
			// 最小空闲连接
			String minIdle = dbProps.getProperty("minIdle");
			// 最大空闲连接
			String maxIdle = dbProps.getProperty("maxIdle");
			// 没有可用连接，最大等待时间
			String maxWait = dbProps.getProperty("maxWait");
			// 开启对获取连接的验证
			String testOnBorrow = dbProps.getProperty("testOnBorrow");
			// 连接验证语句
			String validationQuery = dbProps.getProperty("validationQuery");
			// 开启回收没有被使用的活动连接
			String removeAbandoned = dbProps.getProperty("removeAbandoned");
			// 没有被使用的活动连接的毫秒数
			String removeAbandonedTimeout = dbProps.getProperty("removeAbandonedTimeout");
			// 是否在自动回收超时连接的时候打印连接的超时错误
			String logAbandoned = dbProps.getProperty("logAbandoned");
			// 指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除
			String testWhileIdle = dbProps.getProperty("testWhileIdle");
			// 在空闲连接回收器线程运行期间休眠的时间值,以毫秒为单位. 如果设置为非正数,则不运行空闲连接回收器线程
			String timeBetweenEvictionRunsMillis = dbProps.getProperty("timeBetweenEvictionRunsMillis");
			
			// 对数据源进行赋值
			BasicDataSource ds = new BasicDataSource();
			ds.setUrl(url);
			ds.setDriverClassName(driverClassName);
			ds.setUsername(username);
			ds.setPassword(password);

			ds.setInitialSize(Integer.parseInt(initialSize)); 
			ds.setMaxActive(Integer.parseInt(maxActive));
			ds.setMinIdle(Integer.parseInt(minIdle));
			ds.setMaxIdle(Integer.parseInt(maxIdle));
			ds.setMaxWait(Integer.parseInt(maxWait));
			ds.setTestOnBorrow(Boolean.getBoolean(testOnBorrow));
			ds.setValidationQuery(validationQuery);
			ds.setRemoveAbandoned(Boolean.getBoolean(removeAbandoned));
			ds.setRemoveAbandonedTimeout(Integer.parseInt(removeAbandonedTimeout));
			ds.setLogAbandoned(Boolean.getBoolean(logAbandoned));
			ds.setTestWhileIdle(Boolean.getBoolean(testWhileIdle));
			ds.setTimeBetweenEvictionRunsMillis(Integer.parseInt(timeBetweenEvictionRunsMillis));
			bds.put(dsName, ds);
			
		} catch (Exception e) {
			
			System.out.println("连接池初始化，读取数据库 连接池 配置文件DBConfig出错！");
		}
	}
}