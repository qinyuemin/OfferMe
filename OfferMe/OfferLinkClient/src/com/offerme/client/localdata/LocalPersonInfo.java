package com.offerme.client.localdata;

import java.io.File;
import java.util.ArrayList;

import com.offerme.client.service.inscription.InscriptionInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public class LocalPersonInfo {
	private static LocalPersonInfo localPersonInfo = null;
	private SharedPreferences globalInfo = null;
	private SharedPreferences.Editor editor = null;
	private static int START_POSITION = 8;
	public static String FILENAME = "InscriptionInfo";
	public static String NAME = "name";
	public static String CITY = "city";
	public static String ENTREPRISE = "entreprise";
	public static String EMAIL = "email";
	public static String TELEPHONE = "telephoneNumber";
	public static String PASSWORD = "password";
	public static String PROFILE = "profile";
	public static String AGE = "age";
	public static String POST = "post";
	public static String EDUCATION = "education";
	public static String COLLEAGE = "colleage";
	public static String WORKYEAR = "workyear";
	public static String FIRSTWORK = "firstwork";
	public static String FIRSTWORKPOST = "firstworkpost";
	public static String FIRSTWORKYEAR = "firstworkyear";
	public static String SECONDWORK = "secondwork";
	public static String SECONDWORKPOST = "secondworkpost";
	public static String SECONDWORKYEAR = "secondworkyear";
	public static String THIRDWORK = "thirdwork";
	public static String THIRDWORKPOST = "thirdworkpost";
	public static String THIRDWORKYEAR = "thirdworkyear";
	public static String USERID = "UserID";
	public static String IS_EMAIL_PUBLISHED = "IS_EMAIL_PUBLISED";
	public static String IS_PHONE_PUBLISHED = "IS_PHONE_PUBLISHED";
	public static String ACCEPT_MESSAGE = "accept_message";
	public static String IS_FIRST_FEEDBACK = "IS_FIRST_FEEDBACK";
	public static String NEED_ALL_CLEAN = "NEED_ALL_CLEAN";

	private LocalPersonInfo(Context context) {
		try {
			globalInfo = context.getSharedPreferences(FILENAME,
					Activity.MODE_PRIVATE);
			editor = globalInfo.edit();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public static LocalPersonInfo getInstance(Context context) {
		if (localPersonInfo == null) {
			localPersonInfo = new LocalPersonInfo(context);
		}
		return localPersonInfo;
	}

	public boolean setInscriptionInfo(InscriptionInfo info) {
		if (info.getPhotoByte() != null) {
			editor.putString(PROFILE,
					Base64.encodeToString(info.getPhotoByte(), Base64.DEFAULT));
		}
		editor.putString(NAME, info.getName());
		editor.putString(CITY, info.getCity());
		editor.putString(ENTREPRISE, info.getEntreprise());
		editor.putString(EMAIL, info.getEmail());
		editor.putString(TELEPHONE, info.getTelephoneNumber());
		editor.putString(PASSWORD, info.getPassword());
		editor.putString(WORKYEAR, info.getWorkyear());
		editor.putString(AGE, info.getAge());
		editor.putString(POST, info.getPost());
		editor.putBoolean(IS_EMAIL_PUBLISHED, info.isEmailPublished());
		editor.putBoolean(IS_PHONE_PUBLISHED, info.isPhonePublished());
		return editor.commit();
	}

	public boolean setProfile(String profile) {
		editor.putString(PROFILE, profile);
		return editor.commit();
	}

	public boolean setUserID(String ID) {
		editor.putString(USERID, ID);
		return editor.commit();
	}

	public boolean setName(String name) {
		editor.putString(NAME, name);
		return editor.commit();
	}

	public boolean setCity(String city) {
		editor.putString(CITY, city);
		return editor.commit();
	}

	public boolean setEnterprise(String enterprise) {
		editor.putString(ENTREPRISE, enterprise);
		return editor.commit();
	}

	public boolean setEmail(String email) {
		editor.putString(EMAIL, email);
		return editor.commit();
	}

	public boolean setPhoneNumber(String phoneNumber) {
		editor.putString(TELEPHONE, phoneNumber);
		return editor.commit();
	}

	public boolean setAge(String age) {
		editor.putString(AGE, age);
		return editor.commit();
	}

	public boolean setEducation(String education) {
		editor.putString(EDUCATION, education);
		return editor.commit();
	}

	public boolean setColleage(String colleage) {
		editor.putString(COLLEAGE, colleage);
		return editor.commit();
	}

	public boolean setWorkyear(String workyear) {
		editor.putString(WORKYEAR, workyear);
		return editor.commit();
	}

	public boolean setPost(String post) {
		editor.putString(POST, post);
		return editor.commit();
	}

	public boolean setFirstWork(String work, String year, String post) {
		editor.putString(FIRSTWORK, work);
		editor.putString(FIRSTWORKYEAR, year);
		editor.putString(FIRSTWORKPOST, post);
		return editor.commit();
	}

	public boolean setSecondWork(String work, String year, String post) {
		editor.putString(SECONDWORK, work);
		editor.putString(SECONDWORKYEAR, year);
		editor.putString(SECONDWORKPOST, post);
		return editor.commit();
	}

	public boolean setThirdWork(String work, String year, String post) {
		editor.putString(THIRDWORK, work);
		editor.putString(THIRDWORKYEAR, year);
		editor.putString(THIRDWORKPOST, post);
		return editor.commit();
	}

	public boolean setWorks(ArrayList<String> list) {
		clearWorks();
		for (int count = 0; count < list.size();) {
			if (count < 3) {
				setFirstWork(list.get(count), list.get(count + 1),
						list.get(count + 2));
			} else if (count < 6) {
				setSecondWork(list.get(count), list.get(count + 1),
						list.get(count + 2));
			} else if (count < 9) {
				setThirdWork(list.get(count), list.get(count + 1),
						list.get(count + 2));
			}
			count = count + 3;
		}
		return editor.commit();
	}

	public boolean setFirstFeedBack(boolean isFirstFeedBack) {
		editor.putBoolean(IS_FIRST_FEEDBACK, isFirstFeedBack);
		return editor.commit();
	}

	public boolean clearWorks() {
		editor.remove(FIRSTWORK);
		editor.remove(FIRSTWORKYEAR);
		editor.remove(FIRSTWORKPOST);
		editor.remove(SECONDWORK);
		editor.remove(SECONDWORKYEAR);
		editor.remove(SECONDWORKPOST);
		editor.remove(THIRDWORK);
		editor.remove(THIRDWORKYEAR);
		editor.remove(THIRDWORKPOST);
		return editor.commit();
	}

	public boolean setAllClean(boolean needClean) {
		editor.putBoolean(NEED_ALL_CLEAN, needClean);
		return editor.commit();
	}

	public boolean setEmailPublished(boolean isEmailPublished) {
		editor.putBoolean(IS_EMAIL_PUBLISHED, isEmailPublished);
		return editor.commit();
	}

	public boolean setPhonePublished(boolean isPhonePublished) {
		editor.putBoolean(IS_PHONE_PUBLISHED, isPhonePublished);
		return editor.commit();
	}

	public boolean setAcceptMessage(String acceptMessage) {
		editor.putString(ACCEPT_MESSAGE, acceptMessage);
		return editor.commit();
	}

	public String getValue(String key) {
		try {
			if (globalInfo == null) {
				return null;
			}
			return globalInfo.getString(key, null);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean getBoolean(String key) {
		if (globalInfo == null) {
			return false;
		}
		return globalInfo.getBoolean(key, false);
	}

	public ArrayList<String> getWorkList() {
		ArrayList<String> list = new ArrayList<String>();
		if (globalInfo.contains(FIRSTWORK)
				&& globalInfo.contains(FIRSTWORKYEAR)) {
			list.add(globalInfo.getString(FIRSTWORK, null));
			list.add(globalInfo.getString(FIRSTWORKYEAR, null));
			list.add(globalInfo.getString(FIRSTWORKPOST, null));
		}
		if (globalInfo.contains(SECONDWORK)
				&& globalInfo.contains(SECONDWORKYEAR)) {
			list.add(globalInfo.getString(SECONDWORK, null));
			list.add(globalInfo.getString(SECONDWORKYEAR, null));
			list.add(globalInfo.getString(SECONDWORKPOST, null));
		}
		if (globalInfo.contains(THIRDWORK)
				&& globalInfo.contains(THIRDWORKYEAR)) {
			list.add(globalInfo.getString(THIRDWORK, null));
			list.add(globalInfo.getString(THIRDWORKYEAR, null));
			list.add(globalInfo.getString(THIRDWORKPOST, null));
		}
		return list;
	}

	public boolean removeValue(String key) {
		if (globalInfo == null && !globalInfo.contains(key)) {
			return false;
		}
		editor.remove(key);
		return editor.commit();
	}

	public boolean hasValue(String key) {
		return globalInfo.contains(key);
	}

	@SuppressLint("SdCardPath")
	public boolean clearMemory() {
		boolean returnValue = true;
		editor.clear();
		editor.commit();
		String sourceFile = "/data/data/"
				+ this.getClass().getPackage().toString()
						.substring(START_POSITION) + "/shared_prefs/"
				+ FILENAME + ".xml";
		File file = new File(sourceFile);
		if (file.exists()) {
			returnValue = file.delete();
		}
		showAllValue();
		return returnValue;
	}

	public void showAllValue() {
		//System.out.println("LocalPersonaInfo->Show me the name " + NAME
		//		+ " and it's value: " + this.getValue(NAME));
		//System.out.println("LocalPersonaInfo->Show me the name " + CITY
		//		+ " and it's value: " + this.getValue(CITY));
		//System.out.println("LocalPersonaInfo->Show me the name " + ENTREPRISE
		//		+ " and it's value: " + this.getValue(ENTREPRISE));
		//System.out.println("LocalPersonaInfo->Show me the name " + EMAIL
		//		+ " and it's value: " + this.getValue(EMAIL));
		//System.out.println("LocalPersonaInfo->Show me the name " + TELEPHONE
		//		+ " and it's value: " + this.getValue(TELEPHONE));
		//System.out.println("LocalPersonaInfo->Show me the name " + PASSWORD
		//		+ " and it's value: " + this.getValue(PASSWORD));
		//System.out.println("LocalPersonaInfo->Show me the name " + USERID
		//		+ " and it's value: " + this.getValue(USERID));
		//System.out.println("LocalPersonaInfo->Show me the name " + AGE
		//		+ " and it's value: " + this.getValue(AGE));
		//System.out.println("LocalPersonaInfo->Show me the name " + WORKYEAR
		//		+ " and it's value: " + this.getValue(WORKYEAR));
		//System.out.println("LocalPersonaInfo->Show me the name " + POST
		//		+ " and it's value: " + this.getValue(POST));
		//System.out.println("LoclaPersonaInfo->Show me the portrait " + PROFILE
		//		+ " and it's value: " + this.getValue(PROFILE));
	}
}
