package com.offerme.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.offerme.server.model.business.message.CVList;
import com.offerme.server.model.business.message.ChatMessage;
import com.offerme.server.model.business.message.ChatMessageList;
import com.offerme.server.model.business.message.PollingMessageList;
import com.offerme.server.model.business.personinfo.PersonalCV;
import com.offerme.server.model.business.personinfo.PersonalWorkInfo;
import com.offerme.server.model.db.Cv;
import com.offerme.server.model.db.Message;
import com.offerme.server.model.db.Offer;
import com.offerme.server.model.db.User;
import com.offerme.server.service.ApplySrvc;
import com.offerme.server.service.MessageSrvc;
import com.offerme.server.service.OfferSrvc;
import com.offerme.server.service.PollingSrvc;
import com.offerme.server.service.UserSrvc;

@Service("pollingSrvc")
@Transactional
public class PollingSrvcImpl implements PollingSrvc {

	@Autowired
	private MessageSrvc messageSrvc;
	@Autowired
	private UserSrvc userSrvc;
	@Autowired
	private OfferSrvc offerSrvc;
	@Autowired
	private ApplySrvc applySrvc;

	private ChatMessageList chatList = new ChatMessageList();
	private CVList cvList = new CVList();
	private PollingMessageList pollingList = new PollingMessageList();

	@Override
	public PollingMessageList getComingMessages(Integer userId) {
		try {
			getChatList(userId);
			getCVList(userId);
			pollingList.setChatMessageList(chatList);
			pollingList.setCvList(cvList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pollingList;
	}

	private void getChatList(Integer userId) {
		try {
			List<Message> messages = messageSrvc
					.getMessagesByReceiverIncrem(userId);
			chatList = convertMessagesToChatMessageList(messages);
			chatList.setResponseCode(0);
		} catch (Exception e) {
			chatList.setResponseCode(-1);
			e.printStackTrace();
		}
	}

	private void getCVList(Integer userId) {
		try {
			List<Hashtable<Cv, Integer>> cvs = applySrvc.getCommingCV(userId);
			cvList = convertToPersonalCV(cvs);
			cvList.setResponseCode(0);
		} catch (Exception e) {
			cvList.setResponseCode(-1);
			e.printStackTrace();
		}
	}

	private CVList convertToPersonalCV(List<Hashtable<Cv, Integer>> cvs) {
		CVList cvlist = new CVList();
		try {
			if (!CollectionUtils.isEmpty(cvs)) {
				for (int count = 0; count < cvs.size(); count++) {
					Set<Cv> key = cvs.get(count).keySet();
					for (Cv cv : key) {
						// cv = cvs.get(count).get(new Integer(count));
						PersonalCV personalCV = new PersonalCV();
						personalCV
								.setAge(String.valueOf(getUser(cv.getUserId())
										.getAge()));
						personalCV.setColleage(cv.getSchool());
						personalCV.setCity(getCity(cv.getUserId()));
						personalCV.setEducation(cv.getDiploma());
						personalCV.setEntreprise(cv.getCurrentCompany());
						personalCV.setName(getUser(cv.getUserId()).getName());
						personalCV.setPost(cv.getCurrentPost());
						personalCV.setProfile(getUser(cv.getUserId())
								.getPortrait());
						personalCV.setUserID(String.valueOf(cv.getUserId()));
						personalCV.setWorkyear(String.valueOf(cv.getJobYear()));
						personalCV.setWorkInfo(getWorkInfo(cv));
						personalCV.setDate(new SimpleDateFormat("yyyy-MM-dd")
								.format(cv.getDate()));
						personalCV.setPostApplied(getPostApplied(cvs.get(count)
								.get(cv)));
						personalCV.setPostID(String.valueOf(cvs.get(count).get(
								cv)));
						cvlist.getCVList().add(personalCV);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cvlist;
	}

	private String getCity(Integer userId) {
		String city = null;
		User user;
		try {
			user = userSrvc.getUserById(userId);
			city = user.getCity();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return city;
	}

	private PersonalWorkInfo getWorkInfo(Cv cv) {
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

	private String getPostApplied(Integer postId) {
		String resualt = null;
		try {
			Offer offer = offerSrvc.getOfferById(postId);
			resualt = offer.getTitle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resualt;
	}

	private ChatMessageList convertMessagesToChatMessageList(
			List<Message> messages) {

		ChatMessageList res = new ChatMessageList();
		try {
			if (!CollectionUtils.isEmpty(messages)) {
				for (Message message : messages) {
					ChatMessage chatMsg = new ChatMessage();
					chatMsg.setMessageId(message.getMessageId());
					chatMsg.setUserId(message.getsUserId());
					User user = userSrvc.getUserById(message.getsUserId());
					chatMsg.setName(user != null ? user.getName() : "");
					chatMsg.setComMsg(true);
					chatMsg.setText(message.getContent());

					if (isProfileChanged(message, message.getsUserId())) {
						chatMsg.setProfile(getUser(message.getsUserId())
								.getPortrait());
					}

					chatMsg.setProfileVersion(getUser(message.getsUserId())
							.getPortraitUpdateCount());
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					chatMsg.setDate(df.format(message.getDate()));
					res.getChatMessageList().add(chatMsg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private boolean isProfileChanged(Message message, int userID) {
		try {
			if (message.getProfileVersion() < getUser(userID)
					.getPortraitUpdateCount()
					&& message.getProfileVersion() != ChatMessage.DEFAULT_PROFILE_VERSION) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private User getUser(int userID) {
		return userSrvc.getUserById(userID);
	}

}
