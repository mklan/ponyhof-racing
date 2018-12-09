package de.ponyhofgang.ponyhofgame.game.gameObjects;

import de.ponyhofgang.ponyhofgame.framework.math.Line;

public class Checkpoint extends Line{

	public int type;
	
	public static final int X = 1;
	public static final int MINUS_X = 2;	
	public static final int Y = 3;
	public static final int MINUS_Y = 4;	
	
	public Checkpoint(float x, float y, float angle, float width, float height, int type) {
		super(x, y, angle, width, height);
		this.type = type;
		// TODO Auto-generated constructor stub  
	}

}
