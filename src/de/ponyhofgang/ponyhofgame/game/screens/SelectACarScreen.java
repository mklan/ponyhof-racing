package de.ponyhofgang.ponyhofgame.game.screens;

import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;

import de.ponyhofgang.ponyhofgame.framework.Game;
import de.ponyhofgang.ponyhofgame.framework.Input.TouchEvent;
import de.ponyhofgang.ponyhofgame.framework.Sound;
import de.ponyhofgang.ponyhofgame.framework.gl.Camera2D;
import de.ponyhofgang.ponyhofgame.framework.gl.SpriteBatcher;
import de.ponyhofgang.ponyhofgame.framework.impl.GLScreen;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.framework.math.PonyMath;
import de.ponyhofgang.ponyhofgame.framework.math.Rectangle;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;
import de.ponyhofgang.ponyhofgame.game.Assets;
import de.ponyhofgang.ponyhofgame.game.CarSpecs;
import de.ponyhofgang.ponyhofgame.game.ShowCaseRenderer;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Car;

public class SelectACarScreen extends GLScreen implements CarSpecs {

	private Camera2D guiCam;
	private SpriteBatcher batcher;
	private Vector2 touchPoint;
	private Rectangle backButtonBounds, nextButtonBounds;

	private ShowCaseRenderer renderer;

	private int height;
	private int width;

	public boolean pressedBackKey = false;

	public int selectedCar = MYSTERYMACHINE;
	public int selectedCar0 = -1;
	public int selectedCar1 = -1;
	public int selectedCar2 = -1;
	public int selectedCar3 = -1;

	public ArrayList<Integer> cars;

	private int down_x = -1;
	private int swipe;

	private static SelectACarScreen instance = null;
	private MainMenuScreen mainMenuScreen;
	private boolean multiplayer;

	public Sound sound = null;
	private boolean paused;

	private SelectACarScreen(Game game, boolean multiplayer) {
		super(game);

		mainMenuScreen = MainMenuScreen.getInstance();

		height = mainMenuScreen.height;
		width = mainMenuScreen.width;

		this.multiplayer = multiplayer;

		cars = new ArrayList<Integer>();

		for (int i = 0; i < mainMenuScreen.game.playerCount; i++) {

			cars.add(null);

		}

		renderer = new ShowCaseRenderer(glGraphics);

		guiCam = new Camera2D(glGraphics, width, height);
		batcher = new SpriteBatcher(glGraphics, 10);
		touchPoint = new Vector2();
		backButtonBounds = new Rectangle(PonyMath.getRatio(width, 100)
				- PonyMath.getRatio(width, 132 / 2), PonyMath.getRatio(width,
				100) - PonyMath.getRatio(width, 132 / 2), PonyMath.getRatio(
				width, 132), PonyMath.getRatio(width, 132));
		nextButtonBounds = new Rectangle(width / 2
				- PonyMath.getRatio(width, 254), PonyMath.getRatio(width, 182),
				PonyMath.getRatio(width, 508), PonyMath.getRatio(width, 166));
	}

	@Override
	public void update(float deltaTime) {

		

		selectedCar = Math.round(ShowCaseRenderer.rotationCamera);
		// ermittelt
		// anhand
		// der
		// Rotation
		// das
		// Auto
		
		playThemeSong(Math.round(ShowCaseRenderer.rotationCamera));

		List<TouchEvent> events = game.getInput().getTouchEvents();

		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);

			if (event.type == TouchEvent.TOUCH_DOWN) {

				down_x = event.x;

			}

			if (event.type != TouchEvent.TOUCH_UP)
				continue;

			if (down_x >= 0) {

				if ((down_x - event.x) > 50) {
					swipe = ShowCaseRenderer.SWIPE_LEFT;
					ShowCaseRenderer.rotationCamera -= 1.5;
				}
				if ((down_x - event.x) < -50) {
					swipe = ShowCaseRenderer.SWIPE_RIGHT;
					ShowCaseRenderer.rotationCamera += 1.5;
				}

				down_x = -1;

			}

			guiCam.touchToWorld(touchPoint.set(event.x, event.y));

			if (OverlapTester.pointInRectangle(backButtonBounds, touchPoint)) {
				pressedBackKey = false;
				Assets.playSound(Assets.clickSound);
				if (sound != null)
					sound.stop();
			    if (Assets.breakingBadTheme.isPlaying()) Assets.breakingBadTheme.stop();
			    if (Assets.ghostbusterTheme.isPlaying()) Assets.ghostbusterTheme.stop();
				ShowCaseRenderer.rotationCamera = 0;
				game.setScreen(MainMenuScreen.getInstance());
				clear();

			}

			if (ShowCaseRenderer.rotationCamera % 90 == 0) { // Man darf nur
																// weiter
																// klicken, wenn
																// das Auto
																// zuende
																// Rotiert ist

				if (OverlapTester
						.pointInRectangle(nextButtonBounds, touchPoint)) {

					cars.set(mainMenuScreen.game.ownId, selectedCar);
					if (multiplayer)
						mainMenuScreen.game.sendStringCommands(
								selectedCar + "", "car"); // Die auswahl des
															// Autos wird an
															// andere Spieler
															// geschickt
					Assets.playSound(Assets.clickSound);

					if (mainMenuScreen.game.ownId == 0) { // Nur der Host darf
															// eine Map
						if (sound != null)
							sound.stop(); // auswählen
						if (Assets.breakingBadTheme.isPlaying()) Assets.breakingBadTheme.stop();
						 if (Assets.ghostbusterTheme.isPlaying()) Assets.ghostbusterTheme.stop();

						if (multiplayer)
							game.setScreen(SelectAMapScreen.getInstance(game,
									true));
						if (!multiplayer)
							game.setScreen(SelectAMapScreen.getInstance(game,
									false));

					}

				}
			} else {

				if (sound != null)
					sound.stop();
				  if (Assets.breakingBadTheme.isPlaying()) Assets.breakingBadTheme.stop();
				  if (Assets.ghostbusterTheme.isPlaying()) Assets.ghostbusterTheme.stop();

			}
		}

		if (pressedBackKey) {

			pressedBackKey = false;
			Assets.playSound(Assets.clickSound);
			if (sound != null)
				sound.stop();

			ShowCaseRenderer.rotationCamera = 0;
			game.setScreen(MainMenuScreen.getInstance());
			clear();

		}

	}

	private void playThemeSong(int selectedCar) {

		switch (selectedCar) {

		case ECTOMOBILE:


			if ( sound == null || !Assets.ghostbusterTheme.isPlaying())
			   Assets.ghostbusterTheme.play();


			break;
		case BATMOBILE:

			if (sound == null || !sound.equals(Assets.batmanTheme)
					|| !Assets.batmanTheme.isPlaying())
				sound = Assets.playloopedSound(Assets.batmanTheme, 1);

			break;
		case MYSTERYMACHINE:

			if (sound == null || !sound.equals(Assets.scoobyTheme)
					|| !Assets.scoobyTheme.isPlaying())
				sound = Assets.playloopedSound(Assets.scoobyTheme, 1);

			break;

		case CRYSTALSHIP:

			if ( sound == null || !Assets.breakingBadTheme.isPlaying())
			   Assets.breakingBadTheme.play();

			break;
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

		renderer.render(swipe, deltaTime);

		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(PonyMath.getRatio(width, 100),
				PonyMath.getRatio(width, 100), PonyMath.getRatio(width, 132),
				PonyMath.getRatio(width, 132), Assets.backButtonRegion);
		batcher.endBatch();

		batcher.beginBatch(Assets.background);
		batcher.drawSprite(width / 2, height - PonyMath.getRatio(width, 54),
				PonyMath.getRatio(width, 512), PonyMath.getRatio(width, 105),
				Assets.chooseACarTextRegion);
		batcher.drawSprite(width / 2, PonyMath.getRatio(width, 37),
				PonyMath.getRatio(width, 512), PonyMath.getRatio(width, 105),
				Assets.chooseACarBackgroundRegion);

		switch (selectedCar) {

		case ECTOMOBILE:

			batcher.drawSprite(width / 2, PonyMath.getRatio(width, 37),
					PonyMath.getRatio(width, 512),
					PonyMath.getRatio(width, 27), Assets.ectoMobileRegion);

			break;
		case BATMOBILE:

			batcher.drawSprite(width / 2, PonyMath.getRatio(width, 37),
					PonyMath.getRatio(width, 512),
					PonyMath.getRatio(width, 27), Assets.batMobileRegion);

			break;
		case MYSTERYMACHINE:

			batcher.drawSprite(width / 2, PonyMath.getRatio(width, 37),
					PonyMath.getRatio(width, 512),
					PonyMath.getRatio(width, 27), Assets.mysteryMachineRegion);

			break;

		case CRYSTALSHIP:

			batcher.drawSprite(width / 2, PonyMath.getRatio(width, 37),
					PonyMath.getRatio(width, 512),
					PonyMath.getRatio(width, 27), Assets.krystalShipRegion);

		}

		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);

	}

	@Override
	public void pause() {

		paused = true;

	}

	@Override
	public void resume() {

		if (paused) {

			paused = false;

			Assets.SelectACarScreenReload();
			Assets.reload();
		}
	}

	@Override
	public void dispose() {
	}

	public static SelectACarScreen getInstance(Game game, boolean multiplayer) {
		if (instance == null) {
			instance = new SelectACarScreen(game, multiplayer);
		}
		return instance;
	}

	public static SelectACarScreen getInstance() {
		if (instance == null) {
			return null;
		}
		return instance;
	}

	public void clear() {
		instance = null;
	}

}
