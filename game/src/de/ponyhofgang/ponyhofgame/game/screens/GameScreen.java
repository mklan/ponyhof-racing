package de.ponyhofgang.ponyhofgame.game.screens;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import de.ponyhofgang.ponyhofgame.framework.Game;

import de.ponyhofgang.ponyhofgame.framework.Input.TouchEvent;
import de.ponyhofgang.ponyhofgame.framework.gl.Camera2D;
import de.ponyhofgang.ponyhofgame.framework.gl.FPSCounter;
import de.ponyhofgang.ponyhofgame.framework.gl.SpriteBatcher;
import de.ponyhofgang.ponyhofgame.framework.impl.GLGame;
import de.ponyhofgang.ponyhofgame.framework.impl.GLScreen;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.framework.math.PonyMath;
import de.ponyhofgang.ponyhofgame.framework.math.Rectangle;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;

import de.ponyhofgang.ponyhofgame.game.Assets;
import de.ponyhofgang.ponyhofgame.game.Settings;
import de.ponyhofgang.ponyhofgame.game.World;
import de.ponyhofgang.ponyhofgame.game.World.WorldListener;
import de.ponyhofgang.ponyhofgame.game.WorldRenderer;

public class GameScreen extends GLScreen {
	public static final int GAME_RUNNING = 0;
	public static final int GAME_PAUSED = 1;
	public static final int GAME_OVER = 2;
	
	public static final int ACCELERATING = 0;
	public static final int BRAKING = 1;
	public static final int IDLING = 2;
	
	
	
	
	public boolean DPAD_UP = false;
	public boolean DPAD_DOWN = false;
	public boolean DPAD_LEFT = false;
	public boolean DPAD_RIGHT = false;

	static final float ACCEL_STEERING_ANGLE = 3;
	static final float TOUCH_STEERING_ANGLE = 2;
	
	public int state;
	
	
	private static GameScreen instance = null;
	
	Camera2D guiCam;
	Vector2 touchPoint;

	SpriteBatcher batcher;

	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle leftBounds;
	Rectangle rightBounds;
	Rectangle accelBounds;
	Rectangle brakeBounds;

	Rectangle resumeBounds;
	Rectangle quitBounds;

	Rectangle leftSideBounds;
	Rectangle rightSideBounds;

	String lastTime;
	String timeString;
	FPSCounter fpsCounter;

	private int height;
	private int width;
	float accelY;
	
	
	static GLGame game;
	
	MainMenuScreen mainMenuScreen;
	private ArrayList<Integer> selectedCars;
	

	

	private GameScreen(Game game, int ... selectedCars) {
		super(game);
		
		GameScreen.game = (GLGame) game;
       
		
	    mainMenuScreen = MainMenuScreen.getInstance();
		
		height = mainMenuScreen.height;
		width = mainMenuScreen.width;
		

		state = GAME_RUNNING;
		guiCam = new Camera2D(glGraphics, width, height);
		batcher = new SpriteBatcher(glGraphics, 10);
		touchPoint = new Vector2();

		this.selectedCars = new ArrayList<Integer>();
		for (int i = 0; i < selectedCars.length; i++)
		this.selectedCars.add(selectedCars[i]);
		
		
		world = new World(this.selectedCars.size(), 0, 0, this.selectedCars);
		
	
		
		
		worldListener = new WorldListener() {
			
			public void collision() {
				// Assets.playSound(Assets.shotSound);
				GameScreen.game.getVibrator().vibrate(20);
			}

			

		};
		
		
		world.setWorldListener(worldListener);
		
		renderer = new WorldRenderer(glGraphics);

		pauseBounds = new Rectangle(width - PonyMath.getRatio(width, 70)
				- PonyMath.getRatio(width, 70) / 2, height
				- PonyMath.getRatio(width, 70) - PonyMath.getRatio(width, 70)
				/ 2, width / 9.5f, width / 9.5f);

		resumeBounds = new Rectangle(width/2-PonyMath.getRatio(width, 256), height/2-PonyMath.getRatio(width, 85), PonyMath.getRatio(width, 512), PonyMath.getRatio(width, 126));
		quitBounds = new Rectangle(width/2-PonyMath.getRatio(width, 256), height/2-PonyMath.getRatio(width, 250), PonyMath.getRatio(width, 512), PonyMath.getRatio(width, 126));

		leftBounds = new Rectangle(PonyMath.getRatio(width, 130+30)-PonyMath.getRatio(width, 130), PonyMath.getRatio(width, 63+20)-PonyMath.getRatio(width, 63), 128, 126); 
		rightBounds = new Rectangle(PonyMath.getRatio(width, 130+30), PonyMath.getRatio(width, 63+20)-PonyMath.getRatio(width, 63), 130, 126);
		
		brakeBounds = new Rectangle(width-PonyMath.getRatio(width, 130+30)-PonyMath.getRatio(width, 130), PonyMath.getRatio(width, 63+20)-PonyMath.getRatio(width, 63), 128, 126); 
		accelBounds = new Rectangle(width-PonyMath.getRatio(width, 130+30), PonyMath.getRatio(width, 63+20)-PonyMath.getRatio(width, 63), 130, 126);

		// wenn Touch deaktiviert wurde, gibt man mit der linken Seite Gas und
		// mit der rechten bremst man
		leftSideBounds = new Rectangle(0, 0, width / 2, height);
		rightSideBounds = new Rectangle(width / 2, 0, width / 2, height);

		// lastScore = 0;
		// scoreString = "lives:" + lastLives + " waves:" + lastWaves +
		// " score:"
		// + lastScore;
		fpsCounter = new FPSCounter(); // zum Debuggen
	}

	

	
	@Override
	public void update(float deltaTime) {
		
		
		switch (state) {
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
		
		

	}

	private void updateGameOver() { // Tippen um zum Startbildschirm zu gelangen
		List<TouchEvent> events = game.getInput().getTouchEvents();
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				Assets.playSound(Assets.clickSound);
				SelectAMapScreen.getInstance().loaded = false;
				SelectAMapScreen.getInstance().loading = false;
			    game.setScreen(mainMenuScreen);
			    world = null;
			}
		}

	}

	private void updateRunning(float deltaTime) {

		List<TouchEvent> events = game.getInput().getTouchEvents();
		int len = events.size();
		
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type != TouchEvent.TOUCH_DOWN)
				continue;
			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
			if (OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
			}

		}
		
	

		if (true) {

			// lastTime = world.time; //TODO time

			timeString = "time:" + lastTime;
		}
		if (world.isGameOver()) {
			state = GAME_OVER;
		}

		world.update(deltaTime, InputAcceleration(), calculateInputRotation());

	}
	
	
	private void updatePaused() {
		List<TouchEvent> events = game.getInput().getTouchEvents();
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;
			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
			if (OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
			}
			if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				SelectAMapScreen.getInstance().loaded = false;
				SelectAMapScreen.getInstance().loading = false;
				game.setScreen(mainMenuScreen);
				world = null;
				LoadingScreen.getInstance().clear();
				clear();
				
				
			}
		}

	}

	private float calculateInputRotation() { // Hier Lenkung abfangen
		float angle = 0;
		if (Settings.touchEnabled) {  // Ist TOUCH eingestellt
			for (int i = 0; i < 2; i++) {
				if (game.getInput().isTouchDown(i) || DPAD_LEFT || DPAD_RIGHT) {
					guiCam.touchToWorld(touchPoint.set(game.getInput()
							.getTouchX(i), game.getInput().getTouchY(i)));
					if (OverlapTester.pointInRectangle(leftBounds, touchPoint) || DPAD_LEFT ) {
						angle = -TOUCH_STEERING_ANGLE;    //TODO hier muss auf jeden fall was Acceleormetermäßiges geschehen =/
					}
					if (OverlapTester.pointInRectangle(rightBounds, touchPoint) ||  DPAD_RIGHT) {
						angle = TOUCH_STEERING_ANGLE;
					}
				}
			}
		} else {   // Ist ACCEL eingestellt
			accelY = game.getInput().getAccelY()/1.5f;
			if (accelY > -ACCEL_STEERING_ANGLE && accelY < ACCEL_STEERING_ANGLE) {  //im Rahmen ist es OK
				angle = accelY;
			} else if (accelY < -ACCEL_STEERING_ANGLE){  // das ist das Maxiumum
				angle = -ACCEL_STEERING_ANGLE;
			} else{
				angle = ACCEL_STEERING_ANGLE;  // das ist das negative Maximum
			}

			
		}    
		  
		  return angle;
	}

	private int InputAcceleration() { // Hier Beschleunigung abfangen
		
		if (Settings.touchEnabled) {  // Ist TOUCH eingestellt
			for (int i = 0; i < 2; i++) {
				if (game.getInput().isTouchDown(i) ||  DPAD_UP ||  DPAD_DOWN) {
					guiCam.touchToWorld(touchPoint.set(game.getInput()
							.getTouchX(i), game.getInput().getTouchY(i)));
					if (OverlapTester.pointInRectangle(accelBounds, touchPoint) ||  DPAD_UP) {
						
						return ACCELERATING;
					}
					
					if (OverlapTester.pointInRectangle(brakeBounds, touchPoint) ||  DPAD_DOWN) {
						
						return BRAKING;
					}
				
				}
			}
		} else {
			for (int i = 0; i < 2; i++) { // Ist ACCEL eingestellt
				if (game.getInput().isTouchDown(i)) {
					guiCam.touchToWorld(touchPoint.set(game.getInput()
							.getTouchX(i), game.getInput().getTouchY(i)));
					if (OverlapTester.pointInRectangle(rightSideBounds,
							touchPoint)) {
						
						return ACCELERATING;
						
					}
					if (OverlapTester.pointInRectangle(leftSideBounds,
							touchPoint)) {
						
						return BRAKING;
					}
				}
					
					
				}
			}

		
		return IDLING;
	}

	

	@Override
	public void present(float deltaTime) {

		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(width / 2, height / 2, width, height,
				Assets.backgroundRegion);
		batcher.endBatch();

		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);

		renderer.render(world, deltaTime);

		switch (state) {
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_OVER:
			presentGameOver();
		}
		fpsCounter.logFrame();

	}

	private void presentGameOver() {
		GL10 gl = glGraphics.getGL();
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		batcher.beginBatch(Assets.items);
		// batcher.drawSprite(240, 160, 128, 64, Assets.gameOverRegion); //TODO
		// GameOverScreen ( wohl transparetner Kasten mit Endposition, und Zeit
		// )
		// Assets.font.drawText(batcher, scoreString, 10, 320-20);
		batcher.endBatch();
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);

	}

	private void presentPaused() {
		GL10 gl = glGraphics.getGL();
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		batcher.beginBatch(Assets.items2);
		batcher.drawSprite(width/2, height/2, width, height, Assets.pauseBackgroundRegion);
		batcher.drawSprite(width/2, height/2, PonyMath.getRatio(width, 512), PonyMath.getRatio(width, 419), Assets.pauseMenuRegion);
		
		batcher.endBatch();
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);

	}

	private void presentRunning() {
		GL10 gl = glGraphics.getGL();
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(width - PonyMath.getRatio(width, 70), height
				- PonyMath.getRatio(width, 70), width / 9.5f, width / 9.5f,
				Assets.pauseButtonRegion);
		// Assets.font.drawText(batcher, timeString, 10, 320-20); //TODO
		// Zeitdarstellen , bzw. auch Position
		if (Settings.touchEnabled) { 
			 batcher.drawSprite(PonyMath.getRatio(width, 130+30), PonyMath.getRatio(width, 63+20), 260, 126, Assets.steeringRegion);
			 batcher.drawSprite(width-PonyMath.getRatio(width, 130+30), PonyMath.getRatio(width, 63+20), 260, 126, Assets.accelRegion);
		}

		batcher.endBatch();
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);

	}

	@Override
	public void pause() {
		state = GAME_PAUSED;

	}

	@Override
	public void resume() {
		Assets.gameScreenReload();

	}

	@Override
	public void dispose() {
		

	}
	
	
	 public static GameScreen getInstance(Game game, int ... selectedCars) {
	        if (instance == null) {
	            instance = new GameScreen(game, selectedCars);
	        }
	        return instance;
	    }
	 
	 public static GameScreen getInstance() {
	        if (instance == null) {
	            instance = new GameScreen(null);
	        }
	        return instance;
	    }
	 
	 public void clear()  
	  {  
	    instance = null;  
	  }  

}
