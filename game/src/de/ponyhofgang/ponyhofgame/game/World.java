package de.ponyhofgang.ponyhofgame.game;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;


import de.ponyhofgang.ponyhofgame.framework.math.LineRectangle;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Car;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Gadget;
import de.ponyhofgang.ponyhofgame.game.gameObjects.OilSpill;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Rocket;
import de.ponyhofgang.ponyhofgame.game.screens.MainMenuScreen;

public class World implements CarSpecs {
	
	public interface WorldListener {
		public void collision();

		public void collectedGadget(int collectedGadgetContent);

		public void droveTroughtOilSpill();

		public void detonatingRocket();

	}
	
	WorldListener listener;

	public static boolean raceStarted = false; //TODO

	public final static int LEVEL_DOCKS = 0;
	public final static int LEVEL_2  = 1;
	public final static int LEVEL_3 = 2;

	
	
	public List<LineRectangle> colliders;
	public ArrayList<Gadget> gadgets;
	public ArrayList<Car> cars;
    public ArrayList<Rocket> rockets;
    public ArrayList<OilSpill> oilSpills;

	
	public Car myCar;
	public Car car0;
	public Car car1;
	public Car car2;
	public Car car3;


	public int myId;
	private int playerCount;

	private int ticker = 0;
	public float time = 0;

	private boolean multiplayer;


	

	public World(int playerCount, int myId, int worldId,
			ArrayList<Integer> chosenCars, boolean multiplayer) { 
		
		

		this.multiplayer = multiplayer;
		
		cars = new ArrayList<Car>();
		colliders = new ArrayList<LineRectangle>();
		gadgets = new ArrayList<Gadget>();
		rockets = new ArrayList<Rocket>();
		oilSpills = new ArrayList<OilSpill>();
		
	
		
		
		//erstelle die Auto instanzen
		for (int i = 0; i < chosenCars.size(); i++) {
			
			float carWidth = 0;
			float carLength = 0;

			switch (chosenCars.get(i)) {
			case ECTOMOBILE:

				carWidth = ECTOMOBILE_WIDTH;
				carLength = ECTOMOBILE_LENGTH;

				break;
			case BATMOBILE:

				carWidth = BATMOBILE_WIDTH;
				carLength = BATMOBILE_LENGTH;

				break;
			case MYSTERYMACHINE:

				carWidth = MYSTERYMACHINE_WIDTH;
				carLength = MYSTERYMACHINE_LENGTH;

				break;

			case PODRACER:

				carWidth = PODRACER_WIDTH;
				carLength = PODRACER_LENGTH;

				break;
			}

			cars.add(new Car(0 + (i + 1), -6, 0, carWidth, carLength,
					chosenCars.get(i)));
		}

		myCar = cars.get(myId);  // identifiziere mein Auto
		this.myId = myId;
		this.playerCount = playerCount;

		switch (playerCount) {  //jenachdem wieviele leute mitspielen, werden den Autos objekte zugeordnet 
								//( Ab 2 Spieler hat man selbst 2 Instanzen!!! myCar + carX !!! )

		case 1:

			car0 = myCar;

			break;

		case 2:

			car0 = cars.get(0);
			car1 = cars.get(1);

			break;

		case 3:

			car0 = cars.get(0);
			car1 = cars.get(1);
			car2 = cars.get(2);

			break;

		case 4:

			car0 = cars.get(0);
			car1 = cars.get(1);
			car2 = cars.get(2);
			car3 = cars.get(3);

			break;

		}

		if (worldId == LEVEL_DOCKS) {

			colliders.add(new LineRectangle(-2, 4, 0, 1, 8));
			colliders.add(new LineRectangle(2, 0, 0, 1, 8));
			colliders.add(new LineRectangle(2, -4, 0, 1, 16));
			colliders.add(new LineRectangle(10.5f, 0, 90, 1, 8));
			colliders.add(new LineRectangle(7, -6.5f, 90, 1, 4));
			colliders.add(new LineRectangle(6.5f, 4, 90, 1, 8));
			colliders.add(new LineRectangle(-1.5f, -10, 90, 1, 4));
			colliders.add(new LineRectangle(-6.5f, 0, 90, 1, 8));
			colliders.add(new LineRectangle(-11, 0, 90, 1, 4));
			colliders.add(new LineRectangle(-9, -7, -45, 1, 4));
			colliders.add(new LineRectangle(0.5f, -2, 0, 19, 29));

			gadgets.add(new Gadget(11.777f, -6.036f, 0, 0.363f, 0.363f));
			gadgets.add(new Gadget(12.881f, -6.036f, 0, 0.363f, 0.363f));
			gadgets.add(new Gadget(13.986f, -6.036f, 0, 0.363f, 0.363f));
		}
		
	

		
		
		
	
	}

	public void setWorldListener(WorldListener worldListener) {
		this.listener = worldListener;
	}

	public void update(float deltaTime, int accelerationState, float angle) {  //das kommt aus den Controlls raus vom Spieler
		

		time = time + deltaTime;
		
		myCar.update(deltaTime, angle, accelerationState);
	
		
		ticker ++;
		if((multiplayer) && ticker%20 == 0){  //wenn es mehr Spieler gibt, dann sende meine Position an die anderen
	    ticker = 0;	
		MainMenuScreen.getInstance().game.sendData(myCar.position.x, myCar.position.y, myCar.pitch, "data");	
			
		}

	
		//Falls das Auto ausrutscht
		if(myCar.state == Car.SLIPPING){
		if (time - myCar.slippingStartTime > Car.SLIPPING_DURATION) myCar.state = Car.DRIVING;
		}

		//---> die Raketen werden in ihrer Update Methode vorangetrieben
		int len = rockets.size();
		for (int i = 0; i < len; i++) {
			Rocket rocket = rockets.get(i);
			rocket.update(deltaTime);
			
			if(rocket.explotionTimeState > Assets.explosionAnim.frameDuration*12) rockets.set(i, null);
			
		}
		//<---
		
		checkWorldCollisions();
		checkGadgetBoxCollisions(); // TODO keine Gadgetboxen im Timetrial.. // höchstens ggf. Speedboost

		// Die folgenden If-Abfragen dienen der Entlastung der Methdenaufrufe

		if (car1 != null)
			checkOppenentCollisions(); // Falls man mit mindestens einem Gegner
										// Spielt, soll die Colission gecheckt
		if (oilSpills.size() > 0)
			checkOilSpillCollisions(); // ist eine ÖlLache plaziert worden, soll
										// die Collision gecheckt werden
		if (rockets.size() > 0)
			checkRocketCollisions(); // ist eine Rakete abgefeuert worden, soll
										// die Collision gecheckt werden

	}

	private void checkWorldCollisions() {
		
		//Ramme ich eine Wand?

		int len = colliders.size();
		for (int i = 0; i < len; i++) {
			LineRectangle collider = colliders.get(i);
			if (OverlapTester
					.intersectSegmentRectangles(myCar.bounds, collider)) {
				myCar.colliderDirectionAngle = collider.angle;
				myCar.state = Car.CRASHING;
				listener.collision();
				
				return;
			}
		}
		
		//Nur falls er nicht im Slippingmode it, soll der DRIVING modus aktiviert werden, da dies oben geklärt wird	
	    if(myCar.state !=  Car.SLIPPING) myCar.state = Car.DRIVING;


	}

	private void checkOppenentCollisions() {
		
		//Ramme ich ein gegnerisches Auto?

	}

	private void checkGadgetBoxCollisions() {
		
		//sammel ich eine Gadgetbox ein?

		int len = gadgets.size();
		for (int i = 0; i < len; i++) {
			Gadget gadget = gadgets.get(i);

			
			if (!gadget.active) {  // ist eine Box schon eingesammelt worden, wird geguckt ob es Zeit ist eine neue zu erstellen

				if (time - gadget.lastTimeCollected > Gadget.BOX_APEARING_INTERVAL) {
					gadget.generateNewContent();
					gadget.active = true;
				}

				continue;
			}

			if (OverlapTester.intersectSegmentRectangles(myCar.bounds,
					gadget.bounds)) {
				
				if(myCar.gadgetState == Car.NO_GADGET) myCar.collectedGadgetId = i; //falls noch kein Gadget eingesammelt wurde
				
				gadget.active = false;
				gadget.lastTimeCollected = time;
				
				if (multiplayer) MainMenuScreen.getInstance().game.sendStringCommands(i+" "+time, "boxCollected"); //melde den anderen Spielern, das einsammeln
				
				
				listener.collectedGadget(gadget.content);
				return;
			}

		}

	}

	private void checkOilSpillCollisions() {

		//fahre Ich über eine Öllache?
		
		int len = oilSpills.size();
		for (int i = 0; i < len; i++) {
			OilSpill oilspill = oilSpills.get(i);

			if (oilspill != null && OverlapTester.intersectSegmentRectangles(myCar.bounds,
					oilspill.bounds)) {

				listener.droveTroughtOilSpill();
				
				oilSpills.set(i, null);
				if (multiplayer) MainMenuScreen.getInstance().game.sendStringCommands(i+" ", "slipped");
				//send Remove
				
				myCar.state = Car.SLIPPING;
				myCar.slippingStartTime = time;
				// TODO auto ggf um 360° drehen
				return;
			}

		}

	}

	private void checkRocketCollisions() {

		int colliderLen = colliders.size();
		int len = rockets.size();
		for (int i = 0; i < len; i++) {
			Rocket rocket = rockets.get(i);

			if ( rocket != null){
			// fliegt Rakete gegen eine Wand?

			
			for (int j = 0; j < colliderLen; j++) {
				LineRectangle collider = colliders.get(j);

				if (OverlapTester.intersectSegmentRectangles(
						collider, rocket.bounds)) {

					listener.detonatingRocket();
					
					rocket.state = Rocket.DETONATING;
					
					return;    //TODO hier muss man sich was einfallen lassen, 
					//weil ja mehere Rakete gleichzeitig im umlauf sind... vll nicht so tragisch, da schnelle wiederholung
					
				}

			}

			// trifft die Rakete mein Auto?
			if (OverlapTester.intersectSegmentRectangles(myCar.bounds,
					rocket.bounds)) {

				listener.detonatingRocket();
				
				rocket.state = Rocket.DETONATING;
				
				myCar.resetVelocity();
				if (multiplayer) MainMenuScreen.getInstance().game.sendStringCommands(i+" ", "hit");
				return;
			}

		}
		}

	}

	public void useGadget(int gadgetContent) {

		if (gadgetContent == Gadget.OILSPILL) {

			//Eine Öllache wird hinter dem Auto erzeugt und existiert so lange, bis jemand drüber fährt
			oilSpills.add(new OilSpill(myCar.position.x - myCar.bounds.direction.x
					* (myCar.bounds.length + 0.1f), myCar.position.y
					- myCar.bounds.direction.y * (myCar.bounds.length + 0.1f),
					myCar.pitch, 0.8f, 0.8f));

			if (multiplayer) MainMenuScreen.getInstance().game.sendData(myCar.position.x - myCar.bounds.direction.x
					* (myCar.bounds.length + 0.1f), myCar.position.y
					- myCar.bounds.direction.y * (myCar.bounds.length + 0.1f),
					myCar.pitch, "oilSpill");
			
		}

		if (gadgetContent == Gadget.ROCKET) {

			//eine Rakete wird vor dem Auto erzeugt und fliegt in der momentanen Richtung, bis Sie auf ein Hindernis stößt
		rockets.add(new Rocket(myCar.position.x + myCar.bounds.direction.x   * (myCar.bounds.length + 0.1f),
		               myCar.position.y + myCar.bounds.direction.y * (myCar.bounds.length + 0.1f),
		               myCar.pitch, 0.034f, 0.465f));
			if (multiplayer) MainMenuScreen.getInstance().game.sendData(myCar.position.x + myCar.bounds.direction.x   * (myCar.bounds.length + 0.1f),
		               myCar.position.y + myCar.bounds.direction.y * (myCar.bounds.length + 0.1f),
		               myCar.pitch, "rocket");
			
			
	

		}

	}

	public boolean isGameOver() {
		return myCar.finished == true; // wenn auto im Ziel angelangt ist
	}
	
	
	public void deactivateBox(int id, int time){
		
		Assets.playSound(Assets.collectSound);
		Gadget gadget = gadgets.get(id);
		gadget.active = false;
		gadget.lastTimeCollected = time;
	
	}
	
	
public void removeOilSplill(int id){
		
	    listener.droveTroughtOilSpill();
		oilSpills.set(id, null);
		//car.state = Car.SLIPPING;  //TODO animation bei den anderen abspielen
		//car.slippingStartTime = time;

	}


public void removeRocket(int id){
	
    listener.detonatingRocket();
	rockets.get(id).state = Rocket.DETONATING;
	//car.state = Car.SLIPPING;  //TODO animation bei den anderen abspielen
	//car.slippingStartTime = time;

}



public void createOilSplill(float x, float y, float angle){
	
	Assets.playSound(Assets.squashSound);
	oilSpills.add(new OilSpill(x, y, angle, 0.8f, 0.8f));
    

}


public void createRocket(float x, float y, float angle){
	
	Assets.playSound(Assets.launchingSound);
	rockets.add(new Rocket(x, y, angle, 0.034f, 0.465f));
    

}
	

	

}
