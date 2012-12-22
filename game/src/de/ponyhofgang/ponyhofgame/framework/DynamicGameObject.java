package de.ponyhofgang.ponyhofgame.framework;

import de.ponyhofgang.ponyhofgame.framework.math.Vector2;

public class DynamicGameObject extends GameObject {
	public final Vector2 velocity;
	public final Vector2 accel;
	public final Vector2 direction; 
	public final Vector2 lotDirection; 

	public DynamicGameObject(float x, float y, float angle, float width, float length) {
		super(x, y, angle, width, length);
		velocity = new Vector2();
		accel = new Vector2();
		direction = new Vector2();
		lotDirection = new Vector2();
	}
}