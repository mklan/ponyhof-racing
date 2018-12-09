package de.ponyhofgang.ponyhofgame.framework.impl;

import de.ponyhofgang.ponyhofgame.framework.Game;
import de.ponyhofgang.ponyhofgame.framework.Screen;

public abstract class GLScreen extends Screen {
	protected final GLGraphics glGraphics;
	protected final GLGame glGame;

	public GLScreen(Game game) {
		super(game);
		glGame = (GLGame) game;
		glGraphics = ((GLGame) game).getGLGraphics();
	}
}