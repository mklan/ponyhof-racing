package de.ponyhofgang.chat3000;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ChatActivity extends Activity implements OnClickListener {

	private EditText chatText;
	private ListView listView;
	private ArrayList<HashMap<String, String>> chatStrings = new ArrayList<HashMap<String, String>>();;
	private HashMap<String, String> map;
	private SimpleAdapter simpleAdapter;
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	final Calendar cal = Calendar.getInstance();
	
	private String chatBuddyId;
	private String msg;
	private String regId;
	private String chatBuddyName;
	

	private boolean active = false;
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent){
			Log.v("test", "broadcastreceiver empaengt");
			if(!active) {
				Log.v("test", "Activity soll starten");
				Intent i = new Intent(context, ChatActivity.class);
				i.putExtras(intent);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
			
			if (chatBuddyId.equals(intent.getStringExtra("fromId"))){
			
			msg = intent.getStringExtra("msgTxt");
			chatBuddyName = intent.getStringExtra("fromName");
			chatBuddyId = intent.getStringExtra("fromId");
			recieveMessage(msg);
		
			}
		}
	};
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		

		if (Build.VERSION.SDK_INT >= 11)
			hideActionBarIcon();

		chatBuddyName = getIntent().getStringExtra("fromName");
		this.setTitle("Chat mit " + chatBuddyName );
		
		chatBuddyId = getIntent().getStringExtra("fromId");
		
		SharedPreferences settings = getSharedPreferences("prefs", MODE_PRIVATE);
		regId = settings.getString("regId", "");
		

		chatText = (EditText) findViewById(R.id.chatText);
		listView = (ListView) findViewById(R.id.messageList);

		findViewById(R.id.sendButton).setOnClickListener(this);

		simpleAdapter = new SimpleAdapter(this, chatStrings,
				R.layout.message_item, new String[] { "to", "from", "toHeader",
						"fromHeader" }, new int[] { R.id.messageTextRight,
						R.id.messageTextLeft, R.id.messageTextHeaderRight,
						R.id.messageTextHeaderLeft });
		listView.setAdapter(simpleAdapter);

		registerReceiver(broadcastReceiver, new IntentFilter(GCMIntentService.BROADCAST_ACTION));
		
		if(getIntent().getExtras().containsKey("msgTxt")){
        	msg = getIntent().getExtras().getString("msgTxt");
        	chatBuddyName = getIntent().getExtras().getString("fromName");
			chatBuddyId = getIntent().getExtras().getString("fromId");
        	recieveMessage(msg);
        }
		
	}
	
	@Override
    public void onStart() {
       super.onStart();
       GCMIntentService.setCurrentBuddy(chatBuddyName);
       active = true;
    } 

    @Override
    public void onStop() {
       super.onStop();
       GCMIntentService.setCurrentBuddy("");
       active = false;
    }
    
    
    public void onPause(){
    	super.onPause();
    	GCMIntentService.setCurrentBuddy("");
    	
    }
    
    public void onResume(){
    	super.onPause();
    	GCMIntentService.setCurrentBuddy(chatBuddyName);
    	
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
		map.put("to", chatText.getText().toString());
		map.put("from", "");
		map.put("fromHeader", "");
		chatStrings.add(map);
		
		msg = chatText.getText().toString();
		
		new SendThread().execute();
		
		chatText.setText("");

		simpleAdapter.notifyDataSetChanged();
		listView.smoothScrollToPosition(listView.getCount());

	}

	public void recieveMessage(String inMsg) {

		map = new HashMap<String, String>();
		Intent intent = getIntent();
		map.put("fromHeader", chatBuddyName + getTime());
		map.put("from", inMsg);
		map.put("to", "");
		map.put("toHeader", "");
		chatStrings.add(map);

	    simpleAdapter.notifyDataSetChanged();
		listView.smoothScrollToPosition(listView.getCount());
	}

	@SuppressLint("SimpleDateFormat")
	public String getTime() {

		return "(" + sdf.format(System.currentTimeMillis()) + "):";

	}

	public boolean isEmpty(EditText chatText) {

		return (chatText.getText().toString().equals(""));

	}

	@TargetApi(11)
	public void hideActionBarIcon() {

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);

	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
				|| newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			simpleAdapter.notifyDataSetChanged();
			listView.invalidateViews();
			listView.setSelection(listView.getCount());

		}
	}
	
	
	public class SendThread extends AsyncTask<String, Void, Boolean>{

		
		
		public Boolean doInBackground(String... strings){
						
			
			ServerUtilities.send(regId, chatBuddyId, msg);
			return true;
		}
		
		protected void onPostExecute(Vector<String> Result){
			
		}
	}
	
	


}
