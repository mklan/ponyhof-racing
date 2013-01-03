package de.ponyhofgang.ponyhofgame.game.gameObjects;

import de.ponyhofgang.ponyhofgame.framework.DynamicGameObject;




public class Rocket extends DynamicGameObject{

	public static final int DETONATING = 0;
	public static final int FLYING = 1;
	
	public int state;
	public float timeState = 0;
	public float explotionTimeState;

	public Rocket(float x, float y, float angle, float width, float length) {
		super(x, y, angle, width, length);
		
		velocity.set(8,8);  //wie schnell soll sich die Rakete bewegen?
		state = FLYING;
		
		
		
		
	}
	
	
	public void update(float deltaTime){
		
		
		
		if(state == FLYING){position.add(velocity.x * bounds.direction.x * deltaTime, velocity.y
				* bounds.direction.y * deltaTime);
		moveCollider(position);
		}
		if(state == DETONATING){
			explotionTimeState+=deltaTime;
		}
		
		timeState += deltaTime;
	}
	
	
	
	
	
	
	



}
