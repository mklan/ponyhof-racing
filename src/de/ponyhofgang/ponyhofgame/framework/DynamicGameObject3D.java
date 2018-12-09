package de.ponyhofgang.ponyhofgame.framework;

import de.ponyhofgang.ponyhofgame.framework.math.Vector3;

public class DynamicGameObject3D extends GameObject3D {
	public final Vector3 velocity;
	public final Vector3 accel;

	public DynamicGameObject3D(float x, float y, float z, float width, float height) {
		super(x, y, z, width, height);
		velocity = new Vector3();
		accel = new Vector3();
	}
}