package de.ponyhofgang.ponyhofgame.framework;

public interface Sound {
	

	public void play(float volume);
	
	public void play(float volume, int loopFlag, float pitch);

	public void dispose();

	public void pitch(float f);

	public void stop();

	public boolean isPlaying();

	public void setPitch(float f);
}