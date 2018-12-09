package de.ponyhofgang.ponyhofgame.framework.math;

public class Rectangle {
	public final Vector2 lowerLeft;
	public float width, height;
	public final Vector2  position;

	public Rectangle(float x, float y, float width, float height) {
		this.position = new Vector2(x+width/2, y+height/2);
		this.lowerLeft = new Vector2(x, y);
		this.width = width;
		this.height = height;
	}
}