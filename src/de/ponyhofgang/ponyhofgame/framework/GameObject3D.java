package de.ponyhofgang.ponyhofgame.framework;


import de.ponyhofgang.ponyhofgame.framework.math.Rectangle;

import de.ponyhofgang.ponyhofgame.framework.math.Vector3;

public class GameObject3D {
	public final Vector3 position;
	public final Rectangle bounds;

	public GameObject3D(float x, float y, float z, float width, float height) {
		this.position = new Vector3(x, y, z);
		this.bounds = new Rectangle(x - width / 2, y - height / 2,
				width, height);
		//this.bounds = new Rectangle(x-width/2, y-depth/2, width, depth);   // width soll der Radius sein
		
	}
}