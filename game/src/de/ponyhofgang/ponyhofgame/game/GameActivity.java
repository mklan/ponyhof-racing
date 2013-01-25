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
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import de.ponyhofgang.ponyhofgame.R;
import de.ponyhofgang.ponyhofgame.framework.Screen;
import de.ponyhofgang.ponyhofgame.framework.impl.GLGame;
import de.ponyhofgang.ponyhofgame.game.multiplayer.MultiplayerInterface;
import de.ponyhofgang.ponyhofgame.game.multiplayer.ParcelData;

import de.ponyhofgang.ponyhofgame.game.screens.SelectACarScreen;
import de.ponyhofgang.ponyhofgame.game.screens.SelectAMapScreen;
import de.ponyhofgang.ponyhofgame.game.screens.GameScreen;

import de.ponyhofgang.ponyhofgame.game.screens.MainMenuScreen;


public class GameActivity extends GLGame {

	boolean firstTimeCreate = true;

	private final String TAG = GameActivity.class.getName();
	private final Messenger messengerActivity = new Messenger(
			new ActivityHandler());
	private Messenger messengerService;
	private ServiceConnection serviceConnection = new MyServiceConnection();
	private boolean boundToService = false;
	private List<PackageInfo> availableServices;
	private int chosenServiceIndex = -1;

	public int ownId = 0;
	public int playerCount = 1;
	public int map = 0;
	public boolean multiplayer = false;
	
	public String[] playerNames;

	public String packageName;
	


	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Toast.makeText(this, "loading...", Toast.LENGTH_SHORT).show();
		
		 

	} 
	
	
	public Screen getStartScreen() {

	
		
		
		return MainMenuScreen.getInstance(this, super.getHeight(),
				super.getWidth());
		
		

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate) {
			


			Assets.loadLoadingScreen(this);
			
			Settings.load(getFileIO());
			Assets.load(this);
			firstTimeCreate = false;
			
			

		} else {
			Assets.reload();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		// TODO
		// if (Settings.musicEnabled)
		// Assets.music.pause();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		KeyMapping.backKeyManager(this.getCurrentScreen(), this, keyCode);

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		KeyMapping.controlManager(this.getCurrentScreen(), keyCode);

		return super.onKeyDown(keyCode, event);
	}

	/////////////////////////////////////////////////////////////////////Multiplayer Teil//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void initializeMultiplayer() {

		availableServices = new ArrayList<PackageInfo>();
		List<PackageInfo> services = getPackageManager().getInstalledPackages(
				PackageManager.GET_SERVICES);
		for (PackageInfo pi : services) {

			if (pi.packageName
					.startsWith(MultiplayerInterface.CONNECTION_SERVICE_PREFIX)) {
				availableServices.add(pi);
			}
		}

		new ClassChooserDialog().show(getFragmentManager(), TAG);

	}

	@Override
	protected void onStart() {
		super.onStart();
		if (chosenServiceIndex != -1)
			doBind();

	}

	@Override
	protected void onStop() {
		super.onStop();
		
		if (chosenServiceIndex != -1)
			unbindService(serviceConnection);

	}

	private void doBind() {
		Intent i = new Intent();
		PackageInfo chosenService = availableServices.get(chosenServiceIndex);

		packageName = chosenService.packageName;

		i.setClassName(
				chosenService.packageName,
				chosenService.packageName
						+ "."
						+ chosenService.applicationInfo
								.loadLabel(getPackageManager())); 

		bindService(i, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	public void doUnbind() 
	{

		if (boundToService) {

			// tell service i am offline

			Message msg = Message.obtain(null,
					MultiplayerInterface.MESSAGE_5_CLOSE);

			msg.replyTo = messengerActivity;
			try {
				messengerService.send(msg);
			} catch (RemoteException e) {
				Log.e(TAG, e.getClass().getName() + ": " + e.getMessage());
			}
		
			unbindService(serviceConnection);
			boundToService = false;
		}
	}
	
	

	private class MyServiceConnection implements ServiceConnection {
		public void onServiceConnected(ComponentName name, IBinder service) {
			messengerService = new Messenger(service);

			// make myself public to the service

			Message msg = Message.obtain(null,
					MultiplayerInterface.MESSAGE_1_REGISTER);
			msg.replyTo = messengerActivity;
			try {
				messengerService.send(msg);
			} catch (RemoteException e) {
				Log.e(TAG, e.getClass().getName() + ": " + e.getMessage());
			}
			boundToService = true;
		}

		public void onServiceDisconnected(ComponentName name) {
			messengerService = null;
			boundToService = false;
		}
	}

	private class ActivityHandler extends Handler {

		private boolean firstTime = true;
		

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MultiplayerInterface.MESSAGE_2_CONNECTION:

				// process incoming messages
				Log.d("message", "" + 2);
				Bundle bundle = msg.getData();
				// Bundle bundle=(Bundle) msg.obj;// TODO Jan

				playerCount = bundle
						.getInt(MultiplayerInterface.CONNECTION_PLAYERCOUNT);
				
				parsePlayerNames(bundle.getString(MultiplayerInterface.CONNECTION_PLAYERNAMES));
				ownId = bundle.getInt(MultiplayerInterface.CONNECTION_OWN_ID);
				
				if (firstTime) {

					Intent i = new Intent();
					i.setClassName(
							packageName,
							bundle.getString(MultiplayerInterface.CONNECTION_ACTIVITY));
					startActivity(i);

					firstTime = false;

				} else {

					// Auswahl der Autos, Host wählt Strecke, Welt wird erstellt
					 multiplayer = true;

					MainMenuScreen.getInstance().loading = true;



				}

				break;
			case MultiplayerInterface.MESSAGE_4_GET:

				Log.d("message", "" + 4);

				Bundle dataBundle = msg.getData();
				

				Log.d("test",
						"adress: "
								+ dataBundle
										.getInt(MultiplayerInterface.GET_ADDRESS));
				String messageType = dataBundle
						.getString(MultiplayerInterface.GET_TYPE);
				byte[] recievedByteData = dataBundle
						.getByteArray(MultiplayerInterface.GET_DATA);

				if (messageType.equals("car")) {

					String dataString = new String(recievedByteData);
					parseCarString(dataString);

				}

				if (messageType.equals("map")) {

					String[] temp = new String(recievedByteData).split(" ");

			
					map = Integer.parseInt(temp[1]); // Map wird gesetzt

					if (ownId != 0) { // Wenn man nicht der Host ist, wird der
										// Ladebildschirm gestartet
						
						if(SelectACarScreen.getInstance().sound != null)SelectACarScreen.getInstance().sound.stop();

						setScreen(SelectAMapScreen.getInstance(
								GameActivity.this, true));
						SelectAMapScreen.getInstance().loading = true;
					}

				}

				if (messageType.equals("boxCollected")) {

					String dataStrings[] = new String(recievedByteData)
							.split(" ");
					GameScreen.getInstance().world.deactivateBox(
							Integer.parseInt(dataStrings[1]),
							Float.parseFloat(dataStrings[2]));

				}

				if (messageType.equals("slipped")) {

					GameScreen.getInstance().world.removeOilSplill(Integer
							.parseInt(new String(recievedByteData).split(" ")[1]));

				}

				if (messageType.equals("hit")) {

					
					GameScreen.getInstance().world.removeRocket(Integer
							.parseInt(new String(recievedByteData).split(" ")[1]));

				}

				if (messageType.equals("rocket")) {

					final Parcel parcelData = Parcel.obtain();
					parcelData.unmarshall(recievedByteData, 0,
							recievedByteData.length);
					parcelData.setDataPosition(0);
					ParcelData recievedData = ParcelData.CREATOR
							.createFromParcel(parcelData);
					
					GameScreen.getInstance().world.createRocket(recievedData.positionX, recievedData.positionY, recievedData.angle);
					
					parcelData.recycle();

				}

				if (messageType.equals("oilSpill")) {

					final Parcel parcelData = Parcel.obtain();
					parcelData.unmarshall(recievedByteData, 0,
							recievedByteData.length);
					parcelData.setDataPosition(0);
					ParcelData recievedData = ParcelData.CREATOR
							.createFromParcel(parcelData);
					
					
					GameScreen.getInstance().world.createOilSplill(recievedData.positionX, recievedData.positionY, recievedData.angle);
					
					parcelData.recycle();

				}

				if (messageType.equals("data")) {

					final Parcel parcelData = Parcel.obtain();
					parcelData.unmarshall(recievedByteData, 0,
							recievedByteData.length);
					parcelData.setDataPosition(0);
					ParcelData recievedData = ParcelData.CREATOR
							.createFromParcel(parcelData);

			    if (recievedData.playerId != ownId)
						setCoords(recievedData); // nur falls das nicht meine
													// Daten sind
					parcelData.recycle();

				}

				if (messageType.equals("pause")) {

					String dataString = new String(recievedByteData);
					parsePauseString(dataString);

				}
				
				if (messageType.equals("time")) {

					String dataString = new String(recievedByteData);
					//TODO
				}
				
				if (messageType.equals("ready")) {

					GameScreen.getInstance().playerReadyCount++;
				}

				break;
				
			case MultiplayerInterface.MESSAGE_6_CLOSED:

				Log.d("message", "" + 6);

				Bundle closingBundle = msg.getData();
				
				

				Toast.makeText(
						GameActivity.this,
						"Verbindung wurde von PlayId "
								+ closingBundle
										.getInt(MultiplayerInterface.CLOSED_BY)
								+ " beendet", Toast.LENGTH_SHORT).show();
				
				GameScreen.getInstance().goToMainMenu();
				
				

				break;
			default:
				super.handleMessage(msg);
			}
		}

		public void parsePlayerNames(String dataString) {
			
			playerNames = dataString.split(" ");
	
		}

		private void parseCarString(String dataString) {

			String[] temp = dataString.split(" ");

			int playerNo = Integer.parseInt(temp[0]);
			int selectedCar = Integer.parseInt(temp[1]);

		
				SelectACarScreen.getInstance().cars.set (playerNo, selectedCar);
				Toast.makeText(GameActivity.this,
						playerNo + " hat ausgewählt " + selectedCar, Toast.LENGTH_SHORT)
						.show();


		}

		private void parsePauseString(String dataString) {

			String[] temp = dataString.split(" ");

			// TODO gucken wer Pause gedrückt hat, damit auch nur dieser Resume
			// drücken kann.

			int playerNo = Integer.parseInt(temp[0]);
			int flag = Integer.parseInt(temp[1]);

			// Hat jemand Pause gedrückt?
			if (flag == 1)
				GameScreen.getInstance().state = GameScreen.GAME_PAUSED;
			if (flag == 0)
				GameScreen.getInstance().state = GameScreen.GAME_RUNNING;

		}

	}

	private void setCoords(ParcelData data) {

		switch (data.playerId) {

		case 0:
			GameScreen.getInstance().world.car0.position.x = data.positionX;
			GameScreen.getInstance().world.car0.position.y = data.positionY;
			GameScreen.getInstance().world.car0.pitch = data.angle;
			GameScreen.getInstance().world.car0.lap = data.lap;
			GameScreen.getInstance().world.car0.inCollider = data.inCollider;

			break;
		case 1:
			GameScreen.getInstance().world.car1.position.x = data.positionX;
			GameScreen.getInstance().world.car1.position.y = data.positionY;
			GameScreen.getInstance().world.car1.pitch = data.angle;
			GameScreen.getInstance().world.car1.lap = data.lap;
			GameScreen.getInstance().world.car1.inCollider = data.inCollider;

			break;
		case 2:
			GameScreen.getInstance().world.car2.position.x = data.positionX;
			GameScreen.getInstance().world.car2.position.y = data.positionY;
			GameScreen.getInstance().world.car2.pitch = data.angle;
			GameScreen.getInstance().world.car2.lap = data.lap;
			GameScreen.getInstance().world.car2.inCollider = data.inCollider;
			break;
		case 3:
			GameScreen.getInstance().world.car3.position.x = data.positionX;
			GameScreen.getInstance().world.car3.position.y = data.positionY;
			GameScreen.getInstance().world.car3.pitch = data.angle;
			GameScreen.getInstance().world.car3.lap = data.lap;
			GameScreen.getInstance().world.car3.inCollider = data.inCollider;
			break;

		}
	}

	public void sendData(float positionX, float positionY, float angle,
			int lap, int inCollider, String type) {

		if (!boundToService) {
			 Toast.makeText(GameActivity.this, "not bound to a service",
			 Toast.LENGTH_SHORT).show();
			return;
		}

		Message msg = Message.obtain(null, MultiplayerInterface.MESSAGE_3_SEND);

		Bundle bundle = new Bundle();

		ParcelData data = new ParcelData(ownId, positionX, positionY, angle, lap, inCollider);

		
		final Parcel parcelData = Parcel.obtain();
		data.writeToParcel(parcelData, 0);

		final byte[] byteData = parcelData.marshall();

		bundle.putByteArray(MultiplayerInterface.SEND_DATA, byteData);
		bundle.putInt(MultiplayerInterface.SEND_ADDRESS, -1);
		bundle.putString(MultiplayerInterface.SEND_TYPE, type);

		msg.setData(bundle);

		try {
			messengerService.send(msg);
		} catch (RemoteException e) {
			Log.e(TAG, e.getClass().getName() + ": " + e.getMessage());
		}

		parcelData.recycle();

	}

	public void sendStringCommands(String data, String type) {

		if (!boundToService) {
			 Toast.makeText(GameActivity.this, "not bound to a service",
			 Toast.LENGTH_SHORT).show();
			return;
		}

		Message msg = Message.obtain(null, MultiplayerInterface.MESSAGE_3_SEND);

		Bundle bundle = new Bundle();

		String dataString = ownId + " " + data;

		bundle.putByteArray(MultiplayerInterface.SEND_DATA,
				dataString.getBytes());

		bundle.putInt(MultiplayerInterface.SEND_ADDRESS, -1);
		bundle.putString(MultiplayerInterface.SEND_TYPE, type);

		msg.setData(bundle);

		try {
			messengerService.send(msg);
		} catch (RemoteException e) {
			Log.e(TAG, e.getClass().getName() + ": " + e.getMessage());
		}

	}

	private class ClassChooserDialog extends DialogFragment implements
			DialogInterface.OnClickListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("choose your service");
			CharSequence[] texts = new CharSequence[availableServices.size()];
			for (int i = 0; i < availableServices.size(); i++) {
				PackageInfo service = availableServices.get(i);
				texts[i] = service.applicationInfo
						.loadLabel(getPackageManager())
						+ " ("
						+ service.packageName + ")";
			}
			builder.setItems(texts, this);
			return builder.create();
		}

		public void onClick(DialogInterface dialog, int which) {
			chosenServiceIndex = which;
			doBind();
		}

	}

	public void toast(String toast) {

		Toast.makeText(GameActivity.this, (CharSequence) toast,
				Toast.LENGTH_SHORT).show();

	}




}
