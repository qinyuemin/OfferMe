package com.offerme.client.service;

import java.util.Collections;

import com.offerme.R;
import com.offerme.client.activity.SettingFeedbackActivity;
import com.offerme.client.http.HttpRequest;
import com.offerme.client.http.HttpServiceType;
import com.offerme.client.localdata.LocalDataBase;
import com.offerme.client.service.chat.ChatFriend;
import com.offerme.client.service.chat.ChatMessage;
import com.offerme.client.service.socket.SocketCommunication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SendMessageSrvc implements SocketSrvc {

	private static SendMessageSrvc sendMessageSrvc = new SendMessageSrvc();
	private Context context;
	private SocketCommunication socket;
	private Dialog processDialog;

	private ChatMessage msg;

	private SendMessageSrvc() {

	}

	public static SendMessageSrvc getInstance() {
		return sendMessageSrvc;
	}

	public void sendMessage(Context ctx, ChatMessage chatMessage) {
		context = ctx;
		socket = new SocketCommunication(this);
		msg = chatMessage;
		socket.execute();
	}

	private void saveMessageInLocalDatabase(ChatMessage chatMessage) {
		if (chatMessage.getUserId() != SettingFeedbackActivity.SYSTEMID) {
			LocalDataBase localData = LocalDataBase.getInstance(context);
			localData
					.insertChatMessages(Collections.singletonList(chatMessage));
			ChatFriend friend = localData.findFriendById(chatMessage
					.getUserId());
			friend.setLastMessageId(chatMessage.getMessageId());
			localData.insertOrUpdateFriends(Collections.singletonList(friend));
		}
	}

	private void setProcessDialog() {
		LayoutInflater factory = LayoutInflater.from(context);
		View dialogView = factory.inflate(R.layout.dialog_process, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		processDialog = builder.create();
		((AlertDialog) processDialog).setView(dialogView, 0, 0, 0, 0);
	}

	@Override
	public void onPreExecute() {
		setProcessDialog();
		// processDialog.show();
		Window window = processDialog.getWindow();
		window.setGravity(Gravity.CENTER);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.5f;
		lp.width = 200;
		lp.height = 300;
		window.setAttributes(lp);
	}
	
	@Override
	public Boolean doInBackground(String... params) {
		String messageId = HttpRequest.getHttpResponseStr(
				HttpServiceType.SEND_MESSAGE, msg);
		if (messageId != null && !"-1".equals(messageId)) {
			msg.setMessageId(messageId);
			return true;
		}
		return false;
	}

	@Override
	public void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPostExecute(Boolean success) {
		// processDialog.dismiss();
		/*Toast toast = null;
		// if (success) {
		toast = Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		
		 * } else { toast = Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT);
		 * toast.setGravity(Gravity.CENTER, 0, 0); toast.show(); }
		 */
		saveMessageInLocalDatabase(msg);
	}

	@Override
	public void onCancelled() {
		socket.cancel(true);
	}

}
