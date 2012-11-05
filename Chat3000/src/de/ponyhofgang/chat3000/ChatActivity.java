package de.ponyhofgang.chat3000;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	private SimpleAdapter simpleAdapter;
	private SimpleDateFormat sdf;
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
		sdf = new SimpleDateFormat("HH:mm:ss");

		simpleAdapter = new SimpleAdapter(this, chatStrings, R.layout.message_item,
				new String[] { "to", "from", "toHeader", "fromHeader" }, new int[] {
						R.id.messageTextRight, R.id.messageTextLeft, R.id.messageTextHeaderRight
						,R.id.messageTextHeaderLeft});
		listView.setAdapter(simpleAdapter);
		

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
		map.put("toHeader", "Du" + getTime());
		map.put("to", chatText.getText().toString() );
		map.put("from","");
		map.put("fromHeader","");
		chatStrings.add(map);
		simpleAdapter.notifyDataSetChanged();
		receiveMessage();
		chatText.setText("");
		
		listView.smoothScrollToPosition(listView.getBottom());

	}

	public void receiveMessage() {

		map = new HashMap<String, String>();
		Intent intent = getIntent();
		map.put("fromHeader", intent.getStringExtra("chatBuddy") + getTime());
		map.put("from", chatText.getText().toString() );
		map.put("to","");
		map.put("toHeader", "");
		chatStrings.add(map);
		simpleAdapter.notifyDataSetChanged();

	}

	@SuppressLint("SimpleDateFormat")
	public String getTime() {

		
		return "(" + sdf.format(System.currentTimeMillis()) + "):";

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
