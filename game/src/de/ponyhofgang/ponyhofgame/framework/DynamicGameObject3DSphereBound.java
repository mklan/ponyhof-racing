package de.ponyhofgang.ponyhofgame.framework;

import de.ponyhofgang.ponyhofgame.framework.math.Vector3;

public class DynamicGameObject3DSphereBound extends GameObject3DSphereBound {
	public final Vector3 velocity;
	public final Vector3 accel;

	public DynamicGameObject3DSphereBound(float x, float y, float z,
			float radius) {
		super(x, y, z, radius);
		velocity = new Vector3();
		accel = new Vector3();
	}
}