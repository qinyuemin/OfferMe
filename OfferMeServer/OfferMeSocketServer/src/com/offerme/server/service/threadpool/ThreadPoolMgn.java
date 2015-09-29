package com.offerme.server.service.threadpool;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.offerme.server.util.Log;

/**
 * 
 * 线程池的管理类
 */
public class ThreadPoolMgn {
	

	static Log myLog = new Log("com.offerme.database.threadpool");

	//业务处理线程池。
	private ThreadPoolExecutor processThreadPool;
	//核心线程池数量
	private int corePoolSize;
	// 最大线程池数量
	private int maximumPoolSize;
	// 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
	private int keepAliveTime;
	//队列大小
	private int queueSize;
	// 队列实例
	private BlockingQueue<Runnable> blockingQueue;
	
	/**
	 * 得到业务处理线程池
	 */
	public synchronized ThreadPoolExecutor getProcessThreadPool(){
		
		if (this.processThreadPool==null){
			return (processThreadPool = this.createProcessThread());
		}else{
			return this.processThreadPool;
		}
		
	}
	
	/**
	 * 创建新的线程池.
	 * 
	 */	
	private  ThreadPoolExecutor createProcessThread() {

		this.configParam();
		// 初始化线程池
		return  new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
				keepAliveTime, TimeUnit.MILLISECONDS,
				this.blockingQueue);

		
	}

	/**
	 * 从配置文件中读取线程池配置信息,配置线程池参数。
	 */
	private void configParam(){

		try {
			String path = System.getProperty("user.dir");
			File inf = new File(path + "/XMLFiles/" + "thread-pool.properties");
			FileInputStream is = new FileInputStream(inf);
			Properties dbProps = new Properties();
			dbProps.load(is);


			// 设置线程池参数
			 // 池中所保存的线程数，包括空闲线程。
			 this.corePoolSize= Integer.parseInt(dbProps.getProperty("corePoolSize"));
			// 池中允许的最大线程数。
			this.maximumPoolSize = Integer.parseInt(dbProps.getProperty("maximumPoolSize"));
			// 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
			 this.keepAliveTime= Integer.parseInt(dbProps.getProperty("keepAliveTime"));
			//队列大小
			 this.queueSize= Integer.parseInt(dbProps.getProperty("queueSize"));
			
			// 设置队列类型
			// 如果 corePoolSize=maximumPoolSize，队列为无界队列,否则为有界队列。
			 if (this.corePoolSize>=this.maximumPoolSize)
				 if(this.queueSize <= 0) //配置文件中配置的队列大小小于等于0，采用默认值初始化。Integer.MAX_VALUE
					 this.blockingQueue=new LinkedBlockingQueue<Runnable>();
				 else
					 this.blockingQueue=new LinkedBlockingQueue<Runnable>(this.queueSize);
			 else
				 this.blockingQueue=new ArrayBlockingQueue<Runnable>(this.queueSize,true);
			 
		} catch (Exception e) {
			
			myLog.error(Log.getStackInfo(e));
		
		}
	
	}

	
}
