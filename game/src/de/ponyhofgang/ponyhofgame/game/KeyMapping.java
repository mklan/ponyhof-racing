package de.ponyhofgang.ponyhofgame.game;

import android.view.KeyEvent;
import de.ponyhofgang.ponyhofgame.framework.Screen;
import de.ponyhofgang.ponyhofgame.game.screens.AboutScreen;
import de.ponyhofgang.ponyhofgame.game.screens.GameScreen;
import de.ponyhofgang.ponyhofgame.game.screens.SelectACarScreen;
import de.ponyhofgang.ponyhofgame.game.screens.SelectAMapScreen;
import de.ponyhofgang.ponyhofgame.game.screens.SettingsScreen;

public class KeyMapping {
	
	
	public static void controlManager(Screen currentScreen ,int keyCode){
		
	    if (keyCode == KeyEvent.KEYCODE_BUTTON_START) {
		       
	    	if (currentScreen.getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_PAUSED) GameScreen.getInstance().state = GameScreen.GAME_RUNNING;
	    	 else if (currentScreen.getClass().getSimpleName().equals("GameScreen")) GameScreen.getInstance().state = GameScreen.GAME_PAUSED;
	   }
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
		       
	    	
	    	 if (currentScreen.getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_LEFT = true;
	   }
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
		       
	    	
	    	 if (currentScreen.getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_RIGHT = true;
	   }
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
		       
	    	
	    	 if (currentScreen.getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_UP = true;
	   }
	    
	    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
		       
	    	
	    	 if (currentScreen.getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_DOWN = true;
	   }
		
		
	}

	public static void backKeyManager(Screen currentScreen, GameActivity gA, int keyCode) {
		
	if (keyCode == KeyEvent.KEYCODE_BACK) {	
		
	 if (currentScreen.getClass().getSimpleName().equals("MainMenuScreen")) System.exit(1); 
   	 if (currentScreen.getClass().getSimpleName().equals("SettingsScreen")) SettingsScreen.getInstance().pressedBackKey = true;
   	 if (currentScreen.getClass().getSimpleName().equals("AboutScreen")) AboutScreen.getInstance().pressedBackKey = true;
   	 if (currentScreen.getClass().getSimpleName().equals("ChooseCarScreen")) SelectACarScreen.getInstance().pressedBackKey = true;
   	 if (currentScreen.getClass().getSimpleName().equals("ChooseMapScreen")) SelectAMapScreen.getInstance().pressedBackKey = true;
	    	
   	 
   	 
   	 if (currentScreen.getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_PAUSED) {
   		 GameScreen.getInstance().state = GameScreen.GAME_RUNNING;
   		 if(gA.multiplayer) gA.sendStringCommands(0+"", "pause"); //wenn im Multiplayer, dann den anderen bescheid sagen
   	 }
   	 else if (currentScreen.getClass().getSimpleName().equals("GameScreen")){
   		 GameScreen.getInstance().state = GameScreen.GAME_PAUSED;
   		if(gA.multiplayer) gA.sendStringCommands(1+"", "pause"); //wenn im Multiplayer, dann den anderen bescheid sagen
   	 }
   	 
   	
   	 
   }
   
   
   if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
	       
   	
   	 if (currentScreen.getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING)GameScreen.getInstance().DPAD_LEFT = false;
  }
   
   if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
	       
   	
   	 if (currentScreen.getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_RIGHT = false;
  }
   
   if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
	       
   	
   	 if (currentScreen.getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_UP = false;
  }
   
   if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
	       
   	
   	 if (currentScreen.getClass().getSimpleName().equals("GameScreen") && GameScreen.getInstance().state ==  GameScreen.GAME_RUNNING) GameScreen.getInstance().DPAD_DOWN = false;
  }
   
   
		
	}


}
