package de.ponyhofgang.ponyhofgame.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.util.Log;

import de.ponyhofgang.ponyhofgame.framework.math.LineRectangle;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Car;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Checkpoint;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Gadget;
import de.ponyhofgang.ponyhofgame.game.gameObjects.OilSpill;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Rocket;
import de.ponyhofgang.ponyhofgame.game.screens.GameScreen;
import de.ponyhofgang.ponyhofgame.game.screens.MainMenuScreen;

public class World implements CarSpecs {

	public interface WorldListener {
		public void collision();

		public void collectedGadget(int collectedGadgetContent);

		public void droveTroughtOilSpill();

		public void detonatingRocket();

	}

	WorldListener listener;

	public static boolean raceStarted = false; // TODO

	public final static int LEVEL_DOCKS = 0;
	public final static int LEVEL_2 = 1;
	public final static int LEVEL_3 = 2;

	private static final int POSITION_FRONT = 1;
	private static final int POSITION_BACK = 2;
	private static final int POSITION_SAME = 3;

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

	private int playerCount;

	private int ticker = 0;
	public float time = 0;

	private boolean multiplayer;

	public int myId;

	private ArrayList<Checkpoint> checkpoints;

	public int laps;



	public World(int playerCount, int myId, int worldId,
			ArrayList<Integer> chosenCars, boolean multiplayer) {
		
		this.multiplayer = multiplayer;

		cars = new ArrayList<Car>();
		colliders = new ArrayList<LineRectangle>();
		gadgets = new ArrayList<Gadget>();
		rockets = new ArrayList<Rocket>();
		oilSpills = new ArrayList<OilSpill>();
		checkpoints = new ArrayList<Checkpoint>();

		// erstelle die Auto instanzen
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

			case CRYSTALSHIP:

				carWidth = CRYSTALSHIP_WIDTH;
				carLength = CRYSTALSHIP_LENGTH;

				break;
			}

			cars.add(new Car(0, 0, 180, carWidth, carLength,
					chosenCars.get(i)));
		}

		myCar = cars.get(myId); // identifiziere mein Auto

		this.myId = myId;

		this.playerCount = playerCount;

		shuffleCarPositions();

		if (worldId == LEVEL_DOCKS) {

			laps = 5; 
			
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

			checkpoints.add(new Checkpoint(-7, 6, 90, 1, 5, Checkpoint.MINUS_Y));
			checkpoints.add(new Checkpoint(-7, -8, 90, 1, 8, Checkpoint.X));
			checkpoints.add(new Checkpoint(12, -4.5f, -180, 1, 5, Checkpoint.Y));
			checkpoints.add(new Checkpoint(10, 5, 90, 1, 4, Checkpoint.MINUS_Y));
			checkpoints.add(new Checkpoint(7, -2, -90, 1, 4, Checkpoint.MINUS_X));
			checkpoints.add(new Checkpoint(-4, -0.5f, -180, 1, 5, Checkpoint.X));
			checkpoints.add(new Checkpoint(3.5f, 3.5f, -180, 1, 5,Checkpoint.MINUS_X));
			checkpoints.add(new Checkpoint(-6, 6, 90, 1, 5, Checkpoint.MINUS_Y));

		}

	}

public void shuffleCarPositions() {
		switch (playerCount) { // jenachdem wieviele leute mitspielen, werden
		// den Autos objekte zugeordnet
		// ( Ab 2 Spieler hat man selbst 2 Instanzen!!!
		// myCar + carX !!! ) + Die position wird fufällig gewählt

case 1:

car0 = myCar;
car0.position.x = -5;
car0.position.y = 6;
car0.rotate(360);


break;

case 2:

car0 = cars.get(0);
car1 = cars.get(1);

car0.position.x = -5;
car0.position.y = 6.5f;
car0.rotate(180);

car1.position.x = -5;
car1.position.y = 5.5f;
car1.rotate(180);

break;

case 3:

Integer[] array = new Integer[]{0, 1, 2};
Collections.shuffle(Arrays.asList(array));

cars.get(array[0]).position.x = -5;
cars.get(array[0]).position.y = 6.5f;
cars.get(array[1]).position.x = -5;
cars.get(array[1]).position.y = 5.5f;
cars.get(array[2]).position.x = -3.5f;
cars.get(array[2]).position.y = 6.5f;

car0 = cars.get(0);
car1 = cars.get(1);
car2 = cars.get(2);

car0.rotate(180);
car1.rotate(180);
car2.rotate(180);

break;

case 4:


Integer[] array4 = new Integer[]{0, 1, 2, 3};
Collections.shuffle(Arrays.asList(array4));

cars.get(array4[0]).position.x = -5;
cars.get(array4[0]).position.y = 6.5f;
cars.get(array4[1]).position.x = -5;
cars.get(array4[1]).position.y = 5.5f;
cars.get(array4[2]).position.x = -3.5f;
cars.get(array4[2]).position.y = 6.5f;
cars.get(array4[3]).position.x = -3.5f;
cars.get(array4[3]).position.y = 5.5f;

car0 = cars.get(0);
car1 = cars.get(1);
car2 = cars.get(2);
car3 = cars.get(3);

car0.rotate(180);
car1.rotate(180);
car2.rotate(180);
car3.rotate(180);

break;

}
		
	}

	public void setWorldListener(WorldListener worldListener) {
		this.listener = worldListener;
	}

	public void update(float deltaTime, int accelerationState, float angle) { // das
																				// kommt
																				// aus
																				// den
																				// Controlls
																				// raus
																				// vom
																				// Spieler

		time = time + deltaTime;

		myCar.update(deltaTime, angle, accelerationState);
		if(myCar.lap > laps) myCar.finished = true;

		ticker++;
		if ((multiplayer) && ticker % 1 == 0) { // wenn es mehr Spieler gibt,
												// dann sende meine Position an
												// die anderen
			ticker = 0;
			MainMenuScreen.getInstance().game.sendData(myCar.position.x,
					myCar.position.y, myCar.pitch, myCar.lap, myCar.inCollider, "data");

		}

		// Falls das Auto ausrutscht
		if (myCar.state == Car.SLIPPING) {
			if (time - myCar.slippingStartTime > Car.SLIPPING_DURATION)
				myCar.state = Car.DRIVING;
		}

		// ---> die Raketen werden in ihrer Update Methode vorangetrieben
		int len = rockets.size();
		for (int i = 0; i < len; i++) {
			Rocket rocket = rockets.get(i);
			if (rocket == null)
				continue;

			rocket.update(deltaTime);
			if (rocket.explotionTimeState > Assets.explosionAnim.frameDuration * 12)
				rockets.set(i, null);

		}
		// <---

		// ----> Rank wird gecheckt
		checkCheckpointCollision();
		checkLap();
		if (multiplayer)
			checkRank();
		// <----

		checkWorldCollisions();
		checkGadgetBoxCollisions(); // TODO keine Gadgetboxen im Timetrial.. //
									// höchstens ggf. Speedboost

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

		// Ramme ich eine Wand?

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

		// Nur falls er nicht im Slippingmode it, soll der DRIVING modus
		// aktiviert werden, da dies oben geklärt wird
		if (myCar.state != Car.SLIPPING)
			myCar.state = Car.DRIVING;

	}

	private void checkOppenentCollisions() {

		// Ramme ich ein gegnerisches Auto?

	}

	private void checkGadgetBoxCollisions() {

		// sammel ich eine Gadgetbox ein?

		int len = gadgets.size();
		for (int i = 0; i < len; i++) {
			Gadget gadget = gadgets.get(i);

			if (!gadget.active) { // ist eine Box schon eingesammelt worden,
									// wird geguckt ob es Zeit ist eine neue zu
									// erstellen

				if (time - gadget.lastTimeCollected > Gadget.BOX_APEARING_INTERVAL) {
					gadget.generateNewContent();
					gadget.active = true;
				}

				continue;
			}

			if (OverlapTester.intersectSegmentRectangles(myCar.bounds,
					gadget.bounds)) {

				if (myCar.gadgetState == Car.NO_GADGET)
					myCar.collectedGadgetId = i; // falls noch kein Gadget
													// eingesammelt wurde

				gadget.active = false;
				gadget.lastTimeCollected = time;

				if (multiplayer)
					MainMenuScreen.getInstance().game.sendStringCommands(i
							+ " " + time, "boxCollected"); // melde den anderen
															// Spielern, das
															// einsammeln
				// TODO hier ist die Zeit asynchron, da eineige Spieler früher
				// in das Spiel gehen als andere.. allerdings wird sich das von
				// selbst lösen, wenn
				// die Zeit erst beim Startschuss gesetzt wird :)

				listener.collectedGadget(gadget.content);
				return;
			}

		}

	}

	private void checkOilSpillCollisions() {

		// fahre Ich über eine Öllache?

		int len = oilSpills.size();
		for (int i = 0; i < len; i++) {
			OilSpill oilspill = oilSpills.get(i);

			if (oilspill != null
					&& OverlapTester.intersectSegmentRectangles(myCar.bounds,
							oilspill.bounds)) {

				listener.droveTroughtOilSpill();

				oilSpills.set(i, null);
				if (multiplayer)
					MainMenuScreen.getInstance().game.sendStringCommands(i
							+ " ", "slipped");
				// send Remove

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

			if (rocket != null) {
				// fliegt Rakete gegen eine Wand?

				for (int j = 0; j < colliderLen; j++) {
					LineRectangle collider = colliders.get(j);

					if (OverlapTester.intersectSegmentRectangles(collider,
							rocket.bounds)) {

						listener.detonatingRocket();

						rocket.state = Rocket.DETONATING;

						return; // TODO hier muss man sich was einfallen lassen,
						// weil ja mehere Rakete gleichzeitig im umlauf sind...
						// vll nicht so tragisch, da schnelle wiederholung

					}

				}

				// trifft die Rakete mein Auto?
				if (OverlapTester.intersectSegmentRectangles(myCar.bounds,
						rocket.bounds)) {

					listener.detonatingRocket();

					rocket.state = Rocket.DETONATING;

					myCar.resetVelocity();
					if (multiplayer)
						MainMenuScreen.getInstance().game.sendStringCommands(i
								+ " ", "hit");
					return;
				}

			}
		}

	}

private int checkCheckpointCollision() {
		

		int len = checkpoints.size();
		for (int i = 0; i < len; i++) {
			Checkpoint checkpoint = checkpoints.get(i);
			if (OverlapTester.intersectSegmentRectanglesLine(myCar.bounds,
					checkpoint)) {
				myCar.inCollider = i;
				myCar.inColliderType = checkpoint.type;
				return i;
			}
		}
		return 0;
	}

	private void checkRank() {

		int checkerRank = 0;

		switch (playerCount) {

		case 2:

			if (checkOtherPlayerRank(myCar, car0) == POSITION_SAME)
				{
				if (checkOtherPlayerRank(myCar, car1) == POSITION_FRONT)
					myCar.rank = 1;
				if (checkOtherPlayerRank(myCar, car1) == POSITION_BACK)
					myCar.rank = 2;
				}
			if (checkOtherPlayerRank(myCar, car1) == POSITION_SAME)
			{
			if (checkOtherPlayerRank(myCar, car0) == POSITION_FRONT)
				myCar.rank = 1;
			if (checkOtherPlayerRank(myCar, car0) == POSITION_BACK)
				myCar.rank = 2;
			}

			
			break;

		case 3:

			checkerRank = 3;
			
			if (checkOtherPlayerRank(myCar, car0) == POSITION_FRONT)
				checkerRank--;
			if (checkOtherPlayerRank(myCar, car1) == POSITION_FRONT)
				checkerRank--;
			if (checkOtherPlayerRank(myCar, car2) == POSITION_FRONT)
				checkerRank--;

			myCar.rank = checkerRank;
			
			break;
			
		case 4:

			checkerRank = 4;
			
			if (checkOtherPlayerRank(myCar, car0) == POSITION_FRONT)
				checkerRank--;
			if (checkOtherPlayerRank(myCar, car1) == POSITION_FRONT)
				checkerRank--;
			if (checkOtherPlayerRank(myCar, car2) == POSITION_FRONT)
				checkerRank--;
			if (checkOtherPlayerRank(myCar, car3) == POSITION_FRONT)
				checkerRank--;

			myCar.rank = checkerRank;
			
			break;

		}
	}

	private int checkOtherPlayerRank(Car myCar, Car opponentCar) {

		Log.d("collidertype", ""+opponentCar.lap);
		if(myCar.equals(opponentCar)) return POSITION_SAME;
		if (myCar.lap == opponentCar.lap) {
			if (myCar.inCollider > opponentCar.inCollider)
				return POSITION_FRONT;
			if (myCar.inCollider < opponentCar.inCollider)
				return POSITION_BACK;
			if (myCar.inCollider == opponentCar.inCollider) {

				switch (myCar.inColliderType) {

				case (Checkpoint.X):

					if (myCar.position.x > opponentCar.position.x)
						return POSITION_FRONT;
					else
						return POSITION_BACK;

				case (Checkpoint.MINUS_X):

					if (myCar.position.x < opponentCar.position.x)
						return POSITION_FRONT;
					else
						return POSITION_BACK;

				case (Checkpoint.Y):

					if (myCar.position.y > opponentCar.position.y)
						return POSITION_FRONT;
					else
						return POSITION_BACK;

				case (Checkpoint.MINUS_Y):

					if (myCar.position.y < opponentCar.position.y)
						return POSITION_FRONT;
					else
						return POSITION_BACK;
				}
			}
		} else if(myCar.lap > opponentCar.lap) {
			return POSITION_FRONT;
		}else if(myCar.lap < opponentCar.lap){

		return POSITION_BACK;
		}
	return POSITION_BACK;
	}

	
	

	private void checkLap() {
			if (myCar.inCollider == myCar.colliderCount) {
				myCar.colliderCount++;
				if (myCar.colliderCount == checkpoints.size()) {

					myCar.lap++;
					myCar.colliderCount = 0;
				}
				
			}

	}

	

	public void useGadget(int gadgetContent) {

		if (gadgetContent == Gadget.OILSPILL) {

			// Eine Öllache wird hinter dem Auto erzeugt und existiert so lange,
			// bis jemand drüber fährt
			oilSpills.add(new OilSpill(myCar.position.x
					- myCar.bounds.direction.x * (myCar.bounds.length + 0.1f),
					myCar.position.y - myCar.bounds.direction.y
							* (myCar.bounds.length + 0.1f), myCar.pitch, 0.8f,
					0.8f));

			if (multiplayer)
				MainMenuScreen.getInstance().game
						.sendData(myCar.position.x - myCar.bounds.direction.x
								* (myCar.bounds.length + 0.1f),
								myCar.position.y - myCar.bounds.direction.y
										* (myCar.bounds.length + 0.1f),
								myCar.pitch,  myCar.lap, myCar.inCollider, "oilSpill");

		}

		if (gadgetContent == Gadget.ROCKET) {

			// eine Rakete wird vor dem Auto erzeugt und fliegt in der
			// momentanen Richtung, bis Sie auf ein Hindernis stößt
			rockets.add(new Rocket(myCar.position.x + myCar.bounds.direction.x
					* (myCar.bounds.length + 0.1f), myCar.position.y
					+ myCar.bounds.direction.y * (myCar.bounds.length + 0.1f),
					myCar.pitch, 0.034f, 0.465f));
			if (multiplayer)
				MainMenuScreen.getInstance().game.sendData(myCar.position.x
						+ myCar.bounds.direction.x
						* (myCar.bounds.length + 0.1f), myCar.position.y
						+ myCar.bounds.direction.y
						* (myCar.bounds.length + 0.1f), myCar.pitch,  myCar.lap, myCar.inCollider, "rocket");

		}

	}

	public boolean isGameOver() {
		return myCar.finished == true; // wenn auto im Ziel angelangt ist
	}

	public void deactivateBox(int id, float time) {

		Assets.playSound(Assets.collectSound);
		Gadget gadget = gadgets.get(id);
		gadget.active = false;
		gadget.lastTimeCollected = time;

	}

	public void removeOilSplill(int id) {

		listener.droveTroughtOilSpill();
		oilSpills.set(id, null);

		// TODO animation bei den anderen abspielen
		// car.state = Car.SLIPPING;
		// car.slippingStartTime = time;

	}

	public void removeRocket(int id) {

		listener.detonatingRocket();
		rockets.set(id, null);

		// TODO animation bei den anderen abspielen
		// rockets.get(id).state = Rocket.DETONATING;

	}

	public void createOilSplill(float x, float y, float angle) {

		Assets.playSound(Assets.squashSound);
		oilSpills.add(new OilSpill(x, y, angle, 0.8f, 0.8f));

	}

	public void createRocket(float x, float y, float angle) {

		Assets.playSound(Assets.launchingSound);
		rockets.add(new Rocket(x, y, angle, 0.034f, 0.465f));

	}
	
	public void resetWorld(){
		
		time = 0;
		myCar.lap = 1;
		myCar.resetVelocity();
		myCar.inStartCollision = true;
		myCar.finished = false;
		oilSpills.clear();
		
	}

}
