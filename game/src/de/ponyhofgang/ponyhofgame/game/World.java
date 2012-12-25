package de.ponyhofgang.ponyhofgame.game;

import java.util.ArrayList;
import java.util.List;

import de.ponyhofgang.ponyhofgame.framework.math.LineRectangle;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Car;

public class World implements CarSpecs {
	public interface WorldListener {
		public void collision();

	
	}

	public static boolean raceStarted = false;

	public final static int LEVEL_DOCKS = 0;

	public final List<LineRectangle> colliders;
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

	public World(int playerCount, int myId, int worldId,
			ArrayList<Integer> chosenCars) { // Hier wird die Welt
		// zusammengebaut :)

		cars = new ArrayList<Car>();
		colliders = new ArrayList<LineRectangle>();

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

			cars.add(new Car(0 + (i + 1), -6, 0, carWidth, carLength, chosenCars.get(i)));
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
		}
	}

	public void setWorldListener(WorldListener worldListener) {
		this.listener = worldListener;
	}

	public void update(float deltaTime, int accelerationState, float angle) {

		myCar.update(deltaTime, angle, inCollision, accelerationState,
				colliderDirectionAngle);
		checkWorldCollisions();
		checkOppenentCollisions();

	}

	private void checkWorldCollisions() {

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

	}

	public boolean isGameOver() {
		return myCar.finished == true; // wenn auto im Ziel angelangt ist
	}

}
