package de.ponyhofgang.ponyhofgame.framework;

public interface Sound {
	public void play(float volume);

	public void dispose();

	public void pitch(float f);
}