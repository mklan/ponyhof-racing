package de.ponyhofgang.ponyhofgame.framework.test;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import de.ponyhofgang.ponyhofgame.framework.Game;
import de.ponyhofgang.ponyhofgame.framework.Input.TouchEvent;
import de.ponyhofgang.ponyhofgame.framework.gl.Camera2D;
import de.ponyhofgang.ponyhofgame.framework.gl.FPSCounter;
import de.ponyhofgang.ponyhofgame.framework.gl.SpriteBatcher;
import de.ponyhofgang.ponyhofgame.framework.impl.GLScreen;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.framework.math.PonyMath;
import de.ponyhofgang.ponyhofgame.framework.math.Rectangle;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;
import de.ponyhofgang.ponyhofgame.framework.test.World.WorldListener;

public class GameScreen extends GLScreen {
	static final int GAME_RUNNING = 0;
	static final int GAME_PAUSED = 1;
	static final int GAME_OVER = 2;

	int state;
	Camera2D guiCam;
	Vector2 touchPoint;

	SpriteBatcher batcher;

	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle leftBounds;
	Rectangle rightBounds;
	Rectangle accBounds;
	Rectangle breakBounds;

	Rectangle resumeBounds;
	Rectangle quitBounds;

	Rectangle leftSideBounds;
	Rectangle rightSideBounds;

	String lastTime;
	String timeString;
	FPSCounter fpsCounter;

	int height;
	int width;

	float angle = 0;
	float translate = 0;

	public GameScreen(Game game, int height, int width) {
		super(game);

		this.height = height;
		this.width = width;

		state = GAME_RUNNING;
		guiCam = new Camera2D(glGraphics, width, height);
		batcher = new SpriteBatcher(glGraphics, 10);
		touchPoint = new Vector2();

		world = new World();

		worldListener = new WorldListener() {
			// TODO Auto-generated method stub
			public void explosion() {
				// Assets.playSound(Assets.shotSound);

			}

			public void shot() {
				// Assets.playSound(Assets.shotSound);

			}

		};

		world.setWorldListener(worldListener);
		renderer = new WorldRenderer(glGraphics);

		pauseBounds = new Rectangle(width - PonyMath.getRatio(width, 70)
				- PonyMath.getRatio(width, 70) / 2, height
				- PonyMath.getRatio(width, 70) - PonyMath.getRatio(width, 70)
				/ 2, width / 9.5f, width / 9.5f);

		resumeBounds = new Rectangle(0, 0, 0, 0); // TODO
		quitBounds = new Rectangle(0, 0, 0, 0); // TODO

		leftBounds = new Rectangle(0, 0, 0, 0); // TODO
		rightBounds = new Rectangle(0, 0, 0, 0); // TODO

		// wenn Touch deaktiviert wurde, gibt man mti der linken Seite Gas und
		// mit der rechten bremst man
		leftSideBounds = new Rectangle(0, 0, width / 2, height);
		rightSideBounds = new Rectangle(width / 2, 0, width / 2, height);

		// lastScore = 0;
		// scoreString = "lives:" + lastLives + " waves:" + lastWaves +
		// " score:"
		// + lastScore;
		fpsCounter = new FPSCounter();
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
				game.setScreen(new MainMenuScreen(game, height, width));
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
		
		
		world.update(deltaTime, calculateInputAcceleration(),
				calculateInputRotation());
		

	}

	private float calculateInputRotation() {   // Hier Lenkung abfangen
		float angle = 0;
		if (Settings.touchEnabled) {
			for (int i = 0; i < 2; i++) {
				if (game.getInput().isTouchDown(i)) {
					guiCam.touchToWorld(touchPoint.set(game.getInput()
							.getTouchX(i), game.getInput().getTouchY(i)));
					if (OverlapTester.pointInRectangle(leftBounds, touchPoint)) {
						angle = 5; // TODO hier den EInlenkwinkel einstellen
					}
					if (OverlapTester.pointInRectangle(rightBounds, touchPoint)) {
						angle = -5;
					}
				}
			}
		} else {
			if (game.getInput().getAccelY() < 0){
				angle = 0.5f;	
			}else{
				angle = -0.5f;	
			}
			
			
													// deaktiviert wurde
		}
		return angle;
	}

	private float calculateInputAcceleration() { // Hier Steuerung abfangen
		float accelX = 0;
		if (Settings.touchEnabled) {
			for (int i = 0; i < 2; i++) {
				if (game.getInput().isTouchDown(i)) {
					guiCam.touchToWorld(touchPoint.set(game.getInput()
							.getTouchX(i), game.getInput().getTouchY(i)));
					if (OverlapTester.pointInRectangle(breakBounds, touchPoint)) {
						accelX = -Gengar.GENGAR_VELOCITY / 5;
					}
					if (OverlapTester.pointInRectangle(accBounds, touchPoint)) {
						accelX = Gengar.GENGAR_VELOCITY / 5;
					}
				}
			}
		} else {
			for (int i = 0; i < 2; i++) {
				if (game.getInput().isTouchDown(i)) {
					guiCam.touchToWorld(touchPoint.set(game.getInput()
							.getTouchX(i), game.getInput().getTouchY(i)));
					if (OverlapTester.pointInRectangle(leftSideBounds,
							touchPoint)) {
						accelX = Gengar.GENGAR_VELOCITY / 5;
						
						
						
						//world.gengar2.position.add(world.gengar2.getDirection().mul(accelX/30));
						
						
					}
					if (OverlapTester.pointInRectangle(rightSideBounds,
							touchPoint)) {
						accelX = -Gengar.GENGAR_VELOCITY / 5;
						
						
						
						//world.gengar2.position.add(world.gengar2.getDirection().mul(accelX/30));
					}
				}
			}
		
		}
		return accelX;
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
				game.setScreen(new MainMenuScreen(game, height, width));
			}
		}

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
		batcher.beginBatch(Assets.items);
		// batcher.drawSprite(240, 160, 160, 64, Assets.pauseRegion); //TODO
		// Pause Menü ( wohl transparetner Kasten mit button Quit und Resume
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
		// Zeitdarstellen , bzw. später auch Poistion
		if (Settings.touchEnabled) { // TODO on Screen Buttons
			// batcher.drawSprite(32, 32, 64, 64, Assets.leftRegion);
			// batcher.drawSprite(96, 32, 64, 64, Assets.rightRegion);
			// batcher.drawSprite(480 - 40, 32, 64, 64, Assets.accRegion);
			// batcher.drawSprite(480 - 40, 32, 64, 64, Assets.breakRegion);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
