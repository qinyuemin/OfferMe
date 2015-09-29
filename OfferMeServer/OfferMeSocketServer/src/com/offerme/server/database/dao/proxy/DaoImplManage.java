package com.offerme.server.database.dao.proxy;

import java.lang.reflect.Proxy;

import com.offerme.server.database.dao.ApplyInfoDao;
import com.offerme.server.database.dao.CVDao;
import com.offerme.server.database.dao.ClassementDao;
import com.offerme.server.database.dao.ClassementDetailDao;
import com.offerme.server.database.dao.MessageDao;
import com.offerme.server.database.dao.OfferDao;
import com.offerme.server.database.dao.OfferUserDao;
import com.offerme.server.database.dao.UserDao;
import com.offerme.server.database.dao.impl.ApplyInfoDaoImpl;
import com.offerme.server.database.dao.impl.CVDaoImpl;
import com.offerme.server.database.dao.impl.ClassementDaoImpl;
import com.offerme.server.database.dao.impl.ClassementDetailDaoImpl;
import com.offerme.server.database.dao.impl.MessageDaoImpl;
import com.offerme.server.database.dao.impl.OfferDaoImpl;
import com.offerme.server.database.dao.impl.OfferUserDaoImpl;
import com.offerme.server.database.dao.impl.UserDaoImpl;

/**
 * 动态代理获取Dao实现的管理类
 * 
 * @author Edouard.Zhang
 * 
 */
public class DaoImplManage {

	/**
	 * 获取UserDao实现
	 * 
	 * @return
	 */
	public static UserDao getUserDaoInstance() {

		UserDao userDao = (UserDao) Proxy.newProxyInstance(UserDao.class
				.getClassLoader(), UserDaoImpl.class.getInterfaces(),
				new InvocationHandlerImp(new UserDaoImpl()));

		return userDao;
	}

	public static UserDao getUserDaoInstance(OfferDao offerDao) {

		UserDao userDao = (UserDao) Proxy.newProxyInstance(UserDao.class
				.getClassLoader(), UserDaoImpl.class.getInterfaces(),
				new InvocationHandlerImp(new UserDaoImpl(offerDao)));

		return userDao;
	}

	/**
	 * 获取OfferDao实现
	 * 
	 * @return
	 */
	public static OfferDao getOfferDaoInstance() {

		OfferDao offerDao = (OfferDao) Proxy.newProxyInstance(OfferDao.class
				.getClassLoader(), OfferDaoImpl.class.getInterfaces(),
				new InvocationHandlerImp(new OfferDaoImpl()));

		return offerDao;
	}

	public static OfferDao getOfferDaoInstance(UserDao userDao) {

		OfferDao offerDao = (OfferDao) Proxy.newProxyInstance(OfferDao.class
				.getClassLoader(), OfferDaoImpl.class.getInterfaces(),
				new InvocationHandlerImp(new OfferDaoImpl(userDao)));

		return offerDao;
	}

	/**
	 * 获取MessageDao实现
	 * 
	 * @return
	 */
	public static MessageDao getMessageDaoInstance() {

		MessageDao messageDao = (MessageDao) Proxy.newProxyInstance(
				MessageDao.class.getClassLoader(), MessageDaoImpl.class
						.getInterfaces(), new InvocationHandlerImp(
						new MessageDaoImpl()));

		return messageDao;
	}

	/**
	 * 获取ClassementDao实现
	 * 
	 * @return
	 */
	public static ClassementDao getClassementDaoInstance() {

		ClassementDao classementDao = (ClassementDao) Proxy.newProxyInstance(
				ClassementDao.class.getClassLoader(),
				ClassementDaoImpl.class.getInterfaces(),
				new InvocationHandlerImp(new ClassementDaoImpl()));

		return classementDao;
	}

	/**
	 * 获取ClassementDetailDao实现
	 * 
	 * @return
	 */
	public static ClassementDetailDao getClassementDetailDaoInstance() {

		ClassementDetailDao classementDetailDao = (ClassementDetailDao) Proxy
				.newProxyInstance(ClassementDetailDao.class.getClassLoader(),
						ClassementDetailDaoImpl.class.getInterfaces(),
						new InvocationHandlerImp(new ClassementDetailDaoImpl()));

		return classementDetailDao;
	}

	/**
	 * 获取OfferUserDao实现
	 * 
	 * @return
	 */
	public static OfferUserDao getOfferUserDaoInstance() {

		OfferUserDao offerUserDao = (OfferUserDao) Proxy.newProxyInstance(
				OfferUserDao.class.getClassLoader(),
				OfferUserDaoImpl.class.getInterfaces(),
				new InvocationHandlerImp(new OfferUserDaoImpl()));

		return offerUserDao;
	}
	
	public static ApplyInfoDao getApplyInfoDaoInstance()
	{
		ApplyInfoDao applyInfoDao = (ApplyInfoDao) Proxy.newProxyInstance(
				ApplyInfoDao.class.getClassLoader(),
				ApplyInfoDaoImpl.class.getInterfaces(),
				new InvocationHandlerImp(new ApplyInfoDaoImpl()));

		return applyInfoDao;
	}
	
	public static CVDao getCVDaoInstance()
	{
		CVDao cVDao = (CVDao) Proxy.newProxyInstance(
				CVDao.class.getClassLoader(),
				CVDaoImpl.class.getInterfaces(),
				new InvocationHandlerImp(new CVDaoImpl()));

		return cVDao;
	}


}
