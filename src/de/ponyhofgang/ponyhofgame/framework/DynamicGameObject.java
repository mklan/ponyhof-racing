package de.ponyhofgang.ponyhofgame.framework;

import de.ponyhofgang.ponyhofgame.framework.math.Vector2;

public class DynamicGameObject extends GameObject {
	public final Vector2 velocity;
	public final Vector2 accel;
	

	public DynamicGameObject(float x, float y, float angle, float width, float length) {
		super(x, y, angle, width, length);
		velocity = new Vector2();
		accel = new Vector2();
		
	}
	
	
	public void moveCollider(Vector2 position){
		
		bounds.position.set(position);

		bounds.p1
				.set(bounds.position.x, bounds.position.y)
				.add(bounds.direction.x * bounds.length / 2,
						bounds.direction.y * bounds.length / 2)
				.sub(bounds.lotDirection.x * bounds.width / 2,
						bounds.lotDirection.y * bounds.width / 2);

		bounds.p2
				.set(bounds.position.x, bounds.position.y)
				.sub(bounds.direction.x * bounds.length / 2,
						bounds.direction.y * bounds.length / 2)
				.sub(bounds.lotDirection.x * bounds.width / 2,
						bounds.lotDirection.y * bounds.width / 2);

		bounds.p3
				.set(bounds.position.x, bounds.position.y)
				.sub(bounds.direction.x * bounds.length / 2,
						bounds.direction.y * bounds.length / 2)
				.add(bounds.lotDirection.x * bounds.width / 2,
						bounds.lotDirection.y * bounds.width / 2);

		bounds.p4
				.set(bounds.position.x, bounds.position.y)
				.add(bounds.direction.x * bounds.length / 2,
						bounds.direction.y * bounds.length / 2)
				.add(bounds.lotDirection.x * bounds.width / 2,
						bounds.lotDirection.y * bounds.width / 2);
	}
}