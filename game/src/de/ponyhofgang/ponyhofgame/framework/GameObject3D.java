package de.ponyhofgang.ponyhofgame.framework;

import de.ponyhofgang.ponyhofgame.framework.math.Cube;
import de.ponyhofgang.ponyhofgame.framework.math.Sphere;
import de.ponyhofgang.ponyhofgame.framework.math.Vector3;

public class GameObject3D {
	public final Vector3 position;
	public final Cube bounds;

	public GameObject3D(float x, float y, float z, float width, float height,
			float depth) {
		this.position = new Vector3(x, y, z);
		this.bounds = new Cube(x - width / 2, y - height / 2, z - depth / 2,
				width, height, depth);
		//this.bounds = new Sphere(x, y, z, width);   // width soll der Radius sein
		
	}
}