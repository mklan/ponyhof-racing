package de.ponyhofgang.ponyhofgame.framework;

import de.ponyhofgang.ponyhofgame.framework.math.LineRectangle;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;

public class GameObject {
	public final Vector2 position;
	public final LineRectangle bounds;
	

	public GameObject(float x, float y, float angle, float width, float length) {
		this.position = new Vector2(x, y);
		this.bounds = new LineRectangle(x, y, angle, width, length);
	}

	
}
