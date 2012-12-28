package de.ponyhofgang.ponyhofgame.game.gameObjects;

import de.ponyhofgang.ponyhofgame.framework.DynamicGameObject;

public class Rocket extends DynamicGameObject{

	

	public Rocket(float x, float y, float angle, float width, float length) {
		super(x, y, angle, width, length);
		
		velocity.set(8,8);  //wie schnell soll sich die Rakete bewegen?
		
		
		
		
		
	}
	
	
	public void update(float deltaTime){
		
		
		
		position.add(velocity.x * bounds.direction.x * deltaTime, velocity.y
				* bounds.direction.y * deltaTime);
		
		
		bounds.position.set(this.position);
		
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
