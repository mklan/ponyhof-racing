package de.ponyhofgang.ponyhofgame.framework.math;

public class Cube {
	public final Vector3 lowerLeft;
	public float width, height, depth;

	public Cube(float x, float y, float z, float width, float height,
			float depth) {
		this.lowerLeft = new Vector3(x, y, z);
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
}