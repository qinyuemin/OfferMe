package com.offerme.server.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.offerme.server.database.dao.ApplyInfoDao;
import com.offerme.server.database.dao.MessageDao;
import com.offerme.server.database.dao.OfferDao;
import com.offerme.server.database.dao.UserDao;
import com.offerme.server.database.dao.proxy.DaoImplManage;
import com.offerme.server.database.model.CV;
import com.offerme.server.database.model.Message;
import com.offerme.server.database.model.Offer;
import com.offerme.server.database.model.User;
import com.offerme.server.service.bean.CVList;
import com.offerme.server.service.bean.ChatMessage;
import com.offerme.server.service.bean.ChatMessageList;
import com.offerme.server.service.bean.PersonalCV;
import com.offerme.server.service.bean.PersonalWorkInfo;
import com.offerme.server.service.bean.PollingMessageList;
import com.offerme.server.util.Log;

public class PollingSrvc {

	private static final Log LOG = new Log(
			"com.offerme.server.service.PollingSrvc");

	private MessageDao msgDao = DaoImplManage.getMessageDaoInstance();
	private UserDao userDao = DaoImplManage.getUserDaoInstance();
	private OfferDao offerDao = DaoImplManage.getOfferDaoInstance();
	private ApplyInfoDao applyDao = DaoImplManage.getApplyInfoDaoInstance();
	private ChatMessageList chatList = new ChatMessageList();
	private CVList cvList = new CVList();
	private PollingMessageList pollingList = new PollingMessageList();

	public PollingMessageList getComingMessages(Integer userId) {
		getChatList(userId);
		getCVList(userId);
		pollingList.setChatMessageList(chatList);
		pollingList.setCvList(cvList);
		return pollingList;
	}

	private void getChatList(Integer userId) {
		try {
			List<Message> messages = msgDao.getMessagesByReceiverIncrem(userId);
			chatList = convertMessagesToChatMessageList(messages);
			chatList.setResponseCode(0);
		} catch (Exception e) {
			chatList.setResponseCode(-1);
			LOG.error("Cannot get coming messages, userId: " + userId);
		}
	}

	private void getCVList(Integer userId) {
		try {
			List<Hashtable<CV, Integer>> cvs = applyDao.getCommingCV(userId);
			cvList = convertToPersonalCV(cvs);
			cvList.setResponseCode(0);
		} catch (Exception e) {
			cvList.setResponseCode(-1);
			e.printStackTrace();
		}
	}

	private CVList convertToPersonalCV(List<Hashtable<CV, Integer>> cvs) {
		CVList cvlist = new CVList();
		try {
			if (CollectionUtils.isNotEmpty(cvs)) {
				for (int count = 0; count < cvs.size(); count++) {
					Set<CV> key = cvs.get(count).keySet();

					for (CV cv : key) {
						// cv = cvs.get(count).get(new Integer(count));
						User applier = getUser(cv.getUserId());
						PersonalCV personalCV = new PersonalCV();
						personalCV.setAge(String.valueOf(applier.getAge()));
						personalCV.setColleage(cv.getSchool());
						personalCV.setCity(getCity(cv.getUserId()));
						personalCV.setEducation(cv.getDiploma());
						personalCV.setEntreprise(cv.getCurrentCompany());
						personalCV.setName(applier.getName());
						personalCV.setPost(cv.getCurrentPost());
						personalCV.setProfile(applier.getPortrait());
						personalCV.setUserID(String.valueOf(cv.getUserId()));
						personalCV.setWorkyear(String.valueOf(cv.getJobYear()));
						personalCV.setWorkInfo(getWorkInfo(cv));
						personalCV.setDate(getDate(cv.getDate()));
						personalCV.setPostID(getPostID(cvs.get(count).get(cv)));
						personalCV.setPostApplied(getPostApplied(cvs.get(count)
								.get(cv)));
						cvlist.getCVList().add(personalCV);
					}

				}
			}
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return cvlist;
	}

	private String getPostApplied(Integer postId) {
		String resualt = null;
		try {
			Offer offer = offerDao.getOffer(postId);
			resualt = offer.getTitle();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return resualt;
	}

	private String getPostID(Integer postId) {
		return String.valueOf(postId);
	}

	private String getDate(Timestamp ts) {
		String date = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date = dateFormat.format(ts);
		return date;
	}

	private String getCity(Integer userId) {
		String city = null;
		User user;
		try {
			user = userDao.getUser(userId);
			city = user.getCity();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return city;
	}

	private PersonalWorkInfo getWorkInfo(CV cv) {
		PersonalWorkInfo workinfo = new PersonalWorkInfo();
		workinfo.setFirstWork(cv.getJobCompany01());
		workinfo.setFirstWorkPost(cv.getJobPost01());
		workinfo.setFirstWorkyear(String.valueOf(cv.getJobYear01()));
		workinfo.setSecondWork(cv.getJobCompany02());
		workinfo.setSecondWorkPost(cv.getJobPost02());
		workinfo.setSecondWorkyear(String.valueOf(cv.getJobYear02()));
		workinfo.setThirdWork(cv.getJobCompany03());
		workinfo.setThirdWorkPost(cv.getJobPost03());
		workinfo.setThirdWorkyear(String.valueOf(cv.getJobYear03()));
		return workinfo;
	}

	private ChatMessageList convertMessagesToChatMessageList(
			List<Message> messages) {

		ChatMessageList res = new ChatMessageList();
		try {
			if (CollectionUtils.isNotEmpty(messages)) {
				for (Message message : messages) {
					ChatMessage chatMsg = new ChatMessage();
					chatMsg.setMessageId(String.valueOf(message.getMessageId()));//temporary should be string
					chatMsg.setUserId(message.getsUserId());
					chatMsg.setName(message.getSUser().getName());
					chatMsg.setComMsg(true);
					chatMsg.setText(message.getContent());

					if (isProfileChanged(message, message.getsUserId())) {
						chatMsg.setProfile(getUser(message.getsUserId())
								.getPortrait());
					}

					chatMsg.setProfileVersion(getUser(message.getsUserId())
							.getPortraitUpdateDt());
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					chatMsg.setDate(df.format(message.getDate()));
					res.getChatMessageList().add(chatMsg);
				}
			}
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return res;
	}

	private boolean isProfileChanged(Message message, int userID) {
		try {
			System.out
					.println("Show me the message profile version and update: "
							+ message.getProfileVersion() + " "
							+ getUser(userID).getPortraitUpdateDt());
			if (message.getProfileVersion() < getUser(userID)
					.getPortraitUpdateDt()
					&& message.getProfileVersion() != ChatMessage.DEFAULT_PROFILE_VERSION) {
				return true;
			}

		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	private User getUser(int userID) throws InvocationTargetException {
		return userDao.getUser(userID);
	}

}
