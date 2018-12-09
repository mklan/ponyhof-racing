package de.ponyhofgang.ponyhofgame.framework.math;

public class Cube {
	public final Vector3 lowerLeft;
	public final Vector3 position;
	public float width, height, depth;

	public Cube(float x, float y, float z, float width, float height,
			float depth) {
		this.lowerLeft = new Vector3(x, y, z);
		this.position = new Vector3(x+width/2, y+height/2, z+depth/2);
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
}