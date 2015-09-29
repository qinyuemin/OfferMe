package com.offerme.client.activity;

import java.util.ArrayList;
import java.util.List;

import com.offerme.R;
import com.offerme.client.localdata.LocalDataBase;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.chat.ChatFriend;
import com.offerme.client.service.chat.ChatMessage;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ChatFriendListActivity extends Fragment {

	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	public static String FRIENDLIST = "FriendList";
	public static String CVLIST = "CVList";
	private LocalDataBase localDatabase = LocalDataBase
			.getInstance(getActivity());
	private Context context;
	private boolean hasNewCV = false;
	private List<ChatFriend> friendList = new ArrayList<ChatFriend>();
	private static List<ChatFriend> newMessageFriends = new ArrayList<ChatFriend>();
	private ChatFriendListAdapter listAdapter = new ChatFriendListAdapter();
	private ListView friendListView;
	private LinearLayout layoutToCV;
	private LinearLayout layoutToSystem;
	private ImageView redIconForNewSystem;
	private ImageView redIconForNewCV;
	private TextView systemMessage;
	private Button deleteButton;
	volatile private boolean isThreadReady = false;
	private Thread waitMessage;
	private Handler mHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_chat_friend_list, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = this.getActivity();
		mHandler = new myHandler();
		initWidget();
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this.getActivity());
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this.getActivity());
		if (!isThreadReady) {
			isThreadReady = true;
			waitMessage = new waitNewMessage(mHandler);
			waitMessage.start();
		}
		loadData();
	}

	@Override
	public void onStop() {
		super.onStop();
		isThreadReady = false;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

	}

	public void setNewFriend(List<ChatFriend> friends) {
		// newMessageFriends = friends;
		if (friends != null) {
			for (int count = 0; count < friends.size(); count++) {
				addNewFriend(friends.get(count));
			}
		}
		listAdapter.notifyDataSetChanged();
	}

	public void removeNewFriend(Integer friendID) {
		if (newMessageFriends != null) {
			for (int count = newMessageFriends.size() - 1; count >= 0; count--) {
				if (friendID.equals(newMessageFriends.get(count).getFriendId())) {
					newMessageFriends.remove(count);
					break;
				}
			}
		} else {
			newMessageFriends = new ArrayList<ChatFriend>();
		}
		listAdapter.notifyDataSetChanged();
	}

	public void addNewFriend(ChatFriend friend) {
		boolean canBeAdded = true;
		if (newMessageFriends != null) {
			for (int count = 0; count < newMessageFriends.size(); count++) {
				if (friend.getFriendId() == newMessageFriends.get(count)
						.getFriendId()) {
					canBeAdded = false;
				}
			}
			if (canBeAdded) {
				newMessageFriends.add(friend);
			}
		} else {
			newMessageFriends = new ArrayList<ChatFriend>();
			newMessageFriends.add(friend);
		}
	}

	private void initWidget() {
		friendListView = (ListView) getActivity().findViewById(
				R.id.chat_friend_list);
		layoutToCV = (LinearLayout) getActivity().findViewById(
				R.id.chat_list_cv_goto_cvlist);
		layoutToSystem = (LinearLayout) getActivity().findViewById(
				R.id.chat_list_system_message_layout);

		redIconForNewSystem = (ImageView) getActivity().findViewById(
				R.id.chat_new_system_message);
		redIconForNewCV = (ImageView) getActivity().findViewById(
				R.id.chat_new_cv);
		systemMessage = (TextView) getActivity().findViewById(
				R.id.chat_list_system_message);
		friendListView.setAdapter(listAdapter);
		layoutToCV.setOnClickListener(new gotoReceivedCvPage());
		layoutToSystem.setOnClickListener(new gotoChatSystemPage());
	}

	private void loadData() {
		List<ChatFriend> friends = localDatabase.findAllFriends();
		setDefaultSystemMessage();
		if (friendListView.getAdapter() != null) {
			friendList = new ArrayList<ChatFriend>();
			friendListView.setAdapter(listAdapter);
		}
		if (friends != null && !friends.isEmpty()) {
			for (int count = 0; count < friends.size(); count++) {
				ChatFriend friend = friends.get(count);
				if (friend.getFriendId() != utilSrvc.getSystemID()) {
					friendList.add(friend);
				}
			}
		}
	}

	private void setDefaultSystemMessage() {
		ChatMessage message = localDatabase.findLastMessage(utilSrvc
				.getSystemID());
		if (message.getText() != null && message.getText().length() != 0) {
			systemMessage.setText(message.getText());
		} else {
			systemMessage.setHint(this.getResources().getText(
					R.string.chat_list_system_noMessage));
		}
	}

	private void doAnimation(Context context, View view, int animId) {
		Animation animation = AnimationUtils.loadAnimation(context, animId);
		view.startAnimation(animation);
	}

	private class gotoChatSystemPage implements OnClickListener {
		@Override
		public void onClick(View view) {
			ChatFriend systemFriend = new ChatFriend();
			systemFriend.setFriendId(utilSrvc.getSystemID());
			systemFriend.setName(getResources().getString(
					R.string.action_system_message));
			localDatabase.removeNewFriend(systemFriend.getFriendId());
			redIconForNewSystem.setVisibility(View.GONE);
			utilSrvc.gotoChatPage(context, systemFriend);
		}
	}

	private class gotoReceivedCvPage implements OnClickListener {
		@Override
		public void onClick(View view) {
			utilSrvc.gotoChatCVList(context);
			redIconForNewCV.setVisibility(View.GONE);
			localDatabase.resetNewCommingCV();
		}
	}

	private class ChatFriendListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return friendList.size();
		}

		@Override
		public ChatFriend getItem(int position) {
			return friendList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ChatFriend friend = this.getItem(position);
			if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(
						R.layout.activity_chat_friend_item, null);
			}
			ImageView redpoint = (ImageView) convertView
					.findViewById(R.id.chat_friend_new_message);
			TextView name = (TextView) convertView
					.findViewById(R.id.chat_friend_name);
			name.setText(friend.getName());

			TextView lastMessage = (TextView) convertView
					.findViewById(R.id.chat_list_last_message);
			TextView lastTime = (TextView) convertView
					.findViewById(R.id.chat_list_last_message_time);
			ChatMessage lastMessageText = localDatabase.findLastMessage(friend
					.getFriendId());
			Button delete = (Button) convertView
					.findViewById(R.id.chat_list_delete);
			redpoint.setVisibility(View.GONE);

			if (newMessageFriends.size() != 0) {
				for (int count = 0; count < newMessageFriends.size(); count++) {
					if (newMessageFriends.get(count) != null) {
						if (newMessageFriends.get(count).getName()
								.equalsIgnoreCase(friend.getName())) {
							redpoint.setVisibility(View.VISIBLE);
						}
					}
				}
			}

			if (friend.getPortrait() != null) {
				ImageView portrait = (ImageView) convertView
						.findViewById(R.id.chat_friend_profile);
				Bitmap imageBitmap = BitmapFactory.decodeByteArray(
						friend.getPortrait(), 0, friend.getPortrait().length);
				imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 50, 50,
						true);
				portrait.setImageBitmap(imageBitmap);
			}

			if (lastMessageText != null && lastMessageText.getDate() != null) {
				lastMessage.setText(lastMessageText.getText());
				lastTime.setText(lastMessageText.getDate().substring(5));
			}

			convertView.setOnTouchListener(new ItemTouchListener(delete,
					position, redpoint));
			delete.setOnClickListener(new DeleteListener(friend));

			return convertView;
		}
	}

	private class ItemTouchListener implements OnTouchListener {

		private int position;
		private ImageView redpoint;
		private float downPosition = 0;
		private float upPosition = 0;
		private float movePosition = 0;
		private Button deleteFriend;

		ItemTouchListener(Button button, int position, ImageView image) {
			this.position = position;
			this.redpoint = image;
			deleteFriend = button;
		}

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downPosition = event.getX();
				if (deleteButton != null
						&& deleteButton.getVisibility() == View.VISIBLE) {
					deleteButton.setVisibility(View.GONE);
					doAnimation(context, deleteButton, R.anim.deleteout);
				}
				deleteButton = deleteFriend;
				break;
			case MotionEvent.ACTION_UP:
				upPosition = event.getX();
				break;
			case MotionEvent.ACTION_MOVE:
				movePosition = event.getX();
				if (downPosition - movePosition > 10) {
					deleteButton.setVisibility(View.VISIBLE);
					doAnimation(context, deleteButton, R.anim.deletein);
					return true;
				} else if (movePosition - downPosition > 10) {
					return true;
				} else {
					return true;
				}
			default:
				break;
			}
			if (upPosition == 0) {
				return true;
			} else if (downPosition == upPosition) {
				onItemClick(position);
			}
			return true;
		}

		private void onItemClick(int position) {
			System.out.println("Chat->onItemClick->Show me the friend name: "
					+ friendList.get(position).getName());
			int positionInNewFriend = -1;
			for (int count = 0; count < newMessageFriends.size(); count++) {
				if (newMessageFriends.get(count).getName()
						.equalsIgnoreCase(friendList.get(position).getName())) {
					positionInNewFriend = count;
				}
			}
			if (positionInNewFriend != -1) {
				newMessageFriends.remove(positionInNewFriend);
			}
			isThreadReady = false;
			redpoint.setVisibility(View.GONE);
			localDatabase.removeNewFriend(friendList.get(position)
					.getFriendId());
			utilSrvc.gotoChatPage(context, friendList.get(position));
		}

	}

	private class DeleteListener implements OnClickListener {

		private ChatFriend friend;

		public DeleteListener(ChatFriend friend) {
			this.friend = friend;
		}

		@Override
		public void onClick(View view) {
			TextView deleteButton = (TextView) view
					.findViewById(R.id.chat_list_delete);
			deleteButton.setVisibility(View.GONE);
			localDatabase.deleteFriend(friend);
			friendList.remove(friend);
			listAdapter.notifyDataSetChanged();
		}

	}

	@SuppressLint("HandlerLeak")
	private class myHandler extends Handler {

		private void refreshFriendList() {
			if (listAdapter != null) {
				if (friendList.size() == 0) {
					friendList = new ArrayList<ChatFriend>(newMessageFriends);
				}
				listAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				refreshFriendList();
				break;
			case 200:
				if (hasNewCV) {
					redIconForNewCV.setVisibility(View.VISIBLE);
				}
				break;
			case 300:
				redIconForNewSystem.setVisibility(View.VISIBLE);
				break;
			}
			super.handleMessage(msg);
		}
	}

	private class waitNewMessage extends Thread {
		private Handler threadHandler;
		private List<Integer> threadFriendList = null;
		private boolean threadCommingCV = false;

		public waitNewMessage(Handler handler) {
			this.threadHandler = handler;
		}

		public void run() {

			while (isThreadReady) {
				try {
					sleep(1 * 1000);
					threadFriendList = localDatabase.getNewFriends();
					threadCommingCV = localDatabase.getNewCommingCV();
					if (threadFriendList != null) {
						Message message = new Message();
						for (int count = 0; count < threadFriendList.size(); count++) {
							ChatFriend friend = localDatabase
									.findFriendById(threadFriendList.get(count));
							if (friend.getFriendId() != utilSrvc.getSystemID()) {
								message.what = 100;
							} else {
								message.what = 300;
							}
							addNewFriend(friend);
						}
						threadHandler.sendMessage(message);
					}
					if (threadCommingCV) {
						Message message = new Message();
						message.what = 200;
						hasNewCV = threadCommingCV;
						threadHandler.sendMessage(message);
					}
				} catch (InterruptedException e) {
					//e.printStackTrace();
				}
			}
		}
	}

}
