package de.ponyhofgang.ponyhofgame.framework.test;

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

public class SettingsScreen extends GLScreen {
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

	public SettingsScreen(Game game, int height, int width) {
		super(game);

		this.height = height;
		this.width = width;

		guiCam = new Camera2D(glGraphics, width, height);
		batcher = new SpriteBatcher(glGraphics, 10);
		touchPoint = new Vector2();
		musicCheckboxBounds = new Rectangle((width/2)+(width/2.5f/5.2244897959183f), height/2-height/8+width/2.5f/5.6888888888888888888888888888889f, width/2.5f/3.6056338028169014084507042253521f, width/2.5f/3.6056338028169014084507042253521f);
		sfxCheckboxBounds = new Rectangle((width/2)+(width/2.5f/5.2244897959183f), height/2-height/8-width/2.5f/7.4202898550724637681159420289855f, width/2.5f/3.6056338028169014084507042253521f, width/2.5f/3.6056338028169014084507042253521f);
		arrowLeftBounds = new Rectangle((width/2)-(width/2.5f/2.106995884773662551440329218107f), height/2-height/8-width/2.5f/2.2850877192982456140350877192982f,  width/2.5f/7.2112676056338028169014084507042f,  width/2.5f/3.6056338028169014084507042253521f);
		arrowRightBounds = new Rectangle((width/2)+(width/2.5f/3.0295857988165680473372781065089f), height/2-height/8-width/2.5f/2.2850877192982456140350877192982f,  width/2.5f/7.2112676056338028169014084507042f,  width/2.5f/3.6056338028169014084507042253521f);
		backButtonBounds = new Rectangle(PonyMath.getRatio(width, 100)-PonyMath.getRatio(width, 132/2), PonyMath.getRatio(width, 100)-PonyMath.getRatio(width, 132/2), PonyMath.getRatio(width, 132), PonyMath.getRatio(width, 132));

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
			if (OverlapTester.pointInRectangle(musicCheckboxBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				 Settings.musicEnabled = !Settings.musicEnabled;
//				if (Settings.musicEnabled) {
//					Assets.music.play();
//				} else {
//					Assets.music.pause();
//				}
				 Settings.save(game.getFileIO());
			}
			if (OverlapTester.pointInRectangle(sfxCheckboxBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				 Settings.sfxEnabled = !Settings.sfxEnabled ;
				 Settings.save(game.getFileIO());
			}
			if (OverlapTester.pointInRectangle(arrowLeftBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				Settings.touchEnabled = !Settings.touchEnabled;
				Settings.save(game.getFileIO());

			}
			if (OverlapTester.pointInRectangle(arrowRightBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				Settings.touchEnabled = !Settings.touchEnabled;
				Settings.save(game.getFileIO());

			}
			if (OverlapTester.pointInRectangle(backButtonBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game, height, width));
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
		batcher.drawSprite(width / 2, height / 2, width, height,
				Assets.backgroundRegion);
		batcher.endBatch();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(width/2, height/2-height/8, width/2.5f, width/2.5f, Assets.settingsRegion);
		batcher.drawSprite(PonyMath.getRatio(width, 100), PonyMath.getRatio(width, 100), PonyMath.getRatio(width, 132), PonyMath.getRatio(width, 132), Assets.backButtonRegion);
//		
		if(Settings.musicEnabled ){
			batcher.drawSprite(width/2+PonyMath.getRatio(width, 169), height/2-height/8+PonyMath.getRatio(width, 163), PonyMath.getRatio(width, 55), PonyMath.getRatio(width, 55), Assets.soundCrossRegion);	
		}
		
		if(Settings.sfxEnabled ){
			batcher.drawSprite(width/2+PonyMath.getRatio(width, 169), height/2-height/8, PonyMath.getRatio(width, 55), PonyMath.getRatio(width, 55), Assets.soundCrossRegion);	
		}
		
		if(Settings.touchEnabled ){
			batcher.drawSprite(width/2, height/2-height/8-PonyMath.getRatio(width, 158), PonyMath.getRatio(width, 512), PonyMath.getRatio(width, 162), Assets.touchSettingRegion);	

		}
		
		if(!Settings.touchEnabled ){
			batcher.drawSprite(width/2, height/2-height/8-PonyMath.getRatio(width, 158), PonyMath.getRatio(width, 512), PonyMath.getRatio(width, 162), Assets.accelSettingRegion);	
		}
	
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
}
