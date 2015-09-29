package com.offerme.client.activity;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.offerme.R;
import com.offerme.client.localdata.LocalDataBase;
import com.offerme.client.localdata.LocalPersonInfo;
import com.offerme.client.service.SendMessageSrvc;
import com.offerme.client.service.UtilSrvc;
import com.offerme.client.service.chat.ChatFriend;
import com.offerme.client.service.chat.ChatMessage;
import com.offerme.client.service.cv.PersonalCV;
import com.offerme.client.service.search.SearchResulatItem;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ChatActivity extends ActionBarActivity {

	public static String CHATFRIEND = "ChatFriend";
	public static String FRIENDCV = "FriendCV";
	public static String FROM_PUBLISHER = "IsFromPublisherOffer";
	public static String FROM_CHAT = "PublisherFromChat";
	public static String FROM_CV = "IsFromPersonalCV";
	private ChatFriend friend;
	private LocalPersonInfo personInfo = LocalPersonInfo.getInstance(this);
	private LocalDataBase localDataBase = LocalDataBase.getInstance(this);
	private SendMessageSrvc sendMessageSrvc = SendMessageSrvc.getInstance();
	private UtilSrvc utilSrvc = UtilSrvc.getInstance();
	private int LIMIT = 20;
	private int lastIndex;
	private Handler handler;
	private TextView friendName;
	private ImageView imageLeft;
	private ImageView imageRight;
	private EditText content;
	private Button sendButton;
	private ScrollView layoutSend;
	private boolean firstMessage = true;
	private ChatMsgViewAdapter mAdapter;
	private ListView mListView;
	private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
	private List<ChatMessage> historyMessages = new ArrayList<ChatMessage>();
	volatile private boolean isThreadReady = false;
	private Thread refreshChatMessage;
	private float downPosition = 0;
	private float upPosition = 0;
	private Bitmap imageBitmapLeft = null;
	private Bitmap imageBitmapRight = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilSrvc.addNewActivity(this);
		setContentView(R.layout.activity_chat);
		Intent intent = this.getIntent();
		friend = (ChatFriend) intent.getSerializableExtra(CHATFRIEND);
		this.handler = new MyHandler(this, friend);
		initView();
		initData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!isThreadReady) {
			isThreadReady = true;
			refreshChatMessage = new RefreshChatMessage(handler);
			refreshChatMessage.start();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		isThreadReady = false;
	}

	@Override
	protected void onDestroy() {
		this.handler.removeCallbacksAndMessages(null);
		super.onDestroy();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		backClick();
		return;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		this.setIntent(intent);
		friend = (ChatFriend) this.getIntent().getSerializableExtra(CHATFRIEND);
		firstMessage = true;
		initData();
	}

	public void backToFriendList(View view) {
		backClick();
	}

	public void backClick() {
		Intent intent = this.getIntent();
		isThreadReady = false;
		localDataBase.removeNewFriend(friend.getFriendId());
		if (intent.getBooleanExtra(FROM_PUBLISHER, false)) {
			SearchResulatItem item = (SearchResulatItem) intent
					.getSerializableExtra(FROM_CHAT);
			utilSrvc.gotoPublisherInfoPage(this, item);
		} else if (intent.getBooleanExtra(FROM_CV, false)) {
			PersonalCV cv = (PersonalCV) intent.getSerializableExtra(FRIENDCV);
			utilSrvc.gotoPersonalCVFromReceived(this, cv);
		} else {
			utilSrvc.gotoLoggedInFromChat(this, friend.getFriendId());
		}
	}

	private void initActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.activity_chat_actionbar);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1e90d2")));
	}

	private void initView() {
		initActionBar();
		mListView = (ListView) findViewById(R.id.chat_message_list);
		friendName = (TextView) findViewById(R.id.chat_bar_title);
		content = (EditText) findViewById(R.id.chat_sendmessage);
		layoutSend = (ScrollView) findViewById(R.id.rl_bottom);
		sendButton = (Button) findViewById(R.id.chat_send);
	}

	private void initData() {
		if (friend != null) {
			setFriendName();
			hideEntrerText();
			getHistoryMessage();
			initBitmapLeft();
			initBitmapRight();
		}
		if (mAdapter == null) {
			mAdapter = new ChatMsgViewAdapter(this, chatMessageList);
			mListView.setAdapter(mAdapter);
		}

		mAdapter.notifyDataSetChanged();
		mListView.setSelection(mListView.getCount() - 1);
		mListView.setOnTouchListener(new onTouchListener());
		sendButton.setOnClickListener(new sendMessageClick());
		content.setOnEditorActionListener(new keyboardSendClick());
	}

	private void initBitmapLeft() {
		if (friend != null) {
			imageBitmapLeft = null;
			if (friend.getPortrait() != null) {
				imageBitmapLeft = BitmapFactory.decodeByteArray(
						friend.getPortrait(), 0, friend.getPortrait().length);
				imageBitmapLeft = Bitmap.createScaledBitmap(imageBitmapLeft,
						50, 50, true);
			} else if (friend.getFriendId() == 0) {
				imageBitmapLeft = BitmapFactory.decodeResource(getResources(),
						R.drawable.logoicon);
				imageBitmapLeft = Bitmap.createScaledBitmap(imageBitmapLeft,
						50, 50, true);
			}
		}
	}

	private void initBitmapRight() {
		if (friend != null) {
			imageBitmapRight = null;
			if (personInfo.getValue(LocalPersonInfo.PROFILE) != null) {
				byte[] image = Base64.decode(
						personInfo.getValue(LocalPersonInfo.PROFILE),
						Base64.DEFAULT);
				imageBitmapRight = BitmapFactory.decodeByteArray(image, 0,
						image.length);
				imageBitmapRight = Bitmap.createScaledBitmap(imageBitmapRight,
						50, 50, true);

			}
		}
	}

	private void setFriendName() {
		friendName.setText(friend.getName());
	}

	private void hideEntrerText() {
		layoutSend.setVisibility(View.VISIBLE);
		if (friend.getFriendId() == 0) {
			layoutSend.setVisibility(View.GONE);
		}
	}

	private void getHistoryMessage() {
		chatMessageList.removeAll(chatMessageList);
		historyMessages = localDataBase.findHistoryMessageByFriend(friend
				.getFriendId());
		lastIndex = historyMessages.size();
		insertHistoryMessage();
	}

	private void insertHistoryMessage() {
		List<ChatMessage> tempList = new ArrayList<ChatMessage>(chatMessageList);
		int startIndex = 0;
		int endIndex = lastIndex;
		if ((lastIndex - LIMIT) >= 0) {
			lastIndex = startIndex = lastIndex - LIMIT;
		} else {
			lastIndex = 0;
		}
		chatMessageList.clear();
		chatMessageList.addAll(historyMessages.subList(startIndex, endIndex));
		chatMessageList.addAll(tempList);
	}

	public void updateChatList(List<ChatMessage> chatMessages) {
		if (chatMessages != null && !chatMessages.isEmpty()) {
			chatMessageList.addAll(chatMessages);
			mAdapter.notifyDataSetChanged();
			mListView.setSelection(mListView.getCount() - 1);
		}
	}

	private class sendMessageClick implements OnClickListener {
		@Override
		public void onClick(View view) {
			sendMessage();
		}
	}

	private class keyboardSendClick implements OnEditorActionListener {
		@Override
		public boolean onEditorAction(TextView view, int actionid,
				KeyEvent event) {
			switch (actionid) {
			case EditorInfo.IME_ACTION_SEND:
				sendMessage();
				break;
			}
			return true;
		}
	}

	private class onTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View view, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downPosition = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				upPosition = event.getY();
				break;
			default:
				break;
			}
			int itemPosition = mListView.getFirstVisiblePosition();
			if (upPosition != 0 && downPosition != 0
					&& (upPosition - downPosition > 50) && itemPosition == 0
					&& lastIndex != 0) {
				// Toast toast = Toast.makeText(context, "获取更多",
				// Toast.LENGTH_SHORT);
				insertHistoryMessage();
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(0);
				upPosition = downPosition = 0;
				// toast.show();
				return true;
			}
			return false;
		}

	}

	private void sendMessage() {
		String contString = content.getText().toString();
		if (contString.length() > 0 && friend != null) {
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setDate(getDate());
			chatMessage.setLocalUserId(Integer.parseInt(personInfo
					.getValue(LocalPersonInfo.USERID)));
			chatMessage.setUserId(friend.getFriendId());
			chatMessage.setName("");
			chatMessage.setComMsg(false);
			chatMessage.setText(contString);
			if (firstMessage) {
				localDataBase.insertOrUpdateFriends(Collections
						.singletonList(friend));
				chatMessage.setProfileVersion(localDataBase.findFriendById(
						friend.getFriendId()).getPortraitVersion());
				firstMessage = false;
			}
			chatMessageList.add(chatMessage);
			mAdapter.notifyDataSetChanged();
			content.setText("");
			mListView.setSelection(mListView.getCount() - 1);
			if (utilSrvc.isNetworkConnected(this) && contString.length() != 0) {
				sendMessageSrvc.sendMessage(this, chatMessage);
			} else {
				utilSrvc.showConnectDialog(this);
			}
		}
	}

	@SuppressLint("SimpleDateFormat")
	private String getDate() {
		Timestamp ts = new Timestamp(new Date().getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(ts);
	}

	private class ChatMsgViewAdapter extends BaseAdapter {

		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;

		private List<ChatMessage> data;
		private LayoutInflater mInflater;

		public TextView tvSendTime;
		public TextView tvContent;

		public ChatMsgViewAdapter(Context context, List<ChatMessage> data) {
			this.data = data;
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return data.size();
		}

		public Object getItem(int position) {
			return data.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public int getItemViewType(int position) {
			ChatMessage entity = data.get(position);
			if (entity.isComMsg()) {
				return IMVT_COM_MSG;
			} else {
				return IMVT_TO_MSG;
			}
		}

		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ChatMessage entity = data.get(position);
			boolean isComMsg = entity.isComMsg();
			try {
				if (isComMsg) {
					convertView = mInflater.inflate(
							R.layout.activity_chat_left, null);
					imageLeft = (ImageView) convertView
							.findViewById(R.id.iv_userheadleft);
					if (imageBitmapLeft != null) {
						imageLeft.setImageBitmap(imageBitmapLeft);
					}

				} else {
					convertView = mInflater.inflate(
							R.layout.activity_chat_right, null);
					imageRight = (ImageView) convertView
							.findViewById(R.id.iv_userheadright);
					if (imageBitmapRight != null) {
						imageRight.setImageBitmap(imageBitmapRight);
					}
				}
				tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_sendtime);
				tvContent = (TextView) convertView
						.findViewById(R.id.tv_chatcontent);
				if (entity.getDate() != null) {
					tvSendTime.setVisibility(View.VISIBLE);
					tvSendTime.setText(entity.getDate());
				} else {
					tvSendTime.setVisibility(View.GONE);
				}
			} catch (Exception e) {
				////e.printStackTrace();
			}

			tvContent.setText(entity.getText());

			return convertView;
		}
	}

	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler {
		private WeakReference<ChatActivity> mOuter;
		private ChatFriend friendHandler;

		public MyHandler(ChatActivity activity, ChatFriend friend) {
			mOuter = new WeakReference<ChatActivity>(activity);
			friendHandler = friend;
		}

		@Override
		public void handleMessage(Message msg) {
			ChatActivity outer = mOuter.get();
			if (outer != null) {
				switch (msg.what) {
				case 1:
					if (friendHandler != null) {
						List<ChatMessage> chatMessages = LocalDataBase
								.getInstance(outer).findChatMessageBySender(
										friendHandler.getFriendId());
						outer.updateChatList(chatMessages);
					}
					break;
				}
				super.handleMessage(msg);
			}
		}
	}

	private class RefreshChatMessage extends Thread {
		private Handler handler;

		public RefreshChatMessage(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void run() {

			while (isThreadReady) {
				try {
					sleep(2 * 1000);
					Message message = new Message();
					message.what = 1;// Magic number is not good!
					handler.sendMessage(message);
				} catch (InterruptedException e) {
					////e.printStackTrace();
				}
			}
		}
	}

}
