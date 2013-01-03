package de.ponyhofgang.ponyhofgame.game;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.ponyhofgang.ponyhofgame.R;
import de.ponyhofgang.ponyhofgame.framework.Screen;
import de.ponyhofgang.ponyhofgame.framework.impl.GLGame;
import de.ponyhofgang.ponyhofgame.game.multiplayer.MultiplayerInterface;
import de.ponyhofgang.ponyhofgame.game.multiplayer.ParcelData;
import de.ponyhofgang.ponyhofgame.game.multiplayer.SearchServices;

import de.ponyhofgang.ponyhofgame.game.screens.AboutScreen;
import de.ponyhofgang.ponyhofgame.game.screens.SelectACarScreen;
import de.ponyhofgang.ponyhofgame.game.screens.SelectAMapScreen;
import de.ponyhofgang.ponyhofgame.game.screens.GameScreen;
import de.ponyhofgang.ponyhofgame.game.screens.LoadingScreen;
import de.ponyhofgang.ponyhofgame.game.screens.MainMenuScreen;
import de.ponyhofgang.ponyhofgame.game.screens.SettingsScreen;


public class GameActivity extends GLGame {
	boolean firstTimeCreate = true;
	LoadingScreen loadingScreen;
	MainMenuScreen mainMenuScreen;
	Intent intent;
	
	
	
	private final String TAG=SearchServices.class.getName();
	private final Messenger messengerActivity=new Messenger(new ActivityHandler());
	private Messenger messengerService;
	private ServiceConnection serviceConnection=new MyServiceConnection();
	private boolean boundToService=false;
	
	private List<PackageInfo> availableServices;
	private int chosenServiceIndex = -1;
	
	
	public int ownId = 0;
	public int playerCount = 1;
	public int map = 0;
	public boolean multiplayer = false;
	// views
	
	
	

	public String packageName;
	
	private byte[] recievedByteData;
	
	

	public Screen getStartScreen() {
		
		return MainMenuScreen.getInstance(this, super.getHeight(), super.getWidth());
		
	}
	
	

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate) {

			Assets.loadLoadingScreen(this);
			Settings.load(getFileIO());
			Assets.load(this);
			firstTimeCreate = false;
			
			intent = new Intent(this, SearchServices.class);
		} else {
			Assets.reload();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		//TODO
		// if (Settings.musicEnabled)
		 //Assets.music.pause();
	}
	
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	       
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("MainMenuScreen")) System.exit(1); 
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("SettingsScreen")) SettingsScreen.getInstance().pressedBackKey = true;
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("AboutScreen")) AboutScreen.getInstance().pressedBackKey = true;
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("ChooseCarScreen")) SelectACarScreen.getInstance().pressedBackKey = true;
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("ChooseMapScreen")) SelectAMapScreen.getInstance().pressedBackKey = true;
		    	
	    	 
	    	 
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_PAUSED) {
	    		 GameScreen.getInstance().state = GameScreen.GAME_RUNNING;
	    		 if(multiplayer) sendPause(0); //wenn im Multiplayer, dann den andeen bescheid sagen
	    	 }
	    	 else if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen")){
	    		 GameScreen.getInstance().state = GameScreen.GAME_PAUSED;
	    		 if(multiplayer) sendPause(1); //wenn im Multiplayer, dann den andeen bescheid sagen
	    	 }
	    	 
	    	
	    	 
	    }
	    
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
		       
	    	
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING)GameScreen.getInstance().DPAD_LEFT = false;
	   }
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
		       
	    	
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_RIGHT = false;
	   }
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
		       
	    	
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_UP = false;
	   }
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
		       
	    	
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_DOWN = false;
	   }
	    
	    
	    
	    
	    return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BUTTON_START) {
	       
	    	if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_PAUSED) GameScreen.getInstance().state = GameScreen.GAME_RUNNING;
	    	 else if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen")) GameScreen.getInstance().state = GameScreen.GAME_PAUSED;
	   }
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
		       
	    	
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_LEFT = true;
	   }
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
		       
	    	
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_RIGHT = true;
	   }
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
		       
	    	
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_UP = true;
	   }
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
		       
	    	
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_DOWN = true;
	   }
	
	    return super.onKeyDown(keyCode, event);
	}
	
	
	public void startSearchService(){
		
		//startActivity(intent);
		initialize();
	}
	
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void initialize()
	{
		
		
		
		
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
		//i.setClassName(chosenService.packageName, chosenService.packageName + ".services.MessengerService"); //Simon
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
			

					
			        playerCount = bundle.getInt(MultiplayerInterface.CONNECTION_PLAYERCOUNT);
			        Log.d ("test", "playerNames "+bundle.getString(MultiplayerInterface.CONNECTION_PLAYERNAMES));
			        ownId = bundle.getInt(MultiplayerInterface.CONNECTION_OWN_ID);
			
				    
				  
				    

					if(firstTime){

					Intent i=new Intent();
					i.setClassName(packageName, bundle.getString(MultiplayerInterface.CONNECTION_ACTIVITY));
					startActivity(i);
					
					firstTime = false;
					
					}else{
						
						
						// Auswahl der Autos, Host wählt Strecke, Welt wird erstellt
						//setScreen(SelectACarScreen.getInstance(GameActivity.this, true));
						
					
						//TODO teeestt
						multiplayer = true;
						setScreen(LoadingScreen.getInstance(GameActivity.this));
						
					}

					
				break;	
				case MultiplayerInterface.MESSAGE_4_GET:
					
					Log.d("message", ""+4);

					Bundle dataBundle =msg.getData();
					

					Log.d ("test", "adress: "+dataBundle.getInt(MultiplayerInterface.GET_ADDRESS));
					String messageType = dataBundle.getString(MultiplayerInterface.GET_TYPE);
					recievedByteData = dataBundle.getByteArray(MultiplayerInterface.GET_DATA);
					
					String dataString = new String(recievedByteData);
                    
//					final Parcel parcelData2  = Parcel.obtain();
//                  parcelData2.setDataPosition(0);
//					parcelData2.unmarshall(recievedByteData, 0, recievedByteData.length);
//					StringData recievedData = StringData.CREATOR.createFromParcel(parcelData2); 
					
					
					if(messageType.equals("car")){
						
						parseCarString(dataString);
						
					}
					
					
					if(messageType.equals("map")){
						
						map = Integer.parseInt(dataString);  //Map wird gesetzt
						
						if(ownId != 0){  //Wenn man nicht der Host ist, wird der Ladebildschirm gestartet
							
							setScreen(SelectAMapScreen.getInstance(GameActivity.this, true));
							SelectAMapScreen.getInstance().loading = true;
						}
						
					}
					
					
					if(messageType.equals("data")){
						
						parseCoordString(dataString);
						
					}
					
					
					if(messageType.equals("pause")){
						
						parsePauseString(dataString);
						
					}
					
					
					 
					
					
					
				break;	
				case MultiplayerInterface.MESSAGE_6_CLOSED:
					
					Log.d("message", ""+6);
					
					Bundle closingBundle =msg.getData();
					
					
					Toast.makeText(GameActivity.this, "Verbindung wurde von PlayId " +closingBundle.getInt(MultiplayerInterface.CLOSED_BY)+" beendet", Toast.LENGTH_SHORT).show();
					

				break;
				default:
					super.handleMessage(msg);
			}
		}



		private void parseCarString(String dataString) {
			
			String[] temp = dataString.split(" ");
			
			int playerNo = Integer.parseInt(temp[0]);
			int selectedCar = Integer.parseInt(temp[1]);
			
			switch(playerNo){
			
			case 0:
				SelectACarScreen.getInstance().selectedCar0 = selectedCar;
				Toast.makeText(GameActivity.this, "0 hat ausgewählt " + selectedCar, Toast.LENGTH_SHORT).show();
				break;
			case 1:
				SelectACarScreen.getInstance().selectedCar1 = selectedCar;
				Toast.makeText(GameActivity.this, "1 hat ausgewählt " + selectedCar, Toast.LENGTH_SHORT).show();
				break;
			case 2:
				SelectACarScreen.getInstance().selectedCar2 = selectedCar;
				Toast.makeText(GameActivity.this, "2 hat ausgewählt " + selectedCar, Toast.LENGTH_SHORT).show();
				break;
			case 3:
				SelectACarScreen.getInstance().selectedCar3 = selectedCar;
				Toast.makeText(GameActivity.this, "3 hat ausgewählt " + selectedCar, Toast.LENGTH_SHORT).show();
				break;
			
			}
			
		}
		
private void parsePauseString(String dataString) { 
			
			String[] temp = dataString.split(" ");
			
			//TODO gucken wer Pause gedrückt hat, damit auch nur dieser Resume drücken kann.
			
			int playerNo = Integer.parseInt(temp[0]);
			int flag = Integer.parseInt(temp[1]);
			
			//Hat jemand Pause gedrückt?
			if(flag ==1)GameScreen.getInstance().state = GameScreen.GAME_PAUSED;
			if(flag ==0)GameScreen.getInstance().state = GameScreen.GAME_RUNNING;
			
		}
		
		
private void parseCoordString(String dataString) {
			
			String[] temp = dataString.split(" ");
			
			int playerId = Integer.parseInt(temp[0]);
			float x = Float.valueOf(temp[1].trim()).floatValue();
			float y =  Float.valueOf(temp[2].trim()).floatValue();
			float angle =  Float.valueOf(temp[3].trim()).floatValue();
			
			if (playerId != ownId){
			switch(playerId){
			
			case 0:
				GameScreen.getInstance().world.car0.position.x = x;
				GameScreen.getInstance().world.car0.position.y = y;
				GameScreen.getInstance().world.car0.pitch = angle;
				
				break;
			case 1:
				GameScreen.getInstance().world.car1.position.x = x;
				GameScreen.getInstance().world.car1.position.y = y;
				GameScreen.getInstance().world.car1.pitch = angle;
				
				break;
			case 2:
				GameScreen.getInstance().world.car2.position.x = x;
				GameScreen.getInstance().world.car2.position.y = y;
				GameScreen.getInstance().world.car2.pitch = angle;
				break;
			case 3:
				GameScreen.getInstance().world.car3.position.x = x;
				GameScreen.getInstance().world.car3.position.y = y;
				GameScreen.getInstance().world.car3.pitch = angle;
				break;
			
			}
			}
			
		}
		
		
		
		
	}
	

		public void sendData(float positionX, float positionY, float angle)
		{
			
				if(!boundToService)
				{
					Toast.makeText(GameActivity.this, "not bound to a service", Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				Message msg=Message.obtain(null, MultiplayerInterface.MESSAGE_3_SEND);
				
				Bundle bundle=new Bundle();
				
				//ParcelData data = new ParcelData((byte)1, (byte)1, (byte)1, 4.0f, 4.0f, 4.0f, 2, 2);
				//StringData stringData = new StringData("es klappt!");
				
				String dataString = ownId+" "+positionX+" "+positionY+" "+angle ;
//				
//				final Parcel parcelData  = Parcel.obtain();
//				data.writeToParcel(parcelData, 0);
//                parcelData.setDataPosition(0);
//                final byte[] byteData = parcelData.marshall();
				
			
				
                bundle.putByteArray(MultiplayerInterface.SEND_DATA, dataString.getBytes());
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
		
		
		public void sendPause(int flag)
		{
			
				if(!boundToService)
				{
					Toast.makeText(GameActivity.this, "not bound to a service", Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				Message msg=Message.obtain(null, MultiplayerInterface.MESSAGE_3_SEND);
				
				Bundle bundle=new Bundle();
				
				//ParcelData data = new ParcelData((byte)1, (byte)1, (byte)1, 4.0f, 4.0f, 4.0f, 2, 2);
				//StringData stringData = new StringData("es klappt!");
				
				String dataString = ownId+" "+flag ;
//				
//				final Parcel parcelData  = Parcel.obtain();
//				data.writeToParcel(parcelData, 0);
//                parcelData.setDataPosition(0);
//                final byte[] byteData = parcelData.marshall();
				
			
				
                bundle.putByteArray(MultiplayerInterface.SEND_DATA, dataString.getBytes());
				bundle.putInt(MultiplayerInterface.SEND_ADDRESS, -1);  
				bundle.putString(MultiplayerInterface.SEND_TYPE, "pause");
				
				
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
		
		
		public void sendSelectedCar(int selectedCar)
		{
			
				if(!boundToService)
				{
					Toast.makeText(GameActivity.this, "not bound to a service", Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				Message msg=Message.obtain(null, MultiplayerInterface.MESSAGE_3_SEND);
				
				Bundle bundle=new Bundle();
				
				//ParcelData data = new ParcelData((byte)1, (byte)1, (byte)1, 4.0f, 4.0f, 4.0f, 2, 2);
				//StringData stringData = new StringData("es klappt!");
				String dataString = ownId+" "+selectedCar ;
//				
//				final Parcel parcelData  = Parcel.obtain();
//				data.writeToParcel(parcelData, 0);
//                parcelData.setDataPosition(0);
//                final byte[] byteData = parcelData.marshall();
				
				
				
                bundle.putByteArray(MultiplayerInterface.SEND_DATA, dataString.getBytes());
				bundle.putInt(MultiplayerInterface.SEND_ADDRESS, -1);  
				bundle.putString(MultiplayerInterface.SEND_TYPE, "car");
				
				
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
		
		
		public void sendSelectedMap(int selectedMap)
		{
			
				if(!boundToService)
				{
					Toast.makeText(GameActivity.this, "not bound to a service", Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				Message msg=Message.obtain(null, MultiplayerInterface.MESSAGE_3_SEND);
				
				Bundle bundle=new Bundle();
				
				//ParcelData data = new ParcelData((byte)1, (byte)1, (byte)1, 4.0f, 4.0f, 4.0f, 2, 2);
				//StringData stringData = new StringData("es klappt!");
				String dataString = ""+selectedMap ;
//				
//				final Parcel parcelData  = Parcel.obtain();
//				data.writeToParcel(parcelData, 0);
//                parcelData.setDataPosition(0);
//                final byte[] byteData = parcelData.marshall();
				
				
				
                bundle.putByteArray(MultiplayerInterface.SEND_DATA, dataString.getBytes());
				bundle.putInt(MultiplayerInterface.SEND_ADDRESS, -1);  
				bundle.putString(MultiplayerInterface.SEND_TYPE, "map");
				
				
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

	
	
public void toast(String toast){
	
	Toast.makeText(GameActivity.this, (CharSequence) toast , Toast.LENGTH_SHORT).show();
	
}


	

}
