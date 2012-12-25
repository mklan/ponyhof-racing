package de.ponyhofgang.ponyhofgame.game.screens;


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

public class LoadingScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	Rectangle musicCheckboxBounds;
	Rectangle sfxCheckboxBounds;
	Rectangle arrowLeftBounds;
	Rectangle arrowRightBounds;
	Rectangle backButtonBounds;

	int height;
	int width;
	
	GLGame game;
	
	public GameScreen gameScreen;
	
	boolean firstTimeCreate = true;
	
	private static LoadingScreen instance = null; 

	private LoadingScreen(Game game) {
		super(game);

		this.game = (GLGame) game;
		
		MainMenuScreen mainMenuScreen = MainMenuScreen.getInstance();
		
		height = mainMenuScreen.height;
		width = mainMenuScreen.width;
		
		
		
		guiCam = new Camera2D(glGraphics, width, height);
		batcher = new SpriteBatcher(glGraphics, 10);
		
		
		
	}

	@Override
	public void update(float deltaTime) {
		
		if (firstTimeCreate) {

			Assets.loadLevel(game);
			firstTimeCreate = false;
		} 
        
		
		game.setScreen(GameScreen.getInstance(game, SelectACarScreen.selectedCar));
		
		
	
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
	
	 public static LoadingScreen getInstance(Game game) {
	        if (instance == null) {
	            instance = new LoadingScreen(game);
	        }
	        return instance;
	    }
	 
	 public static LoadingScreen getInstance() {
	        if (instance == null) {
	            instance = new LoadingScreen(null);
	        }
	        return instance;
	    }
	 
	 public void clear()  
	  {  
	    instance = null;  
	  }
}
