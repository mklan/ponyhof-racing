package de.ponyhofgang.chat3000;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService{
	
	public static final String BROADCAST_ACTION = "de.ponyhofgang.chat3000.broadcastkey";
	private static String buddyCurrent = null;

	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		
		String message = intent.getStringExtra("msgTxt");
		String buddy = intent.getStringExtra("fromName");
		String buddyId = intent.getStringExtra("fromId");
		
		if(!buddyCurrent.equals(buddy))
		generateNotification(context, message, buddy, buddyId);
		
		Intent i = new Intent(BROADCAST_ACTION);
		
	    i.putExtras(intent);
		sendBroadcast(i);
		
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		
		SharedPreferences settings = getSharedPreferences("prefs", MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
    	editor.putString("regId", registrationId);
    	editor.commit();
		
		ServerUtilities.register(context, registrationId);
	
		
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	private static void generateNotification(Context context, String message, String buddy, String buddyId) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
 
        String title = buddy;
 
        Intent notificationIntent = new Intent(context, ChatActivity.class);
        notificationIntent.putExtra("msgTxt", message);
        notificationIntent.putExtra("fromName", buddy);
        notificationIntent.putExtra("fromId", buddyId);
        
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
        
        
        
        
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
 
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
 
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);      
 
    }
	
	
	static void setCurrentBuddy(String buddyCur){
		
		buddyCurrent = buddyCur;
		
	}

}
