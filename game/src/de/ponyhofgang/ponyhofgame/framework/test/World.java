package de.ponyhofgang.ponyhofgame.framework.test;

import android.util.Log;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;



public class World {
public interface WorldListener {
public void explosion();
public void shot();
}

final static float WORLD_MIN_X = -14;
final static float WORLD_MAX_X = 14;
final static float WORLD_MIN_Z = -15;

WorldListener listener;



GengarStatic gengar;
final Gengar gengar2;



public World() {    //TODO Hier wird die Welt zusammengebaut :)
gengar = new GengarStatic(0, 0, -2, 0.608f*5, 0.296f*5, 1.545f*5);
gengar2 = new Gengar(0, 0, -1.4f, 0.608f*5, 0.296f*5, 1.545f*5);




}

public void setWorldListener(WorldListener worldListener) {
this.listener = worldListener;
}

public void update(float deltaTime, float accelX, float angle) {
	
	gengar2.update(deltaTime, accelX, angle);  //TODO collisionsbox mitverschieben !
	checkWorldCollisions();
	checkOppenentCollisions();
	


}

private void checkOppenentCollisions() {
	
		//int len = opponents.size();
		//for (int i = 0; i < len; i++) {
		//Car opponent = oponents.get(i);
		if (OverlapTester.overlapCubes(gengar.bounds, gengar2.bounds)) {
		Log.d("collision", "collision");
		return;
		}
		//}
	
}

private void checkWorldCollisions() {
	// TODO Auto-generated method stub
	
}


public boolean isGameOver() { 
return gengar.finished == true;   //wenn auto im Ziel angelangt ist
}





}



