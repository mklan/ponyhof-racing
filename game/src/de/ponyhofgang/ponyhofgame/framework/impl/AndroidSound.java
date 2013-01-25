package de.ponyhofgang.ponyhofgame.framework.impl;

import android.media.SoundPool;
import de.ponyhofgang.ponyhofgame.framework.Sound;

public class AndroidSound implements Sound {
	int soundId;
	int streamId;
	SoundPool soundPool;
	float currentPitch = 1;
	boolean isPlaying = false;

	public AndroidSound(SoundPool soundPool, int soundId) {
		this.soundId = soundId;
		this.soundPool = soundPool;
	}

	
	public void play(float volume) {
		streamId = soundPool.play(soundId, volume, volume, 0, 0, 1);
		isPlaying = true;
		
	}
	
	public void stop() {
		soundPool.stop(streamId);
		isPlaying = false;
	}
	
	public void play(float volume, int loopFlag, float pitch) {
		streamId = soundPool.play(soundId, volume, volume, 0, loopFlag, pitch);
		isPlaying = true;
	}
	
	
	public void pitch(float factor) {
		currentPitch += factor;
		soundPool.setRate ((int) soundId, currentPitch);
	}
	
	public void setPitch(float pitch) {
		
		soundPool.setRate (streamId, pitch);
	}
	
	public boolean isPlaying(){
		
		return isPlaying;
	}

	
	public void dispose() {
		soundPool.unload(soundId);
	}
}
