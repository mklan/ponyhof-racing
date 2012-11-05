package de.ponyhofgang.chat3000;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ChatActivity extends Activity implements OnClickListener {

	private EditText chatText;
	private ListView listView;
	private ArrayList<HashMap<String, String>> chatStrings;
	private HashMap<String, String> map;
	private SimpleAdapter mSchedule;
	final Calendar cal = Calendar.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		if (Build.VERSION.SDK_INT >= 11)
			hideActionBarIcon();

		Intent intent = getIntent();

		chatText = (EditText) findViewById(R.id.chatText);

		findViewById(R.id.sendButton).setOnClickListener(this);
		listView = (ListView) findViewById(R.id.messageList);

		this.setTitle("Chat mit " + intent.getStringExtra("chatBuddy"));

		chatStrings = new ArrayList<HashMap<String, String>>();

		mSchedule = new SimpleAdapter(this, chatStrings, R.layout.message_item,
				new String[] { "to", "from" }, new int[] {
						R.id.messageTextRight, R.id.messageTextLeft });
		listView.setAdapter(mSchedule);

	}

	public void onClick(View v) {
		if (v.getId() == R.id.sendButton) {
			if (!isEmpty(chatText)) {

				sendMessage();

			}

		}
	}

	public void sendMessage() {

		map = new HashMap<String, String>();
		map.put("to", "Du" + getTime() + chatText.getText().toString());
		map.put("from","");
		chatStrings.add(map);
		mSchedule.notifyDataSetChanged();
		receiveMessage();
		chatText.setText("");
		
		listView.smoothScrollToPosition(listView.getBottom());

	}

	public void receiveMessage() {

		map = new HashMap<String, String>();
		Intent intent = getIntent();
		map.put("from", intent.getStringExtra("chatBuddy") + getTime()
				+ chatText.getText().toString());
		map.put("to","");
		chatStrings.add(map);
		mSchedule.notifyDataSetChanged();

	}

	@SuppressLint("SimpleDateFormat")
	public String getTime() {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return "(" + sdf.format(cal.getTime()) + "):\n";

	}

	public boolean isEmpty(EditText chatText) {

		if (!chatText.getText().toString().equals("")) {

			return false;
		}

		return true;
	}

	@TargetApi(11)
	public void hideActionBarIcon() {

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);

	}

}
