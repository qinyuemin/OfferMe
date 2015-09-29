package com.offerme.server.service.threadpool;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.offerme.server.MainServer;
import com.offerme.server.database.dbpool.JDBCDataConnection;
import com.offerme.server.service.ApplyOfferSrvc;
import com.offerme.server.service.PollingSrvc;
import com.offerme.server.service.DeleteOfferSrvc;
import com.offerme.server.service.FavoriteSrvc;
import com.offerme.server.service.InscriptionSrvc;
import com.offerme.server.service.LoginSrvc;
import com.offerme.server.service.PersonalSettingSrvc;
import com.offerme.server.service.PublishOfferSrvc;
import com.offerme.server.service.SaveCVSrvc;
import com.offerme.server.service.SearchSrvc;
import com.offerme.server.service.SendMessageSrvc;
import com.offerme.server.service.bean.LoginRequest;
import com.offerme.server.service.bean.LoginResponse;
import com.offerme.server.service.bean.PersonalSetting;
import com.offerme.server.service.bean.PollingMessageList;
import com.offerme.server.service.bean.SearchResulat;
import com.offerme.server.util.JSONUtil;

/**
 * 分发请求管理类
 * 
 * @author Edouard.Zhang
 * 
 */
public class DistributeManage {

	/**
	 * @param request
	 * @param reply
	 * @return
	 */
	public static String DistributeRequest(Request request, Reply reply,
			String clientIP) {
		String strError = "";
		long now = System.currentTimeMillis();
		try {
			switch (request.getServiceRequestType()) {
			case DistributeType.USER_INSCRIPT: {
				InscriptionSrvc srvc = new InscriptionSrvc();
				String strReturn = srvc.inscriptUser(request.getParam(0));
				reply.AddReply("RESPONSE", strReturn);
				break;
			}
			case DistributeType.PUBLISH_OFFER: {
				PublishOfferSrvc srvc = new PublishOfferSrvc();
				String strReturn = srvc.publishOffer(request.getParam(0));
				reply.AddReply("RESPONSE", strReturn);
				break;
			}
			case DistributeType.LOGIN: {
				LoginRequest req = JSONUtil.jsonToBean(request.getParam(0),
						LoginRequest.class);
				LoginSrvc srvc = new LoginSrvc();
				LoginResponse res = srvc.login(req.getLogin(),
						req.getPassword());
				reply.AddReply("RESPONSE", res);
				break;
			}
			case DistributeType.SEARCH: {
				SearchSrvc srvc = new SearchSrvc();
				SearchResulat resualt = srvc.getSearchResulat(request
						.getParam(0));
				reply.AddReply("RESPONSE", resualt);
				break;
			}
			case DistributeType.PERSONAL_SETTING: {
				PersonalSetting setting = JSONUtil.jsonToBean(
						request.getParam(0), PersonalSetting.class);
				PersonalSettingSrvc srvc = new PersonalSettingSrvc();
				String res = srvc.savePersonalSettingOnServer(setting);
				reply.AddReply("RESPONSE", res);
				break;
			}
			case DistributeType.POLLING: {
				PollingSrvc srvc = new PollingSrvc();
				PollingMessageList pollingMsgList = srvc
						.getComingMessages(Integer.parseInt(request.getParam(0)));

				if (pollingMsgList.getChatMessageList() != null
						&& pollingMsgList.getChatMessageList()
								.getChatMessageList().size() > 0) {
					System.out.println("message: "
							+ pollingMsgList.getChatMessageList()
									.getChatMessageList().get(0).getText());
				}// This is for test

				reply.AddReply("RESPONSE", pollingMsgList);
				break;
			}
			case DistributeType.FAVORITE: {
				FavoriteSrvc srvc = new FavoriteSrvc();
				String rlt = srvc.setFavoriteOffer(request.getParam(0));
				reply.AddReply("RESPONSE", rlt);
				break;
			}
			case DistributeType.SEND_MESSAGE: {
				SendMessageSrvc srvc = new SendMessageSrvc();
				Integer messageId = srvc.saveMessage(request.getParam(0));
				reply.AddReply("RESPONSE", messageId);
				break;
			}
			case DistributeType.DELETE_OFFER: {
				DeleteOfferSrvc srvc = new DeleteOfferSrvc();
				String rlt = srvc.deleteOffer(request.getParam(0));
				reply.AddReply("RESPONSE", rlt);
				break;
			}
			case DistributeType.SAVE_CV: {
				SaveCVSrvc srvc = new SaveCVSrvc();
				String strReturn = srvc.saveCV(request.getParam(0));
				reply.AddReply("RESPONSE", strReturn);
				break;
			}
			case DistributeType.APPLY_OFFER: {
				ApplyOfferSrvc srvc = new ApplyOfferSrvc();
				String strReturn = srvc.applyOffer(request.getParam(0));
				reply.AddReply("RESPONSE", strReturn);
				break;
			}
			default: {
				break;
			}
			}

		} catch (Throwable e) {
			if (e.getMessage() == null || "".equals(e.getMessage())) {
				reply.SetError(e.getCause().getMessage());
			} else {
				reply.SetError(e.getMessage());
			}
		} finally {
			if (MainServer.getMainServer().m_SettingData.bStatistique) {
				// 统计请求
				String sql = "Insert into Statistique (clientIP,request,requesttime,date) values (?,?,?,?)";
				PreparedStatement ptmt = null;
				try {
					ptmt = JDBCDataConnection.getConnection().prepareStatement(
							sql);
					ptmt.setString(1, clientIP);
					ptmt.setInt(2, request.getServiceRequestType());
					ptmt.setLong(3, System.currentTimeMillis() - now);
					ptmt.setTimestamp(4, new Timestamp(new Date().getTime()));
					ptmt.executeUpdate();
				} catch (Exception e2) {

				} finally {
					if (ptmt != null) {
						try {
							ptmt.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
			// Edouard.Zhang 2014-06-27 增加对连接的释放 防止mysql超过8小时连接无效的问题
			// TODO 有待测试
			JDBCDataConnection.disconnect();
		}
		return strError;
	}

	/**
	 * 约定好所有请求号
	 * 
	 * @author Edouard.Zhang *
	 */
	public class DistributeType {
		/**
		 * 用户注册
		 */
		public static final int USER_INSCRIPT = 1000;

		public static final int PUBLISH_OFFER = 2000;

		public static final int LOGIN = 3000;

		public static final int SEARCH = 4000;

		public static final int PERSONAL_SETTING = 5000;

		public static final int POLLING = 6000;

		public static final int FAVORITE = 7000;

		public static final int SEND_MESSAGE = 8000;

		public static final int DELETE_OFFER = 9000;

		public static final int SAVE_CV = 10000;

		public static final int APPLY_OFFER = 11000;

	}

}
