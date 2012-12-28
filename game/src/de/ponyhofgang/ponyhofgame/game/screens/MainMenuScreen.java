package de.ponyhofgang.ponyhofgame.game.screens;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import de.ponyhofgang.ponyhofgame.framework.Game;
import de.ponyhofgang.ponyhofgame.framework.Input.TouchEvent;
import de.ponyhofgang.ponyhofgame.framework.gl.Camera2D;
import de.ponyhofgang.ponyhofgame.framework.gl.SpriteBatcher;
import de.ponyhofgang.ponyhofgame.framework.impl.GLScreen;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.framework.math.PonyMath;
import de.ponyhofgang.ponyhofgame.framework.math.Rectangle;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;
import de.ponyhofgang.ponyhofgame.game.Assets;
import de.ponyhofgang.ponyhofgame.game.GameActivity;



public class MainMenuScreen extends GLScreen {

	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;

	Rectangle timeTrialBounds;
	Rectangle multiplayerBounds;
	Rectangle settingsBounds;
	Rectangle quitBounds;
	Rectangle aboutBounds;

	
	
	public int height;
	public int width;
	       

	
	


	private GameActivity game;
	


	
	private static MainMenuScreen instance = null;
	
	
	

	private MainMenuScreen(Game game, int height, int width) {
		super(game);
		
		
		this.height = height;
		this.width = width;
		this.game = (GameActivity) game;
		
		

		guiCam = new Camera2D(glGraphics, width, height);
		batcher = new SpriteBatcher(glGraphics, 10);

		touchPoint = new Vector2();
		
		timeTrialBounds = new Rectangle((width/2)-(width/2.5f/2), height/2-height/8+width/2.5f/4, width/2.5f, width/2.5f/4);
		multiplayerBounds = new Rectangle((width/2)-(width/2.5f/2), height/2-height/8, width/2.5f, width/2.5f/4);
		settingsBounds = new Rectangle((width/2)-(width/2.5f/2), height/2-height/8-width/2.5f/4, width/2.5f, width/2.5f/4);
		quitBounds = new Rectangle((width/2)-(width/2.5f/2), height/2-height/8-width/2.5f/2, width/2.5f, width/2.5f/4);
		
		aboutBounds = new Rectangle(width-PonyMath.getRatio(width, 100)-(width/9.5f/2), PonyMath.getRatio(width, 80)-(width/9.5f/2), width/9.5f, width/9.5f);

	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> events = game.getInput().getTouchEvents();
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;
			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
		
			if (OverlapTester.pointInRectangle(timeTrialBounds, touchPoint)) {
				
				
				Assets.playSound(Assets.clickSound);
				game.setScreen(SelectACarScreen.getInstance(game));
				
				
				
				
			}
			
			if (OverlapTester.pointInRectangle(multiplayerBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				
				
				
				game.startSearchService();
				
			}
			
			if (OverlapTester.pointInRectangle(settingsBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(SettingsScreen.getInstance(game));
			
			}
			
			if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
				
				Assets.playSound(Assets.clickSound);
				System.exit(1);
			
			}
			
			if (OverlapTester.pointInRectangle(aboutBounds, touchPoint)) {
				
				//Assets.pitchSound(Assets.clickSound);
				
				Assets.playSound(Assets.clickSound);
		
				game.setScreen(AboutScreen.getInstance(game));
			
			}
		}
		
	
		
		
	}

	@Override
	public void present(float deltaTime) {

		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Assets.background);
		batcher.drawSprite(width/2, height/2, width, height, Assets.backgroundRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.items);

		batcher.drawSprite(width/2, height/2-height/8, width/2.5f, width/2.5f, Assets.menuRegion);
		batcher.drawSprite(width-PonyMath.getRatio(width, 100), PonyMath.getRatio(width, 80), width/9.5f, width/9.5f, Assets.aboutRegion);

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
	
	 public static MainMenuScreen getInstance(Game game, int height, int width) {
	        if (instance == null) {
	            instance = new MainMenuScreen(game, height, width);
	        }
	        return instance;
	    }
	 
	 public static MainMenuScreen getInstance() {
	        if (instance == null) {
	            instance = new MainMenuScreen(null, -1, -1);
	        }
	        return instance;
	    }

}
