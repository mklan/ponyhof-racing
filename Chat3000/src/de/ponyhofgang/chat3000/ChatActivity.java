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
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
	
	private LocationListener gpsListener;
	private LocationListener networkListener;
	private LocationManager locationManager;
	
	private String chatBuddyId;
	private String msg;
	private String regId;
	private String chatBuddyName;
	
	private String gpsCoords;
	private String networkCoords;

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
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		 gpsListener = new LocationListener(
	        		);
	        networkListener = new LocationListener(
	        		);
		
		
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
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_chat_location:
	            sendLocation();
	            return true;
	        
	    }
	return false;
	}
	



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.activity_chat, menu);
	    return true;
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
    	locationManager.removeUpdates(gpsListener);
		locationManager.removeUpdates(networkListener);
		GCMIntentService.setCurrentBuddy("");
    	super.onPause();
    	
    	
    }
    
    public void onResume(){
    	super.onResume();
    	GCMIntentService.setCurrentBuddy(chatBuddyName);
    	locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 5000, 10, gpsListener);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 5000, 10, networkListener);
    	
    	
    }

	public void onClick(View v) {
		if (v.getId() == R.id.sendButton) {
			if (!isEmpty(chatText)) {

				sendMessage("txt:" + chatText.getText().toString());

			}

		}
	}

	public void sendMessage(String message) {

		
		map = new HashMap<String, String>();
		map.put("toHeader", "Du" + getTime());
		map.put("to", message.substring(4));
		map.put("from", "");
		map.put("fromHeader", "");
		chatStrings.add(map);
		
		msg = message;
		
		new SendThread().execute();
		
		chatText.setText("");

		simpleAdapter.notifyDataSetChanged();
		listView.smoothScrollToPosition(listView.getCount());

	}
	
	private void sendLocation() {
		
		if(!gpsListener.getLocationString().equals("")){
			sendMessage("geo:"+ gpsListener.getLocationString());	
		}else{
			sendMessage("geo:"+ networkListener.getLocationString());	
		}
		
		
	}
	
	
	private void sendWake() {
		sendMessage("vib:");
	}

	public void recieveMessage(String inMsg) {
		
		checkIncomingMessage(inMsg.substring(0, 4), inMsg.substring(4));

		
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
	
	public void checkIncomingMessage(String messageHeader, String message){
		
		if(messageHeader.equals("txt:")){
			map = new HashMap<String, String>();
			map.put("fromHeader", chatBuddyName + getTime());
			map.put("from", message);
			map.put("to", "");
			map.put("toHeader", "");
			chatStrings.add(map);

		    simpleAdapter.notifyDataSetChanged();
			listView.smoothScrollToPosition(listView.getCount());
			
		}
        if(messageHeader.equals("geo:")){
        	Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
        	Uri.parse("geo:0,0?q="+message+ "(" + chatBuddyName + ")"));
        	startActivity(intent);
		}
        if(messageHeader.equals("vib:")){
			
		}
		
		
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
	
	
private class LocationListener extends SimpleLocationListener {
		
		private String locationString = "";
					
		public LocationListener() {
			super();
			
			
		}

		@Override
		public void onLocationChanged(Location location) {
			
			locationString = location.getLatitude() + "," + location.getLongitude() ;
		}

		public String getLocationString() {
			return locationString;
		}

		
	}
	
	


}
