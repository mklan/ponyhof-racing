package de.ponyhofgang.ponyhofgame.game.gameObjects;


import de.ponyhofgang.ponyhofgame.framework.DynamicGameObject;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;
import de.ponyhofgang.ponyhofgame.game.CarSpecs;
import de.ponyhofgang.ponyhofgame.game.screens.GameScreen;

public class Car extends DynamicGameObject implements CarSpecs{

	

	public boolean finished = false;

	public float angle = 0;
	public float pitch = 0;
	public float radians;
	
	public int carType;

	public Car(float x, float y, float angle, float width, float length, int chosenCar) {
	super(x, y, angle, width, length);	
	
	carType = chosenCar;

	}

	public void update(float deltaTime, float angle, boolean inCollision,
			int accelerationState, float colliderDirectionAngle) {

		this.angle = angle;
		
		if ((velocity.x > -0.01 && velocity.x < 0.01)) {
			//this.angle = 0;
			velocity.set(0, 0);
		//} else {
			
		}

		if ((velocity.x < 0 || velocity.y < 0)) {
			this.angle = -angle;
		} // damit man beim rückwärtsfahren in die gegengesetzte richtung lenkt
		
		
		pitch = rotate(this.angle);
		
		
		if(inCollision && OverlapTester.direction){  // fährt an eine Seite des Richtungsvektiors zu  
			
			if((colliderDirectionAngle - pitch < 120 && colliderDirectionAngle - pitch > 60) || (colliderDirectionAngle - pitch < -60 && colliderDirectionAngle - pitch > -120)){
			
				if (velocity.x > 0)position.sub(0.05f * direction.x  , 0.05f  * direction.y );  
				else position.add(0.05f * direction.x  , 0.05f  * direction.y ); // Rückwärtsfahreng
			
			}else{
				
				if( pitch > colliderDirectionAngle-90 && pitch < colliderDirectionAngle + 90 ) pitch = colliderDirectionAngle;   
				else pitch = colliderDirectionAngle-180;
				
			}
			

			
		}
		
		
        if(inCollision && !OverlapTester.direction){   // fährt auf das Lot der Colliders zu    
			
			
        	if((colliderDirectionAngle+90 - pitch < 120 && colliderDirectionAngle+90 - pitch > 60) || (colliderDirectionAngle+90 - pitch < -60 && colliderDirectionAngle+90 - pitch > -120)){
    			
        	
        		if (velocity.x > 0)position.sub(0.05f * direction.x  , 0.05f  * direction.y );  
				else position.add(0.05f * direction.x  , 0.05f  * direction.y ); // Rückwärtsfahreng
			
			}else{
        		
        		
        	if( pitch > colliderDirectionAngle-180 && pitch < colliderDirectionAngle) pitch = colliderDirectionAngle - 90;
			else pitch = colliderDirectionAngle + 90;
			
		}
        	
        }
		

       // Log.d("angle", ""+pitch);
		
		
		direction.set((float) Math.cos(pitch *  Vector2.TO_RADIANS), (float) Math.sin(pitch * Vector2.TO_RADIANS))
				.nor();
		lotDirection.set((float) Math.cos((pitch + 90) * Vector2.TO_RADIANS),
				(float) Math.sin((pitch + 90) * Vector2.TO_RADIANS)).nor();

		

		
	

		if (!inCollision) {

			if (accelerationState == GameScreen.ACCELERATING) {

				if (velocity.len() <= CAR_MAXVELOCITY)
					velocity.add(CAR_ACCELERATION * deltaTime, CAR_ACCELERATION
							* deltaTime);

			}

			if (accelerationState == GameScreen.BRAKING
					&& (velocity.x >= 0 || velocity.y >= 0)) {

				velocity.sub(CAR_BRAKING_DEACCELERATION * deltaTime,
						CAR_BRAKING_DEACCELERATION * deltaTime);

			}

			if (accelerationState == GameScreen.BRAKING
					&& (velocity.x < 0 || velocity.y < 0)) {

				if (velocity.len() <= CAR_MAXNEGATIVEVELOCITY)
					velocity.sub(CAR_BRAKING_DEACCELERATION * deltaTime,
							CAR_BRAKING_DEACCELERATION * deltaTime);

			}

			if (accelerationState == GameScreen.IDLING) {

				if (velocity.x > 0 || velocity.y > 0) {

					velocity.sub(CAR_IDLE_DEACCELERATION * deltaTime,
							CAR_IDLE_DEACCELERATION * deltaTime);

				}
				if (velocity.x < 0 || velocity.y < 0) {

					velocity.add(CAR_IDLE_DEACCELERATION * deltaTime,
							CAR_IDLE_DEACCELERATION * deltaTime);

				}

			}

			position.add(velocity.x * direction.x * deltaTime, velocity.y
					* direction.y * deltaTime);

		} else {
			velocity.set(velocity.mul(0.3f));  // Abbremsen, wenn gegen Wand fahren um Faktor 0.3

		}

		bounds.position.set(this.position);

		bounds.p1
				.set(bounds.position.x, bounds.position.y)
				.add(direction.x * bounds.length / 2,
						direction.y * bounds.length / 2)
				.sub(lotDirection.x * bounds.width / 2,
						lotDirection.y * bounds.width / 2);

		bounds.p2
				.set(bounds.position.x, bounds.position.y)
				.sub(direction.x * bounds.length / 2,
						direction.y * bounds.length / 2)
				.sub(lotDirection.x * bounds.width / 2,
						lotDirection.y * bounds.width / 2);

		bounds.p3
				.set(bounds.position.x, bounds.position.y)
				.sub(direction.x * bounds.length / 2,
						direction.y * bounds.length / 2)
				.add(lotDirection.x * bounds.width / 2,
						lotDirection.y * bounds.width / 2);

		bounds.p4
				.set(bounds.position.x, bounds.position.y)
				.add(direction.x * bounds.length / 2,
						direction.y * bounds.length / 2)
				.add(lotDirection.x * bounds.width / 2,
						lotDirection.y * bounds.width / 2);

		// stateTime += deltaTime;
	}

	public float rotate(float pitchInc) {

		this.pitch += pitchInc;
		if (pitch > 180)
			pitch = pitch - 360;
		if (pitch < -180)
			pitch = pitch + 360;
		

		return pitch ;
	}

	public void resetVelocity() {
		
		velocity.set(0,0);
		
	}

}
