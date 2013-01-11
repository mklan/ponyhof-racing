package de.ponyhofgang.ponyhofgame.game.gameObjects;


import de.ponyhofgang.ponyhofgame.framework.DynamicGameObject;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;
import de.ponyhofgang.ponyhofgame.game.CarSpecs;
import de.ponyhofgang.ponyhofgame.game.screens.GameScreen;

public class Car extends DynamicGameObject implements CarSpecs{

	
	//Driving States
	public static final int DRIVING = 0;
	public static final int SLIPPING = 1;
	public static final int CRASHING = 2;
	public static final int EXPLODING = 3;
	
	
	//Acceleration States
	public static final int ACCELERATING = 0;
	public static final int BRAKING = 1;
	public static final int IDLING = 2;
	
	
	//Gadget States
	public static final int GADGET_COLLECTED = 0;
	public static final int NO_GADGET = 1;
	

	public boolean finished = false;

	public float angle = 0;
	public float pitch = 0;
	public float radians;
	
	public int carType;

	public int state;
	public float colliderDirectionAngle;
	public float slippingStartTime;
	
	public int collectedGadgetId;
	public int gadgetState = NO_GADGET;
	private boolean firstSlippingTime;

	
	public Car(float x, float y, float angle, float width, float length, int chosenCar) {
	super(x, y, angle, width, length);	
	
	carType = chosenCar;

	}

	public void update(float deltaTime, float angle,
			int accelerationState) {

		this.angle = angle;
		
		if ((velocity.x > -0.01 && velocity.x < 0.01)) resetVelocity();
		
		if ((velocity.x < 0 || velocity.y < 0)) {
			this.angle = -angle;
		} // damit man beim rückwärtsfahren in die gegengesetzte richtung lenkt
		
		if ( velocity.x != 0) pitch = rotate(this.angle);  // Man kann nur lenken, wenn man auch fährt 
		
		
		if(state == CRASHING && OverlapTester.direction){  // fährt an eine Seite des Richtungsvektiors zu  
			
			//Wenn in einem fast Rechtenwinkel draufgefahren wird
			if((colliderDirectionAngle - pitch < 120 && colliderDirectionAngle - pitch > 60) || (colliderDirectionAngle - pitch < -60 && colliderDirectionAngle - pitch > -120)){
			
				if (velocity.x > 0){
					position.sub(0.1f * bounds.direction.x  , 0.1f  * bounds.direction.y );  
					resetVelocity();
				}
				else {
					position.add(0.1f * bounds.direction.x  , 0.1f  * bounds.direction.y ); // Rückwärtsfahren
					resetVelocity();
				}
			
			//Ansonsten die Fahrtrichtung dem Richtungsvektor des Colliders anpassen
			}else{
				
				if( pitch > colliderDirectionAngle-90 && pitch < colliderDirectionAngle + 90 ) pitch = colliderDirectionAngle;   
				else pitch = colliderDirectionAngle-180;
				
			}
			
		}
		
		
        if(state == CRASHING && !OverlapTester.direction){   // fährt auf das Lot der Colliders zu    
			
			
        	if((colliderDirectionAngle+90 - pitch < 120 && colliderDirectionAngle+90 - pitch > 60) || (colliderDirectionAngle+90 - pitch < -60 && colliderDirectionAngle+90 - pitch > -120)){
    			
        	
        		if (velocity.x > 0){
        			
        			position.sub(0.1f * bounds.direction.x  , 0.1f  * bounds.direction.y );  
        			resetVelocity();
        		}
				else{
					position.add(0.1f * bounds.direction.x  , 0.1f  * bounds.direction.y ); // Rückwärtsfahren
					resetVelocity();
				}
			
			}else{
        		
        		
        	if( pitch > colliderDirectionAngle-180 && pitch < colliderDirectionAngle) pitch = colliderDirectionAngle - 90;
			else pitch = colliderDirectionAngle + 90;
			
		}
        	
        }
		

    
		//setze den Richtungsvektor !!
        if(state == DRIVING || state == CRASHING ){firstSlippingTime = true;
        
		bounds.direction.set((float) Math.cos(pitch *  Vector2.TO_RADIANS), (float) Math.sin(pitch * Vector2.TO_RADIANS))
				.nor();
		bounds.lotDirection.set((float) Math.cos((pitch + 90) * Vector2.TO_RADIANS),
				(float) Math.sin((pitch + 90) * Vector2.TO_RADIANS)).nor();
        }
		

		
	//Falls du fährst, dann gib Gas, Bremse oder Rolle aus

		if (state == DRIVING ) {

			if (accelerationState == ACCELERATING) {

				if (velocity.len() <= CAR_MAXVELOCITY)
					velocity.add(CAR_ACCELERATION * deltaTime, CAR_ACCELERATION
							* deltaTime);

			}

			if (accelerationState == BRAKING
					&& (velocity.x >= 0 || velocity.y >= 0)) {

				velocity.sub(CAR_BRAKING_DEACCELERATION * deltaTime,
						CAR_BRAKING_DEACCELERATION * deltaTime);

			}

			if (accelerationState == BRAKING
					&& (velocity.x < 0 || velocity.y < 0)) {

				if (velocity.len() <= CAR_MAXNEGATIVEVELOCITY)
					velocity.sub(CAR_BRAKING_DEACCELERATION * deltaTime,
							CAR_BRAKING_DEACCELERATION * deltaTime);

			}

			if (accelerationState == IDLING) {

				if (velocity.x > 0 || velocity.y > 0) {

					velocity.sub(CAR_IDLE_DEACCELERATION * deltaTime,
							CAR_IDLE_DEACCELERATION * deltaTime);

				}
				if (velocity.x < 0 || velocity.y < 0) {

					velocity.add(CAR_IDLE_DEACCELERATION * deltaTime,
							CAR_IDLE_DEACCELERATION * deltaTime);

				}

			}
			
		
		} 
		
		
		//Setzte die neue Position des Autos fest

		position.add(velocity.x * bounds.direction.x * deltaTime, velocity.y
				* bounds.direction.y * deltaTime);
		
		
		if(state == CRASHING) velocity.set(velocity.mul(0.3f));  // Abbremsen, wenn gegen die Wand gefahren wird (um Faktor 0.3)
		if(state == SLIPPING) {
			
			if(firstSlippingTime){
				velocity.set(velocity.mul(0.3f));   // Anhalten, wenn man ausrutscht
				firstSlippingTime = false;
			}
		}
			
		

		 moveCollider(position); // bewege den Collider mit dem Wagen immer mit :)

	}

	public float rotate(float pitchInc) {  //Hier wird der neue Winkel zum alten hin zu addiert und ggf. wieder resettet, damit kein Überlauf entsteht

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
