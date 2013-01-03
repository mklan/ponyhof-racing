package de.ponyhofgang.ponyhofgame.game.multiplayer;

import java.util.ArrayList;
import java.util.List;


import de.ponyhofgang.ponyhofgame.R;
import de.ponyhofgang.ponyhofgame.game.World;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SearchServices extends Activity
{
	private final String TAG=SearchServices.class.getName();
	private final Messenger messengerActivity=new Messenger(new ActivityHandler());
	private Messenger messengerService;
	private ServiceConnection serviceConnection=new MyServiceConnection();
	private boolean boundToService=false;
	
	private List<PackageInfo> availableServices;
	private int chosenServiceIndex;
	
	// views
	private TextView textViewSent, textViewReceived;
	private Button buttonMessageSend;
	
	private MainListener mainListener;
	private World world;
	public String packageName;
	
	private byte[] recievedByteData;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textViewSent=(TextView) findViewById(R.id.textViewSent);
		textViewReceived=(TextView) findViewById(R.id.textViewReceived);
		buttonMessageSend=(Button) findViewById(R.id.buttonSendMessage);
		
		mainListener=new MainListener();
		buttonMessageSend.setOnClickListener(mainListener);
		
		
		
	   
	   
		
		// <code>
		chosenServiceIndex=-1;
		availableServices=new ArrayList<PackageInfo>();
		List<PackageInfo> services=getPackageManager().getInstalledPackages(PackageManager.GET_SERVICES);
		for(PackageInfo pi : services)
		{
		
			if(pi.packageName.startsWith(MultiplayerInterface.CONNECTION_SERVICE_PREFIX))
			{
				availableServices.add(pi);
			}
		}
		
		new ClassChooserDialog().show(getFragmentManager(), TAG);
		// </code>
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		if(chosenServiceIndex != -1)
		{
			doBind();
		}
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		//doUnbind();
		unbindService(serviceConnection);
		
	}
	
	
	
//	@Override
//	protected void onResume()
//	{
//		super.onResume();
//		doBind();
//	}
	
	// <code>
	private void doBind()
	{
		Intent i=new Intent();
		PackageInfo chosenService=availableServices.get(chosenServiceIndex);
		
		packageName = chosenService.packageName;
		
		//i.setClassName(chosenService.packageName, chosenService.packageName + ".ConnectionService"); //Jan
	//	i.setClassName(chosenService.packageName, chosenService.packageName + ".services.MessengerService"); //Simon
		i.setClassName(chosenService.packageName, chosenService.packageName + "." + chosenService.applicationInfo.loadLabel(getPackageManager())); // Hans
		
		
		
		bindService(i, serviceConnection, Context.BIND_AUTO_CREATE);
	}
	// </code>
	
	private void doUnbind()   //TODO Bug funct nicht!
	{
		
		
		if(boundToService)
		{
			
			
			// tell service i am offline
			
			Message msg=Message.obtain(null, MultiplayerInterface.MESSAGE_5_CLOSE);
			
			msg.replyTo=messengerActivity;
			try
			{
				messengerService.send(msg);
			}
			catch(RemoteException e)
			{
				Log.e(TAG, e.getClass().getName() + ": " + e.getMessage());
			}
			boundToService=false;
			
			unbindService(serviceConnection);
		}
	}

	private class MyServiceConnection implements ServiceConnection
	{
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			messengerService=new Messenger(service);
			
			
			
			// make myself public to the service
			
			Message msg=Message.obtain(null, MultiplayerInterface.MESSAGE_1_REGISTER);
			msg.replyTo=messengerActivity;
			try
			{
				messengerService.send(msg);
			}
			catch(RemoteException e)
			{
				Log.e(TAG, e.getClass().getName() + ": " + e.getMessage());
			}
			boundToService=true;
		}

		public void onServiceDisconnected(ComponentName name)
		{
			messengerService=null;
			boundToService=false;
		}
	}
	
	private class ActivityHandler extends Handler
	{

		private boolean firstTime = true;
		

		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case MultiplayerInterface.MESSAGE_2_CONNECTION:
					
					// process incoming messages
					Log.d("message", ""+2);
					Bundle bundle=msg.getData();
					//Bundle bundle=(Bundle) msg.obj;// TODO Jan
			

					
			    Log.d ("test", "playerCount "+bundle.getInt(MultiplayerInterface.CONNECTION_PLAYERCOUNT));
			    Log.d ("test", "playerNames "+bundle.getString(MultiplayerInterface.CONNECTION_PLAYERNAMES));
			    Log.d ("test", "OwnID "+bundle.getInt(MultiplayerInterface.CONNECTION_OWN_ID));
			
				    
				  
				    

					if(firstTime){

					
					Intent i=new Intent();
					i.setClassName(packageName, bundle.getString(MultiplayerInterface.CONNECTION_ACTIVITY));
					startActivity(i);
					
					
					
					firstTime = false;
					
					}else{
						
						
						// TODO Auswahl der Autos, Host wählt Strecke, Welt wird erstellt
						
						
					}

					
				break;	
				case MultiplayerInterface.MESSAGE_4_GET:
					
					Log.d("message", ""+4);

					Bundle dataBundle =msg.getData();
					

					Log.d ("test", "adress: "+dataBundle.getInt(MultiplayerInterface.GET_ADDRESS));
					Log.d ("test", "type: "+dataBundle.getString(MultiplayerInterface.GET_TYPE));
					recievedByteData = dataBundle.getByteArray(MultiplayerInterface.GET_DATA);
					
					String str2 = new String(recievedByteData);
                    
//					final Parcel parcelData2  = Parcel.obtain();
//                  parcelData2.setDataPosition(0);
//					parcelData2.unmarshall(recievedByteData, 0, recievedByteData.length);
//					StringData recievedData = StringData.CREATOR.createFromParcel(parcelData2); 
					
					
					
					Log.d ("test", "positionY: "+ str2); 
					
					
					   //TODO hier werden die Daten für die Autos gesetzt
					
					
					
					
				break;	
				case MultiplayerInterface.MESSAGE_6_CLOSED:
					
					Log.d("message", ""+6);
					
					Bundle closingBundle =msg.getData();
					
					
					Toast.makeText(SearchServices.this, "Verbindung wurde von PlayId " +closingBundle.getInt(MultiplayerInterface.CLOSED_BY)+" beendet", Toast.LENGTH_SHORT).show();
					

				break;
				default:
					super.handleMessage(msg);
			}
		}
	}
	private class MainListener implements View.OnClickListener
	{

		public void onClick(View v)
		{
			if(v.getId() == R.id.buttonSendMessage)
			{
				if(!boundToService)
				{
					Toast.makeText(SearchServices.this, "not bound to a service", Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				Message msg=Message.obtain(null, MultiplayerInterface.MESSAGE_3_SEND);
				
				Bundle bundle=new Bundle();
				
				ParcelData data = new ParcelData((byte)1, (byte)1, (byte)1, 4.0f, 4.0f, 4.0f, 2, 2);
				//StringData stringData = new StringData("es klappt!");
				String test ="es klappt";
//				
//				final Parcel parcelData  = Parcel.obtain();
//				data.writeToParcel(parcelData, 0);
//                parcelData.setDataPosition(0);
//                final byte[] byteData = parcelData.marshall();
				
			
				
                bundle.putByteArray(MultiplayerInterface.SEND_DATA, test.getBytes());
				bundle.putInt(MultiplayerInterface.SEND_ADDRESS, -1);
				bundle.putString(MultiplayerInterface.SEND_TYPE, "data");
				
				
				msg.setData(bundle);
				
				
				try
				{
					messengerService.send(msg);
				}
				catch(RemoteException e)
				{
					Log.e(TAG, e.getClass().getName() + ": " + e.getMessage());
				}
				
				
			//	parcelData.recycle();
				


			}
		}
	}
	

	private class ClassChooserDialog extends DialogFragment implements DialogInterface.OnClickListener
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
			builder.setTitle("choose your service");
			CharSequence[] texts=new CharSequence[availableServices.size()];
			for(int i=0; i<availableServices.size(); i++)
			{
				PackageInfo service=availableServices.get(i);
				texts[i]=service.applicationInfo.loadLabel(getPackageManager()) + " (" + service.packageName + ")";
			}
			builder.setItems(texts, this);
			return builder.create();
		}

		public void onClick(DialogInterface dialog, int which)
		{
			chosenServiceIndex=which;
			doBind();
		}
		
	}
}
