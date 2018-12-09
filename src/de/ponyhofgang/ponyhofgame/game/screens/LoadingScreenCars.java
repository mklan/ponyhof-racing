package de.ponyhofgang.ponyhofgame.game.screens;


import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import de.ponyhofgang.ponyhofgame.framework.Game;
import de.ponyhofgang.ponyhofgame.framework.gl.Camera2D;
import de.ponyhofgang.ponyhofgame.framework.gl.SpriteBatcher;
import de.ponyhofgang.ponyhofgame.framework.impl.GLGame;
import de.ponyhofgang.ponyhofgame.framework.impl.GLScreen;
import de.ponyhofgang.ponyhofgame.framework.math.PonyMath;
import de.ponyhofgang.ponyhofgame.framework.math.Rectangle;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;
import de.ponyhofgang.ponyhofgame.game.Assets;

public class LoadingScreenCars extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	
	int height;
	int width;
	
	GLGame game;
	
	public GameScreen gameScreen;
	
	boolean firstTimeCreate = true;
	
	MainMenuScreen mainMenuScreen;
	private boolean multiplayer;

	
	private static LoadingScreenCars instance = null; 

	private LoadingScreenCars(Game game, boolean multiplayer) {
		super(game);


		this.multiplayer = multiplayer;
		this.game = (GLGame) game;
		
		mainMenuScreen = MainMenuScreen.getInstance();
		
		height = mainMenuScreen.height;
		width = mainMenuScreen.width;
		
		
		
		guiCam = new Camera2D(glGraphics, width, height);
		batcher = new SpriteBatcher(glGraphics, 10);
		
		
		
	}

	@Override
	public void update(float deltaTime) {
		
		
	 
		if (firstTimeCreate) {

			Assets.loadCars(game);
			firstTimeCreate = false;
		} 
        

	if (multiplayer) game.setScreen(SelectACarScreen.getInstance(game, true)); 
	else game.setScreen(SelectACarScreen.getInstance(game, false)); 
		
		
	
		}
			
	

	@Override
	public void present(float deltaTime) {
		
        
		
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.loading);
		batcher.drawSprite(width / 2, height / 2, width, height,
				Assets.loadingBackgroundRegion);
		
		batcher.drawSprite(width / 2, height / 2, PonyMath.getRatio(width, 512), PonyMath.getRatio(width, 271), Assets.iconAndLoadingRegion);
		
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
	
	 public static LoadingScreenCars getInstance(Game game, boolean multiplayer) {
	        if (instance == null) {
	            instance = new LoadingScreenCars(game, multiplayer);
	        }
	        return instance;
	    }
	 
	 public static LoadingScreenCars getInstance() {
	        if (instance == null) {
	            instance = new LoadingScreenCars(null, false);
	        }
	        return instance;
	    }
	 
	 public void clear()  
	  {  
	    instance = null;  
	  }
}
