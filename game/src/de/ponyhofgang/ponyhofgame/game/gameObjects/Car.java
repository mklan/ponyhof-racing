package de.ponyhofgang.ponyhofgame.game.gameObjects;


import de.ponyhofgang.ponyhofgame.framework.DynamicGameObject;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;
import de.ponyhofgang.ponyhofgame.game.CarSpecs;
import de.ponyhofgang.ponyhofgame.game.screens.GameScreen;

public class Car extends DynamicGameObject implements CarSpecs{

	
	public static final int DRIVING = 0;
	public static final int SLIPPING = 1;
	public static final int CRASHING = 2;
	public static final int EXPLODING = 3;
	public static final float SLIPPING_DURATION = 5;

	public boolean finished = false;

	public float angle = 0;
	public float pitch = 0;
	public float radians;
	
	public int carType;

	public int state;
	public float colliderDirectionAngle;
	public float slippingStartTime;
	
	public Car(float x, float y, float angle, float width, float length, int chosenCar) {
	super(x, y, angle, width, length);	
	
	carType = chosenCar;

	}

	public void update(float deltaTime, float angle,
			int accelerationState) {

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
		
		
		if(state == CRASHING && OverlapTester.direction){  // fährt an eine Seite des Richtungsvektiors zu  
			
			if((colliderDirectionAngle - pitch < 120 && colliderDirectionAngle - pitch > 60) || (colliderDirectionAngle - pitch < -60 && colliderDirectionAngle - pitch > -120)){
			
				if (velocity.x > 0)position.sub(0.1f * bounds.direction.x  , 0.1f  * bounds.direction.y );  
				else position.add(0.1f * bounds.direction.x  , 0.1f  * bounds.direction.y ); // Rückwärtsfahren
			
			}else{
				
				if( pitch > colliderDirectionAngle-90 && pitch < colliderDirectionAngle + 90 ) pitch = colliderDirectionAngle;   
				else pitch = colliderDirectionAngle-180;
				
			}
			

			
		}
		
		
        if(state == CRASHING && !OverlapTester.direction){   // fährt auf das Lot der Colliders zu    
			
			
        	if((colliderDirectionAngle+90 - pitch < 120 && colliderDirectionAngle+90 - pitch > 60) || (colliderDirectionAngle+90 - pitch < -60 && colliderDirectionAngle+90 - pitch > -120)){
    			
        	
        		if (velocity.x > 0)position.sub(0.1f * bounds.direction.x  , 0.1f  * bounds.direction.y );  
				else position.add(0.1f * bounds.direction.x  , 0.1f  * bounds.direction.y ); // Rückwärtsfahren
			
			}else{
        		
        		
        	if( pitch > colliderDirectionAngle-180 && pitch < colliderDirectionAngle) pitch = colliderDirectionAngle - 90;
			else pitch = colliderDirectionAngle + 90;
			
		}
        	
        }
		

       // Log.d("angle", ""+pitch);
		
		
		bounds.direction.set((float) Math.cos(pitch *  Vector2.TO_RADIANS), (float) Math.sin(pitch * Vector2.TO_RADIANS))
				.nor();
		bounds.lotDirection.set((float) Math.cos((pitch + 90) * Vector2.TO_RADIANS),
				(float) Math.sin((pitch + 90) * Vector2.TO_RADIANS)).nor();

		

		
	

		if (state == DRIVING ) {

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

			position.add(velocity.x * bounds.direction.x * deltaTime, velocity.y
					* bounds.direction.y * deltaTime);
			
			

		} 
		
		
		
		if(state == CRASHING) velocity.set(velocity.mul(0.3f));  // Abbremsen, wenn gegen die Wand gefahren wird (um Faktor 0.3)

		
		
		 
		 
		if(state == SLIPPING)velocity.set(0,0);
			
		

		 moveCollider(position);

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
