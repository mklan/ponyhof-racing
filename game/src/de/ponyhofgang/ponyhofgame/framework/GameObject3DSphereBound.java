package de.ponyhofgang.ponyhofgame.framework;

import de.ponyhofgang.ponyhofgame.framework.math.Sphere;
import de.ponyhofgang.ponyhofgame.framework.math.Vector3;

public class GameObject3DSphereBound {
	public final Vector3 position;
	public final Sphere bounds;

	public GameObject3DSphereBound(float x, float y, float z, float radius) {
		this.position = new Vector3(x, y, z);
		this.bounds = new Sphere(x, y, z, radius);
	}
}