package de.ponyhofgang.ponyhofgame.game.gameObjects;

import java.util.Random;

import de.ponyhofgang.ponyhofgame.framework.GameObject;

public class Gadget extends GameObject{

	public int content;
	public boolean active = true;
	public float lastTimeCollected = -1;

	public Gadget(float x, float y, float angle, float width, float length) {
		super(x, y, angle, width, length);
		generateNewContent();
		
		
		
		
	}
	
	
	
	
	
	
	public void generateNewContent(){
		
		Random r = new Random();
		content=r.nextInt(2);
	}

}
