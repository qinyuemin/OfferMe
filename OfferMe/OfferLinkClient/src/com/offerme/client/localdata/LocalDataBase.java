package com.offerme.client.localdata;

import java.util.ArrayList;
import java.util.List;

import com.offerme.client.service.chat.ChatFriend;
import com.offerme.client.service.chat.ChatMessage;
import com.offerme.client.service.cv.PersonalCV;
import com.offerme.client.service.cv.PersonalWorkInfo;
import com.offerme.client.service.publishoffer.OfferInfo;
import com.offerme.client.service.publishoffer.PublisherInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDataBase {

	private static LocalDataBase data = null;
	private LocalPersonInfo personInfo = null;
	private static SQLiteDatabase dataBase = null;
	private Context context = null;
	private List<Integer> newMessageFriends = new ArrayList<Integer>();
	private static boolean newCommingCV = false;
	private final static String DATABASENAME = "OfferMeLocalDB.db";
	private final static int DATABASEVERSION = 1;
	public final static String OFFERTABLENAME = "OfferTable";
	public final static String MESSAGETABLENAME = "MessageTable";
	public final static String FRIENDTABLENAME = "FriendTable";
	public final static String USERTABLENAME = "UserTable";
	public final static String CVTABLENAME = "CVTable";

	private LocalDataBase(Context ctx) {
		context = ctx;
		personInfo = LocalPersonInfo.getInstance(ctx);
	}

	public static LocalDataBase getInstance(Context context) {
		if (data == null) {
			data = new LocalDataBase(context);
		}
		return data;
	}

	public void deleteDataBase(String tableName) {
		connectDataBase();
		String sql = "DROP TABLE " + tableName;
		System.out
				.println("LogcalDataBase->deleteDataBase: Show me the delete sql: "
						+ sql);
		dataBase.execSQL(sql);
		closeDataBase();
	}

	public void connectDataBase() {
		openHelper helper = new openHelper();
		if (dataBase != null && dataBase.isOpen()) {
			closeDataBase();
		}
		dataBase = helper.getWritableDatabase();
		createOfferTable();
		createMessageTable();
		createFriendTable();
		createUserTable();
		createCVTable();
	}

	public boolean createUserTable() {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS " + USERTABLENAME + " ( "
					+ " TELEPHONE VARCHAR(50) DEFAULT NULL, "
					+ " MAIL  VARCHAR(100) DEFAULT NULL, "
					+ " NAME  VARCHAR(100)  DEFAULT NULL , "
					+ " CITY VARCHAR(10) DEFAULT NULL, "
					+ " ENTREPRISE  VARCHAR(100) DEFAULT NULL, "
					+ " ISPHONEPUBLIC VARCHAR(100) DEFAULT FALSE, "
					+ " ISMAILPUBLIC VARCHAR(100) DEFAULT FALSE, "
					+ " PROFILE BLOB DEFAULT NULL, "
					+ " USERID VARCHAR(100) DEFAULT NULL, "
					+ " POST VARCHAR(100) DEFAULT NULL,"
					+ "PRIMARY KEY ('USERID'))";
			if (dataBase.isOpen()) {
				dataBase.execSQL(sql);
				return true;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return false;
	}

	private boolean createOfferTable() {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS " + OFFERTABLENAME + " ( "
					+ " OFFERID VARCHAR(100) DEFAULT NULL, "
					+ " DATE  VARCHAR(20) DEFAULT NULL, "
					+ " USERID  VARCHAR(100)  DEFAULT NULL , "
					+ " FAVORITE VARCHAR(5) DEFAULT FALSE, "
					+ " ENTREPRISE  VARCHAR(100) DEFAULT NULL, "
					+ " CITY VARCHAR(100) DEFAULT NULL, "
					+ " POST VARCHAR(100) DEFAULT NULL, "
					+ " DOMAIN VARCHAR(100) DEFAULT NULL, "
					+ " SALARY VARCHAR(100) DEFAULT NULL, "
					+ " DESCRIPTION VARCHAR(1000) DEFAULT NULL, "
					+ " APPLIED VARCHAR(5) DEFAULT FALSE, "
					+ " OFFEROWNERID VARCHAR(20) DEFAULT NULL, "
					+ " WORKYEAR VARCHAR(100) DEFAULT NULL, "
					+ " EDUCATION VARCHAR(100) DEFAULT NULL, "
					+ " MAIL VARCHAR(100) DEFAULT NULL, "
					+ " PRIMARY KEY ('OFFERID'))";
			if (dataBase.isOpen()) {
				dataBase.execSQL(sql);
				return true;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return false;
	}

	private boolean createMessageTable() {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS " + MESSAGETABLENAME
					+ " ( " + " MESSAGE_ID VARCHAR(100) DEFAULT NULL, "
					+ " SENDER_ID numeric(100) DEFAULT NULL, "
					+ " SENDER varchar(100) DEFAULT NULL, "
					+ " RECEIVER_ID numeric(100)  DEFAULT NULL , "
					+ " RECEIVER varchar(100)  DEFAULT NULL , "
					+ " CONTENT varchar(512) DEFAULT NULL, "
					+ " IS_COMING char(1) DEFAULT NULL, "
					+ " RECEIVE_DATE varchar(100) DEFAULT NULL, "
					+ " IS_READ char(1) DEFAULT NULL, "
					+ " USERID VARCHAR(100) DEFAULT NULL, "
					+ "PRIMARY KEY ('MESSAGE_ID',USERID))";
			if (dataBase.isOpen()) {
				dataBase.execSQL(sql);
				return true;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return false;
	}

	private boolean createFriendTable() {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS " + FRIENDTABLENAME
					+ " ( " + " FRIEND_ID numeric(100) DEFAULT NULL, "
					+ " NAME varchar(100) DEFAULT NULL, "
					+ " PORTRAIT blob DEFAULT NULL, "
					+ " LAST_MESSAGE_ID numeric(100)  DEFAULT NULL , "
					+ " USERID VARCHAR(100) DEFAULT NULL, "
					+ " FRIEND_PROTRAIT_VERSION numeric(100) DEFAULT NULL, "
					+ "PRIMARY KEY ('FRIEND_ID','USERID'))";
			if (dataBase.isOpen()) {
				dataBase.execSQL(sql);
				return true;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return false;
	}

	private boolean createCVTable() {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS " + CVTABLENAME + " ( "
					+ " CVUSERID VARCHAR(10) DEFAULT NULL, "
					+ " NAME  VARCHAR(100) DEFAULT NULL, "
					+ " POST  VARCHAR(100)  DEFAULT NULL , "
					+ " AGE VARCHAR(10) DEFAULT NULL, "
					+ " ENTREPRISE  VARCHAR(100) DEFAULT NULL, "
					+ " WORKYEAR VARCHAR(10) DEFAULT NULL, "
					+ " EDUCATION VARCHAR(100) DEFAULT NULL, "
					+ " COLLEAGE VARCHAR(100) DEFAULT NULL, "
					+ " FIRSTWORKENTREPRISE VARCHAR(100) DEFAULT NULL, "
					+ " FIRSTWORKYEAR VARCHAR(10) DEFAULT NULL, "
					+ " FIRSTWORKPOST VARCHAR(100) DEFAULT NULL, "
					+ " SECONDWORKENTREPRISE VARCHAR(100) DEFAULT NULL, "
					+ " SECONDWORKYEAR VARCHAR(10) DEFAULT NULL, "
					+ " SECONDWORKPOST VARCHAR(100) DEFAULT NULL, "
					+ " THIRDWORKENTREPRISE VARCHAR(100) DEFAULT NULL, "
					+ " THIRDWORKYEAR VARCHAR(10) DEFAULT NULL, "
					+ " THIRDWORKPOST VARCHAR(100) DEFAULT NULL, "
					+ "	DATE VARCHAR(100) DEFAULT NULL,"
					+ " PROFILE BLOB DEFAULT NULL, "
					+ "	CITY VARCHAR(100) DEFAULT NULL,"
					+ " POSTID VARCHAR(100) DEFAULT NULL,"
					+ " POSTAPPLIED VARCHAR(100) DEFAULT NULL,"
					+ " USERID VARCHAR(100) DEFAULT NULL, "
					+ "PRIMARY KEY ('CVUSERID','USERID','POSTID'))";
			if (dataBase.isOpen()) {
				dataBase.execSQL(sql);
				return true;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return false;
	}

	synchronized public boolean hasOffer(String offerID) {
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			String sql = "SELECT * FROM " + OFFERTABLENAME + " WHERE OFFERID="
					+ offerID;
			Cursor cursor = dataBase.rawQuery(sql, null);
			if (cursor.isAfterLast()) {
				return false;
			}
			cursor.moveToFirst();
			cursor.close();
			// closeDataBase();
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	synchronized public boolean hasMessage(String messageId) {
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			String sql = "SELECT * FROM " + MESSAGETABLENAME
					+ " WHERE MESSAGE_ID = '" + messageId + "' AND USERID= "
					+ personInfo.getValue(LocalPersonInfo.USERID);
			Cursor cursor = dataBase.rawQuery(sql, null);
			if (cursor.isAfterLast()) {
				return false;
			}
			cursor.moveToFirst();
			cursor.close();
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	synchronized public boolean hasFriend(Integer friendId) {
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			String sql = "SELECT * FROM " + FRIENDTABLENAME
					+ " WHERE FRIEND_ID =" + friendId + " AND USERID= "
					+ personInfo.getValue(LocalPersonInfo.USERID);
			Cursor cursor = dataBase.rawQuery(sql, null);
			if (cursor.isAfterLast()) {
				return false;
			}
			cursor.moveToFirst();
			cursor.close();
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	synchronized public boolean hasUser(Integer userId) {
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			String sql = "SELECT * FROM " + USERTABLENAME + " WHERE USERID ="
					+ userId;
			Cursor cursor = dataBase.rawQuery(sql, null);
			if (cursor.isAfterLast()) {
				return false;
			}
			if (userId == Integer.parseInt(personInfo
					.getValue(LocalPersonInfo.USERID))) {
				return false;
			}
			cursor.moveToFirst();
			cursor.close();
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	synchronized public boolean hasCV(Integer cvuserID, Integer postID) {
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			String sql = "SELECT * FROM " + CVTABLENAME + " WHERE CVUSERID="
					+ cvuserID + " AND POSTID = " + postID + " AND USERID = "
					+ personInfo.getValue(LocalPersonInfo.USERID);
			Cursor cursor = dataBase.rawQuery(sql, null);
			// System.out.println("Show me the sql: " + sql);
			if (cursor.isAfterLast()) {
				return false;
			}
			cursor.moveToFirst();
			cursor.close();
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	synchronized public boolean insertUser(PublisherInfo info) {
		try {
			ContentValues values = new ContentValues();
			// System.out.println("Show me the user id: " + info.getUserID());
			if (!hasUser(info.getUserID())) {
				values.put("TELEPHONE", info.getTelephone());
				values.put("MAIL", info.getEmail());
				values.put("NAME", info.getName());
				values.put("CITY", info.getCity());
				values.put("ENTREPRISE", info.getEntreprise());
				values.put("ISPHONEPUBLIC", info.isPhonePublished());
				values.put("ISMAILPUBLIC", info.isMailPublished());
				values.put("PROFILE", info.getProfile());
				values.put("USERID", info.getUserID());
				values.put("POST", info.getPost());
				dataBase.insert(USERTABLENAME, null, values);
			} else {
				updateUser(info);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	synchronized public boolean insertOffer(OfferInfo info) {
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			ContentValues values = new ContentValues();
			if (!hasOffer(info.getOfferID())) {
				values.put("OFFERID", info.getOfferID());
				values.put("DATE", info.getDate());
				values.put("USERID",
						personInfo.getValue(LocalPersonInfo.USERID));
				values.put("FAVORITE", String.valueOf(info.getFavorite()));
				values.put("ENTREPRISE", info.getEntreprise());
				values.put("CITY", info.getCity());
				values.put("POST", info.getPost());
				values.put("DOMAIN", info.getDomain());
				values.put("SALARY", info.getSalary());
				values.put("DESCRIPTION", info.getDescription());
				values.put("APPLIED", String.valueOf(info.getApplied()));
				values.put("OFFEROWNERID", info.getOfferOwnerID());
				values.put("WORKYEAR", info.getWorkyear());
				values.put("EDUCATION", info.getEducation());
				values.put("MAIL", info.getMailbox());
				dataBase.insert(OFFERTABLENAME, null, values);
				insertUser(info.getPublisherInfo());
			} else {
				updateOffer(info);
				insertUser(info.getPublisherInfo());
			}
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		} finally {
			closeDataBase();
		}
		return true;
	}

	synchronized public void insertChatMessages(List<ChatMessage> chatMessages) {
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			ContentValues values = new ContentValues();
			if (chatMessages != null && !chatMessages.isEmpty()) {
				for (ChatMessage chatMessage : chatMessages) {
					if (!hasMessage(chatMessage.getMessageId())) {
						values.put("USERID",
								personInfo.getValue(LocalPersonInfo.USERID));
						values.put("MESSAGE_ID", chatMessage.getMessageId());
						values.put("CONTENT", chatMessage.getText());
						values.put("RECEIVE_DATE", chatMessage.getDate());
						values.put("IS_READ", "N");
						if (chatMessage.isComMsg()) {
							values.put("SENDER_ID", chatMessage.getUserId());
							values.put("SENDER", chatMessage.getName());
							values.put("IS_COMING", "Y");
						} else {
							values.put("RECEIVER_ID", chatMessage.getUserId());
							values.put("RECEIVER", chatMessage.getName());
							values.put("IS_COMING", "N");
						}
						dataBase.insert(MESSAGETABLENAME, null, values);
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			closeDataBase();
		}
	}

	synchronized public void insertOrUpdateFriends(List<ChatFriend> friends) {
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			ContentValues values = new ContentValues();

			if (friends != null && !friends.isEmpty()) {
				for (ChatFriend friend : friends) {
					if (!hasFriend(friend.getFriendId())) {
						values.put("USERID",
								personInfo.getValue(LocalPersonInfo.USERID));
						values.put("FRIEND_ID", friend.getFriendId());
						values.put("NAME", friend.getName());
						if (friend.getPortrait() != null) {
							values.put("PORTRAIT", friend.getPortrait());
						}
						values.put("LAST_MESSAGE_ID", friend.getLastMessageId());
						setFriendProfileVersion(friend);
						values.put("FRIEND_PROTRAIT_VERSION",
								friend.getPortraitVersion());
						dataBase.insert(FRIENDTABLENAME, null, values);
					} else {
						values.put("USERID",
								personInfo.getValue(LocalPersonInfo.USERID));
						values.put("NAME", friend.getName());
						if (friend.getPortrait() != null) {
							values.put("PORTRAIT", friend.getPortrait());
						}
						values.put("LAST_MESSAGE_ID", friend.getLastMessageId());
						setFriendProfileVersion(friend);
						values.put("FRIEND_PROTRAIT_VERSION",
								friend.getPortraitVersion());
						String[] args = { String.valueOf(friend.getFriendId()) };
						dataBase.update(FRIENDTABLENAME, values, "FRIEND_ID=?",
								args);
					}
					insertNewFriend(friend.getFriendId());
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			closeDataBase();
		}
	}

	synchronized public boolean insertCV(List<PersonalCV> cvs) {
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			ContentValues values = new ContentValues();
			if (cvs != null && !cvs.isEmpty()) {
				for (PersonalCV cv : cvs) {
					if (!hasCV(Integer.parseInt(cv.getUserID()),
							Integer.parseInt(cv.getPostID()))) {
						values.put("CVUSERID", cv.getUserID());
						values.put("NAME", cv.getName());
						values.put("POST", cv.getPost());
						values.put("AGE", cv.getAge());
						values.put("ENTREPRISE", cv.getEntreprise());
						values.put("WORKYEAR", cv.getWorkyear());
						values.put("EDUCATION", cv.getEducation());
						values.put("COLLEAGE", cv.getColleage());
						values.put("CITY", cv.getCity());
						values.put("FIRSTWORKENTREPRISE", cv.getWorkInfo()
								.getFirstWork());
						values.put("FIRSTWORKYEAR", cv.getWorkInfo()
								.getFirstWorkyear());
						values.put("FIRSTWORKPOST", cv.getWorkInfo()
								.getFirstWorkPost());
						values.put("SECONDWORKENTREPRISE", cv.getWorkInfo()
								.getSecondWork());
						values.put("SECONDWORKYEAR", cv.getWorkInfo()
								.getSecondWorkyear());
						values.put("SECONDWORKPOST", cv.getWorkInfo()
								.getSecondWorkPost());
						values.put("THIRDWORKENTREPRISE", cv.getWorkInfo()
								.getThirdWork());
						values.put("THIRDWORKYEAR", cv.getWorkInfo()
								.getThirdWorkyear());
						values.put("THIRDWORKPOST", cv.getWorkInfo()
								.getThirdWorkPost());
						values.put("DATE", cv.getDate());
						values.put("PROFILE", cv.getProfile());
						values.put("POSTID", cv.getPostID());
						values.put("POSTAPPLIED", cv.getPostApplied());
						values.put("USERID",
								personInfo.getValue(LocalPersonInfo.USERID));
						dataBase.insert(CVTABLENAME, null, values);
						newCommingCV = true;
					}
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	synchronized public boolean deleteFavoriteOffer(OfferInfo info) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		if (!isOfferApplied(info.getOfferID())) {
			deleteOffer(info);
		} else {
			updateOffer(info);
		}
		closeDataBase();
		return true;
	}

	synchronized public boolean deleteAppliedOffer(OfferInfo info) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		if (!isOfferFavorite(info.getOfferID())) {
			deleteOffer(info);
		} else {
			updateOffer(info);
		}
		closeDataBase();
		return true;
	}

	synchronized public boolean deletePublishedOffer(OfferInfo info) {
		deleteOffer(info);
		return true;
	}

	synchronized public boolean deleteOffer(OfferInfo info) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		String sql = "DELETE FROM " + OFFERTABLENAME + " WHERE OFFERID= "
				+ info.getOfferID() + " AND USERID ="
				+ personInfo.getValue(LocalPersonInfo.USERID);
		System.out
				.println("LocalDataBase->deleteOffer->Show me the delete sql: "
						+ sql);
		dataBase.execSQL(sql);

		/*
		 * if (info.getUserID() != personInfo.getValue(LocalPersonInfo.USERID))
		 * { deleteUser(info.getOfferOwnerID()); }
		 */
		closeDataBase();
		return true;
	}

	synchronized public boolean deleteUser(String userID) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		String sql = "DELETE FROM " + USERTABLENAME + " WHERE USERID= "
				+ userID;
		System.out
				.println("LocalDataBase->deleteUser->Show me the delete sql: "
						+ sql);
		dataBase.execSQL(sql);
		closeDataBase();
		return true;
	}

	synchronized public void deleteChatMessages(List<ChatMessage> chatMessages) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		if (chatMessages != null && !chatMessages.isEmpty()) {
			for (ChatMessage chatMessage : chatMessages) {
				String sql = "DELETE FROM " + MESSAGETABLENAME
						+ " WHERE MESSAGE_ID = " + chatMessage.getMessageId()
						+ " AND USERID= "
						+ personInfo.getValue(LocalPersonInfo.USERID);
				dataBase.execSQL(sql);
			}
		}
		closeDataBase();
	}

	synchronized public void deleteFriend(ChatFriend friend) {
		if (friend != null) {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			String sql = "DELETE FROM " + FRIENDTABLENAME
					+ " WHERE FRIEND_ID = " + friend.getFriendId()
					+ " AND USERID= "
					+ personInfo.getValue(LocalPersonInfo.USERID);
			dataBase.execSQL(sql);
			closeDataBase();
		}
	}

	synchronized public void deleteCV(Integer cvuserID, Integer postID) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		String sql = "DELETE FROM " + CVTABLENAME + " WHERE CVUSERID="
				+ cvuserID + " AND POSTID = " + postID + " AND USERID = "
				+ personInfo.getValue(LocalPersonInfo.USERID);
		// System.out.println("LocalDataBase->deleteCV->Show me the delete sql: "
		// + sql);
		dataBase.execSQL(sql);
		closeDataBase();
	}

	synchronized public boolean updateUser(PublisherInfo info) {
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			ContentValues values = new ContentValues();
			values.put("TELEPHONE", info.getTelephone());
			values.put("MAIL", info.getEmail());
			values.put("NAME", info.getName());
			values.put("CITY", info.getCity());
			values.put("ENTREPRISE", info.getEntreprise());
			values.put("ISPHONEPUBLIC", info.isPhonePublished());
			values.put("ISMAILPUBLIC", info.isMailPublished());
			values.put("PROFILE", info.getProfile());
			values.put("POST", info.getPost());
			String whereClause = "USERID=?";
			String[] whereArgs = { String.valueOf(info.getUserID()) };
			dataBase.update(USERTABLENAME, values, whereClause, whereArgs);
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	synchronized public boolean updateOffer(OfferInfo info) {
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			ContentValues values = new ContentValues();
			values.put("DATE", info.getDate());
			values.put("USERID", personInfo.getValue(LocalPersonInfo.USERID));
			values.put("FAVORITE", String.valueOf(info.getFavorite()));
			values.put("ENTREPRISE", info.getEntreprise());
			values.put("CITY", info.getCity());
			values.put("DOMAIN", info.getDomain());
			values.put("SALARY", info.getSalary());
			values.put("DESCRIPTION", info.getDescription());
			values.put("APPLIED", String.valueOf(info.getApplied()));
			values.put("WORKYEAR", info.getWorkyear());
			values.put("EDUCATION", info.getEducation());
			values.put("MAIL", info.getMailbox());
			values.put("POST", info.getPost());
			String whereClause = "OFFERID=?";
			String[] whereArgs = { info.getOfferID() };
			dataBase.update(OFFERTABLENAME, values, whereClause, whereArgs);
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	synchronized public boolean isOfferFavorite(String offerID) {
		boolean resualt = false;
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			String sql = "SELECT * FROM " + OFFERTABLENAME + " WHERE OFFERID="
					+ offerID + " AND USERID ="
					+ personInfo.getValue(LocalPersonInfo.USERID);
			// System.out.println("Show me the sql: " + sql);
			Cursor cursor = dataBase.rawQuery(sql, null);
			if (cursor.isAfterLast()) {
				return false;
			}
			cursor.moveToFirst();
			resualt = Boolean.parseBoolean(cursor.getString(3));
			closeDataBase();
			cursor.close();
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return resualt;
	}

	synchronized public boolean isOfferApplied(String offerID) {
		boolean resualt = false;
		try {
			if (dataBase == null || !dataBase.isOpen()) {
				connectDataBase();
			}
			String sql = "SELECT * FROM " + OFFERTABLENAME + " WHERE OFFERID="
					+ offerID + " AND USERID ="
					+ personInfo.getValue(LocalPersonInfo.USERID);
			// System.out.println("Show me the statement: " + sql);
			Cursor cursor = dataBase.rawQuery(sql, null);
			if (cursor.isAfterLast()) {
				return false;
			}
			cursor.moveToFirst();
			resualt = Boolean.parseBoolean(cursor.getString(10));
			closeDataBase();
			cursor.close();
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return resualt;
	}

	synchronized public PublisherInfo findUserInfo(String userID) {
		String userSQL = "SELECT * FROM " + USERTABLENAME + " WHERE USERID = "
				+ userID;
		Cursor userCursor = dataBase.rawQuery(userSQL, null);
		// System.out.println("LocalDataBase->findUserInfo->Show me the: "
		// + userSQL);
		userCursor.moveToFirst();
		PublisherInfo info = new PublisherInfo();
		if (userCursor.isAfterLast() == false) {
			info.setTelephone(userCursor.getString(0));
			info.setEmail(userCursor.getString(1));
			info.setName(userCursor.getString(2));
			info.setCity(userCursor.getString(3));
			info.setEntreprise(userCursor.getString(4));
			info.setPhonePublished(Boolean.getBoolean(userCursor.getString(5)));
			info.setMailPublished(Boolean.getBoolean(userCursor.getString(6)));
			info.setProfile(userCursor.getBlob(7));
			info.setUserID(Integer.parseInt(userCursor.getString(8)));
			info.setPost(userCursor.getString(9));
		}
		userCursor.close();
		return info;
	}

	synchronized public ArrayList<OfferInfo> findFavoriteOffer() {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		ArrayList<OfferInfo> offerList = new ArrayList<OfferInfo>();
		String offerSQL = "SELECT * FROM " + OFFERTABLENAME
				+ " WHERE FAVORITE = 'true' AND USERID ="
				+ personInfo.getValue(LocalPersonInfo.USERID);

		Cursor offerCursor = dataBase.rawQuery(offerSQL, null);
		// System.out.println("LocalDataBase->findFavoriteOffer1->Show me the: "
		// + offerSQL);
		offerCursor.moveToFirst();
		while (offerCursor.isAfterLast() == false) {
			OfferInfo offer = new OfferInfo();
			offer.setOfferID(offerCursor.getString(0));
			offer.setDate(offerCursor.getString(1));
			offer.setUserID(offerCursor.getString(2));
			offer.setFavorite(Boolean.valueOf(offerCursor.getString(3)));
			offer.setEntreprise((offerCursor.getString(4)));
			offer.setCity(offerCursor.getString(5));
			offer.setPost(offerCursor.getString(6));
			offer.setDomain(offerCursor.getString(7));
			offer.setSalary(offerCursor.getString(8));
			offer.setDescription(offerCursor.getString(9));
			offer.setApplied(Boolean.valueOf(offerCursor.getString(10)));
			offer.setOfferOwnerID(offerCursor.getString(11));
			offer.setWorkyear(offerCursor.getString(12));
			offer.setEducation(offerCursor.getString(13));
			offer.setMailbox(offerCursor.getString(14));
			offer.setPublisherInfo(findUserInfo(offer.getOfferOwnerID()));
			offerList.add(offer);
			offerCursor.moveToNext();
		}
		offerCursor.close();
		closeDataBase();
		return offerList;
	}

	synchronized public ArrayList<OfferInfo> findPublishedOffer() {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		ArrayList<OfferInfo> offerList = new ArrayList<OfferInfo>();
		String sql = "SELECT * FROM "
				+ OFFERTABLENAME
				+ " WHERE APPLIED = 'false' AND FAVORITE = 'false' AND USERID = "
				+ personInfo.getValue(LocalPersonInfo.USERID);
		Cursor cursor = dataBase.rawQuery(sql, null);
		cursor.moveToFirst();
		// System.out.println("LocalDataBase->findPublishedOffer->Show me the: "
		// + sql);
		while (cursor.isAfterLast() == false) {
			OfferInfo offer = new OfferInfo();
			offer.setOfferID(cursor.getString(0));
			offer.setDate(cursor.getString(1));
			offer.setUserID(cursor.getString(2));
			offer.setFavorite(Boolean.valueOf(cursor.getString(3)));
			offer.setEntreprise(cursor.getString(4));
			offer.setCity(cursor.getString(5));
			offer.setPost(cursor.getString(6));
			offer.setDomain(cursor.getString(7));
			offer.setSalary(cursor.getString(8));
			offer.setDescription(cursor.getString(9));
			offer.setApplied(Boolean.valueOf(cursor.getString(10)));
			offer.setWorkyear(cursor.getString(12));
			offer.setEducation(cursor.getString(13));
			offer.setMailbox(cursor.getString(14));
			offerList.add(offer);
			cursor.moveToNext();
		}
		cursor.close();
		closeDataBase();
		return offerList;
	}

	synchronized public ArrayList<OfferInfo> findAppliedOffer() {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		ArrayList<OfferInfo> offerList = new ArrayList<OfferInfo>();
		String offerSQL = "SELECT * FROM " + OFFERTABLENAME
				+ " WHERE APPLIED = 'true' AND USERID ="
				+ personInfo.getValue(LocalPersonInfo.USERID);
		Cursor cursor = dataBase.rawQuery(offerSQL, null);
		cursor.moveToFirst();
		// System.out.println("LocalDataBase->findAppliedOffer->Show me the: "
		// + offerSQL);
		while (cursor.isAfterLast() == false) {
			OfferInfo offer = new OfferInfo();
			offer.setOfferID(cursor.getString(0));
			offer.setDate(cursor.getString(1));
			offer.setUserID(cursor.getString(2));
			offer.setFavorite(Boolean.valueOf(cursor.getString(3)));
			offer.setEntreprise(cursor.getString(4));
			offer.setCity(cursor.getString(5));
			offer.setPost(cursor.getString(6));
			offer.setDomain(cursor.getString(7));
			offer.setSalary(cursor.getString(8));
			offer.setDescription(cursor.getString(9));
			offer.setApplied(Boolean.valueOf(cursor.getString(10)));
			offer.setOfferOwnerID(cursor.getString(11));
			offer.setWorkyear(cursor.getString(12));
			offer.setEducation(cursor.getString(13));
			offer.setMailbox(cursor.getString(14));
			offer.setPublisherInfo(findUserInfo(offer.getOfferOwnerID()));
			offerList.add(offer);
			cursor.moveToNext();
		}
		cursor.close();
		closeDataBase();
		return offerList;
	}

	synchronized public ChatMessage findChatMessageById(String messageId) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		if (messageId == null) {
			return null;
		}
		ChatMessage chatMessage = null;

		String sql = "SELECT * FROM " + MESSAGETABLENAME
				+ " WHERE MESSAGE_ID = '" + messageId + "' AND USERID= "
				+ personInfo.getValue(LocalPersonInfo.USERID);
		Cursor cursor = dataBase.rawQuery(sql, null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			chatMessage = mappingChatMessage(cursor);
		}
		cursor.close();
		closeDataBase();
		return chatMessage;
	}

	synchronized public List<ChatMessage> findHistoryMessageByFriend(
			Integer friendId) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
		String userID = personInfo.getValue(LocalPersonInfo.USERID);
		String sql = "SELECT * FROM " + MESSAGETABLENAME
				+ " WHERE (SENDER_ID = " + friendId + " OR RECEIVER_ID = "
				+ friendId + ")" + " AND USERID= " + userID;
		// System.out.println("LocalDataBase->findHistoryMessageByFriend->sql"
		// + sql);
		Cursor cursor = dataBase.rawQuery(sql, null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			int senderId = cursor.getInt(1);
			if (!("N".equalsIgnoreCase(cursor.getString(8)) && senderId == friendId)) {
				chatMessages.add(mappingChatMessage(cursor));
			}
			cursor.moveToNext();
		}
		cursor.close();
		closeDataBase();
		return chatMessages;
	}

	synchronized public List<ChatMessage> findChatMessageBySender(
			Integer senderId) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
		String sql = "SELECT * FROM " + MESSAGETABLENAME
				+ " WHERE SENDER_ID = " + senderId + " AND USERID= "
				+ personInfo.getValue(LocalPersonInfo.USERID)
				+ " AND IS_READ != 'Y'";
		Cursor cursor = dataBase.rawQuery(sql, null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			ChatMessage message = mappingChatMessage(cursor);
			chatMessages.add(message);
			cursor.moveToNext();
		}
		readMessages(chatMessages);
		cursor.close();
		closeDataBase();
		return chatMessages;
	}

	synchronized public ChatMessage findLastChatMessageById(Integer friendId) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		ChatMessage chatMessage = null;

		String sql = "SELECT * FROM " + MESSAGETABLENAME + " WHERE USERID= "
				+ personInfo.getValue(LocalPersonInfo.USERID)
				+ " AND (SENDER_ID = " + friendId + " OR RECEIVER_ID = "
				+ friendId + ")";
		Cursor cursor = dataBase.rawQuery(sql, null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			ChatMessage resualt = mappingChatMessage(cursor);
			if (resualt != null) {
				chatMessage = resualt;
			}
			cursor.moveToNext();
		}
		cursor.close();
		closeDataBase();
		return chatMessage;
	}

	synchronized public ChatMessage findLastMessage(Integer friendId) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		ChatMessage message = new ChatMessage();
		String sql = "SELECT * FROM " + FRIENDTABLENAME + " WHERE USERID= "
				+ personInfo.getValue(LocalPersonInfo.USERID)
				+ " AND FRIEND_ID=" + friendId;
		Cursor cursor = dataBase.rawQuery(sql, null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			ChatFriend friend = new ChatFriend();
			friend.setFriendId(cursor.getInt(0));
			friend.setLastMessageId(cursor.getString(3));
			friend.setLastMessage(findLastChatMessageById(friend.getFriendId()));
			if (friend.getLastMessage() != null) {
				message = friend.getLastMessage();
			}
			cursor.moveToNext();
		}
		cursor.close();
		closeDataBase();
		return message;
	}

	synchronized private void readMessages(List<ChatMessage> chatMessages) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		if (!chatMessages.isEmpty()) {
			for (ChatMessage chatMessage : chatMessages) {
				String sql = "UPDATE " + MESSAGETABLENAME
						+ " SET IS_READ = 'Y' WHERE MESSAGE_ID = '"
						+ chatMessage.getMessageId() + "' AND USERID= "
						+ personInfo.getValue(LocalPersonInfo.USERID);
				dataBase.execSQL(sql);
			}
		}
	}

	synchronized private ChatMessage mappingChatMessage(Cursor cursor) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setMessageId(cursor.getString(0));
		chatMessage.setText(cursor.getString(5));
		chatMessage.setDate(cursor.getString(7));
		if ("Y".equals(cursor.getString(6))) {
			chatMessage.setComMsg(true);
			chatMessage.setUserId(cursor.getInt(1));
			chatMessage.setName(cursor.getString(2));
		} else {
			chatMessage.setComMsg(false);
			chatMessage.setUserId(cursor.getInt(3));
			chatMessage.setName(cursor.getString(4));
		}
		return chatMessage;
	}

	synchronized private void setFriendProfileVersion(ChatFriend friend) {
		if (friend.getPortraitVersion() == -100) {
			friend.setPortraitVersion(-1);
		}
	}

	synchronized private void insertNewFriend(Integer friendID) {
		if (!newMessageFriends.contains(friendID)) {
			newMessageFriends.add(friendID);
		}
	}

	synchronized public void removeNewFriend(Integer friendID) {
		if (newMessageFriends.contains(friendID)) {
			newMessageFriends.remove(friendID);
		}
	}

	synchronized public List<ChatFriend> findAllFriends() {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		List<ChatFriend> friends = new ArrayList<ChatFriend>();
		String sql = "SELECT * FROM " + FRIENDTABLENAME + " WHERE USERID= "
				+ personInfo.getValue(LocalPersonInfo.USERID);
		Cursor cursor = dataBase.rawQuery(sql, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ChatFriend friend = new ChatFriend();
			friend.setFriendId(cursor.getInt(0));
			friend.setName(cursor.getString(1));
			friend.setPortrait(cursor.getBlob(2));
			friend.setLastMessageId(cursor.getString(3));
			friend.setLastMessage(findChatMessageById(friend.getLastMessageId()));
			friends.add(friend);
			cursor.moveToNext();
		}
		cursor.close();
		closeDataBase();
		return friends;
	}

	synchronized public List<PersonalCV> findAllCVs() {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		List<PersonalCV> cvs = new ArrayList<PersonalCV>();
		String sql = "SELECT * FROM " + CVTABLENAME + " WHERE USERID = "
				+ personInfo.getValue(LocalPersonInfo.USERID);
		// System.out.println("Show me the sql: " + sql);
		Cursor cursor = dataBase.rawQuery(sql, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PersonalCV personalCV = new PersonalCV();
			personalCV.setUserID(cursor.getString(0));
			personalCV.setName(cursor.getString(1));
			personalCV.setPost(cursor.getString(2));
			personalCV.setAge(cursor.getString(3));
			personalCV.setEntreprise(cursor.getString(4));
			personalCV.setWorkyear(cursor.getString(5));
			personalCV.setEducation(cursor.getString(6));
			personalCV.setColleage(cursor.getString(7));
			personalCV.setWorkInfo(getWorkInfo(cursor));
			personalCV.setDate(cursor.getString(17));
			personalCV.setProfile(cursor.getBlob(18));
			personalCV.setCity(cursor.getString(19));
			personalCV.setPostID(cursor.getString(20));
			personalCV.setPostApplied(cursor.getString(21));
			cvs.add(personalCV);
			cursor.moveToNext();
		}
		cursor.close();
		closeDataBase();
		return cvs;
	}

	synchronized private PersonalWorkInfo getWorkInfo(Cursor cursor) {
		PersonalWorkInfo workInfo = new PersonalWorkInfo();
		workInfo.setFirstWork(cursor.getString(8));
		workInfo.setFirstWorkyear(cursor.getString(9));
		workInfo.setFirstWorkPost(cursor.getString(10));
		workInfo.setSecondWork(cursor.getString(11));
		workInfo.setSecondWorkyear(cursor.getString(12));
		workInfo.setSecondWorkPost(cursor.getString(13));
		workInfo.setThirdWork(cursor.getString(14));
		workInfo.setThirdWorkyear(cursor.getString(15));
		workInfo.setThirdWorkPost(cursor.getString(16));
		return workInfo;
	}

	synchronized public ChatFriend findFriendById(Integer friendId) {
		if (friendId == null) {
			return null;
		}
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		ChatFriend friend = null;

		String sql = "SELECT * FROM " + FRIENDTABLENAME + " WHERE FRIEND_ID ="
				+ friendId + " AND USERID= "
				+ personInfo.getValue(LocalPersonInfo.USERID);
		Cursor cursor = dataBase.rawQuery(sql, null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			friend = new ChatFriend();
			friend.setFriendId(cursor.getInt(0));
			friend.setName(cursor.getString(1));
			friend.setPortrait(cursor.getBlob(2));
			friend.setLastMessageId(cursor.getString(3));
			friend.setPortraitVersion(cursor.getInt(5));
			friend.setLastMessage(findChatMessageById(friend.getLastMessageId()));
		}
		cursor.close();
		return friend;
	}

	synchronized public List<Integer> getNewFriends() {
		List<Integer> list = new ArrayList<Integer>(this.newMessageFriends);
		return list;
	}

	synchronized public boolean getNewCommingCV() {
		return newCommingCV;
	}

	synchronized public void resetNewCommingCV() {
		newCommingCV = false;
	}

	public void closeDataBase() {
		dataBase.close();
	}

	public void visiterAllData(String tableName) {
		// This method is just for test!
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		String sql = "SELECT * FROM " + tableName;
		Cursor cursor = dataBase.rawQuery(sql, null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			cursor.moveToNext();
		}
		cursor.close();
		closeDataBase();
	}

	synchronized public boolean checkColume(String tableName, String columeName) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		boolean resualt = false;
		Cursor cursor = null;
		try {
			String sql = "SELECT * FROM " + tableName + " LIMIT 0";
			cursor = dataBase.rawQuery(sql, null);
			if (cursor.getColumnIndex(columeName) != -1) {
				return true;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			cursor.close();
			closeDataBase();
		}
		return resualt;
	}

	synchronized public void insertUserTableColume(int index) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		String sql = null;
		switch (index) {
		case 0:
			sql = "ALTER TABLE " + USERTABLENAME
					+ " ADD TELEPHONE VARCHAR(50) DEFAULT(NULL)";
			break;
		case 1:
			sql = "ALTER TABLE " + USERTABLENAME
					+ " ADD MAIL VARCHAR(100) DEFAULT(NULL)";
			break;
		case 2:
			sql = "ALTER TABLE " + USERTABLENAME
					+ " ADD NAME VARCHAR(100) DEFAULT(NULL)";
			break;
		case 3:
			sql = "ALTER TABLE " + USERTABLENAME
					+ " ADD CITY VARCHAR(10) DEFAULT(NULL)";
			break;
		case 4:
			sql = "ALTER TABLE " + USERTABLENAME
					+ " ADD ENTREPRISE VARCHAR(100) DEFAULT(NULL)";
			break;
		case 5:
			sql = "ALTER TABLE " + USERTABLENAME
					+ " ADD ISPHONEPUBLIC VARCHAR(100) DEFAULT(FALSE)";
			break;
		case 6:
			sql = "ALTER TABLE " + USERTABLENAME
					+ " ADD ISMAILPUBLIC VARCHAR(100) DEFAULT(FALSE)";
			break;
		case 7:
			sql = "ALTER TABLE " + USERTABLENAME
					+ " ADD PROFILE BLOB DEFAULT(NULL)";
			break;
		case 8:
			sql = "ALTER TABLE " + USERTABLENAME
					+ " ADD USERID VARCHAR(100) DEFAULT(NULL)";
			break;
		case 9:
			sql = "ALTER TABLE " + USERTABLENAME
					+ " ADD POST VARCHAR(100) DEFAULT(NULL)";
			break;
		default:
			break;
		}
		try {
			if (sql != null && dataBase.isOpen()) {
				dataBase.execSQL(sql);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	synchronized public void insertCVTableColume(int index) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		String sql = null;
		switch (index) {
		case 0:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD CVUSERID VARCHAR(100) DEFAULT(NULL)";
			break;
		case 1:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD NAME VARCHAR(100) DEFAULT(NULL)";
			break;
		case 2:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD POST VARCHAR(100) DEFAULT(NULL)";
			break;
		case 3:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD AGE VARCHAR(10) DEFAULT(NULL)";
			break;
		case 4:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD ENTREPRISE VARCHAR(100) DEFAULT(NULL)";
			break;
		case 5:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD WORKYEAR VARCHAR(10) DEFAULT(NULL)";
			break;
		case 6:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD EDUCATION VARCHAR(100) DEFAULT(NULL)";
			break;
		case 7:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD COLLEAGE VARCHAR(100) DEFAULT(NULL)";
			break;
		case 8:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD FIRSTWORKENTREPRISE VARCHAR(100) DEFAULT(NULL)";
			break;
		case 9:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD FIRSTWORKYEAR VARCHAR(10) DEFAULT(NULL)";
			break;
		case 10:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD FIRSTWORKPOST VARCHAR(100) DEFAULT(NULL)";
			break;
		case 11:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD SECONDWORKENTREPRISE VARCHAR(100) DEFAULT(NULL)";
			break;
		case 12:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD SECONDWORKYEAR VARCHAR(10) DEFAULT(NULL)";
			break;
		case 13:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD SECONDWORKPOST VARCHAR(100) DEFAULT(NULL)";
			break;
		case 14:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD THIRDWORKENTREPRISE VARCHAR(100) DEFAULT(NULL)";
			break;
		case 15:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD THIRDWORKYEAR VARCHAR(10) DEFAULT(NULL)";
			break;
		case 16:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD THIRDWORKPOST VARCHAR(100) DEFAULT(NULL)";
			break;
		case 17:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD DATE VARCHAR(100) DEFAULT(NULL)";
			break;
		case 18:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD PROFILE BLOB DEFAULT(NULL)";
			break;
		case 19:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD CITY VARCHAR(100) DEFAULT(NULL)";
			break;
		case 20:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD POSTID VARCHAR(100) DEFAULT(NULL)";
			break;
		case 21:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD POSTAPPLIED VARCHAR(100) DEFAULT(NULL)";
			break;
		case 22:
			sql = "ALTER TABLE " + CVTABLENAME
					+ " ADD USERID VARCHAR(100) DEFAULT(NULL)";
			break;
		default:
			break;
		}
		try {
			if (sql != null && dataBase.isOpen()) {
				dataBase.execSQL(sql);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	synchronized public void insertOfferTableColume(int index) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		String sql = null;
		switch (index) {
		case 0:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD OFFERID VARCHAR(100) DEFAULT(NULL)";
			break;
		case 1:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD DATE VARCHAR(20) DEFAULT(NULL)";
			break;
		case 2:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD USERID VARCHAR(100) DEFAULT(NULL)";
			break;
		case 3:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD FAVORITE VARCHAR(5) DEFAULT(FALSE)";
			break;
		case 4:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD ENTREPRISE VARCHAR(100) DEFAULT(NULL)";
			break;
		case 5:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD CITY VARCHAR(100) DEFAULT(NULL)";
			break;
		case 6:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD POST VARCHAR(100) DEFAULT(NULL)";
			break;
		case 7:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD DOMAIN VARCHAR(100) DEFAULT(NULL)";
			break;
		case 8:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD SALARY VARCHAR(100) DEFAULT(NULL)";
			break;
		case 9:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD DESCRIPTION VARCHAR(1000) DEFAULT(NULL)";
			break;
		case 10:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD APPLIED VARCHAR(5) DEFAULT(FALSE)";
			break;
		case 11:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD OFFEROWNERID VARCHAR(20) DEFAULT(NULL)";
			break;
		case 12:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD WORKYEAR VARCHAR(100) DEFAULT(NULL)";
			break;
		case 13:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD EDUCATION VARCHAR(100) DEFAULT(NULL)";
			break;
		case 14:
			sql = "ALTER TABLE " + OFFERTABLENAME
					+ " ADD MAIL VARCHAR(100) DEFAULT(NULL)";
			break;
		default:
			break;
		}
		try {
			if (sql != null && dataBase.isOpen()) {
				dataBase.execSQL(sql);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	synchronized public void insertFriendTableColume(int index) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		String sql = null;
		switch (index) {
		case 0:
			sql = "ALTER TABLE " + FRIENDTABLENAME
					+ " ADD FRIEND_ID NUMERIC(100) DEFAULT(NULL)";
			break;
		case 1:
			sql = "ALTER TABLE " + FRIENDTABLENAME
					+ " ADD NAME VARCHAR(100) DEFAULT(NULL)";
			break;
		case 2:
			sql = "ALTER TABLE " + FRIENDTABLENAME
					+ " ADD PORTRAIT BLOB DEFAULT(NULL)";
			break;
		case 3:
			sql = "ALTER TABLE " + FRIENDTABLENAME
					+ " ADD LAST_MESSAGE_ID NUMERIC(100) DEFAULT(NULL)";
			break;
		case 4:
			sql = "ALTER TABLE " + FRIENDTABLENAME
					+ " ADD USERID VARCHAR(100) DEFAULT(NULL)";
			break;
		case 5:
			sql = "ALTER TABLE " + FRIENDTABLENAME
					+ " ADD FRIEND_PROTRAIT_VERSION NUMERIC(100) DEFAULT(NULL)";
			break;
		default:
			break;
		}
		try {
			if (sql != null && dataBase.isOpen()) {
				dataBase.execSQL(sql);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	synchronized public void insertMessageTableColume(int index) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		String sql = null;
		switch (index) {
		case 0:
			sql = "ALTER TABLE " + MESSAGETABLENAME
					+ " ADD MESSAGE_ID VARCHAR(100) DEFAULT(NULL)";
			break;
		case 1:
			sql = "ALTER TABLE " + MESSAGETABLENAME
					+ " ADD SENDER_ID NUMERIC(100) DEFAULT(NULL)";
			break;
		case 2:
			sql = "ALTER TABLE " + MESSAGETABLENAME
					+ " ADD SENDER VARCHAR(100) DEFAULT(NULL)";
			break;
		case 3:
			sql = "ALTER TABLE " + MESSAGETABLENAME
					+ " ADD RECEIVER_ID NUMERIC(100) DEFAULT(NULL)";
			break;
		case 4:
			sql = "ALTER TABLE " + MESSAGETABLENAME
					+ " ADD RECEIVER VARCHAR(100) DEFAULT(NULL)";
			break;
		case 5:
			sql = "ALTER TABLE " + MESSAGETABLENAME
					+ " ADD CONTENT VARCHAR(512) DEFAULT(NULL)";
			break;
		case 6:
			sql = "ALTER TABLE " + MESSAGETABLENAME
					+ " ADD IS_COMING CHAR(1) DEFAULT(NULL)";
			break;
		case 7:
			sql = "ALTER TABLE " + MESSAGETABLENAME
					+ " ADD RECEIVE_DATE VARCHAR(100) DEFAULT(NULL)";
			break;
		case 8:
			sql = "ALTER TABLE " + MESSAGETABLENAME
					+ " ADD IS_READ CHAR(1) DEFAULT(NULL)";
			break;
		case 9:
			sql = "ALTER TABLE " + MESSAGETABLENAME
					+ " ADD USERID VARCHAR(100) DEFAULT(NULL)";
			break;
		default:
			break;
		}
		try {
			if (sql != null && dataBase.isOpen()) {
				dataBase.execSQL(sql);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	synchronized public void updateUserTable(ArrayList<String> colums) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		try {
			for (int count = 0; count < colums.size(); count++) {
				if (!checkColume(USERTABLENAME, colums.get(count))) {
					insertUserTableColume(count);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	synchronized public void updateCVTable(ArrayList<String> colums) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		try {
			for (int count = 0; count < colums.size(); count++) {
				if (!checkColume(CVTABLENAME, colums.get(count))) {
					insertCVTableColume(count);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	synchronized public void updateOfferTable(ArrayList<String> colums) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		try {
			for (int count = 0; count < colums.size(); count++) {
				if (!checkColume(OFFERTABLENAME, colums.get(count))) {
					insertOfferTableColume(count);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	synchronized public void updateFriendTable(ArrayList<String> colums) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		try {
			for (int count = 0; count < colums.size(); count++) {
				if (!checkColume(FRIENDTABLENAME, colums.get(count))) {
					insertFriendTableColume(count);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	synchronized public void updateMessageTable(ArrayList<String> colums) {
		if (dataBase == null || !dataBase.isOpen()) {
			connectDataBase();
		}
		try {
			for (int count = 0; count < colums.size(); count++) {
				if (!checkColume(MESSAGETABLENAME, colums.get(count))) {
					insertMessageTableColume(count);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private class openHelper extends SQLiteOpenHelper {
		public openHelper() {
			super(context, DATABASENAME, null, DATABASEVERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			dataBase = db;
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			deleteDataBase(OFFERTABLENAME);
			deleteDataBase(MESSAGETABLENAME);
			deleteDataBase(USERTABLENAME);
			onCreate(db);
		}
	}

}
