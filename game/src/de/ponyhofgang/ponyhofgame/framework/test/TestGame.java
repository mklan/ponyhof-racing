package de.ponyhofgang.ponyhofgame.framework.test;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import de.ponyhofgang.ponyhofgame.framework.Screen;
import de.ponyhofgang.ponyhofgame.framework.impl.GLGame;

public class TestGame extends GLGame {
	boolean firstTimeCreate = true;

	public Screen getStartScreen() {
		return new MainMenuScreen(this, super.getHeight(), super.getWidth());
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate) {
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
		// if (Settings.musicEnabled)
		 //Assets.music.pause();
	}

}
