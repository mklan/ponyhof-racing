package de.ponyhofgang.ponyhofgame.game;

import java.util.ArrayList;
import java.util.List;

import de.ponyhofgang.ponyhofgame.framework.math.LineRectangle;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Car;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Gadget;
import de.ponyhofgang.ponyhofgame.game.gameObjects.OilSpill;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Rocket;
import de.ponyhofgang.ponyhofgame.game.screens.GameScreen;

public class World implements CarSpecs {
	public interface WorldListener {
		public void collision();

		public void collectedGadget(int collectedGadgetContent);

		public void droveTroughtOilSpill();

		public void detonatingRocket();

	}

	public static boolean raceStarted = false;

	public final static int LEVEL_DOCKS = 0;

	private static final float BOX_APEARING_INTERVAL = 5;

	public final List<LineRectangle> colliders;
	public final ArrayList<Gadget> gadgets;
	public ArrayList<Car> cars;

	WorldListener listener;

	boolean inCollision = false;

	public float colliderDirectionAngle;

	public Car myCar;
	public Car car0;
	public Car car1;
	public Car car2;
	public Car car3;

	private float carLength, carWidth;

	protected int collectedGadgetId;

	private int collectedGadgetContent;

	

	public ArrayList<Rocket> rockets;

	public ArrayList<OilSpill> oilSpills;

	public float time = 0;

	

	public World(int playerCount, int myId, int worldId,
			ArrayList<Integer> chosenCars) { 

		cars = new ArrayList<Car>();
		colliders = new ArrayList<LineRectangle>();
		gadgets = new ArrayList<Gadget>();
		rockets = new ArrayList<Rocket>();
		oilSpills = new ArrayList<OilSpill>();

		for (int i = 0; i < playerCount; i++) {

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

		myCar = cars.get(myId);

		switch (playerCount) {

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

	public void update(float deltaTime, int accelerationState, float angle) {

		time = time + deltaTime;
		
		myCar.update(deltaTime, angle, inCollision, accelerationState,
				colliderDirectionAngle);

		//---> die Raketen werden in ihrere Update Methode vorangetrieben
		int len = rockets.size();
		for (int i = 0; i < len; i++) {
			Rocket rocket = rockets.get(i);
			rocket.update(deltaTime);
		}
		//<---
		
		checkWorldCollisions();
		checkGadgetBoxCollisions(); // TODO keine Gadgetboxen im Timetrial..
									// höchstens ggf. Speedboost

		// Die ganzen If abfragen dienen der Entlastung der Methdenaufrufe

		if (car1 != null)
			checkOppenentCollisions(); // Falls man mit mindestens einem Gegner
										// Spielt, soll die Colission gecheckt
										// werden
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
				colliderDirectionAngle = collider.angle;
				inCollision = true;
				listener.collision();
				return;
			}

		}
		inCollision = false;

	}

	private void checkOppenentCollisions() {
		
		//Ramme ich ein gegnerisches Auto?

	}

	private void checkGadgetBoxCollisions() {
		
		//sammel ich eine Gadgetbox ein?

		int len = gadgets.size();
		for (int i = 0; i < len; i++) {
			Gadget gadget = gadgets.get(i);

			
			if (!gadget.active) {  // ist eine Box schon eingesammeltworden wird, geguckt obs Zeit ist eine neue zu erstellen

				if (time - gadget.lastTimeCollected > BOX_APEARING_INTERVAL) {
					gadget.generateNewContent();
					gadget.active = true;
				}

				continue;
			}

			if (OverlapTester.intersectSegmentRectangles(myCar.bounds,
					gadget.bounds)) {
				collectedGadgetId = i;
				collectedGadgetContent = gadget.content;
				gadget.active = false;
				gadget.lastTimeCollected = time;
				listener.collectedGadget(collectedGadgetContent);
				return;
			}

		}

	}

	private void checkOilSpillCollisions() {

		//fahre Ich über eine Öllache?
		
		int len = oilSpills.size();
		for (int i = 0; i < len; i++) {
			OilSpill oilspill = oilSpills.get(i);

			if (OverlapTester.intersectSegmentRectangles(myCar.bounds,
					oilspill.bounds)) {

				listener.droveTroughtOilSpill();
				oilspill = null;
				oilSpills.remove(i);
				myCar.resetVelocity();
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

			// fliegt Rakete gegen eine Wand?

			

			
			for (int j = 0; j < colliderLen; j++) {
				LineRectangle collider = colliders.get(j);

				if (OverlapTester.intersectSegmentRectangles(
						collider, rocket.bounds)) {

					listener.detonatingRocket();
					rocket = null;
					rockets.remove(i);
                    return;    //TODO hier muss man sich was einfallen lasse, weil ja mehere Rakete gleichzeitig im umlauf sind... vll nicht so tragisch
					
				}

			}

			// trifft die Rakete mein Auto?
			if (OverlapTester.intersectSegmentRectangles(myCar.bounds,
					rocket.bounds)) {

				listener.detonatingRocket();
				rocket = null;
				rockets.remove(i);
				myCar.resetVelocity();
				return;
			}

		}

	}

	public void useGadget(int gadgetContent) {

		if (gadgetContent == GameScreen.OILSPILL) {

			//Eine Öllache wird hinter dem Auto erzeugt und existiert so lange, bis jemand drüber fährt
			oilSpills.add(new OilSpill(myCar.position.x - myCar.direction.x
					* (myCar.bounds.length + 0.1f), myCar.position.y
					- myCar.direction.y * (myCar.bounds.length + 0.1f),
					myCar.pitch, 0.8f, 0.8f));

		}

		if (gadgetContent == GameScreen.ROCKET) {

			//eine Rakete wird vor dem Auto erzeugt und fliegt in der momentanen Richtung, bis Sie auf ein Hindernis stößt
			rockets.add(new Rocket(myCar.position.x + myCar.direction.x   * (myCar.bounds.length + 0.1f),
					               myCar.position.y + myCar.direction.y * (myCar.bounds.length + 0.1f),
					               myCar.pitch, 0.034f, 0.465f));
			
	

		}

	}

	public boolean isGameOver() {
		return myCar.finished == true; // wenn auto im Ziel angelangt ist
	}

}
