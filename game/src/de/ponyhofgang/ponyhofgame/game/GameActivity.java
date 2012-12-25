package de.ponyhofgang.ponyhofgame.game;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.view.KeyEvent;
import de.ponyhofgang.ponyhofgame.framework.Screen;
import de.ponyhofgang.ponyhofgame.framework.impl.GLGame;
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
		    	
	    	 
	    	 
	    	 if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_PAUSED) GameScreen.getInstance().state = GameScreen.GAME_RUNNING;
	    	 else if (this.getCurrentScreen().getClass().getSimpleName().equals("GameScreen")) GameScreen.getInstance().state = GameScreen.GAME_PAUSED;
	    	 
	    	
	    	 
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
		
		startActivity(intent);
	}
	


	

}
